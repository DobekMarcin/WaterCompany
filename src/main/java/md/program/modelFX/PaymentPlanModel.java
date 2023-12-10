package md.program.modelFX;

import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
import md.program.database.model.RateYear;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentPlanModel {
    private PartnerRepository partnerRepository = new PartnerRepository();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();

    public void generatePaymentPlan(RateYearFX rateYearFX) throws SQLException,RuntimeException {
        List<Partner> partnerList = partnerRepository.getAllPartner();
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        RateYear rateYear= RateYearConverter.convertToRateYear(rateYearFX);
        partnerList.forEach(item->{
            PaymentPlan temp = new PaymentPlan();
            temp.setPartnerId(item.getId());
            temp.setYearId(rateYear.getId());
            temp.setM1(rateYear.getRate()*item.getPeopleCount());
            temp.setM2(rateYear.getRate()*item.getPeopleCount());
            temp.setM3(rateYear.getRate()*item.getPeopleCount());
            temp.setM4(rateYear.getRate()*item.getPeopleCount());
            temp.setM5(rateYear.getRate()*item.getPeopleCount());
            temp.setM6(rateYear.getRate()*item.getPeopleCount());
            temp.setM7(rateYear.getRate()*item.getPeopleCount());
            temp.setM8(rateYear.getRate()*item.getPeopleCount());
            temp.setM9(rateYear.getRate()*item.getPeopleCount());
            temp.setM10(rateYear.getRate()*item.getPeopleCount());
            temp.setM11(rateYear.getRate()*item.getPeopleCount());
            temp.setM12(rateYear.getRate()*item.getPeopleCount());
            paymentPlanList.add(temp);
        });

        for (PaymentPlan item : paymentPlanList) {
            paymentPlanRepository.addPartner(item);
        }

    }
}
