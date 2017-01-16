package spriteList.ctrl;

import exceptions.CannotPlaceEnemyOnMapException;
import map.MapPoint;
import sprite.nomad.*;
import spriteList.SpriteList;

import java.util.List;
import java.util.Random;

import static images.ImagesLoader.IMAGE_SIZE;
import static spriteList.ctrl.AddingMethods.addBreakingEnemy;
import static spriteList.ctrl.AddingMethods.addWalkingEnemy;

public class GenerationMethodes {

    /**
     * Place a certain number of elements of a certain type of enemy.
     *
     * @param EnemyType   the type of enemy to place
     * @param nbElt       the number of elements to place
     * @param spriteList  the list into which adding the enemy
     * @param emptyPtList the list of empty points (available points to place the enemies)
     * @throws CannotPlaceEnemyOnMapException if a sprite cannot be placed on map
     */
    public static void randomlyPlaceEnemy(SpriteList spriteList, EnemyType EnemyType, int nbElt,
                                          List<MapPoint> emptyPtList) throws CannotPlaceEnemyOnMapException {

        for (int i = 0; i < nbElt; i++) {
            if (emptyPtList.isEmpty()) {
                throw new CannotPlaceEnemyOnMapException("cannot (create) and place a sprite on map, "
                        + "the list of empty point is empty.");
            }
            Random R = new Random(); // randomly get a point.
            int caseIdx = Math.abs(R.nextInt(emptyPtList.size()));
            int xMap = emptyPtList.get(caseIdx).getColIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;
            int yMap = emptyPtList.get(caseIdx).getRowIdx() * IMAGE_SIZE + IMAGE_SIZE / 2;

            // create the enemy.
            switch (EnemyType) {
                case CLOAKED_SKELETON: {
                    addWalkingEnemy(spriteList, new CloakedSkeleton(xMap, yMap));
                    break;
                }
                case MECA_ANGEL: {
                    addWalkingEnemy(spriteList, new MecaAngel(xMap, yMap));
                    break;
                }
                case MUMMY: {
                    addWalkingEnemy(spriteList, new Mummy(xMap, yMap));
                    break;
                }
                case MINOTOR: {
                    addBreakingEnemy(spriteList, new Minotor(xMap, yMap));
                    break;
                }
            }
            emptyPtList.remove(caseIdx); // remove the current point from the list of empty points.
        }
    }
}
