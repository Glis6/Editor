package domain.drop;

import domain.item.Item;

/**
 * Created by Gilles on 13/05/2016.
 */
public class NpcDropItem {
    private final int id;
    private final int count;
    private final int chance;

    public NpcDropItem(int id, int count[], DropChance chance) {
        this.id = id;
        this.count = count[0];
        this.chance = chance.ordinal();
    }

    public NpcDropItem(int id, int count, DropChance chance) {
        this.id = id;
        this.count = count;
        this.chance = chance.ordinal();
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public DropChance getChance() {
        switch (chance) {
            case 1:
                return DropChance.ALMOST_ALWAYS; // 50% <-> 1/2
            case 2:
                return DropChance.VERY_COMMON; // 20% <-> 1/5
            case 3:
                return DropChance.COMMON; // 5% <-> 1/20
            case 4:
                return DropChance.UNCOMMON; // 2% <-> 1/50
            case 5:
                return DropChance.RARE; // 0.5% <-> 1/200
            case 6:
                return DropChance.LEGENDARY; // 0.2% <-> 1/500
            case 7:
                return DropChance.LEGENDARY_2;
            case 8:
                return DropChance.LEGENDARY_3;
            case 9:
                return DropChance.LEGENDARY_4;
            case 10:
                return DropChance.LEGENDARY_5;
            default:
                return DropChance.ALWAYS; // 100% <-> 1/1
        }
    }

    public Item getItem() {
        return new Item(id, count);
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}
