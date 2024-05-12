package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import md.program.modelFX.PaymentPlanModel;
import md.program.modelFX.RateYearFX;
import md.program.modelFX.RateYearListModel;
import md.program.modelFX.RateYearModel;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
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
    private TableColumn<RateYearFX, String> rateColumn;
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
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asString());
        rateColumn.setCellFactory(param-> new TableCell<RateYearFX,String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(Utils.getDecimalFormatWithTwoPlaces().format(Double.valueOf(item)));
                }
            }
        });
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
        yearRateTable.getSelectionModel().selectLast();
        yearRateTable.scrollTo(yearRateTable.getSelectionModel().getSelectedItem());
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
        if (rateYearFX != null) {
            rateYearModel.setRateYearFX(rateYearFX);
            try {
                Boolean result = rateYearModel.deleteRateYear();
                if(!result){
                    DialogUtil.dialogAboutApplication("dialog.title","dialog.header","dialog.year.rate.delete");
                }else{
                    initialize();
                    yearRateTable.getSelectionModel().selectLast();
                    yearRateTable.scrollTo(yearRateTable.getSelectionModel().getSelectedItem());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
