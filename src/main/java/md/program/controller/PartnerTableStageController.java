package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;

public class PartnerTableStageController {

    private static final String FXML_PARTNER_TABLE_ADD_STAGE_FXML = "/FXML/PartnerTableAddStage.fxml";
    private static final String FXML_PARTNER_TABLE_EDIT_STAGE_FXML = "/FXML/PartnerTableEditStage.fxml";

    @FXML
    private TableColumn<PartnerFX,Boolean> meterColumnPartnerTable;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<PartnerFX> partnerTable;
    @FXML
    private TableColumn<PartnerFX, Number> idColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, String> nameColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, String> surnameColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, String> addressColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX,String> postCodeColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX,String> postColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX,String> nipColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, Number> peopleColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, Boolean> archiveColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX,Boolean> companyColumnPartnerTable;
    private Stage mainStage = null;
    private PartnerListModel partnerListModel = new PartnerListModel();
    private PartnerModel partnerModel = new PartnerModel();

    public void init() {
        try {
            partnerListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
            partnerListModel.init();
            tableInit();
            filterTextField.textProperty().addListener(observable -> partnerListModel.filterPartnerList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        partnerTable.setItems(partnerListModel.getPartnerFXObservableList());
        idColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        nameColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
        surnameColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().surnameProperty());
        addressColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().addressProperty());
        postCodeColumnPartnerTable.setCellValueFactory(cellDate ->cellDate.getValue().postCodeProperty());
        postColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().postProperty());
        nipColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().nipProperty());
        peopleColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().peopleCountProperty());
        archiveColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().archivesProperty());
        archiveColumnPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(archiveColumnPartnerTable));
        companyColumnPartnerTable.setCellValueFactory(cellData->cellData.getValue().companyProperty());
        companyColumnPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(companyColumnPartnerTable));
        meterColumnPartnerTable.setCellValueFactory(cellData->cellData.getValue().meterProperty());
        meterColumnPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(meterColumnPartnerTable));
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void openPartnerTableAddStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PARTNER_TABLE_ADD_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("partner.table.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        PartnerTableAddStageController partnerTableAddStageController = fxmlLoader.getController();
        partnerTableAddStageController.init();
        partnerTableAddStageController.setStage(stage1);
        stage1.showAndWait();
        init();
        partnerTable.getSelectionModel().selectLast();
        partnerTable.scrollTo(partnerTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void addPartnerOnAction() {
        openPartnerTableAddStage();
    }

    @FXML
    public void deletePartnerOnAction() {
        if(partnerTable.getSelectionModel().getSelectedItem()!=null) {
            PartnerFX partnerFX = partnerTable.getSelectionModel().getSelectedItem();
            partnerModel.setPartnerFX(partnerFX);
            try {
                Boolean result = partnerModel.deletePartner();
                if(result==false){
                    DialogUtil.dialogAboutApplication("dialog.title","dialog.header","dialog.partner.delete");
                }else{
                    init();
                    partnerTable.getSelectionModel().selectLast();
                    partnerTable.scrollTo(partnerTable.getSelectionModel().getSelectedItem());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @FXML
    public void editPartnerOnAction() {
        if(partnerTable.getSelectionModel().getSelectedItem()!=null){
        PartnerFX partnerFX = partnerTable.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PARTNER_TABLE_EDIT_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("partner.table.edit.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);

        PartnerTableEditStageController partnerTableEditStageController = fxmlLoader.getController();
        partnerTableEditStageController.getPartnerModel().setPartnerFX(partnerFX);
        partnerTableEditStageController.init();
        partnerTableEditStageController.setStage(stage1);
        stage1.showAndWait();
        init();
        partnerTable.getSelectionModel().select(partnerListModel.getPartnerFXObservableList().filtered(partnerFX1 -> partnerFX1.getId()==partnerFX.getId()).get(0));
            partnerTable.scrollTo(partnerTable.getSelectionModel().getSelectedItem());
    }}

    public void partnerPrintOnAction() {
        try {
            partnerListModel.printPartnerList();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
