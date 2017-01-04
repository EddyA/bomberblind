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

    @Test
    public void getCharTopOrdinateShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharTopOrdinate(50)).isEqualTo(35);
    }

    @Test
    public void getCharBottomOrdinateShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharBottomOrdinate(50)).isEqualTo(50);
    }

    @Test
    public void getCharLeftAbscissaShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharLeftAbscissa(50)).isEqualTo(35);
    }

    @Test
    public void getCharRightAbscissaShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRightAbscissa(50)).isEqualTo(64);
    }

    @Test
    public void getCharRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRowIdx(10)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharRowIdx(29)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharRowIdx(30)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharColIdx(10)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharColIdx(29)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharColIdx(30)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharTopRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharTopRowIdx(10)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharTopRowIdx(14)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharTopRowIdx(20)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharTopRowIdx(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharTopRowIdx(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharBottomRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharBottomRowIdx(20)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharBottomRowIdx(29)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharBottomRowIdx(30)).isEqualTo(1); // moving to another case.

    }

    @Test
    public void getCharLeftColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharLeftColIdx(10)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharLeftColIdx(14)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharLeftColIdx(30)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharLeftColIdx(44)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharLeftColIdx(45)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharRightColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRightColIdx(30)).isEqualTo(1); // standard case.
        assertThat(Tools.getCharRightColIdx(45)).isEqualTo(1); // limit before moving to another case.
        assertThat(Tools.getCharRightColIdx(46)).isEqualTo(2); // moving to another case.
    }

}