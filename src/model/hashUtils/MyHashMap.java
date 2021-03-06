package model.hashUtils;

import java.util.*;

public class MyHashMap <K, V> extends HashMap<K, V> {
    private ArrayList<HashNode<K, V>> buckets;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 10;
    public MyHashMap() {
        // initial capacity
        this(INITIAL_CAPACITY);
    }

    public MyHashMap(int capacity) {
        this.buckets = new ArrayList<>(capacity);
        initList(this.buckets, capacity);
    }

    private void initList(List ls,int capacity) {
        for (int i = 0; i < capacity; i++) {
            ls.add(null);
        }
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
        for (HashNode<K, V> hashNode: this.buckets) {
            while (hashNode != null) {
                if (hashNode.getValue().equals(value)) {
                    return true;
                }
                hashNode = hashNode.getNext();
            }
        }
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
        this.size++;
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
            this.buckets = new ArrayList<>(numBuckets);
            initList(this.buckets, numBuckets);
            this.size = 0;
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
    public V remove(Object o) {
        K key;
        try {
            key = (K) o;
        }catch (ClassCastException e) {
            return null;
        }
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> hashNode = this.buckets.get(bucketIndex);
        HashNode<K, V> prevNode = null;
        while (hashNode != null) {
            if (hashNode.getKey().equals(key) &&
                    hashNode.getHashCode() == hashCode)
                break;
            prevNode = hashNode;
            hashNode = hashNode.getNext();
        }
        // if key not found
        if (hashNode == null) {
            return null;
        }
        this.size--;
        // remove the key
        if (prevNode != null) {
            prevNode.setNext(hashNode.getNext());
        } else {
            this.buckets.set(bucketIndex, hashNode.getNext());
        }
        return hashNode.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // does not need implementation for this project!
    }

    @Override
    public void clear() {
        this.buckets = new ArrayList<>(INITIAL_CAPACITY);
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (HashNode<K, V> hashNode: this.buckets) {
            while (hashNode != null) {
                keys.add(hashNode.getKey());
                hashNode = hashNode.getNext();
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (HashNode<K, V> hashNode: this.buckets) {
            while (hashNode != null) {
                values.add(hashNode.getValue());
                hashNode = hashNode.getNext();
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (HashNode<K, V> hashNode: this.buckets) {
            while (hashNode != null) {
                entrySet.add(hashNode);
                hashNode = hashNode.getNext();
            }
        }
        return entrySet;
    }

    private int hashCode(K key) {
        return Objects.hashCode(key);
    }
    private int getBucketIndex(K key) {
        int index = hashCode(key) % this.buckets.size();
        return index < 0 ? index * -1 : index;
    }

}
