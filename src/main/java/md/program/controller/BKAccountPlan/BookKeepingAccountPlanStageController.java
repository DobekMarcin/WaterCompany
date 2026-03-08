package md.program.controller.BKAccountPlan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.database.model.BKAccount;
import md.program.modelFX.BKAccountFX;
import md.program.modelFX.BKAccountPlanModel;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;
import md.program.utils.converters.BKAccountConverter;

import java.io.IOException;
import java.sql.SQLException;

public class BookKeepingAccountPlanStageController {
    private static final String FXML_ACCOUNT_PLAN_EDIT_STAGE_FXML = "/FXML/BKAccountPlanEditStage.fxml";
    private static final String FXML_ACCOUNT_PLAN_ADD_STAGE_FXML = "/FXML/BKAccountPlanAddStage.fxml";
    private static final String FXML_ADD_YEAR_BOOKKEEPING_FXML = "/FXML/BKInitializeYearTableStage.fxml";
    private BKAccountPlanModel bkAccountPlanModel = new BKAccountPlanModel();
    @FXML
    private TreeView<BKAccountFX> treeView = new TreeView<>();
    private Stage thisStage = null;


    public void init() {

        try {
            bkAccountPlanModel.init();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        treeView.setRoot(bkAccountPlanModel.buildTree());

        treeView.setCellFactory(tv -> new javafx.scene.control.TreeCell<BKAccountFX>() {
            @Override
            protected void updateItem(BKAccountFX item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Wyświetlamy np. "100 - Kasa"
                    setText(item.getAccount() + " - " + item.getDescription());
                }
            }
        });

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
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            BKAccountFX bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();
            bkAccountPlanModel.setBkAccountEdit(BKAccountConverter.convertToBKAccount(bkAccount));


            int response = 0;

            response = bkAccountPlanModel.getBkAccountEdit().getId();

            if (response == 0) {
                DialogUtil.errorAboutApplication("dialog.title", "error.header", "dialog.accountPlan.edit.root");
            } else {

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
            }
        }
    }

    //
    public void deleteOnAction() {
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            BKAccountFX bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();
            bkAccountPlanModel.setBkAccountEdit(BKAccountConverter.convertToBKAccount(bkAccount));

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
            BKAccountFX bkAccount = treeView.getSelectionModel().getSelectedItem().getValue();
            bkAccountPlanModel.setBkAccountEdit(BKAccountConverter.convertToBKAccount(bkAccount));

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

    public void addGenerateYearButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_YEAR_BOOKKEEPING_FXML));
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

        BKInitializeYearTableStageController bkInitializeYearTableStageController = fxmlLoader.getController();
        bkInitializeYearTableStageController.setThisStage(stage1);
        bkInitializeYearTableStageController.init();
        stage1.showAndWait();
    }
}
