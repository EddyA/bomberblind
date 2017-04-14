package utils;

/**
 * This call allows playing with triple object.
 *
 * @param <U> type of the first element of the pair
 * @param <V> type of the second element of the pair
 * @param <W> type of the third element of the pair
 */
public class Tuple3<U, V, W> {

    private U first; // first element of that pair.
    private V second; // second element of that pair.
    private W third; // third element of that pair.

    /**
     * Constructs a new pair with the given values.
     *
     * @param first  the first element
     * @param second the second element
     * @param third  the third element
     */
    public Tuple3(U first, V second, W third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public U getFirst() {
        return first;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public W getThird() {
        return third;
    }

    public void setThird(W third) {
        this.third = third;
    }
}
