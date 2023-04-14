package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprite.SpriteType;

import java.io.IOException;

import static sprite.SpriteAction.ACTION_WALKING;

class ZoraTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void constructorShouldSetMembersWithTheExpectedValues() {
        Zora zora = new Zora(15, 30);
        assertThat(zora.getXMap()).isEqualTo(15);
        assertThat(zora.getYMap()).isEqualTo(30);
        assertThat(zora.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_WALKING_ENEMY);

        // - dying values.
        assertThat(zora.getDeathImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.deathMatrixRowIdx]);
        assertThat(zora.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_DEATH_FRAME);
        assertThat(zora.getDeathRefreshTime()).isEqualTo(Zora.DEATH_REFRESH_TIME);

        // - walking values.
        assertThat(zora.getWalkBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkBackMatrixRowIdx]);
        assertThat(zora.getWalkFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkFrontMatrixRowIdx]);
        assertThat(zora.getWalkLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkLeftMatrixRowIdx]);
        assertThat(zora.getWalkRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.zoraWalkRightMatrixRowIdx]);
        assertThat(zora.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_ZORA_WALK_FRAME);
        assertThat(zora.getWalkRefreshTime()).isEqualTo(Zora.WALK_REFRESH_TIME);

        assertThat(zora.getActingTime()).isEqualTo(Zora.ACTING_TIME);
        assertThat(zora.getCurSpriteAction()).isEqualTo(ACTION_WALKING);
        assertThat(zora.getRefreshTime()).isEqualTo(Zora.WALK_REFRESH_TIME);
    }
}
