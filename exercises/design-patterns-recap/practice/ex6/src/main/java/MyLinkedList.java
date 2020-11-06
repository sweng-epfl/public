public class MyLinkedList<T>{

    private Element<T> head;
    private Element<T> tail;

    public MyLinkedList(){
    }

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

}
