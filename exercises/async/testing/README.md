## Exercise 3 (Testing)

In this exercise, you will get your hands on a difficult part of asynchrony: testing. As you will see, it becomes harder to test code once you do not know when a call will be answered.

The goal of this exercise is to test both `DistantCredentialDatabase`:
- `THDDistantCredentialDatabase` that answer to query asynchronously using the `CredentialDatabaseCallback` and the methods `onSuccess(user)` or `onError(exception)`. The database itself has two methods:

```java
    /**
     * Add a user to the database
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param sciper       The user's sciper
     * @param callback     The callback
     */
    void addUser(String userName, String userPassword, int sciper, CredentialDatabaseCallback callback);

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param callback     The callback
     */
    void authenticate(String userName, String userPassword, CredentialDatabaseCallback callback);
```

- `CFDistantCredentialDatabase` that returns a `CompletableFuture<AuthenticatedUser>`

You can test the following scenarios:

> :information_source: As these scenarios are similar try to do some for each database until you have grasped the concept

1. Trying to authenticate a user that does not exist will return an `UnknownUserException`
2. Adding a user on an empty database will add the correct user
3. Adding the same user twice will return an `AlreadyExistsUserException`
4. Adding a user to the database and trying to authenticate the same user will yield the correct user
5. Adding a user to the database and trying to authenticate this user with a wrong password will return an `InvalidCredentialException`
