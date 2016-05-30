package gui.item;

import domain.DomainController;
import domain.item.EquipmentType;
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
public class ItemDefinitionsPanelController extends AnchorPane {


    @FXML
    private ScrollPane bonusses;

    @FXML
    private CheckBox stackable;

    @FXML
    private ScrollPane requirements;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnPull;

    @FXML
    private TextField txtDbName;

    @FXML
    private TextArea lblDescription;

    @FXML
    private TextField lblValue;

    @FXML
    private CheckBox noted;

    @FXML
    private ChoiceBox<EquipmentType> choiceType;

    @FXML
    private Button btnSaveChanges;

    @FXML
    private ListView<ItemDefinition> itemDefinitionsList;

    @FXML
    private CheckBox weapon;

    @FXML
    private Label lblItemId;

    @FXML
    private TextField lblName;

    @FXML
    private CheckBox twoHanded;

    @FXML
    private TextField txtColName;

    @FXML
    private Button btnPush;

    private final DomainController domainController;
    private final MainPaneController mainPaneController;
    private BonusPane bonusPane;
    private RequirementsPane requirementsPane;

    public ItemDefinitionsPanelController(DomainController domainController, MainPaneController mainPaneController) {
        this.domainController = domainController;
        this.mainPaneController = mainPaneController;

        domainController.getItemDefinitionsController().selectedItemDefinitionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            displayDefinition(newValue);
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ItemDefinitionsPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        choiceType.setItems(FXCollections.observableList(Arrays.asList(EquipmentType.values())));

        itemDefinitionsList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            domainController.getItemDefinitionsController().setSelectedItemDefinition(newValue);
        });
    }

    @FXML
    void undoAll(ActionEvent event) {

    }

    @FXML
    void undoCurrent(ActionEvent event) {

    }

    @FXML
    public void pullChanges(ActionEvent event) {
        try {
            if(!domainController.isConnected())
                mainPaneController.connect();
            if (domainController.pingMongo()) {
                domainController.loadItemDefinitions(getDatabaseName(), getCollectionName());
            }
            itemDefinitionsList.setItems(new SortedList<>(FXCollections.observableArrayList(domainController.getItemDefinitionsController().getItemDefinitions().values()), (o1, o2) -> Integer.compare(o1.getId(), o2.getId())));
        } catch (Exception e) {
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
    void reload(ActionEvent event) {
        displayDefinition(domainController.getItemDefinitionsController().getSelectedItemDefinition());
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if(lblName.getText().isEmpty()) {
            sendPrompt("Please fill in an item name");
            return;
        }
        if(lblDescription.getText().isEmpty()) {
            sendPrompt("Please fill in a description");
            return;
        }
        if(lblValue.getText().isEmpty()) {
            sendPrompt("Please fill in a value");
            return;
        }
        if(!lblValue.getText().matches("^\\d+$")) {
            sendPrompt("The value can only be a number");
            return;
        }
        if(bonusPane == null)
            return;
        for (TextField textField : bonusPane.getContents()) {
            if(textField.getText().isEmpty()) {
                sendPrompt("Not all bonusses are filled in");
                return;
            }
            if(!textField.getText().matches("[0-9]{1,13}(\\.[0-9]+)?")) {
                sendPrompt("Bonusses can only be doubles");
                return;
            }
        }
        if(requirementsPane == null)
            return;
        for (TextField textField : requirementsPane.getContents()) {
            if(textField.getText().isEmpty()) {
                sendPrompt("Not all requirements are filled in");
                return;
            }
            if(!textField.getText().matches("^\\d+$")) {
                sendPrompt("Requirements can only be numbers");
                return;
            }
        }
        ItemDefinition itemDefinition = domainController.getItemDefinitionsController().getSelectedItemDefinition();
        itemDefinition.setName(lblName.getText());
        itemDefinition.setDescription(lblDescription.getText());
        itemDefinition.setEquipmentType(choiceType.getValue());
        itemDefinition.setNoted(noted.isSelected());
        itemDefinition.setStackable(stackable.isSelected());
        itemDefinition.setTwoHanded(twoHanded.isSelected());
        itemDefinition.setWeapon(weapon.isSelected());
        itemDefinition.setValue(Integer.parseInt(lblValue.getText()));
        itemDefinition.setBonus(bonusPane.getBonusses());
        itemDefinition.setRequirement(requirementsPane.getRequirements());
        domainController.getItemDefinitionsController().addChangedDefinition(itemDefinition.getId());
    }

    private void sendPrompt(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);

        alert.showAndWait();
    }

    private String getDatabaseName() {
        if (txtDbName.getText().isEmpty())
            return "Definitions";
        return txtDbName.getText();
    }

    private String getCollectionName() {
        if (txtColName.getText().isEmpty())
            return "item-definitions";
        return txtColName.getText();
    }

    private void displayDefinition(ItemDefinition itemDefinition) {
        lblItemId.setText(Integer.toString(itemDefinition.getId()));
        fillTextField(lblName, itemDefinition.getName());
        lblDescription.setText(itemDefinition.getDescription());
        lblDescription.setDisable(false);
        lblDescription.setEditable(true);
        choiceType.setValue(itemDefinition.getEquipmentType());
        choiceType.setDisable(false);
        fillTextField(lblValue, Integer.toString(itemDefinition.getValue()));
        stackable.setDisable(false);
        stackable.setSelected(itemDefinition.isStackable());
        noted.setDisable(false);
        noted.setSelected(itemDefinition.isNoted());
        twoHanded.setDisable(false);
        twoHanded.setSelected(itemDefinition.isTwoHanded());
        weapon.setDisable(false);
        weapon.setSelected(itemDefinition.isWeapon());
        requirements.setDisable(false);
        requirementsPane = new RequirementsPane(itemDefinition.getRequirement());
        requirements.setContent(requirementsPane);
        bonusses.setDisable(false);
        bonusPane = new BonusPane(itemDefinition.getBonus());
        bonusses.setContent(bonusPane);
        btnSaveChanges.setDisable(false);
        btnReload.setDisable(false);
    }

    private void fillTextField(TextField textField, String text) {
        textField.setText(text);
        textField.setDisable(false);
        textField.setEditable(true);
    }
}
