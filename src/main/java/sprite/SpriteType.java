package sprite;

import java.util.Optional;

public enum SpriteType {
    TYPE_BOMB,
    TYPE_BOMBER,
    TYPE_BONUS,
    TYPE_BREAKING_ENEMY,
    TYPE_EXPLORING_ENEMY,
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
            case TYPE_BONUS: {
                label = Optional.of("bonus");
                break;
            }
            case TYPE_BREAKING_ENEMY: {
                label = Optional.of("breaking_enemy");
                break;
            }
            case TYPE_EXPLORING_ENEMY: {
                label = Optional.of("exploring_enemy");
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
