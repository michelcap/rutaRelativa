package TADs.Hash;

import Entidades.HashTag;

import java.util.List;

public interface HashTable<K, V> {
    public void put(K key, V value);
    public boolean contains(K key);
    public void remove(K clave);
    V get(K key);

    public List<Entry<K, V>> getEntries();
}