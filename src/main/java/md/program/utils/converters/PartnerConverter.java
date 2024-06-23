package md.program.utils.converters;

import md.program.database.model.LogPartner;
import md.program.database.model.Partner;
import md.program.modelFX.PartnerFX;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class PartnerConverter {

    public static Partner convertToPartner(PartnerFX partnerFX){
        Partner partner = new Partner();
        partner.setId(partnerFX.getId());
        partner.setName(partnerFX.getName());
        partner.setSurname(partnerFX.getSurname());
        partner.setAddress(partnerFX.getAddress());
        partner.setPostCode(partnerFX.getPostCode());
        partner.setPost(partnerFX.getPost());
        partner.setNip(partnerFX.getNip());
        partner.setPeopleCount(partnerFX.getPeopleCount());
        partner.setArchives(partnerFX.archivesProperty().get());
        partner.setCompany(partnerFX.companyProperty().get());
        partner.setMeter(partnerFX.meterProperty().get());
        return partner;
    }

    public static PartnerFX convertToPartnerFX(Partner partner){
        PartnerFX partnerFX = new PartnerFX();
        partnerFX.setId(partner.getId());
        partnerFX.setName(partner.getName());
        partnerFX.setSurname(partner.getSurname());
        partnerFX.setAddress(partner.getAddress());
        partnerFX.setPostCode(partner.getPostCode());
        partnerFX.setPost(partner.getPost());
        partnerFX.setNip(partner.getNip());
        partnerFX.setPeopleCount(partner.getPeopleCount());
        partnerFX.setArchives(partner.getArchives());
        partnerFX.setCompany(partner.getCompany());
        partnerFX.setMeter(partner.getMeter());
        return partnerFX;
    }



}
