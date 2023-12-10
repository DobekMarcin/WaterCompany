package md.program.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import md.program.database.repository.PaymentPlanRepository;
import md.program.modelFX.PaymentPlanFX;
import md.program.modelFX.PaymentPlanListModel;
import md.program.modelFX.RateYearFX;
import md.program.modelFX.RateYearListModel;

import java.sql.SQLException;

public class PaymentPlanTableStageController {
    public ComboBox<RateYearFX> yearComboBox;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<PaymentPlanFX> paymentTable;
    @FXML
    private TableColumn<PaymentPlanFX,Number> idColumn;
    @FXML
    private TableColumn<PaymentPlanFX,String> nameColumn;
    @FXML
    private TableColumn<PaymentPlanFX,String> surnameColumn;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m1Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m2Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m3Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m4Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m5Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m6Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m7Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m8Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m9Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m10Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m11Column;
    @FXML
    private TableColumn<PaymentPlanFX,Number> m12Column;

    private PaymentPlanListModel paymentPlanListModel = new PaymentPlanListModel();
    private RateYearListModel rateYearListModel = new RateYearListModel();
    public void init(){
            tableInit();
            initComboBox();
            initFilter();
    }

    private void initFilter() {
        FilteredList<PaymentPlanFX> filteredData = new FilteredList<>(paymentPlanListModel.getPaymentPlanFXObservableList(), p->true);
        filterTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(paymentPlan->{
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(paymentPlan.getPartner().getName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (paymentPlan.getPartner().getSurname().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (String.valueOf(paymentPlan.getPartnerId()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<PaymentPlanFX> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(paymentTable.comparatorProperty());
        paymentTable.setItems(sortedList);
    }

    public void initComboBox(){
        try {
            rateYearListModel.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        yearComboBox.setItems(rateYearListModel.getRateYearFXObservableList());
    }

    private void tableInit() {
        paymentTable.setItems(paymentPlanListModel.getPaymentPlanFXObservableList());
        idColumn.setCellValueFactory(cellData->cellData.getValue().partnerIdProperty());
        nameColumn.setCellValueFactory(cellData->cellData.getValue().getPartner().nameProperty());
        surnameColumn.setCellValueFactory(cellData->cellData.getValue().getPartner().surnameProperty());
        m1Column.setCellValueFactory(cellData->cellData.getValue().m1Property());
        m2Column.setCellValueFactory(cellData->cellData.getValue().m2Property());
        m3Column.setCellValueFactory(cellData->cellData.getValue().m3Property());
        m4Column.setCellValueFactory(cellData->cellData.getValue().m4Property());
        m5Column.setCellValueFactory(cellData->cellData.getValue().m5Property());
        m6Column.setCellValueFactory(cellData->cellData.getValue().m6Property());
        m7Column.setCellValueFactory(cellData->cellData.getValue().m7Property());
        m8Column.setCellValueFactory(cellData->cellData.getValue().m8Property());
        m9Column.setCellValueFactory(cellData->cellData.getValue().m9Property());
        m10Column.setCellValueFactory(cellData->cellData.getValue().m10Property());
        m11Column.setCellValueFactory(cellData->cellData.getValue().m11Property());
        m12Column.setCellValueFactory(cellData->cellData.getValue().m12Property());
    }

    @FXML
    public void selectYearOnAction() {
    RateYearFX rateYearFX=yearComboBox.getSelectionModel().getSelectedItem();
        try {
            paymentPlanListModel.init(rateYearFX);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
