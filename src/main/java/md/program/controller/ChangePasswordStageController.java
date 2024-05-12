package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import md.program.modelFX.ChangePasswordModel;
import md.program.utils.Utils;

import java.sql.SQLException;

public class ChangePasswordStageController {
    @FXML
    private Label noLogLabel;
    @FXML
    private PasswordField newPasswordTextFieldAccept;
    private Stage stage;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private TextField oldPasswordTextField;

    private ChangePasswordModel changePasswordModel = new ChangePasswordModel();

    public void init(){
        bindings();
        setListeners();
    }

    private void setListeners(){
        newPasswordTextField.textProperty().addListener(observable -> noLogLabel.setText(""));
        newPasswordTextFieldAccept.textProperty().addListener(observable -> noLogLabel.setText(""));
        oldPasswordTextField.textProperty().addListener(observable -> noLogLabel.setText(""));

        newPasswordTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                changePasswordOnAction();
            }
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)){
                cancelOnAction();
            }
        });
        newPasswordTextFieldAccept.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                changePasswordOnAction();
            }
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)){
                cancelOnAction();
            }
        });
        oldPasswordTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                changePasswordOnAction();
            }
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)){
                cancelOnAction();
            }
        });
    }

    private void bindings() {
    newPasswordTextField.textProperty().bindBidirectional(changePasswordModel.getChangePasswordFX().newPasswordProperty());
    oldPasswordTextField.textProperty().bindBidirectional(changePasswordModel.getChangePasswordFX().oldPasswordProperty());
    newPasswordTextFieldAccept.textProperty().bindBidirectional(changePasswordModel.getChangePasswordFX().newPasswordAcceptProperty());
    }

    @FXML
    public void changePasswordOnAction() {
        try {
            Boolean result = changePasswordModel.changePassword();
            if(result==true){
                stage.close();
            }else{
                noLogLabel.setText(Utils.getResourceBundle().getString("change.password.error"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancelOnAction() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
