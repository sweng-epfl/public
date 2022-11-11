# Design Patterns

This document contains a curated list of common design patterns, including context and examples.

_These examples are there to concisely illustrate patterns._
_Real code would also include visibility annotations (`public`, `private`, etc.), make fields `final` if possible,_
_provide documentation, and other improvements that would detract from the point being made by each example._


## Adapter

An _adapter_ converts an object of type `X` when it must be used with an interface that accepts only objects of type `X'`, similar but not the same as `X`.
For instance, an app may use two libraries that both represent color with four channels, one B/G/R/A and one A/R/G/B.
It is not possible to directly use an object from one library with the other library, and that's where you can use an _adapter_:
an object that wraps another object and provides a different interface.

A real-world example is an electrical adapter, for instance to use a Swiss device in the United States: both plugs fundamentally do the same job,
but one needs a passive adapter to convert from one plug type to the other.

Example:
```java
interface BgraColor {
    // 0 = B, 1 = G, 2 = R, 3 = A
    float getChannel(int index);
}

interface ArgbColor {
    float getA();
    float getR();
    float getG();
    float getB();
}

class BgraToArgbAdapter implements ArgbColor {
    BgraColor wrapped;

    BgraToArgbAdapter(BgraColor wrapped) {
        this.wrapped = wrapped;
    }

    @Override public float getA() { return wrapped.getChannel(3); }
    @Override public float getR() { return wrapped.getChannel(2); }
    @Override public float getG() { return wrapped.getChannel(1); }
    @Override public float getB() { return wrapped.getChannel(0); }
}
```


## Builder

A _builder_ works around the limitations of constructors when an object must be immutable yet creating it all at once is not desirable.
For instance, a `Rectangle` with arguments `width, height, borderThickness, borderColor, isBorderDotted, backgroundColor` is complex,
and a constructor with all of these arguments would make code creating a `Rectangle` hard to read.
Furthermore, some arguments logically form groups: it is not useful to specify both `borderColor` and `isBorderDotted` if one does not want a border.
Creating many rectangles that share all but a few properties is also verbose if one must re-specify all of the common properties every time.
Instead, one can create a `RectangleBuilder` object that defines property groups, uses default values for unspecified properties,
and has a `build()` method to create a `Rectangle`.
Each method defining properties returns `this` so that the builder is easier to use.

A special case of builders is when creating an object incrementally is otherwise too expensive, as in immutable strings in many languages.
If one wants to create a string by appending many chunks, using the `+` operator will copy the string data over and over again, creating many intermediate strings.
For instance, appending `["a", "b", "c", "d"]` without a builder will create the intermediate strings `"ab"` and `"abc"` which will not be used later.
In contrast, a `StringBuilder` can internally maintain a list of appended strings, and copy their data only once when building the final string.

Example:
```java
class Rectangle {
    public Rectangle(int width, int height, int borderThickness, Color borderColor, boolean isBorderDotted, Color backgroundColor, ...) {
       ...
    }
}

class RectangleBuilder {
    // width, height are required
    RectangleBuilder(int width, int height) { ... }
    // optional, no border by default
    RectangleBuilder withBorder(int thickness, Color color, boolean isDotted) { ... ; return this; }
    // optional, no background by default
    RectangleBuilder withBackgroundColor(Color color) { ... ; return this; }
    // to create the rectangle
    Rectangle build() { ... }
}

// Usage example:
new RectangleBuilder(100, 200)
    .withBorder(10, Colors.BLACK, true)
    .build();
```


## Composite

A _composite_ handles a group of objects of the same kind as a single object, through an object that exposes the same interface as each individual object.
For instance, a building with many apartments can expose an interface similar to that of a single apartment, with operations such as "list residents" that compose the results
of calling the operation on each apartment in the building.

Example:
```java
interface FileSystemItem {
    String getName();
    boolean containsText(String text);
    ...
}

class File implements FileSystemItem {
    // implementation for a file
}

class Folder implements FileSystemItem {
    Folder(String name, List<FileSystemItem> children) { ... }

    // the implementation of "containsText" delegates to its children
    // the children could themselves be folders, without Folder having to know or care
}
```


