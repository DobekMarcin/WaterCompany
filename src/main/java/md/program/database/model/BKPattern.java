package md.program.database.model;

public class BKPattern {

    private int id_pattern;
    private String des_pattenr;
    private int debit;
    private String debitText;
    private int credit;
    private String creditText;
    private Boolean invoice;
    private Boolean bank;
    private Boolean cash;

    public int getId_pattern() {
        return id_pattern;
    }

    public void setId_pattern(int id_pattern) {
        this.id_pattern = id_pattern;
    }

    public String getDes_pattenr() {
        return des_pattenr;
    }

    public void setDes_pattenr(String des_pattenr) {
        this.des_pattenr = des_pattenr;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public String getDebitText() {
        return debitText;
    }

    public void setDebitText(String debitText) {
        this.debitText = debitText;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getCreditText() {
        return creditText;
    }

    public void setCreditText(String creditText) {
        this.creditText = creditText;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public void setInvoice(Boolean invoice) {
        this.invoice = invoice;
    }

    public Boolean getBank() {
        return bank;
    }

    public void setBank(Boolean bank) {
        this.bank = bank;
    }

    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
    }
}
