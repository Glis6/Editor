package gui.drop;

import domain.DomainController;
import domain.drop.DropChance;
import domain.drop.NpcDrop;
import domain.drop.NpcDropItem;
import domain.item.ItemDefinition;
import gui.MainPaneController;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Gilles on 30/05/2016.
 */
public class DropPanelController extends AnchorPane {

    private final DomainController domainController;
    private final MainPaneController mainPaneController;
    @FXML
    private TextField txtSearchForDrop;
    @FXML
    private Button btnEmulate;
    @FXML
    private ChoiceBox<DropChance> choiceRarity;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMonsterName;
    @FXML
    private ListView<NpcDrop> monsterList;
    @FXML
    private Button btnAddMonster;
    @FXML
    private Button btnSaveMonster;
    @FXML
    private Button btnReloadItem;
    @FXML
    private Button btnPull;
    @FXML
    private Button btnAddDrop;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtDbName;
    @FXML
    private TextField txtSearchItems;
    @FXML
    private Button btnDeleteDrop;
    @FXML
    private Button btnReloadDefinitions;
    @FXML
    private TextField txtItemId;
    @FXML
    private Text lblSortTypeItems;
    @FXML
    private ListView<NpcDropItem> dropList;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblMonsterName;
    @FXML
    private Button btnDeleteMonster;
    @FXML
    private TextField txtColName;
    @FXML
    private Button btnPush;
    @FXML
    private Button btnSaveItemChanges;
    private SortedList<NpcDrop> npcDropSortedList;

    private FilteredList<NpcDrop> npcDropFilteredList;

    private FilteredList<NpcDrop> npcDropFilteredListByItem;

    private SortedList<NpcDropItem> npcDropItemSortedList;

    private FilteredList<NpcDropItem> npcDropItemFilteredList;


