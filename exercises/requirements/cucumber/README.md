# Requirements gathering - Cucumber

In this exercise, you will write requirement specifications using *Cucumber* and use them to write some unit tests in Java.

You are working in a startup that wants to propose an inventory management program for small shops. You and your team agreed that you were in charge of meeting a shop manager that showed real interest in your project and accepted to help you in the development process. Your task is to chat with her to collect the needs of your future clients to best meet them while developing the application. 

Here is a transcript of what she told you:

> While using the application, I need to be able to add products to my inventory when I receive them. I would add products when receiving them by post and remove the ones that I sell when sending them to the client. I also need to see the current state of the inventory which must of course reflect the state of the real one. I will request the number of items per product. Sometimes a client sends me back a product because it did not match their expectations: in this case I have to refund the customer and this should add the product back to the inventory. I will never remove an item from the inventory if I do not have any in stock.

Let us assume that the shops you are targeting only sell three different products: "T-Shirt", "Jeans" and "Sweater".

Your task after the discussion is to extract more precise specifications from this conversation and write them down using *Cucumber*. You need to write *Cucumber Scenarios* and Java code in *StepDefinitions.java*. *Cucumber* will then use your scenarios and the Java code you write and run that as unit tests.

> *Cucumber* is a tool that let you write your specification is some sort of strictly formatted english and translate them as Java unit tests. The idea is to have easily readable specifications that you can discuss with the client that translate directly into tests. You need to first write a *scenario* in *test/resources/inventory/managingInventory.feature* using the [Gherkin language](https://cucumber.io/docs/gherkin/reference/) (here you will just need *Given*, *When* and *Then*). Then you need to write some code in *test/java/ch/sweng/inventory/StepDefinitions.java* using the *Cucumber* annotations that will translate the scenario into code. You can have a look at the [*Cucumber* documentation](https://cucumber.io/docs/guides/10-minute-tutorial/#write-a-scenario) if you are lost.

> We provide the beginning of the first scenario as well as the beginning of the implementation in *StepDefinitions.java* to get you started. For this exercise, you can put all scenarios in the same *.feature* file (in a real project you would modularized your scenarios into different files). We also provide a small implementation of the inventory so you can write your tests against something and run them.

> :information_source: To run Cucumber, execute `gradlew.bat cucumber` on Windows or `./gradlew cucumber` on macOS and Linux.
