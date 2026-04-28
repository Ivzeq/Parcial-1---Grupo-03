package inventarioModule;

public class LinkedDictionary<K, V> implements SimpleDictionary<K, V> {

    // ── Nodo interno ──────────────────────────────────────────────────────────

    private static class Entry<K, V> {
        K       key;
        V       value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
            this.next  = null;
        }
    }

    // ── Estado ────────────────────────────────────────────────────────────────

    private Entry<K, V> head;
    private int         size;

    public LinkedDictionary() {
        head = null;
        size = 0;
    }

    // ── Operaciones del TDA ───────────────────────────────────────────────────

    @Override
    public V put(K key, V value) {
        validateKey(key);
        if (value == null) {
            throw new IllegalArgumentException("El valor no puede ser null.");
        }

        Entry<K, V> existing = findEntry(key);
        if (existing != null) {
            V old = existing.value;
            existing.value = value;
            return old;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = head;
        head          = newEntry;
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        validateKey(key);
        Entry<K, V> entry = findEntry(key);
        return (entry != null) ? entry.value : null;
    }

    @Override
    public V remove(K key) {
        validateKey(key);

        if (head == null) return null;

        if (head.key.equals(key)) {
            V old = head.value;
            head  = head.next;
            size--;
            return old;
        }

        Entry<K, V> prev = head;
        while (prev.next != null) {
            if (prev.next.key.equals(key)) {
                V old      = prev.next.value;
                prev.next  = prev.next.next;
                size--;
                return old;
            }
            prev = prev.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        validateKey(key);
        return findEntry(key) != null;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public Object[] keys() {
        Object[] result = new Object[size];
        Entry<K, V> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.key;
            current   = current.next;
        }
        return result;
    }

    @Override
    public Object[] values() {
        Object[] result = new Object[size];
        Entry<K, V> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.value;
            current   = current.next;
        }
        return result;
    }

    @Override
    public void clear() {
        // Liberar referencias para ayudar al GC
        Entry<K, V> current = head;
        while (current != null) {
            Entry<K, V> next = current.next;
            current.key   = null;
            current.value = null;
            current.next  = null;
            current       = next;
        }
        head = null;
        size = 0;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private Entry<K, V> findEntry(K key) {
        Entry<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) return current;
            current = current.next;
        }
        return null;
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser null.");
        }
        if (key instanceof String && ((String) key).isBlank()) {
            throw new IllegalArgumentException("La clave no puede estar vacía.");
        }
    }
}
