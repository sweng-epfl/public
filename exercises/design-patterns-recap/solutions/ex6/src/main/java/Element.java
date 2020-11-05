public class Element<T> {

    private T data;
    private Element<T> next;

    public Element(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Element<T> getNext() {
        return next;
    }

    public void setNext(Element<T> next){
        this.next = next;
    }





}
