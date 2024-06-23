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
import md.program.modelFX.LogPartnerFX;
import md.program.modelFX.LogPartnerListModel;
import md.program.modelFX.PartnerFX;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;
import org.jfree.util.Log;

import java.io.IOException;
import java.sql.SQLException;

public class LogPartnerTableStageController {

    private static final String FXML_LOG_PARTNER_TABLE_EDIT_STAGE_FXML = "/FXML/LogPartnerTableEditStage.fxml";

    @FXML
    private TableView<LogPartnerFX> logPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Number> idColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Number> yearColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Number> monthColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> nameColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> surnameColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> addressColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> postCodeColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> postColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Number> peopleColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Boolean> companyColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, String> nipColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Boolean> meterColumnLogPartnerTable;
    @FXML
    private TableColumn<LogPartnerFX, Boolean> archiveColumnLogPartnerTable;
    private Stage thisStage;
    private Integer idPartnera;

    private LogPartnerListModel logPartnerListModel = new LogPartnerListModel();

    public void init() {
        try {
            logPartnerListModel.setIdPartnera(idPartnera);
            logPartnerListModel.init();
            tableInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        logPartnerTable.setItems(logPartnerListModel.getLogPartnerFXObservableList());
        idColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().id_loguProperty());
        yearColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().yearProperty());
        monthColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().monthProperty());
        nameColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
        surnameColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().surnameProperty());
        addressColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().addressProperty());
        postCodeColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().postCodeProperty());
        postColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().postProperty());
        nipColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().nipProperty());
        peopleColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().peopleCountProperty());
        archiveColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().archivesProperty());
        archiveColumnLogPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(archiveColumnLogPartnerTable));
        companyColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().companyProperty());
        companyColumnLogPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(companyColumnLogPartnerTable));
        meterColumnLogPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().meterProperty());
        meterColumnLogPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(meterColumnLogPartnerTable));

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

    public Integer getIdPartnera() {
        return idPartnera;
    }

    public void setIdPartnera(Integer idPartnera) {
        this.idPartnera = idPartnera;
    }

    public void deleteLog() {
        if (logPartnerTable.getSelectionModel().getSelectedItem() != null) {
            LogPartnerFX logPartnerFX = logPartnerTable.getSelectionModel().getSelectedItem();
            logPartnerListModel.setLogPartnerFX(logPartnerFX);
            try {
                Boolean result = logPartnerListModel.deletePartner();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            init();
            logPartnerTable.getSelectionModel().selectLast();
            logPartnerTable.scrollTo(logPartnerTable.getSelectionModel().getSelectedItem());
        }
    }

    public void editLogOnAction() {
        LogPartnerFX logPartnerFX = logPartnerTable.getSelectionModel().getSelectedItem();
        if (logPartnerFX != null) {
            logPartnerListModel.setLogPartnerFX(logPartnerFX);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_LOG_PARTNER_TABLE_EDIT_STAGE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("log.partner.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            LogPartnerTableDateEditStageController logPartnerTableDateEditStageController = fxmlLoader.getController();
            logPartnerTableDateEditStageController.setLogPartnerListModel(logPartnerListModel);
            logPartnerTableDateEditStageController.setThisStage(stage1);

            logPartnerTableDateEditStageController.init();
            stage1.resizableProperty().set(true);
            stage1.showAndWait();
        }
    }
}
