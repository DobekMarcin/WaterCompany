package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PartnerFX {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty address;
    private SimpleIntegerProperty peopleCount;
    private SimpleBooleanProperty archives;

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getPeopleCount() {
        return peopleCount.get();
    }

    public SimpleIntegerProperty peopleCountProperty() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount.set(peopleCount);
    }

    public boolean isArchives() {
        return archives.get();
    }

    public SimpleBooleanProperty archivesProperty() {
        return archives;
    }

    public void setArchives(boolean archives) {
        this.archives.set(archives);
    }
}
