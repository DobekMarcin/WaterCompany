package md.program.controller;

import javafx.stage.Stage;
import md.program.modelFX.PartnerListModel;

public class PartnerStageController {
    private Stage mainStage = null;
    private PartnerListModel partnerListModel = new PartnerListModel();

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
