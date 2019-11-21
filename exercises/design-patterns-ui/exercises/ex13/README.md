# Exercise 13

## Real-time chat mini-project

In this project, you will use the [NodeJS](https://nodejs.org/en/) environment to program a real-time chat server and client.

You can use either [ReactJS](https://reactjs.org/) or [VueJS](https://vuejs.org/) to build the front-end.
The front-end should hold a state consisting of a list of messages. In the view, a chat list should display all messages received since connection (this chat list should be derived automatically from the state using your framework of choice).

Use [SocketIO](https://socket.io/), an abstraction over websockets and backward-compatible techniques such as polling, to program the real-time back-end. Use the socket.io client to connect your front-end to this server.

Both parties should communicate using the following events:

- `send_message(username, message)`: a client can send a message with a username and a message body

- `push_message(username, message, timestamp)`: the server broadcasts the received messages. He can also send messages when a user connects / disconnects.

### Test with peers

You can test your implementation with several machines or people. Connect to the same local network (for instance, create an ad-hoc network using your smartphone). All clients should be able to connect to the IP address of a single server and interact with it.

### Styling

Optionally, you can style your application with CSS or a CSS framework.


This statement is voluntarily kept short to let you explore and struggle against new tools and environments. You should be able to find most of your answers on Google, but ask an assistant if you get stuck or if you do not understand a concept.

### Declarative frameworks crash course

[ReactJS](https://reactjs.org/) is a declarative framework in which the view is not explicitely modified by the programmer (like setting a `<p>` tag or reading from an `<input>`). As you might remember from exercise 8, this synchronization between state of the model and state of the view must be manually enforced at every change, which proved to be a major source of bugs in application front-ends.

The solution to this problem came from functional programming: instead of remembering view state, we create a system in which the view is automatically derived at all times from model state changes, by letting client-side algorithms do the heavy lifting. In short, the key idea is to never modify the view, and instead describe views declaratively by enumerating all possible states. Views are then bound to the model state and react (heh) to any change using the Observer design pattern.

```
UI = f(state)
```

Many big companies actively develop open-source declarative frameworks, to name a few examples: Facebook (ReactJS), Google (Angular, Flutter), Ebay (Marko).

Some MVVM frameworks such as [VueJS](https://vuejs.org/) and the Android MVVM components also allow binding from the view to the state (unlike ReactJS and Anvil in which data flows from state to view). In these frameworks, state and view are always synchronized by means of reactive programming constructs such as Streams.

### HTML crash course

The web uses HTML (a definition of XML) to create user interfaces. You can display text using the tag `<p>`:

```html
<p>A nice text here.</p>
```

You can define links to routes with `<a>`:

```html
<a href="/">An internal link</a>
<a href="https://sweng.epfl.ch">An external link</a>
<a href="#">A link to self</a>
```

You can also display forms, and POST them to some routes:

```html
<form action="/contact" method="post">
    <input type="text" name="username">
    <input type="text" name="content">
    <input type="submit" value="Submit">
</form>
```

When parsed by a browser, this will display a form with 2 texts inputs, and hitting the submit button will send a POST request to route `/contact` which body encodes pairs `username=Name written by user` and `content=Message of the user`.

There are additional tags for images, titles, containers (you can create any box layout by wrapping many `<div>` together).