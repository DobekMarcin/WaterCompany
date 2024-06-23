package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.FormatStringConverter;
import md.program.modelFX.CounterReadModel;
import md.program.utils.Utils;

import java.sql.SQLException;

public class CounterReadEditTableStageController {
    @FXML
    private TextField month1TextField;
    @FXML
    private TextField month2TextField;
    @FXML
    private TextField month3TextField;
    @FXML
    private TextField month4TextField;
    @FXML
    private TextField month5TextField;
    @FXML
    private TextField month6TextField;
    @FXML
    private TextField month7TextField;
    @FXML
    private TextField month8TextField;
    @FXML
    private TextField month9TextField;
    @FXML
    private TextField month10TextField;
    @FXML
    private TextField month11TextField;
    @FXML
    private TextField month12TextField;
    private Stage stage;
    private CounterReadModel counterReadModel = new CounterReadModel();

    public void addButtonOnAction(ActionEvent actionEvent) {

        try {
            counterReadModel.updateCounterRead();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stage.close();
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
        stage.close();
    }

    public void init() {
        bindings();
        setTextFieldFormatter();
    }

    private void bindings() {
        month1TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m1Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month2TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m2Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month3TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m3Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month4TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m4Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month5TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m5Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month6TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m6Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month7TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m7Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month8TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m8Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month9TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m9Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month10TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m10Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month11TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m11Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));
        month12TextField.textProperty().bindBidirectional(counterReadModel.getCounterReadFX().m12Property(), new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()));

    }

    private void setTextFieldFormatter() {
        month1TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month2TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month3TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month4TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month5TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month6TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month7TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month8TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month9TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month10TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month11TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
        month12TextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public CounterReadModel getCounterReadModel() {
        return counterReadModel;
    }

    public void setCounterReadModel(CounterReadModel counterReadModel) {
        this.counterReadModel = counterReadModel;
    }
}
