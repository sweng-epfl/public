# Solution - C String Buffer Overlows

The issue with `strncpy` is that it does *not* guarantee that the destination string is null-terminated. In code-contract speak, we'd say that this function does not guarantee it's post-condition in some cases. Code which uses the output of `strncpy` could then chain misbehaviours :-\

You can find more detailed explanations at the following links:

* [How can code that tries to prevent a buffer overflow end up causing one](https://devblogs.microsoft.com/oldnewthing/?p=36773)
* [Stop using strncpy already!](https://randomascii.wordpress.com/2013/04/03/stop-using-strncpy-already/).
