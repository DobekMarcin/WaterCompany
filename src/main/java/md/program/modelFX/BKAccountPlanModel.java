package md.program.modelFX;

import javafx.scene.control.TreeItem;
import md.program.database.model.BKAccount;
import md.program.database.model.BKAccountYear;
import md.program.database.repository.BKAccountPlanRepository;
import md.program.utils.converters.BKAccountConverter;
import md.program.utils.converters.BKAccountYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BKAccountPlanModel {

    private BKAccountPlanRepository bkAccountPlanRepository = new BKAccountPlanRepository();
    private BKAccount bkAccountEdit = new BKAccount();
    private BKAccountFX bkAccountNewFX = new BKAccountFX();
    private BKAccountFX bkAccountFX = new BKAccountFX();
    private ArrayList<BKAccount> allAccount;

    public void init() throws SQLException {
        allAccount = bkAccountPlanRepository.getAllAccount();
    }

    public TreeItem<BKAccountFX> buildTree() {
        if (allAccount == null) return new TreeItem<>(new BKAccountFX());

        Map<Integer, TreeItem<BKAccountFX>> itemMap = new HashMap<>();
        TreeItem<BKAccountFX> rootNode = new TreeItem<>(new BKAccountFX());

        // 1. Konwersja i tworzenie mapy TreeItemów
        for (BKAccount acc : allAccount) {
            // Tu używamy konwertera (musisz go mieć lub przepisać pola ręcznie)
            BKAccountFX fxObject = BKAccountConverter.convertToBKAccountFX(acc);
            itemMap.put(fxObject.getId(), new TreeItem<>(fxObject));
        }

        // 2. Budowanie struktury
        for (TreeItem<BKAccountFX> currentItem : itemMap.values()) {
            int parentId = currentItem.getValue().getRoot();
            if (parentId == 0) {
                rootNode.getChildren().add(currentItem);
            } else {
                TreeItem<BKAccountFX> parentItem = itemMap.get(parentId);
                if (parentItem != null) {
                    parentItem.getChildren().add(currentItem);
                }
            }
        }
        return rootNode;
    }

    public void convertEdit() {
        bkAccountFX = BKAccountConverter.convertToBKAccountFX(bkAccountEdit);
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

    public int saveEdit() throws SQLException {
        bkAccountPlanRepository.save(BKAccountConverter.convertToBKAccount(bkAccountFX));
        return 1;
    }

    public int delete() throws SQLException {
        if (bkAccountEdit.getId().equals(0)) return -1;
        if (bkAccountPlanRepository.checkChildren(bkAccountEdit) > 0) return -2;

        bkAccountPlanRepository.delete(bkAccountEdit);
        return 10;
    }

    public int addnew() throws SQLException {
        BKAccount bkAccountNew = BKAccountConverter.convertToBKAccount(bkAccountNewFX);
        bkAccountPlanRepository.insertNewAccount(new BKAccount(1, bkAccountEdit.getId(), bkAccountNew.getAccount(), bkAccountNew.getDescription(), bkAccountNew.getSyn(), bkAccountEdit.getId() == 0 ? bkAccountNew.getAccount() : bkAccountEdit.getFullName() + " - " + bkAccountNew.getAccount()));
        return 1;
    }


    public int getRoot() throws SQLException {
        int root = bkAccountPlanRepository.getRoot(bkAccountEdit);
        return root;
    }


    public BKAccountFX getBkAccountNewFX() {
        return bkAccountNewFX;
    }

    public void resetAccountNew() {
        bkAccountNewFX = new BKAccountFX();
    }

}
