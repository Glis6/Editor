package domain.drop;

/**
 * Created by Gilles on 13/05/2016.
 */
public final class NpcDrop {
    private final String npcName;
    private final NpcDropItem[] drops;

    public NpcDrop(String npcName, NpcDropItem[] drops) {
        this.npcName = npcName;
        this.drops = drops;
    }

    public String getNpcName() {
        return npcName;
    }

    public NpcDropItem[] getDropList() {
        return drops;
    }

    @Override
    public String toString() {
        return npcName;
    }
}
