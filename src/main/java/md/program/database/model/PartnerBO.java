package md.program.database.model;

public class PartnerBO {

    Partner partner;
    Double bo;
    Integer partnerId;

    public PartnerBO() {
    }

    public PartnerBO(Partner partner, Double bo, Integer partnerId) {
        this.partner = partner;
        this.bo = bo;
        this.partnerId = partnerId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Double getBo() {
        return bo;
    }

    public void setBo(Double bo) {
        this.bo = bo;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}
