package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.PartnerModel;

import java.sql.SQLException;

public class PartnerTableEditStageController {

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
        idTextField.setEditable(false);
        nameTextField.requestFocus();
    }

    private void bindings() {
        idTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().idProperty(), new NumberStringConverter());
        nameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().nameProperty());
        surnameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().surnameProperty());
        addressTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().addressProperty());
        peopleTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().peopleCountProperty(), new NumberStringConverter());
        archivesChceckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().archivesProperty());
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
            partnerModel.saveUpdatePartner();
            stage.close();
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
