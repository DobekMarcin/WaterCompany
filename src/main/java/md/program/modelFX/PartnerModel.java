package md.program.modelFX;

import md.program.database.model.LogPartner;
import md.program.database.model.Partner;
import md.program.database.repository.CounterReadRepository;
import md.program.database.repository.LogPartnerRepository;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.utils.Utils;
import md.program.utils.converters.PartnerConverter;

import java.sql.SQLException;

public class PartnerModel {

    private Partner oldPartner;
    private PartnerFX partnerFX = new PartnerFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private LogPartnerRepository logPartnerRepository = new LogPartnerRepository();

    public void setOldPartner() throws CloneNotSupportedException {
        oldPartner = PartnerConverter.convertToPartner(partnerFX);

    }

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
        if (partner.getCompany() == false && partner.getPeopleCount().intValue() < 0) return false;
        if (partner.getCompany()== true && partner.getNip().isEmpty()) return false;
        if (partner.getYear()<2024 || partner.getYear()>2050) return false;
        if(partner.getMonth()<0 || partner.getMonth()>12) return false;
        return true;
    }

    public void addPartner() throws SQLException {
        Partner partner = PartnerConverter.convertToPartner(partnerFX);
        if (partner.getCompany()) partner.setPeopleCount(0);
        partnerRepository.addPartner(partner);
    }

    public Boolean deletePartner() throws SQLException {
        Integer temp = paymentPlanRepository.checkPartnerInPaymentPlan(PartnerConverter.convertToPartner(partnerFX));
        Integer temp2 = counterReadRepository.checkPartnerInCounterRead(PartnerConverter.convertToPartner(partnerFX));
        if (temp == 0 && temp2==0) {
            partnerRepository.deletePartnerById(PartnerConverter.convertToPartner(partnerFX));
            logPartnerRepository.deleteLogByPartner(PartnerConverter.convertToPartner(partnerFX));
            return true;
        } else return false;
    }

    public void saveUpdatePartner() throws SQLException {
        Partner newPartner = PartnerConverter.convertToPartner(partnerFX);
        if(!newPartner.equals(oldPartner)){
            logPartnerRepository.addPartnerLog(new LogPartner(Utils.getYear(),Utils.getMonth(),oldPartner));
        }
        partnerRepository.updatePartner(PartnerConverter.convertToPartner(partnerFX));


    }

}
