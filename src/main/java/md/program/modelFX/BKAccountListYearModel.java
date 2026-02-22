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
    private List<BKAccountYearFX> accountYearFXList = new ArrayList<>();
    private static final String JR_PRINT_ALL_PARTNER_PDF = "/JR_TEMPLATES/partnerList.jrxml";

    private BKAccountYearFX level0 = new BKAccountYearFX();
    private BKAccountYearFX level1 = new BKAccountYearFX();
    private BKAccountYearFX level2 = new BKAccountYearFX();

    private int level = 0;
    private int year;

    List<BKAccountYear> allAccount = null;

    public void init() throws SQLException {


        allAccount = bkAccountPlanYearRepository.getAllAccount(year);



//        List<BKAccountYear> allAccount = null;
//        if (level == 0) {
//            allAccount = bkAccountPlanYearRepository.getAllAccountLevel0(year);
//        } else if (level == 1) {
//            allAccount = bkAccountPlanYearRepository.getAllAccountLevel(BKAccountYearConverter.convertToBKAccountYear(level0),year);
//        } else if (level == 2) {
//            allAccount = bkAccountPlanYearRepository.getAllAccountLevel(BKAccountYearConverter.convertToBKAccountYear(level1),year);
//        }
//        accountYearFXList.clear();
//
//        allAccount.forEach(item -> {
//            BKAccountYearFX bkAccountYearFX = BKAccountYearConverter.convertToBKAccountYearFX(item);
//            accountYearFXList.add(bkAccountYearFX);
//        });
//        bkAccountYearFXObservableList.setAll(accountYearFXList);
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

    public BKAccountYearFX getLevel0() {
        return level0;
    }

    public void setLevel0(BKAccountYearFX level0) {
        this.level0 = level0;
    }

    public BKAccountYearFX getLevel1() {
        return level1;
    }

    public void setLevel1(BKAccountYearFX level1) {
        this.level1 = level1;
    }

    public BKAccountYearFX getLevel2() {
        return level2;
    }

    public void setLevel2(BKAccountYearFX level2) {
        this.level2 = level2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //    public void printPartnerList() throws JRException, SQLException {
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(partnerFXObservableList.stream().toList());
//
//        InputStream file = getClass().getResourceAsStream(JR_PRINT_ALL_PARTNER_PDF);
//        JasperDesign jasperDesign = JRXmlLoader.load(file);
//
//       // String filepath2= getClass().getResource(JR_PRINT_ALL_PARTNER_PDF).getPath();
//        Map<String,Object> parameters = new HashMap<>();
//
//        parameters.put("TestDataSet",jrBeanCollectionDataSource);
//        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
//        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());
//
//        JasperViewer jv=new JasperViewer(print,false);
//        jv.setTitle("Challan");
//        jv.setVisible(true);
//    }

    public void resetLevels() {
        level0 = new BKAccountYearFX();
        level1 = new BKAccountYearFX();
        level2 = new BKAccountYearFX();
    }

    public Boolean checkBKPlanDefaultYear(Integer defaultYear) throws SQLException {
        return bkYearRepository.chceckIsDefaultYear(defaultYear) > 0 ? true : false;
    }


    public TreeItem<BKAccountYear> buildTree() {

        if (allAccount == null) {
            return new TreeItem<>(new BKAccountYear());
        }

        Map<Integer, TreeItem<BKAccountYear>> itemMap = new HashMap<>();

        // Ukryty korzeń całego drzewa
        TreeItem<BKAccountYear> rootNode = new TreeItem<>(new BKAccountYear());

        // 1. Tworzymy TreeItem dla każdego konta
        for (BKAccountYear acc : allAccount) {
            itemMap.put(acc.getId(), new TreeItem<>(acc));
        }

        // 2. Łączymy dzieci z rodzicami
        for (BKAccountYear acc : allAccount) {
            TreeItem<BKAccountYear> currentItem = itemMap.get(acc.getId());
            int parentId = acc.getRoot();

            if (parentId == 0) {
                // Jeśli root=0, to jest to konto główne - dodaj do korzenia
                rootNode.getChildren().add(currentItem);
            } else {
                // Jeśli ma rodzica, znajdź go w mapie i przypisz dziecko
                TreeItem<BKAccountYear> parentItem = itemMap.get(parentId);
                if (parentItem != null) {
                    parentItem.getChildren().add(currentItem);
                }
            }
        }
        return rootNode;
    }
}
