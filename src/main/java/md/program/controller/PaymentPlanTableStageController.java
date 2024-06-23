package md.program.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.database.repository.PaymentPlanRepository;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.Utils;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentPlanTableStageController {
    private static final String FXML_PAYMENT_PLAN_EDIT_STAGE_FXML = "/FXML/PaymentPlanTableEditStage.fxml";
    private static final String FXML_PAYMENT_PLAN_INITIALIZE_NEW_YEAR_FXML = "/FXML/InitializePaymentPlanStage.fxml";
    public ComboBox<Integer> yearComboBox;
    @FXML
    private CheckBox companyFilter;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<PaymentPlanFX> paymentTable;
    @FXML
    private TableColumn<PaymentPlanFX, Number> idColumn;
    @FXML
    private TableColumn<PaymentPlanFX, String> nameColumn;
    @FXML
    private TableColumn<PaymentPlanFX, String> surnameColumn;
    @FXML
    private TableColumn<PaymentPlanFX, String> m1Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m2Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m3Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m4Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m5Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m6Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m7Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m8Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m9Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m10Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m11Column;
    @FXML
    private TableColumn<PaymentPlanFX, String> m12Column;

    private PaymentPlanListModel paymentPlanListModel = new PaymentPlanListModel();
    private PaymentPlanModel paymentPlanModel = new PaymentPlanModel();
    private PaymentPlanRepository paymentPlanRepository = new PaymentPlanRepository();
    private Integer initializeYear = 0;
private SettingsModel settingsModel = new SettingsModel();

    public void init() {
        paymentPlanListModel.clearList();
        paymentTable.refresh();
        paymentPlanListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
        paymentPlanListModel.companyFilterProperty().bindBidirectional(companyFilter.selectedProperty());
        tableInit();
        initComboBox();
        filterTextField.textProperty().addListener(observable -> paymentPlanListModel.filterPaymentList());
        companyFilter.selectedProperty().addListener(observable -> {
            paymentPlanListModel.filterPaymentList();
        });
        try {
         Boolean isPaymentPlan = paymentPlanModel.chceckPaymentPlanByDefayultYear(settingsModel.getDefaultYear());
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
            yearComboBox.setItems(paymentPlanModel.getAllYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableInit() {
        paymentTable.setItems(paymentPlanListModel.getPaymentPlanFXObservableList());
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

    @FXML
    public void selectYearOnAction() {
        Integer planYear = yearComboBox.getSelectionModel().getSelectedItem();
        try {
            if (planYear != null) paymentPlanListModel.init(planYear);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void editPaymentPlanOnAction() {
        PaymentPlanFX paymentPlanFX = paymentTable.getSelectionModel().getSelectedItem();
        if (paymentPlanFX != null)
            if (paymentPlanFX.getPartner().isCompany()) {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PAYMENT_PLAN_EDIT_STAGE_FXML));
                fxmlLoader.setResources(Utils.getResourceBundle());
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setTitle(Utils.getResourceBundle().getString("payment.plan.edit.title"));
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setResizable(false);
                PaymentPlanEditStageController paymentPlanEditStageController = fxmlLoader.getController();
                paymentPlanEditStageController.setStage(stage1);
                paymentPlanEditStageController.getPaymentPlanModel().setPaymentPlanFX(paymentPlanFX);
                paymentPlanEditStageController.init();
                stage1.showAndWait();
                selectYearOnAction();
                paymentTable.getSelectionModel().select(paymentPlanListModel.getPaymentPlanFXObservableList().filtered(partnerFX1 -> partnerFX1.getId() == paymentPlanFX.getId()).get(0));
                paymentTable.scrollTo(paymentTable.getSelectionModel().getSelectedItem());
            }
    }

    @FXML
    public void paymentPlanPrintOnAction() {
        try {
            paymentPlanListModel.printPaymentPlanList();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void paymentPlanBillOcAction() {
        try {
            paymentPlanListModel.printPaymentPlanBill();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void generatePaymentPlanOnAction() {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PAYMENT_PLAN_INITIALIZE_NEW_YEAR_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("payment.table.generate"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        InitializePaymentPlanStageController initializePaymentPlanStageController = fxmlLoader.getController();
        initializePaymentPlanStageController.setThisStage(stage1);
        initializePaymentPlanStageController.setPaymentPlanTableStageController(this);
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

    public void deletePaymentPlanOnAction() {
        try {
            Integer year = yearComboBox.getSelectionModel().getSelectedItem();
            if (year>0) paymentPlanRepository.deletePaymnetPlanByYear(year);
            init();
            initComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generatePaymentPlanOneOnAction() {
        PaymentPlanFX paymentPlanFX = paymentTable.getSelectionModel().getSelectedItem();
        try {
            if(paymentPlanFX!=null) {
                paymentPlanModel.generatePaymentPlanOnePerson(yearComboBox.getSelectionModel().getSelectedItem(), paymentPlanFX);
                yearComboBox.getSelectionModel().select(yearComboBox.getSelectionModel().getSelectedItem());
                selectYearOnAction();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePaymentPlanOneOnAction() {
        PaymentPlanFX paymentPlanFX = paymentTable.getSelectionModel().getSelectedItem();
        try {
            paymentPlanModel.deletePaymentPlanOnePerson(paymentPlanFX);
            yearComboBox.getSelectionModel().select(yearComboBox.getSelectionModel().getSelectedItem());
            selectYearOnAction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
