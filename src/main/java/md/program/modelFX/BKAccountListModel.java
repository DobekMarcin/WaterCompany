package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.BKAccount;
import md.program.database.repository.BKAccountPlanRepository;
import md.program.utils.converters.BKAccountConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BKAccountListModel {
    private ObservableList<BKAccountFX> bkAccountFXObservableList = FXCollections.observableArrayList();
    private BKAccountPlanRepository bkAccountPlanRepository = new BKAccountPlanRepository();
    private List<BKAccountFX> accountFXList = new ArrayList<>();
    private static final String JR_PRINT_ALL_PARTNER_PDF = "/JR_TEMPLATES/partnerList.jrxml";

    private BKAccountFX level0 = new BKAccountFX();
    private BKAccountFX level1 = new BKAccountFX();
    private BKAccountFX level2 = new BKAccountFX();

    private int level = 0;

    public void init() throws SQLException {
        List<BKAccount> allAccount = null;
        if (level == 0) {
            allAccount = bkAccountPlanRepository.getAllAccountLevel0();
        } else if (level == 1) {
            allAccount = bkAccountPlanRepository.getAllAccountLevel(level0);
        } else if (level == 2) {
            allAccount = bkAccountPlanRepository.getAllAccountLevel(level1);
        }
        accountFXList.clear();

        allAccount.forEach(item -> {
            BKAccountFX bkAccountFX = BKAccountConverter.convertToBKAccountFX(item);
            accountFXList.add(bkAccountFX);
        });
        bkAccountFXObservableList.setAll(accountFXList);
    }


    public ObservableList<BKAccountFX> getBkAccountFXObservableList() {
        return bkAccountFXObservableList;
    }

    public void setBkAccountFXObservableList(ObservableList<BKAccountFX> bkAccountFXObservableList) {
        this.bkAccountFXObservableList = bkAccountFXObservableList;
    }

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(bkAccountPlanRepository.getYearList());
    }

    public BKAccountFX getLevel0() {
        return level0;
    }

    public void setLevel0(BKAccountFX level0) {
        this.level0 = level0;
    }

    public BKAccountFX getLevel1() {
        return level1;
    }

    public void setLevel1(BKAccountFX level1) {
        this.level1 = level1;
    }

    public BKAccountFX getLevel2() {
        return level2;
    }

    public void setLevel2(BKAccountFX level2) {
        this.level2 = level2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        level0 = new BKAccountFX();
        level1 = new BKAccountFX();
        level2 = new BKAccountFX();
    }

    public Boolean checkBKPlanDefaultYear(Integer defaultYear) throws SQLException {
        return bkAccountPlanRepository.chceckIsDefaultYear(defaultYear) > 0 ? true : false;
    }
}
