package map;

import java.io.IOException;

import exceptions.InvalidMapConfigurationException;
import utils.MapProperties;

/**
 * Define the map size and the number of elements to place onto it.
 */
public class RMapSetting {

    private int mapWidth; // widht of the map (expressed in RMapPoint).
    private int mapHeight; // height of the map (expressed in RMapPoint).

    private int nbWood1; // the number of Wood1 to place.
    private int nbWood2; // the number of Wood2 to place.
    private int nbTree1; // the number of Tree1 to place.
    private int nbTree2; // the number of Tree2 to place.
    private int nbPuddle1; // the number of puddle1 to place.
    private int nbPuddle2; // the number of puddle2 to place.

    private int perSingleMutable; // the percentage of single mutable to place among available cases.
    private int perSingleObstacle; // the percentage of single obstacle to place among available cases.
    private int perSingleFlowerPathway; // the percentage of single flower pathway to place among available cases.

    RMapSetting(MapProperties mapConfiguration) throws IOException, InvalidMapConfigurationException {
        this.mapWidth = mapConfiguration.getMapSizeWidth();
        this.mapHeight = mapConfiguration.getMapSizeHeight();
        this.nbWood1 = mapConfiguration.getMapElementNbWood1();
        this.nbWood2 = mapConfiguration.getMapElementNbWood2();
        this.nbTree1 = mapConfiguration.getMapElementNbTree1();
        this.nbTree2 = mapConfiguration.getMapElementNbTree2();
        this.nbPuddle1 = mapConfiguration.getMapElementNbPuddle1();
        this.nbPuddle2 = mapConfiguration.getMapElementNbPuddle2();
        this.perSingleMutable = mapConfiguration.getMapElementPerSingleMutable();
        this.perSingleObstacle = mapConfiguration.getMapElementPerSingleObstacle();
        this.perSingleFlowerPathway = mapConfiguration.getMapElementPerSingleDynPathway();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getNbWood1() {
        return nbWood1;
    }

    public int getNbWood2() {
        return nbWood2;
    }

    public int getNbTree1() {
        return nbTree1;
    }

    public int getNbTree2() {
        return nbTree2;
    }

    public int getNbPuddle1() {
        return nbPuddle1;
    }

    public int getNbPuddle2() {
        return nbPuddle2;
    }

    public int getPerSingleMutable() {
        return perSingleMutable;
    }

    public int getPerSingleObstacle() {
        return perSingleObstacle;
    }

    public int getPerSingleFlowerPathway() {
        return perSingleFlowerPathway;
    }
}