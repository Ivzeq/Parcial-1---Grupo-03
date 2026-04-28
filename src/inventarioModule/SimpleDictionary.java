package inventarioModule;

public interface SimpleDictionary<K, V> {

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();

    Object[] keys();

    Object[] values();

    void clear();
}
