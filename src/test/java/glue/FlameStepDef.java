package glue;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import sprite.SpriteType;
import utils.Tools;

public class FlameStepDef implements WithAssertions {

    private final FlameState flameState;
    private final SpriteListState listOfSprites;

    public FlameStepDef(FlameState flameState,
            SpriteListState listOfSprites) {
        this.flameState = flameState;
        this.listOfSprites = listOfSprites;
    }

    @And("^the flame is finished$")
    public void the_flame_is_finished() {
        Mockito.when(flameState.getFlame().isFinished()).thenReturn(true);
    }

    @Then("^a flame end should be added at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_flame_end_should_be_added_at_rowIdx_and_colIdx(int rowIdx, int colIdx) {

        // check in the list of sprites.
        assertThat(listOfSprites.isSpriteInSpriteList(
                Tools.getCaseCentreAbscissa(colIdx),
                Tools.getCaseBottomOrdinate(rowIdx),
                SpriteType.FLAME_END)).isTrue();
    }

    @Then("^the flame should be marked as removable from the sprite list$")
    public void the_flame_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(flameState.isShouldBeRemoved()).isTrue();
    }
}
