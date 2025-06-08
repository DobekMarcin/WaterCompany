package md.program.controller.BKAccountPlan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.BKAccountPlanModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BKAccountPlanAddStageController {
    @FXML
    private CheckBox synCheck;
    @FXML
    private TextField accountNumber;
    @FXML
    private TextField accountDes;
    private Stage thisStage;
    private BKAccountPlanModel bkAccountPlanModel;


    public void init() {
        // bkAccountPlanModel.convertEdit();
        bkAccountPlanModel.resetAccountNew();
        accountNumber.textProperty().bindBidirectional(bkAccountPlanModel.getBkAccountNewFX().accountProperty());
        accountDes.textProperty().bindBidirectional(bkAccountPlanModel.getBkAccountNewFX().descriptionProperty());
        synCheck.selectedProperty().bindBidirectional(bkAccountPlanModel.getBkAccountNewFX().synProperty());

        synCheck.selectedProperty().set(false);
        accountNumber.setText("");
        accountDes.setText("");

    }

    public void addButtonOnAction() {
        try {

            if (accountNumber.textProperty().getValue().isEmpty() || accountDes.textProperty().getValue().isEmpty()) {
                DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.accountPlan.empty.data");
            } else {

                bkAccountPlanModel.addnew();
                thisStage.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public BKAccountPlanModel getBkAccountPlanModel() {
        return bkAccountPlanModel;
    }

    public void setBkAccountPlanModel(BKAccountPlanModel bkAccountPlanModel) {
        this.bkAccountPlanModel = bkAccountPlanModel;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
