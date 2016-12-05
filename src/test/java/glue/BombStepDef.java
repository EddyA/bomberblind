package glue;

import static sprite.settled.abstracts.Settled.Status.STATUS_FINISHED;

import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import map.MapPoint;

public class BombStepDef implements WithAssertions {

    private final BombState bombState;
    private final MapPointMatrixState mapPointMatrixState;

    public BombStepDef(BombState bombState,
            MapPointMatrixState mapPointMatrixState) {
        this.bombState = bombState;
        this.mapPointMatrixState = mapPointMatrixState;
    }

    @And("^the bomb is exploding$")
    public void the_bomb_is_exploding() {
        Mockito.when(bombState.getBomb().isFinished()).thenReturn(true);
    }

    @Then("^the bomb should explode$")
    public void the_bomb_should_explode() {
        Mockito.when(bombState.getBomb().getCurStatus()).equals(STATUS_FINISHED);
    }

    @Then("^the bomb has exploded adding the following flames:$")
    public void the_bomb_has_exploded_adding_the_following_flames(List<MapPoint> entries) {
        assertThat(bombState.getBomb().isFinished()).isTrue();
        for (MapPoint entry : entries) {
            assertThat(mapPointMatrixState.getMapPoint(entry.getRowIdx(), entry.getColIdx()).isBurning()).isTrue();
        }
    }

    @Then("^the bomb should be removed from the sprite list$")
    public void the_bom_should_be_removed_from_the_sprite_list() {
        assertThat(bombState.isShouldBeRemoved()).isTrue();
    }
}
