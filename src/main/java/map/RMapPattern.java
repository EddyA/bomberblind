package map;

import java.awt.*;

public class RMapPattern {

    // images information.
    private Image[] imageArray;
    private int width;
    private int height;

    private boolean isPathway;  // is a pathway object?
    private boolean isMutable; // is a mutable object?

    private String name;

    public RMapPattern(Image[] imageArray, int width, int height, boolean isPathway, boolean isMutable, String name) {
        this.imageArray = imageArray;
        this.width = width;
        this.height = height;
        this.isPathway = isPathway;
        this.isMutable = isMutable;
        this.name = name;
    }

    public Image[] getImageArray() {
        return imageArray;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPathway() {
        return isPathway;
    }

    public boolean isMutable() {
        return isMutable;
    }

    public String getName() {
        return name;
    }
}
