package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.Partner;
import md.program.database.model.PartnerBO;
import md.program.database.repository.PartnerBORepository;
import md.program.database.repository.PartnerRepository;
import md.program.database.repository.SettingsRepository;
import md.program.utils.converters.PartnerBOConverter;
import md.program.utils.converters.PartnerConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PartnerBOListModel {
    private ObservableList<PartnerBOFX> partnerFXObservableList = FXCollections.observableArrayList();
    private PartnerBORepository partnerBORepository = new PartnerBORepository();
    private SimpleStringProperty filter = new SimpleStringProperty();
    private List<PartnerBOFX> partnerBOFXList = new ArrayList<>();
    private SettingsRepository settingsRepository = new SettingsRepository();
    private Integer yearBO;


    public void initYear(){
        try {
            yearBO= settingsRepository.getBOYear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void init() throws SQLException {
        List<PartnerBO> partnerBOList = partnerBORepository.getAllPartner();
        partnerBOFXList.clear();

        partnerBOList.forEach(item -> {
            PartnerBOFX partnerFX = PartnerBOConverter.convertToPartnerBOFX(item);
            partnerBOFXList.add(partnerFX);
        });
        partnerFXObservableList.setAll(partnerBOFXList);

    }

    public Boolean generateBO() throws SQLException {
        return partnerBORepository.insertBOFromSelect();
    }

    /*
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

        InputStream file = getClass().getResourceAsStream(JR_PRINT_ALL_PARTNER_PDF);
        JasperDesign jasperDesign = JRXmlLoader.load(file);

       // String filepath2= getClass().getResource(JR_PRINT_ALL_PARTNER_PDF).getPath();
        Map<String,Object> parameters = new HashMap<>();

        parameters.put("TestDataSet",jrBeanCollectionDataSource);
        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());

        JasperViewer jv=new JasperViewer(print,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
    }*/

    public Integer getYearBO() {
        return yearBO;
    }

    public void setYearBO(Integer yearBO) {
        this.yearBO = yearBO;
    }

    public ObservableList<PartnerBOFX> getPartnerFXObservableList() {
        return partnerFXObservableList;
    }

    public void setPartnerFXObservableList(ObservableList<PartnerBOFX> partnerFXObservableList) {
        this.partnerFXObservableList = partnerFXObservableList;
    }
}
