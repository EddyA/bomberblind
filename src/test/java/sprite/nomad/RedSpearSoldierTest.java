package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprite.SpriteType;

import java.io.IOException;

class RedSpearSoldierTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        RedSpearSoldier redSpearSoldier = new RedSpearSoldier(15, 30);
        assertThat(redSpearSoldier.getXMap()).isEqualTo(15);
        assertThat(redSpearSoldier.getYMap()).isEqualTo(30);
        assertThat(redSpearSoldier.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_BREAKING_ENEMY);

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
}
