package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BKPatternFX {

    private SimpleIntegerProperty id_pattern = new SimpleIntegerProperty();
    private SimpleStringProperty des_pattern = new SimpleStringProperty();
    private SimpleIntegerProperty debit = new SimpleIntegerProperty();
    private SimpleStringProperty debitText = new SimpleStringProperty();
    private SimpleIntegerProperty credit = new SimpleIntegerProperty();
    private SimpleStringProperty creditText = new SimpleStringProperty();
    private SimpleBooleanProperty invoice = new SimpleBooleanProperty();
    private SimpleBooleanProperty bank = new SimpleBooleanProperty();
    private SimpleBooleanProperty cash = new SimpleBooleanProperty();

    public int getId_pattern() {
        return id_pattern.get();
    }

    public SimpleIntegerProperty id_patternProperty() {
        return id_pattern;
    }

    public void setId_pattern(int id_pattern) {
        this.id_pattern.set(id_pattern);
    }

    public String getDes_pattern() {
        return des_pattern.get();
    }

    public SimpleStringProperty des_patternProperty() {
        return des_pattern;
    }

    public void setDes_pattern(String des_pattern) {
        this.des_pattern.set(des_pattern);
    }

    public int getDebit() {
        return debit.get();
    }

    public SimpleIntegerProperty debitProperty() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit.set(debit);
    }

    public String getDebitText() {
        return debitText.get();
    }

    public SimpleStringProperty debitTextProperty() {
        return debitText;
    }

    public void setDebitText(String debitText) {
        this.debitText.set(debitText);
    }

    public int getCredit() {
        return credit.get();
    }

    public SimpleIntegerProperty creditProperty() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit.set(credit);
    }

    public String getCreditText() {
        return creditText.get();
    }

    public SimpleStringProperty creditTextProperty() {
        return creditText;
    }

    public void setCreditText(String creditText) {
        this.creditText.set(creditText);
    }

    public boolean isInvoice() {
        return invoice.get();
    }

    public SimpleBooleanProperty invoiceProperty() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice.set(invoice);
    }

    public boolean isBank() {
        return bank.get();
    }

    public SimpleBooleanProperty bankProperty() {
        return bank;
    }

    public void setBank(boolean bank) {
        this.bank.set(bank);
    }

    public boolean isCash() {
        return cash.get();
    }

    public SimpleBooleanProperty cashProperty() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash.set(cash);
    }
}
