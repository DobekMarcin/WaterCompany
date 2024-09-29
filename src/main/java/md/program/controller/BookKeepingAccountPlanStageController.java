package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import md.program.database.model.BKAccount;
import md.program.modelFX.BKAccountPlanModel;

import java.sql.SQLException;

public class BookKeepingAccountPlanStageController {

    private BKAccountPlanModel bkAccountPlanModel = new BKAccountPlanModel();

    @FXML
    private TreeView<BKAccount> treeView;
    private Stage thisStage = null;



    public void init() {
        try {
      //  treeView = new TreeView<BKAccount>(new TreeItem<>(bkAccountPlanModel.getheader()));
            bkAccountPlanModel.init();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        treeView.setRoot(bkAccountPlanModel.getTreeItemRoot());
        treeView.getRoot().setExpanded(true);
    }
    public void closeButtonOnAction(ActionEvent actionEvent) {
    thisStage.close();
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }


}
