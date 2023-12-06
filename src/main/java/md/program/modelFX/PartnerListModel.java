package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.model.RateYear;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.PartnerConverter;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.List;

public class PartnerListModel {
    private ObservableList<PartnerFX> partnerFXObservableList = FXCollections.observableArrayList();
    private PartnerRepository partnerRepository = new PartnerRepository();

    public void init() throws SQLException {
        List<Partner> partnerList = partnerRepository.getAllPartner();
        partnerFXObservableList.clear();
        partnerList.forEach(item->{
            PartnerFX partnerFX = PartnerConverter.convertToPartnerFX(item);
            partnerFXObservableList.add(partnerFX);
        });
    }

    public ObservableList<PartnerFX> getPartnerFXObservableList() {
        return partnerFXObservableList;
    }

    public void setPartnerFXObservableList(ObservableList<PartnerFX> partnerFXObservableList) {
        this.partnerFXObservableList = partnerFXObservableList;
    }
}
