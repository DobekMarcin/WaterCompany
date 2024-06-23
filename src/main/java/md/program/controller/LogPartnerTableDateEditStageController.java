package md.program.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import md.program.modelFX.LogPartnerListModel;
import md.program.utils.Utils;

import java.sql.SQLException;

public class LogPartnerTableDateEditStageController {
    private Stage thisStage;

    private LogPartnerListModel logPartnerListModel;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField monthTextField;

    public void init() {

        yearTextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        monthTextField.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
        Bindings.bindBidirectional(yearTextField.textProperty(), logPartnerListModel.getLogPartnerFX().yearProperty(), new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string);
            }
        });

        Bindings.bindBidirectional(monthTextField.textProperty(), logPartnerListModel.getLogPartnerFX().monthProperty(), new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string);
            }
        });

    }

    public void saveButtonOnAction() {
        try {
            logPartnerListModel.saveData();
            thisStage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public Stage getThisStage() {

        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public LogPartnerListModel getLogPartnerListModel() {
        return logPartnerListModel;
    }

    public void setLogPartnerListModel(LogPartnerListModel logPartnerListModel) {
        this.logPartnerListModel = logPartnerListModel;
    }


}
