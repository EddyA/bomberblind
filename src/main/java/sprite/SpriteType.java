package sprite;

import java.util.Optional;

public enum SpriteType {
    TYPE_BOMB,
    TYPE_BOMBER,
    TYPE_BONUS_BOMB,
    TYPE_BONUS_FLAME,
    TYPE_BONUS_HEART,
    TYPE_BONUS_ROLLER,
    TYPE_BREAKING_ENEMY,
    TYPE_FLAME,
    TYPE_FLAME_END,
    TYPE_FLYING_NOMAD,
    TYPE_WALKING_ENEMY;

    public static Optional<String> getlabel(SpriteType spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
            case TYPE_BOMB: {
                label = Optional.of("bomb");
                break;
            }
            case TYPE_BOMBER: {
                label = Optional.of("bomber");
                break;
            }
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
            case TYPE_BREAKING_ENEMY: {
                label = Optional.of("breaking_enemy");
                break;
            }
            case TYPE_FLAME: {
                label = Optional.of("flame");
                break;
            }
            case TYPE_FLAME_END: {
                label = Optional.of("flame_end");
                break;
            }
            case TYPE_FLYING_NOMAD: {
                label = Optional.of("flying_nomad");
                break;
            }
            case TYPE_WALKING_ENEMY: {
                label = Optional.of("walking_enemy");
                break;
            }
        }
        return label;
    }
}
