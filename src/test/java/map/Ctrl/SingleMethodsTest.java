package map.Ctrl;

import images.ImagesLoader;
import map.RMapPoint;
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
        RMapPoint rMapPoint1 = new RMapPoint(0, 0);
        rMapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSinglePathwayOnMap(rMapPoint1, 0)).isFalse();
        assertThat(rMapPoint1.getImage()).isNull();

        // should return true because available and put a static pathway.
        RMapPoint rMapPoint2 = new RMapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(rMapPoint2, 0)).isTrue();
        assertThat(rMapPoint2.getImage()).isNotNull();
        assertThat(rMapPoint2.getImages()).isNull();

        // should return true because available and put a dynamic pathway.
        RMapPoint rMapPoint3 = new RMapPoint(0, 0);
        assertThat(SingleMethods.placeSinglePathwayOnMap(rMapPoint3, 100)).isTrue();
        assertThat(rMapPoint3.getImage()).isNull();
        assertThat(rMapPoint3.getImages()).isNotNull();
    }

    @Test
    public void placeSingleMutableOnMapShouldWorkAsExpected() throws Exception {
        // test all cases in a single function to avaid calling fillImagesMatrix() several times.

        // should return false because not available.
        RMapPoint rMapPoint1 = new RMapPoint(0, 0);
        rMapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSingleMutableOnMap(rMapPoint1)).isFalse();
        assertThat(rMapPoint1.getImage()).isNull();

        // should return true because available and put a static mutable.
        RMapPoint rMapPoint2 = new RMapPoint(0, 0);
        assertThat(SingleMethods.placeSingleMutableOnMap(rMapPoint2)).isTrue();
        assertThat(rMapPoint2.getImage()).isNotNull();
    }

    @Test
    public void placeSingleObstacleOnMapShouldWorkAsExpected() throws Exception {
        // test all cases in a single function to avaid calling fillImagesMatrix() several times.

        // should return false because not available.
        RMapPoint rMapPoint1 = new RMapPoint(0, 0);
        rMapPoint1.setAvailable(false);
        assertThat(SingleMethods.placeSingleObstacleOnMap(rMapPoint1)).isFalse();
        assertThat(rMapPoint1.getImage()).isNull();

        // should return true because available and put a static obstacle.
        RMapPoint rMapPoint2 = new RMapPoint(0, 0);
        assertThat(SingleMethods.placeSingleObstacleOnMap(rMapPoint2)).isTrue();
        assertThat(rMapPoint2.getImage()).isNotNull();
    }
}