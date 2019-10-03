# Solution - Regular Expression-based Denial-of-Service

#### How would you protect you application from ReDoS attacks if you allow the user to provide their own regexes?

If users can provide their own regex, then the only "true" defense you have it to prevent runaway regexes. This can be done by using a text-directed regex engine (i.e. a regex engine that does not backtrack) as the performance of these engines depends on the length of the input string instead of the complexity of the regular expression.

If you need to use a backtracking-based regex engine (if you need to support backreferences for example), then you'll need to mitigate the consequences of catastrophic backtracking. Mitigation can take several forms, but perhaps the most sensible one would be to use a regex engine which aborts when catastrophic backtracking is detected rather than running until the program fails.

#### How would you protect you application from ReDoS attacks if your server only contains hard-coded regexes?

If the regexes are hard-coded by yourself, then there's a lot more headroom to protect the system. The main idea is to make sure the regexes won't exhibit catastrophic backtracking regardless of the inputs they're used on. This can be done by looking at the regex independently of the data it will operate on and determining that it is not possible for multiple permutations of the same regex to match the same thing.

* Make sure your regexes are as strict as possible. The stricter the regex, the less backtracking is possible.
* If you want to match a list of words, prefer using atomic groups such as `\b(?>one|two|three)\b`.
* Use negated character classes instead of the wildcard `.` which matches "anything". It is rare that you really want to match "anything". Even though a double-quoted string could be represented by `".*?"`, it is much more preferable to represent it as `"[^"\n]*+"` since this pattern can't contain unescaped double quotes (which is most probably what the programmer intended).
* ... and many others

[This](https://www.regular-expressions.info/redos.html ) great online resource goes over various techniques that can help mitigate ReDoS attacks.
