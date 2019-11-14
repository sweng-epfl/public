### Question 1

Which of the following examples are likely to implement the Observer Design Pattern or the callback/listener? Select all that apply.

- [x] Push notifications 
- [x] android.widget.TextView and android.text.TextWatcher 
- [ ] java.util.concurrent.ThreadPoolExecutor.execute(java.lang.Runnable command)
- [x] Collaborative Office-like tools 

> Push notifications are sent when a change in the state of an app is relevant to its clients. Clients subscribe to the changes they are interested in.
>
> One can attach multiple TextWatchers to the same TextView, and they are all called whenever the text changes.
>
> java.util.concurrent.ThreadPoolExecutor.execute is used to execute the Runnable command piece of code in the future, not to react to some events.
>
> Collaborative Office-like tools can implement the Observer design pattern to keep a document in sync across multiple devices. The devices can subscribe to a central repository that sends events at every change of the document state.

### Question 2 (Answer)

Imagine you have an application for managing a telephone directory through a web UI.  This application operates on `Person` objects, with each `Person` representing an entry in the phone directory. The data of the directory is stored in a SQL database on the backend, and each `Person` object corresponds to a row in a _Directory_ table in the database. Whenever the application uses a getter of a `Person` object, such as `Person.getZipcode()`, the `Person` implementation goes to the database to read the data and return it. Whenever the application uses a `Person` setter, like `Person.setZipcode(...)`, the code goes to the database and updates the corresponding row. Beyond this, the `Person` code does little else.

Of the following four design patterns, which would you say is the one implemented in the `Person` class?

- [x] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator

> The telephone directory data is stored in the SQL database, and the `Person` class does not maintain any state. All it does is provide an interface between the application and the database.
>
>The correct choice is #1: the `Person` class essentially converts between the SQL interface provided by the database to the phone directory data and the setter/getter interface needed by the application to operate on that data. There is no change of functionality, just an adaptation of the interface.
>
> Option #2 is incorrect, even though it matches architecturally the client/server model used to illustrate the proxy design pattern. It nevertheless isn't a good match here, because a proxy by definition preserves the interface provided by the server, whereas here the SQL interface provided by the server is not exposed to the client. One might argue that the application uses both an adapter and a proxy, but the proxy is not strictly necessary here, whereas the adapter is.
>
> Option #3 is incorrect because the `Person` class does not provide any additional functionality over the database, yet that is a key property of composites.
>
> Option #4 is incorrect because the SQL interface is significantly different from the `Person` interface, yet a decorator does not change interfaces.

### Question 3

An example of the composite design pattern is a tree of menus and menu items; a tree is by definition acyclic. Is it possible to get cyclic data structures when employing the Composite design pattern?

- [x] yes
- [ ] no

> Yes, it is possible, if a `Composite` has a reference to an ancestor node. For example, a menu might refer to another menu (as in "this dessert menu can also be had in the Cafe, so if the Diner has no available seats, you can go to enjoy a dessert in the Cafe"). This would lead to a cycle.
>
> In general, having such loops in a composite is not a good idea, because operations applied recursively might never finish, unless additional checks are put in place.

### Question 4

Consider the Abstract Syntax Tree example in the lecture video about the Visitor Design Pattern (implementation sketch around 3m:30s). Suppose the children of the `AddNode` and `AssignmentNode` were publicly accessible, but that there was no `accept()` method in the `Node` interface.

How would you need to change the `NodeVisitor` class to achieve functionality similar to the Visitor pattern?

- [ ] Not possible, since without the `accept()` method one cannot visit all elements
- [ ] Call `visit(lhs)` and `visit(rhs)`, or `visit(op1)` and `visit(op2)`, to visit the sub-elements of an operation
- [x] Identify the type of the sub-elements and call the appropriate methods, such as `visit((VariableNode)lhs)` or `visit((NumberLiteralNode)lhs)`

> The role of the `accept()` method is to serve as the second round of dispatch in the double-dispatch scheme of the Visitor pattern. By calling the abstract `accept()`, the exact node type is identified at runtime and the appropriate `accept()` method is dispatched, in turn calling the appropriate `visit()` (see video lecture at 5m:08s).
>
> Without the `accept()` method, we have to identify the type of the sub-elements explicitly, as done in option #3. Then it becomes possible to visit all elements of a structure even without using the classic Visitor pattern, which makes option #1 incorrect. However, without the Visitor pattern the code is more complex and less manageable. Regarding option #2, simply calling `visit(lhs)` instead of `lhs.accept()` would cause a compiler error, because the target `visit()` overload cannot be determined statically.

### Question 5

Consider the following implementation of an iterator for a custom list class, based on the standard Java's `ArrayList` implementation.

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T>{
    ArrayList<T> mList = new ArrayList<T>();

    public boolean add(T e) {
        return mList.add(e);
    }

    public class MyListIterator implements Iterator<T> {
        int mIdxNextElem = 0;
        @Override
        public boolean hasNext() {
            return mIdxNextElem < mList.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T toReturn = mList.get(mIdxNextElem);
            mIdxNextElem++;
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new MyListIterator();
    }
}
```

Which statement(s) is (are) true about the following code:

```java
public class TestList {
    public static void main(String args[]) {
        MyArrayList<String> list = new MyArrayList<String>();

        //... add n elements to the list ...

        for (String s : list) {
            System.out.println(s);
        }
    }
}
```

- [x] Iterating over a list of 10,000 elements takes twice as much time as iterating over a list of 5,000 elements
- [ ] Iterating over a list of 10,000 elements takes four times as much time as iterating over a list of 5,000 elements
- [x] Determining if the iterator reached the end of the list takes a fixed amount of time on each iteration in the `next()` method
- [x] Accessing the item at the current position of the iterator takes a fixed amount of time

> `ArrayList`'s `get()` and `size()` methods take a constant amount of time to execute (independent of the size of the list). Therefore, iterating over all elements of `MyArrayList` using `MyListIterator` is O(n).
>
> [StackOverflow post with link to JavaDoc](https://stackoverflow.com/questions/6540511/time-complexity-for-java-arraylist)