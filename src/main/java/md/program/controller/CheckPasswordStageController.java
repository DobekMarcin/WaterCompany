package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import md.program.database.repository.UserRepository;
import md.program.modelFX.PartnerBOListModel;
import md.program.modelFX.UserModel;

import java.sql.SQLException;

public class CheckPasswordStageController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button okButton;
    @FXML
    private Label noLogLabel;
    @FXML
    private Button cancelButton;
    private Stage thisStage;

private UserModel userModel = new UserModel();
private PartnerBOListModel partnerBOListModel = new PartnerBOListModel();

public void init(){
    passwordTextField.textProperty().addListener(observable -> noLogLabel.setText(""));
}
    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void acceptOnAction() {
        try {
          Boolean temp=  userModel.checkPassword(passwordTextField.textProperty().get());
          if(temp==true){
              partnerBOListModel.deleteCloseBO();
              thisStage.close();
          }else{
              noLogLabel.setText("Podajesz błędne hasło!");
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelOnAction() {
        thisStage.close();
    }
}
