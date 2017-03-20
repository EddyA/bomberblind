package utils;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static images.ImagesLoader.IMAGE_SIZE;

public class ToolsTest implements WithAssertions {

    @Test
    public void isValidIntegerWithAnIntegerConvertibleStringShouldReturnTrue() throws Exception {
        assertThat(Tools.isNotNullAndValidInteger("150")).isTrue();
    }

    @Test
    public void isValidIntegerWithANullStringShouldReturnFalse() throws Exception {
        assertThat(Tools.isNotNullAndValidInteger(null)).isFalse();
    }

    @Test
    public void isValidIntegerWithANotIntegerConvertibleStringShouldReturnFalse() throws Exception {
        assertThat(Tools.isNotNullAndValidInteger("zero")).isFalse();
    }

    @Test
    public void getCharTopOrdinateShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharTopOrdinate(50)).isEqualTo(50 - IMAGE_SIZE / 2);
    }

    @Test
    public void getCharBottomOrdinateShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharBottomOrdinate(50)).isEqualTo(50);
    }

    @Test
    public void getCharLeftAbscissaShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharLeftAbscissa(50)).isEqualTo(50 - IMAGE_SIZE / 2);
    }

    @Test
    public void getCharRightAbscissaShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRightAbscissa(50)).isEqualTo(50 + IMAGE_SIZE / 2 - 1);
    }

    @Test
    public void getCharRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE / 3)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharRowIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharColIdx(IMAGE_SIZE / 3)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharColIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharColIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharTopRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 3)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 2 - 1)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE / 3 * 2)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE + IMAGE_SIZE / 2 - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharTopRowIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharBottomRowIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE / 2)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharBottomRowIdx(IMAGE_SIZE)).isEqualTo(1); // moving to another case.

    }

    @Test
    public void getCharLeftColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE / 3)).isEqualTo(-1); // negative case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE / 2 - 1)).isEqualTo(-1); // limit before negative case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE)).isEqualTo(0); // standard case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE + IMAGE_SIZE / 2 - 1)).isEqualTo(0); // limit before moving to another case.
        assertThat(Tools.getCharLeftColIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // moving to another case.
    }

    @Test
    public void getCharRightColIdxShouldReturnExpectedValue() throws Exception {
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE)).isEqualTo(1); // standard case.
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE + IMAGE_SIZE / 2)).isEqualTo(1); // limit before moving to another case.
        assertThat(Tools.getCharRightColIdx(IMAGE_SIZE + IMAGE_SIZE / 2 + 1)).isEqualTo(2); // moving to another case.
    }

    @Test
    public void getCaseCentreAbscissaShouldReturnTheExpectedValue() throws Exception {
        assertThat(Tools.getCaseCentreAbscissa(0)).isEqualTo(IMAGE_SIZE / 2);
        assertThat(Tools.getCaseCentreAbscissa(1)).isEqualTo(IMAGE_SIZE + IMAGE_SIZE / 2);
        assertThat(Tools.getCaseCentreAbscissa(10)).isEqualTo(10 * IMAGE_SIZE + IMAGE_SIZE / 2);
    }

    @Test
    public void getCaseBottomOrdinateShouldReturnTheExpectedValue() throws Exception {
        assertThat(Tools.getCaseBottomOrdinate(0)).isEqualTo(IMAGE_SIZE - 1);
        assertThat(Tools.getCaseBottomOrdinate(1)).isEqualTo(2 * IMAGE_SIZE - 1);
        assertThat(Tools.getCaseBottomOrdinate(10)).isEqualTo(11 * IMAGE_SIZE - 1);
    }
}