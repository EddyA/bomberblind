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
    public final static int IMAGE_SIZE = 30; // size of an 'Image' in pixels (30*30).

    public static Image[][] imagesMatrix; // matrix of images (holding all the game images).
    protected final static int NB_MATRIX_ROW = 28;
    protected final static int NB_MATRIX_COL = 80;

    // images location.
    private final static String BBMAN_SKIN_DIR = "/images/characters/bbman";
    private final static String ENEMY_SKIN_DIR = "/images/characters/enemy";
    private final static String BOMB_SKIN_DIR = "/images/bomb";
    private final static String FLAME_SKIN_DIR = "/images/flame";
    private final static String SCENE_SKIN_DIR = "/images/scene";

    // bbmans:
    public final static int NB_BBMAN_DEATH_FRAME = 9;
    public final static int NB_BBMAN_WAIT_FRAME = 4;
    public final static int NB_BBMAN_WALK_FRAME = 4;
    public final static int NB_BBMAN_WIN_FRAME = 8;

    // - blue Bomber.
    public static int blueBbManDeathMatrixRowIdx;
    public static int blueBbManWaitMatrixRowIdx;
    public static int blueBbManWalkBackMatrixRowIdx;
    public static int blueBbManWalkFrontMatrixRowIdx;
    public static int blueBbManWalkLeftMatrixRowIdx;
    public static int blueBbManWalkRightMatrixRowIdx;
    public static int blueBbManWinMatrixRowIdx;

    // enemies:
    public final static int NB_ENEMY_WALK_FRAME = 4;

    // - cloaked skeleton.
    public static int cloakedSkeletonWalkBackMatrixRowIdx;
    public static int cloakedSkeletonWalkFrontMatrixRowIdx;
    public static int cloakedSkeletonWalkLeftMatrixRowIdx;
    public static int cloakedSkeletonWalkRightMatrixRowIdx;

    // sprites:
    public final static int NB_BOMB_FRAME = 4;
    public static int bombMatrixRowIdx;
    public final static int NB_FLAME_FRAME = 3;
    public static int flameMatrixRowIdx;
    public final static int NB_FLAME_END_FRAME = 9;
    public static int flameEndMatrixRowIdx;

    // scene elements:
    public final static int CASTLE_WIDTH = 6;
    public final static int CASTLE_HEIGHT = 5;
    public static int castleT1MatrixRowIdx;
    public static int castleT2MatrixRowIdx;
    public final static int EDGE_WIDTH = 1;
    public final static int EDGE_HEIGHT = 4;
    public static int edgeMatrixRowIdx;
    public final static int TREE_WIDTH = 4;
    public final static int TREE_HEIGHT = 4;
    public static int tree1MatrixRowIdx;
    public static int tree2MatrixRowIdx;
    public final static int WOOD_WIDTH = 8;
    public final static int WOOD_HEIGHT = 10;
    public static int wood1MatrixRowIdx;
    public static int wood2MatrixRowIdx;
    public final static int NB_SINGLE_OBSTABLE = 2;
    public static int singleObstacleMatrixRowIdx;
    public final static int NB_FLOWER_FRAME = 3;
    public static int flowerMatrixRowIdx;
    public final static int NB_SINGLE_BOOM = 1;
    public static int singleBoomMatrixRowIdx;
    public final static int PUDDLE_WIDTH = 7;
    public final static int PUDDLE_HEIGHT = 6;
    public static int puddle1MatrixRowIdx;
    public static int puddle2MatrixRowIdx;
    public final static int NB_SINGLE_PATHWAY = 20;
    public static int singlePathwayMatrixRowIdx;
    public final static int NB_SINGLE_MUTABLE = 3;
    public static int singleMutableMatrixRowIdx;

    public static int lastRowIdx; // for test purpose.

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
        int rowIdx = 0;
        imagesMatrix = new Image[NB_MATRIX_ROW][NB_MATRIX_COL];

        // bbmans:
        // - blue Bomber.
        for (int i = 0; i < NB_BBMAN_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/death_" + imageIdx + ".png");
        }
        blueBbManDeathMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WAIT_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/wait_" + imageIdx + ".png");
        }
        blueBbManWaitMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/walk_back_" + imageIdx + ".png");
        }
        blueBbManWalkBackMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/walk_front_" + imageIdx + ".png");
        }
        blueBbManWalkFrontMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/walk_left_" + imageIdx + ".png");
        }
        blueBbManWalkLeftMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/walk_right_" + imageIdx + ".png");
        }
        blueBbManWalkRightMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_BBMAN_WIN_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BBMAN_SKIN_DIR + "/blue/win_" + imageIdx + ".png");
        }
        blueBbManWinMatrixRowIdx = rowIdx++;

        // enemies:
        // - cloaked skeleton.
        for (int i = 0; i < NB_ENEMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(ENEMY_SKIN_DIR + "/cloaked_skeleton/walk_back_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkBackMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_ENEMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(ENEMY_SKIN_DIR + "/cloaked_skeleton/walk_front_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkFrontMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_ENEMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(ENEMY_SKIN_DIR + "/cloaked_skeleton/walk_left_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkLeftMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_ENEMY_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(ENEMY_SKIN_DIR + "/cloaked_skeleton/walk_right_" + imageIdx + ".png");
        }
        cloakedSkeletonWalkRightMatrixRowIdx = rowIdx++;

        // sprites:
        for (int i = 0; i < NB_BOMB_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BOMB_SKIN_DIR + "/bomb_" + imageIdx + ".png");
        }
        bombMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_FLAME_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(FLAME_SKIN_DIR + "/flame_" + imageIdx + ".png");
        }
        flameMatrixRowIdx = rowIdx++;
        for (int i = 0; i < NB_FLAME_END_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(FLAME_SKIN_DIR + "/flame_end_" + imageIdx + ".png");
        }
        flameEndMatrixRowIdx = rowIdx++;

        // scene:
        // - immutable elements.
        // -- castle (team 1 & 2).
        for (int i = 0; i < CASTLE_WIDTH * CASTLE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/castle/team_1/castle_t1_" + imageIdx + ".png");
        }
        castleT1MatrixRowIdx = rowIdx++;
        for (int i = 0; i < CASTLE_WIDTH * CASTLE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/castle/team_2/castle_t2_" + imageIdx + ".png");
        }
        castleT2MatrixRowIdx = rowIdx++;

        // -- edge.
        for (int i = 0; i < EDGE_WIDTH * EDGE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/edge/edge_" + imageIdx + ".png");
        }
        edgeMatrixRowIdx = rowIdx++;

        // -- tree (1 & 2).
        for (int i = 0; i < TREE_WIDTH * TREE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/tree_1/tree_1_" + imageIdx + ".png");
        }
        tree1MatrixRowIdx = rowIdx++;
        for (int i = 0; i < TREE_WIDTH * TREE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/tree_2/tree_2_" + imageIdx + ".png");
        }
        tree2MatrixRowIdx = rowIdx++;

        // -- wood (1 & 2).
        for (int i = 0; i < WOOD_WIDTH * WOOD_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/wood_1/wood_1_" + imageIdx + ".png");
        }
        wood1MatrixRowIdx = rowIdx++;
        for (int i = 0; i < WOOD_WIDTH * WOOD_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/wood_2/wood_2_" + imageIdx + ".png");
        }
        wood2MatrixRowIdx = rowIdx++;

        // -- single obstacle (1*1).
        for (int i = 0; i < NB_SINGLE_OBSTABLE; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/obstacle/obstacle_" + imageIdx + ".png");
        }
        singleObstacleMatrixRowIdx = rowIdx++;

        // -- boom (1*1).
        for (int i = 0; i < NB_SINGLE_BOOM; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/boom/boom_" + imageIdx + ".png");
        }
        singleBoomMatrixRowIdx = rowIdx++;

        // -- flower (animated).
        for (int i = 0; i < NB_FLOWER_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/flower/flower_" + imageIdx + ".png");
        }
        flowerMatrixRowIdx = rowIdx++;

        // -- puddle (1 & 2).
        for (int i = 0; i < PUDDLE_WIDTH * PUDDLE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/puddle_1/puddle_1_" + imageIdx + ".png");
        }
        puddle1MatrixRowIdx = rowIdx++;
        for (int i = 0; i < PUDDLE_WIDTH * PUDDLE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/puddle_2/puddle_2_" + imageIdx + ".png");
        }
        puddle2MatrixRowIdx = rowIdx++;

        // -- single pathway (1*1).
        for (int i = 0; i < NB_SINGLE_PATHWAY; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/pathway_" + imageIdx + ".png");
        }
        singlePathwayMatrixRowIdx = rowIdx++;

        // single mutable (1*1).
        for (int i = 0; i < NB_SINGLE_MUTABLE; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/mutable/mutable_" + imageIdx + ".png");
        }
        singleMutableMatrixRowIdx = rowIdx;
        lastRowIdx = rowIdx;
    }


    /**
     * @return a random single static pathway image.
     */
    public static Image getRandomSingleStaticPathway() {
        Random R = new Random(); // initStatement the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_PATHWAY);
        return ImagesLoader.imagesMatrix[ImagesLoader.singlePathwayMatrixRowIdx][imageIdx];
    }

    /**
     * @return a random single dynamic pathway image.
     */
    @SuppressWarnings("unchecked")
    public static Tuple3<Image[], Integer, Integer> getRandomSingleDynamicPathway() {
        return new Tuple3<>(ImagesLoader.imagesMatrix[ImagesLoader.flowerMatrixRowIdx],
                ImagesLoader.NB_FLOWER_FRAME, 100);
    }

    /**
     * @return a random single mutable image.
     */
    public static Image getRandomSingleMutable() {
        Random R = new Random(); // initStatement the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_MUTABLE);
        return ImagesLoader.imagesMatrix[ImagesLoader.singleMutableMatrixRowIdx][imageIdx];
    }

    /**
     * @return a random single obstacle image.
     */
    public static Image getRandomSingleObstacle() {
        Random R = new Random(); // initStatement the random function.
        int imageIdx = R.nextInt(ImagesLoader.NB_SINGLE_OBSTABLE);
        return ImagesLoader.imagesMatrix[ImagesLoader.singleObstacleMatrixRowIdx][imageIdx];
    }
}
