//https://www.geeksforgeeks.org/linked-list-set-1-introduction/
class LinkedList{
    Node head;

    class Node {
        int data;
        Node next;

        Node(int d) {
            data = d;
        }
    }

    public void printList(){
        Node n = head;
        while (n != null){
            System.out.print(n.data+" ");
            n = n.next;
        }
    }

    public void pushFront(int data){
        Node n  = new Node(data);
        n.next = head;
        head = n;
    }

    public static LinkedList createList(int arr[]){
        LinkedList llist = new LinkedList();
        for (int data : arr){
            llist.pushFront(data);
        }


        return llist;
    }

    public static void main(String args[]) {
        int arr[] = {10, 7, 8, 9, 1, 5};
        LinkedList llist = createList(arr);
        llist.printList();
    }
}