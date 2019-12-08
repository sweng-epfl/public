# Software Engineering - Mock Midterm

## Part 2: Practice - Operating System Shell [60 points]

In this part of the exam you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles.
You should write code that is:
- Correct and robust
- Readable and concise
- Judiciously commented

You should use the same development environment as for the [bootcamp](https://github.com/sweng-epfl/public/blob/master/bootcamp/Readme.md).
To validate your installation, you can do a `java -version` from the command line. You are ready to go as soon as the command outputs something like this:

	java version "12.0.2"
	Java(TM) SE Runtime Environment (build 12)
	Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)

The version number need not be exactly identical to the one above, but it must start with `12`.

To run the tests and generate a coverage report, run `gradlew build` on Windows or `./gradlew build` on macOS/Linux in this folder.
The coverage report is in `build/reports/coverage/index.html`.

You are free to use an editor or IDE of your choice, as long as the code and tests build using the gradlew command mentioned above,
since we will use this command to build your code.

Please remember that we will only grade whatever is in your `master` branch. 

We provide you with continuous integration using [Travis](https://travis-ci.com/), which automatically retrieves and builds the code from your `master` branch.
You may need to synchronize your GitHub account if you don't see your repository on Travis.
This allows you to make sure your code works on other computers than your own.


## Introduction

`SwengShell` is a small operating system shell that implements the `Shell` interface shown below:

```java
public interface Shell {
    String       printWorkingDirectory();
    void         changeDirectory(String dirname)                        throws NoSuchPathException;
    void         createDirectory(String dirname)                        throws DirectoryAlreadyExistsException, DirectoryNotWriteableException;
    void         createFile(String filename, int sizeInBytes)           throws FileAlreadyExistsException, DirectoryNotWriteableException;
    List<String> listWorkingDirectory()                                 throws DirectoryNotReadableException;
    List<String> listWorkingDirectory(boolean showHidden)               throws DirectoryNotReadableException;
    int          sizeInBytes(String fileOrDirName)                      throws NoSuchFileException;
    void         remove(String fileOrDirName)                           throws NoSuchFileException, DirectoryNotWriteableException, FileNotWriteableException, DirectoryNotEmptyException;
    String       getPermissions(String fileOrDirName)                   throws NoSuchFileException;
    void         setPermissions(String fileOrDirName, Permissions perm) throws NoSuchFileException;
}
```

The directory hierarchy works as follows:

- `"/"` is the root directory
- Files and directories all have read/write `Permissions`. These can be one of the following: `"rw"`, `"r-"`, `"-w"`, `"--"`.
  `r` means the file/directory can be read, `w` that it can be written; a `-` means the absence of a permission.
- Permissions of the root directory *cannot* be changed.
- Listing the contents of a directory can only be done if it has read permissions (the permissions begin with `r`).
- Creating a file/directory can only be done if the directory where it is being created has write permissions (the permissions end with `w`).
- Hidden files/directories have names that start with `"."` (e.g., `.bashrc`) and are not shown when listing directory contents unless explicitly requested.
- Going up the filesystem hierarchy can be done using `".."`, but calling `".."` in the root directory will loop back to the root directory.
  Note that directories *do not* have a self-referencing `"."` field as in some operating systems.
- The size of a directory is the sum of the sizes of all its children.
- Changing directories can be done using relative *or* absolute paths.
- Creating files/directories can *only* be done in the *current working directory* (this means absolute paths are not permitted in calls to `createDirectory` and `createFile`, and relative paths have a depth of 1).

We stress the last peculiarity listed above again: The authors of `SwengShell` decided to keep its logic simple,
so adding/removing elements (files/directories) can only be done by changing the current working directory to the intended place before calling the intended operation.
We show an example of this behavior next. For illustration purposes, imagine a user wants to create the following file hierarchy:

```
/
└── dir_1/
    └── dir_2/
        └── file_1.txt
```

In `SwengShell`, this file hierarchy can be created using the following sequence
of API calls:

```java
changeDirectory("/");
createDirectory("dir_1");
changeDirectory("dir_1"); // or changeDirectory("/dir_1");
createDirectory("dir_2");
changeDirectory("dir_2"); // or changeDirectory("/dir_1/dir_2);
createFile("file_1.txt");
```

Notice that the user must always change the current working directory to the intended location before manipulating or listing files/directories.
The only method in `SwengShell` that can understand compound paths is `changeDirectory`.


## Problem

Users of this shell have reported some bugs which they encountered while using the system.
As the engineer responsible for this project, your first task is to *reproduce the bugs* so they can be later fixed.

The bugs reported by users are listed below:
- `Bug 1:` Files/directories that start with `"."` are not hidden when listed.
- `Bug 2:` Deleting a *non-empty* directory should throw a `DirectoryNotEmptyException`, but currently the directory is deleted silently and users keep losing files.
- `Bug 3:` File and directory sizes are *always* smaller than their actual size.
- `Bug 4:` It is possible to create a duplicate file, but this should not be permitted.
  If a duplicate file is going to be created, the system should throw a `FileAlreadyExistsException`.
  This bug does not seem to occur for directories.
- `Bug 5:` It is possible to create a directory in a *non-writeable* directory, but this is incorrect behavior.
  Directories can only be created in writeable directories, otherwise a `DirectoryNotWriteableException` should be thrown.
  This bug does not seem to occur for files.
- `Bug 6:` It is possible to list the files/directories in a *non-readable* current working directory, but it should throw a `DirectoryNotReadableException` instead.

## Task 1: Reproduce the bugs [30 points]

Users reported 6 bugs in `SwengShell`. Your task is to write 6 tests (1 test for each bug) that showcase the problematic behavior.
Your tests should be written in such a way that
- The tests *fail* if the bug *exists*
- The tests *pass* if the bug *does not exist*

Write your tests in the [`AppTest.java`](src/test/java/AppTest.java) file using the `Shell` interface defined in [`Shell.java`](src/main/java/Shell.java).

We provide you with a sample test that checks if files can be created with negative file sizes as an example in [`testAddFileWithNegativeSize`](src/test/java/AppTest.java#L51).

**Grading**: 5 points per bug that your tests cover.


### Note

Due to the simple nature of our shell's API, it is quite tedious to create interesting file hierarchies for testing purposes.
The test class we provide helps you with respect to this by using `JUnit`'s `@BeforeEach` annotation to setup the following file hierarchy for you so you already have a sample directory structure to work with:

```
/
├── d_a/                    [rw]
│   ├── d_c/                [rw]
│   │   ├── f_g.txt         [rw]  8 byte
│   │   └── f_h.txt         [rw] 12 byte
│   ├── f_c.txt             [rw] 10 byte
│   └── f_d.txt             [rw]  4 byte
├── d_b/                    [rw]
│   ├── d_d/                [rw]
│   │   ├── d_e/            [rw]
│   │   ├── .f_hidden_1.txt [rw]  1 byte
│   │   ├── .f_hidden_2.txt [rw]  2 byte
│   │   ├── f_i.txt         [rw]  9 byte
│   │   └── f_j.txt         [rw] 32 byte
│   ├── f_e.txt             [rw]  1 byte
│   └── f_f.txt             [rw]  3 byte
├── f_a.txt                 [rw] 10 byte
└── f_b.txt                 [rw] 13 byte
```


## Task 2: Fix the bugs [30 points]

Fix each of the 6 bugs that you reproduced in the previous section. 

**Grading**: 5 points per bug fix.
