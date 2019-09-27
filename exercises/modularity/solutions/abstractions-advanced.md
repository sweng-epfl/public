# Solutions: Abstractions - Advanced

Because the questions are open-ended, there is no one true solution, but merely examples of solutions.


## Students

In the first case, one class is most likely enough, with some kind of list of interests per students, and a method to evaluate how similar one student's interests are to another's. Students also need a name, and perhaps other properties such as age or spoken languages to help match them to one another.

In the second case, there are probably many kinds of students: standard ones, exchange ones, auditors, students from other nearby universities, PhD students, ... all of which could be their own class, which all implement common methods such as "receive credits for a class" or "report for bad behavior" differently. The student base interface will also need administrative data, such as the full name, date of birth, nationalities...


## Transportation

There are many other ways to get someone from point A to point B, such as:

- Renting a car
- Car-sharing services
- Public transport
- Airplanes
- Bikes
- ...

Those can hide many internal details, such as which fuel the plane needs, or the exact mechanical details of a bike. However, an airplane cannot hide the fact that it needs an airport to land. Similarly, a car-sharing service cannot allow its users to start a journey anywhere, they must first find a car.


## Java

Languages like Java provide an abstraction of a computer: a machine with infinite memory, infinite storage, a clear memory model in the case of concurrent execution, standard types such as integers...

But the abstraction is not perfect. For instance, because a real computer does not have infinite memory, Java uses a garbage collector, which can pause a program to reclaim memory and thus break the illusion. Similarly, even though the average person does not imagine that integers have a limit, Java's integer types have bounds because the underlying machine cannot provide performant operations otherwise.


## Your experience

Talk about your answers with colleagues. If you both worked on the same project, do you share the same opinions? Why or why not?

