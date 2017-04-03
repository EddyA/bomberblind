package images;

import utils.Tuple3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * Load the images of the game in a matrix of type 'Image'.
 * The 'Image' are loaded in a 'MediaTracker' to speed up the displaying and avoid sparkling.
 */
public class ImagesLoader {
    public final static int IMAGE_SIZE = 32; // size of an 'Image' in pixels (30*30).

    public static Image[][] imagesMatrix; // matrix of images (holding all the game images).+
    public final static int NB_MATRIX_ROW = 51;
    public final static int NB_MATRIX_COL = 128;

    // images location.
    public final static String IMAGES_DIR = "/images";
    public final static String ASCII_SKIN_DIR = IMAGES_DIR + "/ascii";
    public final static String SPRITE_SKIN_DIR = IMAGES_DIR + "/sprites";
    public final static String SCENE_SKIN_DIR = IMAGES_DIR + "/scene/light_world_wood";

    // ascii.
    public static int asciiMatrixRowIdx;

    // bonus.
    public final static int NB_BONUS_BOMB_FRAME = 28;
    public static int bonusBombMatrixRowIdx;
    public final static int NB_BONUS_FLAME_FRAME = 28;
    public static int bonusFlameMatrixRowIdx;
    public final static int NB_BONUS_HEART_FRAME = 32;
    public static int bonusHeartMatrixRowIdx;
    public final static int NB_BONUS_ROLLER_FRAME = 28;
    public static int bonusRollerMatrixRowIdx;

    // scene.
    // - immutable obstacles.
    public final static int CASTLE_WIDTH = 4;
    public final static int CASTLE_HEIGHT = 6;
    public static int castleMatrixRowIdx;
    public final static int EDGE_WIDTH = 1;
    public final static int EDGE_HEIGHT = 5;
    public static int edgeMatrixRowIdx;
    public final static int GREEN_TREE_WIDTH = 4;
    public final static int GREEN_TREE_HEIGHT = 5;
    public static int greenTreeMatrixRowIdx;
    public final static int ORCHARD_WIDTH = 9;
    public final static int ORCHARD_HEIGHT = 8;
    public static int orchardMatrixRowIdx;
    public final static int RED_TREE_WIDTH = 4;
    public final static int RED_TREE_HEIGHT = 5;
    public static int redTreeMatrixRowIdx;
    public final static int STATUE_WIDTH = 2;
    public final static int STATUE_HEIGHT = 3;
    public static int statueMatrixRowIdx;
    public final static int TROUGH_WIDTH = 7;
    public final static int TROUGH_HEIGHT = 8;
    public static int troughMatrixRowIdx;
    public final static int TRUNK_WIDTH = 2;
    public final static int TRUNK_HEIGHT = 3;
    public static int trunkMatrixRowIdx;
    public final static int YELLOW_TREE_WIDTH = 4;
    public final static int YELLOW_TREE_HEIGHT = 5;
    public static int yellowTreeMatrixRowIdx;
    public final static int NB_SINGLE_IMMUTABLE_OBSTABLE = 3;
    public static int singleImmutableObstacleMatrixRowIdx;

    // - mutable obstacles.
    public final static int NB_SINGLE_MUTABLE_OBSTACLE = 2;
    public static int singleMutableObstacleMatrixRowIdx;

    // - pathways.
    public final static int NB_SINGLE_BOOM = 1;
    public static int singleBoomMatrixRowIdx;
    public final static int NB_FLOWER_FRAME = 3;
    public static int flowerMatrixRowIdx;
    public final static int PATHWAY_WIDTH = 5;
    public final static int PATHWAY_HEIGHT = 4;
    public static int pathwayMatrixRowIdx;
    public final static int NB_SINGLE_PATHWAY = 7;
    public static int singlePathwayMatrixRowIdx;

