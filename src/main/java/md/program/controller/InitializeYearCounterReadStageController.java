package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.database.repository.CounterReadRepository;
import md.program.modelFX.CounterReadModel;

import java.sql.SQLException;

public class InitializeYearCounterReadStageController {

    @FXML
    private TextField yearTextField;
    private Stage thisStage = null;
    private CounterReadTableStageController counterReadTableStageController;
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private CounterReadModel counterReadModel = new CounterReadModel();

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void generateButtonOnAction() {
        Integer year=Integer.valueOf(yearTextField.getText());

        try {
            counterReadRepository.deleteCounterReadByYear(year);
            counterReadModel.generateCounterYear(year);
            counterReadTableStageController.setInitializeYear(year);
            thisStage.close();
        } catch (SQLException e) {
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
