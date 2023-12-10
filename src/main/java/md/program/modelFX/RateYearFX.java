package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RateYearFX {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleDoubleProperty rate = new SimpleDoubleProperty();
    private SimpleBooleanProperty paymentPlanIsGenerated = new SimpleBooleanProperty();

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

    public boolean isPaymentPlanIsGenerated() {
        return paymentPlanIsGenerated.get();
    }

    public SimpleBooleanProperty paymentPlanIsGeneratedProperty() {
        return paymentPlanIsGenerated;
    }

    public void setPaymentPlanIsGenerated(boolean paymentPlanIsGenerated) {
        this.paymentPlanIsGenerated.set(paymentPlanIsGenerated);
    }

    @Override
    public String toString() {
        return  year.getValue()+"";
    }
}