    // sprites.
    // - bomber.
    public final static int NB_BOMBER_DEATH_FRAME = 9;
    public final static int NB_BOMBER_WAIT_FRAME = 4;
    public final static int NB_BOMBER_WALK_FRAME = 4;
    public final static int NB_BOMBER_WIN_FRAME = 8;

    // -- blue Bomber.
    public static int blueBomberDeathMatrixRowIdx;
    public static int blueBomberWaitMatrixRowIdx;
    public static int blueBomberWalkBackMatrixRowIdx;
    public static int blueBomberWalkFrontMatrixRowIdx;
    public static int blueBomberWalkLeftMatrixRowIdx;
    public static int blueBomberWalkRightMatrixRowIdx;
    public static int blueBomberWinMatrixRowIdx;

    // - enemies.
    // -- walking enemies.
    // --- zora.
    public final static int NB_ZORA_WALK_FRAME = 2;
    public static int zoraWalkBackMatrixRowIdx;
    public static int zoraWalkFrontMatrixRowIdx;
    public static int zoraWalkLeftMatrixRowIdx;
    public static int zoraWalkRightMatrixRowIdx;

    // --- green soldier.
    public final static int NB_GREEN_SOLDIER_WALK_FRAME = 10;
    public static int greenSoldierWalkBackMatrixRowIdx;
    public static int greenSoldierWalkFrontMatrixRowIdx;
    public static int greenSoldierWalkLeftMatrixRowIdx;
    public static int greenSoldierWalkRightMatrixRowIdx;

    // -- breaking enemies.
    // --- red spear soldier.
    public final static int NB_RED_SPEAR_SOLDIER_WALK_FRAME = 2;
    public static int redSpearSoldierWalkBackMatrixRowIdx;
    public static int redSpearSoldierWalkFrontMatrixRowIdx;
    public static int redSpearSoldierWalkLeftMatrixRowIdx;
    public static int redSpearSoldierWalkRightMatrixRowIdx;
    public final static int NB_RED_SPEAR_SOLDIER_BREAK_FRAME = 4;
    public static int redSpearSoldierBreakBackMatrixRowIdx;
    public static int redSpearSoldierBreakFrontMatrixRowIdx;
    public static int redSpearSoldierBreakLeftMatrixRowIdx;
    public static int redSpearSoldierBreakRightMatrixRowIdx;

    // - flying nomads.
    // -- bird.
    public final static int NB_BIRD_FLY_FRAME = 3;
    public static int birdFlyBackMatrixRowIdx;
    public static int birdFlyFrontMatrixRowIdx;
    public static int birdFlyLeftMatrixRowIdx;
    public static int birdFlyRightMatrixRowIdx;

    // - settled.
    public final static int NB_BOMB_FRAME = 4;
    public static int bombMatrixRowIdx;
    public final static int NB_FLAME_FRAME = 3;
    public static int flameMatrixRowIdx;
    public final static int NB_FLAME_END_FRAME = 9;
    public static int flameEndMatrixRowIdx;

    // - death.
    public final static int NB_DEATH_FRAME = 7;
    public static int deathMatrixRowIdx;

    public static int curMatrixRowIdx;
    public static int lastMatrixRowIdx; // for test purpose.
    public static boolean imagesLoaded = false; // have images been loaded? (for test purpose).

    /**
     * Create an 'Image' based to a relative path (from 'resources' folder).
     *
     * @param relativePath relative path to an image
     * @return the created 'Image'
     * @throws IOException if the file does not exist
     */
    public static Image createImage(String relativePath) throws IOException {
        URL imageURL = ImagesLoader.class.getResource(relativePath);
        if (imageURL != null) {
            return ImageIO.read(imageURL);
        } else {
            throw new IOException("file not found: " + relativePath);
        }
    }

