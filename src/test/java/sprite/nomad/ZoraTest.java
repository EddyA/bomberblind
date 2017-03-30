package sprite.nomad;

import static sprite.SpriteAction.ACTION_WALKING;

import java.io.IOException;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import images.ImagesLoader;
import sprite.SpriteType;

public class ZoraTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        Zora zora = new Zora(15, 30);

        // check members value.
        assertThat(zora.getxMap()).isEqualTo(15);
        assertThat(zora.getyMap()).isEqualTo(30);

        // - walking values.
        assertThat(zora.getSpriteType()).isEqualTo(SpriteType.TYPE_WALKING_ENEMY);
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