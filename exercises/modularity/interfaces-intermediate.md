# Interfaces - Intermediate

In these exercises, we will take a step back from Java and think in terms of general concepts.


Which interface(s) do Apple, Samsung, and Nokia all have in common?

- [ ] Suppliers
- [ ] Consumers
- [ ] Service providers
- [ ] Managers

Which interface(s) do Bill Gates, Bill Clinton, and Bill Murray all have in common?

- [ ] Humans
- [ ] Humans named Bill
- [ ] Rich humans
- [ ] Benefactors to charity

Which interface should the Nobel Prize, the Turing Award, and the Fields Medal all expose? What methods should this interface have given their differences?

Which interface should a computer science faculty, a high school specialized in economics, and a night school all expose? How should this interface change based on one's point of view?

Of which concept are paper money, credit cards, and store gift cards examples of? In practice, which one would require the interface to change because of important implementation details?


## Practical example - Developing in a team

Alice, Bob, Carol and David are working on a software project together.
They are implementing an online multiplayer game.
They have not written any code yet and each member is specialized in one aspect of the game:
- Alice works on the graphics and game engine: she animates the characters and defines the game's physics and mechanics.
- Bob works on the authentication: he is developing an authentication server for user registration and login.
- Carol works on the multiplayer networking: she makes sure that all the events that are happening in each player's game are synchronized and processed on the game's main servers.
- David works on the GUI: he defines what the user sees when they are not in the game itself, such as the login screen, the main menu, etc.

They would love to work independently, but they are running into organizational issues.


### Situation 1

David would like to implement the login screen.
He is working on how its looks and behavior, including checking the user credentials against the authentication server.
However, Bob does not have any client API yet that would allow David to do so.
The user should also be able to register through some form, and also reset their password. 

Can you help them define a `AuthenticationClient` interface describing the actions that the authentication server provides?


### Situation 2

Alice would like to create 10 different characters.
Each character has a different play style, but they are all controlled the same way with keyboard and mouse.
But Carol needs to synchronize these player's actions across the network during play.

Can you help them define a `Character` parent that describes all interactions that a character can have?
When implementing this, should they define `Character` as an interface or an abstract class?
