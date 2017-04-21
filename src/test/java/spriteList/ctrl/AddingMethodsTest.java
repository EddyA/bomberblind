package spriteList.ctrl;

import images.ImagesLoader;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.Sprite;
import sprite.SpriteType;
import sprite.nomad.*;
import sprite.settled.*;
import utils.Direction;
import utils.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AddingMethodsTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void addBombShouldNotAddTheBomb() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;
        int flameSize = 3;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int caseRowIdx = 5;
        int caseColIdx = 6;

        // it is a bombing case.
        mapPointMatrix[caseRowIdx][caseColIdx].setBombing(true);
        Bomb bomb = new Bomb(caseRowIdx, caseColIdx, flameSize);
        AddingMethods.addBomb(spriteList, mapPointMatrix, bomb);
        assertThat(spriteList.contains(bomb)).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();

        // it is a burning case.
        mapPointMatrix[caseRowIdx][caseColIdx].setBombing(false);
        mapPointMatrix[caseRowIdx][caseColIdx].addFlame();
        assertThat(AddingMethods.addBomb(spriteList, mapPointMatrix, bomb)).isFalse();
        assertThat(spriteList.contains(bomb)).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();
    }

    @Test
    public void addBombShouldAddTheBombToTheListOfSpriteAndUpdateTheCaseStatus() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;
        int flameSize = 3;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int caseRowIdx = 5;
        int caseColIdx = 6;
        mapPointMatrix[caseRowIdx][caseColIdx].setBombing(false);
        mapPointMatrix[caseRowIdx][caseColIdx].setNbFlames(0);

        // test.
        Bomb bomb = new Bomb(caseRowIdx, caseColIdx, flameSize);
        assertThat(AddingMethods.addBomb(spriteList, mapPointMatrix, bomb)).isTrue();
        assertThat(spriteList.contains(bomb)).isTrue();
        assertThat(mapPointMatrix[caseRowIdx][caseColIdx].isBombing()).isEqualTo(true);
    }

    @Test
    public void addBomberShouldAddTheBomberToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        Bomber bomber = new BlueBomber(1, 2);
        AddingMethods.addBomber(spriteList, bomber);

        // test.
        assertThat(spriteList.contains(bomber)).isTrue();
    }

    @Test
    public void addBonusShouldNotAddTheBonus() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int caseRowIdx = 5;
        int caseColIdx = 6;

        BonusFlame bonusFlame = new BonusFlame(caseRowIdx, caseColIdx);

        // it is not a pathway.
        mapPointMatrix[caseRowIdx][caseColIdx].setPathway(false);
        AddingMethods.addBonus(spriteList, mapPointMatrix, bonusFlame);
        assertThat(spriteList.contains(bonusFlame)).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();

        // it is a bonusing case.
        mapPointMatrix[caseRowIdx][caseColIdx].setPathway(true);
        mapPointMatrix[caseRowIdx][caseColIdx].setBonusing(true);
        assertThat(AddingMethods.addBonus(spriteList, mapPointMatrix, bonusFlame)).isFalse();
        assertThat(spriteList.contains(bonusFlame)).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();
    }

    @Test
    public void addBonusShouldAddTheBonusToTheListOfSpriteAndUpdateTheCaseStatus() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int caseRowIdx = 5;
        int caseColIdx = 6;
        mapPointMatrix[caseRowIdx][caseColIdx].setPathway(true);
        mapPointMatrix[caseRowIdx][caseColIdx].setBonusing(false);

        // test.
        BonusFlame bonusFlame = new BonusFlame(caseRowIdx, caseColIdx);
        assertThat(AddingMethods.addBonus(spriteList, mapPointMatrix, bonusFlame)).isTrue();
        assertThat(spriteList.contains(bonusFlame)).isTrue();
        assertThat(mapPointMatrix[caseRowIdx][caseColIdx].isBonusing()).isEqualTo(true);
    }

    @Test
    public void addBreakingEnemyShouldAddTheEnemyToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(1, 2);
        AddingMethods.addBreakingEnemy(spriteList, redSpearSoldier);

        // test.
        assertThat(spriteList.contains(redSpearSoldier)).isTrue();
    }

    @Test
    public void checkMapPointAndAddBonusShouldAddBonusAndSetTheRelativeCase() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 4;
        int mapHeight = 4;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int caseRowIdx = 1;
        int caseColIdx = 2;
        mapPointMatrix[caseRowIdx][caseColIdx].setAttachedBonus(BonusType.TYPE_BONUS_BOMB);
        mapPointMatrix[caseRowIdx][caseColIdx].setBonusing(false);
        AddingMethods.checkMapPointAndAddBonus(spriteList, mapPointMatrix[caseRowIdx][caseColIdx]);

        // test.
        assertThat(mapPointMatrix[caseRowIdx][caseColIdx].getAttachedBonus()).isNull();
        assertThat(mapPointMatrix[caseRowIdx][caseColIdx].isBonusing()).isTrue();
        assertThat(((Bonus)spriteList.get(0)).getBonusType()).isEqualTo(BonusType.TYPE_BONUS_BOMB);

        // quickly (& dirty!) check the other bonus.
        mapPointMatrix[1][1].setAttachedBonus(BonusType.TYPE_BONUS_FLAME);
        AddingMethods.checkMapPointAndAddBonus(spriteList, mapPointMatrix[1][1]);
        assertThat(((Bonus)spriteList.get(1)).getBonusType()).isEqualTo(BonusType.TYPE_BONUS_FLAME);
        mapPointMatrix[2][2].setAttachedBonus(BonusType.TYPE_BONUS_HEART);
        AddingMethods.checkMapPointAndAddBonus(spriteList, mapPointMatrix[2][2]);
        assertThat(((Bonus)spriteList.get(2)).getBonusType()).isEqualTo(BonusType.TYPE_BONUS_HEART);
        mapPointMatrix[3][3].setAttachedBonus(BonusType.TYPE_BONUS_ROLLER);
        AddingMethods.checkMapPointAndAddBonus(spriteList, mapPointMatrix[3][3]);
        assertThat(((Bonus)spriteList.get(3)).getBonusType()).isEqualTo(BonusType.TYPE_BONUS_ROLLER);
    }

    @Test
    public void addFlameShouldNotAddAFlameAndReturnFalse() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int notAvCaseRowIdx = 7;
        int notAvCaseColIdx = 8;
        mapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setPathway(false);
        mapPointMatrix[notAvCaseRowIdx][notAvCaseColIdx].setMutable(false);

        // test.
        Flame flame = new Flame(notAvCaseRowIdx, notAvCaseColIdx);
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, flame)).isFalse();
        assertThat(spriteList.contains(flame)).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();
    }

    @Test
    public void addFlameShouldAddAFlameToTheListOfSpriteAndReturnTrue() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int pathwayCaseRowIdx = 7;
        int pathwayCaseColIdx = 8;
        mapPointMatrix[pathwayCaseRowIdx][pathwayCaseColIdx].setPathway(true);

        // test.
        Flame flame = new Flame(pathwayCaseRowIdx, pathwayCaseColIdx);
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, flame)).isTrue();
        assertThat(spriteList.contains(flame)).isTrue();
        assertThat(mapPointMatrix[pathwayCaseRowIdx][pathwayCaseColIdx].isBurning()).isEqualTo(true);
    }

    @Test
    public void addFlameShouldAddAFlameToTheListOfSpriteAndReturnFalse() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }
        int mutableCaseRowIdx = 7;
        int mutableCaseColIdx = 8;
        mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].setPathway(false);
        mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].setMutable(true);

        // test.
        Flame flame = new Flame(mutableCaseRowIdx, mutableCaseColIdx);
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, flame)).isFalse();
        assertThat(spriteList.contains(flame)).isTrue();
        assertThat(mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].isBurning()).isEqualTo(true);
        assertThat(mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].isMutable()).isEqualTo(false); // no more mutable.
    }

    @Test
    public void addFlamesShouldAddAFlameAllAround() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;
        int flameSize = 3;

        /*
         * set map as:
         *    0 1 2 3 4 5 6 7 8 9
         * 0 | | | | | | | | | | |
         * 1 | | | | |*| | | | | |
         * 2 | | | | |*| | | | | |
         * 3 | | | | |*| | | | | |
         * 4 | |*|*|*|C|*|*|*| | |
         * 5 | | | | |*| | | | | |
         * 6 | | | | |*| | | | | |
         * 7 | | | | |*| | | | | |
         * 8 | | | | | | | | | | |
         * 9 | | | | | | | | | | |
         * 
         * with:
         * - C: centre of explosion + expected flame,
         * - *: expected flame.
         */

        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
                mapPointMatrix[rowIdx][colIdx].setMutable(false);
                mapPointMatrix[rowIdx][colIdx].setBombing(false);
            }
        }
        mapPointMatrix[4][4].setPathway(true);

        // expected flame.
        ArrayList<Tuple2<Integer, Integer>> expectedFlamesCoordinates = new ArrayList<>();
        expectedFlamesCoordinates.add(new Tuple2<>(1, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(2, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(3, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(5, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(6, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(7, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 1));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 2));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 3));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 5));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 6));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 7));

        // test.
        AddingMethods.addFlames(spriteList, mapPointMatrix, mapWidth, mapHeight, 4, 4,
                flameSize);

        assertThat(spriteList.size()).isEqualTo(13);
        for (Tuple2<Integer, Integer> expectedFlamesCoordinate : expectedFlamesCoordinates) {
            boolean found = false;

            // check if the expected flame has been added to the sprite list.
            for (Sprite aSpriteList : spriteList) {
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME);
                if (expectedFlamesCoordinate.getFirst() == ((Flame) aSpriteList).getRowIdx() &&
                        expectedFlamesCoordinate.getSecond() == ((Flame) aSpriteList).getColIdx()) {
                    found = true;
                    break;
                }
            }
            assertThat(found).isTrue();
            assertThat(mapPointMatrix[expectedFlamesCoordinate.getFirst()][expectedFlamesCoordinate
                    .getSecond()].isBurning()).isEqualTo(true);
        }
    }

    @Test
    public void addFlamesShouldAddAFlameAndNotPropagateThemFromMutableCases() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;
        int flameSize = 3;

        /*
         * set map as:
         *    0 1 2 3 4 5 6 7 8 9
         * 0 | | | | | | | | | | |
         * 1 | | | | | | | | | | |
         * 2 | | | | |M| | | | | |
         * 3 | | | | |*| | | | | |
         * 4 | | |M|*|C|*|M| | | |
         * 5 | | | | |*| | | | | |
         * 6 | | | | |M| | | | | |
         * 7 | | | | | | | | | | |
         * 8 | | | | | | | | | | |
         * 9 | | | | | | | | | | |
         *
         * with:
         * - C: centre of explosion + expected flame,
         * - M: mutable case + expected flame,
         * - *: expected flame.
         */

        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
                mapPointMatrix[rowIdx][colIdx].setMutable(false);
                mapPointMatrix[rowIdx][colIdx].setBombing(false);
            }
        }
        mapPointMatrix[4][4].setPathway(true);
        mapPointMatrix[2][4].setPathway(false);
        mapPointMatrix[2][4].setMutable(true);
        mapPointMatrix[6][4].setPathway(false);
        mapPointMatrix[6][4].setMutable(true);
        mapPointMatrix[4][2].setPathway(false);
        mapPointMatrix[4][2].setMutable(true);
        mapPointMatrix[4][6].setPathway(false);
        mapPointMatrix[4][6].setMutable(true);

        // expected flame.
        ArrayList<Tuple2<Integer, Integer>> expectedFlamesCoordinates = new ArrayList<>();
        expectedFlamesCoordinates.add(new Tuple2<>(2, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(3, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(5, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(6, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 2));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 3));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 5));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 6));

        // test.
        AddingMethods.addFlames(spriteList, mapPointMatrix, mapWidth, mapHeight, 4, 4,
                flameSize);

        assertThat(spriteList.size()).isEqualTo(9);
        for (Tuple2<Integer, Integer> expectedFlamesCoordinate : expectedFlamesCoordinates) {
            boolean found = false;

            // check if the expected flame has been added to the sprite list.
            for (Sprite aSpriteList : spriteList) {
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME);
                if (expectedFlamesCoordinate.getFirst() == ((Flame) aSpriteList).getRowIdx() &&
                        expectedFlamesCoordinate.getSecond() == ((Flame) aSpriteList).getColIdx()) {
                    found = true;
                    break;
                }
            }
            assertThat(found).isTrue();
            assertThat(mapPointMatrix[expectedFlamesCoordinate.getFirst()][expectedFlamesCoordinate
                    .getSecond()].isBurning()).isEqualTo(true);
        }
    }

    @Test
    public void addFlamesShouldAddAFlameAndStopByMutableCases() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        int mapWidth = 10;
        int mapHeight = 8;
        int flameSize = 3;

        /*
         * set map as:
         *    0 1 2 3 4 5 6 7 8 9
         * 0 | | | | | | | | | | |
         * 1 | | | | | | | | | | |
         * 2 | | | | |O| | | | | |
         * 3 | | | | |*| | | | | |
         * 4 | | |O|*|C|*|O| | | |
         * 5 | | | | |*| | | | | |
         * 6 | | | | |O| | | | | |
         * 7 | | | | | | | | | | |
         * 8 | | | | | | | | | | |
         * 9 | | | | | | | | | | |
         *
         * with:
         * - C: centre of explosion + expected flame,
         * - O: obstacle case,
         * - *: expected expected flame.
         */

        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                mapPointMatrix[rowIdx][colIdx].setPathway(true);
                mapPointMatrix[rowIdx][colIdx].setMutable(false);
                mapPointMatrix[rowIdx][colIdx].setBombing(false);
            }
        }
        mapPointMatrix[4][4].setPathway(true);
        mapPointMatrix[2][4].setPathway(false);
        mapPointMatrix[2][4].setMutable(false);
        mapPointMatrix[6][4].setPathway(false);
        mapPointMatrix[6][4].setMutable(false);
        mapPointMatrix[4][2].setPathway(false);
        mapPointMatrix[4][2].setMutable(false);
        mapPointMatrix[4][6].setPathway(false);
        mapPointMatrix[4][6].setMutable(false);

        // expected flame.
        ArrayList<Tuple2<Integer, Integer>> expectedFlamesCoordinates = new ArrayList<>();
        expectedFlamesCoordinates.add(new Tuple2<>(3, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(5, 4));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 3));
        expectedFlamesCoordinates.add(new Tuple2<>(4, 5));

        // test.
        AddingMethods.addFlames(spriteList, mapPointMatrix, mapWidth, mapHeight, 4, 4,
                flameSize);

        assertThat(spriteList.size()).isEqualTo(5);
        for (Tuple2<Integer, Integer> expectedFlamesCoordinate : expectedFlamesCoordinates) {
            boolean found = false;

            // check if the expected flame has been added to the sprite list.
            for (Sprite aSpriteList : spriteList) {
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLAME);
                if (expectedFlamesCoordinate.getFirst() == ((Flame) aSpriteList).getRowIdx() &&
                        expectedFlamesCoordinate.getSecond() == ((Flame) aSpriteList).getColIdx()) {
                    found = true;
                    break;
                }
            }
            assertThat(found).isTrue();
            assertThat(mapPointMatrix[expectedFlamesCoordinate.getFirst()][expectedFlamesCoordinate
                    .getSecond()].isBurning()).isEqualTo(true);
        }
    }

    @Test
    public void addFlameEndShouldAddAFlameEnd() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();

        // add sprite.
        FlameEnd flameEnd = new FlameEnd(1, 2);
        AddingMethods.addFlameEnd(spriteList, flameEnd);

        // test.
        assertThat(spriteList.contains(flameEnd)).isTrue();
    }

    @Test
    public void addFlyingNomadShouldAddTheFlyingNomadToTheList() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();

        // add sprite.
        WhiteBird whiteBird = new WhiteBird(1, 2, Direction.DIRECTION_EAST, -5);
        AddingMethods.addFlyingNomad(spriteList, whiteBird);

        // test.
        assertThat(spriteList.contains(whiteBird)).isTrue();
    }

    @Test
    public void addWalkingEnemyShouldAddTheEnemyToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        Zora zora = new Zora(1, 2);
        AddingMethods.addWalkingEnemy(spriteList, zora);

        // test.
        assertThat(spriteList.contains(zora)).isTrue();
    }
}