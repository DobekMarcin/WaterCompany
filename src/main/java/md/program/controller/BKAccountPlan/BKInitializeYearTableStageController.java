package md.program.controller.BKAccountPlan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.BKYearFX;
import md.program.modelFX.BKYearListModel;
import md.program.modelFX.PartnerFX;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class BKInitializeYearTableStageController {

    private static final String FXML_ADD_NEW_YEAR_BOOKKEEPING_FXML = "/FXML/BKInitializeYearStage.fxml";

    private Stage thisStage;
    @FXML
    private TableView<BKYearFX> bkYearTable;
    @FXML
    private TableColumn<BKYearFX,Number> idColumn;
    @FXML
    private TableColumn<BKYearFX,Number> yearColumn;
    private BKYearListModel bkYearListModel = new BKYearListModel();

    public void init() {
        try {
            bkYearListModel.init();
            tableInit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        bkYearTable.setItems(bkYearListModel.getBkYearFXES());
        idColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        yearColumn.setCellValueFactory(cellDate -> cellDate.getValue().yearProperty());
    }


    public void addYearButtonOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_YEAR_BOOKKEEPING_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("bk.year.list"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);

        BKInitializeYearStageController bkInitializeYearStageController = fxmlLoader.getController();
        bkInitializeYearStageController.setStage(stage1);
        bkInitializeYearStageController.init();
        stage1.showAndWait();
        init();
    }

    public void deleteYearButtonOnAction() {
        if (bkYearTable.getSelectionModel().getSelectedItem() != null) {
            BKYearFX bkYearFX = bkYearTable.getSelectionModel().getSelectedItem();
            bkYearListModel.setDeleteYearFX(bkYearFX);
            try {
                bkYearListModel.deleteYear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            init();
            //TODO: dodać kasowanie planu kont by year
        }
    }

    public void closeButtonOnAction() {
    thisStage.close();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
