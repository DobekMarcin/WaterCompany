package md.program.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class PartnerTableStageController {

    private static final String FXML_PARTNER_TABLE_ADD_STAGE_FXML = "/FXML/PartnerTableAddStage.fxml";
    private static final String FXML_PARTNER_TABLE_EDIT_STAGE_FXML = "/FXML/PartnerTableEditStage.fxml";
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
    private TableColumn<PartnerFX, Number> peopleColumnPartnerTable;
    @FXML
    private TableColumn<PartnerFX, Boolean> archiveColumnPartnerTable;
    private Stage mainStage = null;
    private PartnerListModel partnerListModel = new PartnerListModel();
    private PartnerModel partnerModel = new PartnerModel();

    public void init() {
        try {
            partnerListModel.init();
            tableInit();
            initFilter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void initFilter() {
        FilteredList<PartnerFX> filteredData = new FilteredList<>(partnerListModel.getPartnerFXObservableList(), p->true);
        filterTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(paymentPlan->{
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(paymentPlan.getName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (paymentPlan.getSurname().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(paymentPlan.getAddress()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(paymentPlan.getId()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(paymentPlan.getPeopleCount()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<PartnerFX> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(partnerTable.comparatorProperty());
        partnerTable.setItems(sortedList);
    }
    private void tableInit() {
        partnerTable.setItems(partnerListModel.getPartnerFXObservableList());
        idColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        nameColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
        surnameColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().surnameProperty());
        addressColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().addressProperty());
        peopleColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().peopleCountProperty());
        archiveColumnPartnerTable.setCellValueFactory(cellDate -> cellDate.getValue().archivesProperty());
        archiveColumnPartnerTable.setCellFactory(CheckBoxTableCell.forTableColumn(archiveColumnPartnerTable));
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
    }

    @FXML
    public void addPartnerOnAction() {
        openPartnerTableAddStage();
    }

    @FXML
    public void deletePartnerOnAction() {
        PartnerFX partnerFX = partnerTable.getSelectionModel().getSelectedItem();
        partnerModel.setPartnerFX(partnerFX);
        try {
            partnerModel.deletePartner();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        init();
    }

    @FXML
    public void editPartnerOnAction() {
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
    }
}
