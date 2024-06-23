package md.program.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.SettingsModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class DefaultYearStageController {
    @FXML
    private TextField yearTextField;
    private Stage thisStage;
    private SettingsModel settingsModel = new SettingsModel();

    public void init() {
        try {
            Integer defaultYear = settingsModel.getDefaultYear();
            yearTextField.setText(defaultYear.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirmYear() {
        try {
            Integer year = Integer.valueOf(yearTextField.getText());
            settingsModel.deleteDefaultYear();
            settingsModel.updateDefaultYear(year);
            thisStage.close();
        } catch (SQLException | NumberFormatException | NullPointerException e) {
            DialogUtil.errorAboutApplication("error.title", "error.header", "error.bad.date");
        }
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
