# Question 5: To like it, or to dislike it? That is the question. [30 points]

Now that Komix gained some prestige, you are looking into letting people rate the books they read.


## Part 1: Test the reaction service

You hired a friend to develop a service that collects the reactions of your users. 
Users will be able to like or dislike a book. To do so, they will have to be logged in to your service.

Your friend already implemented this feature in [`ReactionService`](src/main/java/model/ReactionService.java),
your task is to test it in [`ReactionServiceTest`](src/test/java/model/ReactionServiceTest.java).

_You will get up to 20 points for a useful and complete test suite that fully covers the statements and branches of [`ReactionService`](src/main/java/model/ReactionService.java)_


## Part 2: Display trending books

Now that people can react to books, you want to display the ones that are the most liked, to show that they are trending.
You will implement it in [`TrendingService`](src/main/java/model/TrendingService.java).

To help you, we provide [`App`](src/main/java/App.java), a simple interactive command-line interface to test your implementation.
We also encourage you to write your own tests in [`TrendingServiceTest`](src/test/java/model/TrendingServiceTest.java).

_You will get up to 10 points for a correct and maintainable `TrendingService`._
