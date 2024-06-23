package md.program.modelFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import md.program.database.model.LogPartner;
import md.program.database.model.Partner;
import md.program.database.repository.LogPartnerRepository;
import md.program.database.repository.PartnerRepository;
import md.program.stage.LoginStage;
import md.program.utils.converters.LogPartnerConverter;
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

public class LogPartnerListModel {
    private ObservableList<LogPartnerFX> logPartnerFXObservableList = FXCollections.observableArrayList();
    private LogPartnerRepository logPartnerRepository = new LogPartnerRepository();
    private List<LogPartnerFX> logPartnerFXES = new ArrayList<>();

    private LogPartnerFX logPartnerFX;

    private Integer idPartnera =0;
    public void init() throws SQLException {
        List<LogPartner> logPartnerList = logPartnerRepository.getAllLogForPartner(idPartnera);
        logPartnerFXES.clear();

        logPartnerList.forEach(item -> {
            LogPartnerFX logPartnerFX = LogPartnerConverter.convertToLogPartnerFX(item);
            logPartnerFXES.add(logPartnerFX);
        });
        logPartnerFXObservableList.setAll(logPartnerFXES);
    }

    public ObservableList<LogPartnerFX> getLogPartnerFXObservableList() {
        return logPartnerFXObservableList;
    }

    public void setLogPartnerFXObservableList(ObservableList<LogPartnerFX> logPartnerFXObservableList) {
        this.logPartnerFXObservableList = logPartnerFXObservableList;
    }

    public Integer getIdPartnera() {
        return idPartnera;
    }

    public void setIdPartnera(Integer idPartnera) {
        this.idPartnera = idPartnera;
    }

    public LogPartnerFX getLogPartnerFX() {
        return logPartnerFX;
    }

    public void setLogPartnerFX(LogPartnerFX logPartnerFX) {
        this.logPartnerFX = logPartnerFX;
    }

    public Boolean deletePartner() throws SQLException {
        LogPartner logPartner = LogPartnerConverter.convertToLogPartner(this.logPartnerFX);
        return logPartnerRepository.deleteLogPartner(logPartner);
    }

    public void saveData() throws SQLException {
        LogPartner logPartner = LogPartnerConverter.convertToLogPartner(logPartnerFX);
        logPartnerRepository.saveDate(logPartner);
    }
}
