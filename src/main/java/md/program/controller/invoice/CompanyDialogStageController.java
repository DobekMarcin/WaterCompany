package md.program.controller.invoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.CompanyFX;
import md.program.modelFX.CompanyListModel;

import java.sql.SQLException;

public class CompanyDialogStageController {
    @FXML
    private TableView<CompanyFX> companyTable;
    @FXML
    private TableColumn<CompanyFX, Number> nrColumn;
    @FXML
    private TableColumn<CompanyFX, String> nameColumn;
    @FXML
    private TextField filterTextField;

    private CompanyListModel companyListModel;

    private Stage thisStage;

    public void init() {
        try {
            companyListModel.filterProperty().bindBidirectional(filterTextField.textProperty());
            companyListModel.init();
            tableInit();

            filterTextField.textProperty().addListener(observable -> companyListModel.filterCompanyList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        companyTable.setItems(companyListModel.getCompanyFXObservableList());

        nrColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
    }

    public void chooseCompanyButtonOnAction() {
        if (companyTable.getSelectionModel().getSelectedItem() != null) {
            CompanyFX companyFX = companyTable.getSelectionModel().getSelectedItem();
            companyListModel.setChooseCompanyInvoice(companyFX);

            thisStage.close();
        }
    }

    public void closeButtonOnAction() {
        thisStage.close();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public CompanyListModel getCompanyListModel() {
        return companyListModel;
    }

    public void setCompanyListModel(CompanyListModel companyListModel) {
        this.companyListModel = companyListModel;
    }
}
