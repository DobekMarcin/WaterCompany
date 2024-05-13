package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.repository.CounterReadRepository;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.database.repository.RateYearRepository;

import java.sql.SQLException;

public class CounterReadModel {

    private CounterReadFX counterReadFX = new CounterReadFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private RateYearRepository rateYearRepository = new RateYearRepository();

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(counterReadRepository.getYearList());
    }

    public void generateCounterYear(Integer year) {
    }
}
