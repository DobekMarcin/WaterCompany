package md.program.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.PartnerModel;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class PartnerTableAddStageController {

    private static final String FXML_ARCHIVE_INFO_STAGE_FXML = "/FXML/ArchiveInfoStage.fxml";
    @FXML
    public CheckBox meterCheckBox;
    @FXML
    private TextField postTextField;
    @FXML
    private TextField nipTextField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private Label errorLabel;
    private Stage stage;
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
    @FXML
    private CheckBox companyChceckBox;
    private PartnerModel partnerModel = new PartnerModel();

    public void init() {
        bindings();
        setListeners();
        try {
            partnerModel.setId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        peopleTextField.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
        idTextField.setEditable(false);
        nameTextField.requestFocus();
    }

    private void setListeners() {
        nameTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        surnameTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        addressTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        peopleTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        companyChceckBox.selectedProperty().addListener(observable -> errorLabel.setText(""));
        companyChceckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == true) peopleTextField.setText("0");
        });
        postTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        postCodeTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        nipTextField.textProperty().addListener(observable -> errorLabel.setText(""));
    }
    private void bindings() {

        idTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().idProperty(), new NumberStringConverter());
        idTextField.disableProperty().set(true);
        nameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().nameProperty());
        surnameTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().surnameProperty());
        addressTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().addressProperty());
        postCodeTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().postCodeProperty());
        postTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().postProperty());
        nipTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().nipProperty());
        nipTextField.disableProperty().set(true);
        peopleTextField.textProperty().bindBidirectional(partnerModel.getPartnerFX().peopleCountProperty(), new NumberStringConverter());
        archivesChceckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().archivesProperty());
        companyChceckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().companyProperty());
        peopleTextField.disableProperty().bindBidirectional(companyChceckBox.selectedProperty());
        meterCheckBox.selectedProperty().bindBidirectional(partnerModel.getPartnerFX().meterProperty());
        nameTextField.setText("");
        surnameTextField.setText("");
        addressTextField.setText("");
        postCodeTextField.setText("");
        postTextField.setText("");
        nipTextField.setText("");

        companyChceckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if(isNowSelected) nipTextField.disableProperty().set(false);
            if(!isNowSelected) {
                nipTextField.disableProperty().set(true);
                nipTextField.setText("");
            }
        });
    }

    @FXML
    public void addButtonOnAction() {
        try {
            Boolean validData = partnerModel.validData();
            if (!validData) {
                errorLabel.setText(Utils.getResourceBundle().getString("partner.add.error"));
            } else {
                partnerModel.addPartner();
                stage.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void infoArchiveOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ARCHIVE_INFO_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("default.archive.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        ArchiveInfoStageController archiveInfoStageController = fxmlLoader.getController();
        archiveInfoStageController.setThisStage(stage1);
        archiveInfoStageController.init();
        stage1.showAndWait();
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
