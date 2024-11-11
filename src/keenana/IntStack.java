/*
 * Course: CSC 1120 121
 * Term: Spring 2024
 * Assignment: Lab 9
 * Name: Andrew Keenan
 * Created: 3-20-2024
 */
package keenana;

import java.util.EmptyStackException;

/**
 * This is the intStack class that describes an IntStack used
 * for the callstack and other stacks needed in the lab
 */
public class IntStack {
    private Node head;
    private static class Node {
        private int data;
        private Node next;

        private Node(int value, Node nextNode){
            this.data = value;
            this.next = nextNode;
        }

        private Node(int data){
            this.data = data;
            this.next = null;
        }
    }

    private Node walkTo(int index){
        Node walker = head;
        for (int i = 0; i < index; i++){
            walker = walker.next;
        }
        return walker;
    }
    /**
     * adds an item to the top of the stack
     * @param i the int that is wished to be added
     */
    public void push(int i) {
        if(this.head == null){
            head = new Node(i);
        } else {
            Node temp = walkTo(size() - 1);
            temp.next = new Node(i);
        }
    }

    /**
     * returns the size of the stack
     * @return the size of the stack as an int
     */
    public int size() {
        int size = 0;
        Node walker = this.head;
        while (walker != null){
            size++;
            walker = walker.next;
        }
        return size;
    }

    /**
     * gets the int at the top of the stack and then removes it off
     * of the stack
     * @return the int value stored at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public int pop() {
        if (isEmpty()){
            throw new EmptyStackException();
        } else if (size() == 1){
            int retVal = head.data;
            head = head.next;
            return retVal;
        } else {
            Node temp = walkTo(size() - 2);
            int retVal = temp.next.data;
            temp.next = null;
            return retVal;
        }
    }

    /**
     * peeks at the top value of the stack but does not remove it
     * @return the value at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public int peek() {
        if (isEmpty()){
            throw new EmptyStackException();
        } else {
            Node temp = walkTo(size() - 1);
            return temp.data;
        }
    }

    /**
     * checks to see if the stack is empty
     * @return a boolean if the stack is empty or not
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String toString() {
        final int spaces = 9;
        StringBuilder sb = new StringBuilder();
        sb.append("|          |\n");
        sb.append("|----------|\n");
        for (int i = size() - 1; i >= 0; i--){
            sb.append("|");
            Node node = walkTo(i);
            int x = node.data;
            int space = spaces - String.valueOf(x).length();
            sb.append(" ".repeat(Math.max(0, space)));
            sb.append(x).append(" |\n");
        }
        sb.append("+----------+");
        return sb.toString();
    }
}
