package domain.drop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Gilles on 13/05/2016.
 */
public final class NpcDrop {
    private final String npcName;
    private final ObservableList<NpcDropItem> drops;

    public NpcDrop(String npcName) {
        this.npcName = npcName;
        this.drops = FXCollections.observableArrayList();
    }

    public NpcDrop(String npcName, NpcDropItem[] drops) {
        this.npcName = npcName;
        this.drops = FXCollections.observableArrayList(drops);
    }

    public String getNpcName() {
        return npcName;
    }

    public ObservableList<NpcDropItem> getDropList() {
        return drops;
    }

    @Override
    public String toString() {
        return npcName;
    }
}
