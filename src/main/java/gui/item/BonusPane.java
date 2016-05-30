package gui.item;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Gilles on 30/05/2016.
 */
public class BonusPane extends AnchorPane {


    @FXML
    private TextField txtDefSlash;

    @FXML
    private TextField txtMagicDmg;

    @FXML
    private TextField txtDeflectMagic;

    @FXML
    private TextField txtAttStab;

    @FXML
    private TextField txtAttSlash;

    @FXML
    private TextField txtDeflectRanged;

    @FXML
    private TextField txtAttCrush;

    @FXML
    private TextField txtDefRanged;

    @FXML
    private TextField txtBonusStr;

    @FXML
    private TextField txtDefCrush;

    @FXML
    private TextField txtAttMagic;

    @FXML
    private TextField txtAttRanged;

    @FXML
    private TextField txtDefMagic;

    @FXML
    private TextField txtDeflectMelee;

    @FXML
    private TextField txtBonusPray;

    @FXML
    private TextField txtDefSum;

    @FXML
    private TextField txtRangedStr;

    @FXML
    private TextField txtDefStab;

    public BonusPane(double[] bonusses) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BonussesPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtAttStab.setText(Double.toString(bonusses[0]));
        txtAttSlash.setText(Double.toString(bonusses[1]));
        txtAttCrush.setText(Double.toString(bonusses[2]));
        txtAttMagic.setText(Double.toString(bonusses[3]));
        txtAttRanged.setText(Double.toString(bonusses[4]));
        txtDefStab.setText(Double.toString(bonusses[5]));
        txtDefSlash.setText(Double.toString(bonusses[6]));
        txtDefCrush.setText(Double.toString(bonusses[7]));
        txtDefMagic.setText(Double.toString(bonusses[8]));
        txtDefRanged.setText(Double.toString(bonusses[9]));
        txtDefSum.setText(Double.toString(bonusses[10]));
        txtDeflectMelee.setText(Double.toString(bonusses[11]));
        txtDeflectMagic.setText(Double.toString(bonusses[12]));
        txtDeflectRanged.setText(Double.toString(bonusses[13]));
        txtBonusStr.setText(Double.toString(bonusses[14]));
        txtRangedStr.setText(Double.toString(bonusses[15]));
        txtBonusPray.setText(Double.toString(bonusses[16]));
        txtMagicDmg.setText(Double.toString(bonusses[17]));
    }

    public TextField[] getContents() {
        return new TextField[]{txtAttStab, txtAttSlash, txtAttCrush, txtAttMagic, txtAttRanged, txtDefStab, txtDefSlash, txtDefCrush, txtDefMagic, txtDefRanged, txtDefSum, txtDeflectMelee, txtDeflectRanged, txtDeflectMagic, txtBonusStr, txtBonusPray, txtRangedStr, txtMagicDmg};
    }

    public double[] getBonusses() {
        double[] bonus = new double[18];
        bonus[0] = Double.parseDouble(txtAttStab.getText());
        bonus[1] = Double.parseDouble(txtAttSlash.getText());
        bonus[2] = Double.parseDouble(txtAttCrush.getText());
        bonus[3] = Double.parseDouble(txtAttMagic.getText());
        bonus[4] = Double.parseDouble(txtAttRanged.getText());
        bonus[5] = Double.parseDouble(txtDefStab.getText());
        bonus[6] = Double.parseDouble(txtDefSlash.getText());
        bonus[7] = Double.parseDouble(txtDefCrush.getText());
        bonus[8] = Double.parseDouble(txtDefMagic.getText());
        bonus[9] = Double.parseDouble(txtDefRanged.getText());
        bonus[10] = Double.parseDouble(txtDefSum.getText());
        bonus[11] = Double.parseDouble(txtDeflectMelee.getText());
        bonus[12] = Double.parseDouble(txtDeflectMagic.getText());
        bonus[13] = Double.parseDouble(txtDeflectRanged.getText());
        bonus[14] = Double.parseDouble(txtBonusStr.getText());
        bonus[15] = Double.parseDouble(txtRangedStr.getText());
        bonus[16] = Double.parseDouble(txtBonusPray.getText());
        bonus[17] = Double.parseDouble(txtMagicDmg.getText());
        return bonus;
    }
}
