package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class Tuple2Test implements WithAssertions {

    @Test
    void getterShouldReturnExpectedValue() {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Eddy", 1000);
        assertThat(tuple2.getFirst()).isEqualTo("Eddy");
        assertThat(tuple2.getSecond()).isEqualTo(1000);
    }

    @Test
    void setterShouldSetValues() {
        Tuple2<String, Integer> tuple2 = new Tuple2<>("Eddy", 1000);
        tuple2.setFirst("Rude");
        tuple2.setSecond(2000);
        assertThat(tuple2.getFirst()).isEqualTo("Rude");
        assertThat(tuple2.getSecond()).isEqualTo(2000);
    }
}
