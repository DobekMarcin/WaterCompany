package md.program.controller.BKPatterns;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.BKAccountListModel;
import md.program.modelFX.BKPatternFX;
import md.program.modelFX.PatternModelList;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class BKPatternTableStageController {
    private static final String FXML_ADD_NEW_PATTERN_FXML = "/FXML/BKPatternTableAddStage.fxml";
    private static final String FXML_EDIT_NEW_PATTERN_FXML = "/FXML/BKPatternTableEditStage.fxml";
    public TableColumn<BKPatternFX,String> debitColumn;
    public TableColumn<BKPatternFX,String> creditColumn;
    public TableColumn<BKPatternFX,Boolean> invoiceColumn;
    public TableColumn<BKPatternFX,Boolean> bankColumn;
    public TableColumn<BKPatternFX,Boolean> cashColumn;

    @FXML
    private TableView<BKPatternFX> patternTable;
    @FXML
    private TableColumn<BKPatternFX,Number> nrColumn;
    @FXML
    private TableColumn<BKPatternFX,String> desColumn;
    private PatternModelList patternInvoiceModelList = new PatternModelList();
    private BKAccountListModel bkAccountListModel = new BKAccountListModel();

    private Stage thisStage;

    public void init() {
        try {

            patternInvoiceModelList.init();
            tableInit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        patternTable.setItems(patternInvoiceModelList.getBkPatternFXObservableList());

        nrColumn.setCellValueFactory(cellDate->cellDate.getValue().id_patternProperty());
        desColumn.setCellValueFactory(cellDate -> cellDate.getValue().des_patternProperty());
        debitColumn.setCellValueFactory(cellDate-> cellDate.getValue().debitTextProperty());
        creditColumn.setCellValueFactory(cellDate->cellDate.getValue().creditTextProperty());


        invoiceColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceProperty());
        invoiceColumn.setCellFactory(CheckBoxTableCell.forTableColumn(invoiceColumn));

        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        bankColumn.setCellFactory(CheckBoxTableCell.forTableColumn(bankColumn));

        cashColumn.setCellValueFactory(cellData -> cellData.getValue().cashProperty());
        cashColumn.setCellFactory(CheckBoxTableCell.forTableColumn(cashColumn));

    }

    public void addPatternButtonOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_PATTERN_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKPatternTableAddStageController bkPatternTableAddStageController = fxmlLoader.getController();
        bkPatternTableAddStageController.init();
        bkPatternTableAddStageController.setThisStage(stage1);
        stage1.showAndWait();
        init();
        patternTable.getSelectionModel().selectLast();
        patternTable.scrollTo(patternTable.getSelectionModel().getSelectedItem());
    }

    public void deletePatternButtonOnAction() throws SQLException {
        if (patternTable.getSelectionModel().getSelectedItem() != null) {
            BKPatternFX patternFX = patternTable.getSelectionModel().getSelectedItem();
            patternInvoiceModelList.setDeletePattern(patternFX);
            patternInvoiceModelList.deletePattern();
            init();
        }
    }

    public void closeButtonOnAction() {
        thisStage.close();
    }



    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void editOnAction() {
        if (patternTable.getSelectionModel().getSelectedItem() != null) {
            BKPatternFX patternFX = patternTable.getSelectionModel().getSelectedItem();
            patternInvoiceModelList.setEditPatter(patternFX);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_EDIT_NEW_PATTERN_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.edit.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKPatternTableEditStageController bkPatternTableEditStageController = fxmlLoader.getController();
        bkPatternTableEditStageController.setPatternInvoiceModelList(patternInvoiceModelList);

        bkPatternTableEditStageController.init();
        bkPatternTableEditStageController.setThisStage(stage1);
        stage1.showAndWait();
        init();
        patternTable.getSelectionModel().selectLast();
        patternTable.scrollTo(patternTable.getSelectionModel().getSelectedItem());
    }}


}
