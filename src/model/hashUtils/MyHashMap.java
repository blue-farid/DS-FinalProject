package model.hashUtils;

import java.util.*;

public class MyHashMap <K, V> implements Map<K, V> {
    private ArrayList<HashNode<K, V>> buckets;
    private int size = 0;

    public MyHashMap() {
        // initial capacity
        this(10);
    }

    public MyHashMap(int capacity) {
        this.buckets = new ArrayList<>(capacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        K key;
        try {
            key = (K) o;
        }catch (ClassCastException e) {
            return false;
        }
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> hashNode = this.buckets.get(bucketIndex);
        while (hashNode != null) {
            if (hashNode.getKey().equals(key) &&
                    hashNode.getHashCode() == hashCode)
                return true;
            hashNode = hashNode.getNext();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object o) {
        K key;
        try {
            key = (K) o;
        }catch (ClassCastException e) {
            return null;
        }
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> hashNode = this.buckets.get(bucketIndex);
        while (hashNode != null) {
            if (hashNode.getKey().equals(key) &&
            hashNode.getHashCode() == hashCode)
                return hashNode.getValue();
            hashNode = hashNode.getNext();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> hashNode = this.buckets.get(bucketIndex);
        while (hashNode != null) {
            if (hashNode.getKey().equals(key) &&
            hashNode.getHashCode() == hashCode) {
                return hashNode.setValue(value);
            }
            hashNode = hashNode.getNext();
        }
        hashNode = this.buckets.get(bucketIndex);
        HashNode<K, V> newHashNode = new HashNode<>(key, value, hashCode);
        newHashNode.setNext(hashNode);
        this.buckets.set(bucketIndex, newHashNode);
        checkForIncreaseSize();
        return null;
    }

    private boolean checkForIncreaseSize() {
        if ((1.0 * size) / this.buckets.size() >= 0.8) {
            ArrayList<HashNode<K, V> > temp = this.buckets;
            int numBuckets = this.buckets.size() * 2;
            this.buckets = new ArrayList<>();
            this.size = 0;
            for (int i = 0; i < numBuckets; i++)
                this.buckets.add(null);

            for (HashNode<K, V> hashNode : temp) {
                while (hashNode != null) {
                    put(hashNode.getKey(), hashNode.getValue());
                    hashNode = hashNode.getNext();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private int hashCode(K key) {
        return Objects.hashCode(key);
    }
    private int getBucketIndex(K key) {
        int index = hashCode(key) % this.buckets.size();
        return index < 0 ? index * -1 : index;
    }

}
