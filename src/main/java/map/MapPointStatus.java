package map;


import java.util.BitSet;

/**
 * List the different status of a MapPoint.
 */
public class MapPointStatus {

    private static final int INDEX_STATUS_IS_AVAILABLE = 0;
    private static final int INDEX_STATUS_IS_PATHWAY = 1;
    private static final int INDEX_STATUS_IS_MUTABLE = 2;
    private static final int INDEX_STATUS_IS_ENTRANCE = 3;
    private static final int INDEX_STATUS_IS_EXIT = 4;

    private final BitSet bitSet = new BitSet(5); // status as bitset.

    public void setAvailable(boolean isAvailable) {
        bitSet.set(INDEX_STATUS_IS_AVAILABLE, isAvailable);
    }

    public void setPathway(boolean isPathway) {
        bitSet.set(INDEX_STATUS_IS_PATHWAY, isPathway);
    }

    public void setMutable(boolean isMutable) {
        bitSet.set(INDEX_STATUS_IS_MUTABLE, isMutable);
    }

    public void setEntrance(boolean isEntrance) {
        bitSet.set(INDEX_STATUS_IS_ENTRANCE, isEntrance);
    }

    public void setExit(boolean isExit) {
        bitSet.set(INDEX_STATUS_IS_EXIT, isExit);
    }

    public boolean isAvailable() {
        return bitSet.get(INDEX_STATUS_IS_AVAILABLE);
    }

    public boolean isPathway() {
        return bitSet.get(INDEX_STATUS_IS_PATHWAY);
    }

    public boolean isMutable() {
        return bitSet.get(INDEX_STATUS_IS_MUTABLE);
    }

    public boolean isEntrance() {
        return bitSet.get(INDEX_STATUS_IS_ENTRANCE);
    }

    public boolean isExit() {
        return bitSet.get(INDEX_STATUS_IS_EXIT);
    }

    public void init() {
        bitSet.clear();
        bitSet.set(INDEX_STATUS_IS_AVAILABLE, true);
    }
}
