package map.zelda;

import exceptions.InvalidPropertiesException;
import map.MapProperties;
import map.MapSettings;

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

    private int perSingleMutableObstacle; // the percentage of single mutable obstacle to place among available cases.
    private int perSingleImmutableObstacle; // the percentage of single immutable obstacle to place among available cases.
    private int perSingleDynamicPathway; // the percentage of dynamic pathway to place among available cases.

    private int nbBonusBomb; // the number of bonus bomb to place.
    private int nbBonusFlame; // the number of bonus flame to place.
    private int nbBonusHeart; // the number of bonus heart to place.
    private int nbBonusRoller; // the number of bonus roller to place.

    public ZeldaMapSetting(MapProperties mapConfiguration) throws InvalidPropertiesException {
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
            this.perSingleMutableObstacle = zeldaMapProperties.getMapElementPerSingleMutableObstacle();
            this.perSingleImmutableObstacle = zeldaMapProperties.getMapElementPerSingleImmutableObstacle();
            this.perSingleDynamicPathway = zeldaMapProperties.getMapElementPerSingleDynamicPathway();
            this.nbBonusBomb = zeldaMapProperties.getMapBonusNbBomb();
            this.nbBonusFlame = zeldaMapProperties.getMapBonusNbFlame();
            this.nbBonusHeart = zeldaMapProperties.getMapBonusNbHeart();
            this.nbBonusRoller = zeldaMapProperties.getMapBonusNbRoller();
        } else {
            throw new InvalidPropertiesException("the provided MapProperties is badly typed.");
        }
    }

    int getVerticalMargin() {
        return verticalMargin;
    }

    int getHorizontalMargin() {
        return horizontalMargin;
    }

    int getNbWood1() {
        return nbWood1;
    }

    int getNbWood2() {
        return nbWood2;
    }

    int getNbTree1() {
        return nbTree1;
    }

    int getNbTree2() {
        return nbTree2;
    }

    int getNbPuddle1() {
        return nbPuddle1;
    }

    int getNbPuddle2() {
        return nbPuddle2;
    }

    int getPerSingleMutableObstacle() {
        return perSingleMutableObstacle;
    }

    int getPerSingleImmutableObstacle() {
        return perSingleImmutableObstacle;
    }

    int getPerSingleDynamicPathway() {
        return perSingleDynamicPathway;
    }

    public int getNbBonusBomb() {
        return nbBonusBomb;
    }

    public int getNbBonusFlame() {
        return nbBonusFlame;
    }

    public int getNbBonusHeart() {
        return nbBonusHeart;
    }

    public int getNbBonusRoller() {
        return nbBonusRoller;
    }
}