    public DropPanelController(DomainController domainController, MainPaneController mainPaneController) {
        this.domainController = domainController;
        this.mainPaneController = mainPaneController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DropsPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        domainController.getDropsController().addSelectedDefinitionListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            if (domainController.getItemDefinitionsController().getDefinitions().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please pull the item definitions first");
                alert.showAndWait();
                return;
            }
            displayMonster(newValue);
            domainController.getDropsController().setSelectedDropItem(null);

            npcDropItemSortedList = new SortedList<>(newValue.getDropList(), npcDropItemSortedList.getComparator());
            npcDropItemFilteredList = new FilteredList<>(npcDropItemSortedList);
            dropList.setItems(npcDropItemFilteredList);
        });

        domainController.getDropsController().addSelectedDropItemListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            displayItem(newValue);
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equalsIgnoreCase(oldValue))
                return;
            npcDropFilteredList.setPredicate(npcDrop -> npcDrop.getNpcName().toLowerCase().contains(newValue.toLowerCase()));
        });

        txtSearchForDrop.textProperty().addListener((observable1, oldValue, newValue) -> {
            if (newValue.equalsIgnoreCase(oldValue))
                return;
            if(newValue.isEmpty()) {
                npcDropFilteredListByItem.setPredicate(null);
            } else if(newValue.matches("\\d+")) {
                npcDropFilteredListByItem.setPredicate(npcDrop -> npcDrop.getDropList()
                        .stream()
                        .mapToInt(NpcDropItem::getId)
                        .anyMatch(value -> value == Integer.parseInt(newValue)));
            } else {
                npcDropFilteredListByItem.setPredicate(npcDrop -> npcDrop.getDropList()
                        .stream()
                        .map(npcDropItem -> {
                            Optional<ItemDefinition> optional = domainController.getItemDefinitionsController().getDefinitionForKey(npcDropItem.getId());
                            if(!optional.isPresent())
                                return "";
                            return optional.get().getName();
                        })
                        .anyMatch(itemName -> itemName.equalsIgnoreCase(newValue)));
            }
        });

        txtSearchItems.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equalsIgnoreCase(oldValue))
                return;
            npcDropItemFilteredList.setPredicate(npcDropItem -> {
                Optional<ItemDefinition> optional = domainController.getItemDefinitionsController().getDefinitionForKey(npcDropItem.getId());
                return optional.isPresent() && optional.get().getName().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        npcDropItemSortedList = new SortedList<>(FXCollections.emptyObservableList());
        npcDropItemFilteredList = new FilteredList<>(npcDropItemSortedList);
        dropList.setItems(npcDropItemFilteredList);

        npcDropSortedList = new SortedList<>(domainController.getDropsController().getDefinitions(), (o1, o2) -> o1.getNpcName().compareToIgnoreCase(o2.getNpcName()));
        npcDropFilteredList = new FilteredList<>(npcDropSortedList);
        npcDropFilteredListByItem = new FilteredList<>(npcDropFilteredList);
        monsterList.setItems(npcDropFilteredListByItem);

        monsterList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            domainController.getDropsController().setSelectedDefinition(newValue);
        });

        dropList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            domainController.getDropsController().setSelectedDropItem(newValue);
        });

        monsterList.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
            if(monsterList.getSelectionModel().getSelectedItems().isEmpty()) {
                btnDeleteMonster.setDisable(true);
                btnAddDrop.setDisable(true);
                btnDeleteDrop.setDisable(true);
                return;
            }
            btnAddDrop.setDisable(false);
            btnDeleteDrop.setDisable(false);
            btnDeleteMonster.setDisable(false);
        });

        dropList.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
            if(dropList.getSelectionModel().getSelectedItems().isEmpty()) {
                btnDeleteDrop.setDisable(true);
                return;
            }
            btnDeleteDrop.setDisable(false);
        });

        dropList.setCellFactory(lv -> new ListCell<NpcDropItem>() {
            @Override
            protected void updateItem(NpcDropItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else if (item == null) {
                    setText(null);
                } else {
                    domainController.getItemDefinitionsController().getDefinitionForKey(item.getId()).ifPresent(itemDefinition -> setText(itemDefinition.getName()));
                }
            }
        });
        choiceRarity.setItems(FXCollections.observableArrayList(Arrays.asList(DropChance.values())));
    }

    @FXML
    void reloadItem(ActionEvent event) {
        domainController.getDropsController().getSelectedDropItem().ifPresent(this::displayItem);
    }

    @FXML
    void saveItemChanges(ActionEvent event) {

    }

    @FXML
    void addDrop(ActionEvent event) {
        domainController.getDropsController().getSelectedDefinition().ifPresent(npcDrop -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setTitle("Choose an item ID");
            dialog.setContentText("Item name: ");

            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty() || newValue.length() > 5 || !newValue.matches("\\d+") || !domainController.getItemDefinitionsController().getDefinitionForKey(Integer.parseInt(newValue)).isPresent())
                    dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            });

            dialog.showAndWait().ifPresent(result -> {
                int itemId = Integer.parseInt(result);
                if(!domainController.getItemDefinitionsController().getDefinitionForKey(itemId).isPresent())
                    return;
                NpcDropItem npcDropItem = new NpcDropItem(itemId, 1, DropChance.RARE);
                npcDrop.getDropList().add(npcDropItem);
                dropList.getSelectionModel().select(npcDropItem);
            });
        });
    }

    @FXML
    void deleteDrop(ActionEvent event) {
        domainController.getDropsController().getSelectedDropItem().ifPresent(npcDropItem ->
                domainController.getDropsController().getSelectedDefinition().ifPresent(npcDrop -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this drop?");

                    alert.showAndWait().ifPresent(result -> {
                        if (result == ButtonType.OK) {
                            npcDrop.getDropList().remove(npcDropItem);
                        }
                    });
                }));
    }

    @FXML
    void deleteMonster(ActionEvent event) {
        domainController.getDropsController().getSelectedDefinition().ifPresent(npcDrop -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this monster?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    domainController.getDropsController().removeDefinition(npcDrop);
                    monsterList.getSelectionModel().selectFirst();
                }
            });
        });
    }

    @FXML
    void addMonster(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Choose a monster name");
        dialog.setContentText("Monster name: ");

        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || newValue.length() > 30 || domainController.getDropsController().getDefinitionForKey(newValue).isPresent())
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });

        dialog.showAndWait().ifPresent(result -> {
            if(domainController.getDropsController().getDefinitionForKey(result).isPresent())
                return;
            NpcDrop npcDrop = new NpcDrop(result);
            domainController.getDropsController().addDefinition(npcDrop);
            monsterList.getSelectionModel().select(npcDrop);
        });
    }

    @FXML
    void saveMonsterChanges(ActionEvent event) {

    }

    @FXML
    void reloadDefinitions(ActionEvent event) {
        domainController.getDropsController().getSelectedDefinition().ifPresent(this::displayMonster);
    }

    @FXML
    void sortByNameItem(ActionEvent event) {
        npcDropItemSortedList.setComparator((o1, o2) -> {
            Optional<ItemDefinition> itemOneDefinition = domainController.getItemDefinitionsController().getDefinitionForKey(o1.getId());
            Optional<ItemDefinition> itemTwoDefinition = domainController.getItemDefinitionsController().getDefinitionForKey(o2.getId());
            if (!itemOneDefinition.isPresent() && !itemTwoDefinition.isPresent())
                return 0;
            if (!itemOneDefinition.isPresent() && itemTwoDefinition.isPresent())
                return -1;
            if (itemOneDefinition.isPresent() && !itemTwoDefinition.isPresent())
                return 1;
            return itemOneDefinition.get().getName().compareToIgnoreCase(itemTwoDefinition.get().getName());
        });
        lblSortTypeItems.setText("Name");
    }

    @FXML
    void sortByIdItem(ActionEvent event) {
        npcDropItemSortedList.setComparator((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        lblSortTypeItems.setText("ID");
    }

    @FXML
    void pullChanges(ActionEvent event) {
        try {
            if (!domainController.isConnected())
                mainPaneController.connect();
            if (domainController.pingMongo()) {
                domainController.getDropsController().clearDefinitions();
                domainController.loadNpcDrops(getDatabaseName(), getCollectionName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not connect the the database!");
            alert.showAndWait();
        }
    }

    @FXML
    void pushChanges(ActionEvent event) {

    }

    @FXML
    void emulate(ActionEvent event) {

    }

    private void displayMonster(NpcDrop npcDrop) {
        lblMonsterName.setText(npcDrop.getNpcName());
        txtMonsterName.setDisable(false);
        txtMonsterName.setText(npcDrop.getNpcName());
        btnSaveMonster.setDisable(false);
        btnReloadDefinitions.setDisable(false);
        btnEmulate.setDisable(false);
        resetDisplayItem();
    }

    private void resetDisplayItem() {
        txtName.setDisable(true);
        txtName.setText("");
        txtItemId.setDisable(true);
        txtItemId.setText("");
        txtAmount.setDisable(true);
        txtAmount.setText("");
        choiceRarity.setDisable(true);
        choiceRarity.setValue(null);
        btnSaveItemChanges.setDisable(true);
        btnReloadItem.setDisable(true);
    }

    private void displayItem(NpcDropItem npcDropItem) {
        ItemDefinition itemDefinition = domainController.getItemDefinitionsController().getDefinitionForKey(npcDropItem.getId()).get();
        if (itemDefinition != null) {
            txtName.setDisable(false);
            txtName.setText(itemDefinition.getName());
        } else {
            txtName.setDisable(true);
            txtName.setText("");
        }
        txtItemId.setDisable(false);
        txtItemId.setText(Integer.toString(npcDropItem.getId()));
        txtAmount.setDisable(false);
        txtAmount.setText(Integer.toString(npcDropItem.getCount()));
        choiceRarity.setDisable(false);
        choiceRarity.setValue(npcDropItem.getChance());
        btnSaveItemChanges.setDisable(false);
        btnReloadItem.setDisable(false);
    }

    private String getDatabaseName() {
        if (txtDbName.getText().isEmpty())
            return "Definitions";
        return txtDbName.getText();
    }

    private String getCollectionName() {
        if (txtColName.getText().isEmpty())
            return "npc-drops";
        return txtColName.getText();
    }
}