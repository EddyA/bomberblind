package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class Tuple2Test implements WithAssertions {

    @Test
    public void getterShouldReturnExpectedValue() throws Exception {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Eddy", 1000);
        assertThat(tuple2.getFirst()).isEqualTo("Eddy");
        assertThat(tuple2.getSecond()).isEqualTo(1000);
    }

    @Test
    public void setterShouldSetValues() throws Exception {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Eddy", 1000);
        tuple2.setFirst("Rude");
        tuple2.setSecond(2000);
        assertThat(tuple2.getFirst()).isEqualTo("Rude");
        assertThat(tuple2.getSecond()).isEqualTo(2000);
    }
}