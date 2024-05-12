package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;


public class CounterTableStageController {
    private static final String FXML_COUNTER_TABLE_ADD_STAGE_FXML = "/FXML/CounterTableAddStage.fxml";

    @FXML
    private TableView yearRateTable;
    @FXML
    private TableColumn yearColumn;
    @FXML
    private TableColumn rateColumn;
    private Stage thisStage;


    public void initialize() {

    }

    public void addCounterRateButtonOnAction() {
        openCounterTableAddStage();
    }

    public void deleteCounterRateButtonOnAction() {
    }

    public void closeButtonOnAction() {
        thisStage.close();
    }

    private void openCounterTableAddStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_COUNTER_TABLE_ADD_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("rate.table.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        CounterTableAddStageController counterTableAddStageController = fxmlLoader.getController();
        counterTableAddStageController.setThisStage(stage1);
        counterTableAddStageController.init();
        stage1.showAndWait();
        initialize();
        yearRateTable.getSelectionModel().selectLast();
        yearRateTable.scrollTo(yearRateTable.getSelectionModel().getSelectedItem());
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
