package sprite;

import java.util.Optional;

public enum SpriteAction {

    ACTION_BREAKING,
    ACTION_DYING,
    ACTION_EXPLORING,
    ACTION_FLYING,
    ACTION_WAITING,
    ACTION_WALKING,
    ACTION_WINING;

    public static Optional<String> getLabel(SpriteAction spriteType) {
        Optional<String> label = Optional.empty();
        switch (spriteType) {
            case ACTION_BREAKING -> label = Optional.of("breaking");
            case ACTION_DYING -> label = Optional.of("dying");
            case ACTION_EXPLORING -> label = Optional.of("exploring");
            case ACTION_FLYING -> label = Optional.of("flying");
            case ACTION_WAITING -> label = Optional.of("waiting");
            case ACTION_WALKING -> label = Optional.of("walking");
            case ACTION_WINING -> label = Optional.of("wining");
        }
        return label;
    }
}
