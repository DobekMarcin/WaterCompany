package md.program.controller.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.CompanyFX;
import md.program.modelFX.CompanyListModel;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class CompanyTableStageController {

    public static final String FXML_ADD_NEW_COMPANY_FXML = "/FXML/CompanyTableAddStage.fxml";
    public static final String FXML_EDIT_NEW_COMPANY_FXML = "/FXML/CompanyTableEditStage.fxml";
    @FXML
    private TextField filterTextField;

    @FXML
    private TableView<CompanyFX> companyTable;
    @FXML
    private TableColumn<CompanyFX,Number> idColumn;
    @FXML
    private TableColumn<CompanyFX,String> nameColumn;
    @FXML
    private TableColumn<CompanyFX,String> nipColumn;
    @FXML
    private TableColumn<CompanyFX,String> placeColumn;
    @FXML
    private TableColumn<CompanyFX,String> postCodeColumn;
    @FXML
    private TableColumn<CompanyFX,String> postColumn;
    @FXML
    private TableColumn<CompanyFX,String> addressColumn;
    @FXML
    private TableColumn<CompanyFX,String> phoneColumn;
    @FXML
    private TableColumn<CompanyFX,String> emailColumn;
    private CompanyListModel companyListModel = new CompanyListModel();


    public void init() {
        try {
            companyListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
            companyListModel.init();
            tableInit();

            filterTextField.textProperty().addListener(observable -> companyListModel.filterCompanyList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        companyTable.setItems(companyListModel.getCompanyFXObservableList());

        idColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
        nipColumn.setCellValueFactory(cellDate -> cellDate.getValue().nipProperty());
        placeColumn.setCellValueFactory(cellDate -> cellDate.getValue().placeProperty());
        postCodeColumn.setCellValueFactory(cellDate-> cellDate.getValue().post_codeProperty());
        postColumn.setCellValueFactory(cellDate -> cellDate.getValue().postProperty());
        addressColumn.setCellValueFactory(cellDate -> cellDate.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellDate -> cellDate.getValue().phoneProperty());
        emailColumn.setCellValueFactory(cellDate -> cellDate.getValue().emailProperty());



    }

    public void addCompanyOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_COMPANY_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("company.table.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        CompanyTableAddStageController companyTableAddStageController = fxmlLoader.getController();
        companyTableAddStageController.init();
        companyTableAddStageController.setThisStage(stage1);
        stage1.showAndWait();
        init();
        companyTable.getSelectionModel().selectLast();
        companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());
    }

    public void deleteCompanyOnAction() {
        if (companyTable.getSelectionModel().getSelectedItem() != null) {
            CompanyFX companyFX = companyTable.getSelectionModel().getSelectedItem();
            companyListModel.setDeleteCompany(companyFX);
            try {
                if(companyListModel.checkInvoice()){
                    DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.company.delete");
                }else{
                try {
                    Boolean result = companyListModel.deleteCompany();
                    if (result == false) {
                     //   DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.partner.delete");
                    } else {
                        init();
                        companyTable.getSelectionModel().selectLast();
                        companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }}
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void editCompanyOnAction(ActionEvent actionEvent) {

        if (companyTable.getSelectionModel().getSelectedItem() != null) {
            CompanyFX companyFX = companyTable.getSelectionModel().getSelectedItem();
            companyListModel.setEditCompany(companyFX);

            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_EDIT_NEW_COMPANY_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("company.table.edit.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            CompanyTableEditStageController companyTableEditStageController = fxmlLoader.getController();
            companyTableEditStageController.setThisStage(stage1);
            companyTableEditStageController.setCompanyListModel(companyListModel);
            companyTableEditStageController.init();

            stage1.showAndWait();
            init();
            companyTable.getSelectionModel().selectLast();
            companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());

        }



    }

    public void printCompanyOnAction(ActionEvent actionEvent) {
    }
}
