package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.BKAccount;
import md.program.database.model.BKYear;
import md.program.database.repository.BKAccountPlanRepository;
import md.program.database.repository.BKAccountPlanYearRepository;
import md.program.database.repository.BKYearRepository;
import md.program.utils.converters.BKAccountConverter;
import md.program.utils.converters.BKYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BKYearListModel {

    private ObservableList<BKYearFX> bkYearFXES = FXCollections.observableArrayList();
    private BKYearRepository bkYearRepository = new BKYearRepository();
    private BKAccountPlanYearRepository bkAccountPlanYearRepository = new BKAccountPlanYearRepository();
    private BKAccountPlanRepository bkAccountPlanRepository = new BKAccountPlanRepository();
    private List<BKYearFX> bkYearFXList = new ArrayList<>();
    private BKYearFX deleteYearFX = new BKYearFX();
    private BKYearFX addnewYear = new BKYearFX();


    public void init() throws SQLException {
        List<BKYear> allYear = bkYearRepository.getAllBKYear();
        bkYearFXList.clear();

        allYear.forEach(item -> {
            BKYearFX bkYearFX = BKYearConverter.convertToRateYearFX(item);
            bkYearFXList.add(bkYearFX);
        });
        bkYearFXES.setAll(bkYearFXList);
    }

    public void deleteYear() throws SQLException {
        bkYearRepository.deleteBKYear(BKYearConverter.convertToBKYear(deleteYearFX));
    }

    public int addYear() throws SQLException {
        BKYear bkYear = BKYearConverter.convertToBKYear(addnewYear);

        if(bkYearRepository.existsByYear(bkYear.getYear())) {
            return -1;
        }else {
            bkYearRepository.addYear(bkYear);
            List<BKAccount> bkAccountList = bkAccountPlanRepository.getAllAccount();
            bkAccountPlanYearRepository.insertNewAccount(bkAccountList,bkYear.getYear());
            return 1;
        }
    }


    public ObservableList<BKYearFX> getBkYearFXES() {
        return bkYearFXES;
    }

    public void setBkYearFXES(ObservableList<BKYearFX> bkYearFXES) {
        this.bkYearFXES = bkYearFXES;
    }

    public BKYearFX getDeleteYearFX() {
        return deleteYearFX;
    }

    public void setDeleteYearFX(BKYearFX deleteYearFX) {
        this.deleteYearFX = deleteYearFX;
    }

    public BKYearFX getAddnewYear() {
        return addnewYear;
    }

    public void setAddnewYear(BKYearFX addnewYear) {
        this.addnewYear = addnewYear;
    }
}
