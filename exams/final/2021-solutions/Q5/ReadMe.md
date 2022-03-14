# Question 5: Testing [25 points]

Your team decides to implement a "watch later" feature for the SwengFlix platform. The product backlog contains the following user stories:

As an **authenticated** user, I would like:
- To bookmark interesting content so that I can watch it later 
- To have an overview of my saved items so that I can choose what to watch more easily
- To remove items from my list so that I don't watch the same thing twice
- My "watch later" list to be only visible to me, in order to preserve my privacy

A team member already implemented the corresponding features:
- `media/WatchLaterService.java` implements the code managing the "watch later" list

Note the following:
- `data/RemoteDatabase.java` models a **remote** database; it behaves in an asynchronous fashion and can be unreliable.
- `data/RemoteAuthenticationService.java` models a **remote** authentication service; it behaves in an asynchronous fashion and can be unreliable.
 
To model unreliability and asynchrony, the code introduces a random delay on the database and the authentication service. 

_Your task is to write a clean and robust test suite for the `WatchLaterService`_:
- _You get 15 points if your tests are clean, robust, and are not affected by the unreliability of remote services_
- _You can get up to 10 points for coverage (100% branch coverage = max points)_

> :warning: Please double-check your coverage using the report generated by Gradle via `gradlew.bat build` on Windows and `./gradlew build` on Linux and macOS.
> IDEs may have slightly different ways of counting coverage; we will only check the Gradle-generated coverage when grading.