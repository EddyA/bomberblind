package glue;

import org.assertj.core.api.WithAssertions;

import cucumber.api.java.en.Given;
import spriteList.ctrl.AddingMethods;
import utils.Tools;

public class AddingMethodsStepDef implements WithAssertions {

    private final SpriteListState listOfSprites;
    private final MapPointMatrixState mapPointMatrixState;
    private final BomberState bomberState;
    private final EnemyState enemyState;
    private final BombState bombState;

    public AddingMethodsStepDef(SpriteListState listOfSprites,
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

    @Given("^a bomber at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void a_bomber_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        bomberState.getBomber().setXMap(Tools.getCaseCentreAbscissa(colIdx));
        bomberState.getBomber().setYMap(Tools.getCaseBottomOrdinate(rowIdx));
        bomberState.getBomber().setInitialXMap(Tools.getCaseCentreAbscissa(colIdx));
        bomberState.getBomber().setInitialYMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addBomber(listOfSprites.getSpriteList(), bomberState.getBomber());
    }

    @Given("^an enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void an_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        enemyState.getEnemy().setXMap(Tools.getCaseCentreAbscissa(colIdx));
        enemyState.getEnemy().setYMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addEnemy(listOfSprites.getSpriteList(), enemyState.getEnemy());
    }

    @Given("^a bomb at rowIdx (\\d+) and coldIdx (\\d+) and a flame size of (\\d+)$")
    public void a_bomb_at_rowIdx_and_coldIdx_with_a_flame_size_of(int rowIdx, int colIdx, int flameSize) {
        bombState.getBomb().setRowIdx(rowIdx);
        bombState.getBomb().setColIdx(colIdx);
        bombState.getBomb().setFlamesize(flameSize);
        AddingMethods.addBomb(listOfSprites.getSpriteList(), mapPointMatrixState.getMapPointMatrix(), bombState.getBomb());
    }
}
