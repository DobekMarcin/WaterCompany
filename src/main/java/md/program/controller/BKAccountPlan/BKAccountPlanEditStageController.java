package md.program.controller.BKAccountPlan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.BKAccountPlanModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BKAccountPlanEditStageController {

    @FXML
    private TextField accountNumber;
    @FXML
    private TextField accountDes;
    private Stage thisStage;

    private BKAccountPlanModel bkAccountPlanModel;

    public void init() {
        bkAccountPlanModel.convertEdit();

        accountNumber.textProperty().bindBidirectional(bkAccountPlanModel.getBkAccountFX().accountProperty());
        accountDes.textProperty().bindBidirectional(bkAccountPlanModel.getBkAccountFX().descriptionProperty());
    }

    public void saveButtonOnAction() {
        try {
            if (accountNumber.textProperty().getValue().isEmpty() || accountDes.textProperty().getValue().isEmpty()) {
                DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.accountPlan.empty.data");
            } else {
                int answer = bkAccountPlanModel.saveEdit();
                thisStage.close();
            }

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

    public BKAccountPlanModel getBkAccountPlanModel() {
        return bkAccountPlanModel;
    }

    public void setBkAccountPlanModel(BKAccountPlanModel bkAccountPlanModel) {
        this.bkAccountPlanModel = bkAccountPlanModel;
    }
}
