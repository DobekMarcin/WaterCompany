package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Invoice;
import md.program.database.repository.InvoiceRepository;
import md.program.utils.converters.InvoiceConverter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InvoiceListModel {

    private ObservableList<InvoiceFX> invoiceFXObservableList = FXCollections.observableArrayList();
    private InvoiceRepository invoiceRepository = new InvoiceRepository();
    private SimpleStringProperty filter = new SimpleStringProperty();
    private List<InvoiceFX> invoiceFXList = new ArrayList<>();
    //  private static final String JR_PRINT_ALL_PARTNER_PDF = "/JR_TEMPLATES/partnerList.jrxml";

    private InvoiceFX newInvoice = new InvoiceFX();
    private InvoiceFX deleteInvoice = new InvoiceFX();
    private InvoiceFX editInvoice = new InvoiceFX();
    private int yearOfInvoice=0;


    public void init() throws SQLException {
        List<Invoice> invoiceList = invoiceRepository.getAllInvoice(yearOfInvoice);
        invoiceFXList.clear();

        invoiceList.forEach(item -> {
            InvoiceFX invoiceFX = InvoiceConverter.convertToInvoiceFX(item);
            invoiceFXList.add(invoiceFX);
        });
        invoiceFXObservableList.setAll(invoiceFXList);
        filterInvoiceList();
    }

    public Boolean deleteCompany() throws SQLException, ParseException {
        invoiceRepository.deleteInvoiceById(InvoiceConverter.convertToInvoice(deleteInvoice));
        return true;
    }


    public InvoiceFX getNewInvoice() {
        return newInvoice;
    }

    public void setNewInvoice(InvoiceFX newInvoice) {
        this.newInvoice = newInvoice;
    }

    public InvoiceFX getDeleteInvoice() {
        return deleteInvoice;
    }

    public void setDeleteInvoice(InvoiceFX deleteInvoice) {
        this.deleteInvoice = deleteInvoice;
    }

    public InvoiceFX getEditInvoice() {
        return editInvoice;
    }

    public void setEditInvoice(InvoiceFX editInvoice) {
        this.editInvoice = editInvoice;
    }




    //    public CompanyFX getNewCompany() {
//        return newCompany;
//    }
//
//    public void setNewCompany(CompanyFX newCompany) {
//        this.newCompany = newCompany;
//    }
//
//    public int getNextId() throws SQLException {
//        return companyRepository.getNextId();
//    }
//
//    public void addNewCompany() throws SQLException {
//        companyRepository.addCompany(CompanyConverter.convertToCompany(getNewCompany()));
//    }
//

//    public Boolean saveEditCompany() throws SQLException {
//        companyRepository.updateCompany(CompanyConverter.convertToCompany(editCompany));
//        return true;
//    }
//
//    public CompanyFX getDeleteCompany() {
//        return deleteCompany;
//    }
//
//    public void setDeleteCompany(CompanyFX deleteCompany) {
//        this.deleteCompany = deleteCompany;
//    }
//
//    public CompanyFX getEditCompany() {
//        return editCompany;
//    }
//
//    public void setEditCompany(CompanyFX editCompany) {
//        this.editCompany = editCompany;
//    }


    public ObservableList<InvoiceFX> getInvoiceFXObservableList() {
        return invoiceFXObservableList;
    }

    public void setInvoiceFXObservableList(ObservableList<InvoiceFX> invoiceFXObservableList) {
        this.invoiceFXObservableList = invoiceFXObservableList;
    }

    public int getNextId() throws SQLException {
        return invoiceRepository.getNextId(yearOfInvoice);
    }

    public void addNewInvoice() throws SQLException, ParseException {
        invoiceRepository.addInvoice(InvoiceConverter.convertToInvoice(newInvoice));
    }

    public void saveInvoice() throws ParseException, SQLException {
        invoiceRepository.updateInvoice(InvoiceConverter.convertToInvoice(editInvoice));
    }

    public int getYearOfInvoice() {
        return yearOfInvoice;
    }

    public void setYearOfInvoice(int yearOfInvoice) {
        this.yearOfInvoice = yearOfInvoice;
    }


    public void filterInvoiceList() {
        filterPredicate(predicateID().or(predicateInvoiceNumber().or(predicateInvoiceDate().or(predicateCompanyName()).or(predicateCompanyId()).or(predicateInvoiceDes()).or(predicateInvoiceAmount()))));
    }
    private void filterPredicate(Predicate<InvoiceFX> predicate) {
        List<InvoiceFX> newList = invoiceFXList.stream().filter(predicate).collect(Collectors.toList());
        invoiceFXObservableList.setAll(newList);
    }
    private Predicate<InvoiceFX> predicateID() {
        Predicate<InvoiceFX> predicate = bookFX -> String.valueOf(bookFX.getId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateInvoiceNumber() {
        Predicate<InvoiceFX> predicate = bookFX -> bookFX.getInvoiceNumber().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateInvoiceDate() {
        Predicate<InvoiceFX> predicate = bookFX -> bookFX.getInvoiceDate().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateCompanyName() {
        Predicate<InvoiceFX> predicate = bookFX -> bookFX.getCompanyName().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateCompanyId() {
        Predicate<InvoiceFX> predicate = bookFX -> String.valueOf(bookFX.getCompanyId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateInvoiceDes() {
        Predicate<InvoiceFX> predicate = bookFX -> bookFX.getInvoiceDes().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<InvoiceFX> predicateInvoiceAmount() {
        Predicate<InvoiceFX> predicate = bookFX -> String.valueOf(bookFX.getAmount()).toLowerCase().contains(filter.get().toString().toLowerCase());
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

//    public void printPartnerList() throws JRException, SQLException {
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(partnerFXObservableList.stream().toList());
//
//        InputStream file = getClass().getResourceAsStream(JR_PRINT_ALL_PARTNER_PDF);
//        JasperDesign jasperDesign = JRXmlLoader.load(file);
//
//        // String filepath2= getClass().getResource(JR_PRINT_ALL_PARTNER_PDF).getPath();
//        Map<String,Object> parameters = new HashMap<>();
//
//        parameters.put("TestDataSet",jrBeanCollectionDataSource);
//        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
//        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());
//
//        JasperViewer jv=new JasperViewer(print,false);
//        jv.setTitle("Challan");
//        jv.setVisible(true);
//    }
}
