package glue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;
import utils.Tools;

import static utils.Action.ACTION_DYING;
import static utils.Action.ACTION_WAITING;

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
        assertThat(bomberState.getBomber().getCurAction()).isEqualTo(ACTION_DYING);
    }

    @Then("^the bomber should not die$")
    public void the_bomber_should_not_die() {
        assertThat(bomberState.getBomber().getCurAction()).isNotEqualTo(ACTION_DYING);
    }

    @Then("^the bomber is re-init$")
    public void the_bomber_is_reset() {
        assertThat(bomberState.getBomber().getxMap()).isEqualTo(bomberState.getBomber().getInitialXMap());
        assertThat(bomberState.getBomber().getyMap()).isEqualTo(bomberState.getBomber().getInitialYMap());
        assertThat(bomberState.getBomber().getCurAction()).isEqualTo(ACTION_WAITING);
        assertThat(bomberState.getBomber().isInvincible()).isTrue();
    }
}
