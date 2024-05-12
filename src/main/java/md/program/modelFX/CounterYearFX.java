package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CounterYearFX {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleDoubleProperty counterRate = new SimpleDoubleProperty();

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
        return counterRate.get();
    }

    public SimpleDoubleProperty rateProperty() {
        return counterRate;
    }

    public void setRate(double rate) {
        this.counterRate.set(rate);
    }

    @Override
    public String toString() {
        return year.getValue() + "";
    }
}
