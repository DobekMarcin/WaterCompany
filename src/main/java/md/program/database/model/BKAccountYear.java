package md.program.database.model;

import java.util.List;

public class BKAccountYear {

    private Integer id = 0;
    private Integer year;
    private Integer root= 0;
    private String account="";
    private String description="";
    private Boolean syn;
    private String fullName="";
    private Double credit;
    private Double debit;

    public BKAccountYear() {
    this.credit = 0.0;
    this.debit = 0.0;
    }

    public BKAccountYear(Integer id, Integer year, Integer root, String account, String description, Boolean syn, String fullName, Double credit, Double debit) {
        this.id = id;
        this.year = year;
        this.root = root;
        this.account = account;
        this.description = description;
        this.syn = syn;
        this.fullName = fullName;
        this.credit = credit;
        this.debit = debit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(Integer root) {
        this.root = root;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Boolean getSyn() {
        return syn;
    }

    public void setSyn(Boolean syn) {
        this.syn = syn;
    }

    @Override
    public String toString() {
        return "BKAccountYear{" +
                "id=" + id +
                ", year=" + year +
                ", root=" + root +
                ", account='" + account + '\'' +
                ", description='" + description + '\'' +
                ", syn=" + syn +
                ", fullName='" + fullName + '\'' +
                ", credit=" + credit +
                ", debit=" + debit +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
