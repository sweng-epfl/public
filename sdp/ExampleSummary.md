# Example weekly summary

This document is an example of a good weekly summary.

---

# Summary for week 4

## Bernadetta

I implemented single-sign-on login through EPFL's Tequila authentication system.

My time estimate was accurate, probably thanks to my experience implementing login via Google accounts two sprints ago.

Next time, I'll think more about how to reuse existing code, since I didn't do that there is a bunch of duplicated logic between Google and EPFL logins.


## Caspar

I implemented the map view for items, but I could not implement the "show itinerary" feature as the service we're using for the map does not support it, which is not documented.

The interface I designed for the map will be particularly useful for some of the next high-priority features, such as viewing all of the user's favorite items at once.

Next time, I'll try another map service and begin with a rough prototype to ensure it actually supports the features we need.


## Dorothea

I implemented filtering and searching for items, as well as an offline cache for the user's favorite items.

The filter and search feature was supposed to take the whole week, but I found a library that did exactly what we needed, so I integrated it,
and had time to add the offline cache, which was the highest-priority item in the product backlog.

In hindsight, I should maybe have spent the extra time on other tasks such as helping Caspar with the map itinerary feature.


## Ferdinand

I implemented a drawer menu for the app. It works on almost all devices, but there is a visual bug on some devices from one manufacturer, which I added to the product backlog.

My task went very smoothly thanks to the detailed Android documentation.

Next time, I'll try to pick a harder task, since this one was not very challenging.


## Hubert (Scrum Master)

I did not manage to implement either of my tasks, which were adding more fields to items and adding a basic interface to edit existing items, due to other projects keeping me too busy.

On the positive side, I did have time to prepare a demo for the app, including some of the new features.

Next time, I will manage my time better to avoid spending all of it on something else.


## Petra

I implemented localization in the app, with a choice of English and German for now.

I learned a lot of interesting ideas for our user interface, and implemented some of them to allow for dynamic language switching.

Next time, I will try to coordinate more with the rest of the team, as I got surprised by some new localization requirements from some of the pull requests merged during the week, which were stressful to integrate last-minute.


## Overall team

We implemented almost all of the user stories we had assigned to this sprint, and we believe the remaining ones are still high-priority and should stay.

Our time estimates are getting better, and this week we did not have any major problems due to them, unlike last week.

However, we only did one standup meeting at the beginning of the week, which was not enough since Petra was blindsided by new localization to do and Dorothea was not aware that her freed time could have been put to better use.
