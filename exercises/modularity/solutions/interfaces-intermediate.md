# Solutions: Interfaces - Intermediate

Apple, Samsung and Nokia are certainly not managers; a manager is a person, not a company. But they can all be suppliers, consumers or service providers depending on your point of view. For instance, a company could be supplied in phones by Apple. Or they could get a service from Samsung to get phones, IT support for the phones, and replacement phones if they break. If you are a manufacturer of screens or processors, then Nokia can be one of your consumers.

The three Bills mentioned in the statement are all humans. Regardless of your exact definition of a human, this concept clearly exists: a human has an appearance, some level of physical and mental ability, and so on. They are also benefactors to charity, an interface that could contain members such as "how much they are willing to spend per year" or "which kinds of charity do they favor". However, "Humans named Bill" is not an interface: there is nothing particular that humans named Bill can do that others cannot. Similarly, one could include "amount of money owned" in the definition of a human, but "rich humans" is not an interface.

The Nobel Prize, the Turing Award and the Fields Medal are all awards. But they have different names, different criteria, and different regularity (the Fields Medal is only given once every four years). Thus, an interface representing them should expose these three concepts. Such an interface could also include a list of past winners, or a detailed description of the award.

A computer science faculty, a high school specialized in economics and a night school are all kinds of school. If you are a student, you may think of them in terms of which degree they require incoming students to have, which degree they grand, and what their pedagogical strengths are. If you are a government, you may think of them in terms of how much funding they need, how many students are enrolled, or the average amount of tax the state gets from their graduates. Other points of view are also possible.

Paper money, credit cards and store gift cards are all kinds of money. However, credit cards are special because the merchant is not guaranteed to get the money; the card owner can later dispute the transaction. This would most likely require special handling.


## Practical example - Developing in a team

### Situation 1

The `AuthenticationClient` interface should contain:

- A method to log an user in, given that user's credentials
- A method to register a new user, given that user's information
- A method to reset an user's password, given that user's e-mail

The exact details are also interesting: how should errors be handled? Can anyone request a password reset at any time, or should there be a limit on number of resets per day? What happens if an user tries to register with an e-mail that belongs to an existing user?


### Situation 2

There are two ways you could go here. One is to do a low-level implementation where `Character`s store the exact inputs they receive, and those inputs are sent across the network and executed on other player's machines to determine what happens. Another is to store the actual actions that characters peform, and send those across the network. The characters may also need to store the customizations applied by the player, e.g. different weapons or equipment, cosmetic accessories...

