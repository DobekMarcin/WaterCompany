package md.program.modelFX;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PaymentPlanFX {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty yearId = new SimpleIntegerProperty();
    private SimpleIntegerProperty partnerId = new SimpleIntegerProperty();
    private SimpleDoubleProperty m1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m2 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m3 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m4 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m5 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m6 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m7 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m8 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m9 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m10 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m11 = new SimpleDoubleProperty();
    private SimpleDoubleProperty m12 = new SimpleDoubleProperty();
    private SimpleObjectProperty<PartnerFX> partner = new SimpleObjectProperty<>();

    public PartnerFX getPartner() {
        return partner.get();
    }

    public SimpleObjectProperty<PartnerFX> partnerProperty() {
        return partner;
    }

    public void setPartner(PartnerFX partner) {
        this.partner.set(partner);
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

    public int getYearId() {
        return yearId.get();
    }

    public SimpleIntegerProperty yearIdProperty() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId.set(yearId);
    }

    public int getPartnerId() {
        return partnerId.get();
    }

    public SimpleIntegerProperty partnerIdProperty() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId.set(partnerId);
    }

    public double getM1() {
        return m1.get();
    }

    public SimpleDoubleProperty m1Property() {
        return m1;
    }

    public void setM1(double m1) {
        this.m1.set(m1);
    }

    public double getM2() {
        return m2.get();
    }

    public SimpleDoubleProperty m2Property() {
        return m2;
    }

    public void setM2(double m2) {
        this.m2.set(m2);
    }

    public double getM3() {
        return m3.get();
    }

    public SimpleDoubleProperty m3Property() {
        return m3;
    }

    public void setM3(double m3) {
        this.m3.set(m3);
    }

    public double getM4() {
        return m4.get();
    }

    public SimpleDoubleProperty m4Property() {
        return m4;
    }

    public void setM4(double m4) {
        this.m4.set(m4);
    }

    public double getM5() {
        return m5.get();
    }

    public SimpleDoubleProperty m5Property() {
        return m5;
    }

    public void setM5(double m5) {
        this.m5.set(m5);
    }

    public double getM6() {
        return m6.get();
    }

    public SimpleDoubleProperty m6Property() {
        return m6;
    }

    public void setM6(double m6) {
        this.m6.set(m6);
    }

    public double getM7() {
        return m7.get();
    }

    public SimpleDoubleProperty m7Property() {
        return m7;
    }

    public void setM7(double m7) {
        this.m7.set(m7);
    }

    public double getM8() {
        return m8.get();
    }

    public SimpleDoubleProperty m8Property() {
        return m8;
    }

    public void setM8(double m8) {
        this.m8.set(m8);
    }

    public double getM9() {
        return m9.get();
    }

    public SimpleDoubleProperty m9Property() {
        return m9;
    }

    public void setM9(double m9) {
        this.m9.set(m9);
    }

    public double getM10() {
        return m10.get();
    }

    public SimpleDoubleProperty m10Property() {
        return m10;
    }

    public void setM10(double m10) {
        this.m10.set(m10);
    }

    public double getM11() {
        return m11.get();
    }

    public SimpleDoubleProperty m11Property() {
        return m11;
    }

    public void setM11(double m11) {
        this.m11.set(m11);
    }

    public double getM12() {
        return m12.get();
    }

    public SimpleDoubleProperty m12Property() {
        return m12;
    }

    public void setM12(double m12) {
        this.m12.set(m12);
    }
}
