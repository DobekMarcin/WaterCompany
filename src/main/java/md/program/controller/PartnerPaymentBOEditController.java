package md.program.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.FormatStringConverter;
import md.program.modelFX.PartnerBOFXModel;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.sql.SQLException;

public class PartnerPaymentBOEditController
{
private Stage thisStage;
    @FXML
    private TextField valueTextField;
    @FXML
    private Label labelText;
    private PartnerBOFXModel partnerBOFXModel = new PartnerBOFXModel();

    public void init(){
        labelText.textProperty().bind(Bindings.concat(getPartnerBOFXModel().getPartnerBOFX().getPartnerFXSimpleObjectProperty().nameProperty()," ",getPartnerBOFXModel().getPartnerBOFX().getPartnerFXSimpleObjectProperty().surnameProperty()));
        valueTextField.textProperty().bindBidirectional(getPartnerBOFXModel().getPartnerBOFX().partnerBoValuePropertyProperty(),  new FormatStringConverter<>(Utils.getDecimalFormatWithTwoPlaces()) );
        valueTextField.setTextFormatter(new TextFormatter<>(Utils.doubleFilter));
    }

    public void addButtonOnAction() {
        try {
            partnerBOFXModel.updatePartnerBO();
            thisStage.close();
        } catch (SQLException e) {
            DialogUtil.errorAboutApplication("error.title", "error.header", "error.bad.date");
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

    public PartnerBOFXModel getPartnerBOFXModel() {
        return partnerBOFXModel;
    }

    public void setPartnerBOFXModel(PartnerBOFXModel partnerBOFXModel) {
        this.partnerBOFXModel = partnerBOFXModel;
    }
}
