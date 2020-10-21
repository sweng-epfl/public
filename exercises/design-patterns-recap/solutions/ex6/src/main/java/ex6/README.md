# Exercise 6
## Solution

Your ```iterator``` method should be implemented this way

```java
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                private Element<T> current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    T data = current.getData();
                    current = current.getNext();
                    return data;
                }
            };
        }
```
        
