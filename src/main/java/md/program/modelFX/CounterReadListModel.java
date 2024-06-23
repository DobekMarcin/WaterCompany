package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.CounterRead;
import md.program.database.model.PaymentPlan;
import md.program.database.repository.CounterReadRepository;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.utils.converters.CounterReadConverter;
import md.program.utils.converters.PaymentPlanConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CounterReadListModel {

    private ObservableList<CounterReadFX> counterReadFXObservableList = FXCollections.observableArrayList();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private List<CounterReadFX> counterReadFXList = new ArrayList<>();
    private SimpleStringProperty filter = new SimpleStringProperty();


    public void init(Integer planYear) throws SQLException {
        List<CounterRead> counterReadList = counterReadRepository.getAllCounterReadByYear(planYear);
        for (CounterRead counterRead : counterReadList) {
            counterRead.setPartner(partnerRepository.getPartnerById(counterRead.getPartnerId()));
        }
        counterReadFXList.clear();
        counterReadList.forEach(item->{
            CounterReadFX counterReadFX = CounterReadConverter.convertToCounterReadFX(item);
            counterReadFXList.add(counterReadFX);
        });
        counterReadFXObservableList.setAll(counterReadFXList);
        filterPaymentList();
    }

    public void clearList(){
        counterReadFXObservableList.clear();
    }

    public ObservableList<CounterReadFX> getCounterReadFXObservableList() {
        return counterReadFXObservableList;
    }

    public void setCounterReadFXObservableList(ObservableList<CounterReadFX> counterReadFXObservableList) {
        this.counterReadFXObservableList = counterReadFXObservableList;
    }

    public void filterPaymentList() {
        filterPredicate(predicateName().or(predicateSurname().or(predicateID())));
    }

    private void filterPredicate(Predicate<CounterReadFX> predicate) {
        List<CounterReadFX> newList = counterReadFXList.stream().filter(predicate).collect(Collectors.toList());
        counterReadFXObservableList.setAll(newList);
    }
    private Predicate<CounterReadFX> predicateName() {
        Predicate<CounterReadFX> predicate = paymentFX -> paymentFX.getPartner().getName().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CounterReadFX> predicateSurname() {
        Predicate<CounterReadFX> predicate = paymentFX -> paymentFX.getPartner().getSurname().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CounterReadFX> predicateID() {
        Predicate<CounterReadFX> predicate = bookFX -> String.valueOf(bookFX.getPartnerId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }

    public String getFilter() {
        return filter.get();
    }

    public SimpleStringProperty filterProperty() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter.set(filter);
    }

}
