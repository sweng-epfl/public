```sh
curl 'https://api.github.com/search/repositories?q=stars:>100000'
```

For all 9 repos execute, where the LOGIN is the login value in the owner property of each of the responses.
```sh
curl https://api.github.com/users/LOGIN
```

Get all "locations" from the JSON that the user API call returns.

Answers: ['Just here on Earth... for now', None, 'All Over the World', 'Menlo Park, California', 'San Francisco', None, 'virtual', None, 'Austin, TX']
