package map;

import java.awt.Image;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class MapPatternTest implements WithAssertions {

    @Test
    void MapPatternShouldSetMembersWithTheExpectedValues() {
        Image[] imgArray = new Image[20];
        MapPattern MapPattern = new MapPattern(imgArray, 4, 5, true, true, true, true, "myPattern");

        assertThat(MapPattern.imageArray()).isEqualTo(imgArray);
        assertThat(MapPattern.width()).isEqualTo(4);
        assertThat(MapPattern.height()).isEqualTo(5);
        assertThat(MapPattern.isPathway()).isTrue();
        assertThat(MapPattern.isMutable()).isTrue();
        assertThat(MapPattern.isEntrance()).isTrue();
        assertThat(MapPattern.isExit()).isTrue();
        assertThat(MapPattern.name()).isEqualTo("myPattern");
    }
}
