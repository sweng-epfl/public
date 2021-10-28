# Exercise 4 (Visitor)
Say you are creating a text editor that is able to manipulate documents. Specifically, the editor creates
documents (`Document` class) that consist of smaller document parts (`DocumentPart` class). Your customer asked
for a feature that converts a document to its HTML or plain text equivalent. Your approach is to introduce `toHTML` and `toPlainText` methods in your classes.

Have a look at the code. Do you see a problem with this approach? 
What if a customer wants to convert a document to, let say, LaTeX? What would you
do then? 

The problem is that if you want to add a new conversion (e.g., conversion to LaTeX), you will have to modify the implementation of Document, DocumentPart and the classes implementing them. One solution is to use the Visitor Pattern. Indeed, the Visitor Pattern allows you to add new features to a group of classes without modifying them.

Modify the given code using the Visitor Design Pattern to introduce the ability to convert a document to LaTeX.
