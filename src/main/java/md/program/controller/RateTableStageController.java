package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RateTableStageController {

    private Stage thisStage;

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
@FXML
    public void closeButtonOnAction() {
    thisStage.close();
    }
}
