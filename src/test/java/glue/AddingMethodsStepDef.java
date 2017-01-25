package glue;

import java.util.List;

import org.assertj.core.api.WithAssertions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import map.MapPoint;
import sprite.SpriteType;
import spriteList.ctrl.AddingMethods;
import utils.Tools;

public class AddingMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;
    private final BomberState bomberState;
    private final WalkingEnemyState walkingEnemyState;
    private final BreakingEnemyState breakingEnemyState;
    private final BombState bombState;
    private final FlameState flameState;
    private final FlameEndState flameEndState;

    public AddingMethodsStepDef(SpriteListState listOfSprites,
            MapPointMatrixState mapPointMatrixState,
            BomberState bomberState,
            WalkingEnemyState walkingEnemyState,
            BreakingEnemyState breakingEnemyState,
            BombState bombState,
            FlameState flameState,
            FlameEndState flameEndState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bomberState = bomberState;
        this.walkingEnemyState = walkingEnemyState;
        this.breakingEnemyState = breakingEnemyState;
        this.bombState = bombState;
        this.flameState = flameState;
        this.flameEndState = flameEndState;
    }

    @Given("^a bomber at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bomber_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bomberState.getBomber().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bomberState.getBomber().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        bomberState.getBomber().setInitialXMap(Tools.getCaseCentreAbscissa(colIdx));
        bomberState.getBomber().setInitialYMap(Tools.getCaseBottomOrdinate(rowIdx));
        bomberState.getBomber().setLastInvincibilityTs(0); // deactivate the invincibility at init.
        AddingMethods.addBomber(listOfSprites.getSpriteList(), bomberState.getBomber());
    }

    @Given("^a walking enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_walking_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        walkingEnemyState.getEnemy().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        walkingEnemyState.getEnemy().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addWalkingEnemy(listOfSprites.getSpriteList(), walkingEnemyState.getEnemy());
    }

    @Given("^a breaking enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_breaking_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        breakingEnemyState.getEnemy().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        breakingEnemyState.getEnemy().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBreakingEnemy(listOfSprites.getSpriteList(), breakingEnemyState.getEnemy());
    }

    @Given("^a bomb at rowIdx (\\d+) and coldIdx (\\d+) and a flame size of (\\d+)$")
    public void a_bomb_at_rowIdx_and_coldIdx_with_a_flame_size_of(int rowIdx, int colIdx, int flameSize) {
        bombState.getBomb().setRowIdx(rowIdx);
        bombState.getBomb().setColIdx(colIdx);
        bombState.getBomb().setFlamesize(flameSize);
        AddingMethods.addBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bombState.getBomb());
    }

    @Given("^a flame at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_flame_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        flameState.getFlame().setRowIdx(rowIdx);
        flameState.getFlame().setColIdx(colIdx);
        AddingMethods.addFlame(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), flameState.getFlame());
    }

    @Given("^a flame end at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_flame_end_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        flameEndState.getFlameEnd().setRowIdx(rowIdx);
        flameEndState.getFlameEnd().setColIdx(colIdx);
        AddingMethods.addFlameEnd(listOfSprites.getSpriteList(), flameEndState.getFlameEnd());
    }

    @Then("^the following flames should be added:$")
    public void the_following_flames_should_be_added(List<MapPoint> entries) {
        // only the presence of the sprites in the list is checked,
        // the status cases are already checked in the adding methods tests.
        for (MapPoint entry : entries) {
            assertThat(listOfSprites.isSpriteInSpriteList(
                    Tools.getCaseCentreAbscissa(entry.getColIdx()),
                    Tools.getCaseBottomOrdinate(entry.getRowIdx()),
                    SpriteType.TYPE_FLAME)).isTrue();
        }
    }
}
