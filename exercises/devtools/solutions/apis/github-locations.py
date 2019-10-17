import requests


def get_most_starred_repos(stars):
    request = requests.get(
        'https://api.github.com/search/repositories',
        params={'q': 'stars:>{0}'.format(stars)})
    if request.status_code == 200:
        return request.json()['items']
    else:
        raise Exception("Query failed to run by returning code of {}. {}".format(
            request.status_code, request))


def get_users(repos):
    users = []
    for repo in repos:
        users.append(repo['owner']['login'])
    return users


def get_locations(users):
    locations = []
    for user in users:
        request = requests.get(
            'https://api.github.com/users/{0}'.format(user))
        if request.status_code == 200:
            locations.append(request.json()['location'])
        else:
            raise Exception("Query failed to run by returning code of {}. {}".format(
                request.status_code, request))
    return locations


repos = get_most_starred_repos(100000)
users = get_users(repos)
locations = get_locations(users)

print(locations)
