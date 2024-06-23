package md.program.controller;

import javafx.stage.Stage;

public class ArchiveInfoStageController {
    private Stage thisStage;

    public void confirmateButtonOnAction() {
        thisStage.close();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void init() {
    }
}
