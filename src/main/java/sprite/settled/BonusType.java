package sprite.settled;

import java.util.Optional;

public enum BonusType {
    TYPE_BONUS_BOMB,
    TYPE_BONUS_FLAME,
    TYPE_BONUS_HEART,
    TYPE_BONUS_ROLLER;

    public static Optional<String> getlabel(BonusType bonusType) {
        Optional<String> label = Optional.empty();
        switch (bonusType) {
            case TYPE_BONUS_BOMB: {
                label = Optional.of("bonus_bomb");
                break;
            }
            case TYPE_BONUS_FLAME: {
                label = Optional.of("bonus_flame");
                break;
            }
            case TYPE_BONUS_HEART: {
                label = Optional.of("bonus_heart");
                break;
            }
            case TYPE_BONUS_ROLLER: {
                label = Optional.of("bonus_roller");
                break;
            }
        }
        return label;
    }
}
