package map;

import lombok.Getter;

/**
 * Abstract class of map settings.
 * Should at least define map dimensions.
 */
public abstract class MapSettings {

    @Getter
    private final int mapWidth; // width of the map (expressed in MapPoint).
    @Getter
    private final int mapHeight; // height of the map (expressed in MapPoint).

    protected MapSettings(MapProperties mapConfiguration) {
        this.mapWidth = mapConfiguration.getMapSizeWidth();
        this.mapHeight = mapConfiguration.getMapSizeHeight();
    }
}
