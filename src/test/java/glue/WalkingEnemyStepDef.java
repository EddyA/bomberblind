package glue;

import static sprite.SpriteAction.ACTION_DYING;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import utils.Direction;

public class WalkingEnemyStepDef implements WithAssertions {

    private final WalkingEnemyState walkingEnemyState;

    public WalkingEnemyStepDef(WalkingEnemyState walkingEnemyState) {
        this.walkingEnemyState = walkingEnemyState;
    }

    @And("^the walking enemy is walking to the south$")
    public void the_walking_enemy_is_walking_toward() {
        walkingEnemyState.getEnemy().setCurDirection(Direction.DIRECTION_SOUTH);
        walkingEnemyState.getEnemy().setLastDirection(Direction.DIRECTION_SOUTH);
    }

    @And("^the walking enemy is dead$")
    public void the_walking_enemy_is_dead() {
        Mockito.when(walkingEnemyState.getEnemy().isFinished()).thenReturn(true);
    }

    @Then("^the walking enemy should die$")
    public void the_walking_enemy_should_die() {
        assertThat(walkingEnemyState.getEnemy().getCurSpriteAction()).isEqualTo(ACTION_DYING);
    }

    @Then("^the walking enemy should get another direction$")
    public void the_walking_enemy_should_get_another_direction() {
        assertThat(walkingEnemyState.getEnemy().getCurDirection())
                .isNotEqualTo(walkingEnemyState.getEnemy().getLastDirection());
    }

    @Then("^the walking enemy should be marked as removable from the sprite list$")
    public void the_walking_enemy_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(walkingEnemyState.isShouldBeRemoved()).isTrue();
    }
}
