package md.program.utils.converters;

import md.program.database.model.BKYear;
import md.program.database.model.RateYear;
import md.program.modelFX.BKYearFX;
import md.program.modelFX.RateYearFX;

public class BKYearConverter {

    public static BKYear convertToBKYear(BKYearFX bkYearFX){
        BKYear bkYear = new BKYear();
        bkYear.setId(bkYearFX.getId());
        bkYear.setYear(bkYearFX.getYear());
        return bkYear;
    }

    public static BKYearFX convertToRateYearFX(BKYear bkYear){
        BKYearFX bkYearFX = new BKYearFX();
        bkYearFX.setId(bkYear.getId());
        bkYearFX.setYear(bkYear.getYear());

        return bkYearFX;
    }
}
