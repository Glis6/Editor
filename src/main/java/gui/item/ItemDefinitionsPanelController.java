package gui.item;

import domain.DomainController;
import domain.item.EquipmentType;
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
import java.util.Objects;

/**
 * Created by Gilles on 30/05/2016.
 */
public class ItemDefinitionsPanelController extends AnchorPane {

    private final DomainController domainController;
    private final MainPaneController mainPaneController;
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
    private TextField lblShopValue;
    @FXML
    private TextField lblAlchValue;
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
    @FXML
    private Button btnDeleteItem;
    @FXML
    private TextField txtSearch;
    @FXML
    private Text lblSortType;
    @FXML
    private Button btnAddItem;
    private BonusPane bonusPane;
    private RequirementsPane requirementsPane;

    private SortedList<ItemDefinition> sortedList;

    private FilteredList<ItemDefinition> filteredList;

    public ItemDefinitionsPanelController(DomainController domainController, MainPaneController mainPaneController) {
        this.domainController = domainController;
        this.mainPaneController = mainPaneController;
        sortedList = new SortedList<>(domainController.getItemDefinitionsController().getDefinitions(), (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        filteredList = new FilteredList<>(sortedList);

        domainController.getItemDefinitionsController().addSelectedDefinitionListener((observable, oldValue, newValue) -> {
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

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue.equalsIgnoreCase(newValue))
                return;
            filteredList.setPredicate(itemDefinition -> itemDefinition.getName().toLowerCase().contains(newValue.toLowerCase()));
        });

        itemDefinitionsList.setItems(filteredList);

        choiceType.setItems(FXCollections.observableList(Arrays.asList(EquipmentType.values())));

        itemDefinitionsList.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue == oldValue)
                return;
            domainController.getItemDefinitionsController().setSelectedDefinition(newValue);
        });

        itemDefinitionsList.setCellFactory(lv -> new ListCell<ItemDefinition>() {
            public void updateItem(ItemDefinition itemDefinition, boolean empty) {
                super.updateItem(itemDefinition, empty);
                if (empty) {
                    setText(null);
                    return;
                }
                setText(itemDefinition.getName());
            }
        });

        itemDefinitionsList.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
            if(itemDefinitionsList.getSelectionModel().getSelectedItems().isEmpty()) {
                btnDeleteItem.setDisable(true);
                return;
            }
            btnDeleteItem.setDisable(false);
        });

        lblValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 9) {
                lblValue.setText(newValue.substring(0, 9));
                return;
            }
            if (Objects.equals(oldValue, newValue) || newValue.isEmpty() || !newValue.matches("\\d+")) {
                lblShopValue.setText("Not available");
                lblShopValue.setDisable(true);
                lblAlchValue.setText("Not available");
                lblAlchValue.setDisable(true);
                return;
            }
            int itemValue = Integer.parseInt(newValue);
            lblShopValue.setText(Integer.toString(((int) (itemValue * 0.2))));
            lblShopValue.setDisable(false);
            lblAlchValue.setText(Integer.toString(((int) (itemValue * 0.8))));
            lblAlchValue.setDisable(false);
        });
    }

    @FXML
    public void pullChanges(ActionEvent event) {
        try {
            if (!domainController.isConnected())
                mainPaneController.connect();
            if (domainController.pingMongo()) {
                domainController.getItemDefinitionsController().clearDefinitions();
                domainController.loadItemDefinitions(getDatabaseName(), getCollectionName());
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
    void reload(ActionEvent event) {
        domainController.getItemDefinitionsController().getSelectedDefinition().ifPresent(this::displayDefinition);
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if (lblName.getText().isEmpty()) {
            sendPrompt("Please fill in an item name");
            return;
        }
        if (lblDescription.getText().isEmpty()) {
            sendPrompt("Please fill in a description");
            return;
        }
        if (lblValue.getText().isEmpty()) {
            sendPrompt("Please fill in a value");
            return;
        }
        if (!lblValue.getText().matches("^\\d+$")) {
            sendPrompt("The value can only be a number");
            return;
        }
        if (bonusPane == null)
            return;
        for (TextField textField : bonusPane.getContents()) {
            if (textField.getText().isEmpty()) {
                sendPrompt("Not all bonusses are filled in");
                return;
            }
            if (!textField.getText().matches("[0-9]{1,13}(\\.[0-9]+)?")) {
                sendPrompt("Bonusses can only be doubles");
                return;
            }
        }
        if (requirementsPane == null)
            return;
        for (TextField textField : requirementsPane.getContents()) {
            if (textField.getText().isEmpty()) {
                sendPrompt("Not all requirements are filled in");
                return;
            }
            if (!textField.getText().matches("^\\d+$")) {
                sendPrompt("Requirements can only be numbers");
                return;
            }
        }
        ItemDefinition selectedDefinition = domainController.getItemDefinitionsController().getSelectedDefinition().get();
        ItemDefinition newDefinition = new ItemDefinition(
                selectedDefinition.getId(),
                lblName.getText(),
                lblDescription.getText(),
                stackable.isSelected(),
                Integer.parseInt(lblValue.getText()),
                noted.isSelected(),
                twoHanded.isSelected(),
                weapon.isSelected(),
                choiceType.getValue(),
                bonusPane.getBonusses(),
                requirementsPane.getRequirements()
        );
        domainController.getItemDefinitionsController().removeDefinition(selectedDefinition);
        domainController.getItemDefinitionsController().addDefinition(newDefinition);
        itemDefinitionsList.getSelectionModel().select(newDefinition);
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
        lblShopValue.setText(Integer.toString(((int) (itemDefinition.getValue() * 0.2))));
        lblShopValue.setDisable(false);
        lblAlchValue.setText(Integer.toString(((int) (itemDefinition.getValue() * 0.8))));
        lblAlchValue.setDisable(false);
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

    @FXML
    void addItem(ActionEvent event) {
        if (itemDefinitionsList.getItems().isEmpty()) {
            sendPrompt("You need to load in the items first");
            return;
        }
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Choose an item ID");
        dialog.setContentText("Item ID: ");

        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !newValue.matches("\\d+") || newValue.length() > 5 || domainController.getItemDefinitionsController().getDefinitions().stream().mapToInt(ItemDefinition::getId).anyMatch(i -> i == Integer.parseInt(newValue)))
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });

        dialog.showAndWait().ifPresent(result -> {
            int itemId = Integer.parseInt(result);
            if(domainController.getItemDefinitionsController().getDefinitions().stream().mapToInt(ItemDefinition::getId).anyMatch(i -> i == itemId))
                return;
            ItemDefinition itemDefinition = new ItemDefinition(itemId);
            domainController.getItemDefinitionsController().addDefinition(itemDefinition);
            itemDefinitionsList.getSelectionModel().select(itemDefinition);
        });
    }

    @FXML
    void deleteItem(ActionEvent event) {
        domainController.getItemDefinitionsController().getSelectedDefinition().ifPresent(itemDefinition -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this item?");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    domainController.getItemDefinitionsController().removeDefinition(itemDefinition);
                    itemDefinitionsList.getSelectionModel().selectFirst();
                }
            });
        });
    }

    @FXML
    void sortByName(ActionEvent event) {
        sortedList.setComparator((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
        lblSortType.setText("Name");
    }

    @FXML
    void sortById(ActionEvent event) {
        sortedList.setComparator((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        lblSortType.setText("ID");
    }
}
