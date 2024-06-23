package md.program.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.database.repository.CounterReadRepository;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CounterReadTableStageController {

    private static final String FXML_INITIALIZE_YEAR_COUNTER_READ_STAGE_FXML = "/FXML/InitializeYearCounterReadStage.fxml";
    private static final String FXML_COUNTER_READ_EDIT_TABLE_STAGE_FXML = "/FXML/CounterReadEditTableStage.fxml";

    @FXML
    private CheckBox companyFilter;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<CounterReadFX> counterReadTable;
    @FXML
    private TableColumn<CounterReadFX,Number> idColumn;
    @FXML
    private TableColumn<CounterReadFX,String> nameColumn;
    @FXML
    private TableColumn<CounterReadFX,String> surnameColumn;
    @FXML
    private TableColumn<CounterReadFX,String> m1Column;
    @FXML
    private TableColumn<CounterReadFX,String> m2Column;
    @FXML
    private TableColumn<CounterReadFX,String> m3Column;
    @FXML
    private TableColumn<CounterReadFX,String> m4Column;
    @FXML
    private TableColumn<CounterReadFX,String> m5Column;
    @FXML
    private TableColumn<CounterReadFX,String> m6Column;
    @FXML
    private TableColumn<CounterReadFX,String> m7Column;
    @FXML
    private TableColumn<CounterReadFX,String> m8Column;
    @FXML
    private TableColumn<CounterReadFX,String> m9Column;
    @FXML
    private TableColumn<CounterReadFX,String> m10Column;
    @FXML
    private TableColumn<CounterReadFX,String> m11Column;
    @FXML
    private TableColumn<CounterReadFX,String> m12Column;

    private CounterReadListModel counterReadListModel = new CounterReadListModel();
    private CounterReadRepository counterReadRepository = new CounterReadRepository();
    private Integer initializeYear = 0;
    private CounterReadModel counterReadModel = new CounterReadModel();
private SettingsModel settingsModel = new SettingsModel();


    public void init() {

        counterReadListModel.clearList();
        counterReadTable.refresh();
        counterReadListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
        tableInit();
        initComboBox();
        filterTextField.textProperty().addListener(observable -> counterReadListModel.filterPaymentList());
        try {
            Boolean isPaymentPlan =  counterReadModel.chceckCounterReadByDefayultYear(settingsModel.getDefaultYear());
            if(isPaymentPlan) {
                yearComboBox.getSelectionModel().select(settingsModel.getDefaultYear());
                selectYearOnAction();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initComboBox() {
        try {
            yearComboBox.setItems(counterReadModel.getAllYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        counterReadTable.setItems(counterReadListModel.getCounterReadFXObservableList());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getPartner().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartner().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartner().surnameProperty());
        m1Column.setCellValueFactory(cellData -> cellData.getValue().m1Property().asString());
        m2Column.setCellValueFactory(cellData -> cellData.getValue().m2Property().asString());
        m3Column.setCellValueFactory(cellData -> cellData.getValue().m3Property().asString());
        m4Column.setCellValueFactory(cellData -> cellData.getValue().m4Property().asString());
        m5Column.setCellValueFactory(cellData -> cellData.getValue().m5Property().asString());
        m6Column.setCellValueFactory(cellData -> cellData.getValue().m6Property().asString());
        m7Column.setCellValueFactory(cellData -> cellData.getValue().m7Property().asString());
        m8Column.setCellValueFactory(cellData -> cellData.getValue().m8Property().asString());
        m9Column.setCellValueFactory(cellData -> cellData.getValue().m9Property().asString());
        m10Column.setCellValueFactory(cellData -> cellData.getValue().m10Property().asString());
        m11Column.setCellValueFactory(cellData -> cellData.getValue().m11Property().asString());
        m12Column.setCellValueFactory(cellData -> cellData.getValue().m12Property().asString());

        List<TableColumn> tableColumns = Arrays.asList(m1Column, m2Column, m3Column, m4Column, m5Column, m6Column, m7Column, m8Column, m9Column, m10Column, m11Column, m12Column);

        tableColumns.forEach(item -> {
            item.setCellFactory(param -> new TableCell<RateYearFX, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        setText(Utils.getDecimalFormatWithTwoPlaces().format(Double.valueOf(item)));
                    }
                }
            });
        });
    }

    public void selectYearOnAction() {
        Integer planYear = yearComboBox.getSelectionModel().getSelectedItem();
        try {
            if (planYear != null) counterReadListModel.init(planYear);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editCounterReadOnAction() {
        CounterReadFX counterReadFX = counterReadTable.getSelectionModel().getSelectedItem();
        if (counterReadFX != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_COUNTER_READ_EDIT_TABLE_STAGE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("counter.read.edit.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            CounterReadEditTableStageController counterReadEditTableStageController = fxmlLoader.getController();
            counterReadEditTableStageController.setStage(stage1);
            counterReadEditTableStageController.getCounterReadModel().setCounterReadFX(counterReadFX);
            counterReadEditTableStageController.init();
            stage1.showAndWait();
            selectYearOnAction();
       //     paymentTable.getSelectionModel().select(paymentPlanListModel.getPaymentPlanFXObservableList().filtered(partnerFX1 -> partnerFX1.getId() == paymentPlanFX.getId()).get(0));
       //     paymentTable.scrollTo(paymentTable.getSelectionModel().getSelectedItem());
        }
    }


    public void initYearCounterReadOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_INITIALIZE_YEAR_COUNTER_READ_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("counter.read.initialize.year"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        InitializeYearCounterReadStageController initializeYearCounterReadStageController = fxmlLoader.getController();
        initializeYearCounterReadStageController.setThisStage(stage1);
        initializeYearCounterReadStageController.setCounterReadTableStageController(this);
        stage1.showAndWait();
        initComboBox();
        yearComboBox.getSelectionModel().select(initializeYear);
        selectYearOnAction();
    }

    public Integer getInitializeYear() {
        return initializeYear;
    }

    public void setInitializeYear(Integer initializeYear) {
        this.initializeYear = initializeYear;
    }

    public void deleteCounterReadOneOnAction() {
        CounterReadFX counterReadFX = counterReadTable.getSelectionModel().getSelectedItem();
        if(counterReadFX!= null){
        try {

            counterReadModel.deleteCounterReadOnePerson(counterReadFX);
            yearComboBox.getSelectionModel().select(yearComboBox.getSelectionModel().getSelectedItem());
            selectYearOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
    }

    public void deleteCounterReadYearOnAction() {
        Integer year = yearComboBox.getSelectionModel().getSelectedItem();
        if (year>0 && year!= null) {
            try {
                counterReadRepository.deleteCounterReadByYear(year);
            } catch (SQLException | NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        init();
        initComboBox();
    }
}
