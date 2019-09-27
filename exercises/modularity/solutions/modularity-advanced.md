# Solutions: Modularity - Advanced

There is no exact solution here.

Your core classes will include concepts such as the kinds of food (e.g. "vegetarian" or "pasta"), the dishes served by a cafeteria (which have one or more kinds, a name, a price...), the cafeterias (which have a location, a name, a list of dishes per day, an owner...), the users (which have a name, favorite kinds of food, a location, ...).

You will likely need to use OS classes such as an HTTP client to view and edit data, and a GPS service to see where the user is.

Your app will have pages such as a login page for users, a page for owners to edit their cafeteria's menu, a page to edit an user's profile, a page to display nearby food for users, and perhaps others.

The utilities are classes such as the authentication service for users (which could be reused for other apps targeting the same university), and a local cache for menus (which could be reused whenever you need to cache something).

