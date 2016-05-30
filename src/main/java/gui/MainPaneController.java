package gui;

import domain.DomainController;
import gui.drop.DropPanelController;
import gui.item.ItemDefinitionsPanelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by Gilles on 30/05/2016.
 */
public class MainPaneController extends TabPane {

    @FXML
    private Tab itemDefinitionsTab;

    @FXML
    private Tab dropsTab;

    @FXML
    private Tab settingsTab;

    @FXML
    private Tab npcDefinitionsTab;

    private final DomainController domainController = new DomainController();
    private final ClientConnectionController connectionSettingsController = new ClientConnectionController(domainController);
    private final ItemDefinitionsPanelController itemDefinitionsPanelController = new ItemDefinitionsPanelController(domainController, this);
    private final DropPanelController dropPanelController = new DropPanelController(domainController, this);

    public MainPaneController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        settingsTab.setContent(connectionSettingsController);
        itemDefinitionsTab.setContent(itemDefinitionsPanelController);
        dropsTab.setContent(dropPanelController);
    }

    public void connect() {
        connectionSettingsController.connect(null);
    }
}
