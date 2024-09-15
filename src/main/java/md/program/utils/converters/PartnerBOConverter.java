package md.program.utils.converters;

import md.program.database.model.PartnerBO;
import md.program.database.model.RateYear;
import md.program.modelFX.PartnerBOFX;
import md.program.modelFX.RateYearFX;

public class PartnerBOConverter {

    public static PartnerBO convertToPartnerBO(PartnerBOFX partnerBOFX) {
        PartnerBO partnerBO = new PartnerBO();
        partnerBO.setPartnerId(partnerBOFX.getParterIdSimpleIntegerProperty());
        partnerBO.setBo(partnerBOFX.getPartnerBoValueProperty());
        partnerBO.setPartner(PartnerConverter.convertToPartner(partnerBOFX.getPartnerFXSimpleObjectProperty()));
        return partnerBO;
    }

    public static PartnerBOFX convertToPartnerBOFX(PartnerBO partnerBO) {
        PartnerBOFX partnerBOFX = new PartnerBOFX();
        partnerBOFX.setParterIdSimpleIntegerProperty(partnerBO.getPartnerId());
        partnerBOFX.setPartnerBoValueProperty(partnerBO.getBo());
        partnerBOFX.setPartnerFXSimpleObjectProperty(PartnerConverter.convertToPartnerFX(partnerBO.getPartner()));
        return partnerBOFX;
    }
}
