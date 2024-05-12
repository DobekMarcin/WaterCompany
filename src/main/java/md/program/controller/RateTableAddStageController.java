package md.program.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.RateYearModel;
import md.program.utils.Utils;

import java.sql.SQLException;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class RateTableAddStageController {
    @FXML
    private Label noLogLabel;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField rateTextField;
    private Stage thisStage = null;
    private RateYearModel rateYearModel = new RateYearModel();

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
            rateYearModel.initYear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rateYearModel.getRateYearFX().getYear() > 0) {
            yearTextField.setEditable(false);
            rateTextField.requestFocus();

        } else {
            yearTextField.setEditable(true);
            yearTextField.requestFocus();
        }
    }

    private void bindings() {
        yearTextField.textProperty().bindBidirectional(rateYearModel.getRateYearFX().yearProperty(), new NumberStringConverter());
        rateTextField.textProperty().bindBidirectional(rateYearModel.getRateYearFX().rateProperty(), new NumberStringConverter(Locale.US));
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    @FXML
    public void cancelButtonOnAction() {
        thisStage.close();
    }

    @FXML
    public void addButtonOnAction() {
        try {
            if (rateYearModel.valid()) {
                rateYearModel.addRateYear();
                thisStage.close();
            } else {
                noLogLabel.setText(Utils.getResourceBundle().getString("partner.add.error"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
