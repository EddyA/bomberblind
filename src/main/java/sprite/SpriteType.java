package sprite;

import java.util.Optional;

public enum SpriteType {

    TYPE_SPRITE_BOMB,
    TYPE_SPRITE_BOMBER,
    TYPE_SPRITE_BONUS,
    TYPE_SPRITE_BREAKING_ENEMY,
    TYPE_SPRITE_EXPLORING_ENEMY,
    TYPE_SPRITE_FLAME,
    TYPE_SPRITE_FLAME_END,
    TYPE_SPRITE_FLYING_NOMAD,
    TYPE_SPRITE_SPARKLE,
    TYPE_SPRITE_WALKING_ENEMY;

    public static Optional<String> getLabel(SpriteType spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
            case TYPE_SPRITE_BOMB -> label = Optional.of("bomb");
            case TYPE_SPRITE_BOMBER -> label = Optional.of("bomber");
            case TYPE_SPRITE_BONUS -> label = Optional.of("bonus");
            case TYPE_SPRITE_BREAKING_ENEMY -> label = Optional.of("breaking_enemy");
            case TYPE_SPRITE_EXPLORING_ENEMY -> label = Optional.of("exploring_enemy");
            case TYPE_SPRITE_FLAME -> label = Optional.of("flame");
            case TYPE_SPRITE_FLAME_END -> label = Optional.of("flame_end");
            case TYPE_SPRITE_FLYING_NOMAD -> label = Optional.of("flying_nomad");
            case TYPE_SPRITE_SPARKLE -> label = Optional.of("sparkle");
            case TYPE_SPRITE_WALKING_ENEMY -> label = Optional.of("walking_enemy");
        }
        return label;
    }
}
