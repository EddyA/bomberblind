package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class Tuple3Test implements WithAssertions {

    @Test
    void Tuple3ShouldReturnExpectedValue() {
        Tuple3 tuple3 = new Tuple3<>("Eddy", 1000, 2.0f);
        assertThat(tuple3.first()).isEqualTo("Eddy");
        assertThat(tuple3.second()).isEqualTo(1000);
        assertThat(tuple3.third()).isEqualTo(2.0f);
    }
}
