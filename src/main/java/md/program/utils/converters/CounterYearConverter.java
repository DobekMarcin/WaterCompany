package md.program.utils.converters;

import md.program.database.model.CounterYear;
import md.program.database.model.RateYear;
import md.program.modelFX.CounterYearFX;
import md.program.modelFX.RateYearFX;

public class CounterYearConverter {

    public static CounterYear convertToCounterYear(CounterYearFX counterYearFX){
        CounterYear counterYear = new CounterYear();
        counterYear.setId(counterYearFX.getId());
        counterYear.setYear(counterYearFX.getYear());
        counterYear.setCounterRate(counterYearFX.getRate());
        return counterYear;
    }

    public static CounterYearFX convertToCounterYearFX(CounterYear counterYear){
        CounterYearFX counterYearFX = new CounterYearFX();
        counterYearFX.setId(counterYear.getId());
        counterYearFX.setYear(counterYear.getYear());
        counterYearFX.setRate(counterYear.getCounterRate());
        return counterYearFX;
    }
}
