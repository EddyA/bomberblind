package glue;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import sprite.SpriteType;
import spriteList.ctrl.AddingMethods;
import utils.Tools;

import java.util.List;

public class AddingMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;

    private final BombState bombState;
    private final BomberState bomberState;
    private final BonusBombState bonusBombState;
    private final BonusFlameState bonusFlameState;
    private final BonusHeartState bonusHeartState;
    private final BonusRollerState bonusRollerState;
    private final BreakingEnemyState breakingEnemyState;
    private final FlameState flameState;
    private final FlameEndState flameEndState;
    private final FlyingNomadState flyingNomadState;
    private final WalkingEnemyState walkingEnemyState;

    public AddingMethodsStepDef(SpriteListState listOfSprites,
                                MapPointMatrixState mapPointMatrixState,
                                BombState bombState,
                                BomberState bomberState,
                                BonusBombState bonusBombState,
                                BonusFlameState bonusFlameState,
                                BonusHeartState bonusHeartState,
                                BonusRollerState bonusRollerState,
                                BreakingEnemyState breakingEnemyState,
                                FlameState flameState,
                                FlameEndState flameEndState,
                                FlyingNomadState flyingNomadState,
                                WalkingEnemyState walkingEnemyState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bombState = bombState;
        this.bomberState = bomberState;
        this.bonusBombState = bonusBombState;
        this.bonusFlameState = bonusFlameState;
        this.bonusHeartState = bonusHeartState;
        this.bonusRollerState = bonusRollerState;
        this.breakingEnemyState = breakingEnemyState;
        this.flameState = flameState;
        this.flameEndState = flameEndState;
        this.flyingNomadState = flyingNomadState;
        this.walkingEnemyState = walkingEnemyState;
    }

    @Given("^a bomb at rowIdx (\\d+) and coldIdx (\\d+) and a flame size of (\\d+)$")
    public void a_bomb_at_rowIdx_and_coldIdx_with_a_flame_size_of(int rowIdx, int colIdx, int flameSize) {
        bombState.getBomb().setRowIdx(rowIdx);
        bombState.getBomb().setColIdx(colIdx);
        bombState.getBomb().setFlamesize(flameSize);
        AddingMethods.addBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bombState.getBomb());
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

    @Given("^a bonus bomb at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bonus_bomb_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bonusBombState.getBonusBomb().setRowIdx(rowIdx);
        bonusBombState.getBonusBomb().setColIdx(colIdx);
        bonusBombState.getBonusBomb().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bonusBombState.getBonusBomb().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBonus(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bonusBombState.getBonusBomb());
    }

    @Given("^a bonus flame at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bonus_flame_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bonusFlameState.getBonusFlame().setRowIdx(rowIdx);
        bonusFlameState.getBonusFlame().setColIdx(colIdx);
        bonusFlameState.getBonusFlame().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bonusFlameState.getBonusFlame().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBonus(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bonusFlameState.getBonusFlame());
    }

    @Given("^a bonus heart at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bonus_heart_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bonusHeartState.getBonusHeart().setRowIdx(rowIdx);
        bonusHeartState.getBonusHeart().setColIdx(colIdx);
        bonusHeartState.getBonusHeart().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bonusHeartState.getBonusHeart().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBonus(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bonusHeartState.getBonusHeart());
    }

    @Given("^a bonus roller at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bonus_roller_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bonusRollerState.getBonusRoller().setRowIdx(rowIdx);
        bonusRollerState.getBonusRoller().setColIdx(colIdx);
        bonusRollerState.getBonusRoller().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        bonusRollerState.getBonusRoller().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBonus(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bonusRollerState.getBonusRoller());
    }

    @Given("^a breaking enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_breaking_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        breakingEnemyState.getEnemy().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        breakingEnemyState.getEnemy().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBreakingEnemy(listOfSprites.getSpriteList(), breakingEnemyState.getEnemy());
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

    @Given("^a flying nomad at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_flying_nomad_at_rowIdx_and_coldIdx_going_north(int rowIdx, int colIdx) {
        flyingNomadState.getFlyingNomad().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        flyingNomadState.getFlyingNomad().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addFlyingNomad(listOfSprites.getSpriteList(), flyingNomadState.getFlyingNomad());
    }

    @Given("^a walking enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_walking_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        walkingEnemyState.getEnemy().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        walkingEnemyState.getEnemy().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addWalkingEnemy(listOfSprites.getSpriteList(), walkingEnemyState.getEnemy());
    }
}
