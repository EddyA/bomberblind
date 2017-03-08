package sprite.settled;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static sprite.settled.BonusType.*;

public class BonusTypeTest implements WithAssertions {

    @Test
    public void getlabelShouldReturnTheAppropriateLabel() throws Exception {
        assertThat(getlabel(TYPE_BONUS_BOMB).orElse("no_name")).isEqualTo("bonus_bomb");
        assertThat(getlabel(TYPE_BONUS_FLAME).orElse("no_name")).isEqualTo("bonus_flame");
        assertThat(getlabel(TYPE_BONUS_HEART).orElse("no_name")).isEqualTo("bonus_heart");
        assertThat(getlabel(TYPE_BONUS_ROLLER).orElse("no_name")).isEqualTo("bonus_roller");
    }
}