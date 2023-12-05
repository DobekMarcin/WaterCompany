package md.program.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;

public class MainStageController {
    public static final String FXML_RATE_TABLE_STAGE_FXML = "/FXML/RateTableStage.fxml";
    @FXML
    public void closeOnAction() {
        Platform.exit();
    }

    @FXML
    public void rateTableOnAction() {
        openRateTableStage();
    }

    private void openRateTableStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_RATE_TABLE_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("rate.table.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        RateTableStageController rateTableStageController=fxmlLoader.getController();
        rateTableStageController.setThisStage(stage1);
      //  stage1.setResizable(true);
        stage1.show();
    }
}
