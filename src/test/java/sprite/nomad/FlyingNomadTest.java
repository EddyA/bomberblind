package sprite.nomad;

import static sprite.SpriteAction.ACTION_BREAKING;
import static sprite.SpriteAction.ACTION_DYING;
import static sprite.SpriteAction.ACTION_FLYING;
import static sprite.SpriteAction.ACTION_WAITING;
import static sprite.SpriteAction.ACTION_WALKING;
import static sprite.SpriteAction.ACTION_WINING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteAction;
import sprite.SpriteType;
import utils.Direction;

public class FlyingNomadTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    // ToDo: The whole class is tested with WhiteBird.class, replace it using a 4-way directionnal class when available.

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, 5);

        // check members value.
        assertThat(whiteBird.getxMap()).isEqualTo(15);
        assertThat(whiteBird.getyMap()).isEqualTo(30);
        assertThat(whiteBird.getSpriteType()).isEqualTo(SpriteType.TYPE_FLYING_NOMAD);
        assertThat(whiteBird.getFlyFrontImages()).isNull();
        assertThat(whiteBird.getFlyBackImages()).isNull();
        assertThat(whiteBird.getFlyLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx]);
        assertThat(whiteBird.getFlyRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx]);
        assertThat(whiteBird.getNbFlyFrame()).isEqualTo(ImagesLoader.NB_BIRD_FLY_FRAME);
        assertThat(whiteBird.getRefreshTime()).isEqualTo(WhiteBird.REFRESH_TIME);
        assertThat(whiteBird.getActingTime()).isEqualTo(WhiteBird.ACTING_TIME);
        assertThat(whiteBird.getCurSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getLastSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(whiteBird.getCurDirection()).isEqualTo(Direction.EAST);
        assertThat(whiteBird.getLastDirection()).isEqualTo(Direction.EAST);
        assertThat(whiteBird.getDeviation()).isEqualTo(5);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(0);
        assertThat(whiteBird.getCurImageIdx()).isBetween(0, ImagesLoader.NB_BIRD_FLY_FRAME - 1);
    }

    @Test
    public void computeMoveTowardNorthWithAEastDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 3);

        // yChar is decreasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(29);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(28);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(27);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(26);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(25);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(24);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(18); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(23);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardNorthWithAWestDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, -2);

        // yChar is decreasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(29);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(28);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(27);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(26);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(12); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(25);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardSouthWithAEastDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.SOUTH, 3);

        // yChar is increasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(31);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(32);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(33);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(34);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(35);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(36);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(18); // x + 1.
        assertThat(whiteBird.getyMap()).isEqualTo(37);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardSouthWithAWestDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.SOUTH, -2);

        // yChar is increasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(31);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(32);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(33);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13); // same x.
        assertThat(whiteBird.getyMap()).isEqualTo(34);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(12); // x - 1.
        assertThat(whiteBird.getyMap()).isEqualTo(35);
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardWestWithASouthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.WEST, 3);

        // xChar is decreasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(12);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(11);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(10);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(9);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(8);
        assertThat(whiteBird.getyMap()).isEqualTo(33); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardWestWithANorthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.WEST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(14);
        assertThat(whiteBird.getyMap()).isEqualTo(29); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(13);
        assertThat(whiteBird.getyMap()).isEqualTo(29); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(12);
        assertThat(whiteBird.getyMap()).isEqualTo(28); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(11);
        assertThat(whiteBird.getyMap()).isEqualTo(28); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(10);
        assertThat(whiteBird.getyMap()).isEqualTo(27); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardEastWithASouthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, 3);

        // xChar is increasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(18);
        assertThat(whiteBird.getyMap()).isEqualTo(31); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(19);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(20);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(21);
        assertThat(whiteBird.getyMap()).isEqualTo(32); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(22);
        assertThat(whiteBird.getyMap()).isEqualTo(33); // y + 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardEastWithANorthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(16);
        assertThat(whiteBird.getyMap()).isEqualTo(29); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(1);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(17);
        assertThat(whiteBird.getyMap()).isEqualTo(29); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(18);
        assertThat(whiteBird.getyMap()).isEqualTo(28); // y - 1.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(3);

        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(19);
        assertThat(whiteBird.getyMap()).isEqualTo(28); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        whiteBird.computeMove();
        assertThat(whiteBird.getxMap()).isEqualTo(20);
        assertThat(whiteBird.getyMap()).isEqualTo(27); // same y.
        assertThat(whiteBird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void isActionAllowedShouldReturnTrue() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);
        assertThat(whiteBird.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(whiteBird.isActionAllowed(ACTION_FLYING)).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnFalse() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);
        assertThat(whiteBird.isActionAllowed(ACTION_BREAKING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WALKING)).isFalse();
        assertThat(whiteBird.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_DYING);
        whiteBird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndFlyingToTheSameDirectionShouldReturnFalse() {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.NORTH);
        whiteBird.setLastSpriteAction(ACTION_FLYING);
        whiteBird.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedAndFlyingToADifferentDirectionShouldReturnTrue() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.NORTH, 0);

        // set test.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.NORTH);
        whiteBird.setLastSpriteAction(ACTION_FLYING);
        whiteBird.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(whiteBird.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, -10);

        // walking back.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.NORTH);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyBackImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking front.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.SOUTH);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyFrontImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking left.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.WEST);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyLeftImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());

        // walking right.
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        whiteBird.setCurDirection(Direction.EAST);
        whiteBird.updateSprite();
        assertThat(whiteBird.getImages()).isEqualTo(whiteBird.getFlyRightImages());
        assertThat(whiteBird.getNbImages()).isEqualTo(whiteBird.getNbFlyFrame());
    }

    @Test
    public void isFinishedShouldReturnFalse() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, -10);
        whiteBird.setCurSpriteAction(ACTION_FLYING);
        assertThat(whiteBird.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTruee() throws Exception {
        WhiteBird whiteBird = new WhiteBird(15, 30, Direction.EAST, -10);
        whiteBird.setCurSpriteAction(ACTION_DYING);
        assertThat(whiteBird.isFinished()).isTrue();
    }
}