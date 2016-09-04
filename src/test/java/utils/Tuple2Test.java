package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class Tuple2Test implements WithAssertions {

    @Test
    public void Tuple2ShouldReturnExpectedValue() throws Exception {
        Tuple2 tuple2 = new Tuple2<>("Eddy", 1000);
        assertThat(tuple2.getFirst()).isEqualTo("Eddy");
        assertThat(tuple2.getSecond()).isEqualTo(1000);
    }
}