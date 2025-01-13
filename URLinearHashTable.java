/*
 * @author [Eray Bozoglu]
 * @netid [ebozoglu]
 * @course CSC172 - Data Structures and Algorithms
 * @project Project 3: Street Mapping
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class URLinearHashTable<Key, Value> extends UR_HashTable<Key, Value> {

    private static final int INIT_CAPACITY = 5 ;
    private final Key DUMMY_KEY = (Key) new DummyKey();

    protected int n; // size of the data set
    protected int m ; // size of the hash table
    protected Key[] keys;
    Value[] vals;
    int inserts, collisions;

    private class URLinearHashTableIterator implements Iterator<Key> {
        int curr = 0;
        int iterated = 0;

        @Override
        public boolean hasNext() {
            return curr < m && n != 0 && iterated < n;
        }

        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            while (curr < m) {
                if (keys[curr] != null && !keys[curr].equals(DUMMY_KEY)) {
                    Key nextKey = keys[curr];
                    curr++;
                    iterated++;
                    return nextKey;
                } else {
                    curr++;
                }
            }

            throw new NoSuchElementException();
        }
    }

    // Dummy variable to replace deleted keys with. Keeps track of empty-after-deletion vs empty-since-start.
    private static class DummyKey {
        private DummyKey() {}
    }

    public URLinearHashTable() {
        n = 0;
        m = INIT_CAPACITY;
        inserts = 0;
        collisions = 0;

        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }

    public URLinearHashTable(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }

        n = 0;
        m = cap;
        inserts = 0;
        collisions = 0;

        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }


    public void put (Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }

        int bucket = hash(key);
        int initialBucket = bucket;

        while (keys[bucket] != null) {
            // Check if the key already exists and update the value
            if (keys[bucket].equals(key)) {
                vals[bucket] = val;
                return;
            }

            bucket = (bucket + 1) % m;

            // If we are back to the initial bucket, table is full
            if (bucket == initialBucket) {
                resize(m * 2);
                put(key, val);
                return;
            }

            collisions++;
        }

        keys[bucket] = key;
        vals[bucket] = val;
        n++;
    }

    public Value get (Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }

        int bucket = hash(key);
        int initialBucket = bucket;

        while (keys[bucket] != null) {
            if (!keys[bucket].equals(DUMMY_KEY) && keys[bucket].equals(key)) {
                return vals[bucket];
            }

            bucket = (bucket + 1) % m;

            // We are back to the initial bucket, key is not found
            if (bucket == initialBucket) {
                return null;
            }
        }

        return null; // We hit a null bucket, key is not found
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }

        int bucket = hash(key);
        int initialBucket = bucket;

        while (keys[bucket] != null) {
            if (keys[bucket].equals(key)) {
                keys[bucket] = DUMMY_KEY;
                vals[bucket] = null;
                n--;
                return;
            }

            bucket = (bucket + 1) % m;

            // We are back to the initial bucket, key is not found
            if (bucket == initialBucket) {
                return;
            }
        }
    }

    public int size() {
        return m;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(Key key){
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }

        int bucket = hash(key);
        int initialBucket = bucket;

        while (keys[bucket] != null) {
            if (keys[bucket].equals(key)) {
                return true;
            }

            bucket = (bucket + 1) % m;

            // We are back to the initial bucket, key is not found
            if (bucket == initialBucket) {
                return false;
            }
        }

        return false; // We hit a null bucket, key is not found
    }

    public Iterable<Key> keys() {
        return new Iterable<Key>() {
            public Iterator<Key> iterator() {
                return new URLinearHashTableIterator();
            }
        };
    }


    private int hash(Key key) {
        return Math.abs(key.hashCode()) % m;
    }

    private int hash(Key key, int newSize) {
        return Math.abs(key.hashCode()) % newSize;
    }

    private void resize(int m) {
        int newSize = getNextPrime(m);

        Key[] newKeys = (Key[]) new Object[newSize];
        Value[] newVals = (Value[]) new Object[newSize];

        for (Key key : keys()) {
            Value val = get(key);
            putDuringResize(key, val, newKeys, newVals, newSize);
        }

        this.m = newSize;
        keys = newKeys;
        vals = newVals;
    }

    private void putDuringResize(Key key, Value val, Key[] newKeys, Value[] newVals, int newSize) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null.");
        }
        int bucket = hash(key,newSize);

        while (newKeys[bucket] != null) {
            bucket = (bucket + 1) % newSize;
        }

        newKeys[bucket] = key;
        newVals[bucket] = val;
    }

    /*
     * Algorithm optimization based on:
     * Source: "Optimal way to find next prime number" (2017)
     * URL: https://stackoverflow.com/questions/47407251/optimal-way-to-find-next-prime-number-java
     */

    private int getNextPrime(int m) {
        boolean isPrime;
        m++;
        while(true){
            int l = (int) Math.sqrt(m);
            isPrime = true;
            for(int i = 2; i <= l; i ++){
                if (m % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if(isPrime)
                return m;
            else{
                m++;
            }
        }
    }
}
