#include <stdio.h>
void lava_set(unsigned int bn, unsigned int val);
extern unsigned int lava_get(unsigned int) ;
static unsigned int lava_val[1000000];
void lava_set(unsigned int bug_num, unsigned int val);
void lava_set(unsigned int bug_num, unsigned int val) { lava_val[bug_num] = val; }
unsigned int lava_get(unsigned int bug_num);
unsigned int lava_get(unsigned int bug_num) {
#define SWAP_UINT32(x) (((x) >> 24) | (((x) & 0x00FF0000) >> 8) | (((x) & 0x0000FF00) << 8) | ((x) << 24))
    if (0x6c617661 - bug_num == lava_val[bug_num] ||
        SWAP_UINT32(0x6c617661 - bug_num) == lava_val[bug_num]) {
        dprintf(1,"Successfully triggered bug %d, crashing now!\n", bug_num);
    }
    else {
        //printf("Not successful for bug %d; val = %08x not %08x or %08x\n", bug_num, lava_val[bug_num], 0x6c617661 + bug_num, 0x6176616c + bug_num);
    }
    return lava_val[bug_num];
}
/* Compute checksums of files or strings.
   Copyright (C) 1995-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* Written by Ulrich Drepper <drepper@gnu.ai.mit.edu>.  */

#include <config.h>

#include <getopt.h>
#include <sys/types.h>

#include "system.h"

#if HASH_ALGO_MD5
# include "md5.h"
#endif
#if HASH_ALGO_SHA1
# include "sha1.h"
#endif
#if HASH_ALGO_SHA256 || HASH_ALGO_SHA224
# include "sha256.h"
#endif
#if HASH_ALGO_SHA512 || HASH_ALGO_SHA384
# include "sha512.h"
#endif
#include "error.h"
#include "fadvise.h"
#include "stdio--.h"
#include "xfreopen.h"

/* The official name of this program (e.g., no 'g' prefix).  */
#if HASH_ALGO_MD5
# define PROGRAM_NAME "md5sum"
# define DIGEST_TYPE_STRING "MD5"
# define DIGEST_STREAM md5_stream
# define DIGEST_BITS 128
# define DIGEST_REFERENCE "RFC 1321"
# define DIGEST_ALIGN 4
#elif HASH_ALGO_SHA1
# define PROGRAM_NAME "sha1sum"
# define DIGEST_TYPE_STRING "SHA1"
# define DIGEST_STREAM sha1_stream
# define DIGEST_BITS 160
# define DIGEST_REFERENCE "FIPS-180-1"
# define DIGEST_ALIGN 4
#elif HASH_ALGO_SHA256
# define PROGRAM_NAME "sha256sum"
# define DIGEST_TYPE_STRING "SHA256"
# define DIGEST_STREAM sha256_stream
# define DIGEST_BITS 256
# define DIGEST_REFERENCE "FIPS-180-2"
# define DIGEST_ALIGN 4
#elif HASH_ALGO_SHA224
# define PROGRAM_NAME "sha224sum"
# define DIGEST_TYPE_STRING "SHA224"
# define DIGEST_STREAM sha224_stream
# define DIGEST_BITS 224
# define DIGEST_REFERENCE "RFC 3874"
# define DIGEST_ALIGN 4
#elif HASH_ALGO_SHA512
# define PROGRAM_NAME "sha512sum"
# define DIGEST_TYPE_STRING "SHA512"
# define DIGEST_STREAM sha512_stream
# define DIGEST_BITS 512
# define DIGEST_REFERENCE "FIPS-180-2"
# define DIGEST_ALIGN 8
#elif HASH_ALGO_SHA384
# define PROGRAM_NAME "sha384sum"
# define DIGEST_TYPE_STRING "SHA384"
# define DIGEST_STREAM sha384_stream
# define DIGEST_BITS 384
# define DIGEST_REFERENCE "FIPS-180-2"
# define DIGEST_ALIGN 8
#else
# error "Can't decide which hash algorithm to compile."
#endif

#define DIGEST_HEX_BYTES (DIGEST_BITS / 4)
#define DIGEST_BIN_BYTES (DIGEST_BITS / 8)

#define AUTHORS \
  proper_name ("Ulrich Drepper"), \
  proper_name ("Scott Miller"), \
  proper_name ("David Madore")

/* The minimum length of a valid digest line.  This length does
   not include any newline character at the end of a line.  */
#define MIN_DIGEST_LINE_LENGTH \
  (DIGEST_HEX_BYTES /* length of hexadecimal message digest */ \
   + 1 /* blank */ \
   + 1 /* minimum filename length */ )

/* True if any of the files read were the standard input. */
static bool have_read_stdin;

/* The minimum length of a valid checksum line for the selected algorithm.  */
static size_t min_digest_line_length;

/* Set to the length of a digest hex string for the selected algorithm.  */
static size_t digest_hex_bytes;

/* With --check, don't generate any output.
   The exit code indicates success or failure.  */
static bool status_only = false;

/* With --check, print a message to standard error warning about each
   improperly formatted checksum line.  */
static bool warn = false;

/* With --check, suppress the "OK" printed for each verified file.  */
static bool quiet = false;

/* With --check, exit with a non-zero return code if any line is
   improperly formatted. */
static bool strict = false;

/* Whether a BSD reversed format checksum is detected.  */
static int bsd_reversed = -1;

/* For long options that have no equivalent short option, use a
   non-character as a pseudo short option, starting with CHAR_MAX + 1.  */
enum
{
  STATUS_OPTION = CHAR_MAX + 1,
  QUIET_OPTION,
  STRICT_OPTION,
  TAG_OPTION
};

