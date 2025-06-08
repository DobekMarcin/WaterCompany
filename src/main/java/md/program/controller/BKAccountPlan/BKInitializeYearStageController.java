package md.program.controller.BKAccountPlan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import md.program.modelFX.BKYearListModel;
import md.program.utils.DialogUtil;

import java.sql.SQLException;

public class BKInitializeYearStageController {
    
    private Stage stage;
    @FXML
    private TextField yearTextField;

    private BKYearListModel bkYearListModel = new BKYearListModel();

    public void init() {
        yearTextField.textProperty().bindBidirectional(bkYearListModel.getAddnewYear().yearProperty(),new NumberStringConverter());
    }

    public void generateButtonOnAction() {
        if(yearTextField.getText().isEmpty()){
            DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.bkYear");
        }else{
            try {
                bkYearListModel.addYear();
                stage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelButtonOnAction() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
