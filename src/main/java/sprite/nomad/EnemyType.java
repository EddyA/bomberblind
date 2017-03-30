package sprite.nomad;

import java.util.Optional;

/**
 * Enum the different type of enemy.
 */
public enum EnemyType {
    TYPE_ENEMY_ZORA,
    TYPE_ENEMY_RED_SPEAR_SOLDIER;

    public static Optional<String> getlabel(EnemyType enemyType) {
        Optional<String> label = Optional.empty();
        switch (enemyType) {
            case TYPE_ENEMY_ZORA: {
                label = Optional.of("zora");
                break;
            }
            case TYPE_ENEMY_RED_SPEAR_SOLDIER: {
                label = Optional.of("red_spear_soldier");
                break;
            }
        }
        return label;
    }
}
