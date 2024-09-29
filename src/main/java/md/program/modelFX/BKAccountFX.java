package md.program.modelFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class BKAccountFX {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty root = new SimpleStringProperty();
    private SimpleStringProperty account = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleListProperty<BKAccountFX> accountList = new SimpleListProperty<>();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getRoot() {
        return root.get();
    }

    public SimpleStringProperty rootProperty() {
        return root;
    }

    public void setRoot(String root) {
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

    public ObservableList<BKAccountFX> getAccountList() {
        return accountList.get();
    }

    public SimpleListProperty<BKAccountFX> accountListProperty() {
        return accountList;
    }

    public void setAccountList(ObservableList<BKAccountFX> accountList) {
        this.accountList.set(accountList);
    }

    @Override
    public String toString() {
        return "BKAccountFX{" +
                "id=" + id +
                ", root=" + root +
                ", account=" + account +
                ", description=" + description +
                ", accountList=" + accountList +
                '}';
    }
}
