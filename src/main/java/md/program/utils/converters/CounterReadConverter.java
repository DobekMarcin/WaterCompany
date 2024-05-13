package md.program.utils.converters;

import md.program.database.model.CounterRead;
import md.program.database.model.PaymentPlan;
import md.program.modelFX.CounterReadFX;
import md.program.modelFX.PaymentPlanFX;

public class CounterReadConverter {

    public static CounterRead convertToCounterRead(CounterReadFX counterReadFX){
        CounterRead counterRead = new CounterRead();
        counterRead.setId(counterReadFX.getId());
        counterRead.setPartnerId(counterReadFX.getPartnerId());
        counterRead.setYearId(counterReadFX.getYearId());
        counterRead.setPartner(PartnerConverter.convertToPartner(counterReadFX.getPartner()));
        counterRead.setM1(counterReadFX.getM1());
        counterRead.setM2(counterReadFX.getM2());
        counterRead.setM3(counterReadFX.getM3());
        counterRead.setM4(counterReadFX.getM4());
        counterRead.setM5(counterReadFX.getM5());
        counterRead.setM6(counterReadFX.getM6());
        counterRead.setM7(counterReadFX.getM7());
        counterRead.setM8(counterReadFX.getM8());
        counterRead.setM9(counterReadFX.getM9());
        counterRead.setM10(counterReadFX.getM10());
        counterRead.setM11(counterReadFX.getM11());
        counterRead.setM12(counterReadFX.getM12());
        return counterRead;
    }

    public static CounterReadFX convertToCounterReadFX(CounterRead counterRead){
        CounterReadFX counterReadFX = new CounterReadFX();
        counterReadFX.setId(counterRead.getId());
        counterReadFX.setPartnerId(counterRead.getPartnerId());
        counterReadFX.setPartner(PartnerConverter.convertToPartnerFX(counterRead.getPartner()));
        counterReadFX.setYearId(counterRead.getYearId());
        counterReadFX.setM1(counterRead.getM1());
        counterReadFX.setM2(counterRead.getM2());
        counterReadFX.setM3(counterRead.getM3());
        counterReadFX.setM4(counterRead.getM4());
        counterReadFX.setM5(counterRead.getM5());
        counterReadFX.setM6(counterRead.getM6());
        counterReadFX.setM7(counterRead.getM7());
        counterReadFX.setM8(counterRead.getM8());
        counterReadFX.setM9(counterRead.getM9());
        counterReadFX.setM10(counterRead.getM10());
        counterReadFX.setM11(counterRead.getM11());
        counterReadFX.setM12(counterRead.getM12());
        return counterReadFX;
    }

}
