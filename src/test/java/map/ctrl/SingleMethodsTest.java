package map.ctrl;

import images.ImagesLoader;
import map.MapPoint;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SingleMethodsTest implements WithAssertions {

    @Before
    public void fillImagesMatrix() throws IOException {
        ImagesLoader.fillImagesMatrix();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnFalseBecauseNotAvailable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0, 0)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutAVirginSinglePathway() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0, 0)).isTrue();
        assertThat(mapPoint.getImage()).isEqualTo(ImagesLoader.getVirginSinglePathway());
        assertThat(mapPoint.getImages()).isNull();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutADecoratedSinglePathway() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 100, 0)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
        assertThat(mapPoint.getImages()).isNull();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutADynamicPathway() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 100, 100)).isTrue();
        assertThat(mapPoint.getImage()).isNull();
        assertThat(mapPoint.getImages()).isNotNull();
    }

    @Test
    public void placeSingleMutableObstacleOnMapShouldReturnFalseBecauseNotAvailable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleMutableObstacleOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSingleMutableObstacleOnMapShouldReturnTrueBecauseAvailableAndPutAMutableObstacle() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleMutableObstacleOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }

    @Test
    public void placeSingleImmutableObstacleOnMapShouldReturnFalseBecauseNotAvailable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleImmutableObstacleOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSingleImmutableObstacleOnMapShouldReturnTrueBecauseAvailableAndPutAnImmutableObstacle() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleImmutableObstacleOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }
}