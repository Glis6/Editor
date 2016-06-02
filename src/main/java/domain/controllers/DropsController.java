package domain.controllers;

import domain.drop.NpcDrop;
import domain.drop.NpcDropItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.util.Optional;

/**
 * Created by Gilles on 30/05/2016.
 */
public class DropsController extends DefinitionsController<String, NpcDrop> {

    private final SimpleObjectProperty<NpcDropItem> selectedDropItem = new SimpleObjectProperty<>();

    @Override
    protected String getKeyForDefinition(NpcDrop definition) {
        return definition.getNpcName();
    }

    @Override
    public Optional<NpcDrop> getDefinitionForKey(String searchKey) {
        return getDefinitions().parallelStream().filter(npcDrop -> npcDrop.getNpcName().equalsIgnoreCase(searchKey)).findFirst();
    }

    public Optional<NpcDropItem> getSelectedDropItem() {
        return Optional.of(selectedDropItem.get());
    }

    public void setSelectedDropItem(NpcDropItem selectedDefinition) {
        this.selectedDropItem.set(selectedDefinition);
    }

    public void addSelectedDropItemListener(ChangeListener<NpcDropItem> listener) {
        selectedDropItem.addListener(listener);
    }
}
