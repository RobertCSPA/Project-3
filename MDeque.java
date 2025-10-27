import java.util.NoSuchElementException;
import java.util.Iterator;

public class MDeque<E> implements Iterable<E>{

    private Node front;
    private Node back;
    private Node middle;
    private int length;

    private class MDequeIterator implements Iterator<E>{
        private Node current;
        private boolean rev;

        public MDequeIterator(boolean reverse){
            if(!reverse) current = front;
            else current = back;
            rev = reverse;
        }

        public boolean hasNext(){
            return current != null;
        }

        public E next() {
            if(!rev){
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.getVal();
                current = current.getNext();
                return value;
            }
            else{
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.getVal();
                current = current.getPrev();
                return value;
            }
        }
    }

    @Override
    public Iterator<E> iterator(){
        return new MDequeIterator(false);
    }

    public Iterator<E> reverseIterator(){
        return new MDequeIterator(true);
    }

    
    private class Node{
        private E value;
        private Node next;
        private Node prev;

        public Node(E v, Node n, Node p){
            value = v;
            next = n;
            prev = p;
        }

        public void setNext(Node newNext){
            next = newNext;
        }

        public void setPrev(Node newPrev){
            prev = newPrev;
        }

        public Node getNext(){
            return next;
        }

        public Node getPrev(){
            return prev;
        }

        public E getVal(){
            return value;
        }
    }

    public MDeque(){
        front = null;
        back = null;
        middle = null;
        length = 0;
    }

    public E peekBack(){
        return back.getVal();
    }

    public E peekFront(){
        return front.getVal();
    }

    public E peekMiddle(){
        return middle.getVal();
    }

    public E popBack(){
        if(length == 0){
            return null;
        }
        else if(length == 1){
            E temp = back.getVal();
            front = null;
            back = null;
            middle = null;
            length--;
            return temp;
        }
        else if(length % 2 == 0){
            E temp = back.getVal();
            back.getPrev().setNext(null);
            back = back.getPrev();
            middle = middle.getPrev();
            length--;
            return temp;
        }
        else{
            E temp = back.getVal();
            back.getPrev().setNext(null);
            back = back.getPrev();
            length--;
            return temp;
        }
    }

    public E popFront(){
        if(length == 0){
            return null;
        }
        else if(length == 1){
            E temp = front.getVal();
            front = null;
            back = null;
            middle = null;
            length--;
            return temp;
        }
        else if(length % 2 == 0){
            E temp = front.getVal();
            front.getNext().setPrev(null);
            front = front.getNext();
            length--;
            return temp;
        }
        else{
            E temp = front.getVal();
            front.getNext().setPrev(null);
            front = front.getNext();
            middle = middle.getNext();
            length--;
            return temp;
        }
    }

    public E popMiddle(){
        if(length == 0){
            return null;
        }
        else if(length == 1){
            E temp = middle.getVal();
            front = null;
            back= null;
            middle = null;
            length--;
            return temp;
        }
        else if(length == 2){
            E temp = middle.getVal();
            middle = middle.getPrev();
            middle.setNext(null);
            back = middle;
            length--;
            return temp;
        }
        else if(length % 2 == 0 && length > 2){
            E temp = middle.getVal();
            Node before = middle.getPrev();
            Node after = middle.getNext();
            if (before != null) before.setNext(after);
            if (after != null) after.setPrev(before);
            middle = before;
            length--;
            return temp;
        }
        else{
            E temp = middle.getVal();
            Node before = middle.getPrev();
            Node after = middle.getNext();
            if (before != null) before.setNext(after);
            if (after != null) after.setPrev(before);
            middle = after;
            length--;
            return temp;
        }
    }

    public void pushBack(E item) {
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        if(length == 0){
            Node newBack = new Node(item, null, null);
            front = newBack;
            back = newBack;
            middle = newBack;
            length++;
        }
        else if(length % 2 == 1){
            Node newBack = new Node(item, null, back);
            back.setNext(newBack);
            back = newBack;
            middle = middle.getNext();
            length++;
        }
        else{
            Node newBack = new Node(item, null, back);
            back.setNext(newBack);
            back = newBack;
            length++;
        }
    }

    public void pushFront(E item) {
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        if(length == 0){
            Node newFront = new Node(item, null, null);
            front = newFront;
            back = newFront;
            middle = newFront;
            length++;
        }
        else if(length % 2 == 1){
            Node newFront = new Node(item, front, null);
            front.setPrev(newFront);
            front = newFront;
            length++;
        }
        else{
            Node newFront = new Node(item, front, null);
            front.setPrev(newFront);
            front = newFront;
            middle = middle.getPrev();
            length++;
        }
    }

    public void pushMiddle(E item){
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        if(length == 0){
            Node newMid = new Node(item, null, null);
            front = newMid;
            back = newMid;
            middle = newMid;
            length++;
        }
        else if(length == 1){
            Node after = middle.getNext();
            Node newMid = new Node(item, after, middle);
            middle.setNext(newMid);
            middle = newMid;
            back = back.getNext();
            length++;
        }
        else if(length % 2 == 1 && length > 1){
            Node after = middle.getNext();
            Node newMid = new Node(item, after, middle);
            middle.setNext(newMid);
            if (after != null) after.setPrev(newMid);
            middle = newMid;
            if (middle.getNext() == null) back = middle;
            length++;
        }
        else{
            Node before = middle.getPrev();
            Node newMid = new Node(item, middle, before);
            middle.setPrev(newMid);
            if (before != null) before.setNext(newMid);
            middle = newMid;
            if (middle.getPrev() == null) front = middle;
            length++;
        }
    }

    public int size(){
        return length;
    }

    public String toString(){
        if(length == 0){
            return "[]";
        }
        return "[" + recursiveString(front) + "]";
    }

    private String recursiveString(Node current){
        if(current == null){
            return "";
        }
        if(current.getNext() == null){
            return "" + current.getVal();
        }
        else{
            return "" + current.getVal() + ", " + recursiveString(current.getNext());
        }
    }
}

