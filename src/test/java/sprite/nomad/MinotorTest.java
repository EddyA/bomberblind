package sprite.nomad;

import static sprite.SpriteAction.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;

public class MinotorTest implements WithAssertions {

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
}