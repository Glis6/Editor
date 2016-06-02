package domain.controllers;

import domain.item.ItemDefinition;

import java.util.Optional;

/**
 * Created by Gilles on 30/05/2016.
 */
public class ItemDefinitionsController extends DefinitionsController<Integer, ItemDefinition> {
    @Override
    protected Integer getKeyForDefinition(ItemDefinition definition) {
        return definition.getId();
    }

    @Override
    public Optional<ItemDefinition> getDefinitionForKey(Integer searchKey) {
        return getDefinitions().parallelStream().filter(itemDefinition -> itemDefinition.getId() == searchKey).findFirst();
    }
}