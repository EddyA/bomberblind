package glue;

import map.MapPoint;

public class MapPointMatrixState {

    private MapPoint[][] mapPointMatrix;
    private int mapWidth;
    private int mapHeight;

    public void initMapPointMatrixState(int mapWidth, int mapHeight) {

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapPointMatrix = new MapPoint[mapHeight][mapWidth];
        for (int rowIdx = 0; rowIdx < mapHeight; rowIdx++) {
            for (int colIdx = 0; colIdx < mapWidth; colIdx++) {
                this.mapPointMatrix[rowIdx][colIdx] = new MapPoint(rowIdx, colIdx);
                this.mapPointMatrix[rowIdx][colIdx].setPathway(true);
            }
        }
    }

    public MapPoint[][] getMapPointMatrix() {
        return mapPointMatrix;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public MapPoint getMapPoint(int rowIdx, int colIdx) {
        return mapPointMatrix[rowIdx][colIdx];
    }
}
