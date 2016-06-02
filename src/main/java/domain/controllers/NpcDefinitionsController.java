package domain.controllers;

import domain.definition.NpcDefinition;

import java.util.Optional;

/**
 * Created by Gilles on 2/06/2016.
 */
public class NpcDefinitionsController extends DefinitionsController<Integer, NpcDefinition> {
    @Override
    protected Integer getKeyForDefinition(NpcDefinition definition) {
        return definition.getId();
    }

    @Override
    public Optional<NpcDefinition> getDefinitionForKey(Integer searchKey) {
        return getDefinitions().parallelStream().filter(npcDefinition -> npcDefinition.getId() == searchKey).findFirst();
    }
}
