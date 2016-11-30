package glue;

import org.assertj.core.api.WithAssertions;

import cucumber.api.java.en.When;
import spriteList.ctrl.ActionMethods;

public class ActionMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;
    private final BomberState bomberState;
    private final EnemyState enemyState;
    private final BombState bombState;

    public ActionMethodsStepDef(SpriteListState listOfSprites,
            MapPointMatrixState mapPointMatrixState,
            BomberState bomberState,
            EnemyState enemyState,
            BombState bombState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bomberState = bomberState;
        this.enemyState = enemyState;
        this.bombState = bombState;
    }

    @When("processing the bomber$")
    public void processing_the_bomber() {
        ActionMethods.processBomber(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                bomberState.getBomber());
    }

    @When("^processing the enemy$")
    public void processing_the_enemy() {
        ActionMethods.processEnemy(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(), enemyState.getEnemy());
    }

    @When("processing the bomb$")
    public void processing_the_bomb() {
        ActionMethods.processBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(),
                mapPointMatrixState.getMapWidth(), mapPointMatrixState.getMapHeight(), bombState.getBomb());
    }
}
