package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class BombStepDef implements WithAssertions {

    private final BombState bombState;

    public BombStepDef(BombState bombState) {
        this.bombState = bombState;
    }

    @And("^the bomb is finished$")
    public void the_bomb_is_finishing() {
        Mockito.when(bombState.getBomb().isFinished()).thenReturn(true);
    }

    @Then("^the bomb should be finished$")
    public void the_bomb_should_finish() {
        assertThat(bombState.getBomb().isFinished()).isTrue();
    }

    @Then("^the bomb should be marked as removable from the sprite list$")
    public void the_bomb_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(bombState.isShouldBeRemoved()).isTrue();
    }
}
