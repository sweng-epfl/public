# Working with APIs

## curl GitHub

Curling is not not only a [sport](https://en.wikipedia.org/wiki/Curling) but also a command line tool you can use to access resources via various protocols [cURL](https://en.wikipedia.org/wiki/CURL).
We will focus on HTTPS and how to query a developer API, namely, the GitHub API. 
An API is a programmatic way to access resources and is the predominant way of using services in software applications. 

### GitHub access token

Go to the settings of your GitHub profile and create a new _Personal access token_ that you will use to access the GitHub API.

For an _advanced_ advanced experience, you can try the other ways for accessing the GitHub API on your own.

Once you have your personal token, open a terminal window and type **(Substitute OAUTH-TOKEN with your token)**:  

```sh
curl -H "Authorization: token OAUTH-TOKEN" https://api.github.com
```

### Find repos, users, and locations

Go to the GitHub API documentation and find a way to get

1. The all repositories with more that 100'000 stars (At least 2 ways to do accomplish this. Pointers: search and repositories APIs)
   - from the repositories, take the information for the owner and search for the location of the users in their user profile (Pointer: user API)

**There are 9 repos with more than 100'000 stars.** You can verify with the search on the [GitHub website](https://github.com/search?utf8=✓&q=stars%3A%3E100000&type=Repositories&ref=advsearch&l=&l=).


## Scraping GitHub with Python

The terminal is a useful tool when you need something quick but there are other ways to access different APIs. Use Python to do the same you did with cURL.

### Setup Python and pip

Find the instructions how to install Python [here](https://www.python.org/downloads/). Use Python 3 (any version > 3.x) to do the same requests.

Many IDEs support Python but you don't need any to run a script.
You can run a Python script with the command:

```sh
python3 script.py
```

### Make requests

To access the GitHub API, you can use the _requests_ package.

```sh
pip install requests
```

Use the [github-locations.py](./github-locations.py) file to start off your script. You can rewrite it completely.

[An intro to requests in Python](https://realpython.com/python-requests/).

## Make an authenticated request

Create a repo in your account with an API call.
