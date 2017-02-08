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
        Minotor minotor = new Minotor(15, 30);

        // check members value.
        assertThat(minotor.getxMap()).isEqualTo(15);
        assertThat(minotor.getyMap()).isEqualTo(30);
        assertThat(minotor.getSpriteType()).isEqualTo(SpriteType.TYPE_BREAKING_ENEMY);
        assertThat(minotor.getDeathImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorDeathMatrixRowIdx]);
        assertThat(minotor.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_MINOTOR_DEATH_FRAME);
        assertThat(minotor.getBreakBackImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakBackMatrixRowIdx]);
        assertThat(minotor.getBreakFrontImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakFrontMatrixRowIdx]);
        assertThat(minotor.getBreakLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakLeftMatrixRowIdx]);
        assertThat(minotor.getBreakRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakRightMatrixRowIdx]);
        assertThat(minotor.getNbBreakFrame()).isEqualTo(ImagesLoader.NB_MINOTOR_BREAK_FRAME);
        assertThat(minotor.getWalkBackImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkBackMatrixRowIdx]);
        assertThat(minotor.getWalkFrontImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkFrontMatrixRowIdx]);
        assertThat(minotor.getWalkLeftImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkLeftMatrixRowIdx]);
        assertThat(minotor.getWalkRightImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkRightMatrixRowIdx]);
        assertThat(minotor.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_MINOTOR_WALK_FRAME);
        assertThat(minotor.getRefreshTime()).isEqualTo(Minotor.REFRESH_TIME);
        assertThat(minotor.getActingTime()).isEqualTo(Minotor.ACTING_TIME);
        assertThat(minotor.getCurSpriteAction()).isEqualTo(ACTION_WALKING);
    }

    @Test
    public void isBreakingSpriteFinishedWithACurActionDifferentOfBreakingShouldReturnFalse() throws Exception {
        Minotor minotor = new Minotor(15, 30);
        minotor.setCurSpriteAction(ACTION_DYING);
        assertThat(minotor.isBreakingSpriteFinished()).isFalse();
        minotor.setCurSpriteAction(ACTION_WALKING);
        assertThat(minotor.isBreakingSpriteFinished()).isFalse();
    }

    @Test
    public void isBreakingSpriteFinishedWithCurActionIsBreakingButNotFinishedShouldReturnFalse() throws Exception {
        Minotor minotor = new Minotor(15, 30);
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setPaintedAtLeastOneTime(false);
        assertThat(minotor.isBreakingSpriteFinished()).isFalse();
    }

    @Test
    public void isBreakingSpriteFinishedWithCurActionIsBreakingAndFinishedShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setPaintedAtLeastOneTime(true);
        assertThat(minotor.isBreakingSpriteFinished()).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);
        assertThat(minotor.isActionAllowed(ACTION_BREAKING)).isTrue();
        assertThat(minotor.isActionAllowed(ACTION_DYING)).isTrue();
        assertThat(minotor.isActionAllowed(ACTION_WALKING)).isTrue();
    }

    @Test
    public void isActionAllowedShouldReturnFalse() throws Exception {
        Minotor minotor = new Minotor(15, 30);
        assertThat(minotor.isActionAllowed(ACTION_FLYING)).isFalse();
        assertThat(minotor.isActionAllowed(ACTION_WAITING)).isFalse();
        assertThat(minotor.isActionAllowed(ACTION_WINING)).isFalse();
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_DYING);
        minotor.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndWalkingToTheSameDirectionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.setLastSpriteAction(ACTION_WALKING);
        minotor.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndBreakingToTheSameDirectionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.setLastSpriteAction(ACTION_BREAKING);
        minotor.setLastDirection(Direction.DIRECTION_NORTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setLastSpriteAction(ACTION_DYING);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedWalkingToADifferentDirectionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.setLastSpriteAction(ACTION_WALKING);
        minotor.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedBreakingToADifferentDirectionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.setLastSpriteAction(ACTION_BREAKING);
        minotor.setLastDirection(Direction.DIRECTION_SOUTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // breaking back.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getBreakBackImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbBreakFrame());

        // breaking back.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_SOUTH);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getBreakFrontImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbBreakFrame());

        // breaking back.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_WEST);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getBreakLeftImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbBreakFrame());

        // breaking back.
        minotor.setCurSpriteAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.DIRECTION_EAST);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getBreakRightImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbBreakFrame());

        // dying.
        minotor.setCurSpriteAction(ACTION_DYING);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getDeathImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbDeathFrame());

        // walking back.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_NORTH);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getWalkBackImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbWalkFrame());

        // walking front.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_SOUTH);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getWalkFrontImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbWalkFrame());

        // walking left.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_WEST);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getWalkLeftImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbWalkFrame());

        // walking right.
        minotor.setCurSpriteAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.DIRECTION_EAST);
        minotor.updateSprite();
        assertThat(minotor.getImages()).isEqualTo(minotor.getWalkRightImages());
        assertThat(minotor.getNbImages()).isEqualTo(minotor.getNbWalkFrame());
    }
}