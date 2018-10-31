# DP3 Exercises
The design patterns covered in this exercise set are:

* Strategy
* Iterator
* Model-View-Controller (MVC)

This set has 3 [theoretical questions](#theory) and 5 [practical exercises](#practice).

<a name="theory"></a>
## Theory

### Question 1
The following pseudo-code is a sketch of an online students management application that uses the MVC pattern.

```java
class StudentsDatabase {
    List<Student> mStudents;

    //...

    List<Student> getStudents();
}
class StudentsManager implements WebPage {
    @Override
    public String toHtml() {
        //...
        for (Student s : mDb.getStudents()) {
            toReturn = toReturn + s.name + "<br/>";
        }
        return toReturn;
    }
}

class WebApplication {
    public String getRequest(String url) {
        //...
        WebPage page = new StudentsManager(...);
        //...
        return page.toHtml();
    }
}
```

Which of the following statements is (are) true?

- [ ] `StudentsDatabase` is the model, `StudentsManager` is the controller, and `WebApplication` is the the view.
- [ ] `StudentsDatabase` is the model, `StudentsManager` is the view, and `WebApplication` is the controller.
- [ ] `StudentsManager` is the model, `StudentsDatabase` is the view, and `StudentsManager` is the controller.
- [ ] This is not MVC, because `StudentsManager` must use a listener to be notified when the database changes.

### Question 2

Which of the following are true about the strategy pattern?

- [ ] The strategy pattern uses aggregation instead of inheritance in order to avoid code duplication
- [ ] The strategy pattern requires to settle for a particular implementation of an algorithm at compile time
- [ ] It is possible for a class to implement multiple unrelated strategies to be used in different contexts
- [ ] The strategy pattern requires a field of the desired strategy interface to be present in the class that wants to use the strategy

### Question 3
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

<a name="practice"></a>
## Practice

You can open this exercise and start working on it using Android Studio. The steps to open this exercise are:

1. Import Project
2. Point to the directory of this exercise (the parent folder of this README.md)
3. Keep following the steps (by pressing `Next`)

* [`Exercise 1`](src/ch/epfl/sweng/dp3/ex1/README.md)
* [`Exercise 2`](src/ch/epfl/sweng/dp3/ex2/README.md)
* [`Exercise 3`](src/ch/epfl/sweng/dp3/ex3/README.md)
* [`Exercise 4`](src/ch/epfl/sweng/dp3/ex4/README.md)
* [`Exercise 5`](src/ch/epfl/sweng/dp3/ex5/README.md)

Congratulations, you completed the entire exercise set. Now you can look at [the solutions](Solutions.md).