package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.database.repository.PaymentPlanRepository;
import md.program.modelFX.PaymentPlanModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class InitializePaymentPlanStageController {
    @FXML
    private CheckBox noCompany;
    @FXML
    private TextField yearTextField;
    @FXML
    private Label noLogLabel;
    private PaymentPlanModel paymentPlanModel = new PaymentPlanModel();
    private Stage thisStage = null;
    private PaymentPlanTableStageController paymentPlanTableStageController;

    public void initialize(){
        paymentPlanModel.noComapnyProperty().bindBidirectional(noCompany.selectedProperty());
    }
    public void generateButtonOnAction() {
        try {
            Integer year=Integer.valueOf(yearTextField.getText());
            paymentPlanModel.generatePaymentPlan(year);
            paymentPlanTableStageController.setInitializeYear(year);
            thisStage.close();
            DialogUtil.confirmationDialog("dialog.confirmation.title","dialog.confirmation.header","dialog.confirmation.generate");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public PaymentPlanTableStageController getPaymentPlanTableStageController() {
        return paymentPlanTableStageController;
    }

    public void setPaymentPlanTableStageController(PaymentPlanTableStageController paymentPlanTableStageController) {
        this.paymentPlanTableStageController = paymentPlanTableStageController;
    }
}
