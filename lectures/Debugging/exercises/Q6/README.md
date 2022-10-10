# Sanitizing CSVs to Avoid 'Garbage In, Garbage Out'
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
#### Regex Expressions
To help you enforce some format requirements we provide you the following _regex_ expressions:

- `^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$`
  - `^` and `$` matches the beginning and end of the string respectively
  - `\d{X}` matches any digit characters (0-9) of length X
- `^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$`
  -  `?` matches between 0 and 1 of the preceding token, in this case `http` or `https` is accepted.

To learn more about regex expressions checkout this [tutorial article](https://medium.com/factory-mind/regex-tutorial-a-simple-cheatsheet-by-examples-649dc1c3f285).
### Tasks
You are given a Java project you need to complete, in order to:

1. parse the CSV input file
2. enforce the format requirements (checkout the [java.util.regex](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/package-summary.html) package)
3. ignore invalid lines, i.e.,
    - lines with missing values
    - lines with values of incorrect format
4. log ignored lines along with the reason to the standard output if the verbose flag is set
5. write a cleaned version of the CSV input file.

### Testing
For the purpose of manually testing if your implementation indeed outputs the CSV file correctly sanitized as described above, we provided the [expected csv file](src/expected_output.csv).