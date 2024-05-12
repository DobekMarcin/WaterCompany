package md.program.modelFX;

import md.program.database.model.CounterYear;
import md.program.database.model.RateYear;
import md.program.database.repository.CounterYearRepository;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.CounterYearConverter;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;

public class CounterYearModel {

    private CounterYearFX counterYearFX = new CounterYearFX();

    CounterYearRepository counterYearRepository = new CounterYearRepository();

    public void addCounterYear() throws SQLException {
        CounterYear temp = CounterYearConverter.convertToCounterYear(counterYearFX);
        temp.setCounterRate(Math.round(temp.getCounterRate()*100.0)/100.0);
        counterYearRepository.addRateYear(temp);
    }

    public Boolean deleteCounterYear() throws SQLException {
        CounterYear temp = CounterYearConverter.convertToCounterYear(counterYearFX);
        counterYearRepository.deleteRateYearById(temp);
        return true;
    }

    public CounterYearFX getCounterYearFX() {
        return counterYearFX;
    }

    public void setCounterYearFX(CounterYearFX counterYearFX) {
        this.counterYearFX = counterYearFX;
    }

    public Boolean valid() {
        if ((Math.round((counterYearFX.getRate() * 100.0) / 100.0)) == 0) return false;
        if (counterYearFX.getYear() == 0) return false;
        return true;
    }
    public void initYear() throws SQLException {
        counterYearFX.setYear(counterYearRepository.getNextYear());
    }
}
