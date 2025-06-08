package md.program.controller.invoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FormatStringConverter;
import javafx.util.converter.NumberStringConverter;
import md.program.controller.BOYearStageController;
import md.program.modelFX.CompanyListModel;
import md.program.modelFX.InvoiceListModel;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

public class InvoiceTableAddStageController {

    private static final String FXML_DIALOG_CHOOSE_COMPANY_FXML = "/FXML/CompanyDialogStage.fxml";

    @FXML
    private TextField invoiceIdCompanyTextField;
    @FXML
    private TextField invoiceIdTextField;
    @FXML
    private TextField invoiceNumberTextField;
    @FXML
    private TextField invoiceCompanyTextField;
    @FXML
    private TextField invoiceAmountTextField;
    @FXML
    private DatePicker invoiceDatePicker;
    @FXML
    private TextArea invoiceDesTextField;
    private Stage thisStage;
    private InvoiceListModel invoiceListModel = new InvoiceListModel();
    private CompanyListModel companyListModel = new CompanyListModel();

    public void init(){
        invoiceIdTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().idProperty(), new NumberStringConverter());
        invoiceNumberTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().invoiceNumberProperty());
        invoiceCompanyTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().companyNameProperty());
        invoiceAmountTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().amountProperty(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        invoiceIdCompanyTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().companyIdProperty(),new NumberStringConverter());
        invoiceDesTextField.textProperty().bindBidirectional(invoiceListModel.getNewInvoice().invoiceDesProperty());
        invoiceDatePicker.setValue(LocalDate.now());
        invoiceAmountTextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));



        try {
            invoiceListModel.getNewInvoice().setId(invoiceListModel.getNextId());
            invoiceListModel.getNewInvoice().setInvoiceNumber("");
            invoiceListModel.getNewInvoice().setCompanyName("");
            invoiceListModel.getNewInvoice().setCompanyId(0);
            invoiceListModel.getNewInvoice().setAmount(0.0);
            invoiceListModel.getNewInvoice().setInvoiceDes("");
            invoiceListModel.getNewInvoice().setPk(false);
            invoiceListModel.getNewInvoice().setYear(0);
            invoiceListModel.getNewInvoice().setInvoiceDate("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addButtonOnAction() {
        if(invoiceNumberTextField.getText().isEmpty() || invoiceCompanyTextField.getText().isEmpty() || invoiceIdCompanyTextField.getText().isEmpty() || invoiceDesTextField.getText().isEmpty() || invoiceAmountTextField.getText().isEmpty()){
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "invoice.dialog.data");
        }else{
            if(Double.valueOf(invoiceAmountTextField.getText())>0.00d){
                try {
                    Date date = Date.valueOf(invoiceDatePicker.getValue());
                    int year = invoiceListModel.getYearOfInvoice();
                    invoiceListModel.getNewInvoice().setYear(year);
                    invoiceListModel.getNewInvoice().setInvoiceDate(date.toString());
                    invoiceListModel.addNewInvoice();
                    thisStage.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }else{
                DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "invoice.dialog.data");
            }
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public InvoiceListModel getInvoiceListModel() {
        return invoiceListModel;
    }

    public void setInvoiceListModel(InvoiceListModel invoiceListModel) {
        this.invoiceListModel = invoiceListModel;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void chooseCompanyOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_DIALOG_CHOOSE_COMPANY_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("invoice.dialog.company"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        CompanyDialogStageController companyDialogStageController = fxmlLoader.getController();
        companyDialogStageController.setThisStage(stage1);
        companyDialogStageController.setCompanyListModel(companyListModel);
        companyDialogStageController.init();
        stage1.showAndWait();

        invoiceIdCompanyTextField.setText(String.valueOf(companyListModel.getChooseCompanyInvoice().getId()));
        invoiceCompanyTextField.setText(companyListModel.getChooseCompanyInvoice().getName());
    }

//
//    public void addButtonOnAction(ActionEvent actionEvent) {
//        if(companyListModel.getNewCompany().getName().isEmpty() || companyListModel.getNewCompany().getNip().isEmpty()){
//            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "company.add.dialog");
//        }else{
//            try {
//                companyListModel.addNewCompany();
//                thisStage.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void cancelButtonOnAction() {
//        thisStage.close();
//    }
}
