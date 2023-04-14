package images;

import java.io.IOException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class ImagesLoaderTest implements WithAssertions {

    @Test
    void createImageShouldThrowAnExceptionWithABadPath() {
        String path = "bad_path/img.png";
        assertThatThrownBy(() -> ImagesLoader.createImage(path))
            .isInstanceOf(IOException.class)
            .hasMessage("file not found: " + path);
    }

    @Test
    void createImageShouldSuccessWithAGoodPath() {
        IOException myException = null;
        String path = "/images/icon.gif";
        try {
            ImagesLoader.createImage(path);
        } catch (IOException e) {
            myException = e;
        }
        assertThat(myException).isNull();
    }

    @Test
    void fillImagesMatrixShouldFillExpectedNumberOfRows() throws IOException {
        ImagesLoader.fillImagesMatrix();
        assertThat(ImagesLoader.NB_MATRIX_ROW).isEqualTo(ImagesLoader.lastMatrixRowIdx + 1);
    }
}
