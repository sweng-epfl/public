# TDD Part 1

You are a new developer and want to make your own online concert ticket shop. Follow these steps:

- Create a class representing the tickets. The tickets should have a category (Normal, VIP, ...), a location, and a music group associated with it. For simplicity, you can assume that the groups can be uniquely identified by their name.

- Create a class representing the actual shop. The shop should hold all currently available tickets and should allow the following operations:
	- You should be able to add new tickets to your shop.
	- You should be able to buy a ticket, given different requirements (the location/the group/the category or all of them).
	- You should be able to improve the category of any ticket (i.e., change its category to VIP)  given a special promotion campaign.
	- You should be able to change their ticket with an equivalent one (i.e., same group and category) in another given location, if available.
	- You should have a way to print nicely the currently available tickets.


Implement the shop using TDD. You are free to add any methods/classes that you like.   
