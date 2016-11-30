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
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutAStaticPathway() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 0)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
        assertThat(mapPoint.getImages()).isNull();
    }

    @Test
    public void placeSinglePathwayOnMapShouldReturnTrueBecauseAvailableAndPutADynamicPathway() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint, 100)).isTrue();
        assertThat(mapPoint.getImage()).isNull();
        assertThat(mapPoint.getImages()).isNotNull();
    }

    @Test
    public void placeSingleMutableOnMapShouldReturnFalseBecauseNotAvailable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleMutableOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSingleMutableOnMapShouldReturnTrueBecauseAvailableAndPutAStaticMutable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleMutableOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }

    @Test
    public void placeSingleObstacleOnMapShouldReturnFalseBecauseNotAvailable() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        mapPoint.setAvailable(false);
        assertThat(SingleMethods.placeSingleObstacleOnMap(mapPoint)).isFalse();
        assertThat(mapPoint.getImage()).isNull();
    }

    @Test
    public void placeSingleObstacleOnMapShouldReturnTrueBecauseAvailableAndPutAStaticObstacle() throws Exception {
        MapPoint mapPoint = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleObstacleOnMap(mapPoint)).isTrue();
        assertThat(mapPoint.getImage()).isNotNull();
    }
}