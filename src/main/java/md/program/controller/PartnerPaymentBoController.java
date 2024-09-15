package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import md.program.modelFX.PartnerBOListModel;

public class PartnerPaymentBoController {
    @FXML
    private Button initializeButton;
    @FXML
    private TableView partnerBOTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn boValueColumn;
    private Stage thisStage;

    private PartnerBOListModel partnerBOListModel = new PartnerBOListModel();

    public void init(){

initializeButton.setDisable();
    }

    public void editBOOnAction() {
    }

    public void closeButtonOnAction() {
    }

    public void closeBOButtonOnAction() {
    }

    public void setThisStage(Stage stage1) {
        thisStage=stage1;
    }

    public void initializeBOOnAction() {
    }
}
