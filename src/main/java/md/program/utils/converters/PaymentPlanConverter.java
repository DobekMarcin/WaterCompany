package md.program.utils.converters;

import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
import md.program.modelFX.PartnerFX;
import md.program.modelFX.PaymentPlanFX;

public class PaymentPlanConverter {

    public static PaymentPlan convertToPaymentPlan(PaymentPlanFX paymentPlanFX){
        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan.setId(paymentPlanFX.getId());
        paymentPlan.setPartnerId(paymentPlanFX.getPartnerId());
        paymentPlan.setYearId(paymentPlanFX.getYearId());
        paymentPlan.setPartner(PartnerConverter.convertToPartner(paymentPlanFX.getPartner()));
        paymentPlan.setM1(paymentPlanFX.getM1());
        paymentPlan.setM2(paymentPlanFX.getM2());
        paymentPlan.setM3(paymentPlanFX.getM3());
        paymentPlan.setM4(paymentPlanFX.getM4());
        paymentPlan.setM5(paymentPlanFX.getM5());
        paymentPlan.setM6(paymentPlanFX.getM6());
        paymentPlan.setM7(paymentPlanFX.getM7());
        paymentPlan.setM8(paymentPlanFX.getM8());
        paymentPlan.setM9(paymentPlanFX.getM9());
        paymentPlan.setM10(paymentPlanFX.getM10());
        paymentPlan.setM11(paymentPlanFX.getM11());
        paymentPlan.setM12(paymentPlanFX.getM12());
        return paymentPlan;
    }

    public static PaymentPlanFX convertToPaymentPlanFX(PaymentPlan paymentPlan){
        PaymentPlanFX paymentPlanFX = new PaymentPlanFX();
        paymentPlanFX.setId(paymentPlan.getId());
        paymentPlanFX.setPartnerId(paymentPlan.getPartnerId());
        paymentPlanFX.setPartner(PartnerConverter.convertToPartnerFX(paymentPlan.getPartner()));
        paymentPlanFX.setYearId(paymentPlan.getYearId());
        paymentPlanFX.setM1(paymentPlan.getM1());
        paymentPlanFX.setM2(paymentPlan.getM2());
        paymentPlanFX.setM3(paymentPlan.getM3());
        paymentPlanFX.setM4(paymentPlan.getM4());
        paymentPlanFX.setM5(paymentPlan.getM5());
        paymentPlanFX.setM6(paymentPlan.getM6());
        paymentPlanFX.setM7(paymentPlan.getM7());
        paymentPlanFX.setM8(paymentPlan.getM8());
        paymentPlanFX.setM9(paymentPlan.getM9());
        paymentPlanFX.setM10(paymentPlan.getM10());
        paymentPlanFX.setM11(paymentPlan.getM11());
        paymentPlanFX.setM12(paymentPlan.getM12());
        return paymentPlanFX;
    }

}
