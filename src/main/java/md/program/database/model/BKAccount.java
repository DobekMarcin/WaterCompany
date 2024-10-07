package md.program.database.model;

import md.program.modelFX.BKAccountPlanModel;

import java.util.List;

public class BKAccount {

    private Integer id = 0;
    private Integer root= 0;
    private String account="";
    private String description="";
    private List<BKAccount> accountList;

    public BKAccount() {
    }

    public BKAccount(Integer id, Integer root, String account, String description) {
        this.id = id;
        this.root = root;
        this.account = account;
        this.description = description;
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

    public List<BKAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<BKAccount> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return account +" - "+ description;
    }
}
