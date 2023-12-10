package md.program.modelFX;

import md.program.database.repository.PartnerRepository;
import md.program.utils.converters.PartnerConverter;

import java.sql.SQLException;

public class PartnerModel {

    private PartnerFX partnerFX = new PartnerFX();
    private PartnerRepository partnerRepository = new PartnerRepository();

    public PartnerFX getPartnerFX() {
        return partnerFX;
    }

    public void setPartnerFX(PartnerFX partnerFX) {
        this.partnerFX = partnerFX;
    }

    public void setId() throws SQLException {
        partnerFX.setId(partnerRepository.getNextId());
    }
    public void addPartner() throws SQLException {
        partnerRepository.addPartner(PartnerConverter.convertToPartner(partnerFX));
    }
    public void deletePartner() throws SQLException {
        partnerRepository.deleteRateYearById(PartnerConverter.convertToPartner(partnerFX));
    }
    public void saveUpdatePartner() throws SQLException {
        partnerRepository.updatePartner(PartnerConverter.convertToPartner(partnerFX));
    }

}
