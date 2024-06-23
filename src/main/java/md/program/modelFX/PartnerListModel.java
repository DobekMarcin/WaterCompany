package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.repository.PartnerRepository;
import md.program.stage.LoginStage;
import md.program.utils.converters.PartnerConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PartnerListModel {
    private ObservableList<PartnerFX> partnerFXObservableList = FXCollections.observableArrayList();
    private PartnerRepository partnerRepository = new PartnerRepository();
    private SimpleStringProperty filter = new SimpleStringProperty();
    private List<PartnerFX> partnerFXList = new ArrayList<>();
    private static final String JR_PRINT_ALL_PARTNER_PDF = "/JR_TEMPLATES/partnerList.jrxml";
    public void init() throws SQLException {
        List<Partner> partnerList = partnerRepository.getAllPartner();
        partnerFXList.clear();

        partnerList.forEach(item -> {
            PartnerFX partnerFX = PartnerConverter.convertToPartnerFX(item);
            partnerFXList.add(partnerFX);
        });
        partnerFXObservableList.setAll(partnerFXList);
        filterPartnerList();
    }
    public void filterPartnerList() {
        filterPredicate(predicateName().or(predicateAddress().or(predicateSurname().or(predicateID()).or(predicatePostCode()).or(predicatePost()).or(predicateNip()))));
    }
    private void filterPredicate(Predicate<PartnerFX> predicate) {
        List<PartnerFX> newList = partnerFXList.stream().filter(predicate).collect(Collectors.toList());
        partnerFXObservableList.setAll(newList);
    }
    private Predicate<PartnerFX> predicateName() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getName().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicateSurname() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getSurname().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicateAddress() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getAddress().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicatePostCode() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getPostCode().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicatePost() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getPost().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicateNip() {
        Predicate<PartnerFX> predicate = bookFX -> bookFX.getNip().toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    private Predicate<PartnerFX> predicateID() {
        Predicate<PartnerFX> predicate = bookFX -> String.valueOf(bookFX.getId()).toLowerCase().contains(filter.get().toString().toLowerCase());
        return predicate;
    }
    public ObservableList<PartnerFX> getPartnerFXObservableList() {
        return partnerFXObservableList;
    }

    public void setPartnerFXObservableList(ObservableList<PartnerFX> partnerFXObservableList) {
        this.partnerFXObservableList = partnerFXObservableList;
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

    public void printPartnerList() throws JRException, SQLException {
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(partnerFXObservableList.stream().toList());

        String filepath2= LoginStage.class.getResource(JR_PRINT_ALL_PARTNER_PDF).getPath();
        Map<String,Object> parameters = new HashMap<>();

        parameters.put("TestDataSet",jrBeanCollectionDataSource);
        JasperReport report = JasperCompileManager.compileReport(filepath2);
        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());

        JasperViewer jv=new JasperViewer(print,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
    }
}
