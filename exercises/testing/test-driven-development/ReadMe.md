# Test-Driven Development

In this exercise, you will practice _Test-Driven Development_, TDD for short, in which tests help you write the code instead of only being a way to check code for correctness. The idea is simple:
- Before writing code, write tests for the simplest feature your code will have.
- Write code to makes the tests pass
- Write tests for the next feature
- Write code to makes these new tests pass
- ... and so on ...

This has multiple benefits:
- You are forced to think of what exactly your code should do, including edge cases, before you write the code. Thus, you cannot fall into the trap of simply writing one test per code path and forgetting about the paths that the code does not explicitly handle.
- You can get a feel for the API of the code you're writing. If writing the tests is painful, using the code outside of tests will probably be painful too.
- You work on less code at a time between testing phases, so it's easier to figure out which part of the code is responsible for test failures: the one you just wrote!
- Since you wrote the tests before the code, they are good examples of how the code should work, which can be useful to the rest of the team.


You are a new developer and want to make your own online concert ticket shop. Follow these steps:

- Create a class representing tickets.
  Tickets should have a category (Normal, VIP, ...), a location, and a music group associated with it.
  For simplicity, you can assume that music groups can be uniquely identified by their name.

- Create a class representing the actual shop. The shop should hold all currently available tickets and should allow the following operations:
  - You should be able to add new tickets to your shop.
  - You should be able to buy a ticket, given different requirements (the location/the group/the category or all of them).
  - You should be able to improve the category of any ticket (i.e., change its category to VIP)  given a special promotion campaign.
  - You should be able to change their ticket with an equivalent one (i.e., same group and category) in another given location, if available.
  - You should have a way to print nicely the currently available tickets.

Implement the shop using TDD. You are free to add any methods/classes that you like.
