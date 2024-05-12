package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.CounterYearModel;
import md.program.modelFX.RateYearModel;
import md.program.utils.Utils;

import java.sql.SQLException;
import java.util.Locale;

public class CounterTableAddStageController {

    @FXML
    private TextField yearTextField;
    @FXML
    private TextField rateTextField;
    @FXML
    private Label noLogLabel;
    private Stage thisStage = null;
    private CounterYearModel counterYearModel = new CounterYearModel();

    public void init() {
        bindings();
        setTextFieldFormatter();
        initYear();
    }

    private void setTextFieldFormatter() {
        rateTextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        yearTextField.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    private void initYear() {
        try {
            counterYearModel.initYear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (counterYearModel.getCounterYearFX().getYear()>0) {
            yearTextField.setEditable(false);
            rateTextField.requestFocus();

        } else {
            yearTextField.setEditable(true);
            yearTextField.requestFocus();
        }
    }
    private void bindings() {
        yearTextField.textProperty().bindBidirectional(counterYearModel.getCounterYearFX().yearProperty(), new NumberStringConverter());
        rateTextField.textProperty().bindBidirectional(counterYearModel.getCounterYearFX().rateProperty(), new NumberStringConverter(Locale.US));
    }

    public void addButtonOnAction() {
        System.out.println(counterYearModel.getCounterYearFX());
        try {
            if (counterYearModel.valid()) {
                counterYearModel.addCounterYear();
                thisStage.close();
            } else {
                noLogLabel.setText(Utils.getResourceBundle().getString("partner.add.error"));
            }
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
}
