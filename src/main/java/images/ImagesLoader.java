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

    public static Image[][] imagesMatrix; // matrix of images (holding all the game images).
    public final static int NB_MATRIX_ROW = 58;
    public final static int NB_MATRIX_COL = 128;

    // images location.
    public final static String IMAGES_DIR = "/images";
    public final static String ASCII_SKIN_DIR = IMAGES_DIR + "/ascii";
    public final static String BONUS_SKIN_DIR = IMAGES_DIR + "/bonus";
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
    // --- cloaked skeleton.
    public final static int NB_CLOAKED_SKELETON_DEATH_FRAME = 4;
    public final static int NB_CLOAKED_SKELETON_WALK_FRAME = 4;
    public static int cloakedSkeletonDeathMatrixRowIdx;
    public static int cloakedSkeletonWalkBackMatrixRowIdx;
    public static int cloakedSkeletonWalkFrontMatrixRowIdx;
    public static int cloakedSkeletonWalkLeftMatrixRowIdx;
    public static int cloakedSkeletonWalkRightMatrixRowIdx;

    // --- mummy.
    public final static int NB_MUMMY_DEATH_FRAME = 4;
    public final static int NB_MUMMY_WALK_FRAME = 2;
    public static int mummyDeathMatrixRowIdx;
    public static int mummyWalkBackMatrixRowIdx;
    public static int mummyWalkFrontMatrixRowIdx;
    public static int mummyWalkLeftMatrixRowIdx;
    public static int mummyWalkRightMatrixRowIdx;

    // --- mecanic angel.
    public final static int NB_MECA_ANGEL_DEATH_FRAME = 5;
    public final static int NB_MECA_ANGEL_WALK_FRAME = 2;
    public static int mecaAngelDeathMatrixRowIdx;
    public static int mecaAngelWalkBackMatrixRowIdx;
    public static int mecaAngelWalkFrontMatrixRowIdx;
    public static int mecaAngelWalkLeftMatrixRowIdx;
    public static int mecaAngelWalkRightMatrixRowIdx;

    // -- breaking enemies.
    // --- minotor.
    public final static int NB_MINOTOR_BREAK_FRAME = 2;
    public final static int NB_MINOTOR_DEATH_FRAME = 5;
    public final static int NB_MINOTOR_WALK_FRAME = 4;
    public static int minotorDeathMatrixRowIdx;
    public static int minotorWalkBackMatrixRowIdx;
    public static int minotorWalkFrontMatrixRowIdx;
    public static int minotorWalkLeftMatrixRowIdx;
    public static int minotorWalkRightMatrixRowIdx;
    public static int minotorBreakBackMatrixRowIdx;
    public static int minotorBreakFrontMatrixRowIdx;
    public static int minotorBreakLeftMatrixRowIdx;
    public static int minotorBreakRightMatrixRowIdx;

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

    public static int lastRowIdx; // for test purpose.
    public static boolean imagesLoaded = false; // for test purpose.

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

        int maxtrixRowIdx = 0;
        imagesMatrix = new Image[NB_MATRIX_ROW][NB_MATRIX_COL];

        // ascii.
        for (int i = 0; i <= 127; i++) {
            String imageIdx = String.format("%2s", i).replace(' ', '0');
            try {
                imagesMatrix[maxtrixRowIdx][i] = createImage(ASCII_SKIN_DIR + "/" + imageIdx + ".png");
            } catch (IOException e) { // the ascii character is not available, put an empty image.
                imagesMatrix[maxtrixRowIdx][i] = createImage(ASCII_SKIN_DIR + "/32.png");
            }
        }
        asciiMatrixRowIdx = maxtrixRowIdx++;

        // bonus.
        for (int i = 0; i < NB_BONUS_BOMB_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(BONUS_SKIN_DIR + "/bomb/bomb_" + imageIdx + ".png");
        }
        bonusBombMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BONUS_FLAME_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(BONUS_SKIN_DIR + "/flame/flame_" + imageIdx + ".png");
        }
        bonusFlameMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BONUS_HEART_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(BONUS_SKIN_DIR + "/heart/heart_" + imageIdx + ".png");
        }
        bonusHeartMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BONUS_ROLLER_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(BONUS_SKIN_DIR + "/roller/roller_" + imageIdx + ".png");
        }
        bonusRollerMatrixRowIdx = maxtrixRowIdx++;

        // scene.
        // - immutable obstacles.
        // -- castle.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/castle/castle-",
                CASTLE_WIDTH,
                CASTLE_HEIGHT);
        castleMatrixRowIdx = maxtrixRowIdx++;

        // -- edge.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/edge/edge-",
                EDGE_WIDTH,
                EDGE_HEIGHT);
        edgeMatrixRowIdx = maxtrixRowIdx++;

        // - green tree.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/green_tree/green_tree-",
                GREEN_TREE_WIDTH,
                GREEN_TREE_HEIGHT);
        greenTreeMatrixRowIdx = maxtrixRowIdx++;

        // - orchard.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/orchard/orchard-",
                ORCHARD_WIDTH,
                ORCHARD_HEIGHT);
        orchardMatrixRowIdx = maxtrixRowIdx++;

        // - red tree.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/red_tree/red_tree-",
                RED_TREE_WIDTH,
                RED_TREE_HEIGHT);
        redTreeMatrixRowIdx = maxtrixRowIdx++;

        // -- statue.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/statue/statue-",
                STATUE_WIDTH,
                STATUE_HEIGHT);
        statueMatrixRowIdx = maxtrixRowIdx++;

        // -- trough.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/trough/trough-",
                TROUGH_WIDTH,
                TROUGH_HEIGHT);
        troughMatrixRowIdx = maxtrixRowIdx++;

        // -- trunk.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/trunk/trunk-",
                TRUNK_WIDTH,
                TRUNK_HEIGHT);
        trunkMatrixRowIdx = maxtrixRowIdx++;

        // -- yellow tree.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/immutable_obstacle/yellow_tree/yellow_tree-",
                YELLOW_TREE_WIDTH,
                YELLOW_TREE_HEIGHT);
        yellowTreeMatrixRowIdx = maxtrixRowIdx++;

        // -- single immutable obstacle (1*1).
        for (int i = 0; i < NB_SINGLE_IMMUTABLE_OBSTABLE; i++) {
            imagesMatrix[maxtrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable_obstacle/immutable-" + i + ".png");
        }
        singleImmutableObstacleMatrixRowIdx = maxtrixRowIdx++;

        // - mutable obstacles.
        // -- single mutable obstacle (1*1).
        for (int i = 0; i < NB_SINGLE_MUTABLE_OBSTACLE; i++) {
            imagesMatrix[maxtrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/mutable_obstacle/mutable-" + i + ".png");
        }
        singleMutableObstacleMatrixRowIdx = maxtrixRowIdx++;

        // - pathways.
        // -- pathway.
        fillImagesMatrixWithPatternImages(maxtrixRowIdx,
                SCENE_SKIN_DIR + "/pathway/pathway/pathway-",
                PATHWAY_WIDTH,
                PATHWAY_HEIGHT);
        pathwayMatrixRowIdx = maxtrixRowIdx++;

        // -- boom (1*1).
        for (int i = 0; i < NB_SINGLE_BOOM; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/pathway/boom/boom_" + imageIdx + ".png");
        }
        singleBoomMatrixRowIdx = maxtrixRowIdx++;

        // -- flower (animated).
        for (int i = 0; i < NB_FLOWER_FRAME; i++) {
            imagesMatrix[maxtrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/pathway/flower/flower-" + i + ".png");
        }
        flowerMatrixRowIdx = maxtrixRowIdx++;

        // -- single pathway (1*1).
        for (int i = 0; i < NB_SINGLE_PATHWAY; i++) {
            imagesMatrix[maxtrixRowIdx][i] = createImage(SCENE_SKIN_DIR + "/pathway/pathway-" + i + ".png");
        }
        singlePathwayMatrixRowIdx = maxtrixRowIdx++;

        // sprites.
        // - bombers:
        // -- blue bomber.
        for (int i = 0; i < NB_BOMBER_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/death_" + imageIdx + ".png");
        }
        blueBomberDeathMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WAIT_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/wait_" + imageIdx + ".png");
        }
        blueBomberWaitMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/walk_back_" + imageIdx + ".png");
        }
        blueBomberWalkBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/walk_front_" + imageIdx + ".png");
        }
        blueBomberWalkFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/walk_left_" + imageIdx + ".png");
        }
        blueBomberWalkLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/walk_right_" + imageIdx + ".png");
        }
        blueBomberWalkRightMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BOMBER_WIN_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/blue_bomber/win_" + imageIdx + ".png");
        }
        blueBomberWinMatrixRowIdx = maxtrixRowIdx++;

        // - enemies.
        // -- walking enemies.
        // --- cloaked skeleton.
        for (int i = 0; i < NB_CLOAKED_SKELETON_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/cloaked_skeleton/death_" + imageIdx + ".png");
        }
        cloakedSkeletonDeathMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_CLOAKED_SKELETON_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/cloaked_skeleton/walk_back_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_CLOAKED_SKELETON_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/cloaked_skeleton/walk_front_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_CLOAKED_SKELETON_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/cloaked_skeleton/walk_left_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_CLOAKED_SKELETON_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/cloaked_skeleton/walk_right_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkRightMatrixRowIdx = maxtrixRowIdx++;

        // - mecanical angel.
        for (int i = 0; i < NB_MECA_ANGEL_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/meca_angel/death_" + imageIdx + ".png");
        }
        mecaAngelDeathMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MECA_ANGEL_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/meca_angel/walk_back_" + imageIdx + ".png");
        }
        mecaAngelWalkBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MECA_ANGEL_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/meca_angel/walk_front_" + imageIdx + ".png");
        }
        mecaAngelWalkFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MECA_ANGEL_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/meca_angel/walk_left_" + imageIdx + ".png");
        }
        mecaAngelWalkLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MECA_ANGEL_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/meca_angel/walk_right_" + imageIdx + ".png");
        }
        mecaAngelWalkRightMatrixRowIdx = maxtrixRowIdx++;

        // -- mummy.
        for (int i = 0; i < NB_MECA_ANGEL_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/mummy/death_" + imageIdx + ".png");
        }
        mummyDeathMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MECA_ANGEL_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/mummy/walk_back_" + imageIdx + ".png");
        }
        mummyWalkBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MUMMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/mummy/walk_front_" + imageIdx + ".png");
        }
        mummyWalkFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MUMMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/mummy/walk_left_" + imageIdx + ".png");
        }
        mummyWalkLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MUMMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/mummy/walk_right_" + imageIdx + ".png");
        }
        mummyWalkRightMatrixRowIdx = maxtrixRowIdx++;

        // -- breaking enemies.
        // --- minotor.
        for (int i = 0; i < NB_MINOTOR_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/death_" + imageIdx + ".png");
        }
        minotorDeathMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/walk_back_" + imageIdx + ".png");
        }
        minotorWalkBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/walk_front_" + imageIdx + ".png");
        }
        minotorWalkFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/walk_left_" + imageIdx + ".png");
        }
        minotorWalkLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/walk_right_" + imageIdx + ".png");
        }
        minotorWalkRightMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_BREAK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/break_back_" + imageIdx + ".png");
        }
        minotorBreakBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_BREAK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/break_front_" + imageIdx + ".png");
        }
        minotorBreakFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_BREAK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/break_left_" + imageIdx + ".png");
        }
        minotorBreakLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_MINOTOR_BREAK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/minotor/break_right_" + imageIdx + ".png");
        }
        minotorBreakRightMatrixRowIdx = maxtrixRowIdx++;

        // - flying nomads
        // -- bird.
        for (int i = 0; i < NB_BIRD_FLY_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/bird/fly_back_" + imageIdx + ".png");
        }
        birdFlyBackMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BIRD_FLY_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/bird/fly_front_" + imageIdx + ".png");
        }
        birdFlyFrontMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BIRD_FLY_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/bird/fly_left_" + imageIdx + ".png");
        }
        birdFlyLeftMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_BIRD_FLY_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/bird/fly_right_" + imageIdx + ".png");
        }
        birdFlyRightMatrixRowIdx = maxtrixRowIdx++;

        // settled.
        for (int i = 0; i < NB_BOMB_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/bomb/bomb_" + imageIdx + ".png");
        }
        bombMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_FLAME_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/flame/flame_" + imageIdx + ".png");
        }
        flameMatrixRowIdx = maxtrixRowIdx++;
        for (int i = 0; i < NB_FLAME_END_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[maxtrixRowIdx][i] = createImage(SPRITE_SKIN_DIR + "/flame/flame_end_" + imageIdx + ".png");
        }
        flameEndMatrixRowIdx = maxtrixRowIdx;

        lastRowIdx = maxtrixRowIdx;
        imagesLoaded = true;
    }

    /**
     * The function allows filling the images matrix with pattern images.
     *
     * @param matrixRowIdx    the current matrix rowId
     * @param patternPathName the pattern path name (path/pattern file name)
     * @param patternWidth    the pattern width
     * @param patternHeight   the pattern height
     * @throws IOException if the image file does not exist
     */
    public static void fillImagesMatrixWithPatternImages(int matrixRowIdx,
                                                         String patternPathName,
                                                         int patternWidth,
                                                         int patternHeight) throws IOException {
        for (int rowIdx = 0; rowIdx < patternHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < patternWidth; colIdx++) {
                imagesMatrix[matrixRowIdx][rowIdx * patternWidth + colIdx] =
                        createImage(patternPathName + rowIdx + "-" + colIdx + ".png");
            }
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
