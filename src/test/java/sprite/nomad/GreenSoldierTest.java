package sprite.nomad;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import sprite.SpriteType;

import java.io.IOException;

import static sprite.SpriteAction.ACTION_WALKING;


public class GreenSoldierTest implements WithAssertions {
    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void constructorShouldSetMembersWithTheExpectedValues() throws Exception {
        GreenSoldier greenSoldier = new GreenSoldier(15, 30);
        assertThat(greenSoldier.getxMap()).isEqualTo(15);
        assertThat(greenSoldier.getyMap()).isEqualTo(30);
        assertThat(greenSoldier.getSpriteType()).isEqualTo(SpriteType.TYPE_SPRITE_WALKING_ENEMY);

        // - dying values.
        assertThat(greenSoldier.getDeathImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.deathMatrixRowIdx]);
        assertThat(greenSoldier.getNbDeathFrame()).isEqualTo(ImagesLoader.NB_DEATH_FRAME);
        assertThat(greenSoldier.getDeathRefreshTime()).isEqualTo(GreenSoldier.DEATH_REFRESH_TIME);

        // - walking values.
        assertThat(greenSoldier.getWalkBackImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkBackMatrixRowIdx]);
        assertThat(greenSoldier.getWalkFrontImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkFrontMatrixRowIdx]);
        assertThat(greenSoldier.getWalkLeftImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkLeftMatrixRowIdx]);
        assertThat(greenSoldier.getWalkRightImages()).isEqualTo(ImagesLoader.imagesMatrix[ImagesLoader.greenSoldierWalkRightMatrixRowIdx]);
        assertThat(greenSoldier.getNbWalkFrame()).isEqualTo(ImagesLoader.NB_GREEN_SOLDIER_WALK_FRAME);
        assertThat(greenSoldier.getWalkRefreshTime()).isEqualTo(GreenSoldier.WALK_REFRESH_TIME);

        assertThat(greenSoldier.getActingTime()).isEqualTo(GreenSoldier.ACTING_TIME);
        assertThat(greenSoldier.getCurSpriteAction()).isEqualTo(ACTION_WALKING);
        assertThat(greenSoldier.getRefreshTime()).isEqualTo(GreenSoldier.WALK_REFRESH_TIME);
    }
}