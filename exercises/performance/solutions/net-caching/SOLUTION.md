# Performance - Intermediate Exercise Solution - Caching

> **Question 2:**
> Modify the package manager to avoid synchronizing the repositories every time you run the program. 

You can find our solution in the file [ex2.diff](ex2.diff). This is a standard git diff file, that you can apply on the provided code. You can also have a look at the `PackageRepository.java` file.

Specifically, we modify the `PackageRepository.java` to:
 - Not remove the downloaded package list after stopping
 - Check if a list file already exists
 - Download the file only if it doesn't exist, or if it was last modified more than 12hrs ago.

> **Question 4:**
> What techniques could you use to automatically re-download the repositories lists if they changed?
>
> Hint: have a look at the HTTP Response Headers when you try to download the APKINDEX file (http://dl-cdn.alpinelinux.org/alpine/v3.12/main/aarch64/APKINDEX.tar.gz) - you can use `curl -I` or check your Browser's developer tools.

The response when downloading the file contains a header `Last-Modified`. This header contains the date at which the file was last changed on the server.

Using this date, we can slightly modify our download code to not download the file if it was downloaded more recently than the `Last-Modified` header.

The code to do that is very simple. You can view the diff in [ex4.diff](ex4.diff) or in the [Networking.java](src/main/java/ch/epfl/sweng/javapk/util/Networking.java) file.

This solution still requires making network calls to check the last-modified header. This is way faster than downloading the whole file, but can still slow down the program. Therefore, you can use the solution to question 2 as a complement to this one, perhaps with a lower cache expiration duration (for example 1 hour).
