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
    public void placeSinglePathwayOnMapShouldWorkAsExpected() throws Exception {
        // test all cases in a single function to avaid calling fillImagesMatrix() several times.

        // should return false because not available.
        MapPoint mapPoint1 = new MapPoint(0, 0);
        mapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint1, 0)).isFalse();
        assertThat(mapPoint1.getImage()).isNull();

        // should return true because available and put a static pathway.
        MapPoint mapPoint2 = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint2, 0)).isTrue();
        assertThat(mapPoint2.getImage()).isNotNull();
        assertThat(mapPoint2.getImages()).isNull();

        // should return true because available and put a dynamic pathway.
        MapPoint mapPoint3 = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(mapPoint3, 100)).isTrue();
        assertThat(mapPoint3.getImage()).isNull();
        assertThat(mapPoint3.getImages()).isNotNull();
    }

    @Test
    public void placeSingleMutableOnMapShouldWorkAsExpected() throws Exception {
        // test all cases in a single function to avaid calling fillImagesMatrix() several times.

        // should return false because not available.
        MapPoint mapPoint1 = new MapPoint(0, 0);
        mapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSingleMutableOnMap(mapPoint1)).isFalse();
        assertThat(mapPoint1.getImage()).isNull();

        // should return true because available and put a static mutable.
        MapPoint mapPoint2 = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleMutableOnMap(mapPoint2)).isTrue();
        assertThat(mapPoint2.getImage()).isNotNull();
    }

    @Test
    public void placeSingleObstacleOnMapShouldWorkAsExpected() throws Exception {
        // test all cases in a single function to avaid calling fillImagesMatrix() several times.

        // should return false because not available.
        MapPoint mapPoint1 = new MapPoint(0, 0);
        mapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSingleObstacleOnMap(mapPoint1)).isFalse();
        assertThat(mapPoint1.getImage()).isNull();

        // should return true because available and put a static obstacle.
        MapPoint mapPoint2 = new MapPoint(0, 0);
        assertThat(SingleMethods.placeSingleObstacleOnMap(mapPoint2)).isTrue();
        assertThat(mapPoint2.getImage()).isNotNull();
    }
}