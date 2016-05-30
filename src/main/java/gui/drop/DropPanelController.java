package gui.drop;

import domain.DomainController;
import domain.drop.DropChance;
import domain.drop.NpcDrop;
import domain.drop.NpcDropItem;
import domain.item.ItemDefinition;
import gui.MainPaneController;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

/**
 * Created by Gilles on 30/05/2016.
 */
public class DropPanelController extends AnchorPane {

    private final DomainController domainController;
    private final MainPaneController mainPaneController;
    @FXML
    private Button btnReload;
    @FXML
    private ChoiceBox<DropChance> choiceRarity;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMonsterName;
    @FXML
    private Button btnAddMonster;
    @FXML
    private Button btnAddDrop;
    @FXML
    private TextField txtAmount;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDeleteDrop;
    @FXML
    private TextField txtItemId;
    @FXML
    private Button btnDeleteMonster;
    @FXML
    private Button btnPull;
    @FXML
    private TextField txtDbName;
    @FXML
    private Label lblMonsterName;
    @FXML
    private ListView<NpcDrop> monsterList;
    @FXML
    private ListView<NpcDropItem> dropList;
    @FXML
    private TextField txtColName;
    @FXML
    private Button btnPush;

    public DropPanelController(DomainController domainController, MainPaneController mainPaneController) {
        this.domainController = domainController;
        this.mainPaneController = mainPaneController;

        domainController.getDropsController().selectedDropProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            if(domainController.getItemDefinitionsController().getItemDefinitions().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please pull the item definitions first");
                alert.showAndWait();
                return;
            }
            displayMonster(newValue);
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DropsPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        monsterList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            domainController.getDropsController().setSelectedDrop(newValue);
        });

        dropList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            displayItem(newValue);
        });

        dropList.setCellFactory(lv -> new ListCell<NpcDropItem>() {
            @Override
            protected void updateItem(NpcDropItem item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                } else if(item == null) {
                    setText(null);
                } else if(domainController.getItemDefinitionsController().getItemDefinitions().get(item.getId()) == null) {
                    setText(Integer.toString(item.getId()));
                } else {
                    setText(domainController.getItemDefinitionsController().getItemDefinitions().get(item.getId()).getName());
                }
            }
        });
        choiceRarity.setItems(FXCollections.observableArrayList(Arrays.asList(DropChance.values())));
    }

    @FXML
    void reload(ActionEvent event) {
        displayMonster(monsterList.getSelectionModel().getSelectedItem());
        displayItem(dropList.getSelectionModel().getSelectedItem());
    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

    @FXML
    void addDrop(ActionEvent event) {

    }

    @FXML
    void deleteDrop(ActionEvent event) {

    }

    @FXML
    void deleteMonster(ActionEvent event) {

    }

    @FXML
    void addMonster(ActionEvent event) {

    }

    @FXML
    void pullChanges(ActionEvent event) {
        try {
            if(!domainController.isConnected())
                mainPaneController.connect();
            if (domainController.pingMongo()) {
                domainController.loadNpcDrops(getDatabaseName(), getCollectionName());
            }
            monsterList.setItems(new SortedList<>(FXCollections.observableArrayList(domainController.getDropsController().getNpcDrops().values()), (o1, o2) -> o1.getNpcName().compareTo(o2.getNpcName())));
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

    private void displayMonster(NpcDrop npcDrop) {
        lblMonsterName.setText(npcDrop.getNpcName());
        dropList.setItems(FXCollections.observableList(Arrays.asList(npcDrop.getDropList())));
        txtMonsterName.setDisable(false);
        txtMonsterName.setText(npcDrop.getNpcName());
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
        btnSaveChanges.setDisable(true);
        btnReload.setDisable(true);
    }

    private void displayItem(NpcDropItem npcDropItem) {
        ItemDefinition itemDefinition = domainController.getItemDefinitionsController().getItemDefinitions().get(npcDropItem.getId());
        if(itemDefinition != null) {
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
        btnSaveChanges.setDisable(false);
        btnReload.setDisable(false);
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
