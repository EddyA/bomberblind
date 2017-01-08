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
    private final FlameState flameState;
    private final FlameEndState flameEndState;

    public AddingMethodsStepDef(SpriteListState listOfSprites,
            MapPointMatrixState mapPointMatrixState,
            BomberState bomberState,
            EnemyState enemyState,
            BombState bombState,
            FlameState flameState,
            FlameEndState flameEndState) {
        this.listOfSprites = listOfSprites;
        this.mapPointMatrixState = mapPointMatrixState;
        this.bomberState = bomberState;
        this.enemyState = enemyState;
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
        AddingMethods.addBomber(listOfSprites.getSpriteList(), bomberState.getBomber());
    }

    @Given("^an enemy at rowIdx (\\d+) and coldIdx (\\d+)$")
    public void an_enemy_at_rowIdx_and_coldIdx(int rowIdx, int colIdx) {
        enemyState.getEnemy().setxMap(Tools.getCaseCentreAbscissa(colIdx));
        enemyState.getEnemy().setyMap(Tools.getCaseBottomOrdinate(rowIdx));
        AddingMethods.addEnemy(listOfSprites.getSpriteList(), enemyState.getEnemy());
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
}
