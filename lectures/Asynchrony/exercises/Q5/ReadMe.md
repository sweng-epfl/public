# Testing

In this exercise, you will get your hands on a difficult part of asynchrony: testing. As you will see, it becomes harder
to test code once you do not know when a call will be answered.

The goal of this exercise is to test both `DistantCredentialDatabase`:

- `CBDistantCredentialDatabase` that answer to query asynchronously using the `CredentialDatabaseCallback` and the
  methods `onSuccess(user)` or `onError(exception)`.
- `CFDistantCredentialDatabase` that returns a `CompletableFuture<AuthenticatedUser>`

You can test the following scenarios:

1. Trying to authenticate a user that does not exist will return an `UnknownUserException`
2. Adding a user on an empty database will add the correct user
3. Adding the same user twice will return an `AlreadyExistsUserException`
4. Adding a user to the database and trying to authenticate the same user will yield the correct user
5. Adding a user to the database and trying to authenticate this user with a wrong password will return
   an `InvalidCredentialException`
