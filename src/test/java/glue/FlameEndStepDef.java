package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class FlameEndStepDef implements WithAssertions {

    private final FlameEndState flameEndState;

    public FlameEndStepDef(FlameEndState flameEndState) {
        this.flameEndState = flameEndState;
    }

    @And("^the flame end is finished$")
    public void the_flame_end_is_finished() {
        Mockito.when(flameEndState.getFlameEnd().isFinished()).thenReturn(true);
    }

    @Then("^the flame end should be marked as removable from the sprite list$")
    public void the_flame_end_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(flameEndState.isShouldBeRemoved()).isTrue();
    }
}
