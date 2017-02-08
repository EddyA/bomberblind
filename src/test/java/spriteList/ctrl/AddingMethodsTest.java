package spriteList.ctrl;

import images.ImagesLoader;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;
import sprite.Sprite;
import sprite.nomad.*;
import sprite.nomad.Bird;
import sprite.settled.Bomb;
import sprite.settled.Flame;
import sprite.settled.FlameEnd;
import sprite.settled.LoopedSettled;
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
    public void addBomberShouldAddTheBomberToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        Bomber bomber = new BlueBomber(1, 2);
        AddingMethods.addBomber(spriteList, bomber);

        // test.
        assertThat(spriteList.contains(bomber)).isTrue();
    }

    @Test
    public void addWalkingEnemyShouldAddTheEnemyToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        CloakedSkeleton cloakedSkeleton = new CloakedSkeleton(1, 2);
        AddingMethods.addWalkingEnemy(spriteList, cloakedSkeleton);

        // test.
        assertThat(spriteList.contains(cloakedSkeleton)).isTrue();
    }

    @Test
    public void addBreakingEnemyShouldAddTheEnemyToTheListOfSprite() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();
        Minotor minotor = new Minotor(1, 2);
        AddingMethods.addBreakingEnemy(spriteList, minotor);

        // test.
        assertThat(spriteList.contains(minotor)).isTrue();
    }

    @Test
    public void addBombShouldNotAddABomb() throws Exception {
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
        assertThat(spriteList.isEmpty()).isTrue();

        // it is a burning case.
        mapPointMatrix[caseRowIdx][caseColIdx].setBombing(false);
        mapPointMatrix[caseRowIdx][caseColIdx].addFlame();
        AddingMethods.addBomb(spriteList, mapPointMatrix, bomb);
        assertThat(spriteList.isEmpty()).isTrue();
    }

    @Test
    public void addBombShouldAddABombAndUpdateTheCaseStatus() throws Exception {
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
        AddingMethods.addBomb(spriteList, mapPointMatrix, bomb);
        assertThat(spriteList.getFirst().getSpriteType()).isEqualTo(SpriteType.TYPE_BOMB);
        assertThat(((LoopedSettled) spriteList.getFirst()).getRowIdx()).isEqualTo(caseRowIdx);
        assertThat(((LoopedSettled) spriteList.getFirst()).getColIdx()).isEqualTo(caseColIdx);
        assertThat(mapPointMatrix[caseRowIdx][caseColIdx].isBombing()).isEqualTo(true);
    }

    @Test
    public void addFlameShouldAddAFlameAndReturnTrue() throws Exception {
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
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, new Flame(pathwayCaseRowIdx, pathwayCaseColIdx))).isTrue();
        assertThat(spriteList.getFirst().getSpriteType()).isEqualTo(SpriteType.TYPE_FLAME);
        assertThat(((LoopedSettled) spriteList.getFirst()).getRowIdx()).isEqualTo(pathwayCaseRowIdx);
        assertThat(((LoopedSettled) spriteList.getFirst()).getColIdx()).isEqualTo(pathwayCaseColIdx);
        assertThat(mapPointMatrix[pathwayCaseRowIdx][pathwayCaseColIdx].isBurning()).isEqualTo(true);
    }

    @Test
    public void addFlameShouldAddAFlameAndReturnFalse() throws Exception {
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
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, new Flame(mutableCaseRowIdx, mutableCaseColIdx))).isFalse();
        assertThat(spriteList.getFirst().getSpriteType()).isEqualTo(SpriteType.TYPE_FLAME);
        assertThat(((LoopedSettled) spriteList.getFirst()).getRowIdx()).isEqualTo(mutableCaseRowIdx);
        assertThat(((LoopedSettled) spriteList.getFirst()).getColIdx()).isEqualTo(mutableCaseColIdx);
        assertThat(mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].isBurning()).isEqualTo(true);
        assertThat(mapPointMatrix[mutableCaseRowIdx][mutableCaseColIdx].isMutable()).isEqualTo(false); // no more
        // mutable.
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
        assertThat(AddingMethods.addFlame(spriteList, mapPointMatrix, new Flame(notAvCaseRowIdx, notAvCaseColIdx))).isFalse();
        assertThat(spriteList.isEmpty()).isTrue();
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
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_FLAME);
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
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_FLAME);
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
                assertThat(aSpriteList.getSpriteType()).isEqualTo(SpriteType.TYPE_FLAME);
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
        int mapWidth = 10;
        int mapHeight = 8;

        // set map.
        MapPoint[][] mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
            }
        }

        // add sprite.
        FlameEnd flameEnd = new FlameEnd(1, 2);
        AddingMethods.addFlameEnd(spriteList, flameEnd);

        // test.
        assertThat(spriteList.contains(flameEnd)).isTrue();
    }

    @Test
    public void addBirdShouldAddABird() throws Exception {
        LinkedList<Sprite> spriteList = new LinkedList<>();

        // add sprite.
        Bird bird = new Bird(1, 2, Direction.DIRECTION_EAST, -5);
        AddingMethods.addBird(spriteList, bird);

        // test.
        assertThat(spriteList.contains(bird)).isTrue();
    }
}