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
import sprite.SpriteType;
import utils.Direction;

public class FlyingNomadTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    // ToDo: The whole class is tested with Bird.class, replace it using a 4-way directionnal class when available.

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, 5);

        // check members value.
        assertThat(bird.getxMap()).isEqualTo(15);
        assertThat(bird.getyMap()).isEqualTo(30);
        assertThat(bird.getSpriteType()).isEqualTo(SpriteType.TYPE_FLYING_NOMAD);
        assertThat(bird.getFlyFrontImages()).isNull();
        assertThat(bird.getFlyBackImages()).isNull();
        assertThat(bird.getFlyLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyLeftMatrixRowIdx]);
        assertThat(bird.getFlyRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.birdFlyRightMatrixRowIdx]);
        assertThat(bird.getNbFlyFrame()).isEqualTo(ImagesLoader.NB_BIRD_FLY_FRAME);
        assertThat(bird.getRefreshTime()).isEqualTo(Bird.REFRESH_TIME);
        assertThat(bird.getActingTime()).isEqualTo(Bird.ACTING_TIME);
        assertThat(bird.getCurSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(bird.getLastSpriteAction()).isEqualTo(ACTION_FLYING);
        assertThat(bird.getCurDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(bird.getLastDirection()).isEqualTo(Direction.DIRECTION_EAST);
        assertThat(bird.getDeviation()).isEqualTo(5);
        assertThat(bird.getMoveIdx()).isEqualTo(0);
        assertThat(bird.getCurImageIdx()).isBetween(0, ImagesLoader.NB_BIRD_FLY_FRAME - 1);
    }

    @Test
    public void computeMoveTowardNorthWithAEastDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 3);

        // yChar is decreasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(29);
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // same x.
        assertThat(bird.getyMap()).isEqualTo(28);
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // same x.
        assertThat(bird.getyMap()).isEqualTo(27);
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(26);
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // same x.
        assertThat(bird.getyMap()).isEqualTo(25);
        assertThat(bird.getMoveIdx()).isEqualTo(5);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // same x.
        assertThat(bird.getyMap()).isEqualTo(24);
        assertThat(bird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(18); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(23);
        assertThat(bird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardNorthWithAWestDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, -2);

        // yChar is decreasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(29);
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14); // same x.
        assertThat(bird.getyMap()).isEqualTo(28);
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(27);
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13); // same x.
        assertThat(bird.getyMap()).isEqualTo(26);
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(12); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(25);
        assertThat(bird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardSouthWithAEastDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_SOUTH, 3);

        // yChar is increasing each call, xChar is increasing every 3 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(31);
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // same x.
        assertThat(bird.getyMap()).isEqualTo(32);
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16); // same x.
        assertThat(bird.getyMap()).isEqualTo(33);
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(34);
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // same x.
        assertThat(bird.getyMap()).isEqualTo(35);
        assertThat(bird.getMoveIdx()).isEqualTo(5);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17); // same x.
        assertThat(bird.getyMap()).isEqualTo(36);
        assertThat(bird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(18); // x + 1.
        assertThat(bird.getyMap()).isEqualTo(37);
        assertThat(bird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardSouthWithAWestDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_SOUTH, -2);

        // yChar is increasing each call, xChar is decreasing every 2 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(31);
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14); // same x.
        assertThat(bird.getyMap()).isEqualTo(32);
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(33);
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13); // same x.
        assertThat(bird.getyMap()).isEqualTo(34);
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(12); // x - 1.
        assertThat(bird.getyMap()).isEqualTo(35);
        assertThat(bird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardWestWithASouthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_WEST, 3);

        // xChar is decreasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14);
        assertThat(bird.getyMap()).isEqualTo(31); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13);
        assertThat(bird.getyMap()).isEqualTo(31); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(12);
        assertThat(bird.getyMap()).isEqualTo(31); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(11);
        assertThat(bird.getyMap()).isEqualTo(32); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(10);
        assertThat(bird.getyMap()).isEqualTo(32); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(5);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(9);
        assertThat(bird.getyMap()).isEqualTo(32); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(8);
        assertThat(bird.getyMap()).isEqualTo(33); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardWestWithANorthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_WEST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(14);
        assertThat(bird.getyMap()).isEqualTo(29); // y - 1.
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(13);
        assertThat(bird.getyMap()).isEqualTo(29); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(12);
        assertThat(bird.getyMap()).isEqualTo(28); // y - 1.
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(11);
        assertThat(bird.getyMap()).isEqualTo(28); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(10);
        assertThat(bird.getyMap()).isEqualTo(27); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void computeMoveTowardEastWithASouthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, 3);

        // xChar is increasing each call, yChar is increasing every 3 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16);
        assertThat(bird.getyMap()).isEqualTo(31); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17);
        assertThat(bird.getyMap()).isEqualTo(31); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(18);
        assertThat(bird.getyMap()).isEqualTo(31); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(19);
        assertThat(bird.getyMap()).isEqualTo(32); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(20);
        assertThat(bird.getyMap()).isEqualTo(32); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(5);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(21);
        assertThat(bird.getyMap()).isEqualTo(32); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(6);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(22);
        assertThat(bird.getyMap()).isEqualTo(33); // y + 1.
        assertThat(bird.getMoveIdx()).isEqualTo(7);
    }

    @Test
    public void computeMoveTowardEastWithANorthDeviationShouldSetMembersWithTheExpectedValues() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, -2);

        // xChar is decreasing each call, yChar is decreasing every 2 calls.

        // 1st cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(16);
        assertThat(bird.getyMap()).isEqualTo(29); // y - 1.
        assertThat(bird.getMoveIdx()).isEqualTo(1);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(17);
        assertThat(bird.getyMap()).isEqualTo(29); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(2);

        // 2nd cycle.
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(18);
        assertThat(bird.getyMap()).isEqualTo(28); // y - 1.
        assertThat(bird.getMoveIdx()).isEqualTo(3);

        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(19);
        assertThat(bird.getyMap()).isEqualTo(28); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(4);

        // 3rd cycle, etc ...
        bird.computeMove();
        assertThat(bird.getxMap()).isEqualTo(20);
        assertThat(bird.getyMap()).isEqualTo(27); // same y.
        assertThat(bird.getMoveIdx()).isEqualTo(5);
    }

    @Test
    public void isActionAllowedShouldReturnTrue() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);
        assertThat(bird.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(bird.isActionAllowed(ACTION_FLYING)).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnFalse() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);
        assertThat(bird.isActionAllowed(ACTION_BREAKING)).isFalse();
        assertThat(bird.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(bird.isActionAllowed(ACTION_WALKING)).isFalse();
        assertThat(bird.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        bird.setCurSpriteAction(ACTION_DYING);
        bird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(bird.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndFlyingToTheSameDirectionShouldReturnFalse() {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_NORTH);
        bird.setLastSpriteAction(ACTION_FLYING);
        bird.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(bird.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(bird.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedAndFlyingToADifferentDirectionShouldReturnTrue() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_NORTH, 0);

        // set test.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_NORTH);
        bird.setLastSpriteAction(ACTION_FLYING);
        bird.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(bird.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, -10);

        // walking back.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_NORTH);
        bird.updateSprite();
        assertThat(bird.getImages()).isEqualTo(bird.getFlyBackImages());
        assertThat(bird.getNbImages()).isEqualTo(bird.getNbFlyFrame());

        // walking front.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_SOUTH);
        bird.updateSprite();
        assertThat(bird.getImages()).isEqualTo(bird.getFlyFrontImages());
        assertThat(bird.getNbImages()).isEqualTo(bird.getNbFlyFrame());

        // walking left.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_WEST);
        bird.updateSprite();
        assertThat(bird.getImages()).isEqualTo(bird.getFlyLeftImages());
        assertThat(bird.getNbImages()).isEqualTo(bird.getNbFlyFrame());

        // walking right.
        bird.setCurSpriteAction(ACTION_FLYING);
        bird.setCurDirection(Direction.DIRECTION_EAST);
        bird.updateSprite();
        assertThat(bird.getImages()).isEqualTo(bird.getFlyRightImages());
        assertThat(bird.getNbImages()).isEqualTo(bird.getNbFlyFrame());
    }

    @Test
    public void isFinishedShouldReturnFalse() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, -10);
        bird.setCurSpriteAction(ACTION_FLYING);
        assertThat(bird.isFinished()).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTruee() throws Exception {
        Bird bird = new Bird(15, 30, Direction.DIRECTION_EAST, -10);
        bird.setCurSpriteAction(ACTION_DYING);
        assertThat(bird.isFinished()).isTrue();
    }
}