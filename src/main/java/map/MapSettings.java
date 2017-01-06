package map;

/**
 * Abstract class of map settings.
 * Should at least define map dimensions.
 */
public abstract class MapSettings {

    private final int mapWidth; // width of the map (expressed in MapPoint).
    private final int mapHeight; // height of the map (expressed in MapPoint).

    public MapSettings(MapProperties mapConfiguration) {
        this.mapWidth = mapConfiguration.getMapSizeWidth();
        this.mapHeight = mapConfiguration.getMapSizeHeight();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