    /**
     * Fill a matrix of 'Image'.
     *
     * @throws IOException if a file does not exist
     */
    public static void fillImagesMatrix() throws IOException {
        if (imagesLoaded) return; // load once the images (test purpose).

        curMatrixRowIdx = 0;
        imagesMatrix = new Image[NB_MATRIX_ROW][NB_MATRIX_COL];

        // ascii.
        for (int i = 0; i <= 127; i++) {
            String imageIdx = String.format("%2s", i).replace(' ', '0');
            try {
                imagesMatrix[curMatrixRowIdx][i] = createImage(ASCII_SKIN_DIR + "/" + imageIdx + ".png");
            } catch (IOException e) { // the ascii character is not available, put an empty image.
                imagesMatrix[curMatrixRowIdx][i] = createImage(ASCII_SKIN_DIR + "/32.png");
            }
        }
        asciiMatrixRowIdx = curMatrixRowIdx++;

        // bonus.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/bonus_bomb/bonus_bomb-", NB_BONUS_BOMB_FRAME);
        bonusBombMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/bonus_flame/bonus_flame-", NB_BONUS_FLAME_FRAME);
        bonusFlameMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/bonus_heart/bonus_heart-", NB_BONUS_HEART_FRAME);
        bonusHeartMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/bonus_roller/bonus_roller-", NB_BONUS_ROLLER_FRAME);
        bonusRollerMatrixRowIdx = curMatrixRowIdx++;

        // scene.
        // - immutable obstacles.
        // -- castle.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/castle/castle-",
                CASTLE_WIDTH,
                CASTLE_HEIGHT);
        castleMatrixRowIdx = curMatrixRowIdx++;

