package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.PartnerBOFXModel;
import md.program.modelFX.PartnerBOListModel;
import md.program.modelFX.SettingsModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BOYearStageController {
    @FXML
    private Button initializeButton;
    @FXML
    private TextField yearTextField;
    private Stage thisStage;
    private SettingsModel settingsModel = new SettingsModel();
    private PartnerBOListModel partnerBOListModel = new PartnerBOListModel();

    public void init() {
        try {
            Integer boYear = settingsModel.getBOYear();
            if(boYear>0){
                yearTextField.setEditable(false);
                yearTextField.setText(boYear.toString());
                yearTextField.setDisable(true);
                initializeButton.setVisible(false);


            }else{
                yearTextField.setText(boYear.toString().equals("-1")?"0":boYear.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirmYear() {
        try {
            Integer year = Integer.valueOf(yearTextField.getText());
            settingsModel.updateBOYear(year);
            partnerBOListModel.generateBO();
            thisStage.close();
        } catch (SQLException | NumberFormatException | NullPointerException e) {
            DialogUtil.errorAboutApplication("error.title", "error.header", "error.bad.date");
            e.printStackTrace();
        }
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void closeButton() {
        thisStage.close();
    }
}
