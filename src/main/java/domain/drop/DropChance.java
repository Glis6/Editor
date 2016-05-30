package domain.drop;

/**
 * Created by Gilles on 13/05/2016.
 */
public enum DropChance {
    ALWAYS(0),
    ALMOST_ALWAYS(2),
    VERY_COMMON(5),
    COMMON(15),
    UNCOMMON(40),
    NOTTHATRARE(100),
    RARE(155),
    LEGENDARY(320),
    LEGENDARY_2(410),
    LEGENDARY_3(485),
    LEGENDARY_4(680),
    LEGENDARY_5(900);

    private int random;

    DropChance(int randomModifier) {
        this.random = randomModifier;
    }

    public int getRandom() {
        return this.random;
    }
}