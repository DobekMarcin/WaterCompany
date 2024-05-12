package md.program.modelFX;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.PaymentPlanRepository;
import md.program.print.PaymentBillPrint;
import md.program.print.PaymentPlanPrint;
import md.program.stage.LoginStage;
import md.program.utils.converters.PartnerConverter;
import md.program.utils.converters.PaymentPlanConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PaymentPlanListModel {
    private static final String JR_PRINT_PAYMENT_PLAN_PDF = "/JR_TEMPLATES/paymentPlanList.jrxml";
    private static final String JR_PRINT_PAYMENT_BILL_PDF = "/JR_TEMPLATES/paymentBill.jrxml";
    private ObservableList<PaymentPlanFX> paymentPlanFXObservableList = FXCollections.observableArrayList();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private List<PaymentPlanFX> paymentPlanFXList = new ArrayList<>();
    private SimpleStringProperty filter = new SimpleStringProperty();
    private SimpleBooleanProperty companyFilter= new SimpleBooleanProperty();

    public void init(RateYearFX rateYearFX) throws SQLException {
        List<PaymentPlan> paymentPlanList = paymentPlanRepository.getAllPaymentPlanByYear(rateYearFX.getId());
        for (PaymentPlan paymentPlan : paymentPlanList) {
            paymentPlan.setPartner(partnerRepository.getPartnerById(paymentPlan.getPartnerId()));
        }
        paymentPlanFXList.clear();
        paymentPlanList.forEach(item->{
            PaymentPlanFX paymentPlanFX = PaymentPlanConverter.convertToPaymentPlanFX(item);
            paymentPlanFXList.add(paymentPlanFX);
        });
        paymentPlanFXObservableList.setAll(paymentPlanFXList);
        filterPaymentList();
    }

    public ObservableList<PaymentPlanFX> getPaymentPlanFXObservableList() {
        return paymentPlanFXObservableList;
    }

    public void setPaymentPlanFXObservableList(ObservableList<PaymentPlanFX> paymentPlanFXObservableList) {
        this.paymentPlanFXObservableList = paymentPlanFXObservableList;
    }

    public void filterPaymentList() {
     //   filterPredicate((predicateCompany().and((predicateName()))).or(predicateCompany().and((predicateSurname()))).or(predicateCompany().and((predicateID()))));
        filterPredicate(predicateName().or(predicateSurname().or(predicateID())));
    }


    private void filterPredicate(Predicate<PaymentPlanFX> predicate) {
        List<PaymentPlanFX> newList = paymentPlanFXList.stream().filter(predicate).collect(Collectors.toList());
        paymentPlanFXObservableList.setAll(newList);
    }
    private Predicate<PaymentPlanFX> predicateName() {
        Predicate<PaymentPlanFX> predicate = paymentFX -> paymentFX.getPartner().getName().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PaymentPlanFX> predicateSurname() {
        Predicate<PaymentPlanFX> predicate = paymentFX -> paymentFX.getPartner().getSurname().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PaymentPlanFX> predicateID() {
        Predicate<PaymentPlanFX> predicate = bookFX -> String.valueOf(bookFX.getPartnerId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PaymentPlanFX> predicateCompany() {
        Predicate<PaymentPlanFX> predicate = paymentFX -> Boolean.compare(paymentFX.getPartner().isCompany(), companyFilterProperty().get())==0?true:false;
        return predicate;
    }

    public String getFilter() {
        return filter.get();
    }

    public SimpleStringProperty filterProperty() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter.set(filter);
    }

    public boolean isCompanyFilter() {
        return companyFilter.get();
    }

    public SimpleBooleanProperty companyFilterProperty() {
        return companyFilter;
    }

    public void setCompanyFilter(boolean companyFilter) {
        this.companyFilter.set(companyFilter);
    }

    public void printPaymentPlanList() throws JRException, SQLException {


        List<PaymentPlanFX> listFX = paymentPlanFXObservableList.stream().toList();
        List<PaymentPlan> list = new ArrayList<>();
        List<PaymentPlanPrint> printList = new ArrayList<>();
        listFX.forEach(item->{
            PaymentPlan temp = PaymentPlanConverter.convertToPaymentPlan(item);
            list.add(temp);
        });
        list.forEach(item->{
            PaymentPlanPrint temp = new PaymentPlanPrint();
            temp.setId(item.getPartnerId());
            temp.setName(item.getPartner().getName());
            temp.setSurname(item.getPartner().getSurname());
            temp.setPartnerId(item.getPartnerId());
            temp.setAddress(item.getPartner().getAddress());
            temp.setYearId(item.getYearId());
            temp.setPeopleCount(item.getPartner().getPeopleCount());
            temp.setM1(item.getM1());
            temp.setM2(item.getM2());
            temp.setM3(item.getM3());
            temp.setM4(item.getM4());
            temp.setM5(item.getM5());
            temp.setM6(item.getM6());
            temp.setM7(item.getM7());
            temp.setM8(item.getM8());
            temp.setM9(item.getM9());
            temp.setM10(item.getM10());
            temp.setM11(item.getM11());
            temp.setM12(item.getM12());
            printList.add(temp);
        });
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(printList);
        String filepath2= LoginStage.class.getResource(JR_PRINT_PAYMENT_PLAN_PDF).getPath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("firstName","Aleksandra");
        parameters.put("lastName","Dobek");
        parameters.put("dob","13.12.1999");
        parameters.put("age",28);
        parameters.put("TestDataSet",jrBeanCollectionDataSource);
        JasperReport report = JasperCompileManager.compileReport(filepath2);
        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());

        JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\reina\\IdeaProjects\\JR\\src\\main\\resources\\static\\raport.pdf");
        JasperViewer jv=new JasperViewer(print,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
    }

    public void printPaymentPlanBill() throws JRException, SQLException {


        List<PaymentPlanFX> listFX = paymentPlanFXObservableList.stream().toList();
        List<PaymentPlan> list = new ArrayList<>();
        List<PaymentBillPrint> printList = new ArrayList<>();
        listFX.forEach(item->{
            PaymentPlan temp = PaymentPlanConverter.convertToPaymentPlan(item);
            list.add(temp);
        });
        list.forEach(item->{
            PaymentBillPrint temp = new PaymentBillPrint();
            temp.setName(item.getPartner().getName());
            temp.setSurname(item.getPartner().getSurname());
            temp.setAddress(item.getPartner().getAddress());
            temp.setKw1(item.getM1()+item.getM2()+item.getM3());
            temp.setKw2(item.getM4()+item.getM5()+item.getM6());
            temp.setKw3(item.getM7()+item.getM8()+item.getM9());
            temp.setKw4(item.getM10()+item.getM11()+item.getM12());

            printList.add(temp);
        });
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(printList);
        String filepath2= LoginStage.class.getResource(JR_PRINT_PAYMENT_BILL_PDF).getPath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("firstName","Aleksandra");
        parameters.put("lastName","Dobek");
        parameters.put("dob","13.12.1999");
        parameters.put("age",28);
        parameters.put("TestDataSet",jrBeanCollectionDataSource);
        JasperReport report = JasperCompileManager.compileReport(filepath2);
        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());

        JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\reina\\IdeaProjects\\JR\\src\\main\\resources\\static\\raport.pdf");
        JasperViewer jv=new JasperViewer(print,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
    }
}
