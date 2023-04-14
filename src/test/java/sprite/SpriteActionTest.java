package sprite;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static sprite.SpriteAction.*;

class SpriteActionTest implements WithAssertions {

    @Test
    void getLabelShouldReturnTheAppropriateLabel() {
        assertThat(getLabel(ACTION_BREAKING).orElse("no_name")).isEqualTo("breaking");
        assertThat(getLabel(ACTION_DYING).orElse("no_name")).isEqualTo("dying");
        assertThat(getLabel(ACTION_EXPLORING).orElse("no_name")).isEqualTo("exploring");
        assertThat(getLabel(ACTION_FLYING).orElse("no_name")).isEqualTo("flying");
        assertThat(getLabel(ACTION_WAITING).orElse("no_name")).isEqualTo("waiting");
        assertThat(getLabel(ACTION_WALKING).orElse("no_name")).isEqualTo("walking");
        assertThat(getLabel(ACTION_WINING).orElse("no_name")).isEqualTo("wining");
    }
}
