package utils;


import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class Tuple3Test implements WithAssertions {

    @Test
    public void Tuple3ShouldReturnExpectedValue() throws Exception {
        Tuple3 tuple3 = new Tuple3<>("Eddy", 1000, 2.0f);
        assertThat(tuple3.getFirst()).isEqualTo("Eddy");
        assertThat(tuple3.getSecond()).isEqualTo(1000);
        assertThat(tuple3.getThird()).isEqualTo(2.0f);
    }
}