static struct option const long_options[] =
{
  { "binary", no_argument, NULL, 'b' },
  { "check", no_argument, NULL, 'c' },
  { "quiet", no_argument, NULL, QUIET_OPTION },
  { "status", no_argument, NULL, STATUS_OPTION },
  { "text", no_argument, NULL, 't' },
  { "warn", no_argument, NULL, 'w' },
  { "strict", no_argument, NULL, STRICT_OPTION },
  { "tag", no_argument, NULL, TAG_OPTION },
  { GETOPT_HELP_OPTION_DECL },
  { GETOPT_VERSION_OPTION_DECL },
  { NULL, 0, NULL, 0 }
};

void
usage (int status)
{
  if (status != EXIT_SUCCESS)
    emit_try_help ();
  else
    {
      printf (_("\
Usage: %s [OPTION]... [FILE]...\n\
Print or check %s (%d-bit) checksums.\n\
"),
              program_name,
              DIGEST_TYPE_STRING,
              DIGEST_BITS);

      emit_stdin_note ();

      if (O_BINARY)
        fputs (_("\
\n\
  -b, --binary         read in binary mode (default unless reading tty stdin)\n\
"), stdout);
      else
        fputs (_("\
\n\
  -b, --binary         read in binary mode\n\
"), stdout);
      printf (_("\
  -c, --check          read %s sums from the FILEs and check them\n"),
              DIGEST_TYPE_STRING);
      fputs (_("\
      --tag            create a BSD-style checksum\n\
"), stdout);
      if (O_BINARY)
        fputs (_("\
  -t, --text           read in text mode (default if reading tty stdin)\n\
"), stdout);
      else
        fputs (_("\
  -t, --text           read in text mode (default)\n\
"), stdout);
      fputs (_("\
\n\
The following four options are useful only when verifying checksums:\n\
      --quiet          don't print OK for each successfully verified file\n\
      --status         don't output anything, status code shows success\n\
      --strict         exit non-zero for improperly formatted checksum lines\n\
  -w, --warn           warn about improperly formatted checksum lines\n\
\n\
"), stdout);
      fputs (HELP_OPTION_DESCRIPTION, stdout);
      fputs (VERSION_OPTION_DESCRIPTION, stdout);
      printf (_("\
\n\
The sums are computed as described in %s.  When checking, the input\n\
should be a former output of this program.  The default mode is to print a\n\
line with checksum, a space, a character indicating input mode ('*' for binary,\
\n' ' for text or where binary is insignificant), and name for each FILE.\n"),
              DIGEST_REFERENCE);
      emit_ancillary_info (PROGRAM_NAME);
    }

  exit (status);
}

#define ISWHITE(c) ((c) == ' ' || (c) == '\t')

/* Given a file name, S of length S_LEN, that is not NUL-terminated,
   modify it in place, performing the equivalent of this sed substitution:
   's/\\n/\n/g;s/\\\\/\\/g' i.e., replacing each "\\n" string with a newline
   and each "\\\\" with a single backslash, NUL-terminate it and return S.
   If S is not a valid escaped file name, i.e., if it ends with an odd number
   of backslashes or if it contains a backslash followed by anything other
   than "n" or another backslash, return NULL.  */

static char *
filename_unescape (char *s, size_t s_len)
{
  char *dst = s;

  for (size_t i = 0; i < s_len; i++)
    {
      switch (s[i])
        {
        case '\\':
          if (i == s_len - 1)
            {
              /* File name ends with an unescaped backslash: invalid.  */
              return NULL;
            }
          ++i;
          switch (s[i])
            {
            case 'n':
              *dst++ = '\n';
              break;
            case '\\':
              *dst++ = '\\';
              break;
            default:
              /* Only '\' or 'n' may follow a backslash.  */
              return NULL;
            }
          break;

        case '\0':
          /* The file name may not contain a NUL.  */
          return NULL;

        default:
          *dst++ = s[i];
          break;
        }
    }
  if (dst < s + s_len)
    *dst = '\0';

  return s;
}

/* Split the checksum string S (of length S_LEN) from a BSD 'md5' or
   'sha1' command into two parts: a hexadecimal digest, and the file
   name.  S is modified.  Return true if successful.  */

static bool
bsd_split_3 (char *s, size_t s_len, unsigned char **hex_digest,
             char **file_name, bool escaped_filename)
{
  size_t i;

  if (s_len == 0)
    return false;

  /* Find end of filename.  */
  i = s_len - 1;
  while (i && s[i] != ')')
    i--;

  if (s[i] != ')')
    return false;

  *file_name = s;

  if (escaped_filename && filename_unescape (s, i) == NULL)
    return false;

  s[i++] = '\0';

  while (ISWHITE (s[i]))
    i++;

  if (s[i] != '=')
    return false;

  i++;

  while (ISWHITE (s[i]))
    i++;

  *hex_digest = (unsigned char *) &s[i];
  return true;
}

/* Split the string S (of length S_LEN) into three parts:
   a hexadecimal digest, binary flag, and the file name.
   S is modified.  Return true if successful.  */

