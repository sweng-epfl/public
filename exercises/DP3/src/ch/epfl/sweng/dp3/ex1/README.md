## Exercise 1

Say that you have a `File` class for handling files. You can get the hash of a file by calling `getHash`; the `getHash` method looks as follows:

```Java
    public String getSimpleHash() {
        // a naive hash function
        int hash = 0;
        for (char c : data.toCharArray()) {
            hash += ((int) c) * hash + 19;
        }

        return String.valueOf(hash);
    }
```

You decide that you would like to support some more advanced hash functions. Your approach in solving this problem is changing the `getHash` method and taking a  `hashFunction` as a parameter. You also include the SHA-256 hashing algorithm (check the changes in the `File` class).

Do you see any problems with this approach? What if you would like to introduce more hashing algorithms in the future?

Use the **strategy pattern** to solve these problems. Implement whatever it takes to pass the `FileTest`.


