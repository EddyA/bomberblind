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

public class BreakingEnemyTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        assertThat(redSpearSoldier.getxMap()).isEqualTo(15);
        assertThat(redSpearSoldier.getyMap()).isEqualTo(30);
        assertThat(redSpearSoldier.getSpriteType()).isEqualTo(SpriteType.TYPE_BREAKING_ENEMY);

        // - dying values.
        assertThat(redSpearSoldier.getDeathImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.deathMatrixRowIdx]);
        assertThat(redSpearSoldier.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_DEATH_FRAME);
        assertThat(redSpearSoldier.getDeathRefreshTime()).isEqualTo(RedSpearSoldier.DEATH_REFRESH_TIME);

        // - walking values.
        assertThat(redSpearSoldier.getWalkBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkBackMatrixRowIdx]);
        assertThat(redSpearSoldier.getWalkFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkFrontMatrixRowIdx]);
        assertThat(redSpearSoldier.getWalkLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkLeftMatrixRowIdx]);
        assertThat(redSpearSoldier.getWalkRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierWalkRightMatrixRowIdx]);
        assertThat(redSpearSoldier.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_RED_SPEAR_SOLDIER_WALK_FRAME);
        assertThat(redSpearSoldier.getWalkRefreshTime()).isEqualTo(RedSpearSoldier.WALK_REFRESH_TIME);

        // - breaking values.
        assertThat(redSpearSoldier.getBreakBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakBackMatrixRowIdx]);
        assertThat(redSpearSoldier.getBreakFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakFrontMatrixRowIdx]);
        assertThat(redSpearSoldier.getBreakLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakLeftMatrixRowIdx]);
        assertThat(redSpearSoldier.getBreakRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.redSpearSoldierBreakRightMatrixRowIdx]);
        assertThat(redSpearSoldier.getNbBreakFrame()).isEqualTo(ImagesLoader.NB_RED_SPEAR_SOLDIER_BREAK_FRAME);
        assertThat(redSpearSoldier.getBreakRefreshTime()).isEqualTo(RedSpearSoldier.BREAK_REFRESH_TIME);

        assertThat(redSpearSoldier.getActingTime()).isEqualTo(RedSpearSoldier.ACTING_TIME);
    }

    @Test
    public void isBreakingSpriteFinishedWithACurActionDifferentOfBreakingShouldReturnFalse() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        redSpearSoldier.setCurSpriteAction(ACTION_DYING);
        assertThat(redSpearSoldier.isBreakingSpriteFinished()).isFalse();
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        assertThat(redSpearSoldier.isBreakingSpriteFinished()).isFalse();
    }

    @Test
    public void isBreakingSpriteFinishedWithCurActionIsBreakingButNotFinishedShouldReturnFalse() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setPaintedAtLeastOneTime(false);
        assertThat(redSpearSoldier.isBreakingSpriteFinished()).isFalse();
    }

    @Test
    public void isBreakingSpriteFinishedWithCurActionIsBreakingAndFinishedShouldReturnTrue() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setPaintedAtLeastOneTime(true);
        assertThat(redSpearSoldier.isBreakingSpriteFinished()).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnTrue() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        assertThat(redSpearSoldier.isActionAllowed(ACTION_BREAKING)).isTrue();
        assertThat(redSpearSoldier.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(redSpearSoldier.isActionAllowed(ACTION_WALKING)).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnFalse() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        assertThat(redSpearSoldier.isActionAllowed(ACTION_FLYING)).isFalse();
        assertThat(redSpearSoldier.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(redSpearSoldier.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_DYING);
        redSpearSoldier.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndWalkingToTheSameDirectionShouldReturnFalse() {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.setLastSpriteAction(ACTION_WALKING);
        redSpearSoldier.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndBreakingToTheSameDirectionShouldReturnFalse() {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.setLastSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedWalkingToADifferentDirectionShouldReturnTrue() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.setLastSpriteAction(ACTION_WALKING);
        redSpearSoldier.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedBreakingToADifferentDirectionShouldReturnTrue() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // set test.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.setLastSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(redSpearSoldier.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);

        // dying.
        // dying.
        redSpearSoldier.setCurSpriteAction(ACTION_DYING);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getDeathImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbDeathFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.DEATH_REFRESH_TIME);

        // breaking back.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getBreakBackImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbBreakFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.BREAK_REFRESH_TIME);

        // breaking back.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_SOUTH);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getBreakFrontImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbBreakFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.BREAK_REFRESH_TIME);

        // breaking back.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_WEST);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getBreakLeftImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbBreakFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.BREAK_REFRESH_TIME);

        // breaking back.
        redSpearSoldier.setCurSpriteAction(ACTION_BREAKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_EAST);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getBreakRightImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbBreakFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.BREAK_REFRESH_TIME);

        // walking back.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_NORTH);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getWalkBackImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbWalkFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.WALK_REFRESH_TIME);

        // walking front.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_SOUTH);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getWalkFrontImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbWalkFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.WALK_REFRESH_TIME);

        // walking left.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_WEST);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getWalkLeftImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbWalkFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.WALK_REFRESH_TIME);

        // walking right.
        redSpearSoldier.setCurSpriteAction(ACTION_WALKING);
        redSpearSoldier.setCurDirection(Direction.DIRECTION_EAST);
        redSpearSoldier.updateSprite();
        assertThat(redSpearSoldier.getImages()).isEqualTo(redSpearSoldier.getWalkRightImages());
        assertThat(redSpearSoldier.getNbImages()).isEqualTo(redSpearSoldier.getNbWalkFrame());
        assertThat(redSpearSoldier.getRefreshTime()).isEqualTo(RedSpearSoldier.WALK_REFRESH_TIME);
    }
}