static bool
split_3 (char *s, size_t s_len,
         unsigned char **hex_digest, int *binary, char **file_name)
{
  bool escaped_filename = false;
  size_t algo_name_len;

  size_t i = 0;
  while (ISWHITE (s[i]))
    ++i;

  if (s[i] == '\\')
    {
      ++i;
      escaped_filename = true;
    }

  /* Check for BSD-style checksum line. */

  algo_name_len = strlen (DIGEST_TYPE_STRING);
  if (STREQ_LEN (s + i, DIGEST_TYPE_STRING, algo_name_len))
    {
      if (s[i + algo_name_len] == ' ')
        ++i;
      if (s[i + algo_name_len] == '(')
        {
          *binary = 0;
          return bsd_split_3 (s +      i + algo_name_len + 1,
                              s_len - (i + algo_name_len + 1),
                              hex_digest, file_name, escaped_filename);
        }
    }

  /* Ignore this line if it is too short.
     Each line must have at least 'min_digest_line_length - 1' (or one more, if
     the first is a backslash) more characters to contain correct message digest
     information.  */
  if (s_len - i < min_digest_line_length + (s[i] == '\\'))
    return false;

  *hex_digest = (unsigned char *) &s[i];

  /* The first field has to be the n-character hexadecimal
     representation of the message digest.  If it is not followed
     immediately by a white space it's an error.  */
  i += digest_hex_bytes;
  if (!ISWHITE (s[i]))
    return false;

  s[i++] = '\0';

  /* If "bsd reversed" format detected.  */
  if ((s_len - i == 1) || (s[i] != ' ' && s[i] != '*'))
    {
      /* Don't allow mixing bsd and standard formats,
         to minimize security issues with attackers
         renaming files with leading spaces.
         This assumes that with bsd format checksums
         that the first file name does not have
         a leading ' ' or '*'.  */
      if (bsd_reversed == 0)
        return false;
      bsd_reversed = 1;
    }
  else if (bsd_reversed != 1)
    {
      bsd_reversed = 0;
      *binary = (s[i++] == '*');
    }

  /* All characters between the type indicator and end of line are
     significant -- that includes leading and trailing white space.  */
  *file_name = &s[i];

  if (escaped_filename)
    return filename_unescape (&s[i], s_len - i) != NULL;

  return true;
}

/* Return true if S is a NUL-terminated string of DIGEST_HEX_BYTES hex digits.
   Otherwise, return false.  */
static bool _GL_ATTRIBUTE_PURE
hex_digits (unsigned char const *s)
{
  unsigned int i;
  for (i = 0; i < digest_hex_bytes; i++)
    {
      if (!isxdigit (*s))
        return false;
      ++s;
    }
  return *s == '\0';
}

/* An interface to the function, DIGEST_STREAM.
   Operate on FILENAME (it may be "-").

   *BINARY indicates whether the file is binary.  BINARY < 0 means it
   depends on whether binary mode makes any difference and the file is
   a terminal; in that case, clear *BINARY if the file was treated as
   text because it was a terminal.

   Put the checksum in *BIN_RESULT, which must be properly aligned.
   Return true if successful.  */

static bool
digest_file (const char *filename, int *binary, unsigned char *bin_result)
{
  FILE *fp;
  int err;
  bool is_stdin = STREQ (filename, "-");

  if (is_stdin)
    {
      have_read_stdin = true;
      fp = stdin;
      if (O_BINARY && *binary)
        {
          if (*binary < 0)
            *binary = ! isatty (STDIN_FILENO);
          if (*binary)
            xfreopen (NULL, "rb", stdin);
        }
    }
  else
    {
      fp = fopen (filename, (O_BINARY && *binary ? "rb" : "r"));
      if (fp == NULL)
        {
          ({if (((filename)) && ((filename)))  {int lava_547 = 0;
          lava_547 |= ((unsigned char *) (filename))[0] << (0*8);lava_547 |= ((unsigned char *) (filename))[1] << (1*8);lava_547 |= ((unsigned char *) (filename))[2] << (2*8);lava_547 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(547,lava_547);
          int lava_563 = 0;
          lava_563 |= ((unsigned char *) (filename))[0] << (0*8);lava_563 |= ((unsigned char *) (filename))[1] << (1*8);lava_563 |= ((unsigned char *) (filename))[2] << (2*8);lava_563 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(563,lava_563);
          int lava_499 = 0;
          lava_499 |= ((unsigned char *) (filename))[0] << (0*8);lava_499 |= ((unsigned char *) (filename))[1] << (1*8);lava_499 |= ((unsigned char *) (filename))[2] << (2*8);lava_499 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(499,lava_499);
          }error (0, errno, "%s", filename);});
          return false;
        }
    }

  fadvise (fp+(lava_get(320))*(0x6c617521==(lava_get(320))||0x2175616c==(lava_get(320))), FADVISE_SEQUENTIAL);

  err = DIGEST_STREAM (fp, bin_result);
  if (err)
    {
      error (0, errno, "%s", filename);
      if (fp != stdin)
        fclose (fp);
      return false;
    }

  if (!is_stdin && fclose (fp) != 0)
    {
      error (0, errno, "%s", filename);
      return false;
    }

  return true;
}

