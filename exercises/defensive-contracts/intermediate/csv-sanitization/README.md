# Garbage In ⇏ Garbage Out principle applied to CSV sanitization
This exercise will teach you techniques to protect against invalid inputs from external sources. To this end, you will be given a CSV file. This file is malformed, i.e. lines do not enforce a homogenous format for their fields. Therefore, you will need to parse the CSV file, ignore invalid lines, and produce a valid version of the file.

This exercise emphasizes the importance of dealing with garbage input. For real-world, production software, "garbage in, garbage out" is not acceptable. A good program never outputs garbage, regardless of what it recevies as input.

There are various ways to handle "garbage in." There are also many sources of garbage (e.g., external files).

For this exercise, you have a malformed comma-separated values (CSV) file ([access_log.csv](src/access_log.csv)). The fields do not necessarily have a homogenous format. You need to write a program that reads in the CSV file and produces a cleaned version of it.

### Requirements
The CSV fields are listed below. Each field has a format requirement.

- **datetime**: a `yyyy-mm-dd hh:mm:ss` datetime
    - `2018-09-05 11:15:00` is a valid datetime
    - `2018-9-5 11:15` is not a valid datetime
- **IP address**: a valid IPv4 address
    - `255.255.255.0` is a valid IPv4 address
    - `256.367.478.589` is not a valid IPv4 address
- **user-agent**: a regular string without any comma
    - `Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0` is a valid user-agent
    - `Mozilla/5.0 (Windows NT 6.3; rv:36.0), Gecko/20100101 Firefox/36.0` is not a valid user-agent
- **url**: a url of the form `protocol://domain/some/path` where protocol is either *http* or *https*
    - `https://ic.epfl.ch/en` is a valid url
    - `ftp://epfl.ch` is not a valid url according to the definition for this CSV file

You are free to deal with cases outside the above definitions in any way you want.

### Command-line interface
Your program should support the following command-line options:

- `-i <input>`: the input file
- `-o <output>`: the output file
- `-v`: a verbose flag

You can enforce any order for the command-line options to keep it simple.

### Tasks
You are given a Java project you need to complete, in order to:

1. handle the command-line options
1. parse the CSV input file
1. enforce the format requirements
1. ignore invalid lines, i.e.,
    - lines with missing values
    - lines with values of incorrect format
1. log ignored lines along with the reason to the standard output if the verbose flag is set
1. write a cleaned version of the CSV input file.

### Hints
You might find [regular expressions](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html) useful for validating the format of values in the file.

As a starting point, the regular expression for the **user-agent** field would be `^.+$`. This regular expression matches any string that contains at least one character.
