package md.program.modelFX;

import md.program.database.model.CounterYear;
import md.program.database.repository.CounterCompanyYearRepository;
import md.program.utils.converters.CounterYearConverter;

import java.sql.SQLException;

public class CounterCompanyYearModel {

    private CounterYearFX counterYearFX = new CounterYearFX();

    CounterCompanyYearRepository counterCompanyYearRepository = new CounterCompanyYearRepository();

    public void addCounterYear() throws SQLException {
        CounterYear temp = CounterYearConverter.convertToCounterYear(counterYearFX);
        temp.setCounterRate(Math.round(temp.getCounterRate()*100.0)/100.0);
        counterCompanyYearRepository.addRateYear(temp);
    }

    public Boolean deleteCounterYear() throws SQLException {
        CounterYear temp = CounterYearConverter.convertToCounterYear(counterYearFX);
        counterCompanyYearRepository.deleteRateYearById(temp);
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
        counterYearFX.setYear(counterCompanyYearRepository.getNextYear());
    }
}
