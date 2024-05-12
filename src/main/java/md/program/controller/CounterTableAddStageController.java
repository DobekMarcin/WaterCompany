package md.program.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class CounterTableAddStageController {

    private Stage thisStage = null;

    public void init() {
    }

    public void addButtonOnAction() {
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
}
