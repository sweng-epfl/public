# Exercise 9

## A web forum with MVC

In this exercise, we will be creating a web forum using MVC in-memory (i.e. not persisted to a database). Again, we will start from scratch.

We will be using [Javalin](https://javalin.io/), a lightweight web framework.

First, initialize a Gradle project with:

`gradle init --type=java-application`

Edit `build.gradle` and add to the `dependencies` section:

```groovy
    compile 'io.javalin:javalin:3.6.0'
```

Edit the main file with the following code:

```java
    import io.javalin.Javalin;

    public class App {
        public String getGreeting() {
            return "Hello world.";
        }

        public static void main(String[] args) {
            Javalin app = Javalin.create().start(7000);
            app.get("/", ctx -> ctx.result("Hello World"));
        }
    }
```

You can now launch your web server with `gradle run`. Open a web browser and access URL `http://localhost:7000/`. You should see the rendered "Hello World".


### The project

Our goal is to create a very basic web forum with threads of messages (like [Reddit](https://reddit.com)), but without user management.

### HTTP crash course

Web applications are based around text requests (called "verbs") sent by clients to some server, which is accessed with a URL. The most common are:

- `GET`: retrieve a resource (such as an HTML page)
- `POST`: send data to a server using key-value pairs
- `PUT`: replace some resource on a server
- `DELETE`: remove some data on a server

Servers listen to requests defined for some routes, which are relative to the URL of the server. For instance, when you type `https://sweng.epfl.ch/`, you are sending a `GET` request to route `/` of `sweng.epfl.ch`, which will return you the HTML of the course webpage.

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

### The task

In this exercise, our main function will act as the Controller of MVC: indeed, given an action (a request), it will return a corresponding view (some HTML). Note that more complex applications will define many Controllers in dedicated classes. Your task is to write the necessary code to satisfy the following features, while respecting the Model-View-Controller model:

- All views should inherit from a `View` interface, which defines a single method `render()` that returns an HTML string. In this exercise, you can write HTML strings directly in the View implementation.

- The state of the application should be stored in a `ForumState` model. You can model forum threads however you want, as long as your implementation agrees with the pure MVC architecture. Threads should have a title and a unique ID.

- A GET at route `/` should return a page with the list of all thread titles as links. These links point to the corresponding routes defined next. At the top of the page, a form can POST to `/new` with parameters `user={username}` and `content={body}` where username and body are fed from user input.

- A GET at route `/forum?id={forumID}` where `{forumID}` is the unique identifier of a forum will send a GET request with GET parameter `id={forumID}` and will return a page with the list of all messages in the corresponding thread. At the bottom of the page, a form can POST to `/reply` with parameters `id={forumID}`, `user={username}` and `reply={body}` where username and body are fed from user input.

- The POST request to `/new` handles the creation of a new thread in the `ForumState`. Don't forget to generate some kind of unique ID.

- The POST request to `/reply` handles the creation of a reply to an existing thread.

### Optional: prettify your forum interface

Add some CSS or use a framework such as [PureCSS](https://purecss.io/) or [Bootstrap](https://getbootstrap.com/) to make your user interface visually appealing.
