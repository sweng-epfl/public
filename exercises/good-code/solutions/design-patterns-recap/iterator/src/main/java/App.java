public class App {
    public static void main(String[] args) {
        MyLinkedList<String> names = new MyLinkedList<>();
        names.add("Julian");
        names.add("Oscar");
        names.add("Steve");
        names.add("Roger");

        for(String name : names){
            System.out.println(name);
        }
    }

}
