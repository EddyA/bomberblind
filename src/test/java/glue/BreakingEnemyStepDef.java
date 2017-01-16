package glue;

import static utils.Action.ACTION_BREAKING;
import static utils.Action.ACTION_DYING;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import utils.Direction;

public class BreakingEnemyStepDef  implements WithAssertions {

    private final BreakingEnemyState breakingEnemyState;

    public BreakingEnemyStepDef(BreakingEnemyState breakingEnemyState) {
        this.breakingEnemyState = breakingEnemyState;
    }

    @And("^the breaking enemy is walking to the south$")
    public void the_breaking_enemy_is_breaking_toward() {
        breakingEnemyState.getEnemy().setCurDirection(Direction.SOUTH);
        breakingEnemyState.getEnemy().setLastDirection(Direction.SOUTH);
    }

    @And("^the breaking enemy is dead$")
    public void the_breaking_enemy_is_dead() {
        Mockito.when(breakingEnemyState.getEnemy().isFinished()).thenReturn(true);
    }

    @Then("^the breaking enemy should die$")
    public void the_breaking_enemy_should_die() {
        assertThat(breakingEnemyState.getEnemy().getCurAction()).isEqualTo(ACTION_DYING);
    }

    @Then("^the breaking enemy should get another direction$")
    public void the_breaking_enemy_should_get_another_direction() {
        assertThat(breakingEnemyState.getEnemy().getCurDirection())
                .isNotEqualTo(breakingEnemyState.getEnemy().getLastDirection());
    }

    @Then("^the breaking enemy should break$")
    public void the_breaking_enemy_should_break_the() {
        assertThat(breakingEnemyState.getEnemy().getCurAction()).isEqualTo(ACTION_BREAKING);
    }

    @And("^the breaking sprite is done$")
    public void the_breaking_sprite_is_down() {
        Mockito.when(breakingEnemyState.getEnemy().isBreakingSpriteFinished()).thenReturn(true);
    }

    @Then("^the breaking enemy should be marked as removable from the sprite list$")
    public void the_breaking_enemy_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(breakingEnemyState.isShouldBeRemoved()).isTrue();
    }
}
