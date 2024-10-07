package md.program.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import md.program.database.model.BKAccount;
import md.program.modelFX.BKAccountPlanModel;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class BookKeepingAccountPlanStageController {
    private static final String FXML_ACCOUNT_PLAN_EDIT_STAGE_FXML = "/FXML/BKAccountPlanEditStage.fxml";
    private static final String FXML_ACCOUNT_PLAN_ADD_STAGE_FXML = "/FXML/BKAccountPlanAddStage.fxml";
    private BKAccountPlanModel bkAccountPlanModel = new BKAccountPlanModel();
    @FXML
    private TreeView<BKAccount> treeView = new TreeView<>();
    private Stage thisStage = null;


    public void init() {

        try {
            bkAccountPlanModel.init();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        treeView.setRoot(bkAccountPlanModel.getTreeItemRoot());
        expandTreeView(treeView.getRoot());

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

    public void editOnAction() {
        if(treeView.getSelectionModel().getSelectedItem()!=null) {
        BKAccount bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();
        bkAccountPlanModel.setBkAccountEdit(bkAccount);

        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ACCOUNT_PLAN_EDIT_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("bookkeeping.plan.edit"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKAccountPlanEditStageController bkAccountPlanEditStageController = fxmlLoader.getController();
        bkAccountPlanEditStageController.setThisStage(stage1);
        bkAccountPlanEditStageController.setBkAccountPlanModel(bkAccountPlanModel);
        bkAccountPlanEditStageController.init();
        stage1.showAndWait();
        init();
    }}

    public void deleteOnAction() {
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            BKAccount bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();
            bkAccountPlanModel.setBkAccountEdit(bkAccount);

            try {
                int answer = bkAccountPlanModel.delete();
                if (answer == -1)
                    DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.accountPlan.delete.root");
                if (answer == -2)
                    DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.accountPlan.delete.children");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            init();
        }
    }

    public void addOnAction() {
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            BKAccount bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();

            bkAccountPlanModel.setBkAccountEdit(bkAccount);

            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ACCOUNT_PLAN_ADD_STAGE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("bookkeeping.account.edit.addAccount"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            BKAccountPlanAddStageController bkAccountPlanEditStageController = fxmlLoader.getController();
            bkAccountPlanEditStageController.setThisStage(stage1);
            bkAccountPlanEditStageController.setBkAccountPlanModel(bkAccountPlanModel);
            bkAccountPlanEditStageController.init();
            stage1.showAndWait();
            init();
        }
    }

    private void expandTreeView(TreeItem<?> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (TreeItem<?> child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }
}
