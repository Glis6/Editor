package domain.controllers;

import domain.drop.NpcDrop;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gilles on 30/05/2016.
 */
public class DropsController {
    private final Map<String, NpcDrop> npcDrops = new HashMap<>();
    private final SimpleObjectProperty<NpcDrop> selectedDrop = new SimpleObjectProperty<>();
    private final Set<String> changedDrops = new HashSet<>();

    public Map<String, NpcDrop> getNpcDrops() {
        return npcDrops;
    }

    public NpcDrop getSelectedDrop() {
        return selectedDrop.get();
    }

    public SimpleObjectProperty<NpcDrop> selectedDropProperty() {
        return selectedDrop;
    }

    public void setSelectedDrop(NpcDrop selectedDrop) {
        this.selectedDrop.set(selectedDrop);
    }

    public void addChangedDrop(String monsterName) {
        changedDrops.add(monsterName);
    }
}
