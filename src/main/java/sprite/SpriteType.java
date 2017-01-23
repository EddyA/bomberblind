package sprite;

import java.util.Optional;

public enum SpriteType {
    BOMBER,
    WALKING_ENEMY,
    BREAKING_ENEMY,
    BOMB,
    FLAME,
    FLAME_END,
    BIRD;

    public static Optional<String> getlabel(SpriteType spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
            case BOMBER: {
                label = Optional.of("bomber");
                break;
            }
            case WALKING_ENEMY: {
                label = Optional.of("walking_enemy");
                break;
            }
            case BREAKING_ENEMY: {
                label = Optional.of("breaking_enemy");
                break;
            }
            case BOMB: {
                label = Optional.of("bomb");
                break;
            }
            case FLAME: {
                label = Optional.of("flame");
                break;
            }
            case FLAME_END: {
                label = Optional.of("flame_end");
                break;
            }
        }
        return label;
    }
}
