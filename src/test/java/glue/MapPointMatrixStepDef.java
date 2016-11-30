package glue;

import org.assertj.core.api.WithAssertions;

import cucumber.api.java.en.Given;

public class MapPointMatrixStepDef implements WithAssertions {

    private final MapPointMatrixState mapPointMatrixState;

    public MapPointMatrixStepDef(MapPointMatrixState mapPointMatrixState) {
        this.mapPointMatrixState = mapPointMatrixState;
    }

    @Given("^a MapPoint matrix of (\\d+) rows and (\\d+) cols built with pathway cases$")
    public void a_map_of_rows_and_cols(int nbRows, int nbCols) {
        mapPointMatrixState.initMapPointMatrixState(nbCols, nbRows);
    }

    @Given("^a burning case at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_burning_case_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).addFlame();
    }

    @Given("^an obstacle case at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void an_obstacle_case_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setPathway(false);
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setMutable(false);
    }
}
