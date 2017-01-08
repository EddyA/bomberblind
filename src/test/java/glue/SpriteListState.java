package glue;

import java.util.LinkedList;

import sprite.Sprite;
import sprite.SpriteType;
import spriteList.SpriteList;

public class SpriteListState {

    private final SpriteList spriteList;

    public SpriteListState() {
        spriteList = new SpriteList(null, null, 0, 0);
    }

    LinkedList<Sprite> getSpriteList() {
        return spriteList;
    }

    /**
     * Check if a sprite is in the sprite list according to its coordinates and its type.
     *
     * @param xMap       the sprite's abscissa
     * @param yMap       the sprite's ordinate
     * @param spriteType the sprite's type
     * @return true is the specified sprite is in the sprite list.
     */
    boolean isSpriteInSpriteList(int xMap, int yMap, SpriteType spriteType) {
        for (Sprite sprite : spriteList) {
            if (sprite.getxMap() == xMap &&
                    sprite.getyMap() == yMap &&
                    sprite.getSpriteType().equals(spriteType)) {
                return true;
            }
        }
        return false;
    }
}
