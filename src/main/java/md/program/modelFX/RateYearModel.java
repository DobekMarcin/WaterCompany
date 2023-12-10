package md.program.modelFX;

import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;

public class RateYearModel {

    private RateYearFX rateYearFX = new RateYearFX();

    RateYearRepository rateYearRepository = new RateYearRepository();

    public void addRateYear() throws SQLException {
        rateYearRepository.addRateYear(RateYearConverter.convertToRateYear(rateYearFX));
    }
    public void deleteRateYear() throws SQLException {
        rateYearRepository.deleteRateYearById(RateYearConverter.convertToRateYear(rateYearFX));
    }

    public RateYearFX getRateYearFX() {
        return rateYearFX;
    }

    public void setRateYearFX(RateYearFX rateYearFX) {
        this.rateYearFX = rateYearFX;
    }

    public void updateRateYearGeneratedStatus() throws SQLException {
        rateYearRepository.updateRateYearGeneratedStatus(RateYearConverter.convertToRateYear(rateYearFX));
    }
}
