package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PartnerFX implements Cloneable{
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleStringProperty postCode = new SimpleStringProperty();
    private SimpleStringProperty post = new SimpleStringProperty();
    private SimpleStringProperty nip = new SimpleStringProperty();
    private SimpleIntegerProperty peopleCount = new SimpleIntegerProperty();
    private SimpleBooleanProperty archives = new SimpleBooleanProperty();
    private SimpleBooleanProperty company = new SimpleBooleanProperty();
    private SimpleBooleanProperty meter = new SimpleBooleanProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleIntegerProperty month = new SimpleIntegerProperty();

    public PartnerFX(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty address,SimpleStringProperty postCode,SimpleStringProperty post,SimpleStringProperty nip, SimpleIntegerProperty peopleCount, SimpleBooleanProperty archives, SimpleBooleanProperty company,SimpleBooleanProperty meter,SimpleIntegerProperty year,SimpleIntegerProperty month) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.postCode = postCode;
        this.post = post;
        this.nip = nip;
        this.peopleCount = peopleCount;
        this.archives = archives;
        this.company = company;
        this.meter = meter;
        this.year = year;
        this.month = month;

    }

    public PartnerFX() {
    }

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

    public String getPostCode() {
        return postCode.get();
    }

    public SimpleStringProperty postCodeProperty() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
    }

    public String getPost() {
        return post.get();
    }

    public SimpleStringProperty postProperty() {
        return post;
    }

    public void setPost(String post) {
        this.post.set(post);
    }

    public String getNip() {
        return nip.get();
    }

    public SimpleStringProperty nipProperty() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip.set(nip);
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

    public boolean isCompany() {
        return company.get();
    }

    public SimpleBooleanProperty companyProperty() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company.set(company);
    }

    public boolean isMeter() {
        return meter.get();
    }

    public SimpleBooleanProperty meterProperty() {
        return meter;
    }

    public void setMeter(boolean meter) {
        this.meter.set(meter);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public int getMonth() {
        return month.get();
    }

    public SimpleIntegerProperty monthProperty() {
        return month;
    }

    public void setMonth(int month) {
        this.month.set(month);
    }
}
