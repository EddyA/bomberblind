package sprite;

import java.util.Optional;

public enum SpriteType {
    BOMBER,
    ENEMY_A,
    ENEMY_B,
    BOMB,
    FLAME,
    FLAME_END;

    public static Optional<String> getlabel(SpriteType spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
        case BOMBER: {
            label = Optional.of("bomber");
            break;
        }
        case ENEMY_A: {
            label = Optional.of("enemy");
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
