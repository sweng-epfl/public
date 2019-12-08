# Advanced testing

The exercises are designed such that, if you can correctly solve the Basic exercises every week, then you should be able to get a passing grade on the exams. If you additionally solve correctly the Intermediate exercises, then you should be able to get a very good grade on the exams. The Advanced exercises are for enthusiasts who want go beyond the standard material and get a black belt in Software Engineering. We will be happy to try and provide personalized feedback on your solutions to Advanced exercises if you wish, just send us the info via [this form](https://docs.google.com/forms/d/e/1FAIpQLSem_4qm_rf22V5--EUrh252_JKcBqoHF1Z67exwPz3tPdOjiQ/viewform).

Note that to be able to do Ex 1, 2 and 3, you need to first do the Ex 0 to setup the environment. We recommend you to use the VDI VMs provided by EPFL at [vdi.epfl.ch](https://vdi.epfl.ch/) (you need to be on the EPFL network or VPN to access them).

You'll be required to run some processes for a couple of hours. Most laptops and Desktops will work fine but as it could harm your SSD (premature aging), we highly recommend to use the VM. If you really want to do the work locally on your own computer, you may consider running your fuzzer in a RAM disk: http://www.cipherdyne.org/blog/2014/12/ram-disks-and-saving-your-ssd-from-afl-fuzzing.html.

This exercise set is entirely derived from two labs given in [Prof. Mathias Payer's CS-725 course](https://nebelwelt.net/teaching/18-725-LSS).

## Exercises

- Basics
  - [Ex 0 : Set-up your fuzzing environment](basic/fuzzing-0/README.md)
  - [Ex 1 : Fuzzing an instrumented binary](basic/fuzzing-1/README.md)
  - [Ex 2 : Fuzzing an uninstrumented binary](basic/fuzzing-2/README.md)

- Intermediate
  - [Ex 3 : Instrumenting a binary](intermediate/fuzzing-3/README.md)
  - [Ex 4 : Detecting memory erros with LLVM's `AddressSanitizer`](intermediate/sanitization-1/README.md)

- Advanced
  - [Ex 5 : Detecting uninitialized memory reads with LLVM's `MemorySanitizer`](advanced/sanitization-2/README.md)
  - [EX 6 : Detecting undefined behavior with LLVM's `UndefinedBehaviorSanitizer`](advanced/sanitization-3/README.md)
