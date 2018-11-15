package ch.epfl.sweng.contracts;

import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("0 < capacity && 0 <= numberOfElements && contents != null")
class SwengStack {
    private int capacity;
    private int numberOfElements;
    private int[] contents;

    @Requires("0 < capacity")
    public SwengStack(int capacity) {
        this.capacity = capacity;
        numberOfElements = 0;
        contents = new int[capacity];
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public boolean isFull() {
        return numberOfElements == capacity;
    }

    @Requires("!isEmpty()")
    @Ensures("!isFull()")
    public int pop() {
        numberOfElements -= 1;
        return contents[numberOfElements];
    }

    @Requires("!isFull()")
    @Ensures("!isEmpty()")
    public void push(int x) {
        contents[numberOfElements] = x;
        numberOfElements += 1;
    }
}
