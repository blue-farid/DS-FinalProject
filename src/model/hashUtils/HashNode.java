package model.hashUtils;

import java.util.Map.Entry;

public class HashNode<K, V> implements Entry<K, V> {
    private final K key;
    private V value;
    private final int hashCode;
    private HashNode<K, V> next;
    public HashNode(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public HashNode<K, V> getNext() {
        return next;
    }

    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }
}