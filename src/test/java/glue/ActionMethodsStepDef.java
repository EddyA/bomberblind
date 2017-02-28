package glue;

import cucumber.api.java.en.When;
import org.assertj.core.api.WithAssertions;
import spriteList.ctrl.ActionMethods;

public class ActionMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;

    private final BombState bombState;
    private final BomberState bomberState;
    private final BreakingEnemyState breakingEnemyState;
    private final FlameState flameState;
    private final FlameEndState flameEndState;
    private final FlyingNomadState flyingNomadState;
    private final WalkingEnemyState walkingEnemyState;

    public ActionMethodsStepDef(SpriteListState listOfSprites,
                                MapPointMatrixState mapPointMatrixState,
                                BombState bombState,
                                BomberState bomberState,
                                BreakingEnemyState breakingEnemyState,
                                FlameState flameState,
                                FlameEndState flameEndState,
                                FlyingNomadState flyingNomadState,
                                WalkingEnemyState walkingEnemyState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bombState = bombState;
        this.bomberState = bomberState;
        this.breakingEnemyState = breakingEnemyState;
        this.flameState = flameState;
        this.flameEndState = flameEndState;
        this.flyingNomadState = flyingNomadState;
        this.walkingEnemyState = walkingEnemyState;
    }

    @When("processing the bomb$")
    public void processing_the_bomb() {
        bombState.setShouldBeRemoved(
                ActionMethods.processBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                        mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(), bombState.getBomb()));
    }

    @When("processing the bomber$")
    public void processing_the_bomber() {
        bomberState.setShouldBeRemoved(
                ActionMethods.processBomber(listOfSprites.getSpriteList(), listOfSprites.getSpriteList(),
                        mapPointMatrixState.getMapPointMatrix(), mapPointMatrixState.getMapWidth(),
                        mapPointMatrixState.getMapHeight(), bomberState.getBomber(), 0));
    }

    @When("^processing the breaking enemy$")
    public void processing_the_breaking_enemy() {
        breakingEnemyState.setShouldBeRemoved(
                ActionMethods.processBreakingEnemy(listOfSprites.getSpriteList(), listOfSprites.getSpriteList(),
                                mapPointMatrixState.getMapPointMatrix(), mapPointMatrixState.getMapWidth(),
                                mapPointMatrixState.getMapHeight(), breakingEnemyState.getEnemy()));
    }

    @When("processing the flame$")
    public void processing_the_flame() {
        flameState.setShouldBeRemoved(
                ActionMethods.processFlame(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                        flameState.getFlame()));
    }

    @When("processing the flame end$")
    public void processing_the_flame_end() {
        flameEndState.setShouldBeRemoved(
                ActionMethods.processFlameEnd(flameEndState.getFlameEnd()));
    }

    @When("processing the flying nomad$")
    public void processing_the_flying_nomad() {
        flyingNomadState.setShouldBeRemoved(
                ActionMethods.processFlyingNomad(mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(),
                        flyingNomadState.getFlyingNomad()));
    }

    @When("^processing the walking enemy$")
    public void processing_the_walking_enemy() {
        walkingEnemyState.setShouldBeRemoved(
                ActionMethods.processWalkingEnemy(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                        mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(),
                        walkingEnemyState.getEnemy()));
    }
}
