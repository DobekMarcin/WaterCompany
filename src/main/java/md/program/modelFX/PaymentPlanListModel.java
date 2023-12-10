package md.program.modelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.utils.converters.PartnerConverter;
import md.program.utils.converters.PaymentPlanConverter;

import java.sql.SQLException;
import java.util.List;

public class PaymentPlanListModel {
    private ObservableList<PaymentPlanFX> paymentPlanFXObservableList = FXCollections.observableArrayList();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private PartnerRepository partnerRepository = new PartnerRepository();

    public void init(RateYearFX rateYearFX) throws SQLException {
        List<PaymentPlan> paymentPlanList = paymentPlanRepository.getAllPaymentPlanByYear(rateYearFX.getId());
        for (PaymentPlan paymentPlan : paymentPlanList) {
            paymentPlan.setPartner(partnerRepository.getPartnerById(paymentPlan.getPartnerId()));
        }
        paymentPlanFXObservableList.clear();
        paymentPlanList.forEach(item->{
            PaymentPlanFX paymentPlanFX = PaymentPlanConverter.convertToPaymentPlanFX(item);
            paymentPlanFXObservableList.add(paymentPlanFX);
        });
    }

    public ObservableList<PaymentPlanFX> getPaymentPlanFXObservableList() {
        return paymentPlanFXObservableList;
    }

    public void setPaymentPlanFXObservableList(ObservableList<PaymentPlanFX> paymentPlanFXObservableList) {
        this.paymentPlanFXObservableList = paymentPlanFXObservableList;
    }
}
