Advanced testing infrastructure
===============================

_This is an advanced topic._

You now know how to write tests, including tests for classes with dependencies on the outside world such as the Internet. But there is still a gap here: how do you test the "real" implementation of a dependency, such as an HTTP client? After all, an HTTP client cannot depend on something else, it has to implement the HTTP logic directly... (Even if you made it depend on a sockets framework to send data, eventually there would be some piece of code or hardware that has to talk to the network without depending on something else)

The solution is to implement the "other side" of the communications yourself. For instance, when testing an HTTP client, run a local HTTP server that you control. Or when testing a location service, add a fake "geo-locator" device or plug-in to the operating system that you control.

**Exercise**: Write tests for Java's [`HttpURLConnection`](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html). To do so, run a local HTTP server during your tests using [`NanoHttpd`](https://github.com/NanoHttpd/nanohttpd).
