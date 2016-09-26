package map.zelda;

import exceptions.InvalidMapConfigurationException;
import exceptions.InvalidMapPropertiesException;
import map.abstracts.MapProperties;
import map.abstracts.MapSettings;

/**
 * This class extends {@link MapSettings}.
 * Based on {@link ZeldaMapProperties}, it contains all the properties requiered to randomly generate a zelda map.
 */
public class ZeldaMapSetting extends MapSettings {

    private int verticalMargin; // vertical margin to put castles at a minimum of n cases (i.e n MapPoint) from the top/bottom of the map.
    private int horizontalMargin; // horizontal margin to put castles at exactly n cases (i.e n MapPoint) from the left/right sides of the map.

    private int nbWood1; // the number of Wood1 to place.
    private int nbWood2; // the number of Wood2 to place.
    private int nbTree1; // the number of Tree1 to place.
    private int nbTree2; // the number of Tree2 to place.
    private int nbPuddle1; // the number of puddle1 to place.
    private int nbPuddle2; // the number of puddle2 to place.

    private int perSingleMutable; // the percentage of single mutable to place among available cases.
    private int perSingleObstacle; // the percentage of single obstacle to place among available cases.
    private int perSingleDynPathway; // the percentage of dynamic pathway to place among available cases.

    public ZeldaMapSetting(MapProperties mapConfiguration) throws InvalidMapPropertiesException {
        super(mapConfiguration);
        if (mapConfiguration.getClass().getSimpleName().equals("ZeldaMapProperties")) {
            ZeldaMapProperties zeldaMapProperties = (ZeldaMapProperties) mapConfiguration;
            this.verticalMargin = zeldaMapProperties.getMapMarginVertical();
            this.horizontalMargin = zeldaMapProperties.getMapMarginHorizontal();
            this.nbWood1 = zeldaMapProperties.getMapElementNbWood1();
            this.nbWood2 = zeldaMapProperties.getMapElementNbWood2();
            this.nbTree1 = zeldaMapProperties.getMapElementNbTree1();
            this.nbTree2 = zeldaMapProperties.getMapElementNbTree2();
            this.nbPuddle1 = zeldaMapProperties.getMapElementNbPuddle1();
            this.nbPuddle2 = zeldaMapProperties.getMapElementNbPuddle2();
            this.perSingleMutable = zeldaMapProperties.getMapElementPerSingleMutable();
            this.perSingleObstacle = zeldaMapProperties.getMapElementPerSingleObstacle();
            this.perSingleDynPathway = zeldaMapProperties.getMapElementPerSingleDynPathway();
        } else {
            throw new InvalidMapPropertiesException("the provided MapProperties is badly typed.");
        }
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
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

    public int getPerSingleDynPathway() {
        return perSingleDynPathway;
    }
}