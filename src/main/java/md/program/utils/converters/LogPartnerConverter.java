package md.program.utils.converters;

import md.program.database.model.LogPartner;
import md.program.database.model.Partner;
import md.program.modelFX.LogPartnerFX;
import md.program.modelFX.PartnerFX;

public class LogPartnerConverter {

    public static LogPartner convertToLogPartner(LogPartnerFX logPartnerFX){
        LogPartner logPartner = new LogPartner();
        logPartner.setId_logu(logPartnerFX.getId_logu());
        logPartner.setYear(logPartnerFX.getYear());
        logPartner.setMonth(logPartnerFX.getMonth());
        logPartner.setId_partner(logPartnerFX.getId());
        logPartner.setName(logPartnerFX.getName());
        logPartner.setSurname(logPartnerFX.getSurname());
        logPartner.setAddress(logPartnerFX.getAddress());
        logPartner.setPostCode(logPartnerFX.getPostCode());
        logPartner.setPost(logPartnerFX.getPost());
        logPartner.setNip(logPartnerFX.getNip());
        logPartner.setPeopleCount(logPartnerFX.getPeopleCount());
        logPartner.setArchives(logPartnerFX.archivesProperty().get());
        logPartner.setCompany(logPartnerFX.companyProperty().get());
        logPartner.setMeter(logPartnerFX.meterProperty().get());
        return logPartner;
    }

    public static LogPartnerFX convertToLogPartnerFX(LogPartner logPartner){
        LogPartnerFX logPartnerFX = new LogPartnerFX();
        logPartnerFX.setId_logu(logPartner.getId_logu());
        logPartnerFX.setYear(logPartner.getYear());
        logPartnerFX.setMonth(logPartner.getMonth());
        logPartnerFX.setId(logPartner.getId_partner());
        logPartnerFX.setName(logPartner.getName());
        logPartnerFX.setSurname(logPartner.getSurname());
        logPartnerFX.setAddress(logPartner.getAddress());
        logPartnerFX.setPostCode(logPartner.getPostCode());
        logPartnerFX.setPost(logPartner.getPost());
        logPartnerFX.setNip(logPartner.getNip());
        logPartnerFX.setPeopleCount(logPartner.getPeopleCount());
        logPartnerFX.setArchives(logPartner.getArchives());
        logPartnerFX.setCompany(logPartner.getCompany());
        logPartnerFX.setMeter(logPartner.getMeter());
        return logPartnerFX;
    }
}
