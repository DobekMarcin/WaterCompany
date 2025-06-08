package md.program.utils.converters;

import md.program.database.model.BKAccount;
import md.program.database.model.Partner;
import md.program.modelFX.BKAccountFX;
import md.program.modelFX.PartnerFX;

public class BKAccountConverter {

    public static BKAccount convertToBKAccount(BKAccountFX bkAccountFX) {
        BKAccount bkAccount = new BKAccount();
        bkAccount.setId(bkAccountFX.getId());
        bkAccount.setRoot(bkAccountFX.getRoot());
        bkAccount.setAccount(bkAccountFX.getAccount());
        bkAccount.setDescription(bkAccountFX.getDescription());
        bkAccount.setSyn(bkAccountFX.synProperty().get());
        bkAccount.setFullName(bkAccountFX.getFullName());
        return bkAccount;
    }

    public static BKAccountFX convertToBKAccountFX(BKAccount bkAccount) {
        BKAccountFX bkAccountFX = new BKAccountFX();
        bkAccountFX.setId(bkAccount.getId());
        bkAccountFX.setRoot(bkAccount.getRoot());
        bkAccountFX.setAccount(bkAccount.getAccount());
        bkAccountFX.setDescription(bkAccount.getDescription());
        bkAccountFX.setSyn(bkAccount.getSyn());
        bkAccountFX.setFullName(bkAccount.getFullName());
        return bkAccountFX;
    }
}
