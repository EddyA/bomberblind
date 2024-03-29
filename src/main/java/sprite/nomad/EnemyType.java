package sprite.nomad;

import java.util.Optional;

/**
 * Enum the different type of enemy.
 */
public enum EnemyType {

    TYPE_ENEMY_GREEN_SOLDIER,
    TYPE_ENEMY_RED_SPEAR_SOLDIER,
    TYPE_ENEMY_ZORA;

    public static Optional<String> getLabel(EnemyType enemyType) {
        Optional<String> label = Optional.empty();
        switch (enemyType) {
            case TYPE_ENEMY_GREEN_SOLDIER -> label = Optional.of("green_soldier");
            case TYPE_ENEMY_RED_SPEAR_SOLDIER -> label = Optional.of("red_spear_soldier");
            case TYPE_ENEMY_ZORA -> label = Optional.of("zora");

        }
        return label;
    }
}
