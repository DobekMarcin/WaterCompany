package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.database.repository.CounterReadRepository;
import md.program.modelFX.CounterReadModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class InitializeYearCounterReadStageController {

    @FXML
    private CheckBox onlyNewUsers;
    @FXML
    private TextField yearTextField;
    private Stage thisStage = null;
    private CounterReadTableStageController counterReadTableStageController;
    private CounterReadModel counterReadModel = new CounterReadModel();

    public void initialize(){
        counterReadModel.onlyNewUserProperty().bindBidirectional(onlyNewUsers.selectedProperty());
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void generateButtonOnAction() {
        try {
        Integer year=Integer.valueOf(yearTextField.getText());
            counterReadModel.generateCounterYear(year);
            counterReadTableStageController.setInitializeYear(year);
            thisStage.close();
            DialogUtil.confirmationDialog("dialog.confirmation.title","dialog.confirmation.header","dialog.confirmation.generate");
        } catch (SQLException | NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public CounterReadTableStageController getCounterReadTableStageController() {
        return counterReadTableStageController;
    }

    public void setCounterReadTableStageController(CounterReadTableStageController counterReadTableStageController) {
        this.counterReadTableStageController = counterReadTableStageController;
    }
}
