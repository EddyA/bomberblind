package map.ctrl;

import images.ImagesLoader;
import io.cucumber.java.Before;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SingleMethodsTest implements WithAssertions {

    @BeforeEach
    void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    void placeSinglePathwayOnMapShouldReturnFalseBecauseNotAvailable() {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0, 0)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutAVirginSinglePathway() {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0, 0)).isTrue();
        assertThat(mapPoint.getImage()).isEqualTo(ImagesLoader.getVirginSinglePathway());
        assertThat(mapPoint.getImages()).isNull();
    }

    @Test
    void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutADecoratedSinglePathway() {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 100, 0)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
        assertThat(mapPoint.getImages()).isNull();
    }

    @Test
    void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutADynamicPathway() {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 100, 100)).isTrue();
        assertThat(mapPoint.getImage()).isNull();
        assertThat(mapPoint.getImages()).isNotNull();
    }

    @Test
    void placeSingleMutableObstacleOnMapShouldReturnFalseBecauseNotAvailable() {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleMutableObstacleOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    void placeSingleMutableObstacleOnMapShouldReturnTrueBecauseAvailableAndPutAMutableObstacle() {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleMutableObstacleOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }

    @Test
    void placeSingleImmutableObstacleOnMapShouldReturnFalseBecauseNotAvailable() {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleImmutableObstacleOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    void placeSingleImmutableObstacleOnMapShouldReturnTrueBecauseAvailableAndPutAnImmutableObstacle() {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleImmutableObstacleOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }
}
