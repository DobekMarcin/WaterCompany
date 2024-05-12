package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.database.repository.PaymentPlanRepository;
import md.program.modelFX.PaymentPlanModel;

import java.sql.SQLException;

public class InitializePaymentPlanStageController {
    @FXML
    private TextField yearTextField;
    @FXML
    private Label noLogLabel;
    private PaymentPlanModel paymentPlanModel = new PaymentPlanModel();

    private Stage thisStage = null;
    private PaymentPlanTableStageController paymentPlanTableStageController;
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    public void generateButtonOnAction() {
        try {
            Integer year=Integer.valueOf(yearTextField.getText());
            paymentPlanRepository.deletePaymnetPlanByYear(year);
            paymentPlanModel.generatePaymentPlan(year);
            paymentPlanTableStageController.setInitializeYear(year);
            thisStage.close();
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
