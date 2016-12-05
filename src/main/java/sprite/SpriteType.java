package sprite;

import java.util.Optional;

public enum SpriteType {
    BOMBER,
    ENEMY,
    BOMB,
    FLAME,
    CONCLUSION_FLAME;

    public static Optional<String> getlabel(SpriteType spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
        case BOMBER: {
            label = Optional.of("bomber");
            break;
        }
        case ENEMY: {
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
        case CONCLUSION_FLAME: {
            label = Optional.of("conclusion_flame");
            break;
        }
        }
        return label;
    }
}
