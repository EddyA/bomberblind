package sprite.settled;

import java.util.HashMap;
import java.util.Map;
import utils.Tuple2;

/**
 * Handle a collection of bonus.
 */
public class BonusBundle {

    public static final int DEFAULT_NB_BONUS_BOMB = 1;
    public static final int DEFAULT_NB_BONUS_FLAME = 1;
    public static final int DEFAULT_NB_BONUS_HEART = 5;
    public static final int DEFAULT_NB_BONUS_ROLLER = 1;

    /**
     * Map of bonus with:
     * - key: the type of bonus
     * - value: Tuple2 with:
     * --- 1st value: initial value (i.e. default value)
     * --- 2nd value: current value (i.e. default + collected values)
     */
    private final Map<BonusType, Tuple2<Integer, Integer>> bonusBundle;

    public Map<BonusType, Tuple2<Integer, Integer>> getBonusBundle() {
        return bonusBundle;
    }

    public BonusBundle() {
        this.bonusBundle = new HashMap<>();
        bonusBundle.put(BonusType.TYPE_BONUS_BOMB, new Tuple2<>(DEFAULT_NB_BONUS_BOMB, DEFAULT_NB_BONUS_BOMB));
        bonusBundle.put(BonusType.TYPE_BONUS_FLAME, new Tuple2<>(DEFAULT_NB_BONUS_FLAME, DEFAULT_NB_BONUS_FLAME));
        bonusBundle.put(BonusType.TYPE_BONUS_HEART, new Tuple2<>(DEFAULT_NB_BONUS_HEART, DEFAULT_NB_BONUS_HEART));
        bonusBundle.put(BonusType.TYPE_BONUS_ROLLER, new Tuple2<>(DEFAULT_NB_BONUS_ROLLER, DEFAULT_NB_BONUS_ROLLER));
    }

    /**
     * Get the number of bonus typed 'bonusType'.
     *
     * @param bonusType the bonus type
     * @return the number of bonus typed 'bonusType'
     */
    public int getBonus(BonusType bonusType) {
        Tuple2<Integer, Integer> bonusTuple = bonusBundle.get(bonusType);
        if (bonusTuple != null) {
            return bonusTuple.getSecond();
        } else {
            return 0;
        }
    }

    /**
     * Set the number of bonus typed 'bonusType'.
     *
     * @param bonusType the bonus type
     * @param nbBonus   the number of bonus typed 'bonusType'
     */
    public void setBonus(BonusType bonusType, int nbBonus) {
        Tuple2<Integer, Integer> bonusTuple = bonusBundle.get(bonusType);
        if (bonusTuple != null) {
            bonusTuple.setSecond(nbBonus);
        } else {
            bonusBundle.put(bonusType, new Tuple2<>(0, nbBonus));
        }
    }

    /**
     * Get the number of collected bonus with:
     * - key: the type of bonus
     * - value: the number of collected bonus
     * <p>
     * Note: this function does not take into account heart bonus.
     */
    public Map<BonusType, Integer> getCollectedBonus() {
        Map<BonusType, Integer> collectedBonus = new HashMap<>();
        for (Map.Entry<BonusType, Tuple2<Integer, Integer>> bonusEntry : bonusBundle.entrySet()) {
            if (bonusEntry.getKey() != BonusType.TYPE_BONUS_HEART) {
                collectedBonus.put(bonusEntry.getKey(), bonusEntry.getValue().getSecond() - bonusEntry.getValue().getFirst());
            }
        }
        return collectedBonus;
    }

    /**
     * Reset bonus with default values.
     * <p>
     * Note: this function does not take into account heart bonus.
     */
    public void resetBonus() {
        for (Map.Entry<BonusType, Tuple2<Integer, Integer>> bonusEntry : bonusBundle.entrySet()) {
            if (bonusEntry.getKey() != BonusType.TYPE_BONUS_HEART) {
                bonusEntry.getValue().setSecond(bonusEntry.getValue().getFirst());
            }
        }
    }
}
