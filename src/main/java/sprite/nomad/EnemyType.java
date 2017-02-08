package sprite.nomad;

import java.util.Optional;

/**
 * Enum the different type of enemy.
 */
public enum EnemyType {
    CLOAKED_SKELETON, MECA_ANGEL, MINOTOR, MUMMY;

    public static Optional<String> getlabel(EnemyType enemyType) {
        Optional<String> label = Optional.empty();
        switch (enemyType) {
        case CLOAKED_SKELETON: {
            label = Optional.of("cloaked_skeleton");
            break;
        }
        case MECA_ANGEL: {
            label = Optional.of("meca_angel");
            break;
        }
        case MINOTOR: {
            label = Optional.of("minotor");
            break;
        }
        case MUMMY: {
            label = Optional.of("mummy");
            break;
        }
        }
        return label;
    }
}
