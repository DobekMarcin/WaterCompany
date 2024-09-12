package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.CounterYear;
import md.program.database.repository.CounterCompanyYearRepository;
import md.program.utils.converters.CounterYearConverter;

import java.sql.SQLException;
import java.util.List;

public class CounterCompanyYearListModel {

    private ObservableList<CounterYearFX> counterYearFXObservableList = FXCollections.observableArrayList();
    private CounterCompanyYearRepository counterCompanyYearRepository = new CounterCompanyYearRepository();

    public void init() throws SQLException {
    List<CounterYear> counterYearList = counterCompanyYearRepository.getAllCounterYear();
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
