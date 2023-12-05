package md.program.utils.converters;

import md.program.database.model.RateYear;
import md.program.modelFX.RateYearFX;

public class RateYearConverter {

    public static RateYear convertToRateYear(RateYearFX rateYearFX){
        RateYear rateYear = new RateYear();
        rateYear.setId(rateYearFX.getId());
        rateYear.setYear(rateYearFX.getYear());
        rateYear.setRate(rateYearFX.getRate());
        return rateYear;
    }

    public static RateYearFX convertToRateYearFX(RateYear rateYear){
        RateYearFX rateYearFX = new RateYearFX();
        rateYearFX.setId(rateYear.getId());
        rateYearFX.setYear(rateYear.getYear());
        rateYearFX.setRate(rateYear.getRate());
        return rateYearFX;
    }
}
