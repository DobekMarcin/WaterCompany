package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Company;
import md.program.database.repository.CompanyRepository;
import md.program.utils.converters.CompanyConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompanyListModel {

    private ObservableList<CompanyFX> companyFXObservableList = FXCollections.observableArrayList();
    private CompanyRepository companyRepository = new CompanyRepository();
    private SimpleStringProperty filter = new SimpleStringProperty();
    private List<CompanyFX> companyFXList = new ArrayList<>();
  //  private static final String JR_PRINT_ALL_PARTNER_PDF = "/JR_TEMPLATES/partnerList.jrxml";

    private CompanyFX newCompany = new CompanyFX();
    private CompanyFX deleteCompany = new CompanyFX();
    private CompanyFX editCompany = new CompanyFX();
    private CompanyFX chooseCompanyInvoice = new CompanyFX();

    public void init() throws SQLException {
        List<Company> partnerList = companyRepository.getAllCompany();
        companyFXList.clear();

        partnerList.forEach(item -> {
            CompanyFX companyFX = CompanyConverter.convertToCompanyFXX(item);
            companyFXList.add(companyFX);
        });
        companyFXObservableList.setAll(companyFXList);
        filterCompanyList();
    }

    public ObservableList<CompanyFX> getCompanyFXObservableList() {
        return companyFXObservableList;
    }

    public void setCompanyFXObservableList(ObservableList<CompanyFX> companyFXObservableList) {
        this.companyFXObservableList = companyFXObservableList;
    }

    public CompanyFX getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(CompanyFX newCompany) {
        this.newCompany = newCompany;
    }

    public int getNextId() throws SQLException {
        return companyRepository.getNextId();
    }

    public void addNewCompany() throws SQLException {
        companyRepository.addCompany(CompanyConverter.convertToCompany(getNewCompany()));
    }

    public Boolean deleteCompany() throws SQLException {
companyRepository.deleteCompanyById(CompanyConverter.convertToCompany(deleteCompany));
        return true;
    }
    public Boolean saveEditCompany() throws SQLException {
        companyRepository.updateCompany(CompanyConverter.convertToCompany(editCompany));
        return true;
    }

    public CompanyFX getDeleteCompany() {
        return deleteCompany;
    }

    public void setDeleteCompany(CompanyFX deleteCompany) {
        this.deleteCompany = deleteCompany;
    }

    public CompanyFX getEditCompany() {
        return editCompany;
    }

    public void setEditCompany(CompanyFX editCompany) {
        this.editCompany = editCompany;
    }

        public void filterCompanyList() {
        filterPredicate(predicateName().or(predicateAddress().or(predicatePlace().or(predicateID()).or(predicatePostCode()).or(predicatePost()).or(predicateNip()).or(predicateEmail()).or(predicatePhone()))));
    }
    private void filterPredicate(Predicate<CompanyFX> predicate) {
        List<CompanyFX> newList = companyFXList.stream().filter(predicate).collect(Collectors.toList());
        companyFXObservableList.setAll(newList);
    }
    private Predicate<CompanyFX> predicateName() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getName().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicatePlace() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getPlace().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicateAddress() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getAddress().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicatePostCode() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getPost_code().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicatePost() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getPost().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicateNip() {
        Predicate<CompanyFX> predicate = bookFX -> bookFX.getNip().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicateID() {
        Predicate<CompanyFX> predicate = bookFX -> String.valueOf(bookFX.getId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicatePhone() {
        Predicate<CompanyFX> predicate = bookFX -> String.valueOf(bookFX.getPhone()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<CompanyFX> predicateEmail() {
        Predicate<CompanyFX> predicate = bookFX -> String.valueOf(bookFX.getEmail()).toLowerCase().contains(filter.get().toString().toLowerCase());
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

    public CompanyFX getChooseCompanyInvoice() {
        return chooseCompanyInvoice;
    }

    public void setChooseCompanyInvoice(CompanyFX chooseCompanyInvoice) {
        this.chooseCompanyInvoice = chooseCompanyInvoice;
    }

    public Boolean checkInvoice() throws SQLException {
        int x=companyRepository.checkCompanyInInvoice(CompanyConverter.convertToCompany(deleteCompany));
        return x>0 ? true : false;
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
