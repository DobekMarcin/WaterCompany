package md.program.modelFX;

import md.program.database.model.RateYear;
import md.program.database.repository.PaymentPlanRepository;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;

public class RateYearModel {

    private RateYearFX rateYearFX = new RateYearFX();

    RateYearRepository rateYearRepository = new RateYearRepository();

    public void addRateYear() throws SQLException {
        RateYear temp = RateYearConverter.convertToRateYear(rateYearFX);
        temp.setRate(Math.round(temp.getRate() * 100.0) / 100.0);
        rateYearRepository.addRateYear(temp);
    }

    public Boolean deleteRateYear() throws SQLException {
        RateYear temp = RateYearConverter.convertToRateYear(rateYearFX);
            rateYearRepository.deleteRateYearById(RateYearConverter.convertToRateYear(rateYearFX));
            return true;
    }

    public RateYearFX getRateYearFX() {
        return rateYearFX;
    }

    public void setRateYearFX(RateYearFX rateYearFX) {
        this.rateYearFX = rateYearFX;
    }

    public Boolean valid() {
        if ((Math.round((rateYearFX.getRate() * 100.0) / 100.0)) == 0) return false;
        if (rateYearFX.getYear() == 0) return false;
        return true;
    }
    public void initYear() throws SQLException {
        rateYearFX.setYear(rateYearRepository.getNextYear());
    }
}
