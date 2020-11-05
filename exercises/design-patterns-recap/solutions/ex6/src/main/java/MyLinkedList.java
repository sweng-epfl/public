import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{

    private Element<T> head;
    private Element<T> tail;

    public void add(T data){
        Element<T> newElement = new Element<>(data);
        if(head == null){
            head = newElement;
        }
        else{
            tail.setNext(newElement);
        }
        tail = newElement;
    }

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
}
