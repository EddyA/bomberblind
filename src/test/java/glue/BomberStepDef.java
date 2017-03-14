package glue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;
import sprite.settled.BonusBundle;
import sprite.settled.BonusType;
import utils.Tools;

import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_WAITING;

public class BomberStepDef implements WithAssertions {

    private final BomberState bomberState;

    public BomberStepDef(BomberState bomberState) {
        this.bomberState = bomberState;
    }

    @And("^the bomber move at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void the_bomber_move_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bomberState.getBomber().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bomberState.getBomber().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
    }

    @And("^the bomber has (\\d+) lifes$")
    public void the_bomber_has_lifes(int nbLifes) {
        bomberState.getBomber().setBonus(BonusType.TYPE_BONUS_HEART, nbLifes);
    }

    @And("^the bomber is invincible$")
    public void the_bomber_is_invincible() {
        Mockito.when(bomberState.getBomber().isInvincible()).thenReturn(true);
    }

    @And("^the bomber is dead$")
    public void the_bomber_is_dead() {
        Mockito.when(bomberState.getBomber().isFinished()).thenReturn(true);
    }

    @Then("^the bomber should die$")
    public void the_bomber_should_die() {
        assertThat(bomberState.getBomber().getCurSpriteAction()).isEqualTo(ACTION_DYING);
    }

    @Then("^the bomber should not die$")
    public void the_bomber_should_not_die() {
        assertThat(bomberState.getBomber().getCurSpriteAction()).isNotEqualTo(ACTION_DYING);
    }

    @Then("^the bomber should have 1 bonus heart less$")
    public void the_bomber_should_have_1_bonus_heart_less() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_HEART))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_HEART - 1);
    }

    @Then("^the bomber should have 1 bonus bomb more$")
    public void the_bomber_should_have_1_bonus_bomb_more() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_BOMB))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_BOMB + 1);
    }

    @Then("^the bomber should have 1 bonus flame more$")
    public void the_bomber_should_have_1_bonus_flame_more() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_FLAME))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_FLAME + 1);
    }

    @Then("^the bomber should have 1 bonus heart more$")
    public void the_bomber_should_have_1_bonus_heart_more() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_HEART))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_HEART + 1);
    }

    @Then("^the bomber should have 1 bonus roller more$")
    public void the_bomber_should_have_1_bonus_roller_more() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_ROLLER))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_ROLLER + 1);
    }

    @Then("^the bomber should have its bonus rested$")
    public void the_bomber_should_have_it_bonus_reseted() {
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_BOMB))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_BOMB);
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_FLAME))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_FLAME);
        assertThat(bomberState.getBomber().getBonus(BonusType.TYPE_BONUS_ROLLER))
                .isEqualTo(BonusBundle.DEFAULT_NB_BONUS_ROLLER);
    }

    @Then("^the bomber is re-init$")
    public void the_bomber_is_reset() {
        assertThat(bomberState.getBomber().getxMap()).isEqualTo(bomberState.getBomber().getInitialXMap());
        assertThat(bomberState.getBomber().getyMap()).isEqualTo(bomberState.getBomber().getInitialYMap());
        assertThat(bomberState.getBomber().getCurSpriteAction()).isEqualTo(ACTION_WAITING);
        assertThat(bomberState.getBomber().isInvincible()).isTrue();
    }

    @Then("^the bomber should be marked as removable from the sprite list$")
    public void the_bomber_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(bomberState.isShouldBeRemoved()).isTrue();
    }
}
