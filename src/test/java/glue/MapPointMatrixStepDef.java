package glue;

import org.assertj.core.api.WithAssertions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

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

    @Given("^an immutable obstacle at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void an_immutable_obstacle_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setPathway(false);
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setMutable(false);
    }

    @Given("^a mutable obstacle at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_mutable_obstacle_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setPathway(false);
        mapPointMatrixState.getMapPoint(rowIdx, colIdx).setMutable(true);
    }

    @Then("^the case at rowIdx (\\d+) and coldIdx (\\d+) is no more bombing$")
    public void the_case_at_rowIdx_and_coldIdx_is_no_more_bombing(int rowIdx, int colIdx) {
        assertThat(mapPointMatrixState.getMapPoint(rowIdx, colIdx).isBombing()).isFalse();
    }

    @Then("^the case at rowIdx (\\d+) and coldIdx (\\d+) is no more burning$")
    public void the_case_at_rowIdx_and_coldIdx_is_no_more_burning(int rowIdx, int colIdx) {
        assertThat(mapPointMatrixState.getMapPoint(rowIdx, colIdx).isBurning()).isFalse();
    }

    @Then("^the case at rowIdx (\\d+) and coldIdx (\\d+) is no more bonusing")
    public void the_case_at_rowIdx_and_coldIdx_is_no_more_bonusing(int rowIdx, int colIdx) {
        assertThat(mapPointMatrixState.getMapPoint(rowIdx, colIdx).isBonusing()).isFalse();
    }
}
