# Mocks

Now that you've written a few tests using dependency injection, you're probably thinking that writing full implementations of the dependencies for every test is rather annoying. And in a real app, those interfaces would likely have many methods, all of which need to be implemented in every fake dependency class! To solve this, use a _mocking library_.

Mocking libraries help you write fake dependencies, also called _mocks_, by making it easy to create implementations of any dependency interface that return the values you want, instead of having to write an implementation of the interface every time.

We recommend using the _Mockito_ library; take a look at the [Mockito website](https://site.mockito.org/#how) for a quick tutorial.

Rewrite the tests from the [Adapt the code](adapt-the-code) and [Test driven development](test-driven-development) exercises to use Mockito instead of ad-hoc interface implementations.
