### Question 1

Which of the following examples are likely to implement the Observer Design Pattern or the callback/listener? Select all that apply.

- [ ] Push notifications 
- [ ] android.widget.TextView and android.text.TextWatcher 
- [ ] java.util.concurrent.ThreadPoolExecutor.execute(java.lang.Runnable command)
- [ ] Collaborative Office-like tools

### Question 2

Imagine you have an application for managing a telephone directory through a web UI.  This application operates on `Person` objects, with each `Person` representing an entry in the phone directory. The data of the directory is stored in a SQL database on the backend, and each `Person` object corresponds to a row in a _Directory_ table in the database. Whenever the application uses a getter of a `Person` object, such as `Person.getZipcode()`, the `Person` implementation goes to the database to read the data and return it. Whenever the application uses a `Person` setter, like `Person.setZipcode(...)`, the code goes to the database and updates the corresponding row. Beyond this, the `Person` code does little else.

Of the following four design patterns, which would you say is the one implemented in the `Person` class?

- [ ] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator

### Question 3

An example of the composite design pattern is a tree of menus and menu items; a tree is by definition acyclic. Is it possible to get cyclic data structures when employing the Composite design pattern?

- [ ] yes
- [ ] no

### Question 4

Consider the Abstract Syntax Tree example in the lecture video about the Visitor Design Pattern (implementation sketch around 3m:30s). Suppose the children of the `AddNode` and `AssignmentNode` were publicly accessible, but that there was no `accept()` method in the `Node` interface.

How would you need to change the `NodeVisitor` class to achieve functionality similar to the Visitor pattern?

- [ ] Not possible, since without the `accept()` method one cannot visit all elements
- [ ] Call `visit(lhs)` and `visit(rhs)`, or `visit(op1)` and `visit(op2)`, to visit the sub-elements of an operation
- [ ] Identify the type of the sub-elements and call the appropriate methods, such as `visit((VariableNode)lhs)` or `visit((NumberLiteralNode)lhs)`

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

- [ ] Iterating over a list of 10,000 elements takes twice as much time as iterating over a list of 5,000 elements
- [ ] Iterating over a list of 10,000 elements takes four times as much time as iterating over a list of 5,000 elements
- [ ] Determining if the iterator reached the end of the list takes a fixed amount of time on each iteration in the `next()` method
- [ ] Accessing the item at the current position of the iterator takes a fixed amount of time