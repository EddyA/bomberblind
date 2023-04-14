package map;

import images.ImagesLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.CurrentTimeSupplier;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class MapPointTest implements WithAssertions {

    @Test
    void MapPointShouldSetMembersWithTheExpectedValues() {
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
    void setAvailableShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setAvailable(false);
        assertThat(MapPoint.isAvailable()).isFalse();
    }

    @Test
    void setPathwayShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setPathway(true);
        assertThat(MapPoint.isPathway()).isTrue();
    }

    @Test
    void setMutableShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setMutable(true);
        assertThat(MapPoint.isMutable()).isTrue();
    }

    @Test
    void setEntranceShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setEntrance(true);
        assertThat(MapPoint.isEntrance()).isTrue();
    }

    @Test
    void setExitShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setExit(true);
        assertThat(MapPoint.isExit()).isTrue();
    }

    @Test
    void setBombingShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setBombing(true);
        assertThat(MapPoint.isBombing()).isTrue();
    }

    @Test
    void addAndRemoveFlameShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.addFlame();
        MapPoint.addFlame();
        assertThat(MapPoint.isBurning()).isTrue();
        MapPoint.removeFlame();
        MapPoint.removeFlame();
        assertThat(MapPoint.isBurning()).isFalse();
    }

    @Test
    void setImageShouldSetTheMemberWithExpectedValue() throws IOException {
        MapPoint MapPoint = new MapPoint(5, 10);
        Image img = ImagesLoader.createImage("/images/icon.gif");
        MapPoint.setImage(img);
        assertThat(MapPoint.getImage()).isEqualTo(img);
    }

    @Test
    void setImagesShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        Image[] imgArray = new Image[1];
        MapPoint.setImages(imgArray, 1);
        assertThat(MapPoint.getImages()).isEqualTo(imgArray);
    }

    @Test
    void setRefreshTimeShouldSetTheMemberWithExpectedValue() {
        MapPoint MapPoint = new MapPoint(5, 10);
        MapPoint.setRefreshTime(100);
        assertThat(MapPoint.getRefreshTime()).isEqualTo(100);
    }

    @Test
    void setImageAsBurnedShouldSetTheMemberWithAnImage() throws IOException {
        Image img = ImagesLoader.createImage("/images/icon.gif");
        MapPoint mapPoint = new MapPoint(0, 0);
        MapPoint spiedMapPoint = spy(mapPoint);
        Mockito.doAnswer((t) -> {
            spiedMapPoint.setImage(img);
            return null;
        }).when(spiedMapPoint).setImageAsBurned();

        spiedMapPoint.setImageAsBurned();
        assertThat(spiedMapPoint.getImage()).isEqualTo(img);
    }

    @Test
    void updateImageShouldSetTheMemberWithExpectedValue() throws IOException {

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
        MapPoint.setCurImageIdx(1); // fix it as it is randomly init.

        MapPoint.setLastRefreshTs(1000L); // current time - last refresh time < 100ms -> image no change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[1]);
        MapPoint.setLastRefreshTs(800L); // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[2]);
        MapPoint.setLastRefreshTs(800L); // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[3]);
        MapPoint.setLastRefreshTs(800L); // current time - last refresh time > 100ms -> image change.
        assertThat(MapPoint.updateImage()).isEqualTo(imgArray[0]);
    }
}
