# Modularity - Advanced

Now that you have a better understanding of modularity, its benefits, and how to achieve it, let's stop writing code and think about system design for a while.

For this exercise, you will need to draw a class diagram. While there are standards on how to do this, such as [UML](https://en.wikipedia.org/wiki/Class_diagram), we do not ask you for a formal diagram, since in practice engineers tend to use ad hoc methods. The overall idea is to draw each class/interface as a box, with some information in the box regarding the public interface it exposes and possibly some implementation details, and some arrows between boxes to describe inheritance and containment.

Imagine you've been hired to develop a mobile app to help students find which is the closest campus cafeteria that serves food they like. Your app must also include an administrative interface for cafeterias to input their menus. Draw a class diagram of your app, taking the following into account:

- Which are the core classes of your app? Those are the classes representing domain-specific entities, unlikely to be reused by other apps.
- What classes provided by the OS will you use? You do not have to think of a specific mobile OS, but rather of the overall idea of what a mobile platform provides you, such as an Internet connection.
- What "pages" will your app include? For instance, you will likely need a page for users to input their food preferences.
- Which are the utility classes of your app? Those are the classes that are probably not provided by the operating system, but that you could reuse in other apps.

