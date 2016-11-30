package glue;

import static org.mockito.Matchers.anyInt;

import java.util.LinkedList;

import sprite.abstracts.Sprite;
import spriteList.SpriteList;

public class SpriteListState {

    private final SpriteList spriteList;

    public SpriteListState() {
        spriteList = new SpriteList(null, null, 0, 0);
    }

    public LinkedList<Sprite> getSpriteList() {
        return spriteList;
    }
}
