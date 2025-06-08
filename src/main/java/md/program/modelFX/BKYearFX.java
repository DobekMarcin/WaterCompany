package md.program.modelFX;

import javafx.beans.property.SimpleIntegerProperty;

public class BKYearFX {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();

    public BKYearFX(SimpleIntegerProperty id, SimpleIntegerProperty year) {
        this.id = id;
        this.year = year;
    }

    public BKYearFX() {

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

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }
}
