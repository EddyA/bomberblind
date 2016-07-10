package images;

import images.ImagesLoader;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImagesLoaderTest {

    @Test
    public void createImageShouldThrowAnExceptionWithABadPath() throws Exception {
        String path = "bad_path/img.png";
        assertThatThrownBy(() -> ImagesLoader.createImage(path))
                .isInstanceOf(IOException.class)
                .hasMessage("file not found: " + path);
    }

    @Test
    public void createImageShouldSuccesWithAGoodPath() throws Exception {
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
    public void fillImagesMatrixShouldFillTheAppropriateNumberOfRows() throws Exception {
        ImagesLoader.fillImagesMatrix();
        assertThat(ImagesLoader.NB_MATRIX_ROW).isEqualTo(ImagesLoader.lastRowIdx + 1);
    }
}