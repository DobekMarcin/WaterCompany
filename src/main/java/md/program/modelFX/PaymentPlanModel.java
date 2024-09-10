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
            if(item.getYear()<=generateYear) {
                PaymentPlan temp = null;
                try {
                    temp = getPaymentPlan(generateYear, item);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                paymentPlanList.add(temp);
            }
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

        PaymentPlan paymentPlan;
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

        Boolean isArchive=false;
        if(item.getArchives()==true){

        }
        PaymentPlan temp = new PaymentPlan();

        temp.setPartnerId(item.getId());
        temp.setYearId(generateYear);

        RateYear rateYear = rateYearRepository.getRateYear(generateYear);
        if (rateYear == null) {
            rateYear = new RateYear();
        }

        if (item.getMeter()) {

            CounterYear counterYear = counterYearRepository.getCounterYear(generateYear);
            Integer monthStart = item.getMonth();
            Integer yearStart = item.getYear();

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
            CounterRead precCounterRead1;
            precCounterRead1 = counterReadRepository.getCounterReadByYearAndPartner(generateYear - 1, item.getId());
            if (precCounterRead1.getM12() == null) precCounterRead1.setM12(0d);

            LogPartner logpartner=getMaxLogPartnerNoMeter(item.getId());


            if(logpartner !=null && logpartner.getYear().equals(generateYear)){

                Integer monthMeter = logpartner.getMonth();
                System.out.println(monthMeter);

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart==1){
                        if(monthMeter>1){
                            temp.setM1(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM1() == 0) temp.setM1(0d);
                            else
                                temp.setM1(counterYear.getCounterRate() * (counterRead.getM1() - precCounterRead1.getM12()));
                        }
                    }else{
                        temp.setM1(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()){
                if(monthMeter>1){
                    temp.setM1(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM1() == 0) temp.setM1(0d);
                    else
                        temp.setM1(counterYear.getCounterRate() * (counterRead.getM1() - precCounterRead1.getM12()));
                }}else {
                    temp.setM1(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=2){
                        if(monthMeter>2){
                            temp.setM2(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 2));
                        }else{
                            if (counterRead.getM2() == 0) temp.setM2(0d);
                            else
                                temp.setM2(counterYear.getCounterRate() * (counterRead.getM2() - counterRead.getM1()));
                        }
                    }else{
                        temp.setM2(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {

                        if (monthMeter > 2) {
                            temp.setM2(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 2));
                        } else {
                            if (counterRead.getM2() == 0) temp.setM2(0d);
                            else
                                temp.setM2(counterYear.getCounterRate() * (counterRead.getM2() - counterRead.getM1()));
                        }
                }else{
                    temp.setM2(0d);
                }
                }


                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=3){
                            if(monthMeter>3){
                                temp.setM3(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 3));
                            }else{
                                if (counterRead.getM3() == 0) temp.setM3(0d);
                                else
                                    temp.setM3(counterYear.getCounterRate() * (counterRead.getM3() - counterRead.getM2()));
                            }
                    }else{
                        temp.setM3(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {

                        if(monthMeter>3){
                            temp.setM3(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 3));
                        }else{
                            if (counterRead.getM3() == 0) temp.setM3(0d);
                            else
                                temp.setM3(counterYear.getCounterRate() * (counterRead.getM3() - counterRead.getM2()));
                        }}else{
                        temp.setM3(0d);
                    }
                }


                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=4){
                        if(monthMeter>4){
                            temp.setM4(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 4));
                        }else{
                            if (counterRead.getM4() == 0) temp.setM4(0d);
                            else
                                temp.setM4(counterYear.getCounterRate() * (counterRead.getM4() - counterRead.getM3()));
                        }
                    }else{
                        temp.setM4(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>4){
                    temp.setM4(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 4));
                }else{
                    if (counterRead.getM4() == 0) temp.setM4(0d);
                    else
                        temp.setM4(counterYear.getCounterRate() * (counterRead.getM4() - counterRead.getM3()));
                }}else{
                        temp.setM4(0d);
                    }
                }


                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=5){
                        if (monthMeter > 5) {
                            temp.setM5(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        } else {
                            if (counterRead.getM5() == 0) temp.setM5(0d);
                            else
                                temp.setM5(counterYear.getCounterRate() * (counterRead.getM5() - counterRead.getM4()));
                        }
                    }else{
                        temp.setM5(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()) {
                    if (monthMeter > 5) {
                        temp.setM5(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                    } else {
                        if (counterRead.getM5() == 0) temp.setM5(0d);
                        else
                            temp.setM5(counterYear.getCounterRate() * (counterRead.getM5() - counterRead.getM4()));
                    }
                }else{
                        temp.setM5(0d);
                    }
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=6){
                        if(monthMeter>6){
                            temp.setM6(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM6() == 0) temp.setM6(0d);
                            else
                                temp.setM6(counterYear.getCounterRate() * (counterRead.getM6() - counterRead.getM5()));
                        }
                    }else{
                        temp.setM6(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>6){
                    temp.setM6(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM6() == 0) temp.setM6(0d);
                    else
                        temp.setM6(counterYear.getCounterRate() * (counterRead.getM6() - counterRead.getM5()));
                }}else{
                      temp.setM6(0d);
                    }}


                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=7){
                        if(monthMeter>7){
                            temp.setM7(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM7() == 0) temp.setM7(0d);
                            else
                                temp.setM7(counterYear.getCounterRate() * (counterRead.getM7() - counterRead.getM6()));
                        }
                    }else{
                        temp.setM7(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>7){
                    temp.setM7(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM7() == 0) temp.setM7(0d);
                    else
                        temp.setM7(counterYear.getCounterRate() * (counterRead.getM7() - counterRead.getM6()));
                }}else{
                      temp.setM7(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=8){
                        if(monthMeter>8){
                            temp.setM8(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM8() == 0) temp.setM8(0d);
                            else
                                temp.setM8(counterYear.getCounterRate() * (counterRead.getM8() - counterRead.getM7()));
                        }
                    }else{
                        temp.setM8(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>8){
                    temp.setM8(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM8() == 0) temp.setM8(0d);
                    else
                        temp.setM8(counterYear.getCounterRate() * (counterRead.getM8() - counterRead.getM7()));
                }}else{
                      temp.setM8(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=9){
                        if(monthMeter>9){
                            temp.setM9(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM9() == 0) temp.setM9(0d);
                            else
                                temp.setM9(counterYear.getCounterRate() * (counterRead.getM9() - counterRead.getM8()));
                        }
                    }else{
                        temp.setM9(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>9){
                    temp.setM9(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM9() == 0) temp.setM9(0d);
                    else
                        temp.setM9(counterYear.getCounterRate() * (counterRead.getM9() - counterRead.getM8()));
                }}else{
                      temp.setM9(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=10){
                        if(monthMeter>10){
                            temp.setM10(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM10() == 0) temp.setM10(0d);
                            else
                                temp.setM10(counterYear.getCounterRate() * (counterRead.getM10() - counterRead.getM9()));
                        }
                    }else{
                        temp.setM10(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>10){
                    temp.setM10(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM10() == 0) temp.setM10(0d);
                    else
                        temp.setM10(counterYear.getCounterRate() * (counterRead.getM10() - counterRead.getM9()));
                }}else{
                      temp.setM10(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=11){
                        if(monthMeter>11){
                            temp.setM11(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM11() == 0) temp.setM11(0d);
                            else
                                temp.setM11(counterYear.getCounterRate() * (counterRead.getM11() - counterRead.getM10()));
                        }
                    }else{
                        temp.setM11(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>11){
                    temp.setM11(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM11() == 0) temp.setM11(0d);
                    else
                        temp.setM11(counterYear.getCounterRate() * (counterRead.getM11() - counterRead.getM10()));
                }}else {
                        temp.setM11(0d);
                    } }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=11){
                        if(monthMeter>12){
                            temp.setM12(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                        }else{
                            if (counterRead.getM12() == 0) temp.setM12(0d);
                            else
                                temp.setM12(counterYear.getCounterRate() * (counterRead.getM12() - counterRead.getM11()));
                        }
                    }else{
                        temp.setM12(0d);
                    }
                }else{
                    if(generateYear.intValue()>=yearStart.intValue()) {
                if(monthMeter>12){
                    temp.setM12(rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1));
                }else{
                    if (counterRead.getM12() == 0) temp.setM12(0d);
                    else
                        temp.setM12(counterYear.getCounterRate() * (counterRead.getM12() - counterRead.getM11()));
                }}else{
                      temp.setM12(0d);
                    }}
            }else {
                System.out.println("test2");

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart==1){
                        if (counterRead.getM1() == 0) temp.setM1(0d);
                        else
                            temp.setM1(counterYear.getCounterRate() * (counterRead.getM1() - precCounterRead1.getM12()));
                    }else{
                        temp.setM1(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM1() == 0) temp.setM1(0d);
                    else
                        temp.setM1(counterYear.getCounterRate() * (counterRead.getM1() - precCounterRead1.getM12()));
                }else{
                    temp.setM1(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=2){
                        if (counterRead.getM2() == 0) temp.setM2(0d);
                        else
                            temp.setM2(counterYear.getCounterRate() * (counterRead.getM2() - counterRead.getM1()));
                    }else{
                        temp.setM2(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM2() == 0) temp.setM2(0d);
                    else
                        temp.setM2(counterYear.getCounterRate() * (counterRead.getM2() - counterRead.getM1()));
                }else{
                    temp.setM2(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=3){
                        if (counterRead.getM3() == 0) temp.setM3(0d);
                        else
                            temp.setM3(counterYear.getCounterRate() * (counterRead.getM3() - counterRead.getM2()));
                    }else{
                        temp.setM3(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM3() == 0) temp.setM3(0d);
                    else
                        temp.setM3(counterYear.getCounterRate() * (counterRead.getM3() - counterRead.getM2()));
                }else {
                    temp.setM3(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=4){
                        if (counterRead.getM4() == 0) temp.setM4(0d);
                        else
                            temp.setM4(counterYear.getCounterRate() * (counterRead.getM4() - counterRead.getM3()));
                    }else{
                        temp.setM4(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM4() == 0) temp.setM4(0d);
                    else
                        temp.setM4(counterYear.getCounterRate() * (counterRead.getM4() - counterRead.getM3()));
                }else{
                    temp.setM4(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=5){
                        if (counterRead.getM5() == 0) temp.setM5(0d);
                        else
                            temp.setM5(counterYear.getCounterRate() * (counterRead.getM5() - counterRead.getM4()));
                    }else{
                        temp.setM5(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM5() == 0) temp.setM5(0d);
                    else
                        temp.setM5(counterYear.getCounterRate() * (counterRead.getM5() - counterRead.getM4()));
                }else{
                    temp.setM5(0d);}
                }

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=6){
                        if (counterRead.getM6() == 0) temp.setM6(0d);
                        else
                            temp.setM6(counterYear.getCounterRate() * (counterRead.getM6() - counterRead.getM5()));
                    }else{
                        temp.setM6(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM6() == 0) temp.setM6(0d);
                    else
                        temp.setM6(counterYear.getCounterRate() * (counterRead.getM6() - counterRead.getM5()));
                }else{
                      temp.setM6(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=7){
                        if (counterRead.getM7() == 0) temp.setM7(0d);
                        else
                            temp.setM7(counterYear.getCounterRate() * (counterRead.getM7() - counterRead.getM6()));
                    }else{
                        temp.setM7(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM7() == 0) temp.setM7(0d);
                    else
                        temp.setM7(counterYear.getCounterRate() * (counterRead.getM7() - counterRead.getM6()));
                }else{
                      temp.setM7(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=8){
                        if (counterRead.getM8() == 0) temp.setM8(0d);
                        else
                            temp.setM8(counterYear.getCounterRate() * (counterRead.getM8() - counterRead.getM7()));
                    }else{
                        temp.setM8(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM8() == 0) temp.setM8(0d);
                    else
                        temp.setM8(counterYear.getCounterRate() * (counterRead.getM8() - counterRead.getM7()));
                }else{
                      temp.setM8(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=9){
                        if (counterRead.getM9() == 0) temp.setM9(0d);
                        else
                            temp.setM9(counterYear.getCounterRate() * (counterRead.getM9() - counterRead.getM8()));
                    }else{
                        temp.setM9(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM9() == 0) temp.setM9(0d);
                    else
                        temp.setM9(counterYear.getCounterRate() * (counterRead.getM9() - counterRead.getM8()));
                }else{
                      temp.setM9(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=10){
                        if (counterRead.getM10() == 0) temp.setM10(0d);
                        else
                            temp.setM10(counterYear.getCounterRate() * (counterRead.getM10() - counterRead.getM9()));
                    }else{
                        temp.setM10(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM10() == 0) temp.setM10(0d);
                    else
                        temp.setM10(counterYear.getCounterRate() * (counterRead.getM10() - counterRead.getM9()));
                }else{
                      temp.setM10(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=11){
                        if (counterRead.getM11() == 0) temp.setM11(0d);
                        else
                            temp.setM11(counterYear.getCounterRate() * (counterRead.getM11() - counterRead.getM10()));
                    }else{
                        temp.setM11(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM11() == 0) temp.setM11(0d);
                    else
                        temp.setM11(counterYear.getCounterRate() * (counterRead.getM11() - counterRead.getM10()));
                }else{
                      temp.setM11(0d);
                    }}

                if(yearStart.intValue()==generateYear.intValue()){
                    if(monthStart<=12){
                        if (counterRead.getM12() == 0) temp.setM12(0d);
                        else
                            temp.setM12(counterYear.getCounterRate() * (counterRead.getM12() - counterRead.getM11()));
                    }else{
                        temp.setM12(0d);
                    }
                }else {
                    if(generateYear.intValue()>=yearStart.intValue()){
                    if (counterRead.getM12() == 0) temp.setM12(0d);
                    else
                        temp.setM12(counterYear.getCounterRate() * (counterRead.getM12() - counterRead.getM11()));
                }else{
                      temp.setM12(0d);
                    }}
                }
        } else if (item.getCompany() == false) {

            Integer month = item.getMonth();
            Integer year = item.getYear();

            if(year.equals(generateYear)){

            temp.setM1(month<=1 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 1) : 0);
            temp.setM2(month<=2 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 2):0);
            temp.setM3(month<=3 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 3):0);
            temp.setM4(month<=4 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 4):0);
            temp.setM5(month<=5 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 5):0);
            temp.setM6(month<=6 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 6):0);
            temp.setM7(month<=7 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 7):0);
            temp.setM8(month<=8 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 8):0);
            temp.setM9(month<=9 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 9):0);
            temp.setM10(month<=10 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 10):0);
            temp.setM11(month<=11 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 11):0);
            temp.setM12(month<=12 ? rateYear.getRate() * getPeopleCountFromLog(generateYear, item, 12):0);
        }
        else{
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

    private LogPartner getMaxLogPartnerNoMeter(Integer partnerId) throws SQLException {
        return logPartnerRepository.getLastMeterPartner(partnerId);
    }

    private Integer getMeterFromLog(Integer generateYear, Partner temp, Integer month) throws SQLException {
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
