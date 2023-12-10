package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.PaymentPlanModel;
import md.program.modelFX.RateYearFX;
import md.program.modelFX.RateYearListModel;
import md.program.modelFX.RateYearModel;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class RateTableStageController {

    private static final String FXML_RATE_TABLE_ADD_STAGE_FXML = "/FXML/RateTableAddStage.fxml";
    @FXML
    private TableColumn<RateYearFX, Boolean> planColumn;
    @FXML
    private TableView<RateYearFX> yearRateTable;
    @FXML
    private TableColumn<RateYearFX, Number> yearColumn;
    @FXML
    private TableColumn<RateYearFX, Number> rateColumn;
    private Stage thisStage;
    private RateYearListModel rateYearListModel = new RateYearListModel();
    private RateYearModel rateYearModel = new RateYearModel();
    private PaymentPlanModel paymentPlanModel = new PaymentPlanModel();

    public void initialize() {
        try {
            rateYearListModel.init();
            tableInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        yearRateTable.setItems(rateYearListModel.getRateYearFXObservableList());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty());
        planColumn.setCellValueFactory(cellData -> cellData.getValue().paymentPlanIsGeneratedProperty());
        planColumn.setCellFactory(CheckBoxTableCell.forTableColumn(planColumn));
    }


    @FXML
    public void closeButtonOnAction() {
        thisStage.close();
    }

    @FXML
    public void addRateButtonOnAction() {
        openRateTableAddStage();
    }

    private void openRateTableAddStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_RATE_TABLE_ADD_STAGE_FXML));
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
        RateTableAddStageController rateTableAddStageController = fxmlLoader.getController();
        rateTableAddStageController.setThisStage(stage1);
        rateTableAddStageController.init();
        stage1.showAndWait();
        initialize();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    @FXML
    public void deleteRateButtonOnAction() {
        RateYearFX rateYearFX = yearRateTable.getSelectionModel().getSelectedItem();
        rateYearModel.setRateYearFX(rateYearFX);
        try {
            rateYearModel.deleteRateYear();
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void generatePaymentPlanButtonOnAction() {
        try {
            paymentPlanModel.generatePaymentPlan(yearRateTable.getSelectionModel().getSelectedItem());
            RateYearFX rateYearFX = yearRateTable.getSelectionModel().getSelectedItem();
            rateYearFX.setPaymentPlanIsGenerated(true);
            rateYearModel.setRateYearFX(rateYearFX);
            rateYearModel.updateRateYearGeneratedStatus();
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
