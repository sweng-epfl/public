# Exercise 0

This exercise is in fact the set-up of the fuzzing environment.


## Background: AFL and LAVA-M

**AFL** is a coverage-guided greybox fuzzer. AFL does not require an input
specification to generate new fuzzing inputs but leverages coverage as an
indication of input quality. Based on a set of inputs, AFL randomly mutates the
input. Whenever new (or different) coverage is reached, AFL adds the input to
the set of fuzzing inputs. To collect coverage information, AFL uses a simple
instrumentation to track edges hashed into a coverage map. You'll find AFL at
<http://lcamtuf.coredump.cx/afl/>.

**LAVA-M** is a set of benchmarks taken from coreutils with injected synthetic bugs.
The advantage of LAVA-M is that any input that triggers an injected bug
terminates the application with a message that the bug was triggered. This makes
triaging very simple. For this first lab, we'll be using LAVA-M to test
different fuzzers and compare them against each other. The LAVA-M benchmarks can
be found at <http://panda.moyix.net/~moyix/lava_corpus.tar.xz> with the paper at
<https://seclab.ccs.neu.edu/static/publications/sp2016lava.pdf>.


For your convenience, just execute the `getfiles.sh` script to download both AFL
and LAVA-M. If you chose not to do exercise 1 of intermediate set, you don't need the LAVA-M files.

If you do not have the `wget` command on your system, install it using your favorite package manager.


## Set-up your fuzzing environment

The first step is to set up the necessary build environment. You'll need at
least AFL installed. For exercise 1 of intermediate set, you'll also need to build your own
LAVA-M binaries.

(Note: the two tarballs mentioned below are the two that are automatically downloaded by the `getfiles.sh` script)

* **Download the lab files.** You'll need to download and install the lab files
  with the sample binaries. The lab has been tested on Debian and Ubuntu but
  should equally work on MacOS. If you're struggling with the environment, it
  may be easiest to use the VDI machines at EPFL [vdi.epfl.ch](https://vdi.epfl.ch/).

* **Install AFL.** Download the AFL tarball. The link is at the bottom of the AFL
  page. Read the introduction (at least `QuickStartGuide.txt` to install it).
  You'll need a basic build environment to get AFL up and running. Follow the
  build instructions for AFL (aka `make`).
  For Exercise 2, you will need to enable QEMU support. Make sure to build qemu
  support by executing `buikd_qemu_support.sh` in `qemu_mode`.

* **Download LAVA-M.** For exercise 1 of intermediate set, Download the LAVA-M tarball. The
  link is on the LAVA-M site. You may need a bunch of additional packages to get
  LAVA-M to run, e.g., libacl1-dev, libselinux1-dev, libcap-dev, and libgmp-dev
  on Debian or Ubuntu.
  
  You can install missing packages using your favorite package manager (`homebrew` on macOS, `apt` on Debian/Ubuntu, `pacman` on Arch, ...)
