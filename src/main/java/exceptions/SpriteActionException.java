package exceptions;

import sprite.SpriteAction;

public class SpriteActionException extends RuntimeException {

    public SpriteActionException(SpriteAction spriteAction) {
        super("'" + SpriteAction.getLabel(spriteAction).orElse("n/a") + "' action is not allowed here.");
    }

    public SpriteActionException(SpriteAction spriteAction, Class<?> clazz) {
        super("'" + SpriteAction.getLabel(spriteAction).orElse("n/a") + "' action is not allowed for '"
            + clazz.getSimpleName() + "'");
    }
}

