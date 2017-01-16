package glue;

import org.assertj.core.api.WithAssertions;

import cucumber.api.java.en.When;
import spriteList.ctrl.ActionMethods;

public class ActionMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;
    private final BomberState bomberState;
    private final WalkingEnemyState walkingEnemyState;
    private final BombState bombState;
    private final FlameState flameState;
    private final FlameEndState flameEndState;

    public ActionMethodsStepDef(SpriteListState listOfSprites,
            MapPointMatrixState mapPointMatrixState,
            BomberState bomberState,
            WalkingEnemyState walkingEnemyState,
            BombState bombState,
            FlameState flameState,
            FlameEndState flameEndState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bomberState = bomberState;
        this.walkingEnemyState = walkingEnemyState;
        this.bombState = bombState;
        this.flameState = flameState;
        this.flameEndState = flameEndState;
    }

    @When("processing the bomber$")
    public void processing_the_bomber() {
        ActionMethods.processBomber(listOfSprites.getSpriteList(), listOfSprites.getSpriteList(),
                mapPointMatrixState.getMapPointMatrix(), mapPointMatrixState.getMapWidth(),
                mapPointMatrixState.getMapHeight(), bomberState.getBomber(), 0);
    }

    @When("^processing the enemy$")
    public void processing_the_enemy() {
        walkingEnemyState.setShouldBeRemoved(
                ActionMethods.processWalkingEnemy(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                        mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(),
                        walkingEnemyState.getEnemy()));
    }

    @When("processing the bomb$")
    public void processing_the_bomb() {
        bombState.setShouldBeRemoved(
                ActionMethods.processBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                        mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(), bombState.getBomb()));
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
}
