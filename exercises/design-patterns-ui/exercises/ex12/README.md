# Exercise 12

## Todo app revamped

In this exercise, you will recreate the todo app you made in [Exercise 8](../ex8/README.md) using a declarative framework suitable for MVVM.

Use [anvil-ui](https://github.com/anvil-ui/anvil) and recreate the application with this architecture.

This statement is voluntarily kept short to let you explore and struggle against new tools and environments. You should be able to find most of your answers on Google, but ask an assistant if you get stuck or if you do not understand a concept.

### Declarative frameworks crash course

Anvil UI is a declarative framework inspired by [ReactJS](https://reactjs.org/). In this framework, the view is not explicitely modified by the programmer (like setting a TextView or reading from an EditText). As you might remember from exercise 8, this synchronization between state of the model and state of the view must be manually enforced at every change, which proved to be a major source of bugs in application front-ends.

The solution to this problem came from functional programming: instead of remembering view state, we create a system in which the view is automatically derived at all times from model state changes, by letting client-side algorithms do the heavy lifting. In short, the key idea is to never modify the view, and instead describe views declaratively by enumerating all possible states. Views are then bound to the model state and react (heh) to any change using the Observer design pattern.

```
UI = f(state)
```

Many big companies actively develop open-source declarative frameworks, to name a few examples: Facebook (ReactJS), Google (Angular, Flutter), Ebay (Marko).

Some MVVM frameworks such as [VueJS](https://vuejs.org/) and the Android MVVM components also allow binding from the view to the state (unlike ReactJS and Anvil in which data flows from state to view). In these frameworks, state and view are always synchronized by means of reactive programming constructs such as Streams.