## Facade

A _facade_ hides unnecessary details of legacy or third-party code behind a clean facade.
It's a kind of adapter whose goal is to convert a hard-to-use interface into an easy-to-use one,
containing the problematic code into a single class instead of letting it spill in the rest of the system.
This can be used for legacy code that will be rewritten: if the rewritten code has the same interface as the facade, the rest of the program won't need to change after the rewrite.

Example:
```java
// Very detailed low-level classes, useful in some contexts, but all we want is to read some XML data
class BinaryReader {
    BinaryReader(String path) { ... }
}
class StreamReader {
    StreamReader(BinaryReader reader) { ... }
}
class TextReader {
    TextReader(StreamReader reader) { ... }
}
class XMLOptions { ... }
class XMLReader {
    XMLReader(TextReader reader, XMLOptions options) { ... }
}
class XMLDeserializer {
    XMLDeserializer(XMLReader reader, bool ignoreCase, ...) { ... }
}

// So we provide a facade
class XMLParser {
    XMLParser(String path) {
       // ... creates a BinaryReader, then a StreamReader, ..., and uses specific parameters for XMLOptions, ignoreCase, ...
    }
}
```


## Factory

A _factory_ is a function that works around the limitations of constructors by creating an object whose exact type depends on the arguments, which is not something most languages can do in a constructor.
Thus, one creates instead a factory function whose return type is abstract and which decides what concrete type to return based on the arguments provided to the factory.

Example:

```java
interface Config { ... }

class XMLConfig implements Config { ... }

class JSONConfig implements Config { ... }

class ConfigFactory {
    static Config getConfig(String fileName) {
        // depending on the file, creates a XMLConfig or a JSONConfig
    }
}
```


## Middleware (a.k.a. Decorator)

A _middleware_, also known as _decorator_ is a layer that exposes the same interface as the layer directly below it and adds some functionality,
such as caching results or retrying failed requests.
Instead of bloating an object with code that implements a reusable strategy orthogonal to the object's purpose, one can "decorate" it with a middleware.
Furthermore, if there are multiple implementations of an interface, without a middleware one would need to copy-paste the reusable logic in each implementation.
A middleware may not always use the layer below, as it can "short circuit" a request by answering it directly, for instance if there is recent cached data for a given request.

Example:
```java
interface HttpClient {
    /** Returns null on failure */
    String get(String url);
}

// Implements HTTP 1
class Http1Client implements HttpClient { ... }
// Implements HTTP 2
class Http2Client implements HttpClient { ... }

class RetryingHttpClient implements HttpClient {
    HttpClient wrapped;
    int maxRetries;

    HttpClientImpl(HttpClient wrapped, int maxRetries) {
        this.wrapped = wrapped;
        this.maxRetries = maxRetries;
    }

    @Override
    String get(String url) {
        for (int n = 0; n < maxRetries; n++) {
            String result = wrapped.get(url);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}

class CachingHttpClient implements HttpClient { ... }

// one can now decorate any HttpClient with a RetryingHttpClient or a CachingHttpClient,
// and since the interface is the same, one can decorate an already-decorated object, e.g., new CachingHttpClient(new RetryingHttpClient(new Http2Client(...), 5))
```


## Null Object

A _null object_ is a replacement for "a lack of object", e.g., `null`, that behaves as a "no-op" for all operations, which enables the rest of the code to not have to explicitly handle it.
This is useful even in languages that do not directly have `null`, such as Scala with `Option`, since sometimes one may want to run an operation on a potentially missing object
without having to explicitly handle `None` everywhere.
It's the equivalent of returning an empty list instead of `null` to indicate a lack of results: one can handle an empty list like any other list.

Example:
```java
interface File {
    boolean contains(String text);
}

class RealFile implements File { ... }

class NullFile implements File {
    @Override
    boolean contains(String text) {
        return false;
    }
}

class FileSystem {
    static File getFile(String path) {
        // if the path doesn't exist, instead of returning `null` (or a `None` option in languages like Scala),
        // return a `NullFile`, which can be used like any other `File`
    }
}
```


## Observer

