package sprite;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static sprite.SpriteAction.*;

public class SpriteActionTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(ACTION_BREAKING).orElse("no_name")).isEqualTo("breaking");
        assertThat(getlabel(ACTION_DYING).orElse("no_name")).isEqualTo("dying");
        assertThat(getlabel(ACTION_EXPLORING).orElse("no_name")).isEqualTo("exploring");
        assertThat(getlabel(ACTION_FLYING).orElse("no_name")).isEqualTo("flying");
        assertThat(getlabel(ACTION_WAITING).orElse("no_name")).isEqualTo("waiting");
        assertThat(getlabel(ACTION_WALKING).orElse("no_name")).isEqualTo("walking");
        assertThat(getlabel(ACTION_WINING).orElse("no_name")).isEqualTo("wining");
    }
}