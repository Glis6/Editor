package domain.controllers;

import domain.item.ItemDefinition;
import javafx.beans.property.SimpleObjectProperty;

import java.util.*;

/**
 * Created by Gilles on 30/05/2016.
 */
public class ItemDefinitionsController {
    private final Map<Integer, ItemDefinition> itemDefinitions = new HashMap<>();
    private final SimpleObjectProperty<ItemDefinition> selectedItemDefinition = new SimpleObjectProperty<>();
    private final Set<Integer> changedDefinitions = new HashSet<>();

    public Map<Integer, ItemDefinition> getItemDefinitions() {
        return itemDefinitions;
    }

    public ItemDefinition getSelectedItemDefinition() {
        return selectedItemDefinition.get();
    }

    public void setSelectedItemDefinition(ItemDefinition selectedItemDefinition) {
        this.selectedItemDefinition.set(selectedItemDefinition);
    }

    public SimpleObjectProperty<ItemDefinition> selectedItemDefinitionProperty() {
        return selectedItemDefinition;
    }

    public void addChangedDefinition(int itemId) {
        changedDefinitions.add(itemId);
    }
}