The _observer_ pattern lets objects be notified of events that happen in other objects, without having to poll for changes.
For instance, it would be extremely inefficient for an operating system to constantly ask the keyboard "did the user press a key?", since >99% of the time this is not the case.
Instead, the keyboard lets the OS "observe" it by registering for change notifications.
Furthermore, the keyboard can implement a generic "input change notifier" interface so that the OS can handle input changes without having to depend on the specifics of any input device.
Similarly, the OS can implement a generic "input change observer" interface so that the keyboard can notify the OS of changes without having to depend on the specifics of any OS.

Example:
```java
interface ButtonObserver {
    // Typically the object that triggered the event is an argument, so that the observer can distinguish multiple sources
    // if it has registered to their events
    void clicked(Button source);
}

class Button {
    void registerForClicks(ButtonObserver button) {
        // handles a list of all observers
    }
    // optionally, could provide a way to remove an observer
}

// One can now ask any Button to notify us when it is clicked
// In fact, the Button may implement this by itself observing a lower-level layer, e.g., the mouse, and reporting only relevant events
```


## Pool

A _pool_ keeps a set of reusable objects to amortize the cost of creating these objects, typically because their creation involves some expensive operation in terms of performance.
For instance, connecting to a remote server is slow as it requires multiple round-trips to perform handshakes, cryptographic key exchanges, and so on;
a connection pool can lower this cost by reusing an existing connection that another part of the program used but no longer needs.
As another example, language runtimes keep a pool of free memory that can be used whenever the program allocates memory, instead of asking the operating system for memory each time,
which is slow because it involves a system call. The pool still needs to perform a system call to get more memory once it's empty, but that should happen rarely.

The pool pattern is typically only necessary for advanced performance optimizations.

Example:
```java
class ExpensiveThing {
    ExpensiveThing() {
        // ... some costly operation, e.g., opening a connection to a server ...
    }
}

class ExpensiveThingPool {
    private Set<ExpensiveThing> objects;

    ExpensiveThing get() {
        // ... return an existing instance if `objects` contains one, or create one if the pool is empty ...
    }
    // Warning, "releasing" the same thing twice is dangerous!
    void release(ExpensiveThing thing) { ... }
}

// Instead of `new ExpensiveThing()`, one can now use a pool
```

## Singleton

A _singleton_ is a global variable by another name, which is typically either a bad idea or a workaround for the limitations of third-party code.
A singleton is an object of which there is only one instance, publicly accessible by any other code.

There are advanced cases in which a singleton might be justified, such as in combination with a pool to share a pool across libraries, but it should generally be avoided.


## Strategy

A _strategy_ lets the callers of a function configure part of the function's logic by passing code as a parameter.
For instance, a sorting method needs a way to compare two elements, but the same type of elements might be compared in different ways based on context, such as ascending or descending for integers.
The sorting method can take as argument a function that compares two elements, and then call this function whenever it needs a comparison.
Thus, the sorting method only cares about implementing an efficient sorting algorithm given a way to compare, and does not hardcode a specific kind of comparison.
This also ensures the sorting method does not need to depend on higher-level modules to know how to sort its input, such as what kind of object is being sorted, since the strategy takes care of that.

Example:
```java
// (De)serializes objects
interface Serializer { ... }

// Persistent cache for objets, which stores objects on disk
// Uses a "serializer" strategy since depending on context one may want different serialization formats, or perhaps even encryption for sensitive data
class PersistentCache {
    PersistentCache(Serializer serializer) { ... }
}
```


## MVC: Model-View-Controller

A _controller_ is an object that handles user requests, using a _model_ internally, then creates a _view_ that is rendered to the user.
MVC is appropriate when the user interacts with the controller directly, e.g., through an HTTP request.
MVC can decouple user interface code and business logic, making them more maintainable, reusable, and testable.

