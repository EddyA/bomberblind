package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sprite.SpriteType;
import utils.CurrentTimeSupplier;
import utils.Direction;

import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static utils.Action.*;

public class BomberTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // check members value.
        assertThat(blueBomber.getxMap()).isEqualTo(15);
        assertThat(blueBomber.getyMap()).isEqualTo(30);
        assertThat(blueBomber.getSpriteType()).isEqualTo(SpriteType.BOMBER);
        assertThat(blueBomber.getRefreshTime()).isEqualTo(BlueBomber.REFRESH_TIME);
        assertThat(blueBomber.getActingTime()).isEqualTo(BlueBomber.ACTING_TIME);
        assertThat(blueBomber.getInvincibilityTime()).isEqualTo(BlueBomber.INVINCIBILITY_TIME);
        assertThat(blueBomber.getDeathImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);
        assertThat(blueBomber.getWaitImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.getNbWaitFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);
        assertThat(blueBomber.getWalkBackImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.getWalkFrontImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.getWalkLeftImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.getWalkRightImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);
        assertThat(blueBomber.getWinImages()).
                isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.getNbWinFrame()).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
        assertThat(blueBomber.getInitialXMap()).isEqualTo(blueBomber.getxMap());
        assertThat(blueBomber.getInitialYMap()).isEqualTo(blueBomber.getyMap());
        assertThat(blueBomber.getCurAction()).isEqualTo(ACTION_WAITING);
        assertThat(blueBomber.isInvincible()).isTrue();
    }

    @Test
    public void initShouldSetMembersWithTheExpectedValues() throws Exception {
        BlueBomber blueBomber = new BlueBomber(10, 20);

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(10000L));
        blueBomber.setCurrentTimeSupplier(currentTimeSupplier);

        // set test (update members with value != than the expected ones).
        blueBomber.setCurAction(ACTION_DYING);
        blueBomber.setxMap(30);
        blueBomber.setyMap(40);
        blueBomber.setLastInvincibilityTs(10000L - blueBomber.getInvincibilityTime() - 1); // deactivate invincible.
        assertThat(blueBomber.isInvincible()).isFalse();

        // init bomber and check values.
        blueBomber.init();
        assertThat(blueBomber.getxMap()).isEqualTo(blueBomber.getInitialXMap());
        assertThat(blueBomber.getyMap()).isEqualTo(blueBomber.getInitialYMap());
        assertThat(blueBomber.getCurAction()).isEqualTo(ACTION_WAITING);
        assertThat(blueBomber.isInvincible()).isTrue();
        assertThat(blueBomber.getLastInvincibilityTs()).isEqualTo(10000L);
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_WAITING);
        blueBomber.setLastAction(ACTION_WAITING);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameDirectionShouldReturnFalse() {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.NORTH);
        blueBomber.setLastAction(ACTION_WALKING);
        blueBomber.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_WAITING);
        blueBomber.setLastAction(ACTION_WALKING); // last action != current action.

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedWithADifferentDirectionShouldReturnTrue() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // set test.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.NORTH);
        blueBomber.setLastAction(ACTION_WALKING);
        blueBomber.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(blueBomber.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        BlueBomber blueBomber = new BlueBomber(15, 30);

        // dying.
        blueBomber.setCurAction(ACTION_DYING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberDeathMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_DEATH_FRAME);

        // waiting.
        blueBomber.setCurAction(ACTION_WAITING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWaitMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WAIT_FRAME);

        // walking back.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.NORTH);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkBackMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking front.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.SOUTH);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkFrontMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking left.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.WEST);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkLeftMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // walking right.
        blueBomber.setCurAction(ACTION_WALKING);
        blueBomber.setCurDirection(Direction.EAST);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWalkRightMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WALK_FRAME);

        // win.
        blueBomber.setCurAction(ACTION_WINING);
        blueBomber.updateSprite();
        assertThat(blueBomber.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.blueBomberWinMatrixRowIdx]);
        assertThat(blueBomber.getNbImages()).isEqualTo(ImagesLoader.NB_BOMBER_WIN_FRAME);
    }
}