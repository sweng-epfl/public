# DP - UI -- Ex7
Say you are creating a text editor that is able to manipulate documents. Specifically, the editor creates
documents (`Document` class) that consist of smaller document parts (`DocumentPart` class). Your customer asked
for a feature that converts a document to its HTML or plain text equivalent. Your approach is to introduce `toHTML` and `toPlainText` methods in your classes.

Have a look at the code. Do you see a problem with this approach? 
What if a customer wants to convert a document to, let say, LaTeX? What would you
do then? 

Use one of the design patterns presented in lecture to make the code more modular and open to extension.
Furthermore, introduce the ability to convert a document to LaTeX.
