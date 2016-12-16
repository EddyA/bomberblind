package glue;

import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.mockito.Mockito;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import map.MapPoint;
import sprite.SpriteType;
import utils.Tools;

public class BombStepDef implements WithAssertions {

    private final BombState bombState;
    private final SpriteListState listOfSprites;

    public BombStepDef(BombState bombState,
            SpriteListState listOfSprites) {
        this.bombState = bombState;
        this.listOfSprites = listOfSprites;
    }

    @And("^the bomb is finished$")
    public void the_bomb_is_finishing() {
        Mockito.when(bombState.getBomb().isFinished()).thenReturn(true);
    }

    @Then("^the bomb should be finished")
    public void the_bomb_should_finish() {
        assertThat(bombState.getBomb().isFinished()).isTrue();
    }

    @Then("^the following flames should be added:$")
    public void the_bomb_should_add_the_following_flames(List<MapPoint> entries) {
        // only the presence of the sprites in the list is checked,
        // the status cases are already checked in the adding methods tests.
        for (MapPoint entry : entries) {
            assertThat(listOfSprites.isSpriteInSpriteList(
                    Tools.getCaseCentreAbscissa(entry.getColIdx()),
                    Tools.getCaseBottomOrdinate(entry.getRowIdx()),
                    SpriteType.FLAME)).isTrue();
        }
    }

    @Then("^the bomb should be marked as removable from the sprite list$")
    public void the_bomb_should_be_marked_as_removable_from_the_sprite_list() {
        assertThat(bombState.isShouldBeRemoved()).isTrue();
    }
}
