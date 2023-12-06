package md.program.utils.converters;

import md.program.database.model.Partner;
import md.program.database.model.RateYear;
import md.program.modelFX.PartnerFX;
import md.program.modelFX.RateYearFX;

public class PartnerConverter {

    public static Partner convertToPartner(PartnerFX partnerFX){
        Partner partner = new Partner();
        partner.setId(partnerFX.getId());
        partner.setName(partnerFX.getName());
        partner.setSurname(partnerFX.getSurname());
        partner.setAddress(partnerFX.getAddress());
        partner.setPeopleCount(partnerFX.getPeopleCount());
        partner.setArchives(partnerFX.archivesProperty().get());
        return partner;
    }

    public static PartnerFX convertToPartnerFX(Partner partner){
        PartnerFX partnerFX = new PartnerFX();
        partnerFX.setId(partner.getId());
        partnerFX.setName(partner.getName());
        partnerFX.setSurname(partner.getSurname());
        partnerFX.setAddress(partner.getAddress());
        partnerFX.setPeopleCount(partner.getPeopleCount());
        partnerFX.setArchives(partner.getArchives());
        return partnerFX;
    }

}
