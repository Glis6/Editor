package domain.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Created by Gilles on 2/06/2016.
 */
public abstract class DefinitionsController<K, T> {
    private final ObservableList<T> definitions = FXCollections.observableArrayList();
    private final Set<K> startList = new HashSet<>();
    private final Set<K> changeList = new HashSet<>();
    private final SimpleObjectProperty<T> selectedDefinition = new SimpleObjectProperty<>();

    public ObservableList<T> getDefinitions() {
        return FXCollections.unmodifiableObservableList(definitions);
    }

    public Optional<T> getSelectedDefinition() {
        return Optional.of(selectedDefinition.get());
    }

    public void setSelectedDefinition(T selectedDefinition) {
        this.selectedDefinition.set(selectedDefinition);
    }

    public void addSelectedDefinitionListener(ChangeListener<T> listener) {
        selectedDefinition.addListener(listener);
    }

    public void addDefinition(T definition) {
        addDefinition(definition, true);
    }

    public void removeDefinition(T definition) {
        removeDefinition(definition, true);
    }

    public void clearDefinitions() {
        definitions.clear();
        startList.clear();
        changeList.clear();
    }

    public void addDefinition(T definition, boolean isChanged) {
        definitions.add(definition);
        K key = getKeyForDefinition(definition);
        if(isChanged) {
            changeList.add(key);
        } else {
            startList.add(key);
        }
    }

    public void removeDefinition(T definition, boolean isChanged) {
        definitions.remove(definition);
        K key = getKeyForDefinition(definition);
        if(isChanged) {
            if(!startList.contains(key))
                changeList.remove(key);
        } else {
            startList.remove(key);
        }
    }

    public Set<K> getChangedDefinitions() {
        return changeList;
    }

    public abstract Optional<T> getDefinitionForKey(K searchKey);

    protected abstract K getKeyForDefinition(T definition);
}
