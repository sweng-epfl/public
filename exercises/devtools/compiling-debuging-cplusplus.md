# Exercise 5: Compiling and Debugging C++

In this exercise, we will compile a C++ program and debug it with _gdb_. We will use only tools available from the terminal.

## Exercise 5.1: Use g++ to compile C++ code.

The difference between g++ and gcc for compilation of C++ programs is minimal but exists ([link to a StackOverflow discussion](https://stackoverflow.com/questions/172587/what-is-the-difference-between-g-and-gcc)). We will use g++.

### **Step 0** Install g++

**Windows** For Windows, there are multiple options: you can use the Windows Sybsystem for Linux (WSL), MinGW.
- for MinGW (recommended): Follow the instructions from [here](https://nuwen.net/mingw.html). Then, install gdb (find the command yourself)
- For WSL: Follow the instructions from [here](https://docs.microsoft.com/en-us/windows/wsl/install-win10) and install [Ubuntu 18.04 LTS](https://www.microsoft.com/fr-ch/p/ubuntu-1804-lts/9n9tngvndl3q?rtc=1&activetab=pivot:overviewtab)

**Mac/Linux**

Install g++ and gdb with the standard package manager of your installation.


### **Step 1** Compile _hello.cpp_

1. In the current folder create the file _hello.cpp_.

```cpp 
#include <iostream>

int main()
{
    std::cout << "Hello, World!\n";
}
```

2. Compile the program.

```sh
g++ -o hello hello.cpp
```

The -o option of the command tells the g++ compiler to create an executable called _hello_

3. Run the program


```sh
### On Mac/Linux
./hello 

### On Windows
hello
```

## Exercise 5.1: Debuging with _gdb_

Install gdb on your machine.

### **Step 1** Debug _hello.cpp_

1. Debug before compiling with debug information

```sh
gdb ./hello
```

You should get a message like this:

```
(No debugging symbols found in ./hello)
```

1. Build _hello.cpp_ for debugging.
   - Find the `g++` flag the you need to use in order to build the _hello_ executable with debugging symbols from the source file _hello.cpp_. Hint: Search the web for information, use keywords from the instruction provided.

2. Start debugging

```sh
gdb hello
### Reading symbols from ./hello...
### Reading symbols from <path>/sweng2019/exercises/devtools/ex5/hello.dSYM/Contents/Resources/DWARF/hello...
```

Your terminal now is prefixed with (gdb), you're in the gdb environment.

- run the executable once

```gdb
(gdb) run
```

You will see some explanations and also the message _Hello, World!_ printed on your terminal.

3. Insert a break point at the main method.
   - Find how to insert a breakpoint at methods and at specific source lines.

   - Find how to continue to the next statement

4. Find out how to exit gdb


### **Step 2** Look at variables

Modify the _hello.cpp_ program as follows:


```cpp 
#include <iostream>

int main()
{
    int exNum = 5;
    std::cout << "Hello exercise " << exNum <<"!\n";
}
```

Set a breakpoint at the main method. before running the program. Run the program. Print the value of _exNum_, then continue to the next statement (line 6) and print the value again. The result should be:

```sh
$2 = 5
```
Exit gdb.


### **Step 3** Debug a program with arguments.


Change the _hello.cpp_ progam as follows.
```cpp
#include <iostream>

int main(int argc, char *argv[])
{
    std::cout << "You passed " << argc - 1 << " arguments in addition to the program name.\n";
 
    for (int count=0; count < argc; ++count) {
        std::cout << count << " " << argv[count] << '\n';
    }
}
```
Compile and start gdb the normal way. Run without any additional arguments.

1. Find 2 different ways to pass 3 parameters. The output for both should be:

```sh
0 <PATH>/sweng2019/exercises/devtools/ex5/hello
1 1
2 2
3 3
```




