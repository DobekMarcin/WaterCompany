package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.*;
import md.program.database.repository.*;
import md.program.utils.DialogUtil;
import md.program.utils.converters.PaymentPlanConverter;
import md.program.utils.converters.RateYearConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class PaymentPlanModel {
    private PaymentPlanFX paymentPlanFX = new PaymentPlanFX();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private RateYearRepository rateYearRepository = new RateYearRepository();
    private CounterYearRepository counterYearRepository = new CounterYearRepository();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private SimpleBooleanProperty noComapny = new SimpleBooleanProperty();
    private Integer noCounterRead = 0;
    private LogPartnerRepository logPartnerRepository = new LogPartnerRepository();

    public ObservableList<Integer> getAllYear() throws SQLException {
        return FXCollections.observableArrayList(paymentPlanRepository.getYearList());
    }

    public void generatePaymentPlan(Integer generateYear) throws SQLException {
        noCounterRead = 0;
        List<Partner> partnerList = null;
        if (noComapny.get()) {
            paymentPlanRepository.deletePaymnetPlanByYearNoCompany(generateYear);
            partnerList = partnerRepository.getAllPartnerNoCompany(generateYear);
        } else {
            paymentPlanRepository.deletePaymnetPlanByYear(generateYear);
            partnerList = partnerRepository.getAllPartner();
        }
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        RateYear rateYear = rateYearRepository.getRateYear(generateYear);
        if (rateYear == null) {
            rateYear = new RateYear();
        }
        RateYear finalRateYear = rateYear;
        partnerList.forEach(item -> {
            PaymentPlan temp = null;
            try {
                temp = getPaymentPlan(generateYear, item);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            paymentPlanList.add(temp);
        });
        if (noCounterRead > 0) {
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.counter.noCounterRade");
        }
        for (PaymentPlan item : paymentPlanList) {
            paymentPlanRepository.addPartner(item);
        }
    }

    public void generatePaymentPlanOnePerson(Integer generateYear, PaymentPlanFX paymentPlanFX) throws SQLException, RuntimeException {
        noCounterRead = 0;
        Integer paymentPlanId = paymentPlanFX.getId();
        Partner partner = partnerRepository.getPartnerById(paymentPlanFX.getPartnerId());
        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan = getPaymentPlan(generateYear, partner);
        paymentPlan.setId(paymentPlanId);
        paymentPlanRepository.updatePaymentPlan(paymentPlan);
        if (noCounterRead > 0) {
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "dialog.counter.noCounterRade");
        }
    }

    public void deletePaymentPlanOnePerson(PaymentPlanFX paymentPlanFX) throws SQLException, RuntimeException {
        paymentPlanRepository.deletePaymnetPlanByPerson(PaymentPlanConverter.convertToPaymentPlan(paymentPlanFX));
    }

    private PaymentPlan getPaymentPlan(Integer generateYear, Partner item) throws SQLException {
        PaymentPlan temp = new PaymentPlan();

        temp.setPartnerId(item.getId());
        temp.setYearId(generateYear);

        if (item.getMeter()) {

            CounterYear counterYear = counterYearRepository.getCounterYear(generateYear);
            if (counterYear == null) {
                counterYear = new CounterYear();
            }

            Boolean isCounterRead = counterReadRepository.checkCounterRead(generateYear, item.getId());

            CounterRead counterRead = new CounterRead();
            if (isCounterRead)
                counterRead = counterReadRepository.getCounterReadByYearAndPartner(generateYear, item.getId());
            else
                //  DialogUtil.dialogAboutApplication("dialog.title","dialog.header","dialog.counter.noCounterRade");
                noCounterRead++;
            CounterRead precCounterRead1 = new CounterRead();
            precCounterRead1 = counterReadRepository.getCounterReadByYearAndPartner(generateYear - 1, item.getId());
            if (precCounterRead1.getM12() == null) precCounterRead1.setM12(0d);


            if (counterRead.getM1() == 0) temp.setM1(0d);
            else
                temp.setM1(counterYear.getCounterRate() * (counterRead.getM1() - precCounterRead1.getM12()));

            if (counterRead.getM2() == 0) temp.setM2(0d);
            else
                temp.setM2(counterYear.getCounterRate() * (counterRead.getM2() - counterRead.getM1()));

            if (counterRead.getM3() == 0) temp.setM3(0d);
            else
                temp.setM3(counterYear.getCounterRate() * (counterRead.getM3() - counterRead.getM2()));

            if (counterRead.getM4() == 0) temp.setM4(0d);
            else
                temp.setM4(counterYear.getCounterRate() * (counterRead.getM4() - counterRead.getM3()));

            if (counterRead.getM5() == 0) temp.setM5(0d);
            else
                temp.setM5(counterYear.getCounterRate() * (counterRead.getM5() - counterRead.getM4()));

            if (counterRead.getM6() == 0) temp.setM6(0d);
            else
                temp.setM6(counterYear.getCounterRate() * (counterRead.getM6() - counterRead.getM5()));

            if (counterRead.getM7() == 0) temp.setM7(0d);
            else
                temp.setM7(counterYear.getCounterRate() * (counterRead.getM7() - counterRead.getM6()));

            if (counterRead.getM8() == 0) temp.setM8(0d);
            else
                temp.setM8(counterYear.getCounterRate() * (counterRead.getM8() - counterRead.getM7()));

            if (counterRead.getM9() == 0) temp.setM9(0d);
            else
                temp.setM9(counterYear.getCounterRate() * (counterRead.getM9() - counterRead.getM8()));

            if (counterRead.getM10() == 0) temp.setM10(0d);
            else
                temp.setM10(counterYear.getCounterRate() * (counterRead.getM10() - counterRead.getM9()));

            if (counterRead.getM11() == 0) temp.setM11(0d);
            else
                temp.setM11(counterYear.getCounterRate() * (counterRead.getM11() - counterRead.getM10()));

            if (counterRead.getM12() == 0) temp.setM12(0d);
            else

                temp.setM12(counterYear.getCounterRate() * (counterRead.getM12() - counterRead.getM11()));

        } else if (item.getCompany() == false) {

            RateYear rateYear = rateYearRepository.getRateYear(generateYear);
            if (rateYear == null) {
                rateYear = new RateYear();
            }

            temp.setM1(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
            temp.setM2(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 2));
            temp.setM3(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 3));
            temp.setM4(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 4));
            temp.setM5(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 5));
            temp.setM6(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 6));
            temp.setM7(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 7));
            temp.setM8(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 8));
            temp.setM9(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 9));
            temp.setM10(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 10));
            temp.setM11(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 11));
            temp.setM12(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 12));
        }

        return temp;
    }

    private Integer getPeopleCountFromLog(Integer generateYear, Partner temp, Integer month) throws SQLException {
        List<LogPartner> logPartnerList = logPartnerRepository.getAllLogForPartnerMaxPeriodTime(temp.getId());

        for (LogPartner period : logPartnerList) {
            GregorianCalendar periodDate = new GregorianCalendar();
            periodDate.set(period.getYear(), period.getMonth(), 1);
            GregorianCalendar currentDate = new GregorianCalendar();
            currentDate.set(generateYear, month, 1);
            if (currentDate.compareTo(periodDate) <= 0) {
                return period.getPeopleCount();
            }
        }
        return temp.getPeopleCount();
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

    public boolean isNoComapny() {
        return noComapny.get();
    }

    public SimpleBooleanProperty noComapnyProperty() {
        return noComapny;
    }

    public void setNoComapny(boolean noComapny) {
        this.noComapny.set(noComapny);
    }


    public Boolean chceckPaymentPlanByDefayultYear(Integer defaultYear) throws SQLException {
        return paymentPlanRepository.chceckIsPaymentPlanByDefaultYear(defaultYear) > 0 ? true : false;
    }
}
