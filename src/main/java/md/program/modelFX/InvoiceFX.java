package md.program.modelFX;

import javafx.beans.property.*;

import java.sql.Date;

public class InvoiceFX {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleStringProperty invoiceDate = new SimpleStringProperty();
    private SimpleStringProperty invoiceNumber = new SimpleStringProperty();
    private SimpleIntegerProperty companyId = new SimpleIntegerProperty();
    private SimpleStringProperty companyName = new SimpleStringProperty();
    private SimpleStringProperty invoiceDes = new SimpleStringProperty();
    private SimpleDoubleProperty amount = new SimpleDoubleProperty();
    private SimpleBooleanProperty pk = new SimpleBooleanProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getInvoiceDate() {
        return invoiceDate.get();
    }

    public SimpleStringProperty invoiceDateProperty() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate.set(invoiceDate);
    }

    public String getInvoiceNumber() {
        return invoiceNumber.get();
    }

    public SimpleStringProperty invoiceNumberProperty() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber.set(invoiceNumber);
    }

    public int getCompanyId() {
        return companyId.get();
    }

    public SimpleIntegerProperty companyIdProperty() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId.set(companyId);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getInvoiceDes() {
        return invoiceDes.get();
    }

    public SimpleStringProperty invoiceDesProperty() {
        return invoiceDes;
    }

    public void setInvoiceDes(String invoiceDes) {
        this.invoiceDes.set(invoiceDes);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public boolean isPk() {
        return pk.get();
    }

    public SimpleBooleanProperty pkProperty() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk.set(pk);
    }
}
