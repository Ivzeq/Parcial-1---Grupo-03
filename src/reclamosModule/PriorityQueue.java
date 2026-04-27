package reclamosModule;

public interface PriorityQueue<E> {

    void enqueue(E element, int priority);

    E dequeue();

    E peek();

    int peekPriority();

    void clear();

    int size();

    boolean isEmpty();
}
