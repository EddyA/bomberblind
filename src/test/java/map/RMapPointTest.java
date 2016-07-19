package map;

import images.ImagesLoader;
import org.junit.Test;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RMapPointTest {

    @Test
    public void rMapPointShouldSetMembersWithTheAppropriateValues() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        assertThat(rMapPoint.getRowIdx()).isEqualTo(5);
        assertThat(rMapPoint.getColIdx()).isEqualTo(10);
        assertThat(rMapPoint.isAvailable()).isTrue();
        assertThat(rMapPoint.isPathway()).isFalse();
        assertThat(rMapPoint.isMutable()).isFalse();
        assertThat(rMapPoint.isBombing()).isFalse();
        assertThat(rMapPoint.isBurning()).isFalse();
    }

    @Test
    public void setAvailableShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setAvailable(false);
        assertThat(rMapPoint.isAvailable()).isFalse();
    }

    @Test
    public void setPathwayShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setPathway(true);
        assertThat(rMapPoint.isPathway()).isTrue();
    }

    @Test
    public void setMutableShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setMutable(true);
        assertThat(rMapPoint.isMutable()).isTrue();
    }

    @Test
    public void setBombingShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setBombing(true);
        assertThat(rMapPoint.isBombing()).isTrue();
    }

    @Test
    public void addAndRemoveFlameShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.addFlame();
        rMapPoint.addFlame();
        assertThat(rMapPoint.isBurning()).isTrue();
        rMapPoint.removeFlame();
        rMapPoint.removeFlame();
        assertThat(rMapPoint.isBurning()).isFalse();
    }

    @Test
    public void setImageShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        Image img = ImageIO.read(ImagesLoader.class.getResource("/images/bomb_01.png"));
        rMapPoint.setImage(img);
        assertThat(rMapPoint.image).isEqualTo(img);
    }

    @Test
    public void setImagesShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        Image[] imgArray = new Image[1];
        rMapPoint.setImages(imgArray, 1);
        assertThat(rMapPoint.images).isEqualTo(imgArray);
    }

    @Test
    public void setRefreshTimeShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setRefreshTime(100);
        assertThat(rMapPoint.refreshTime).isEqualTo(100);
    }

    @Test
    public void updateImageShouldSetTheMemberWithTheAppropriateValue() throws Exception {
        RMapPoint rMapPoint = new RMapPoint(5, 10);
        rMapPoint.setRefreshTime(100); // set the refresh time to 100ms.

        Image[] imgArray = new Image[4];
        imgArray[0] = ImageIO.read(ImagesLoader.class.getResource("/images/bomb_01.png"));
        imgArray[1] = ImageIO.read(ImagesLoader.class.getResource("/images/bomb_02.png"));
        imgArray[2] = ImageIO.read(ImagesLoader.class.getResource("/images/bomb_03.png"));
        imgArray[3] = ImageIO.read(ImagesLoader.class.getResource("/images/bomb_04.png"));
        rMapPoint.setImages(imgArray, 4);

        Mockito.mock(System.class);
        Mockito.when(System.currentTimeMillis()).thenReturn(1000L); // set the current timestamp.

        rMapPoint.lastRefreshTs = 1080L;
        assertThat(rMapPoint.updateImage()).isEqualTo(imgArray[0]);
    }
}