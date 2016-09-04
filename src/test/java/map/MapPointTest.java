package map;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.Mockito;
import utils.CurrentTimeSupplier;

import java.awt.*;
import java.time.Instant;

import static org.mockito.Mockito.mock;

public class MapPointTest implements WithAssertions {

    @Test
    public void MapPointShouldSetMembersWithExpectedValues() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        assertThat(MapPoint.getRowIdx()).isEqualTo(5);
        assertThat(MapPoint.getColIdx()).isEqualTo(10);
        assertThat(MapPoint.isAvailable()).isTrue();
        assertThat(MapPoint.isPathway()).isFalse();
        assertThat(MapPoint.isMutable()).isFalse();
        assertThat(MapPoint.isBombing()).isFalse();
        assertThat(MapPoint.isBurning()).isFalse();
    }

    @Test
    public void setAvailableShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setAvailable(false);
        assertThat(MapPoint.isAvailable()).isFalse();
    }

    @Test
    public void setPathwayShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setPathway(true);
        assertThat(MapPoint.isPathway()).isTrue();
    }

    @Test
    public void setMutableShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setMutable(true);
        assertThat(MapPoint.isMutable()).isTrue();
    }

    @Test
    public void setBombingShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setBombing(true);
        assertThat(MapPoint.isBombing()).isTrue();
    }

    @Test
    public void addAndRemoveFlameShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.addFlame();
        MapPoint.addFlame();
        assertThat(MapPoint.isBurning()).isTrue();
        MapPoint.removeFlame();
        MapPoint.removeFlame();
        assertThat(MapPoint.isBurning()).isFalse();
    }

    @Test
    public void setImageShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        Image img = ImagesLoader.createImage("/images/icon.gif");
        MapPoint.setImage(img);
        assertThat(MapPoint.image).isEqualTo(img);
    }

    @Test
    public void setImagesShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        Image[] imgArray = new Image[1];
        MapPoint.setImages(imgArray, 1);
        assertThat(MapPoint.images).isEqualTo(imgArray);
    }

    @Test
    public void setRefreshTimeShouldSetTheMemberWithExpectedValue() throws Exception {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setRefreshTime(100);
        assertThat(MapPoint.refreshTime).isEqualTo(100);
    }

    @Test
    public void setImageAsBurnedShouldSetTheMemberWithAnImage() throws Exception {
        Image img = ImagesLoader.createImage("/images/icon.gif");

        MapPoint MapPoint = mock(MapPoint.class);
        Mockito.doAnswer((t) -> {
            MapPoint.image = img;
            return null;
        }).when(MapPoint).setImageAsBurned();

        MapPoint.setImageAsBurned();
        assertThat(MapPoint.image).isEqualTo(img);
    }

    @Test
    public void updateImageShouldSetTheMemberWithExpectedValue() throws Exception {

        // mock CurrentTimeSupplier class to set currentTimeMillis to 1000ms.
        CurrentTimeSupplier currentTimeSupplier = mock(CurrentTimeSupplier.class);
        Mockito.when(currentTimeSupplier.get()).thenReturn(Instant.ofEpochMilli(1000L));

        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setRefreshTime(100); // set the refresh time to 100ms.
        MapPoint.currentTimeSupplier = currentTimeSupplier;

        Image[] imgArray = new Image[4];
        imgArray[0] = ImagesLoader.createImage("/images/icon.gif");
        imgArray[1] = ImagesLoader.createImage("/images/icon.gif");
        imgArray[2] = ImagesLoader.createImage("/images/icon.gif");
        imgArray[3] = ImagesLoader.createImage("/images/icon.gif");
        MapPoint.setImages(imgArray, 4);
        MapPoint.curImageIdx = 1; // fix it as it is randomly init.

        MapPoint.lastRefreshTs = 1000L; // current time - last refresh time < 100ms -> image no change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[1]);
        MapPoint.lastRefreshTs = 800L; // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[2]);
        MapPoint.lastRefreshTs = 800L; // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[3]);
        MapPoint.lastRefreshTs = 800L; // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[0]);
    }
}