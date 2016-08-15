package utils;

public class Tuple2<U, V> {

    private U first; // first element of that tuple.
    private V second; // second element of that tuple.

    /**
     * Constructs a new pair with the given values.
     *
     * @param first  the first element
     * @param second the second element
     */
    public Tuple2(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
