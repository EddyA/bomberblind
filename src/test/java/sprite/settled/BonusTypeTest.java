package sprite.settled;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import static sprite.settled.BonusType.*;

class BonusTypeTest implements WithAssertions {

    @Test
    void getLabelShouldReturnTheAppropriateLabel() {
        assertThat(getLabel(TYPE_BONUS_BOMB).orElse("no_name")).isEqualTo("bonus_bomb");
        assertThat(getLabel(TYPE_BONUS_FLAME).orElse("no_name")).isEqualTo("bonus_flame");
        assertThat(getLabel(TYPE_BONUS_HEART).orElse("no_name")).isEqualTo("bonus_heart");
        assertThat(getLabel(TYPE_BONUS_ROLLER).orElse("no_name")).isEqualTo("bonus_roller");
    }
}
