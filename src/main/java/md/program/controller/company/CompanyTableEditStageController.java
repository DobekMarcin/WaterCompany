package md.program.controller.company;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.database.repository.CompanyRepository;
import md.program.modelFX.CompanyListModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class CompanyTableEditStageController {

    public TextField idTextField;
    public TextField nameTextField;
    public TextField nipTextField;
    public TextField placeTextField;
    public TextField postCodeTextField;
    public TextField postTextField;
    public TextField addressTextField;
    public TextField phoneTextField;
    public TextField emailTextField;
    private Stage thisStage;
    private CompanyListModel companyListModel;

    private CompanyRepository companyRepository = new CompanyRepository();

    public void init() {
        idTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().idProperty(),new NumberStringConverter());
        nameTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().nameProperty());
        nipTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().nipProperty());
        placeTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().placeProperty());
        postCodeTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().post_codeProperty());
        postTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().postProperty());
        addressTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().addressProperty());
        phoneTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().phoneProperty());
        emailTextField.textProperty().bindBidirectional(companyListModel.getEditCompany().emailProperty());

    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        if(companyListModel.getEditCompany().getName().isEmpty() || companyListModel.getEditCompany().getNip().isEmpty()){
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "company.add.dialog");
        }else{
            try {
                companyListModel.saveEditCompany();
                thisStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public CompanyListModel getCompanyListModel() {
        return companyListModel;
    }

    public void setCompanyListModel(CompanyListModel companyListModel) {
        this.companyListModel = companyListModel;
    }
}
