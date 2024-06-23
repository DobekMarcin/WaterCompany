package md.program.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;

public class MainStageController {
    private static final String FXML_RATE_TABLE_STAGE_FXML = "/FXML/RateTableStage.fxml";
    private static final String FXML_COUNTER_RATE_TABLE_STAGE_FXML = "/FXML/CounterTableStage.fxml";
    private static final String FXML_PARTNER_STAGE_FXML = "/FXML/PartnerTableStage.fxml";
    private static final String FXML_PAYMENT_PLAN_STAGE_FXML = "/FXML/PaymentPlanTableStage.fxml";
    private static final String FXML_COUNTER_READ_STAGE_FXML = "/FXML/CounterReadTableStage.fxml";
    private static final String FXML_CHANGE_PASSWORD_STAGE_FXML = "/FXML/ChangePasswordStage.fxml";
    private static final String FXML_DEFAULT_YEAR_STAGE_FXML = "/FXML/DefaultYearStage.fxml";
    @FXML
    private BorderPane borderPane;

    @FXML
    public void closeOnAction() {
        Platform.exit();
    }

    @FXML
    public void rateTableOnAction() {
        openRateTableStage();
    }

    private void openRateTableStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_RATE_TABLE_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("rate.table.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        RateTableStageController rateTableStageController = fxmlLoader.getController();
        rateTableStageController.setThisStage(stage1);
        stage1.showAndWait();
    }

    @FXML
    public void partnerMenuButtonOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PARTNER_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());

        Pane bordPane = null;
        try {
            bordPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PartnerTableStageController partnerStageController = fxmlLoader.getController();
        partnerStageController.init();
        borderPane.setCenter(bordPane);
    }

    @FXML
    public void paymentPlanMenuButtonOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PAYMENT_PLAN_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());

        Pane bordPane = null;
        try {
            bordPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PaymentPlanTableStageController paymentPlanTableStageController = fxmlLoader.getController();
        paymentPlanTableStageController.init();
        borderPane.setCenter(bordPane);
    }

    @FXML
    public void changePasswordOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_CHANGE_PASSWORD_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("change.password.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        ChangePasswordStageController changePasswordStageController = fxmlLoader.getController();
        changePasswordStageController.setStage(stage1);
        changePasswordStageController.init();
        stage1.showAndWait();
    }

    public void counterRateTableOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_COUNTER_RATE_TABLE_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("counter.rate.table.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        CounterTableStageController counterTableStageController = fxmlLoader.getController();
        counterTableStageController.setThisStage(stage1);
        stage1.showAndWait();
    }

    public void counterMenuButtonOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_COUNTER_READ_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());

        Pane bordPane = null;
        try {
            bordPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CounterReadTableStageController counterReadTableStageController = fxmlLoader.getController();
        counterReadTableStageController.init();
        borderPane.setCenter(bordPane);
    }

    public void defaultYearOnAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_DEFAULT_YEAR_STAGE_FXML));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle(Utils.getResourceBundle().getString("default.year.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        DefaultYearStageController defaultYearStageController = fxmlLoader.getController();
        defaultYearStageController.setThisStage(stage1);
        defaultYearStageController.init();
        stage1.showAndWait();
    }
}
