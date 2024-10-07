package md.program.modelFX;

import javafx.scene.control.TreeItem;
import md.program.database.model.BKAccount;
import md.program.database.repository.BKAccountPlanRepository;
import md.program.utils.converters.BKAccountConverter;

import java.sql.SQLException;
import java.util.List;

public class BKAccountPlanModel {

    private TreeItem<BKAccount> treeItemRoot = new TreeItem<>();
    private BKAccountPlanRepository bkAccountPlanRepository = new BKAccountPlanRepository();

    private BKAccount bkAccountEdit = new BKAccount();
    private BKAccountFX bkAccountFX = new BKAccountFX();
    public void init() throws SQLException {
        treeItemRoot.getChildren().clear();
        treeItemRoot.setValue(getheader());
        List<BKAccount> bkAccountList = bkAccountPlanRepository.getAccountPlanList(0);

        bkAccountList.forEach(e->{
            try {
                e.setAccountList(bkAccountPlanRepository.getAccountPlanList(e.getId()));
                e.getAccountList().forEach(f->{
                    try {
                        f.setAccountList(bkAccountPlanRepository.getAccountPlanList(f.getId()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });




        bkAccountList.forEach(e -> {
            treeItemRoot.getChildren().add(new TreeItem<>(e));
        });
        treeItemRoot.getChildren().forEach(e->{
            e.getValue().getAccountList().forEach(f->{
                e.getChildren().add(new TreeItem<>(f));
            });
        });

       treeItemRoot.getChildren().forEach(e->{
           e.getChildren().forEach(f->{
               f.getValue().getAccountList().forEach(g->{
                   f.getChildren().add(new TreeItem<>(g));
               });
           });
       });
    }

    public void convertEdit(){
        bkAccountFX= BKAccountConverter.convertToBKAccountFX(bkAccountEdit);
    }

    public BKAccount getheader() throws SQLException {
        return bkAccountPlanRepository.getAccountHeader();
    }

    public TreeItem<BKAccount> getTreeItemRoot() {
        return treeItemRoot;
    }

    public void setTreeItemRoot(TreeItem<BKAccount> treeItemRoot) {
        this.treeItemRoot = treeItemRoot;
    }

    public BKAccountPlanRepository getBkAccountPlanRepository() {
        return bkAccountPlanRepository;
    }

    public void setBkAccountPlanRepository(BKAccountPlanRepository bkAccountPlanRepository) {
        this.bkAccountPlanRepository = bkAccountPlanRepository;
    }

    public BKAccount getBkAccountEdit() {
        return bkAccountEdit;
    }

    public void setBkAccountEdit(BKAccount bkAccountEdit) {
        this.bkAccountEdit = bkAccountEdit;
    }

    public BKAccountFX getBkAccountFX() {
        return bkAccountFX;
    }

    public void setBkAccountFX(BKAccountFX bkAccountFX) {
        this.bkAccountFX = bkAccountFX;
    }

    public void saveEdit() throws SQLException {
        bkAccountPlanRepository.save(BKAccountConverter.convertToBKAccount(bkAccountFX));
    }

    public int delete() throws SQLException {
        if(bkAccountEdit.getId().equals(0)) return -1;
        if(bkAccountPlanRepository.checkChildren(bkAccountEdit)>0) return -2;

        bkAccountPlanRepository.delete(bkAccountEdit);
        return 10;
    }

    public int addnew() throws SQLException {
        BKAccount bkAccount = BKAccountConverter.convertToBKAccount(bkAccountFX);

        int level = checkLevel(bkAccount);
        bkAccountPlanRepository.insertNewAccount(new BKAccount(1, bkAccountEdit.getId(), bkAccount.getAccount(),bkAccount.getDescription()));
return 10;
    }

    private int checkLevel(BKAccount bkAccount) throws SQLException {
        int root=bkAccountPlanRepository.getRoot(bkAccount);
     //   if(root>0) checkLevel(bkAccountPlanRepository.getAccountById(int id));
        return 1;
    }
}
