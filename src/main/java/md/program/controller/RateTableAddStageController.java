package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.RateYearModel;

import java.sql.SQLException;

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
    }

    private void bindings() {
        yearTextField.textProperty().bindBidirectional(rateYearModel.getRateYearFX().yearProperty(), new NumberStringConverter());
        rateTextField.textProperty().bindBidirectional(rateYearModel.getRateYearFX().rateProperty(), new NumberStringConverter());
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
            rateYearModel.addRateYear();
            thisStage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
