package md.program.modelFX;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PartnerBOFX {

    private SimpleObjectProperty<PartnerFX> partnerFXSimpleObjectProperty;
    private SimpleIntegerProperty parterIdSimpleIntegerProperty = new SimpleIntegerProperty();
    private SimpleDoubleProperty partnerBoValueProperty = new SimpleDoubleProperty();

    public PartnerBOFX() {
    }

    public PartnerBOFX(SimpleObjectProperty<PartnerFX> partnerFXSimpleObjectProperty, SimpleIntegerProperty parterIdSimpleIntegerProperty, SimpleDoubleProperty partnerBoValueProperty) {
        this.partnerFXSimpleObjectProperty = partnerFXSimpleObjectProperty;
        this.parterIdSimpleIntegerProperty = parterIdSimpleIntegerProperty;
        this.partnerBoValueProperty = partnerBoValueProperty;
    }

    public PartnerFX getPartnerFXSimpleObjectProperty() {
        return partnerFXSimpleObjectProperty.get();
    }

    public SimpleObjectProperty<PartnerFX> partnerFXSimpleObjectPropertyProperty() {
        return partnerFXSimpleObjectProperty;
    }

    public void setPartnerFXSimpleObjectProperty(PartnerFX partnerFXSimpleObjectProperty) {
        this.partnerFXSimpleObjectProperty.set(partnerFXSimpleObjectProperty);
    }

    public int getParterIdSimpleIntegerProperty() {
        return parterIdSimpleIntegerProperty.get();
    }

    public SimpleIntegerProperty parterIdSimpleIntegerPropertyProperty() {
        return parterIdSimpleIntegerProperty;
    }

    public void setParterIdSimpleIntegerProperty(int parterIdSimpleIntegerProperty) {
        this.parterIdSimpleIntegerProperty.set(parterIdSimpleIntegerProperty);
    }

    public double getPartnerBoValueProperty() {
        return partnerBoValueProperty.get();
    }

    public SimpleDoubleProperty partnerBoValuePropertyProperty() {
        return partnerBoValueProperty;
    }

    public void setPartnerBoValueProperty(double partnerBoValueProperty) {
        this.partnerBoValueProperty.set(partnerBoValueProperty);
    }
}
