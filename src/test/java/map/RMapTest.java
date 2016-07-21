package map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class RMapTest {

    @Test
    public void rMapShouldSetMembersWithTheAppropriateValues() throws Exception {
        RMap rMap = new RMap(5, 10, 2, 3);
        assertThat(rMap.getMapWidth()).isEqualTo(5);
        assertThat(rMap.getMapHeight()).isEqualTo(10);
        assertThat(rMap.getScreenWidth()).isEqualTo(2);
        assertThat(rMap.getScreenHeight()).isEqualTo(3);
        int rowIdx = 0;
        int colIdx = 0;
        try {
            for (rowIdx = 0; rowIdx < rMap.getMapHeight() + 10; rowIdx++) {
                for (colIdx = 0; colIdx < rMap.getMapWidth() + 10; colIdx++) {
                    assertThat(rMap.getRMapPoint(rowIdx, colIdx)).isNotNull();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (rowIdx < rMap.getMapHeight() && colIdx < rMap.getMapWidth()) {
                fail("Should not fail."); // should failed if asked case is out of bounds.
            }
        }
    }

    @Test
    public void rMapShouldSetMembersWithTheAppropriateValues() throws Exception {
        RMap rMap = new RMap(5, 10, 2, 3);

    }
}