package md.program.controller.company;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.database.repository.CompanyRepository;
import md.program.modelFX.CompanyListModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class CompanyTableAddStageController {

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
    private CompanyListModel companyListModel = new CompanyListModel();

    private CompanyRepository companyRepository = new CompanyRepository();

    public void init() {
        idTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().idProperty(),new NumberStringConverter());
        nameTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().nameProperty());
        nipTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().nipProperty());
        placeTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().placeProperty());
        postCodeTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().post_codeProperty());
        postTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().postProperty());
        addressTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().addressProperty());
        phoneTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().phoneProperty());
        emailTextField.textProperty().bindBidirectional(companyListModel.getNewCompany().emailProperty());

        try {
            companyListModel.getNewCompany().setId(companyListModel.getNextId());
            companyListModel.getNewCompany().setName("");
            companyListModel.getNewCompany().setNip("");
            companyListModel.getNewCompany().setPlace("");
            companyListModel.getNewCompany().setPost_code("");
            companyListModel.getNewCompany().setPost("");
            companyListModel.getNewCompany().setAddress("");
            companyListModel.getNewCompany().setPhone("");
            companyListModel.getNewCompany().setEmail("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void addButtonOnAction() {
        if(companyListModel.getNewCompany().getName().isEmpty() || companyListModel.getNewCompany().getNip().isEmpty()){
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "company.add.dialog");
        }else{
            try {
                companyListModel.addNewCompany();
                thisStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }
}
