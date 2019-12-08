# DP - UI -- Ex10 - Solutions

The design pattern used in the [Java Remote Method Invocation (RMI)](https://docs.oracle.com/javase/tutorial/rmi/index.html) system is the **Proxy** design pattern.
The goal of Java RMI was to allow the developers to develop distributed applications with the same syntas as non-distributed programs.
It is achieved by defining a proxy class running on the client. The client makes requests on the proxy object (by calling methods on the client object) which will then request the actual server.