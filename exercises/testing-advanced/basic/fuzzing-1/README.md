# Introduction

The goal of this fuzzing part of the exercise set is to get familiar with fuzzing. You will set up AFL
on your machine and experiment with different modes and forms of fuzzing. In a
total of three tasks (one optional) you will play with a fuzzing engine using
different forms of coverage tracking: through `afl-gcc`, `qemu`, and by using
LLVM-based instrumentation.

# Exercise 1

In this first task, we will fuzz two binaries with AFL to find bugs: `base64`
and `md5sum`, both part of the coreutils.  Luckily, `base64-afl` and
`md5sum-afl` have already been instrumented and compiled with `afl-gcc`. `afl-gcc`
is one of three ways to instrument binaries with an afl coverage tracking. 

Here is a short description of these two programs.

## `base64`

The `base64` program allows to encode or decode some bytes in base64 encoding. This encoding represents bytes using 64 characters that are available on most systems. It is very useful in text protocols such as HTTP or emails, for example.

`base64` works on files, and writes its output to stdout. You can encode a file by running `base64 <file path>`, or decode a file by running `base64 -d <file path>`. 

### Your turn

Your first task is to run AFL on the **decoding** part of the `base64` program. Look at the `QuickStartGuide.txt` included with the AFL sources to get started. You'll see that we provided some inputs for you in the `inputs/` directory. You can use them for now.

As you might have guessed, the input we provided (`fuzz`) is not very interesting, and AFL needs a lot of time to find some interesting inputs that can lead to actual bugs. Your next task is therefore to find some "reasonable" inputs that can "kickstart" AFL into finding interesting paths. You can add them into new files in the `inputs/` directory.

## `md5sum`

The `md5sum` program computes the md5 checksum of a file. The checksum is a hash that can be used to ensure that, for example, a file has not been corrupted during a download. It is commonly found alongside huge files, such as operating systems ISOs.

Conveniently, `md5sum` can read MD5 sums from a file and check them. It means that if you provide the following file `README.md.md5` containing `52b14d7295c7797c540cd02938ab43f0 README.md`, then running `md5sum -c README.md.md5` will check that there is a file `README.md` in the current directory and that this file's MD5 checksum is `52b14d7295c7797c540cd02938ab43f0`. You'll note that the content of this `.md5` file is precisely the output of running `md5sum README.md`. This makes the generation of verification files very easy.

### Your turn

Your task is to run AFL on the **checking** part of the `md5sum` program. We again provided a basic input in the `inputs/` directory. First, please try to run AFL with this input.

Again, the input is not very interesting and AFL doesn't find a lot of bugs very early. Try to find some "reasonable" inputs and add them in the `inputs/` directory. Now run AFL again and confirm that it indeed finds bugs more quickly.
