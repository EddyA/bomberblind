package spriteList.ctrl;

import static images.ImagesLoader.IMAGE_SIZE;
import static spriteList.ctrl.AddingMethods.addCloakedSkeleton;
import static spriteList.ctrl.AddingMethods.addMecaAngel;
import static spriteList.ctrl.AddingMethods.addMummy;

import java.util.List;
import java.util.Random;

import exceptions.CannotPlaceSpriteOnMapException;
import map.MapPoint;
import sprite.nomad.EnemyType;
import spriteList.SpriteList;

public class GenerationMethodes {

    /**
     * Place a certain number of elements of a certain type of simple enemy.
     *
     * @param EnemyType the type of simple enemy to place
     * @param nbElt the number of elements to place
     * @param spriteList the list into which adding the enemy
     * @param emptyPtList the list of empty points (available points to place the enemies)
     * @throws CannotPlaceSpriteOnMapException if a sprite cannot be placed on map
     */
    public static void randomlyPlaceSimpleEnemy(SpriteList spriteList, EnemyType EnemyType, int nbElt,
            List<MapPoint> emptyPtList) throws CannotPlaceSpriteOnMapException {

        for (int i = 0; i < nbElt; i++) {
            if (emptyPtList.isEmpty()) {
                throw new CannotPlaceSpriteOnMapException("cannot (create) and place a sprite on map, "
                        + "the list of empty point is empty.");
            }
            Random R = new Random(); // randomly get a point.
            int caseIdx = Math.abs(R.nextInt(emptyPtList.size()));
            int xMap = emptyPtList.get(caseIdx).getColIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;
            int yMap = emptyPtList.get(caseIdx).getRowIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;

            // create the enemy.
            switch (EnemyType) {
            case CLOAKED_SKELETON: {
                addCloakedSkeleton(spriteList, xMap, yMap);
                break;
            }
            case MECA_ANGEL: {
                addMecaAngel(spriteList, xMap, yMap);
                break;
            }
            case MUMMY: {
                addMummy(spriteList, xMap, yMap);
                break;
            }
            }
            emptyPtList.remove(caseIdx); // remove the current point from the list of empty points.
        }
    }
}
