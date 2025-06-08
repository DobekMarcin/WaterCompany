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
    private BKAccountFX bkAccountNewFX = new BKAccountFX();
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

    public int saveEdit() throws SQLException {
        bkAccountPlanRepository.save(BKAccountConverter.convertToBKAccount(bkAccountFX));
        return 1;
    }

    public int delete() throws SQLException {
        if(bkAccountEdit.getId().equals(0)) return -1;
        if(bkAccountPlanRepository.checkChildren(bkAccountEdit)>0) return -2;

        bkAccountPlanRepository.delete(bkAccountEdit);
        return 10;
    }

    public int addnew() throws SQLException {
            BKAccount bkAccountNew = BKAccountConverter.convertToBKAccount(bkAccountNewFX);
            bkAccountPlanRepository.insertNewAccount(new BKAccount(1, bkAccountEdit.getId(), bkAccountNew.getAccount(), bkAccountNew.getDescription(),bkAccountNew.getSyn(), bkAccountEdit.getId()==0? bkAccountNew.getAccount() : bkAccountEdit.getFullName()+" - "+bkAccountNew.getAccount()));
        return 1;
    }

    public int checkLevel( ) throws SQLException {
        int root=bkAccountPlanRepository.getRoot(bkAccountEdit);
        if(root == 0) return 1;

        int root2 = bkAccountPlanRepository.getRootById(root);
        if(root2 == 0)
            return 2;
        int root3 = bkAccountPlanRepository.getRootById(root2);
        if(root3 == 0)
            return 3;

        return -1;
    }

    public int getRoot() throws SQLException {
        int root=bkAccountPlanRepository.getRoot(bkAccountEdit);
        return root;
    }


    public BKAccountFX getBkAccountNewFX() {
        return bkAccountNewFX;
    }

    public void setBkAccountNewFX(BKAccountFX bkAccountNewFX) {
        this.bkAccountNewFX = bkAccountNewFX;
    }
    public void resetAccountNew(){
        bkAccountNewFX = new BKAccountFX();
    }

}
