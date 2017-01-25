package sprite;

import java.util.Optional;

public enum SpriteAction {
    ACTION_BREAKING, ACTION_DYING, ACTION_FLYING, ACTION_WAITING, ACTION_WALKING, ACTION_WINING;

    public static Optional<String> getlabel(SpriteAction spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
        case ACTION_BREAKING: {
            label = Optional.of("breaking");
            break;
        }
        case ACTION_DYING: {
            label = Optional.of("dying");
            break;
        }
        case ACTION_FLYING: {
            label = Optional.of("flying");
            break;
        }
        case ACTION_WAITING: {
            label = Optional.of("waiting");
            break;
        }
        case ACTION_WALKING: {
            label = Optional.of("walking");
            break;
        }
        case ACTION_WINING: {
            label = Optional.of("wining");
            break;
        }
        }
        return label;
    }
}
