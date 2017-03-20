package map.zelda;

import exceptions.InvalidPropertiesException;
import map.MapProperties;
import map.MapSettings;

/**
 * This class extends {@link MapSettings}.
 * Based on {@link ZeldaMapProperties}, it contains all the properties requiered to randomly generate a zelda map.
 */
public class ZeldaMapSetting extends MapSettings {

    private int verticalMargin; // vertical margin to put entrance/exit at a minimum of n cases (i.e n MapPoint) from the top/bottom of the map.
    private int horizontalMargin; // horizontal margin to put entrance/exit at exactly n cases (i.e n MapPoint) from the left/right sides of the map.

    private int nbGreenTree; // the number of green tree to place.
    private int nbOrchard; // the number of orchard to place.
    private int nbRedTree; // the number of red tree to place.
    private int nbStatue; // the number of statue to place.
    private int nbTrough; // the number of trough to place.
    private int nbYellowTree; // the number of yellow tree to place.
    private int nbPathway; // the number of pathway to place.

    private int perSingleMutableObstacle; // the percentage of single mutable obstacle to place among available cases.
    private int perSingleImmutableObstacle; // the percentage of single immutable obstacle to place among available cases.
    private int perDecoratedSinglePathway; // the percentage of decorated elements to place among single pathways.
    private int perDynamicSinglePathway; // the percentage of dynamic elements to place among decorated single pathways.

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
            this.nbGreenTree = zeldaMapProperties.getMapElementNbGreenWood();
            this.nbOrchard = zeldaMapProperties.getMapElementNbOrchard();
            this.nbRedTree = zeldaMapProperties.getMapElementNbRedTree();
            this.nbStatue = zeldaMapProperties.getMapElementNbStatue();
            this.nbTrough = zeldaMapProperties.getMapElementNbTrough();
            this.nbYellowTree = zeldaMapProperties.getMapElementNbYellowTree();
            this.nbPathway = zeldaMapProperties.getMapElementNbPathway();
            this.perSingleMutableObstacle = zeldaMapProperties.getMapElementPerSingleMutableObstacle();
            this.perSingleImmutableObstacle = zeldaMapProperties.getMapElementPerSingleImmutableObstacle();
            this.perDecoratedSinglePathway = zeldaMapProperties.getMapElementPerDecoratedSinglePathway();
            this.perDynamicSinglePathway = zeldaMapProperties.getMapElementPerDynamicSinglePathway();
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

    public int getNbGreenTree() {
        return nbGreenTree;
    }

    public int getNbOrchard() {
        return nbOrchard;
    }

    public int getNbRedTree() {
        return nbRedTree;
    }

    public int getNbStatue() {
        return nbStatue;
    }

    public int getNbTrough() {
        return nbTrough;
    }

    public int getNbYellowTree() {
        return nbYellowTree;
    }

    public int getNbPathway() {
        return nbPathway;
    }

    int getPerSingleMutableObstacle() {
        return perSingleMutableObstacle;
    }

    int getPerSingleImmutableObstacle() {
        return perSingleImmutableObstacle;
    }

    public int getPerDecoratedSinglePathway() {
        return perDecoratedSinglePathway;
    }

    int getPerDynamicSinglePathway() {
        return perDynamicSinglePathway;
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