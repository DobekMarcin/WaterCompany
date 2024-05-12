package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.PartnerModel;
import md.program.utils.Utils;

import java.sql.SQLException;

public class PartnerTableEditStageController {

    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField postTextField;
    @FXML
    private TextField nipTextField;
    @FXML
    private CheckBox meterCheckBox;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox companyChceckBox;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField peopleTextField;
    @FXML
    private CheckBox archivesChceckBox;
    private Stage stage;
    private PartnerModel partnerModel = new PartnerModel();

    public void init() {
        bindings();
        setListeners();
        idTextField.setEditable(false);

        nameTextField.requestFocus();
    }

    private void setListeners() {
        nameTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        surnameTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        addressTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        peopleTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        postCodeTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        postTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        nipTextField.textProperty().addListener(observable -> errorLabel.setText(""));
    }

    private void bindings() {
        idTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().idProperty(), new NumberStringConverter());
        nameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().nameProperty());
        surnameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().surnameProperty());
        addressTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().addressProperty());
        peopleTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().peopleCountProperty(), new NumberStringConverter());
        archivesChceckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().archivesProperty());
        companyChceckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().companyProperty());
        companyChceckBox.setDisable(true);
        meterCheckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().meterProperty());
        peopleTextField.setDisable(companyChceckBox.isSelected());
        postTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().postProperty());
        postCodeTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().postCodeProperty());
        nipTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().nipProperty());
        nipTextField.setDisable(true);
        idTextField.setDisable(true);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void editButtonOnAction() {
        try {
            Boolean validData = partnerModel.validData();
            if (!validData) {
                errorLabel.setText(Utils.getResourceBundle().getString("partner.add.error"));
            } else {
            partnerModel.saveUpdatePartner();
            stage.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        stage.close();
    }

    public PartnerModel getPartnerModel() {
        return partnerModel;
    }

    public void setPartnerModel(PartnerModel partnerModel) {
        this.partnerModel = partnerModel;
    }
}
