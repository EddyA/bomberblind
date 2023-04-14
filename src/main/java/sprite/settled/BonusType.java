package sprite.settled;

import java.util.Optional;

/**
 * Enum the different type of bonus.
 */
public enum BonusType {

    TYPE_BONUS_BOMB,
    TYPE_BONUS_FLAME,
    TYPE_BONUS_HEART,
    TYPE_BONUS_ROLLER;

    public static Optional<String> getLabel(BonusType bonusType) {
        Optional<String> label = Optional.empty();
        switch (bonusType) {
            case TYPE_BONUS_BOMB -> label = Optional.of("bonus_bomb");
            case TYPE_BONUS_FLAME -> label = Optional.of("bonus_flame");
            case TYPE_BONUS_HEART -> label = Optional.of("bonus_heart");
            case TYPE_BONUS_ROLLER -> label = Optional.of("bonus_roller");
        }
        return label;
    }
}
