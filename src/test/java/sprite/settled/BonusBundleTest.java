package sprite.settled;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Map;

public class BonusBundleTest implements WithAssertions {

    @Test
    public void constructorShouldSetMemberWithDefaultValues() throws Exception {

        // set test.
        BonusBundle bonusBundle = new BonusBundle();

        // check.
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_BOMB)).isEqualTo(BonusBundle.DEFAULT_NB_BONUS_BOMB);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_FLAME)).isEqualTo(BonusBundle.DEFAULT_NB_BONUS_FLAME);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_HEART)).isEqualTo(BonusBundle.DEFAULT_NB_BONUS_HEART);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_ROLLER)).isEqualTo(BonusBundle.DEFAULT_NB_BONUS_ROLLER);
    }

    @Test
    public void getBonusWithAnUnpresentBonusShouldReturnZero() throws Exception {

        // set test.
        BonusBundle bonusBundle = new BonusBundle();
        bonusBundle.getBonusBundle().remove(BonusType.TYPE_BONUS_BOMB); // remove the bomb bonus.

        // check.
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_BOMB)).isEqualTo(0);
    }

    @Test
    public void setBonusShouldSetTheRelativeBonus() throws Exception {

        // set test.
        BonusBundle bonusBundle = new BonusBundle();
        bonusBundle.setBonus(BonusType.TYPE_BONUS_BOMB, 12);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_BOMB)).isEqualTo(12);
        bonusBundle.setBonus(BonusType.TYPE_BONUS_FLAME, 24);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_FLAME)).isEqualTo(24);
        bonusBundle.setBonus(BonusType.TYPE_BONUS_HEART, 36);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_HEART)).isEqualTo(36);
        bonusBundle.getBonusBundle().remove(BonusType.TYPE_BONUS_ROLLER); // remove the roller bonus.
        bonusBundle.setBonus(BonusType.TYPE_BONUS_ROLLER, 48); // add & set.

        // check.
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_ROLLER)).isEqualTo(48);
    }

    @Test
    public void getCollectedBonusShouldReturnAllCollectedBonusExceptHeartOne() throws Exception {

        // set test.
        BonusBundle bonusBundle = new BonusBundle();
        bonusBundle.setBonus(BonusType.TYPE_BONUS_BOMB, 12); // +11.
        bonusBundle.setBonus(BonusType.TYPE_BONUS_FLAME, 24); // +23.
        bonusBundle.setBonus(BonusType.TYPE_BONUS_HEART, 36);
        bonusBundle.getBonusBundle().remove(BonusType.TYPE_BONUS_ROLLER); // remove the roller bonus.

        // check.
        for (Map.Entry<BonusType, Integer> bonusEntry: bonusBundle.getCollectedBonus().entrySet()) {
            switch (bonusEntry.getKey()) {
                case TYPE_BONUS_BOMB: {
                    assertThat(bonusEntry.getValue()).isEqualTo(11);
                    break;
                }
                case TYPE_BONUS_FLAME: {
                    assertThat(bonusEntry.getValue()).isEqualTo(23);
                    break;
                }
                default:
                    fail("error: should not happen.");
            }
        }
    }

    @Test
    public void resetBonusShouldResultBonusExceptHeartOne() throws Exception {

        // set test.
        BonusBundle bonusBundle = new BonusBundle();
        bonusBundle.setBonus(BonusType.TYPE_BONUS_BOMB, 12);
        bonusBundle.setBonus(BonusType.TYPE_BONUS_FLAME, 24);
        bonusBundle.setBonus(BonusType.TYPE_BONUS_HEART, 36);
        bonusBundle.setBonus(BonusType.TYPE_BONUS_ROLLER, 48);

        // check.
        bonusBundle.resetBonus();
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_BOMB)).isEqualTo(1);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_FLAME)).isEqualTo(1);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_HEART)).isEqualTo(36);
        assertThat(bonusBundle.getBonus(BonusType.TYPE_BONUS_ROLLER)).isEqualTo(1);
    }
}