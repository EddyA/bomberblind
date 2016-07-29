package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class ToolsTest implements WithAssertions {

    @Test
    public void isValidIntegerWithAnIntegerConvertibleStringShouldReturnTrue() throws Exception {
        assertThat(Tools.isValidInteger("150")).isTrue();
    }

    @Test
    public void isValidIntegerWithANullStringShouldReturnFalse() throws Exception {
        assertThat(Tools.isValidInteger(null)).isFalse();
    }

    @Test
    public void isValidIntegerWithANotIntegerConvertibleStringShouldReturnFalse() throws Exception {
        assertThat(Tools.isValidInteger("zero")).isFalse();
    }
}