static bool
digest_check (const char *checkfile_name)
{
  FILE *checkfile_stream;
  uintmax_t n_misformatted_lines = 0;
  uintmax_t n_properly_formatted_lines = 0;
  uintmax_t n_improperly_formatted_lines = 0;
  uintmax_t n_mismatched_checksums = 0;
  uintmax_t n_open_or_read_failures = 0;
  unsigned char bin_buffer_unaligned[DIGEST_BIN_BYTES + DIGEST_ALIGN];
  /* Make sure bin_buffer is properly aligned. */
  unsigned char *bin_buffer = ptr_align (bin_buffer_unaligned, DIGEST_ALIGN);
  uintmax_t line_number;
  char *line;
  size_t line_chars_allocated;
  bool is_stdin = STREQ (checkfile_name, "-");

  if (is_stdin)
    {
      have_read_stdin = true;
      checkfile_name = _("standard input");
      checkfile_stream = stdin;
    }
  else
    {
      checkfile_stream = fopen (checkfile_name, "r");
      if (checkfile_stream == NULL)
        {
          error (0, errno, "%s", checkfile_name);
          return false;
        }
    }

  line_number = 0;
  line = NULL;
  line_chars_allocated = 0;
  do
    {
      char *filename IF_LINT ( = NULL);
      int binary;
      unsigned char *hex_digest IF_LINT ( = NULL);
      ssize_t line_length;

      ++line_number;
      if (line_number == 0)
        error (EXIT_FAILURE, 0, _("%s: too many checksum lines"),
               checkfile_name);

      line_length = ({if (((line)) && ((line)))  {int lava_549 = 0;
      lava_549 |= ((unsigned char *) (line))[0] << (0*8);lava_549 |= ((unsigned char *) (line))[1] << (1*8);lava_549 |= ((unsigned char *) (line))[2] << (2*8);lava_549 |= ((unsigned char *) (line))[3] << (3*8);lava_set(549,lava_549);
      int lava_565 = 0;
      lava_565 |= ((unsigned char *) (line))[0] << (0*8);lava_565 |= ((unsigned char *) (line))[1] << (1*8);lava_565 |= ((unsigned char *) (line))[2] << (2*8);lava_565 |= ((unsigned char *) (line))[3] << (3*8);lava_set(565,lava_565);
      int lava_314 = 0;
      lava_314 |= ((unsigned char *) (line))[0] << (0*8);lava_314 |= ((unsigned char *) (line))[1] << (1*8);lava_314 |= ((unsigned char *) (line))[2] << (2*8);lava_314 |= ((unsigned char *) (line))[3] << (3*8);lava_set(314,lava_314);
      int lava_317 = 0;
      lava_317 |= ((unsigned char *) (line))[0] << (0*8);lava_317 |= ((unsigned char *) (line))[1] << (1*8);lava_317 |= ((unsigned char *) (line))[2] << (2*8);lava_317 |= ((unsigned char *) (line))[3] << (3*8);lava_set(317,lava_317);
      int lava_323 = 0;
      lava_323 |= ((unsigned char *) (line))[0] << (0*8);lava_323 |= ((unsigned char *) (line))[1] << (1*8);lava_323 |= ((unsigned char *) (line))[2] << (2*8);lava_323 |= ((unsigned char *) (line))[3] << (3*8);lava_set(323,lava_323);
      int lava_326 = 0;
      lava_326 |= ((unsigned char *) (line))[0] << (0*8);lava_326 |= ((unsigned char *) (line))[1] << (1*8);lava_326 |= ((unsigned char *) (line))[2] << (2*8);lava_326 |= ((unsigned char *) (line))[3] << (3*8);lava_set(326,lava_326);
      int lava_371 = 0;
      lava_371 |= ((unsigned char *) (line))[0] << (0*8);lava_371 |= ((unsigned char *) (line))[1] << (1*8);lava_371 |= ((unsigned char *) (line))[2] << (2*8);lava_371 |= ((unsigned char *) (line))[3] << (3*8);lava_set(371,lava_371);
      int lava_374 = 0;
      lava_374 |= ((unsigned char *) (line))[0] << (0*8);lava_374 |= ((unsigned char *) (line))[1] << (1*8);lava_374 |= ((unsigned char *) (line))[2] << (2*8);lava_374 |= ((unsigned char *) (line))[3] << (3*8);lava_set(374,lava_374);
      int lava_359 = 0;
      lava_359 |= ((unsigned char *) (line))[0] << (0*8);lava_359 |= ((unsigned char *) (line))[1] << (1*8);lava_359 |= ((unsigned char *) (line))[2] << (2*8);lava_359 |= ((unsigned char *) (line))[3] << (3*8);lava_set(359,lava_359);
      int lava_356 = 0;
      lava_356 |= ((unsigned char *) (line))[0] << (0*8);lava_356 |= ((unsigned char *) (line))[1] << (1*8);lava_356 |= ((unsigned char *) (line))[2] << (2*8);lava_356 |= ((unsigned char *) (line))[3] << (3*8);lava_set(356,lava_356);
      int lava_362 = 0;
      lava_362 |= ((unsigned char *) (line))[0] << (0*8);lava_362 |= ((unsigned char *) (line))[1] << (1*8);lava_362 |= ((unsigned char *) (line))[2] << (2*8);lava_362 |= ((unsigned char *) (line))[3] << (3*8);lava_set(362,lava_362);
      int lava_365 = 0;
      lava_365 |= ((unsigned char *) (line))[0] << (0*8);lava_365 |= ((unsigned char *) (line))[1] << (1*8);lava_365 |= ((unsigned char *) (line))[2] << (2*8);lava_365 |= ((unsigned char *) (line))[3] << (3*8);lava_set(365,lava_365);
      int lava_368 = 0;
      lava_368 |= ((unsigned char *) (line))[0] << (0*8);lava_368 |= ((unsigned char *) (line))[1] << (1*8);lava_368 |= ((unsigned char *) (line))[2] << (2*8);lava_368 |= ((unsigned char *) (line))[3] << (3*8);lava_set(368,lava_368);
      int lava_347 = 0;
      lava_347 |= ((unsigned char *) (line))[0] << (0*8);lava_347 |= ((unsigned char *) (line))[1] << (1*8);lava_347 |= ((unsigned char *) (line))[2] << (2*8);lava_347 |= ((unsigned char *) (line))[3] << (3*8);lava_set(347,lava_347);
      int lava_353 = 0;
      lava_353 |= ((unsigned char *) (line))[0] << (0*8);lava_353 |= ((unsigned char *) (line))[1] << (1*8);lava_353 |= ((unsigned char *) (line))[2] << (2*8);lava_353 |= ((unsigned char *) (line))[3] << (3*8);lava_set(353,lava_353);
      int lava_332 = 0;
      lava_332 |= ((unsigned char *) (line))[0] << (0*8);lava_332 |= ((unsigned char *) (line))[1] << (1*8);lava_332 |= ((unsigned char *) (line))[2] << (2*8);lava_332 |= ((unsigned char *) (line))[3] << (3*8);lava_set(332,lava_332);
      int lava_380 = 0;
      lava_380 |= ((unsigned char *) (line))[0] << (0*8);lava_380 |= ((unsigned char *) (line))[1] << (1*8);lava_380 |= ((unsigned char *) (line))[2] << (2*8);lava_380 |= ((unsigned char *) (line))[3] << (3*8);lava_set(380,lava_380);
      int lava_335 = 0;
      lava_335 |= ((unsigned char *) (line))[0] << (0*8);lava_335 |= ((unsigned char *) (line))[1] << (1*8);lava_335 |= ((unsigned char *) (line))[2] << (2*8);lava_335 |= ((unsigned char *) (line))[3] << (3*8);lava_set(335,lava_335);
      int lava_341 = 0;
      lava_341 |= ((unsigned char *) (line))[0] << (0*8);lava_341 |= ((unsigned char *) (line))[1] << (1*8);lava_341 |= ((unsigned char *) (line))[2] << (2*8);lava_341 |= ((unsigned char *) (line))[3] << (3*8);lava_set(341,lava_341);
      int lava_320 = 0;
      lava_320 |= ((unsigned char *) (line))[0] << (0*8);lava_320 |= ((unsigned char *) (line))[1] << (1*8);lava_320 |= ((unsigned char *) (line))[2] << (2*8);lava_320 |= ((unsigned char *) (line))[3] << (3*8);lava_set(320,lava_320);
      int lava_270 = 0;
      lava_270 |= ((unsigned char *) (line))[0] << (0*8);lava_270 |= ((unsigned char *) (line))[1] << (1*8);lava_270 |= ((unsigned char *) (line))[2] << (2*8);lava_270 |= ((unsigned char *) (line))[3] << (3*8);lava_set(270,lava_270);
      int lava_281 = 0;
      lava_281 |= ((unsigned char *) (line))[0] << (0*8);lava_281 |= ((unsigned char *) (line))[1] << (1*8);lava_281 |= ((unsigned char *) (line))[2] << (2*8);lava_281 |= ((unsigned char *) (line))[3] << (3*8);lava_set(281,lava_281);
      int lava_303 = 0;
      lava_303 |= ((unsigned char *) (line))[0] << (0*8);lava_303 |= ((unsigned char *) (line))[1] << (1*8);lava_303 |= ((unsigned char *) (line))[2] << (2*8);lava_303 |= ((unsigned char *) (line))[3] << (3*8);lava_set(303,lava_303);
      }__ssize_t kbcieiubweuhc1540383426 = getline (&line+(lava_get(268))*(0x6c617555==(lava_get(268))||0x5575616c==(lava_get(268)))+(lava_get(271))*(0x6c617552==(lava_get(271))||0x5275616c==(lava_get(271)))+(lava_get(273))*(0x6c617550==(lava_get(273))||0x5075616c==(lava_get(273)))+(lava_get(277))*(0x6c61754c==(lava_get(277))||0x4c75616c==(lava_get(277))), &line_chars_allocated+(lava_get(269))*(0x6c617554==(lava_get(269))||0x5475616c==(lava_get(269)))+(lava_get(272))*(0x6c617551==(lava_get(272))||0x5175616c==(lava_get(272)))+(lava_get(274))*(0x6c61754f==(lava_get(274))||0x4f75616c==(lava_get(274))), checkfile_stream+(lava_get(270))*(0x6c617553==(lava_get(270))||0x5375616c==(lava_get(270)))+(lava_get(387))*(0x6c6174de==(lava_get(387))||0xde74616c==(lava_get(387)))+(lava_get(276))*(0x6c61754d==(lava_get(276))||0x4d75616c==(lava_get(276))));if (((line)) && ((line)))  {int lava_271 = 0;
lava_271 |= ((unsigned char *) (line))[0] << (0*8);lava_271 |= ((unsigned char *) (line))[1] << (1*8);lava_271 |= ((unsigned char *) (line))[2] << (2*8);lava_271 |= ((unsigned char *) (line))[3] << (3*8);lava_set(271,lava_271);
int lava_1 = 0;
lava_1 |= ((unsigned char *) (line))[0] << (0*8);lava_1 |= ((unsigned char *) (line))[1] << (1*8);lava_1 |= ((unsigned char *) (line))[2] << (2*8);lava_1 |= ((unsigned char *) (line))[3] << (3*8);lava_set(1,lava_1);
int lava_5 = 0;
lava_5 |= ((unsigned char *) (line))[0] << (0*8);lava_5 |= ((unsigned char *) (line))[1] << (1*8);lava_5 |= ((unsigned char *) (line))[2] << (2*8);lava_5 |= ((unsigned char *) (line))[3] << (3*8);lava_set(5,lava_5);
}kbcieiubweuhc1540383426;});
      if (line_length <= 0)
        break;

      /* Ignore comment lines, which begin with a '#' character.  */
      if (line[0] == '#')
        continue;

      /* Remove any trailing newline.  */
      if (line[line_length - 1] == '\n')
        line[--line_length] = '\0';

      if (! (({if (((hex_digest)) && ((hex_digest)))  {int lava_387 = 0;
      lava_387 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_387 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_387 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_387 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(387,lava_387);
      int lava_305 = 0;
      lava_305 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_305 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_305 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_305 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(305,lava_305);
      int lava_555 = 0;
      lava_555 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_555 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_555 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_555 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(555,lava_555);
      int lava_571 = 0;
      lava_571 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_571 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_571 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_571 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(571,lava_571);
      }if (((line)) && ((line)))  {int lava_273 = 0;
      lava_273 |= ((unsigned char *) (line))[0] << (0*8);lava_273 |= ((unsigned char *) (line))[1] << (1*8);lava_273 |= ((unsigned char *) (line))[2] << (2*8);lava_273 |= ((unsigned char *) (line))[3] << (3*8);lava_set(273,lava_273);
      int lava_2 = 0;
      lava_2 |= ((unsigned char *) (line))[0] << (0*8);lava_2 |= ((unsigned char *) (line))[1] << (1*8);lava_2 |= ((unsigned char *) (line))[2] << (2*8);lava_2 |= ((unsigned char *) (line))[3] << (3*8);lava_set(2,lava_2);
      int lava_7 = 0;
      lava_7 |= ((unsigned char *) (line))[0] << (0*8);lava_7 |= ((unsigned char *) (line))[1] << (1*8);lava_7 |= ((unsigned char *) (line))[2] << (2*8);lava_7 |= ((unsigned char *) (line))[3] << (3*8);lava_set(7,lava_7);
      }_Bool kbcieiubweuhc304089172 = split_3 (line+(lava_get(279))*(0x6c61754a==(lava_get(279))||0x4a75616c==(lava_get(279)))+(lava_get(284))*(0x6c617545==(lava_get(284))||0x4575616c==(lava_get(284))), line_length+(lava_get(281))*(0x6c617548==(lava_get(281))||0x4875616c==(lava_get(281)))+(lava_get(287))*(0x6c617542==(lava_get(287))||0x4275616c==(lava_get(287))), &hex_digest+(lava_get(1))*(0x6c617660==(lava_get(1))||0x6076616c==(lava_get(1)))+(lava_get(289))*(0x6c617540==(lava_get(289))||0x4075616c==(lava_get(289))), &binary+(lava_get(286))*(0x6c617543==(lava_get(286))||0x4375616c==(lava_get(286))), &filename+(lava_get(2))*(0x6c61765f==(lava_get(2))||0x5f76616c==(lava_get(2))));if (((filename)) && ((filename)))  {int lava_272 = 0;
lava_272 |= ((unsigned char *) (filename))[0] << (0*8);lava_272 |= ((unsigned char *) (filename))[1] << (1*8);lava_272 |= ((unsigned char *) (filename))[2] << (2*8);lava_272 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(272,lava_272);
int lava_6 = 0;
lava_6 |= ((unsigned char *) (filename))[0] << (0*8);lava_6 |= ((unsigned char *) (filename))[1] << (1*8);lava_6 |= ((unsigned char *) (filename))[2] << (2*8);lava_6 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(6,lava_6);
}if (((hex_digest)) && ((hex_digest)))  {int lava_556 = 0;
lava_556 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_556 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_556 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_556 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(556,lava_556);
int lava_572 = 0;
lava_572 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_572 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_572 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_572 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(572,lava_572);
int lava_286 = 0;
lava_286 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_286 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_286 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_286 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(286,lava_286);
int lava_9 = 0;
lava_9 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_9 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_9 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_9 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(9,lava_9);
}if (((line)) && ((line)))  {int lava_554 = 0;
lava_554 |= ((unsigned char *) (line))[0] << (0*8);lava_554 |= ((unsigned char *) (line))[1] << (1*8);lava_554 |= ((unsigned char *) (line))[2] << (2*8);lava_554 |= ((unsigned char *) (line))[3] << (3*8);lava_set(554,lava_554);
int lava_274 = 0;
lava_274 |= ((unsigned char *) (line))[0] << (0*8);lava_274 |= ((unsigned char *) (line))[1] << (1*8);lava_274 |= ((unsigned char *) (line))[2] << (2*8);lava_274 |= ((unsigned char *) (line))[3] << (3*8);lava_set(274,lava_274);
int lava_284 = 0;
lava_284 |= ((unsigned char *) (line))[0] << (0*8);lava_284 |= ((unsigned char *) (line))[1] << (1*8);lava_284 |= ((unsigned char *) (line))[2] << (2*8);lava_284 |= ((unsigned char *) (line))[3] << (3*8);lava_set(284,lava_284);
int lava_8 = 0;
lava_8 |= ((unsigned char *) (line))[0] << (0*8);lava_8 |= ((unsigned char *) (line))[1] << (1*8);lava_8 |= ((unsigned char *) (line))[2] << (2*8);lava_8 |= ((unsigned char *) (line))[3] << (3*8);lava_set(8,lava_8);
}kbcieiubweuhc304089172;})
             && ! (is_stdin && STREQ (filename, "-"))
             && ({if (((hex_digest)) && ((hex_digest)))  {int lava_557 = 0;
             lava_557 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_557 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_557 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_557 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(557,lava_557);
             int lava_573 = 0;
             lava_573 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_573 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_573 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_573 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(573,lava_573);
             int lava_276 = 0;
             lava_276 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_276 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_276 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_276 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(276,lava_276);
             int lava_287 = 0;
             lava_287 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_287 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_287 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_287 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(287,lava_287);
             int lava_10 = 0;
             lava_10 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_10 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_10 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_10 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(10,lava_10);
             }_Bool kbcieiubweuhc1303455736 = hex_digits (hex_digest+(lava_get(301))*(0x6c617534==(lava_get(301))||0x3475616c==(lava_get(301)))+(lava_get(302))*(0x6c617533==(lava_get(302))||0x3375616c==(lava_get(302)))+(lava_get(303))*(0x6c617532==(lava_get(303))||0x3275616c==(lava_get(303)))+(lava_get(5))*(0x6c61765c==(lava_get(5))||0x5c76616c==(lava_get(5)))+(lava_get(6))*(0x6c61765b==(lava_get(6))||0x5b76616c==(lava_get(6)))+(lava_get(305))*(0x6c617530==(lava_get(305))||0x3075616c==(lava_get(305)))+(lava_get(9))*(0x6c617658==(lava_get(9))||0x5876616c==(lava_get(9)))+(lava_get(7))*(0x6c61765a==(lava_get(7))||0x5a76616c==(lava_get(7)))+(lava_get(8))*(0x6c617659==(lava_get(8))||0x5976616c==(lava_get(8)))+(lava_get(10))*(0x6c617657==(lava_get(10))||0x5776616c==(lava_get(10)))+(lava_get(306))*(0x6c61752f==(lava_get(306))||0x2f75616c==(lava_get(306)))+(lava_get(307))*(0x6c61752e==(lava_get(307))||0x2e75616c==(lava_get(307))));if (((hex_digest)) && ((hex_digest)))  {int lava_277 = 0;
lava_277 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_277 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_277 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_277 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(277,lava_277);
int lava_306 = 0;
lava_306 |= ((unsigned char *) (hex_digest))[0] << (0*8);lava_306 |= ((unsigned char *) (hex_digest))[1] << (1*8);lava_306 |= ((unsigned char *) (hex_digest))[2] << (2*8);lava_306 |= ((unsigned char *) (hex_digest))[3] << (3*8);lava_set(306,lava_306);
}kbcieiubweuhc1303455736;})))
        {
          ++n_misformatted_lines;

          if (warn)
            {
              error (0, 0,
                     _("%s: %" PRIuMAX
                       ": improperly formatted %s checksum line"),
                     checkfile_name, line_number,
                     DIGEST_TYPE_STRING);
            }

          ++n_improperly_formatted_lines;
        }
      else
        {
          static const char bin2hex[] = { '0', '1', '2', '3',
                                          '4', '5', '6', '7',
                                          '8', '9', 'a', 'b',
                                          'c', 'd', 'e', 'f' };
          bool ok;

          ++n_properly_formatted_lines;

          ok = ({if (((filename)) && ((filename)))  {int lava_289 = 0;
          lava_289 |= ((unsigned char *) (filename))[0] << (0*8);lava_289 |= ((unsigned char *) (filename))[1] << (1*8);lava_289 |= ((unsigned char *) (filename))[2] << (2*8);lava_289 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(289,lava_289);
          int lava_307 = 0;
          lava_307 |= ((unsigned char *) (filename))[0] << (0*8);lava_307 |= ((unsigned char *) (filename))[1] << (1*8);lava_307 |= ((unsigned char *) (filename))[2] << (2*8);lava_307 |= ((unsigned char *) (filename))[3] << (3*8);lava_set(307,lava_307);
          }_Bool kbcieiubweuhc521595368 = digest_file (filename+(lava_get(308))*(0x6c61752d==(lava_get(308))||0x2d75616c==(lava_get(308))), &binary, bin_buffer);kbcieiubweuhc521595368;});

          if (!ok)
            {
              ++n_open_or_read_failures;
              if (!status_only)
                {
                  printf (_("%s: FAILED open or read\n"), filename);
                }
            }
          else
            {
              size_t digest_bin_bytes = digest_hex_bytes / 2;
              size_t cnt;
              /* Compare generated binary number with text representation
                 in check file.  Ignore case of hex digits.  */
              for (cnt = 0; cnt < digest_bin_bytes; ++cnt)
                {
                  if (tolower (hex_digest[2 * cnt])
                      != bin2hex[bin_buffer[cnt] >> 4]
                      || (tolower (hex_digest[2 * cnt + 1])
                          != (bin2hex[bin_buffer[cnt] & 0xf])))
                    break;
                }
              if (cnt != digest_bin_bytes)
                ++n_mismatched_checksums;

              if (!status_only)
                {
                  if (cnt != digest_bin_bytes)
                    printf ("%s: %s\n", filename, _("FAILED"));
                  else if (!quiet)
                    printf ("%s: %s\n", filename, _("OK"));
                }
            }
        }
    }
  while (!feof (checkfile_stream) && !ferror (checkfile_stream));

  free (line);

  if (ferror (checkfile_stream))
    {
      error (0, 0, _("%s: read error"), checkfile_name);
      return false;
    }

  if (!is_stdin && fclose (checkfile_stream) != 0)
    {
      error (0, errno, "%s", checkfile_name);
      return false;
    }

  if (n_properly_formatted_lines == 0)
    {
      /* Warn if no tests are found.  */
      error (0, 0, _("%s: no properly formatted %s checksum lines found"),
             checkfile_name, DIGEST_TYPE_STRING);
    }
  else
    {
      if (!status_only)
        {
          if (n_misformatted_lines != 0)
            error (0, 0,
                   (ngettext
                    ("WARNING: %" PRIuMAX " line is improperly formatted",
                     "WARNING: %" PRIuMAX " lines are improperly formatted",
                     select_plural (n_misformatted_lines))),
                   n_misformatted_lines);

          if (n_open_or_read_failures != 0)
            error (0+(lava_get(499))*(0x6c61746e==(lava_get(499))||0x6e74616c==(lava_get(499))), 0,
                   (ngettext
                    ("WARNING: %" PRIuMAX " listed file could not be read",
                     "WARNING: %" PRIuMAX " listed files could not be read",
                     select_plural (n_open_or_read_failures))),
                   n_open_or_read_failures);

          if (n_mismatched_checksums != 0)
            error (0, 0,
                   (ngettext
                    ("WARNING: %" PRIuMAX " computed checksum did NOT match",
                     "WARNING: %" PRIuMAX " computed checksums did NOT match",
                     select_plural (n_mismatched_checksums))),
                   n_mismatched_checksums);
        }
    }

  return (n_properly_formatted_lines != 0
          && n_mismatched_checksums == 0
          && n_open_or_read_failures == 0
          && (!strict || n_improperly_formatted_lines == 0));
}

