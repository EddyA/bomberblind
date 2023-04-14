package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprite.SpriteType;
import utils.Direction;

import java.io.IOException;

import static sprite.SpriteAction.*;

class FlyingNomadTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    // ToDo: The whole class is tested with WhiteBird.class, replace it using a 4-way directional class when available.

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, 5);

        // check members value.
        assertThat(whiteBird.getXMap()).isEqualTo(15);
        assertThat(whiteBird.getYMap()).isEqualTo(30);
        assertThat(whiteBird.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_FLYING_NOMAD);
        assertThat(whiteBird.getFlyBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyBackMatrixRowIdx]);
        assertThat(whiteBird.getFlyFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyFrontMatrixRowIdx]);
        assertThat(whiteBird.getFlyLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx]);
        assertThat(whiteBird.getFlyRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx]);
        assertThat(whiteBird.getNbFlyFrame()).isEqualTo(ImagesLoader.NB_BIRD_FLY_FRAME);
        assertThat(whiteBird.getRefreshTime()).isEqualTo(WhiteBird.REFRESH_TIME);
        assertThat(whiteBird.getActingTime()).isEqualTo(WhiteBird.ACTING_TIME);
        assertThat(whiteBird.getCurSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getLastSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getCurDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(whiteBird.getLastDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(whiteBird.getDeviation()).isEqualTo(5);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(0);
        assertThat(whiteBird.getCurImageIdx()).isBetween(0, ImagesLoader.NB_BIRD_FLY_FRAME - 1);
    }

    @Test
    void computeMoveTowardNorthWithAEastDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 3);

        // yChar is decreasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(29);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(28);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(27);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(26);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(25);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(24);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(18); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(23);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    void computeMoveTowardNorthWithAWestDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, -2);

        // yChar is decreasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(29);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(28);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(27);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(26);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(12); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(25);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    void computeMoveTowardSouthWithAEastDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_SOUTH, 3);

        // yChar is increasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(31);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(32);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(33);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(34);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(35);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(36);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(18); // x + 1.
        assertThat(whiteBird.getYMap()).isEqualTo(37);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    void computeMoveTowardSouthWithAWestDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_SOUTH, -2);

        // yChar is increasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(31);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(32);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(33);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13); // same x.
        assertThat(whiteBird.getYMap()).isEqualTo(34);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(12); // x - 1.
        assertThat(whiteBird.getYMap()).isEqualTo(35);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    void computeMoveTowardWestWithASouthDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_WEST, 3);

        // xChar is decreasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(12);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(11);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(10);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(9);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(8);
        assertThat(whiteBird.getYMap()).isEqualTo(33); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    void computeMoveTowardWestWithANorthDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_WEST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(14);
        assertThat(whiteBird.getYMap()).isEqualTo(29); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(13);
        assertThat(whiteBird.getYMap()).isEqualTo(29); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(12);
        assertThat(whiteBird.getYMap()).isEqualTo(28); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(11);
        assertThat(whiteBird.getYMap()).isEqualTo(28); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(10);
        assertThat(whiteBird.getYMap()).isEqualTo(27); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    void computeMoveTowardEastWithASouthDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, 3);

        // xChar is increasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(18);
        assertThat(whiteBird.getYMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(19);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(20);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(21);
        assertThat(whiteBird.getYMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(22);
        assertThat(whiteBird.getYMap()).isEqualTo(33); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    void computeMoveTowardEastWithANorthDeviationShouldSetMembersWithTheExpectedValues() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(16);
        assertThat(whiteBird.getYMap()).isEqualTo(29); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(17);
        assertThat(whiteBird.getYMap()).isEqualTo(29); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(18);
        assertThat(whiteBird.getYMap()).isEqualTo(28); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(19);
        assertThat(whiteBird.getYMap()).isEqualTo(28); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getXMap()).isEqualTo(20);
        assertThat(whiteBird.getYMap()).isEqualTo(27); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    void isActionAllowedShouldReturnTrue() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);
        assertThat(whiteBird.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(whiteBird.isActionAllowed(ACTION_FLYING)).isTrue();
    }

    @Test
    void isActionAllowedShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);
        assertThat(whiteBird.isActionAllowed(ACTION_BREAKING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WALKING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    void hasActionChangedWithTheSameActionShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_DYING);
        whiteBird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isFalse();
    }

    @Test
    void hasActionChangedAndFlyingToTheSameDirectionShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_NORTH);
        whiteBird.setLastSpriteAction(ACTION_FLYING);
        whiteBird.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isFalse();
    }

    @Test
    void hasActionChangedWithADifferentActionShouldReturnTrue() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isTrue();
    }

    @Test
    void hasActionChangedAndFlyingToADifferentDirectionShouldReturnTrue() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_NORTH);
        whiteBird.setLastSpriteAction(ACTION_FLYING);
        whiteBird.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isTrue();
    }

    @Test
    void updateSpriteShouldSetTheExpectedMember() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, -10);

        // dying.
        whiteBird.setCurSpriteAction(ACTION_DYING);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isNull();
        assertThat(whiteBird.getNbImages()).isEqualTo(0);

        // walking back.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_NORTH);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyBackImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking front.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_SOUTH);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyFrontImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking left.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_WEST);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyLeftImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking right.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.DIRECTION_EAST);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyRightImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());
    }

    @Test
    void isFinishedShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, -10);
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        assertThat(whiteBird.isFinished()).isFalse();
    }

    @Test
    void isFinishedShouldReturnTruee() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.DIRECTION_EAST, -10);
        whiteBird.setCurSpriteAction(ACTION_DYING);
        assertThat(whiteBird.isFinished()).isTrue();
    }
}
