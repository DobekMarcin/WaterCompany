package md.program.controller.BKAccountList;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import md.program.modelFX.*;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BKAccountListStageController {

    @FXML
    private TableColumn<BKAccountFX, Boolean> synColumn;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private TableView<BKAccountFX> accountTable;
    @FXML
    private TableColumn<BKAccountFX, String> accountNumberColumn;
    @FXML
    private TableColumn<BKAccountFX, String> descriptionAccountColumn;
    @FXML
    private TableColumn<BKAccountFX, String> debetAccountColumn;
    @FXML
    private TableColumn<BKAccountFX, String> creditAccountColumn;
    private Stage stage;

    private BKAccountListYearModel bkAccountListYearModel = new BKAccountListYearModel();
    private SettingsModel settingsModel = new SettingsModel();

    public void init() {

        tableInit();
        initComboBox();

        Boolean isBKYear = null;
        try {
            isBKYear = bkAccountListYearModel.checkBKPlanDefaultYear(settingsModel.getDefaultYear());
            if (isBKYear) {

                yearComboBox.getSelectionModel().select(settingsModel.getDefaultYear());
                bkAccountListYearModel.setYear(settingsModel.getDefaultYear());
                selectYearOnAction();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void initComboBox() {

        try {
            yearComboBox.setItems(bkAccountListYearModel.getAllYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        accountTable.setItems(bkAccountListYearModel.getBkAccountFXObservableList());
        accountNumberColumn.setCellValueFactory(cellDate -> cellDate.getValue().accountProperty());
        descriptionAccountColumn.setCellValueFactory(cellDate -> cellDate.getValue().descriptionProperty());
        debetAccountColumn.setCellValueFactory(cellDate -> cellDate.getValue().accountProperty());
        creditAccountColumn.setCellValueFactory(cellDate -> cellDate.getValue().accountProperty());

        synColumn.setCellValueFactory(cellData -> cellData.getValue().synProperty());
        synColumn.setCellFactory(CheckBoxTableCell.forTableColumn(synColumn));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void selectYearOnAction() {
        try {
            bkAccountListYearModel.setYear((int)yearComboBox.getSelectionModel().getSelectedItem());
            bkAccountListYearModel.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void syntheticOnAction(){

        if(accountTable.getSelectionModel().getSelectedItem().isSyn()){

        }else{
            DialogUtil.confirmationDialog("dialog.confirmation.title","error.header","dialog.account.syn");
        }

    }
}
