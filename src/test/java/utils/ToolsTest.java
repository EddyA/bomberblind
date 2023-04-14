package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static images.ImagesLoader.IMAGE_SIZE;

class ToolsTest implements WithAssertions {

    @Test
    void isValidIntegerWithAnIntegerConvertibleStringShouldReturnTrue() {
        assertThat(Tools.isNotNullAndValidInteger("150")).isTrue();
    }

    @Test
    void isValidIntegerWithANullStringShouldReturnFalse() {
        assertThat(Tools.isNotNullAndValidInteger(null)).isFalse();
    }

    @Test
    void isValidIntegerWithANotIntegerConvertibleStringShouldReturnFalse() {
        assertThat(Tools.isNotNullAndValidInteger("zero")).isFalse();
    }

    @Test
    void getCharTopOrdinateShouldReturnExpectedValue() {
        assertThat(Tools.getCharTopOrdinate(50)).isEqualTo(50 - IMAGE_SIZE / 2);
    }

    @Test
    void getCharBottomOrdinateShouldReturnExpectedValue() {
        assertThat(Tools.getCharBottomOrdinate(50)).isEqualTo(50);
    }

    @Test
    void getCharLeftAbscissaShouldReturnExpectedValue() {
        assertThat(Tools.getCharLeftAbscissa(50)).isEqualTo(50 - IMAGE_SIZE / 2);
    }

    @Test
    void getCharRightAbscissaShouldReturnExpectedValue() {
        assertThat(Tools.getCharRightAbscissa(50)).isEqualTo(50 + IMAGE_SIZE / 2 - 1);
    }

    @Test
    void getCharRowIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE / 3)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.
    }

    @Test
    void getCharColIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharColIdx(IMAGE_SIZE / 3)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharColIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharColIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.
    }

    @Test
    void getCharTopRowIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 3)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 2 - 1)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 3 * 2)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE + IMAGE_SIZE / 2 - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // moving to another case.
    }

    @Test
    void getCharBottomRowIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE / 2)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.

    }

    @Test
    void getCharLeftColIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE / 3)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE / 2 - 1)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE + IMAGE_SIZE / 2 - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // moving to another case.
    }

    @Test
    void getCharRightColIdxShouldReturnExpectedValue() {
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE)).isEqualTo(1); // standard case.
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // limit before moving to another case.
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE + IMAGE_SIZE / 2 + 1)).isEqualTo(2); // moving to another case.
    }

    @Test
    void getCaseCentreAbscissaShouldReturnTheExpectedValue() {
        assertThat(Tools.getCaseCentreAbscissa(0)).isEqualTo(IMAGE_SIZE / 2);
        assertThat(Tools.getCaseCentreAbscissa(1)).isEqualTo(IMAGE_SIZE + IMAGE_SIZE / 2);
        assertThat(Tools.getCaseCentreAbscissa(10)).isEqualTo(10 * IMAGE_SIZE + IMAGE_SIZE / 2);
    }

    @Test
    void getCaseBottomOrdinateShouldReturnTheExpectedValue() {
        assertThat(Tools.getCaseBottomOrdinate(0)).isEqualTo(IMAGE_SIZE - 1);
        assertThat(Tools.getCaseBottomOrdinate(1)).isEqualTo(2 * IMAGE_SIZE - 1);
        assertThat(Tools.getCaseBottomOrdinate(10)).isEqualTo(11 * IMAGE_SIZE - 1);
    }
}
