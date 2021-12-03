# Exercise 2 (Callbacks)

In this exercise, you are going to revisit the Sangria authentication system that you built in exam 2. The goal is to modify the implementation
in order to prepare it for asynchronous operations.

Refactor the `CredentialDatabase` interface so that the `authenticate` method returns the result in a callback rather than as the method's return
value. You can declare the callback interface in `model/CredentialDatabase.java`. Once you have figured out the methods that your callback needs, 
you need to refactor the two database implementations `SangriaDatabase` and `SecureSangriaDatabase`.

> :information_source: Hint: the callback needs to handle both authentication success and failure.

Once you have modified your model classes, you need to modify the `SangriaPresenter` in order to support the new interface.

## Additional questions

Did this refactor alone makes the application asynchronous ? If not, what changes would need to be made in order for database queries to be fully 
asynchronous ?

> ???
