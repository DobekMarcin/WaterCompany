package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import md.program.database.model.BKAccount;
import md.program.database.model.BKAccountYear;
import md.program.database.repository.BKAccountPlanYearRepository;
import md.program.database.repository.BKYearRepository;
import md.program.utils.converters.BKAccountConverter;
import md.program.utils.converters.BKAccountYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BKAccountListYearModel {


    private ObservableList<BKAccountYearFX> bkAccountYearFXObservableList = FXCollections.observableArrayList();
    private BKYearRepository bkYearRepository = new BKYearRepository();
    private BKAccountPlanYearRepository bkAccountPlanYearRepository = new BKAccountPlanYearRepository();

    private int year;

    List<BKAccountYear> allAccount = null;

    public void init() throws SQLException {
        allAccount = bkAccountPlanYearRepository.getAllAccount(year);
    }


    public ObservableList<BKAccountYearFX> getBkAccountYearFXObservableList() {
        return bkAccountYearFXObservableList;
    }

    public void setBkAccountYearFXObservableList(ObservableList<BKAccountYearFX> bkAccountYearFXObservableList) {
        this.bkAccountYearFXObservableList = bkAccountYearFXObservableList;
    }

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(bkYearRepository.getYearList());
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean checkBKPlanDefaultYear(Integer defaultYear) throws SQLException {
        return bkYearRepository.chceckIsDefaultYear(defaultYear) > 0 ? true : false;
    }


    public TreeItem<BKAccountYearFX> buildTree() {
        if (allAccount == null) return new TreeItem<>(new BKAccountYearFX());

        Map<Integer, TreeItem<BKAccountYearFX>> itemMap = new HashMap<>();
        TreeItem<BKAccountYearFX> rootNode = new TreeItem<>(new BKAccountYearFX());

        // 1. Konwersja i tworzenie mapy TreeItemów
        for (BKAccountYear acc : allAccount) {
            // Tu używamy konwertera (musisz go mieć lub przepisać pola ręcznie)
            BKAccountYearFX fxObject = BKAccountYearConverter.convertToBKAccountYearFX(acc);
            itemMap.put(fxObject.getId(), new TreeItem<>(fxObject));
        }

        // 2. Budowanie struktury
        for (TreeItem<BKAccountYearFX> currentItem : itemMap.values()) {
            int parentId = currentItem.getValue().getRoot();
            if (parentId == 0) {
                rootNode.getChildren().add(currentItem);
            } else {
                TreeItem<BKAccountYearFX> parentItem = itemMap.get(parentId);
                if (parentItem != null) {
                    parentItem.getChildren().add(currentItem);
                }
            }
        }
        return rootNode;
    }

    public void insertNewAccount(BKAccountYear bkAccountYear) throws SQLException {
        ArrayList list = new ArrayList();
        list.add(bkAccountYear);
        bkAccountPlanYearRepository.insertNewAccount(list, bkAccountYear.getYear());
    }

    public void deleteById(BKAccountYearFX bkAccountFX) throws SQLException {
        bkAccountPlanYearRepository.deleteByID(bkAccountFX.getId());
    }

    public boolean hasChildren(BKAccountYearFX bkAccountYearFX) throws SQLException {
    return bkAccountPlanYearRepository.hasChildren(bkAccountYearFX.getId());
    }

}
