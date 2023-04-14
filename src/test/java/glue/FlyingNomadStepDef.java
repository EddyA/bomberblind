package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import utils.Direction;

import static images.ImagesLoader.IMAGE_SIZE;
import static sprite.SpriteAction.ACTION_DYING;

public class FlyingNomadStepDef implements WithAssertions {

    private final MapPointMatrixState mapPointMatrixState;
    private final FlyingNomadState flyingNomadState;

    public FlyingNomadStepDef(MapPointMatrixState mapPointMatrixState,
                              FlyingNomadState flyingNomadState) {
        this.mapPointMatrixState = mapPointMatrixState;
        this.flyingNomadState = flyingNomadState;
    }

    @And("^the flying nomad is flying to the north")
    public void the_flying_nomad_is_flying_to_the_north() {
        flyingNomadState.getFlyingNomad().setCurDirection(Direction.DIRECTION_NORTH);
        flyingNomadState.getFlyingNomad().setLastDirection(Direction.DIRECTION_NORTH);
    }

    @And("^the flying nomad is flying to the south$")
    public void the_flying_nomad_is_flying_to_the_south() {
        flyingNomadState.getFlyingNomad().setCurDirection(Direction.DIRECTION_SOUTH);
        flyingNomadState.getFlyingNomad().setLastDirection(Direction.DIRECTION_SOUTH);
    }

    @And("^the flying nomad is flying to the west$")
    public void the_flying_nomad_is_flying_to_the_west() {
        flyingNomadState.getFlyingNomad().setCurDirection(Direction.DIRECTION_WEST);
        flyingNomadState.getFlyingNomad().setLastDirection(Direction.DIRECTION_WEST);
    }

    @And("^the flying nomad is flying to the east$")
    public void the_flying_nomad_is_flying_to_the_east() {
        flyingNomadState.getFlyingNomad().setCurDirection(Direction.DIRECTION_EAST);
        flyingNomadState.getFlyingNomad().setLastDirection(Direction.DIRECTION_EAST);
    }

    @And("^the flying nomad is out of the map from the north limit")
    public void the_flying_nomad_is_out_of_the_map_from_the_north_limit() {
        flyingNomadState.getFlyingNomad().setYMap(-flyingNomadState.getFlyingNomadHeight() - 1);
    }

    @And("^the flying nomad is out of the map from the south limit")
    public void the_flying_nomad_is_out_of_the_map_from_the_south_limit() {
        flyingNomadState.getFlyingNomad().setYMap(mapPointMatrixState.getMapHeight() * IMAGE_SIZE
                + flyingNomadState.getFlyingNomadHeight() + 1);
    }

    @And("^the flying nomad is out of the map from the west limit")
    public void the_flying_nomad_is_out_of_the_map_from_the_west_limit() {
        flyingNomadState.getFlyingNomad().setXMap(-flyingNomadState.getFlyingNomadWidth() - 1);
    }

    @And("^the flying nomad is out of the map from the east limit")
    public void the_flying_nomad_is_out_of_the_map_from_the_east_limit() {
        flyingNomadState.getFlyingNomad().setXMap(mapPointMatrixState.getMapWidth() * IMAGE_SIZE
                + flyingNomadState.getFlyingNomadHeight());
    }

    @And("^the flying nomad is dead$")
    public void the_flying_nomad_is_dead() {
        Mockito.when(flyingNomadState.getFlyingNomad().isFinished()).thenReturn(true);
    }

    @Then("^the flying nomad should move north$")
    public void the_flying_nomad_should_move_north() {
        assertThat(flyingNomadState.getFlyingNomad().getLastCoordinatesOnMap().getSecond())
                .isEqualTo(flyingNomadState.getFlyingNomad().getYMap() + 1);
    }

    @Then("^the flying nomad should move south$")
    public void the_flying_nomad_should_move_south() {
        assertThat(flyingNomadState.getFlyingNomad().getLastCoordinatesOnMap().getSecond())
                .isEqualTo(flyingNomadState.getFlyingNomad().getYMap() - 1);
    }

    @Then("^the flying nomad should move west$")
    public void the_flying_nomad_should_move_west() {
        assertThat(flyingNomadState.getFlyingNomad().getLastCoordinatesOnMap().getFirst())
                .isEqualTo(flyingNomadState.getFlyingNomad().getXMap() + 1);
    }

    @Then("^the flying nomad should move east$")
    public void the_flying_nomad_should_move_east() {
        assertThat(flyingNomadState.getFlyingNomad().getLastCoordinatesOnMap().getFirst())
                .isEqualTo(flyingNomadState.getFlyingNomad().getXMap() - 1);
    }

    @Then("^the flying nomad should die$")
    public void the_flying_nomad_should_die() {
        assertThat(flyingNomadState.getFlyingNomad().getCurSpriteAction()).isEqualTo(ACTION_DYING);
    }

    @Then("^the flying nomad should be marked as removable from the sprite list$")
    public void the_flying_nomad_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(flyingNomadState.isShouldBeRemoved()).isTrue();
    }
}
