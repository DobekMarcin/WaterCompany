package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.PartnerModel;

import java.sql.SQLException;

public class PartnerTableAddStageController {
    private Stage stage;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label noLogLabel;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField peopleTextField;
    @FXML
    private CheckBox archivesChceckBox;

    private PartnerModel partnerModel = new PartnerModel();

    public void init() {
        bindings();
        try {
            partnerModel.setId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    @FXML
    public void addButtonOnAction() {
        try {
            partnerModel.addPartner();
            stage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
