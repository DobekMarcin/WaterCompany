package md.program.controller.BKAccountList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import md.program.modelFX.*;

import java.sql.SQLException;

public class BKAccountListStageController {

    @FXML
    private TableColumn<BKAccountFX,Boolean> synColumn;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private TableView<BKAccountFX> accountTable;
    @FXML
    private TableColumn<BKAccountFX,String> accountNumberColumn;
    @FXML
    private TableColumn<BKAccountFX,String> descriptionAccountColumn;
    @FXML
    private TableColumn<BKAccountFX,String> debetAccountColumn;
    @FXML
    private TableColumn<BKAccountFX,String> creditAccountColumn;
    private Stage stage;

    private BKAccountListModel bkAccountListModel = new BKAccountListModel();

    public void init() {
      //  try {
         //   bkAccountListModel.init();
            tableInit();
     //   } catch (SQLException e) {
     //       throw new RuntimeException(e);
    //    }

        initComboBox();
    }

    public void initComboBox() {

        try {
            yearComboBox.setItems(bkAccountListModel.getAllYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        accountTable.setItems(bkAccountListModel.getBkAccountFXObservableList());
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
            bkAccountListModel.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
