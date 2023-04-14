package map;

import java.awt.Image;

/**
 * @param imageArray images information.
 * @param isPathway  is a pathway pattern?
 * @param isMutable  is a mutable pattern?
 * @param isEntrance is an entrance?
 * @param isExit     is an exit?
 */
public record MapPattern(Image[] imageArray,
                         int width,
                         int height,
                         boolean isPathway,
                         boolean isMutable,
                         boolean isEntrance,
                         boolean isExit,
                         String name) {

}
