package md.program.modelFX;

import md.program.database.repository.PartnerBORepository;
import md.program.utils.converters.PartnerBOConverter;
import md.program.utils.converters.PartnerConverter;

import java.sql.SQLException;

public class PartnerBOFXModel {

    private PartnerBORepository partnerBORepository = new PartnerBORepository();

    private PartnerBOFX partnerBOFX;



    public PartnerBOFX getPartnerBOFX() {
        return partnerBOFX;
    }

    public void setPartnerBOFX(PartnerBOFX partnerBOFX) {
        this.partnerBOFX = partnerBOFX;
    }

    public void updatePartnerBO() throws SQLException {

            partnerBORepository.updatePartnerBO(PartnerBOConverter.convertToPartnerBO(partnerBOFX));

    }
}
