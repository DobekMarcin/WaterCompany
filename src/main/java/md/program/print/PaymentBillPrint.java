package md.program.print;

public class PaymentBillPrint {
    private String name;
    private String surname;
    private String address;
    private Double kw1;
    private Double kw2;
    private Double kw3;
    private Double kw4;

    public PaymentBillPrint(String name, String surname, String address, Double kw1, Double kw2, Double kw3, Double kw4) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.kw1 = kw1;
        this.kw2 = kw2;
        this.kw3 = kw3;
        this.kw4 = kw4;
    }

    public PaymentBillPrint() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getKw1() {
        return kw1;
    }

    public void setKw1(Double kw1) {
        this.kw1 = kw1;
    }

    public Double getKw2() {
        return kw2;
    }

    public void setKw2(Double kw2) {
        this.kw2 = kw2;
    }

    public Double getKw3() {
        return kw3;
    }

    public void setKw3(Double kw3) {
        this.kw3 = kw3;
    }

    public Double getKw4() {
        return kw4;
    }

    public void setKw4(Double kw4) {
        this.kw4 = kw4;
    }
}
