package TADs.Heap;

public interface MyHeap<K extends Comparable<K>, V> {

    V find(K key);

    void insert(K key, V value);
    void delete(); //elimina siempre la raiz

    void delete(K key); //elimina con key

    String toString();

}
