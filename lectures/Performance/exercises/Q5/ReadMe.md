# Performance - Caching

We provide you with a toy package-manager (written in Java) for Alpine Linux, a lightweight Linux distribution popular
on docker containers.

This package manager offers three simple operations:

- Search packages in the archive (`search <query>`)
- Download a package from the archive (`install <package>`, even though we won't actually extract the package in this
  example)
- Show some information about a package (`info <package>`)

The program can be ran using the following command `gradle run --args='<operation> <arguments>'`. An interactive mode is
provided when you start the program without argument, you will be then asked to enter a command.

Here is an example of using the program:

``` bash
JavAPK: the Java APK Package Manager
To install a package, use `javapk install <package name>`
To search for a package, use `javapk search <package name>`
To print information about a package, use `javapk info <package name>`
Please input your command: search supertuxkart

Synchronizing packages in repository main: [=====================] 100 % (620KiB/620KiB)
Synchronizing packages in repository community: [=====================] 100 % (1MiB/1MiB)
Packages found for query supertuxkart: 
[community] supertuxkart-data 1.1-r2: Kart racing game featuring Tux and his friends (data files)
[community] supertuxkart 1.1-r2: Kart racing game featuring Tux and his friends
[community] supertuxkart-static 1.1-r2: Kart racing game featuring Tux and his friends (static library)
```

You will notice that this program uses the real Alpine Linux repositories, and not a stub.

If you try to use the program to search for some packages and download some of them, you will notice that _every time_
you run the program, it needs to synchronize the repositories from the main server.
Depending on your network quality, this operation can take multiple seconds, which is particularly annoying.

Thankfully, we know that the list of packages of the repository doesn't change very often. We can therefore safely
assume that a package list is still valid after a few hours. Sure, one or two packages may have been updated, but in
general most of the packages don't change every hour.

Modify the package manager to avoid synchronizing the repositories every time you run the program.
