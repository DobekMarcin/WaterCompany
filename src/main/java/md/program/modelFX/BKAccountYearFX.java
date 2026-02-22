package md.program.modelFX;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class BKAccountYearFX {

    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleIntegerProperty root = new SimpleIntegerProperty(0);
    private SimpleStringProperty account = new SimpleStringProperty("");
    private SimpleStringProperty description = new SimpleStringProperty("");
    private SimpleBooleanProperty syn = new SimpleBooleanProperty(false);
    private SimpleStringProperty fullName = new SimpleStringProperty();

    private SimpleDoubleProperty debit = new SimpleDoubleProperty();
    private SimpleDoubleProperty credit = new SimpleDoubleProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRoot() {
        return root.get();
    }

    public SimpleIntegerProperty rootProperty() {
        return root;
    }

    public void setRoot(int root) {
        this.root.set(root);
    }

    public String getAccount() {
        return account.get();
    }

    public SimpleStringProperty accountProperty() {
        return account;
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean isSyn() {
        return syn.get();
    }

    public SimpleBooleanProperty synProperty() {
        return syn;
    }

    public void setSyn(boolean syn) {
        this.syn.set(syn);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public double getDebit() {
        return debit.get();
    }

    public SimpleDoubleProperty debitProperty() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit.set(debit);
    }

    public double getCredit() {
        return credit.get();
    }

    public SimpleDoubleProperty creditProperty() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit.set(credit);
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

    @Override
    public String toString() {
        return "BKAccountYearFX{" +
                "id=" + id +
                ", year=" + year +
                ", root=" + root +
                ", account=" + account +
                ", description=" + description +
                ", syn=" + syn +
                ", fullName=" + fullName +
                ", debit=" + debit +
                ", credit=" + credit +
                '}';
    }
}