Example:
```java
// Model
class WeatherForecast {
    WeatherForecast(...) { ... }

    int getTemperature(...) { ... }
}

// View
// Could be HTML as is the example here, but we could also add a `WeatherView` interface and multiple types of views,
// such as a JSON one for automated request (using the HTTP "Accept" header to know what view format the user wants)
class HtmlWeatherView {
    HtmlWeatherView(int temperature, ...) { ... }

    String toString() { ... }
}

// Controller
class WeatherController {
    WeatherForecast forecast;

    WeatherController(...) {
        // The controller could create its own WeatherForecast object, or have it as a dependency, likely with an interface for it to make it testable
        forecast = ...;
    }

    HtmlWeatherView get(...) {
        int temperature = forecast.getTemperature(...);
        return new HtmlWeatherView(temperature, ...);
    }
}

// In general, one would use a framework that can be configured to know which HTTP paths correspond to which method on which controller object,
// But this can be done manually as well:
System.out.println(new WeatherController(...).get(...).toString());
```


## MVP: Model-View-Presenter

A _presenter_ is an object that is used by a _view_ to respond to user commands, and which uses a _model_ internally, updating the view with the results.
MVP is appropriate when the user interacts with the view, such as a mobile app, and has the same goal as MVC: make code more maintainable, reusable, and testable.

Example:
```java
// Model
class WeatherForecast {
    WeatherForecast(...) { ... }

    int getTemperature(...) { ... }
}

// View
// Could have an interface if a single Presenter can use multiple Views, e.g., for testing, or for multiple app form factors
class WeatherView {
    WeatherPresenter presenter;

    WeatherView(WeatherPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
        // ... configure the UI framework to call `onClick` when the user clicks
    }

    void start() { /* ... display the user interface ... */ }

    void onClick(...) { presenter.showTemperature(); }

    void showTemperature(int temperature) {
        // ... displays `temperature` ...
    }
}

// Presenter
class WeatherPresenter {
    WeatherForecast forecast;
    WeatherView view;

    WeatherPresenter(...) {
        // Same remark as the MVC example concerning injection
        forecast = ...;
    }

    void setView(WeatherView view) { this.view = view; }

    void showTemperature() {
        int temperature = forecast.getTemperature(...);
        view.showTemperature(temperature);
    }
}

// Usage example:
new WeatherView(new WeatherPresenter(...)).start();

```


## MVVM: Model-View-ViewModel

A _viewmodel_ is a platform-independent user interface, which defines data, events for data changes using the _observer_ pattern, and commands.
The viewmodel internally uses a _model_ to implement operations, and a _view_ can be layered on top of the viewmodel to display the data and interact with the user.
MVVM is an evolution of MVP which avoids keeping state in the view, and which emphasizes the idea of a platform-independent user interface,
instead of making the presenter/view interface match a specific kind of user interface such as a console app.

Example:
```java
// Model
class WeatherForecast {
    WeatherForecast(...) { ... }

    int getTemperature(...) { ... }
}

// View
// There is no need for an interface, because the viewmodel does not interact with the view,
// thus there can be many different views for a viewmodel without any shared interface between them
class WeatherView {
    WeatherViewModel viewModel;

    WeatherView(WeatherViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.registerForTemperatureChanges(showTemperature);
        // ... configure the UI framework to call `onClick` when the user clicks
    }

    void start() { /* ... display the user interface ... */ }

    void onClick(...) { viewModel.updateTemperature(); }

    void showTemperature() {
        // ... displays `this.viewModel.getTemperature()` ...
        // (or the viewmodel could pass the temperature as an argument directly)
    }
}

// ViewModel
class WeatherViewModel {
    // No reference to a view!
    // Only an observer pattern enabling anyone (views, but also, e.g., unit tests) to register for changes

    WeatherForecast forecast;
    int temperature;
    Runnable temperatureCallback;

    WeatherViewModel(...) {
        // Same remark as the MVC example concerning injection
        forecast = ...;
    }

    // Data
    int getTemperature() { return temperature; }

    // Data change event
    void registerForTemperatureChanges(Runnable action) { this.temperatureCallback = action; }

    // Command
    void updateTemperature() {
        // In a real app, this operation would likely be asynchronous,
        // the viewmodel could perhaps have an "isLoading"  property enabling views to show a progress bar
        int temperature = forecast.getTemperature(...);
        this.temperature = temperature;
        if (temperatureCallback != null) { temperatureCallback.run(); }
    }
}

// Usage example:
new WeatherView(new WeatherViewModel(...)).start();
```
