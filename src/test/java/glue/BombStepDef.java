package glue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import java.util.List;

import static sprite.settled.abstracts.TimedSettled.Status.STATUS_ENDED;

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

    @Then("^the bomb should end")
    public void the_bomb_should_end() {
        assertThat(bombState.getBomb().getCurStatus()).isEqualTo(STATUS_ENDED);
    }

    @Then("^the following flames should be added:$")
    public void the_bomb_should_add_the_following_flames(List<MapPoint> entries) {
        for (MapPoint entry : entries) {
            assertThat(mapPointMatrixState.getMapPoint(entry.getRowIdx(), entry.getColIdx()).isBurning()).isTrue();
        }
    }

    @Then("^the bomb should be marked as removable from the sprite list$")
    public void the_bomb_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(bombState.isShouldBeRemoved()).isTrue();
    }
}
