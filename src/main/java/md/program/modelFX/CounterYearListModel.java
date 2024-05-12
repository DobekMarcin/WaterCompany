package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.CounterYear;
import md.program.database.model.RateYear;
import md.program.database.repository.CounterYearRepository;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.CounterYearConverter;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.List;

public class CounterYearListModel {

    private ObservableList<CounterYearFX> counterYearFXObservableList = FXCollections.observableArrayList();
    private CounterYearRepository counterYearRepository = new CounterYearRepository();

    public void init() throws SQLException {
    List<CounterYear> counterYearList = counterYearRepository.getAllCounterYear();
        counterYearFXObservableList.clear();
    counterYearList.forEach(item->{
        CounterYearFX counterYearFX = CounterYearConverter.convertToCounterYearFX(item);
        counterYearFXObservableList.add(counterYearFX);
    });
    }

    public ObservableList<CounterYearFX> getCounterYearFXObservableList() {
        return counterYearFXObservableList;
    }

    public void setCounterYearFXObservableList(ObservableList<CounterYearFX> counterYearFXObservableList) {
        this.counterYearFXObservableList = counterYearFXObservableList;
    }
}
