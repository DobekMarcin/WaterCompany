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

    public void init() throws SQLException {

        List<BKAccount> bkAccountList = bkAccountPlanRepository.getAccountPlanList(0);
        bkAccountList.forEach(e->treeItemRoot.getChildren().add(new TreeItem<>(e)));
        bkAccountList.forEach(e->System.out.println(e));

    }

    public BKAccount getheader() throws SQLException {
     return  bkAccountPlanRepository.getAccountHeader();

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
}
