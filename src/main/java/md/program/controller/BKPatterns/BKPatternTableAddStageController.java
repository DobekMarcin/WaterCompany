package md.program.controller.BKPatterns;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.BKAccountListModel;
import md.program.modelFX.PatternModelList;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class BKPatternTableAddStageController {
    private static final String FXML_CHOOSE_ACCOUNT_FXML = "/FXML/BKChooseAccountTableStage.fxml";

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField creditTextField;
    @FXML
    private TextField debitTextField;
    @FXML
    private CheckBox invoiceCheckBox;
    @FXML
    private CheckBox bankCheckBox;
    @FXML
    private CheckBox cashCheckBox;
    private Stage thisStage;
    private PatternModelList patternInvoiceModelList = new PatternModelList();
    private BKAccountListModel bkAccountListModel = new BKAccountListModel();
    private int resetAccount = 0;

    public void init() {

        nameTextField.textProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().des_patternProperty());
        creditTextField.textProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().creditTextProperty());
        debitTextField.textProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().debitTextProperty());
        invoiceCheckBox.selectedProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().invoiceProperty());
        bankCheckBox.selectedProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().bankProperty());
        cashCheckBox.selectedProperty().bindBidirectional(patternInvoiceModelList.getAddPattern().cashProperty());

        patternInvoiceModelList.getAddPattern().setDes_pattern("");
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void addButtonOnAction() {
        if (patternInvoiceModelList.getAddPattern().des_patternProperty().getValue().isEmpty() || patternInvoiceModelList.getAddPattern().getDebit()==0 || patternInvoiceModelList.getAddPattern().getCredit()==0) {
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.header", "book.keeping.patterns.dialog");
        } else {
            try {
                patternInvoiceModelList.addNewInvoicePattern();
                thisStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelButtonOnAction() {
        thisStage.close();
    }


    public void chooseCredit() {
        setResetAccount(0);
        bkAccountListModel.resetLevels();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKChooseAccountTableStageController bkChooseAccountTableStageController = fxmlLoader.getController();
        bkChooseAccountTableStageController.setBkAccountListModel(bkAccountListModel);
        bkChooseAccountTableStageController.setLevel(0);
        bkChooseAccountTableStageController.setBkPatternTableAddStageController(this);
        bkChooseAccountTableStageController.init();
        bkChooseAccountTableStageController.setThisStage(stage1);
        stage1.showAndWait();

        if (!bkAccountListModel.getLevel0().getAccount().isEmpty() && bkAccountListModel.getLevel0().isSyn()) {
            FXMLLoader fxmlLoader2 = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
            fxmlLoader2.setResources(Utils.getResourceBundle());
            Scene scene2 = null;
            try {
                scene2 = new Scene(fxmlLoader2.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage2 = new Stage();
            stage2.setScene(scene2);
            stage2.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setResizable(false);
            BKChooseAccountTableStageController bkChooseAccountTableStageController2 = fxmlLoader2.getController();
            bkChooseAccountTableStageController2.setBkAccountListModel(bkAccountListModel);
            bkChooseAccountTableStageController2.setLevel(1);
            bkChooseAccountTableStageController2.setBkPatternTableAddStageController(this);
            bkChooseAccountTableStageController2.init();
            bkChooseAccountTableStageController2.setThisStage(stage2);
            stage2.showAndWait();
        }

        if (!bkAccountListModel.getLevel1().getAccount().isEmpty() && bkAccountListModel.getLevel1().isSyn()) {
            FXMLLoader fxmlLoader3 = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
            fxmlLoader3.setResources(Utils.getResourceBundle());
            Scene scene3 = null;
            try {
                scene3 = new Scene(fxmlLoader3.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage3 = new Stage();
            stage3.setScene(scene3);
            stage3.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.setResizable(false);
            BKChooseAccountTableStageController bkChooseAccountTableStageController3 = fxmlLoader3.getController();
            bkChooseAccountTableStageController3.setBkAccountListModel(bkAccountListModel);
            bkChooseAccountTableStageController3.setLevel(2);
            bkChooseAccountTableStageController3.setBkPatternTableAddStageController(this);
            bkChooseAccountTableStageController3.init();
            bkChooseAccountTableStageController3.setThisStage(stage3);
            stage3.showAndWait();
        }
        if (resetAccount == 0) {

            String account = "";

            account = bkAccountListModel.getLevel0().getFullName();
            patternInvoiceModelList.getAddPattern().setCredit(bkAccountListModel.getLevel0().getId());
            if (bkAccountListModel.getLevel0().isSyn()) {
                account = bkAccountListModel.getLevel1().getFullName();
                patternInvoiceModelList.getAddPattern().setCredit(bkAccountListModel.getLevel1().getId());
            }
            if (bkAccountListModel.getLevel1().isSyn()) {
                account =  bkAccountListModel.getLevel2().getFullName();
                patternInvoiceModelList.getAddPattern().setCredit(bkAccountListModel.getLevel2().getId());
            }
            creditTextField.textProperty().set(account);
        }
    }

    public void chooseDebit() {
        setResetAccount(0);
        bkAccountListModel.resetLevels();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKChooseAccountTableStageController bkChooseAccountTableStageController = fxmlLoader.getController();
        bkChooseAccountTableStageController.setBkAccountListModel(bkAccountListModel);
        bkChooseAccountTableStageController.setLevel(0);
        bkChooseAccountTableStageController.setBkPatternTableAddStageController(this);
        bkChooseAccountTableStageController.init();
        bkChooseAccountTableStageController.setThisStage(stage1);
        stage1.showAndWait();

        if (!bkAccountListModel.getLevel0().getAccount().isEmpty() && bkAccountListModel.getLevel0().isSyn()) {
            FXMLLoader fxmlLoader2 = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
            fxmlLoader2.setResources(Utils.getResourceBundle());
            Scene scene2 = null;
            try {
                scene2 = new Scene(fxmlLoader2.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage2 = new Stage();
            stage2.setScene(scene2);
            stage2.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setResizable(false);
            BKChooseAccountTableStageController bkChooseAccountTableStageController2 = fxmlLoader2.getController();
            bkChooseAccountTableStageController2.setBkAccountListModel(bkAccountListModel);
            bkChooseAccountTableStageController2.setLevel(1);
            bkChooseAccountTableStageController2.setBkPatternTableAddStageController(this);
            bkChooseAccountTableStageController2.init();
            bkChooseAccountTableStageController2.setThisStage(stage2);
            stage2.showAndWait();
        }

        if (!bkAccountListModel.getLevel1().getAccount().isEmpty() && bkAccountListModel.getLevel1().isSyn()) {
            FXMLLoader fxmlLoader3 = new FXMLLoader(LoginStage.class.getResource(FXML_CHOOSE_ACCOUNT_FXML));
            fxmlLoader3.setResources(Utils.getResourceBundle());
            Scene scene3 = null;
            try {
                scene3 = new Scene(fxmlLoader3.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage3 = new Stage();
            stage3.setScene(scene3);
            stage3.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.setResizable(false);
            BKChooseAccountTableStageController bkChooseAccountTableStageController3 = fxmlLoader3.getController();
            bkChooseAccountTableStageController3.setBkAccountListModel(bkAccountListModel);
            bkChooseAccountTableStageController3.setLevel(2);
            bkChooseAccountTableStageController3.setBkPatternTableAddStageController(this);
            bkChooseAccountTableStageController3.init();
            bkChooseAccountTableStageController3.setThisStage(stage3);
            stage3.showAndWait();
        }
        if (resetAccount == 0) {
            String account = "";
            account = bkAccountListModel.getLevel0().getFullName();
            patternInvoiceModelList.getAddPattern().setDebit(bkAccountListModel.getLevel0().getId());
            if (bkAccountListModel.getLevel0().isSyn()) {
                account = bkAccountListModel.getLevel1().getFullName();
                patternInvoiceModelList.getAddPattern().setDebit(bkAccountListModel.getLevel1().getId());
            }
            if (bkAccountListModel.getLevel1().isSyn()) {
                account = bkAccountListModel.getLevel2().getFullName();
                patternInvoiceModelList.getAddPattern().setDebit(bkAccountListModel.getLevel2().getId());
            }
            debitTextField.textProperty().set(account);
        }
    }

    public int getResetAccount() {
        return resetAccount;
    }

    public void setResetAccount(int resetAccount) {
        this.resetAccount = resetAccount;
    }
}
