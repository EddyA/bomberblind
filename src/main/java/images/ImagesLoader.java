package images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Load the images of the game in a matrix of type 'Image'.
 * The 'Image' are loaded in a 'MediaTracker' to speed up the displaying and avoid sparkling.
 */
public class ImagesLoader {

    public static Image[][] imagesMatrix; // matrix of images (holding all the game images).
    private final static int NB_MATRIX_ROW = 53;
    private final static int NB_MATRIX_COL = 80;

    public final static int IMAGE_SIZE = 30; // size of an 'Image' in pixels (30*30).

    public static BbManSprites bbManSprites1; // bbman sprites for team 1.

    // images location.
    private final static String CHAR_SKIN_DIR = "/images/bbman";
    private final static String BOMB_SKIN_DIR = "/images/bomb";
    private final static String FLAME_SKIN_DIR = "/images/flame";
    private final static String SCENE_SKIN_DIR = "/images/scene";

    // images information.

    // - bbmans.
    public static int deathT1MatrixRowIdx;
    public final static int NB_DEATH_FRAME = 10;
    public static int waitT1MatrixRowIdx;
    public final static int NB_WAIT_FRAME = 4;
    public static int walkBackT1MatrixRowIdx;
    public static int walkFrontT1MatrixRowIdx;
    public static int walkLeftT1MatrixRowIdx;
    public static int walkRightT1MatrixRowIdx;
    public final static int NB_WALK_FRAME = 4;
    public static int winT1MatrixRowIdx;
    public final static int NB_WIN_FRAME = 8;

    // - bomb.
    public static int bombMatrixRowIdx;
    public final static int NB_BOMB_FRAME = 4;

    // - flame.
    public static int flameMatrixRowIdx;
    public final static int NB_FLAME_FRAME = 3;

    // - scene.
    public static int castleT1MatrixRowIdx;
    public static int castleT2MatrixRowIdx;
    public final static int CASTLE_WIDTH = 6;
    public final static int CASTLE_HEIGHT = 5;
    public static int edgeMatrixRowIdx;
    public final static int EDGE_WIDTH = 1;
    public final static int EDGE_HEIGHT = 4;
    public static int tree1MatrixRowIdx;
    public static int tree2MatrixRowIdx;
    public final static int TREE_WIDTH = 4;
    public final static int TREE_HEIGHT = 4;
    public static int wood1MatrixRowIdx;
    public static int wood2MatrixRowIdx;
    public final static int WOOD_WIDTH = 8;
    public final static int WOOD_HEIGHT = 10;
    public static int singleObstacleMatrixRowIdx;
    public final static int NB_SINGLE_OBSTABLE = 2;
    public static int flowerMatrixRowIdx;
    public final static int NB_FLOWER_FRAME = 3;
    public static int boomMatrixRowIdx;
    public final static int NB_BOOM = 1;
    public static int puddle1MatrixRowIdx;
    public static int puddle2MatrixRowIdx;
    public final static int PUDDLE_WIDTH = 7;
    public final static int PUDDLE_HEIGHT = 6;
    public static int singlePathwayMatrixRowIdx;
    public final static int NB_SINGLE_PATHWAY = 20;
    public static int foodMatrixRowIdx;
    public final static int FOOD_WIDTH = 5;
    public final static int FOOD_HEIGHT = 3;
    public static int stoneMatrixRowIdx;
    public final static int STONE_WIDTH = 5;
    public final static int STONE_HEIGHT = 3;
    public static int singleMutableMatrixRowIdx;
    public final static int NB_SINGLE_MUTABLE = 3;

    /**
     * Create an 'Image' based to a relative path (from 'resources' folder).
     *
     * @param relativePath relative path to an image
     * @return the created 'Image'
     * @throws IOException if the file does not exist
     */
    private static Image createImage(String relativePath) throws IOException {
        URL imageURL = ImagesLoader.class.getResource(relativePath);
        if (imageURL != null) {
            return ImageIO.read(imageURL);
        } else {
            throw new IOException("\nfile not found: " + relativePath + "\n");
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
        // - death.
        for (int i = 0; i < NB_DEATH_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/death_t1_" + imageIdx + ".png");
        }
        deathT1MatrixRowIdx = rowIdx++;

        // - wait.
        for (int i = 0; i < NB_WAIT_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/wait_t1_" + imageIdx + ".png");
        }
        waitT1MatrixRowIdx = rowIdx++;

        // - walk.
        // -- back.
        for (int i = 0; i < NB_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/walk_back_t1_" + imageIdx + ".png");
        }
        walkBackT1MatrixRowIdx = rowIdx++;

        // -- front.
        for (int i = 0; i < NB_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/walk_front_t1_" + imageIdx + ".png");
        }
        walkFrontT1MatrixRowIdx = rowIdx++;

        // -- left.
        for (int i = 0; i < NB_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/walk_left_t1_" + imageIdx + ".png");
        }
        walkLeftT1MatrixRowIdx = rowIdx++;

        // -- right.
        for (int i = 0; i < NB_WALK_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/walk_right_t1_" + imageIdx + ".png");
        }
        walkRightT1MatrixRowIdx = rowIdx++;

        // - win.
        for (int i = 0; i < NB_WIN_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(CHAR_SKIN_DIR + "/team_1/win_t1_" + imageIdx + ".png");
        }
        winT1MatrixRowIdx = rowIdx++;

        // bomb:
        for (int i = 0; i < NB_BOMB_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(BOMB_SKIN_DIR + "/bomb_" + imageIdx + ".png");
        }
        bombMatrixRowIdx = rowIdx++;

        // flame:
        for (int i = 0; i < NB_FLAME_FRAME; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(FLAME_SKIN_DIR + "/flame_" + imageIdx + ".png");
        }
        flameMatrixRowIdx = rowIdx++;

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
        for (int i = 0; i < NB_BOOM; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/pathway/boom/boom_" + imageIdx + ".png");
        }
        boomMatrixRowIdx = rowIdx++;

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

        // -- food.
        for (int i = 0; i < FOOD_WIDTH * FOOD_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/resource/food/food_" + imageIdx + ".png");
        }
        foodMatrixRowIdx = rowIdx++;

        // -- stone.
        for (int i = 0; i < STONE_WIDTH * STONE_HEIGHT; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/immutable/resource/stone/stone_" + imageIdx + ".png");
        }
        stoneMatrixRowIdx = rowIdx++;

        // single mutable (1*1).
        for (int i = 0; i < NB_SINGLE_MUTABLE; i++) {
            String imageIdx = String.format("%2s", i + 1).replace(' ', '0');
            imagesMatrix[rowIdx][i] = createImage(SCENE_SKIN_DIR + "/mutable/mutable_" + imageIdx + ".png");
        }
        singleMutableMatrixRowIdx = rowIdx;
    }

    /**
     * Set collections if images for the BbMan sprites.
     */
    public static void setBbManSprites() {

        // BbMan of the team 1.
        bbManSprites1 = new BbManSprites(
                imagesMatrix[deathT1MatrixRowIdx],
                NB_DEATH_FRAME,
                imagesMatrix[waitT1MatrixRowIdx],
                NB_WAIT_FRAME,
                imagesMatrix[walkBackT1MatrixRowIdx],
                imagesMatrix[walkFrontT1MatrixRowIdx],
                imagesMatrix[walkLeftT1MatrixRowIdx],
                imagesMatrix[walkRightT1MatrixRowIdx],
                NB_WALK_FRAME,
                imagesMatrix[winT1MatrixRowIdx],
                NB_WIN_FRAME);
    }
}
