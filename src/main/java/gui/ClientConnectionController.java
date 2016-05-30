package gui;

/**
 * Created by Gilles on 30/05/2016.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.DomainController;
import exceptions.NoClientException;
import exceptions.NoConnectionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ClientConnectionController extends Pane {

    private final static File CONFIGURATION_FILE = new File("./configuration.json");

    @FXML
    private Text txtError;

    @FXML
    private TextField txtAuthDb;

    @FXML
    private PasswordField txtDbPassword;

    @FXML
    private TextField txtDbPort;

    @FXML
    private TextField txtDbUsername;

    @FXML
    private TextField txtDbAddress;

    @FXML
    private CheckBox rememberBtn;

    @FXML
    private Button connectButton;

    private final DomainController domainController;

    public ClientConnectionController(DomainController domainController) {
        this.domainController = domainController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientConnectionPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        if(CONFIGURATION_FILE.exists()) {
            try (FileReader fileReader = new FileReader(CONFIGURATION_FILE)) {
                JsonParser parser = new JsonParser();
                JsonObject object = (JsonObject) parser.parse(fileReader);
                if(object.has("db-address"))
                    txtDbAddress.setText(object.get("db-address").getAsString());
                if(object.has("db-port"))
                    txtDbPort.setText(object.get("db-port").getAsString());
                if(object.has("username"))
                    txtDbUsername.setText(object.get("username").getAsString());
                if(object.has("password"))
                    txtDbPassword.setText(object.get("password").getAsString());
                if(object.has("authentication-database"))
                    txtAuthDb.setText(object.get("authentication-database").getAsString());
                rememberBtn.setSelected(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void connect(ActionEvent event) {
        try {
            domainController.connectMongo(getDbAddress(), getDbPort(), getUsername(), txtDbPassword.getText(), getAuthenticationDatabase());
            if(rememberBtn.isSelected()) {
                if (!CONFIGURATION_FILE.getParentFile().exists()) {
                    if (!CONFIGURATION_FILE.getParentFile().mkdirs()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Unable to create file.");
                        alert.showAndWait();
                        return;
                    }
                }
                try (FileWriter writer = new FileWriter(CONFIGURATION_FILE)) {
                    Gson builder = new GsonBuilder().setPrettyPrinting().create();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("db-address", getDbAddress());
                    jsonObject.addProperty("db-port", getDbPort());
                    jsonObject.addProperty("username", getUsername());
                    jsonObject.addProperty("password", txtDbPassword.getText());
                    jsonObject.addProperty("authentication-database", getAuthenticationDatabase());
                    writer.write(builder.toJson(jsonObject));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Unable to save file.");
                    alert.showAndWait();
                }
            }
            txtError.setText("Successfully connected");
        } catch(NoClientException e) {
            txtError.setText("Could not connect to the client");
        } catch(NoConnectionException e) {
            txtError.setText("Could not connect to the database");
        } catch(IllegalArgumentException e) {
            txtError.setText(e.getMessage());
        } catch(Exception e) {
            txtError.setText("Invalid creditials");
        }
    }

    private String getDbAddress() throws IllegalArgumentException {
        if(txtDbAddress.getText().isEmpty())
            return "127.0.0.1";
        if(!txtDbAddress.getText().matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$"))
            throw new IllegalArgumentException("Invalid database address");
        return txtDbAddress.getText();
    }

    private String getUsername() {
        if(txtDbUsername.getText().isEmpty())
            return "admin";
        return txtDbUsername.getText();
    }

    private String getAuthenticationDatabase() {
        if(txtAuthDb.getText().isEmpty())
            return "admin";
        return txtAuthDb.getText();
    }

    private int getDbPort() throws IllegalArgumentException {
        if(txtDbPort.getText().isEmpty())
            return 27017;
        try {
            return Integer.valueOf(txtDbPort.getText());
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number");
        }
    }
}
