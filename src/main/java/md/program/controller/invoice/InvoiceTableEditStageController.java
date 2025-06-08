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

public class InvoiceTableEditStageController {
    private static final String FXML_DIALOG_CHOOSE_COMPANY_FXML = "/FXML/CompanyDialogStage.fxml";
    @FXML
    private TextField invoiceIdTextField;
    @FXML
    private TextField invoiceNumberTextField;
    @FXML
    private TextArea invoiceDesTextField;
    @FXML
    private TextField invoiceAmountTextField;
    @FXML
    private TextField invoiceCompanyTextField;
    @FXML
    private DatePicker invoiceDatePicker;
    @FXML
    private TextField invoiceIdCompanyTextField;

    private Stage thisStage;
    private InvoiceListModel invoiceListModel;
    private CompanyListModel companyListModel = new CompanyListModel();

    public void init(){

        invoiceIdTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().idProperty(), new NumberStringConverter());
        invoiceNumberTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().invoiceNumberProperty());
        invoiceCompanyTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().companyNameProperty());
        invoiceAmountTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().amountProperty(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        invoiceIdCompanyTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().companyIdProperty(),new NumberStringConverter());
        invoiceDesTextField.textProperty().bindBidirectional(invoiceListModel.getEditInvoice().invoiceDesProperty());
        System.out.println(invoiceListModel.getEditInvoice().getInvoiceDate());
        Date date = Date.valueOf(invoiceListModel.getEditInvoice().getInvoiceDate());
        LocalDate ld = date.toLocalDate();
        invoiceDatePicker.setValue(ld);
        invoiceAmountTextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if(invoiceNumberTextField.getText().isEmpty() || invoiceCompanyTextField.getText().isEmpty() || invoiceIdCompanyTextField.getText().isEmpty() || invoiceDesTextField.getText().isEmpty() || invoiceAmountTextField.getText().isEmpty()){
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "invoice.dialog.data");
        }else{
            if(Double.valueOf(invoiceAmountTextField.getText())>0.00d){
                try {
                    Date date = Date.valueOf(invoiceDatePicker.getValue());
                    invoiceListModel.getEditInvoice().setInvoiceDate(date.toString());
                    invoiceListModel.saveInvoice();
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

    public void cancelButtonOnAction(ActionEvent actionEvent) {
        thisStage.close();
    }

    public void chooseCompanyOnAction(ActionEvent actionEvent) {
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

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public CompanyListModel getCompanyListModel() {
        return companyListModel;
    }

    public void setCompanyListModel(CompanyListModel companyListModel) {
        this.companyListModel = companyListModel;
    }

    public InvoiceListModel getInvoiceListModel() {
        return invoiceListModel;
    }

    public void setInvoiceListModel(InvoiceListModel invoiceListModel) {
        this.invoiceListModel = invoiceListModel;
    }
}
