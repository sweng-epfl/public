# DP - UI -- Ex6
People use your application to read and write to files (look at the `App` class). 
You notice that users are able to access highly confidential files. 

Use the **proxy design pattern** to restrict access to all the files that contain the word "sensitive" in their filename.
Do so by creating a `ProxyFile` class. Then, execute the `main` method from the `App` class and see what happens (```gradle run```).