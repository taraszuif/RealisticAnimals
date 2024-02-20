package me.zuif.rean.api.util;

import java.util.Objects;

public class TripleKey<K1, K2, K3> {
    public K1 key1;
    public K2 key2;
    public K3 key3;

    public TripleKey(K1 key1, K2 key2, K3 key3) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripleKey<?, ?, ?> tripleKey = (TripleKey<?, ?, ?>) o;
        if (!Objects.equals(key1, tripleKey.key1)) {
            return false;
        }
        if (!Objects.equals(key2, tripleKey.key2)) {
            return false;
        }

        return Objects.equals(key3, tripleKey.key3);
    }

    @Override
    public int hashCode() {
        int result = key1 != null ? key1.hashCode() : 0;
        result = 31 * result + (key2 != null ? key2.hashCode() : 0);
        result = 31 * result + (key3 != null ? key3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + key1 + ", " + key2 + ", " + key3 + "]";
    }
}