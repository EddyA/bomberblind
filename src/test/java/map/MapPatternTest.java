package map;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.awt.*;

public class MapPatternTest implements WithAssertions {

    @Test
    public void MapPatternShouldSetMembersWithTheAppropriateValues() throws Exception {
        Image[] imgArray = new Image[20];
        MapPattern MapPattern = new MapPattern(imgArray, 4, 5, true, true, "myPattern");

        assertThat(MapPattern.getImageArray()).isEqualTo(imgArray);
        assertThat(MapPattern.getWidth()).isEqualTo(4);
        assertThat(MapPattern.getHeight()).isEqualTo(5);
        assertThat(MapPattern.isPathway()).isTrue();
        assertThat(MapPattern.isMutable()).isTrue();
        assertThat(MapPattern.getName()).isEqualTo("myPattern");
    }
}