package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import sprite.nomad.Enemy;
import utils.Direction;

public class EnemyStepDef implements WithAssertions {

    private final EnemyState enemyState;

    public EnemyStepDef(EnemyState enemyState) {
        this.enemyState = enemyState;
    }

    @And("^the enemy is walking to the south$")
    public void the_enemy_is_walking_toward() {
        enemyState.getEnemy().setCurDirection(Direction.SOUTH);
        enemyState.getEnemy().setLastDirection(Direction.SOUTH);
    }

    @And("^the enemy is dead$")
    public void the_enemy_is_dead() {
        Mockito.when(enemyState.getEnemy().isFinished()).thenReturn(true);
    }

    @Then("^the enemy should die$")
    public void the_enemy_should_die() {
        assertThat(enemyState.getEnemy().getCurAction()).isEqualTo(Enemy.Action.ACTION_DYING);
    }

    @Then("^the enemy should get another direction$")
    public void the_enemy_should_get_another_direction() {
        assertThat(enemyState.getEnemy().getCurDirection()).isNotEqualTo(enemyState.getEnemy().getLastDirection());
    }

    @Then("^the enemy should be marked as removable from the sprite list$")
    public void the_enemy_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(enemyState.isShouldBeRemoved()).isTrue();
    }
}
