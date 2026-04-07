package listModule;

public class SimpleArrayList<E> implements SimpleList<E> {

    private static final int DEFAULT_CAPACITY = 4;
    private Object[] data;
    private int size;

    public SimpleArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public SimpleArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }
        data = new Object[initialCapacity == 0 ? 1 : initialCapacity];
        size = 0;
    }

    @Override //done
    public boolean add(E element) {
        ensureCapacity(size + 1);
        data[size] = element;
        size++;
        return true;
    }

    @Override  //done
    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = element;
        size++;
    }

    @Override //done
    public E remove(int index) {
        checkIndex(index); //validación

        E removed = (E) data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return removed;
    }

    @Override //done
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override //done
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override //done
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override //done
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    @Override //done
    public E set(int index, E element) {
        checkIndex(index);
        E previous = (E) data[index];
        data[index] = element;
        return previous;
    }

    @Override //done
    public int size() {
        return size;
    }

    @Override //done
    public boolean isEmpty() {
        return size == 0;
    }


    //HELPERS

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    private int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(data[i])) return i;
            }
        }
        return -1;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = data.length * 2;
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
        }
    }
}