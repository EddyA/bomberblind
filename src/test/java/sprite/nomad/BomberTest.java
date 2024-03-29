package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sprite.SpriteType;
import sprite.settled.Bomb;
import sprite.settled.BonusType;
import utils.CurrentTimeSupplier;
import utils.Direction;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static sprite.SpriteAction.*;
import static sprite.nomad.Bomber.DEFAULT_ACTING_TIME;

class BomberTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // check members value.
        assertThat(blueBomber.getXMap()).isEqualTo(15);
        assertThat(blueBomber.getYMap()).isEqualTo(30);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BOMBER);
        assertThat(blueBomber.getDeathImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.getWaitImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.getNbWaitFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.getWalkBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.getWalkFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.getWalkLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.getWalkRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.getWinImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.getNbWinFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(DEFAULT_ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
        assertThat(blueBomber.getInitialXMap()).isEqualTo(blueBomber.getXMap());
        assertThat(blueBomber.getInitialYMap()).isEqualTo(blueBomber.getYMap());
        assertThat(blueBomber.getCurSpriteAction()).isEqualTo(ACTION_WAITING);
        assertThat(blueBomber.isInvincible()).isTrue();
    }

    @Test
    void dropBombShouldAddABombToTheDroppedBombsList() {

        // set test.
        BlueBomber blueBomber = new BlueBomber(10, 20);
        Bomb bomb_1 = new Bomb(1, 1, 1);
        blueBomber.dropBomb(bomb_1);
        Bomb bomb_2 = new Bomb(2, 2, 2);
        blueBomber.dropBomb(bomb_2);
        Bomb bomb_3 = new Bomb(3, 3, 3);
        blueBomber.dropBomb(bomb_3);

        // check.
        assertThat(blueBomber.getDroppedBombs().contains(bomb_1)).isTrue();
        assertThat(blueBomber.getDroppedBombs().contains(bomb_2)).isTrue();
        assertThat(blueBomber.getDroppedBombs().contains(bomb_3)).isTrue();
    }

    @Test
    void getNbDroppedBombShouldReturnNbDroppedBombs() {

        // set test.
        BlueBomber blueBomber = new BlueBomber(10, 20);
        Bomb bomb_1 = new Bomb(1, 1, 1);
        blueBomber.dropBomb(bomb_1);
        Bomb bomb_2 = new Bomb(2, 2, 2);
        blueBomber.dropBomb(bomb_2);
        Bomb bomb_3 = new Bomb(3, 3, 3);
        blueBomber.dropBomb(bomb_3);

        // check.
        assertThat(blueBomber.getNbDroppedBomb()).isEqualTo(3);
    }

    @Test
    void getNbDroppedBombShouldRemoveFinishedBombsFromTheDroppedBombsList() {

        // set test.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        Bomb bomb_1 = Mockito.mock(Bomb.class); // bomb_1 is mocked to be finished.
        Mockito.when(bomb_1.isFinished()).thenReturn(true);
        blueBomber.dropBomb(bomb_1);
        Bomb bomb_2 = new Bomb(2, 2, 2);
        blueBomber.dropBomb(bomb_2);
        Bomb bomb_3 = Mockito.mock(Bomb.class); // bomb_3 is mocked to be finished.
        Mockito.when(bomb_3.isFinished()).thenReturn(true);
        blueBomber.dropBomb(bomb_3);


        // check.
        assertThat(blueBomber.getNbDroppedBomb()).isEqualTo(1);
        assertThat(blueBomber.getDroppedBombs().contains(bomb_1)).isFalse(); // no more in the list.
        assertThat(blueBomber.getDroppedBombs().contains(bomb_2)).isTrue();
        assertThat(blueBomber.getDroppedBombs().contains(bomb_3)).isFalse();// no more in the list.
    }

    @Test
    void getBonusShouldReturnTheExpectedNumber() {
        // set test.
        BlueBomber blueBomber = new BlueBomber(10, 20);
        blueBomber.setBonus(BonusType.TYPE_BONUS_BOMB, 5);
        blueBomber.setBonus(BonusType.TYPE_BONUS_FLAME, 10);
        blueBomber.setBonus(BonusType.TYPE_BONUS_HEART, 15);
        blueBomber.setBonus(BonusType.TYPE_BONUS_ROLLER, 20);

        //check.
        assertThat(blueBomber.getBonus(BonusType.TYPE_BONUS_BOMB)).isEqualTo(5);
        assertThat(blueBomber.getBonus(BonusType.TYPE_BONUS_FLAME)).isEqualTo(10);
        assertThat(blueBomber.getBonus(BonusType.TYPE_BONUS_HEART)).isEqualTo(15);
        assertThat(blueBomber.getBonus(BonusType.TYPE_BONUS_ROLLER)).isEqualTo(20);
    }

    @Test
    void setBonusRollerShouldUpdateActingTime() {
        // set test.
        BlueBomber blueBomber = new BlueBomber(10, 20);

        //check.
        blueBomber.setBonus(BonusType.TYPE_BONUS_ROLLER, 1); // standard case.
        assertThat(blueBomber.getActingTime()).isEqualTo(DEFAULT_ACTING_TIME - 1);
        blueBomber.setBonus(BonusType.TYPE_BONUS_ROLLER, 20); // high value -> should reach the limit of 4ms.
        assertThat(blueBomber.getActingTime()).isEqualTo(4);
    }

    @Test
    void initShouldSetMembersWithTheExpectedValues() {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // set test (update members with value != than the expected ones).
        blueBomber.setCurSpriteAction(ACTION_DYING);
        blueBomber.setXMap(30);
        blueBomber.setYMap(40);
        blueBomber.setLastInvincibilityTs(10000L - blueBomber.getInvincibilityTime() - 1); // deactivate invincible.
        assertThat(blueBomber.isInvincible()).isFalse();

        // init bomber and check values.
        blueBomber.init();
        assertThat(blueBomber.getXMap()).isEqualTo(blueBomber.getInitialXMap());
        assertThat(blueBomber.getYMap()).isEqualTo(blueBomber.getInitialYMap());
        assertThat(blueBomber.getCurSpriteAction()).isEqualTo(ACTION_WAITING);
        assertThat(blueBomber.isInvincible()).isTrue();
        assertThat(blueBomber.getLastInvincibilityTs()).isEqualTo(10000L);
    }

    @Test
    void isActionAllowedShouldReturnTrue() {
        BlueBomber blueBomber = new BlueBomber(10, 20);
        assertThat(blueBomber.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(blueBomber.isActionAllowed(ACTION_WAITING)).isTrue();
        assertThat(blueBomber.isActionAllowed(ACTION_WALKING)).isTrue();
        assertThat(blueBomber.isActionAllowed(ACTION_WINING)).isTrue();
    }

    @Test
    void isActionAllowedShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(10, 20);
        assertThat(blueBomber.isActionAllowed(ACTION_BREAKING)).isFalse();
        assertThat(blueBomber.isActionAllowed(ACTION_FLYING)).isFalse();
    }

    @Test
    void hasActionChangedWithTheSameActionShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_WAITING);
        blueBomber.setLastSpriteAction(ACTION_WAITING);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isFalse();
    }

    @Test
    void hasActionChangedWithTheSameDirectionShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_NORTH);
        blueBomber.setLastSpriteAction(ACTION_WALKING);
        blueBomber.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isFalse();
    }

    @Test
    void hasActionChangedWithADifferentActionShouldReturnTrue() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_WAITING);
        blueBomber.setLastSpriteAction(ACTION_WALKING); // last action != current action.

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isTrue();
    }

    @Test
    void hasActionChangedWithADifferentDirectionShouldReturnTrue() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_NORTH);
        blueBomber.setLastSpriteAction(ACTION_WALKING);
        blueBomber.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isTrue();
    }

    @Test
    void updateSpriteShouldSetTheExpectedMember() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // dying.
        blueBomber.setCurSpriteAction(ACTION_DYING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getDeathImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbDeathFrame());

        // waiting.
        blueBomber.setCurSpriteAction(ACTION_WAITING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWaitImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWaitFrame());

        // walking back.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_NORTH);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWalkBackImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWalkFrame());

        // walking front.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_SOUTH);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWalkFrontImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWalkFrame());

        // walking left.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_WEST);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWalkLeftImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWalkFrame());

        // walking right.
        blueBomber.setCurSpriteAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.DIRECTION_EAST);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWalkRightImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWalkFrame());

        // win.
        blueBomber.setCurSpriteAction(ACTION_WINING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages()).isEqualTo(blueBomber.getWinImages());
        assertThat(blueBomber.getNbImages()).isEqualTo(blueBomber.getNbWinFrame());
    }
}
