package sprite.nomad;

import java.util.Optional;

/**
 * Enum the different type of enemy.
 */
public enum EnemyType {
    TYPE_ENEMY_CLOAKED_SKELETON,
    TYPE_ENEMY_MECA_ANGEL,
    TYPE_ENEMY_MINOTOR,
    TYPE_ENEMY_MUMMY;

    public static Optional<String> getlabel(EnemyType enemyType) {
        Optional<String> label = Optional.empty();
        switch (enemyType) {
            case TYPE_ENEMY_CLOAKED_SKELETON: {
                label = Optional.of("cloaked_skeleton");
                break;
            }
            case TYPE_ENEMY_MECA_ANGEL: {
                label = Optional.of("meca_angel");
                break;
            }
            case TYPE_ENEMY_MINOTOR: {
                label = Optional.of("minotor");
                break;
            }
            case TYPE_ENEMY_MUMMY: {
                label = Optional.of("mummy");
                break;
            }
        }
        return label;
    }
}
