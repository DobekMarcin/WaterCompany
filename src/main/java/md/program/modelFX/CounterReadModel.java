package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.*;
import md.program.database.repository.*;
import md.program.utils.converters.CounterReadConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CounterReadModel {

    private CounterReadFX counterReadFX = new CounterReadFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private SimpleBooleanProperty onlyNewUser = new SimpleBooleanProperty();

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(counterReadRepository.getYearList());
    }

    public void generateCounterYear(Integer year) throws SQLException {


        List<Partner> partnerList = null;
        if (onlyNewUser.get()) {
            partnerList = new ArrayList<>();
            List<Partner> newPartnerList = null;
            newPartnerList = partnerRepository.getCounterPartner();
            for(Partner item :newPartnerList){
                Boolean newCounterRead = counterReadRepository.checkCounterRead(year, item.getId());
                if(!newCounterRead) partnerList.add(item);
            }
        } else {
            counterReadRepository.deleteCounterReadByYear(year);
            partnerList = partnerRepository.getCounterPartner();
        }
        List<CounterRead> counterReadList = new ArrayList<>();
        partnerList.forEach(item -> {
            CounterRead temp = generateCounterYearPlan(year, item);
            counterReadList.add(temp);
        });
        for (CounterRead item : counterReadList) {
            counterReadRepository.addCounterRead(item);
        }
    }

    private static CounterRead generateCounterYearPlan(Integer generateYear, Partner item) {
        CounterRead temp = new CounterRead();
        temp.setPartnerId(item.getId());
        temp.setYearId(generateYear);

        return temp;
    }
    public Boolean chceckCounterReadByDefayultYear(Integer defaultYear) throws SQLException {
        return counterReadRepository.chceckIsCounterReadByDefaultYear(defaultYear) > 0 ? true : false;
    }

    public void deleteCounterReadOnePerson(CounterReadFX counterReadFX) throws SQLException {
        counterReadRepository.deleteCounterReadByPerson(CounterReadConverter.convertToCounterRead(counterReadFX));
    }

    public void updateCounterRead() throws SQLException {
        counterReadRepository.updateCounterRead(CounterReadConverter.convertToCounterRead(counterReadFX));
    }


    public CounterReadFX getCounterReadFX() {
        return counterReadFX;
    }

    public void setCounterReadFX(CounterReadFX counterReadFX) {
        this.counterReadFX = counterReadFX;
    }

    public boolean isOnlyNewUser() {
        return onlyNewUser.get();
    }

    public SimpleBooleanProperty onlyNewUserProperty() {
        return onlyNewUser;
    }

    public void setOnlyNewUser(boolean onlyNewUser) {
        this.onlyNewUser.set(onlyNewUser);
    }


}
