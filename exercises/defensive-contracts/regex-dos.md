# Regular Expression-based Denial-of-Service

This exercise assumes you are comfortable reading and writing regular expressions. If this is not the case, we recommend you follow this [tutorial](https://regexone.com/) to get acquainted with the basics before you tackle this exercise.

Regular expressions (a.k.a. regexes) are powerful tools for a variety of text search problems and are commonly used by programs for input validation. As with any powerful tool, one needs to have a solid high-level understanding of how they function internally in order to use them correctly.

> Regular expression matching can be simple and fast, using finite automata-based techniques that have been known for decades. In contrast, Perl, PCRE, Python, Ruby, Java, and many other languages have regular expression implementations based on recursive backtracking that are simple but can be excruciatingly slow. With the exception of backreferences, the features provided by the slow backtracking implementations can be provided by the automata-based implementations at dramatically faster, more consistent speeds. [source](https://stackoverflow.com/questions/8887724/why-can-regular-expressions-have-an-exponential-running-time_)

The goal of this exercise is for you to discover how modern recusive backtracking-based regex engines can occasionally take exponential runtime to match a pattern. Such behavior is known as *catastrophic backtracking*, or *regular expression denial of service (ReDoS)*.

You should read the following article on [runaway regular expressions](https://www.regular-expressions.info/redos.html) to understand where the exponential runtime can come from.

Notice that though knowledge of exponential regex runtimes is known, it is not uncommon to find such regexes online (even in repuable resources such as the Open Web Application Security Project, OWASP). In particular, OWASP has a [validation regex repository](https://www.owasp.org/index.php/OWASP_Validation_Regex_Repository) which can be used to validate whether URLs, IP addresses, emails, ... have valid formats. However, note that some examples on that page are vulnerable to the catastrophic backtracking error we just discussed (check the 2 variants of the validation regex for `Java Classname`).

This exercise set is about defensive programming, so let's consider a few scenarios. How would you protect your application from ReDoS attacks if:

* you allow the user to provide their own regexes?
* your server only contains hard-coded regexes?

Think of a few best practices for writing regexes to minimize the chance they'll backtrack excessively and write them down.
