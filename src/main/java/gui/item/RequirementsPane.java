package gui.item;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Gilles on 30/05/2016.
 */
public class RequirementsPane extends AnchorPane {
    @FXML
    private TextField txtStrength;

    @FXML
    private TextField txtFiremaking;

    @FXML
    private TextField txtFletching;

    @FXML
    private TextField txtMining;

    @FXML
    private TextField txtThieving;

    @FXML
    private TextField txtAgility;

    @FXML
    private TextField txtHerblore;

    @FXML
    private TextField txtSlayer;

    @FXML
    private TextField txtFarming;

    @FXML
    private TextField txtRanged;

    @FXML
    private TextField txtPrayer;

    @FXML
    private TextField txtMagic;

    @FXML
    private TextField txtCrafting;

    @FXML
    private TextField txtConstitution;

    @FXML
    private TextField txtFishing;

    @FXML
    private TextField txtDefence;

    @FXML
    private TextField txtHunter;

    @FXML
    private TextField txtConstruction;

    @FXML
    private TextField txtDungeoneering;

    @FXML
    private TextField txtSmithing;

    @FXML
    private TextField txtCooking;

    @FXML
    private TextField txtWoodcutting;

    @FXML
    private TextField txtRunecrafting;

    @FXML
    private TextField txtSummoning;

    @FXML
    private TextField txtAttack;

    public RequirementsPane(int[] requirements) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RequirementsPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtAttack.setText(Integer.toString(requirements[0]));
        txtDefence.setText(Integer.toString(requirements[1]));
        txtStrength.setText(Integer.toString(requirements[2]));
        txtConstitution.setText(Integer.toString(requirements[3]));
        txtRanged.setText(Integer.toString(requirements[4]));
        txtPrayer.setText(Integer.toString(requirements[5]));
        txtMagic.setText(Integer.toString(requirements[6]));
        txtCooking.setText(Integer.toString(requirements[7]));
        txtWoodcutting.setText(Integer.toString(requirements[8]));
        txtFletching.setText(Integer.toString(requirements[9]));
        txtFishing.setText(Integer.toString(requirements[10]));
        txtFiremaking.setText(Integer.toString(requirements[11]));
        txtCrafting.setText(Integer.toString(requirements[12]));
        txtSmithing.setText(Integer.toString(requirements[13]));
        txtMining.setText(Integer.toString(requirements[14]));
        txtHerblore.setText(Integer.toString(requirements[15]));
        txtAgility.setText(Integer.toString(requirements[16]));
        txtThieving.setText(Integer.toString(requirements[17]));
        txtSlayer.setText(Integer.toString(requirements[18]));
        txtFarming.setText(Integer.toString(requirements[19]));
        txtRunecrafting.setText(Integer.toString(requirements[20]));
        txtHunter.setText(Integer.toString(requirements[21]));
        txtConstruction.setText(Integer.toString(requirements[22]));
        txtSummoning.setText(Integer.toString(requirements[23]));
        txtDungeoneering.setText(Integer.toString(requirements[24]));
    }

    public TextField[] getContents() {
        return new TextField[]{
                txtAttack, txtDefence, txtStrength, txtConstitution, txtRanged, txtPrayer, txtMagic, txtCooking, txtWoodcutting, txtFletching,
                txtFishing, txtFiremaking, txtCrafting, txtSmithing, txtMining, txtHerblore, txtAgility, txtThieving, txtSlayer, txtFarming,
                txtRunecrafting, txtHunter, txtConstruction, txtSummoning, txtDungeoneering
        };
    }

    public int[] getRequirements() {
        int[] requirements = new int[25];
        requirements[0] = Integer.parseInt(txtAttack.getText());
        requirements[1] = Integer.parseInt(txtDefence.getText());
        requirements[2] = Integer.parseInt(txtStrength.getText());
        requirements[3] = Integer.parseInt(txtConstitution.getText());
        requirements[4] = Integer.parseInt(txtRanged.getText());
        requirements[5] = Integer.parseInt(txtPrayer.getText());
        requirements[6] = Integer.parseInt(txtMagic.getText());
        requirements[7] = Integer.parseInt(txtCooking.getText());
        requirements[8] = Integer.parseInt(txtWoodcutting.getText());
        requirements[9] = Integer.parseInt(txtFletching.getText());
        requirements[10] = Integer.parseInt(txtFishing.getText());
        requirements[11] = Integer.parseInt(txtFiremaking.getText());
        requirements[12] = Integer.parseInt(txtCrafting.getText());
        requirements[13] = Integer.parseInt(txtSmithing.getText());
        requirements[14] = Integer.parseInt(txtMining.getText());
        requirements[15] = Integer.parseInt(txtHerblore.getText());
        requirements[16] = Integer.parseInt(txtAgility.getText());
        requirements[17] = Integer.parseInt(txtThieving.getText());
        requirements[18] = Integer.parseInt(txtSlayer.getText());
        requirements[19] = Integer.parseInt(txtFarming.getText());
        requirements[20] = Integer.parseInt(txtRunecrafting.getText());
        requirements[21] = Integer.parseInt(txtHunter.getText());
        requirements[22] = Integer.parseInt(txtConstruction.getText());
        requirements[23] = Integer.parseInt(txtSummoning.getText());
        requirements[24] = Integer.parseInt(txtDungeoneering.getText());
        return requirements;
    }
}
