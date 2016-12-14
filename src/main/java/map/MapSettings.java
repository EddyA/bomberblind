package map;

/**
 * Abstract class of map settings.
 * Should at least define map dimensions.
 */
public abstract class MapSettings {

    private int mapWidth; // width of the map (expressed in MapPoint).
    private int mapHeight; // height of the map (expressed in MapPoint).

    protected MapSettings(MapProperties mapConfiguration) {
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
