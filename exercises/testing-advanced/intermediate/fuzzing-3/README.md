# Exercise 3

LLVM-AFL allows you to optimize the binary through a compiler-based toolchain.
Instead of inserting the AFL instrumentation late in the compilation (as `afl-gcc`
does) or at runtime (as QEMU mode does), LLVM-AFL inserts the instrumentation
early during compilation. Additionally, the programmer can leverage a fork
server that initialized the binary up to a certain point and starts fuzzing only
from that point in time.

Build the LAVA-M benchmarks `base64` and `md5sum` using LLVM-AFL. Look at the
source files and consider where to insert the AFL fork server and how to speed
up fuzzing.

Repeat the same fuzzing campaign from Exercise 1 with the LLVM-AFL binaries.

* What is the performance difference between running the LLVM instrumentation
  and the GCC instrumentation?

* What is the source of this speedup?

* What else would you do to speed up fuzzing execution? (This is an open
  question)
