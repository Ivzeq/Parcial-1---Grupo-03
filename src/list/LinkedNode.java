package list;

public class LinkedNode<E> {
    public LinkedNode <E> prev = null;
    public LinkedNode <E> next = null;
    public E value = null;

    public LinkedNode(E newValue){
        value = newValue;
    }
}
