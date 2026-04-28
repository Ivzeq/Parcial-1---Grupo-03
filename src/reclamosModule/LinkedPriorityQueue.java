package reclamosModule;

import java.util.NoSuchElementException;

public class LinkedPriorityQueue<E> implements PriorityQueue<E> {

    // ── Nodo interno ────────────────────────────────────────────────────────

    private static class Node<E> {
        E       data;
        int     priority;
        Node<E> next;

        Node(E data, int priority) {
            this.data     = data;
            this.priority = priority;
            this.next     = null;
        }
    }

    // ── Estado ───────────────────────────────────────────────────────────────

    private Node<E> head;
    private int     size;

    public LinkedPriorityQueue() {
        head = null;
        size = 0;
    }

    // ── Operaciones del TDA ──────────────────────────────────────────────────

    @Override
    public void enqueue(E element, int priority) {
        if (element == null) {
            throw new IllegalArgumentException("El elemento no puede ser null.");
        }
        if (priority < 1 || priority > 4) {
            throw new IllegalArgumentException(
                "Prioridad inválida: " + priority + ". Debe estar en [1..4].");
        }

        Node<E> newNode = new Node<>(element, priority);

        if (head == null || priority > head.priority) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null && current.next.priority >= priority) {
                current = current.next;
            }
            newNode.next  = current.next;
            current.next  = newNode;
        }

        size++;
    }

    @Override
    public E dequeue() {
        checkNotEmpty();
        E data = head.data;
        head   = head.next;
        size--;
        return data;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return head.data;
    }

    @Override
    public int peekPriority() {
        checkNotEmpty();
        return head.priority;
    }

    @Override
    public void clear() {
        Node<E> current = head;
        while (current != null) {
            Node<E> next  = current.next;
            current.data  = null;
            current.next  = null;
            current       = next;
        }
        head = null;
        size = 0;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    // ── Helper privado ────────────────────────────────────────────────────────

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola de prioridad está vacía.");
        }
    }
}
