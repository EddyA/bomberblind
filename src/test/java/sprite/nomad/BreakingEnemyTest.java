package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;
import utils.Direction;

import java.io.IOException;

import static utils.Action.ACTION_BREAKING;
import static utils.Action.ACTION_DYING;
import static utils.Action.ACTION_WALKING;

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
        assertThat(minotor.getSpriteType()).isEqualTo(SpriteType. BREAKING_ENEMY);
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
        assertThat(minotor.getCurAction()).isEqualTo(ACTION_WALKING);
    }

    @Test
    public void hasActionChangedWithTheSameActionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_DYING);
        minotor.setLastAction(ACTION_DYING);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndWalkingToTheSameDirectionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.setLastAction(ACTION_WALKING);
        minotor.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedAndBreakingToTheSameDirectionShouldReturnFalse() {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.setLastAction(ACTION_BREAKING);
        minotor.setLastDirection(Direction.NORTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isFalse();
    }

    @Test
    public void hasActionChangedWithADifferentActionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setLastAction(ACTION_DYING);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedAndWalkingToADifferentDirectionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.setLastAction(ACTION_WALKING);
        minotor.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void hasActionChangedAndBreakingToADifferentDirectionShouldReturnTrue() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // set test.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.setLastAction(ACTION_BREAKING);
        minotor.setLastDirection(Direction.SOUTH);

        // call & check.
        assertThat(minotor.hasActionChanged()).isTrue();
    }

    @Test
    public void updateSpriteShouldSetTheExpectedMember() throws Exception {
        Minotor minotor = new Minotor(15, 30);

        // dying.
        minotor.setCurAction(ACTION_DYING);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorDeathMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_MINOTOR_DEATH_FRAME);

        // breaking back.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakBackMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_MINOTOR_BREAK_FRAME);

        // breaking back.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.SOUTH);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakFrontMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_MINOTOR_BREAK_FRAME);

        // breaking back.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.WEST);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakLeftMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_MINOTOR_BREAK_FRAME);

        // breaking back.
        minotor.setCurAction(ACTION_BREAKING);
        minotor.setCurDirection(Direction.EAST);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorBreakRightMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_MINOTOR_BREAK_FRAME);

        // walking back.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.NORTH);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkBackMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking front.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.SOUTH);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkFrontMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking left.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.WEST);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkLeftMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);

        // walking right.
        minotor.setCurAction(ACTION_WALKING);
        minotor.setCurDirection(Direction.EAST);
        minotor.updateSprite();
        assertThat(minotor.getImages())
                .isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.minotorWalkRightMatrixRowIdx]);
        assertThat(minotor.getNbImages()).isEqualTo(ImagesLoader.NB_CLOAKED_SKELETON_WALK_FRAME);
    }
}