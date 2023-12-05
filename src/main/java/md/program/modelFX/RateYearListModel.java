package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.RateYear;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.List;

public class RateYearListModel {

    private ObservableList<RateYearFX> rateYearFXObservableList = FXCollections.observableArrayList();
    private RateYearRepository rateYearRepository = new RateYearRepository();

    public void init() throws SQLException {
    List<RateYear> rateYearList = rateYearRepository.getAllRateYear();

    rateYearList.forEach(item->{
        RateYearFX rateYearFX = RateYearConverter.convertToRateYearFX(item);
        rateYearFXObservableList.add(rateYearFX);
    });
    }

    public ObservableList<RateYearFX> getRateYearFXObservableList() {
        return rateYearFXObservableList;
    }

    public void setRateYearFXObservableList(ObservableList<RateYearFX> rateYearFXObservableList) {
        this.rateYearFXObservableList = rateYearFXObservableList;
    }
}
