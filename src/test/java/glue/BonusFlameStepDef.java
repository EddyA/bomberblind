package glue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

public class BonusFlameStepDef implements WithAssertions {

    private final BonusFlameState bonusFlameState;

    public BonusFlameStepDef(BonusFlameState bonusFlameState) {
        this.bonusFlameState = bonusFlameState;
    }

    @And("^the bonus flame is finished$")
    public void the_bonus_flame_is_finishing() {
        Mockito.when(bonusFlameState.getBonusFlame().isFinished()).thenReturn(true);
    }

    @Then("^the bonus flame should be finished$")
    public void the_bonus_flame_should_finish() {
        assertThat(bonusFlameState.getBonusFlame().isFinished()).isTrue();
    }

    @Then("^the bonus flame should be marked as removable from the sprite list$")
    public void the_bonus_flame_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(bonusFlameState.isShouldBeRemoved()).isTrue();
    }
}
