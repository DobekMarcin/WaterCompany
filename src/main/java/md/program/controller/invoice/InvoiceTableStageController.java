package md.program.controller.invoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.controller.company.CompanyTableAddStageController;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class InvoiceTableStageController {


    public static final String FXML_ADD_NEW_INVOICE_FXML = "/FXML/InvoiceTableAddStage.fxml";
    public static final String FXML_EDIT_INVOICE_FXML = "/FXML/InvoiceTableEditStage.fxml";
    @FXML
    private TextField filterTextField;
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private TableView<InvoiceFX> invoiceTable;
    @FXML
    private TableColumn<InvoiceFX, Number> idColumn;
    @FXML
    private TableColumn<InvoiceFX, String> invoiceDateColumn;
    @FXML
    private TableColumn<InvoiceFX, String> invoiceNrColumn;
    @FXML
    private TableColumn<InvoiceFX, Number> companyIdColumn;
    @FXML
    private TableColumn<InvoiceFX, String> companyNameColumn;
    @FXML
    private TableColumn<InvoiceFX, String> invoiceDesColumn;
    @FXML
    private TableColumn<InvoiceFX, String> invoiceAmountColumn;
    @FXML
    private TableColumn<InvoiceFX, Boolean> pkColumn;

    private InvoiceListModel invoiceListModel = new InvoiceListModel();
    private BKAccountListModel bkAccountListModel = new BKAccountListModel();

    public void init() {

        invoiceListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
        initComboBox();
        tableInit();
        filterTextField.textProperty().addListener(observable -> invoiceListModel.filterInvoiceList());
    }

    public void initComboBox() {

        try {
            yearComboBox.setItems(bkAccountListModel.getAllYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        invoiceTable.setItems(invoiceListModel.getInvoiceFXObservableList());

        idColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        invoiceDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().invoiceDateProperty());
        invoiceNrColumn.setCellValueFactory(cellDate -> cellDate.getValue().invoiceNumberProperty());
        companyIdColumn.setCellValueFactory(cellDate -> cellDate.getValue().companyIdProperty());
        companyNameColumn.setCellValueFactory(cellDate -> cellDate.getValue().companyNameProperty());
        invoiceDesColumn.setCellValueFactory(cellDate -> cellDate.getValue().invoiceDesProperty());
        invoiceAmountColumn.setCellValueFactory(cellDate -> cellDate.getValue().amountProperty().asString());
        pkColumn.setCellValueFactory(cellDate -> cellDate.getValue().pkProperty());

        pkColumn.setCellFactory(CheckBoxTableCell.forTableColumn(pkColumn));
        invoiceAmountColumn.setCellFactory(param -> new TableCell<InvoiceFX, String>() {
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



    public void addInvoiceOnAction() {
        if (!yearComboBox.getSelectionModel().isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_INVOICE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("invoice.table.add.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            InvoiceTableAddStageController invoiceTableAddStageController = fxmlLoader.getController();
            invoiceTableAddStageController.setInvoiceListModel(invoiceListModel);
            invoiceTableAddStageController.init();
            invoiceTableAddStageController.setThisStage(stage1);
            stage1.showAndWait();
            selectYearOnAction();
            invoiceTable.getSelectionModel().selectLast();
            invoiceTable.scrollTo(invoiceTable.getSelectionModel().getSelectedItem());
        }
    }

    public void deleteInvoiceOnAction(ActionEvent actionEvent) {
        if (invoiceTable.getSelectionModel().getSelectedItem() != null) {
            InvoiceFX invoiceFX = invoiceTable.getSelectionModel().getSelectedItem();
            invoiceListModel.setDeleteInvoice(invoiceFX);
            try {
                Boolean result = invoiceListModel.deleteCompany();
                if (result == false) {
                    //   DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.partner.delete");
                } else {
                    selectYearOnAction();
                    invoiceTable.getSelectionModel().selectLast();
                    invoiceTable.scrollTo(invoiceTable.getSelectionModel().getSelectedItem());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void editInvoiceOnAction(ActionEvent actionEvent) {
        if (invoiceTable.getSelectionModel().getSelectedItem() != null) {
            InvoiceFX invoiceFX = invoiceTable.getSelectionModel().getSelectedItem();
            invoiceListModel.setEditInvoice(invoiceFX);

            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_EDIT_INVOICE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("invoice.table.edir.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            InvoiceTableEditStageController invoiceTableEditStageController = fxmlLoader.getController();
            invoiceTableEditStageController.setInvoiceListModel(invoiceListModel);
            invoiceTableEditStageController.init();
            invoiceTableEditStageController.setThisStage(stage1);
            stage1.showAndWait();
            selectYearOnAction();
            invoiceTable.getSelectionModel().selectLast();
            invoiceTable.scrollTo(invoiceTable.getSelectionModel().getSelectedItem());
        }
    }

    public void printCompanyOnAction() {
    }

    public void selectYearOnAction() {
        try {
            int year = (int) yearComboBox.getSelectionModel().getSelectedItem();
            invoiceListModel.setYearOfInvoice(year);
            invoiceListModel.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//
//    public void addCompanyOnAction(ActionEvent actionEvent) {
//        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_COMPANY_FXML));
//        fxmlLoader.setResources(Utils.getResourceBundle());
//        Scene scene = null;
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Stage stage1 = new Stage();
//        stage1.setScene(scene);
//        stage1.setTitle(Utils.getResourceBundle().getString("company.table.add.title"));
//        stage1.initModality(Modality.APPLICATION_MODAL);
//        stage1.setResizable(false);
//        CompanyTableAddStageController companyTableAddStageController = fxmlLoader.getController();
//        companyTableAddStageController.init();
//        companyTableAddStageController.setThisStage(stage1);
//        stage1.showAndWait();
//        init();
//        companyTable.getSelectionModel().selectLast();
//        companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());
//    }
//
//    public void deleteCompanyOnAction() {
//        if (companyTable.getSelectionModel().getSelectedItem() != null) {
//            CompanyFX companyFX = companyTable.getSelectionModel().getSelectedItem();
//            companyListModel.setDeleteCompany(companyFX);
//            try {
//                Boolean result = companyListModel.deleteCompany();
//                if (result == false) {
//                 //   DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.partner.delete");
//                } else {
//                    init();
//                    companyTable.getSelectionModel().selectLast();
//                    companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//
//    public void editCompanyOnAction(ActionEvent actionEvent) {
//
//        if (companyTable.getSelectionModel().getSelectedItem() != null) {
//            CompanyFX companyFX = companyTable.getSelectionModel().getSelectedItem();
//            companyListModel.setEditCompany(companyFX);
//
//            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_EDIT_NEW_COMPANY_FXML));
//            fxmlLoader.setResources(Utils.getResourceBundle());
//            Scene scene = null;
//            try {
//                scene = new Scene(fxmlLoader.load());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Stage stage1 = new Stage();
//            stage1.setScene(scene);
//            stage1.setTitle(Utils.getResourceBundle().getString("company.table.edit.title"));
//            stage1.initModality(Modality.APPLICATION_MODAL);
//            stage1.setResizable(false);
//            CompanyTableEditStageController companyTableEditStageController = fxmlLoader.getController();
//            companyTableEditStageController.setThisStage(stage1);
//            companyTableEditStageController.setCompanyListModel(companyListModel);
//            companyTableEditStageController.init();
//
//            stage1.showAndWait();
//            init();
//            companyTable.getSelectionModel().selectLast();
//            companyTable.scrollTo(companyTable.getSelectionModel().getSelectedItem());
//
//        }
//
//
//
//    }
//
//    public void printCompanyOnAction(ActionEvent actionEvent) {
//    }
}
