package md.program.utils.converters;

import md.program.database.model.BKPattern;
import md.program.database.model.CounterYear;
import md.program.modelFX.BKPatternFX;
import md.program.modelFX.CounterYearFX;

public class BKPatternConverter {

    public static BKPattern convertToBKPattern(BKPatternFX bkPatternFX){
        BKPattern bkPattern = new BKPattern();
        bkPattern.setId_pattern(bkPatternFX.getId_pattern());
        bkPattern.setDes_pattenr(bkPatternFX.getDes_pattern());
        bkPattern.setBank(bkPatternFX.isBank());
        bkPattern.setCash(bkPatternFX.isCash());
        bkPattern.setCredit(bkPatternFX.getCredit());
        bkPattern.setDebit(bkPatternFX.getDebit());
        bkPattern.setCreditText(bkPatternFX.getCreditText());
        bkPattern.setInvoice(bkPatternFX.isInvoice());
        bkPattern.setDebitText(bkPatternFX.getDebitText());
        return bkPattern;
    }

    public static BKPatternFX convertToBKPatternFX(BKPattern bkPattern){
        BKPatternFX bkPatternFX = new BKPatternFX();
        bkPatternFX.setId_pattern(bkPattern.getId_pattern());
        bkPatternFX.setDes_pattern(bkPattern.getDes_pattenr());
        bkPatternFX.setBank(bkPattern.getBank());
        bkPatternFX.setCash(bkPattern.getCash());
        bkPatternFX.setCredit(bkPattern.getCredit());
        bkPatternFX.setDebit(bkPattern.getCredit());
        bkPatternFX.setCreditText(bkPattern.getCreditText());
        bkPatternFX.setInvoice(bkPattern.getInvoice());
        bkPatternFX.setDebitText(bkPattern.getDebitText());
        return  bkPatternFX;
    }
}
