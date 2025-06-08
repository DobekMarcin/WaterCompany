package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.BKAccount;
import md.program.database.model.BKPattern;
import md.program.database.repository.BKAccountPlanRepository;
import md.program.database.repository.PatternRepository;
import md.program.utils.converters.BKPatternConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatternModelList {
    private ObservableList<BKPatternFX> bkPatternFXObservableList = FXCollections.observableArrayList();
    private PatternRepository patternRepository = new PatternRepository();
    private BKAccountPlanRepository bkAccountPlanRepository = new BKAccountPlanRepository();
    private List<BKPatternFX> bkPatternFXES = new ArrayList<>();

private BKPatternFX deletePattern = new BKPatternFX();
private BKPatternFX addPattern = new BKPatternFX();
private BKPatternFX editPatter = new BKPatternFX();

    public void init() throws SQLException {
        List<BKPattern> counterReadList = patternRepository.getPatternList(1);

        bkPatternFXES.clear();
        for (BKPattern item : counterReadList) {
            BKPatternFX bkPatternFX = BKPatternConverter.convertToBKPatternFX(item);
            bkPatternFXES.add(bkPatternFX);
        }
        bkPatternFXObservableList.setAll(bkPatternFXES);
    }

    public void clearList(){
        bkPatternFXObservableList.clear();
    }

    public ObservableList<BKPatternFX> getBkPatternFXObservableList() {
        return bkPatternFXObservableList;
    }

    public void setBkPatternFXObservableList(ObservableList<BKPatternFX> bkPatternFXObservableList) {
        this.bkPatternFXObservableList = bkPatternFXObservableList;
    }

public Boolean deletePattern() throws SQLException {
        patternRepository.deletePattern(BKPatternConverter.convertToBKPattern(deletePattern));
        return true;
}

    public BKPatternFX getDeletePattern() {
        return deletePattern;
    }

    public void setDeletePattern(BKPatternFX deletePattern) {
        this.deletePattern = deletePattern;
    }

    public BKPatternFX getAddPattern() {
        return addPattern;
    }

    public void setAddPattern(BKPatternFX addPattern) {
        this.addPattern = addPattern;
    }

    public void addNewInvoicePattern() throws SQLException {
        //    addPattern.setId_pattern(1);
        patternRepository.addPattern(BKPatternConverter.convertToBKPattern(addPattern));
    }

    public BKPatternFX getEditPatter() {
        return editPatter;
    }

    public void setEditPatter(BKPatternFX editPatter) {
        this.editPatter = editPatter;
    }

    public void updatePattern() throws SQLException {
        patternRepository.updatePattern(BKPatternConverter.convertToBKPattern(editPatter));
    }

    public String getFullAccountName(Integer accountId) throws SQLException {
BKAccount level1= bkAccountPlanRepository.getOneAccount(accountId);
BKAccount level2= new BKAccount();
BKAccount level3=new BKAccount();
if(level1.getRoot()>0){

}
        return "";
    }
}
