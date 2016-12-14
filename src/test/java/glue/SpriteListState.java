package glue;

import java.util.LinkedList;

import sprite.Sprite;
import spriteList.SpriteList;

public class SpriteListState {

    private final SpriteList spriteList;

    public SpriteListState() {
        spriteList = new SpriteList(null, null, 0, 0);
    }

    LinkedList<Sprite> getSpriteList() {
        return spriteList;
    }
}
