package map.zelda;

import exceptions.InvalidPropertiesException;
import lombok.Getter;
import map.MapProperties;
import map.MapSettings;

/**
 * This class extends {@link MapSettings}. Based on {@link ZeldaMapProperties}, it contains all the properties required
 * to randomly generate a zelda map.
 */
@Getter
public class ZeldaMapSetting extends MapSettings {

    private final int verticalMargin; // vertical margin to put entrance/exit at a minimum of n cases (i.e n MapPoint) from the top/bottom of the map.
    private final int horizontalMargin; // horizontal margin to put entrance/exit at exactly n cases (i.e n MapPoint) from the left/right sides of the map.

    private final int nbGreenTree; // the number of green tree to place.
    private final int nbOrchard; // the number of orchard to place.
    private final int nbRedTree; // the number of red tree to place.
    private final int nbStatue; // the number of statue to place.
    private final int nbTrough; // the number of trough to place.
    private final int nbYellowTree; // the number of yellow tree to place.
    private final int nbPathway; // the number of pathway to place.

    private final int perSingleMutableObstacle; // the percentage of single mutable obstacle to place among available cases.
    private final int perSingleImmutableObstacle; // the percentage of single immutable obstacle to place among available cases.
    private final int perDecoratedSinglePathway; // the percentage of decorated elements to place among single pathways.
    private final int perDynamicSinglePathway; // the percentage of dynamic elements to place among decorated single pathways.

    private final int nbBonusBomb; // the number of bonus bomb to place.
    private final int nbBonusFlame; // the number of bonus flame to place.
    private final int nbBonusHeart; // the number of bonus heart to place.
    private final int nbBonusRoller; // the number of bonus roller to place.

    public ZeldaMapSetting(MapProperties mapConfiguration) throws InvalidPropertiesException {
        super(mapConfiguration);
        if (mapConfiguration instanceof ZeldaMapProperties zeldaMapProperties) {
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
}
