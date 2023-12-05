package md.program.modelFX;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RateYearFX {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleDoubleProperty rate = new SimpleDoubleProperty();

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

    public double getRate() {
        return rate.get();
    }

    public SimpleDoubleProperty rateProperty() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }
}
