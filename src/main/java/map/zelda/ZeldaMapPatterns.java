package map.zelda;

import images.ImagesLoader;
import lombok.experimental.UtilityClass;
import map.MapPattern;

/**
 * Create patterns for all the graphical elements (except for simple pathway - 1*1).
 * These patterns will be used to dynamically generate the map.
 */
@UtilityClass
public class ZeldaMapPatterns {

    // immutable obstacles.
    public static final MapPattern castle = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.castleMatrixRowIdx], // entrance.
            ImagesLoader.CASTLE_WIDTH, ImagesLoader.CASTLE_HEIGHT, false, false, true, false, "castle");
    public static final MapPattern edge = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.edgeMatrixRowIdx],
            ImagesLoader.EDGE_WIDTH, ImagesLoader.EDGE_HEIGHT, false, false, false, false, "edge");
    public static final MapPattern greenTree = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.greenTreeMatrixRowIdx],
            ImagesLoader.GREEN_TREE_WIDTH, ImagesLoader.GREEN_TREE_HEIGHT, false, false, false, false, "green_tree");
    public static final MapPattern orchad = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.orchardMatrixRowIdx],
            ImagesLoader.ORCHARD_WIDTH, ImagesLoader.ORCHARD_HEIGHT, false, false, false, false, "orchad");
    public static final MapPattern redTree = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.redTreeMatrixRowIdx],
            ImagesLoader.RED_TREE_WIDTH, ImagesLoader.RED_TREE_HEIGHT, false, false, false, false, "red_tree");
    public static final MapPattern statue = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.statueMatrixRowIdx],
            ImagesLoader.STATUE_WIDTH, ImagesLoader.STATUE_HEIGHT, false, false, false, false, "statue");
    public static final MapPattern trough = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.troughMatrixRowIdx],
            ImagesLoader.TROUGH_WIDTH, ImagesLoader.TROUGH_HEIGHT, false, false, false, false, "trough");
    public static final MapPattern trunk = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.trunkMatrixRowIdx], // exit.
            ImagesLoader.TRUNK_WIDTH, ImagesLoader.TRUNK_HEIGHT, false, false, false, true, "trunk");
    public static final MapPattern yellowTree = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.yellowTreeMatrixRowIdx],
            ImagesLoader.YELLOW_TREE_WIDTH, ImagesLoader.YELLOW_TREE_HEIGHT, false, false, false, false, "yellow_tree");

    // pathways.
    public static final MapPattern pathway = new MapPattern(ImagesLoader.imagesMatrix[ImagesLoader.pathwayMatrixRowIdx],
            ImagesLoader.PATHWAY_WIDTH, ImagesLoader.PATHWAY_HEIGHT, true, false, false, false, "pathway");
}
