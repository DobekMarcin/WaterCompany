package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
import md.program.database.model.RateYear;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.database.repository.RateYearRepository;
import md.program.utils.converters.PaymentPlanConverter;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentPlanModel {
    private PaymentPlanFX paymentPlanFX = new PaymentPlanFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private RateYearRepository rateYearRepository = new RateYearRepository();

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(paymentPlanRepository.getYearList());
    }

    public void generatePaymentPlan(Integer generateYear) throws SQLException,RuntimeException {
        List<Partner> partnerList = partnerRepository.getAllPartner();
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        RateYear rateYear= rateYearRepository.getRateYear(generateYear);
        if(rateYear==null) {rateYear = new RateYear();}
        RateYear finalRateYear = rateYear;
        partnerList.forEach(item->{
            PaymentPlan temp = new PaymentPlan();
            temp.setPartnerId(item.getId());
            temp.setYearId(generateYear);
            temp.setM1(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM2(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM3(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM4(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM5(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM6(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM7(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM8(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM9(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM10(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM11(finalRateYear.getRate()*item.getPeopleCount());
            temp.setM12(finalRateYear.getRate()*item.getPeopleCount());
            paymentPlanList.add(temp);
        });

        for (PaymentPlan item : paymentPlanList) {
            paymentPlanRepository.addPartner(item);
        }
    }
    public void updatePaymentPlan() throws SQLException {
        paymentPlanRepository.updatePaymentPlan(PaymentPlanConverter.convertToPaymentPlan(paymentPlanFX));
    }

    public PaymentPlanFX getPaymentPlanFX() {
        return paymentPlanFX;
    }

    public void setPaymentPlanFX(PaymentPlanFX paymentPlanFX) {
        this.paymentPlanFX = paymentPlanFX;
    }

}
