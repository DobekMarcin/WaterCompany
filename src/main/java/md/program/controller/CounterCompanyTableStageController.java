package md.program.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;


public class CounterCompanyTableStageController {
    private static final String FXML_COUNTER_COMPANY_TABLE_ADD_STAGE_FXML = "/FXML/CounterCompanyTableAddStage.fxml";

    @FXML
    private TableView<CounterYearFX> yearRateTable;
    @FXML
    private TableColumn<CounterYearFX, Number> yearColumn;
    @FXML
    private TableColumn<CounterYearFX, String> rateColumn;
    private Stage thisStage;
    private CounterCompanyYearListModel counterYearListModel = new CounterCompanyYearListModel();
    private CounterCompanyYearModel counterYearModelCompany = new CounterCompanyYearModel();


    public void initialize() {
        try {

            counterYearListModel.init();
            tableInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        yearRateTable.setItems(counterYearListModel.getCounterYearFXObservableList());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asString());

        rateColumn.setCellFactory(param-> new TableCell<CounterYearFX,String>(){
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
    public void addCounterRateButtonOnAction() {
        openCounterTableAddStage();
    }

    public void deleteCounterRateButtonOnAction() {

        CounterYearFX counterYearFX = yearRateTable.getSelectionModel().getSelectedItem();
        if (counterYearFX != null) {
            counterYearModelCompany.setCounterYearFX(counterYearFX);
            try {
                Boolean result = counterYearModelCompany.deleteCounterYear();
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

    public void closeButtonOnAction() {
        thisStage.close();
    }

    private void openCounterTableAddStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_COUNTER_COMPANY_TABLE_ADD_STAGE_FXML));
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
        CounterCompanyTableAddStageController counterTableAddStageController = fxmlLoader.getController();
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
