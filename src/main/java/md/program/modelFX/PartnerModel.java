package md.program.modelFX;

import md.program.database.model.Partner;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.utils.converters.PartnerConverter;

import java.sql.SQLException;

public class PartnerModel {

    private PartnerFX partnerFX = new PartnerFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();

    public PartnerFX getPartnerFX() {
        return partnerFX;
    }

    public void setPartnerFX(PartnerFX partnerFX) {
        this.partnerFX = partnerFX;
    }

    public void setId() throws SQLException {
        partnerFX.setId(partnerRepository.getNextId());
    }

    public Boolean validData() {
        Partner partner = PartnerConverter.convertToPartner(partnerFX);
        if (partner.getSurname().isEmpty() || partner.getAddress().isEmpty() || partner.getPostCode().isEmpty() || partner.getPost().isEmpty()) return false;
        if (partner.getCompany() == false && partner.getPeopleCount() == 0) return false;
        if (partner.getCompany()== true && partner.getNip().isEmpty()) return false;
        return true;
    }

    public void addPartner() throws SQLException {
        Partner partner = PartnerConverter.convertToPartner(partnerFX);
        if (partner.getCompany()) partner.setPeopleCount(0);
        partnerRepository.addPartner(partner);
    }

    public Boolean deletePartner() throws SQLException {
        Integer temp = paymentPlanRepository.checkPartnerInPaymentPlan(PartnerConverter.convertToPartner(partnerFX));
        if (temp == 0) {
            partnerRepository.deleteRateYearById(PartnerConverter.convertToPartner(partnerFX));
            return true;
        } else return false;
    }

    public void saveUpdatePartner() throws SQLException {
        partnerRepository.updatePartner(PartnerConverter.convertToPartner(partnerFX));
    }

}