/* If ESCAPE is true, then translate each NEWLINE byte to the string, "\\n",
   and each backslash to "\\\\".  */
static void
print_filename (char const *file, bool escape)
{
  if (! escape)
    {
      fputs (file, stdout);
      return;
    }

  while (*file)
    {
      switch (*file)
        {
        case '\n':
          fputs ("\\n", stdout);
          break;

        case '\\':
          fputs ("\\\\", stdout);
          break;

        default:
          putchar (*file);
          break;
        }
      file++;
    }
}

int
main (int argc, char **argv)
{
  unsigned char bin_buffer_unaligned[DIGEST_BIN_BYTES + DIGEST_ALIGN];
  /* Make sure bin_buffer is properly aligned. */
  unsigned char *bin_buffer = ptr_align (bin_buffer_unaligned, DIGEST_ALIGN);
  bool do_check = false;
  int opt;
  bool ok = true;
  int binary = -1;
  bool prefix_tag = false;

  /* Setting values of global variables.  */
  initialize_main (&argc, &argv);
  set_program_name (argv[0]);
  setlocale (LC_ALL, "");
  bindtextdomain (PACKAGE, LOCALEDIR);
  textdomain (PACKAGE);

  atexit (close_stdout);

  /* Line buffer stdout to ensure lines are written atomically and immediately
     so that processes running in parallel do not intersperse their output.  */
  setvbuf (stdout, NULL, _IOLBF, 0);

  while ((opt = getopt_long (argc, argv, "bctw", long_options, NULL)) != -1)
    switch (opt)
      {
      case 'b':
        binary = 1;
        break;
      case 'c':
        do_check = true;
        break;
      case STATUS_OPTION:
        status_only = true;
        warn = false;
        quiet = false;
        break;
      case 't':
        binary = 0;
        break;
      case 'w':
        status_only = false;
        warn = true;
        quiet = false;
        break;
      case QUIET_OPTION:
        status_only = false;
        warn = false;
        quiet = true;
        break;
      case STRICT_OPTION:
        strict = true;
        break;
      case TAG_OPTION:
        prefix_tag = true;
        binary = 1;
        break;
      case_GETOPT_HELP_CHAR;
      case_GETOPT_VERSION_CHAR (PROGRAM_NAME, AUTHORS);
      default:
        usage (EXIT_FAILURE);
      }

  min_digest_line_length = MIN_DIGEST_LINE_LENGTH;
  digest_hex_bytes = DIGEST_HEX_BYTES;

  if (prefix_tag && !binary)
   {
     /* This could be supported in a backwards compatible way
        by prefixing the output line with a space in text mode.
        However that's invasive enough that it was agreed to
        not support this mode with --tag, as --text use cases
        are adequately supported by the default output format.  */
     error (0, 0, _("--tag does not support --text mode"));
     usage (EXIT_FAILURE);
   }

  if (prefix_tag && do_check)
    {
      error (0, 0, _("the --tag option is meaningless when "
                     "verifying checksums"));
      usage (EXIT_FAILURE);
    }

  if (0 <= binary && do_check)
    {
      error (0, 0, _("the --binary and --text options are meaningless when "
                     "verifying checksums"));
      usage (EXIT_FAILURE);
    }

  if (status_only && !do_check)
    {
      error (0, 0,
       _("the --status option is meaningful only when verifying checksums"));
      usage (EXIT_FAILURE);
    }

  if (warn && !do_check)
    {
      error (0, 0,
       _("the --warn option is meaningful only when verifying checksums"));
      usage (EXIT_FAILURE);
    }

  if (quiet && !do_check)
    {
      error (0, 0,
       _("the --quiet option is meaningful only when verifying checksums"));
      usage (EXIT_FAILURE);
    }

  if (strict & !do_check)
   {
     error (0, 0,
        _("the --strict option is meaningful only when verifying checksums"));
     usage (EXIT_FAILURE);
   }

  if (!O_BINARY && binary < 0)
    binary = 0;

  if (optind == argc)
    argv[argc++] = bad_cast ("-");

  for (; optind < argc; ++optind)
    {
      char *file = argv[optind];

      if (do_check)
        ok &= digest_check (file);
      else
        {
          int file_is_binary = binary;

          if (! digest_file (file, &file_is_binary, bin_buffer))
            ok = false;
          else
            {
              /* We don't really need to escape, and hence detect, the '\\'
                 char, and not doing so should be both forwards and backwards
                 compatible, since only escaped lines would have a '\\' char at
                 the start.  However just in case users are directly comparing
                 against old (hashed) outputs, in the presence of files
                 containing '\\' characters, we decided to not simplify the
                 output in this case.  */
              bool needs_escape = strchr (file, '\\') || strchr (file, '\n');

              if (prefix_tag)
                {
                  if (needs_escape)
                    putchar ('\\');

                  fputs (DIGEST_TYPE_STRING, stdout);
                  fputs (" (", stdout);
                  print_filename (file, needs_escape);
                  fputs (") = ", stdout);
                }

              size_t i;

              /* Output a leading backslash if the file name contains
                 a newline or backslash.  */
              if (!prefix_tag && needs_escape)
                putchar ('\\');

              for (i = 0; i < (digest_hex_bytes / 2); ++i)
                printf ("%02x", bin_buffer[i]);

              if (!prefix_tag)
                {
                  putchar (' ');

                  putchar (file_is_binary ? '*' : ' ');

                  print_filename (file, needs_escape);
                }

              putchar ('\n');
            }
        }
    }

  if (have_read_stdin && fclose (stdin) == EOF)
    error (EXIT_FAILURE, errno, _("standard input"));

  return ok ? EXIT_SUCCESS : EXIT_FAILURE;
}
