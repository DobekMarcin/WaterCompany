package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.BKAccountPlanModel;

import java.sql.SQLException;

public class BKAccountPlanAddStageController {
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

    public void addButtonOnAction() {
        try {
           int answer = bkAccountPlanModel.addnew();

            thisStage.close();
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
