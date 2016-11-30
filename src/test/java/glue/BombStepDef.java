package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import utils.Tools;

public class BombStepDef implements WithAssertions {

    private final BombState bombState;

    public BombStepDef(BombState bombState) {
        this.bombState = bombState;
    }

    @And("^the bomb is exploding$")
    public void the_bomber_is_exploding() {
        Mockito.when(bombState.getBomb().isFinished()).thenReturn(true);
    }
}
