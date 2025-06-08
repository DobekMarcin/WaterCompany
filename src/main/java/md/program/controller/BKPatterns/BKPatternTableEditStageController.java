package md.program.controller.BKPatterns;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.modelFX.PatternModelList;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BKPatternTableEditStageController {


    public TextField nameTextField;
    private Stage thisStage;
    private PatternModelList patternInvoiceModelList;

    public void init() {

        nameTextField.textProperty().bindBidirectional(patternInvoiceModelList.getEditPatter().des_patternProperty());
            patternInvoiceModelList.getAddPattern().setDes_pattern("");
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void addButtonOnAction() {
        if(patternInvoiceModelList.getAddPattern().des_patternProperty().isEmpty().isValid()){
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "book.keeping.patterns.dialog");
        }else{
            try {
                patternInvoiceModelList.updatePattern();
                thisStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }

    public PatternModelList getPatternInvoiceModelList() {
        return patternInvoiceModelList;
    }

    public void setPatternInvoiceModelList(PatternModelList patternInvoiceModelList) {
        this.patternInvoiceModelList = patternInvoiceModelList;
    }
}
