package md.program.utils.converters;

import md.program.database.model.BKAccount;
import md.program.database.model.BKAccountYear;
import md.program.modelFX.BKAccountFX;
import md.program.modelFX.BKAccountYearFX;

public class BKAccountYearConverter {

    public static BKAccountYear convertToBKAccountYear(BKAccountYearFX bkAccountYearFX) {
        BKAccountYear bkAccount = new BKAccountYear();
        bkAccount.setId(bkAccountYearFX.getId());
        bkAccount.setYear(bkAccountYearFX.getYear());
        bkAccount.setRoot(bkAccountYearFX.getRoot());
        bkAccount.setAccount(bkAccountYearFX.getAccount());
        bkAccount.setDescription(bkAccountYearFX.getDescription());
        bkAccount.setSyn(bkAccountYearFX.synProperty().get());
        bkAccount.setFullName(bkAccountYearFX.getFullName());
        bkAccount.setCredit(bkAccountYearFX.getCredit());
        bkAccount.setDebit((bkAccountYearFX.getDebit()));
        return bkAccount;
    }

    public static BKAccountYearFX convertToBKAccountYearFX(BKAccountYear bkAccount) {
        if (bkAccount == null) return null;
        BKAccountYearFX fxModel = new BKAccountYearFX();
        fxModel.setId(bkAccount.getId());
        fxModel.setRoot(bkAccount.getRoot());
        fxModel.setYear(bkAccount.getYear());
        fxModel.setAccount(bkAccount.getAccount());
        fxModel.setDescription(bkAccount.getDescription());
        fxModel.setSyn(bkAccount.getSyn());
        fxModel.setFullName(bkAccount.getFullName());
        fxModel.setCredit(bkAccount.getCredit());
        fxModel.setDebit(bkAccount.getDebit());

        return fxModel;
    }
}
