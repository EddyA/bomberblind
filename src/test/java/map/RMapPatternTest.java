package map;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Image;

import org.junit.Test;

public class RMapPatternTest {

    @Test
    public void rMapPatternShouldSetMembersWithTheAppropriateValues() throws Exception {
        Image[] imgArray = new Image[20];
        RMapPattern rMapPattern = new RMapPattern(imgArray, 4, 5, true, true, "myPattern");

        assertThat(rMapPattern.getImageArray()).isEqualTo(imgArray);
        assertThat(rMapPattern.getWidth()).isEqualTo(4);
        assertThat(rMapPattern.getHeight()).isEqualTo(5);
        assertThat(rMapPattern.isPathway()).isTrue();
        assertThat(rMapPattern.isMutable()).isTrue();
        assertThat(rMapPattern.getName()).isEqualTo("myPattern");
    }
}