        // -- edge.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/edge/edge-",
                EDGE_WIDTH,
                EDGE_HEIGHT);
        edgeMatrixRowIdx = curMatrixRowIdx++;

        // -- green tree.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/green_tree/green_tree-",
                GREEN_TREE_WIDTH,
                GREEN_TREE_HEIGHT);
        greenTreeMatrixRowIdx = curMatrixRowIdx++;

        // -- orchard.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/orchard/orchard-",
                ORCHARD_WIDTH,
                ORCHARD_HEIGHT);
        orchardMatrixRowIdx = curMatrixRowIdx++;

        // -- red tree.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/red_tree/red_tree-",
                RED_TREE_WIDTH,
                RED_TREE_HEIGHT);
        redTreeMatrixRowIdx = curMatrixRowIdx++;

        // -- statue.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/statue/statue-",
                STATUE_WIDTH,
                STATUE_HEIGHT);
        statueMatrixRowIdx = curMatrixRowIdx++;

        // -- trough.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/trough/trough-",
                TROUGH_WIDTH,
                TROUGH_HEIGHT);
        troughMatrixRowIdx = curMatrixRowIdx++;

        // -- trunk.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/trunk/trunk-",
                TRUNK_WIDTH,
                TRUNK_HEIGHT);
        trunkMatrixRowIdx = curMatrixRowIdx++;

        // -- yellow tree.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/immutable_obstacle/yellow_tree/yellow_tree-",
                YELLOW_TREE_WIDTH,
                YELLOW_TREE_HEIGHT);
        yellowTreeMatrixRowIdx = curMatrixRowIdx++;

        // -- single immutable obstacle (1*1).
        for (int i = 0; i < NB_SINGLE_IMMUTABLE_OBSTABLE; i++) {
            imagesMatrix[curMatrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable_obstacle/immutable-" + i + ".png");
        }
        singleImmutableObstacleMatrixRowIdx = curMatrixRowIdx++;

        // - mutable obstacles.
        // -- single mutable obstacle (1*1).
        fillMatrixWithSpriteImages(SCENE_SKIN_DIR + "/mutable_obstacle/mutable-", NB_SINGLE_MUTABLE_OBSTACLE);
        singleMutableObstacleMatrixRowIdx = curMatrixRowIdx++;

        // -- pathways.
        fillMatrixWithPatternImages(SCENE_SKIN_DIR + "/pathway/pathway/pathway-",
                PATHWAY_WIDTH,
                PATHWAY_HEIGHT);
        pathwayMatrixRowIdx = curMatrixRowIdx++;

        // -- boom (1*1).
        fillMatrixWithSpriteImages(SCENE_SKIN_DIR + "/pathway/boom/boom-", NB_SINGLE_BOOM);
        singleBoomMatrixRowIdx = curMatrixRowIdx++;

        // -- flower (animated).
        fillMatrixWithSpriteImages(SCENE_SKIN_DIR + "/pathway/flower/flower-", NB_FLOWER_FRAME);
        flowerMatrixRowIdx = curMatrixRowIdx++;

        // -- single pathway (1*1).
        fillMatrixWithSpriteImages(SCENE_SKIN_DIR + "/pathway/pathway-", NB_SINGLE_PATHWAY);
        singlePathwayMatrixRowIdx = curMatrixRowIdx++;

        // sprites.
        // - bombers:
        // -- blue bomber.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-death-", NB_BOMBER_DEATH_FRAME);
        blueBomberDeathMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-wait-", NB_BOMBER_WAIT_FRAME);
        blueBomberWaitMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-walk-back-", NB_BOMBER_WALK_FRAME);
        blueBomberWalkBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-walk-front-", NB_BOMBER_WALK_FRAME);
        blueBomberWalkFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-walk-left-", NB_BOMBER_WALK_FRAME);
        blueBomberWalkLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-walk-right-", NB_BOMBER_WALK_FRAME);
        blueBomberWalkRightMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/blue_bomber/blue_bomber-win-", NB_BOMBER_WIN_FRAME);
        blueBomberWinMatrixRowIdx = curMatrixRowIdx++;

        // - enemies.
        // -- walking enemies.
        // --- zora.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/zora/zora-walk-back-", NB_ZORA_WALK_FRAME);
        zoraWalkBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/zora/zora-walk-front-", NB_ZORA_WALK_FRAME);
        zoraWalkFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/zora/zora-walk-left-", NB_ZORA_WALK_FRAME);
        zoraWalkLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/zora/zora-walk-right-", NB_ZORA_WALK_FRAME);
        zoraWalkRightMatrixRowIdx = curMatrixRowIdx++;

        // --- green soldier.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/green_soldier/green_soldier-walk-back-", NB_GREEN_SOLDIER_WALK_FRAME);
        greenSoldierWalkBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/green_soldier/green_soldier-walk-front-", NB_GREEN_SOLDIER_WALK_FRAME);
        greenSoldierWalkFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/green_soldier/green_soldier-walk-left-", NB_GREEN_SOLDIER_WALK_FRAME);
        greenSoldierWalkLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/green_soldier/green_soldier-walk-right-", NB_GREEN_SOLDIER_WALK_FRAME);
        greenSoldierWalkRightMatrixRowIdx = curMatrixRowIdx++;

        // -- breaking enemies.
        // --- red spear soldier.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-walk-back-", NB_RED_SPEAR_SOLDIER_WALK_FRAME);
        redSpearSoldierWalkBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-walk-front-", NB_RED_SPEAR_SOLDIER_WALK_FRAME);
        redSpearSoldierWalkFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-walk-left-", NB_RED_SPEAR_SOLDIER_WALK_FRAME);
        redSpearSoldierWalkLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-walk-right-", NB_RED_SPEAR_SOLDIER_WALK_FRAME);
        redSpearSoldierWalkRightMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-break-back-", NB_RED_SPEAR_SOLDIER_BREAK_FRAME);
        redSpearSoldierBreakBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-break-front-", NB_RED_SPEAR_SOLDIER_BREAK_FRAME);
        redSpearSoldierBreakFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-break-left-", NB_RED_SPEAR_SOLDIER_BREAK_FRAME);
        redSpearSoldierBreakLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/red_spear_soldier/red_spear_soldier-break-right-", NB_RED_SPEAR_SOLDIER_BREAK_FRAME);
        redSpearSoldierBreakRightMatrixRowIdx = curMatrixRowIdx++;

        // - flying nomads
        // -- bird.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/white_bird/white_bird-fly-back-", NB_BIRD_FLY_FRAME);
        birdFlyBackMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/white_bird/white_bird-fly-front-", NB_BIRD_FLY_FRAME);
        birdFlyFrontMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/white_bird/white_bird-fly-left-", NB_BIRD_FLY_FRAME);
        birdFlyLeftMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/white_bird/white_bird-fly-right-", NB_BIRD_FLY_FRAME);
        birdFlyRightMatrixRowIdx = curMatrixRowIdx++;

        // settled.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/bomb/bomb-", NB_BOMB_FRAME);
        bombMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/flame/flame-", NB_FLAME_FRAME);
        flameMatrixRowIdx = curMatrixRowIdx++;
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/flame_end/flame_end-", NB_FLAME_END_FRAME);
        flameEndMatrixRowIdx = curMatrixRowIdx++;

        // death.
        fillMatrixWithSpriteImages(SPRITE_SKIN_DIR + "/death/death-", NB_DEATH_FRAME);
        deathMatrixRowIdx = curMatrixRowIdx;

        lastMatrixRowIdx = curMatrixRowIdx;
        imagesLoaded = true;
    }

    /**
     * The function allows filling the images matrix with pattern images.
     *
     * @param patternPathName the pattern path name (path/pattern file name)
     * @param patternWidth    the pattern width
     * @param patternHeight   the pattern height
     * @throws IOException if the image file does not exist
     */
    public static void fillMatrixWithPatternImages(String patternPathName,
                                                   int patternWidth,
                                                   int patternHeight) throws IOException {
        for (int rowIdx = 0; rowIdx < patternHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < patternWidth; colIdx++) {
                imagesMatrix[curMatrixRowIdx][rowIdx * patternWidth + colIdx] =
                        createImage(patternPathName + rowIdx + "-" + colIdx + ".png");
            }
        }
    }

    public static void fillMatrixWithSpriteImages(String spriteActionPathName,
                                                  int nbSpriteActions) throws IOException {
        for (int i = 0; i < nbSpriteActions; i++) {
            imagesMatrix[curMatrixRowIdx][i] = createImage(spriteActionPathName + i + ".png");
        }
    }

    /**
     * @return a random single immutable obstacle image.
     */
    public static Image getRandomSingleImmutableObstacle() {
        Random R = new Random(); // init the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_IMMUTABLE_OBSTABLE);
        return ImagesLoader.imagesMatrix[ImagesLoader.singleImmutableObstacleMatrixRowIdx][imageIdx];
    }

    /**
     * @return a random single mutable obstacle image.
     */
    public static Image getRandomSingleMutableObstacle() {
        Random R = new Random(); // init the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_MUTABLE_OBSTACLE);
        return ImagesLoader.imagesMatrix[ImagesLoader.singleMutableObstacleMatrixRowIdx][imageIdx];
    }

    /**
     * @return a random decorated single pathway image.
     */
    public static Image getRandomDecoratedSinglePathway() {
        Random R = new Random(); // init the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_PATHWAY - 1) + 1;
        return ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][imageIdx];
    }

    /**
     * @return a random dynamic single pathway image.
     */
    @SuppressWarnings("unchecked")
    public static Tuple3<Image[], Integer, Integer> getRandomDynamicSinglePathway() {
        return new Tuple3<>(ImagesLoader.imagesMatrix[ImagesLoader.flowerMatrixRowIdx],
                ImagesLoader.NB_FLOWER_FRAME, 100);
    }

    /**
     * @return the virgin single pathway image.
     */
    public static Image getVirginSinglePathway() {
        return ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][0];
    }
}
