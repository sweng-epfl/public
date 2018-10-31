# Iterator

## Question 1
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