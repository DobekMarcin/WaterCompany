package md.program.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.modelFX.PartnerBOFX;
import md.program.modelFX.PartnerBOListModel;
import md.program.modelFX.PartnerFX;
import md.program.modelFX.RateYearFX;
import md.program.stage.LoginStage;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class PartnerPaymentBoController {
    private static final String FXML_PARTNER_PAYMENT_BO_EDIT_STAGE_FXML = "/FXML/PartnerPaymentBOEdit.fxml";
    @FXML
    private Button initializeButton;
    @FXML
    private TableView<PartnerBOFX> partnerBOTable;
    @FXML
    private TableColumn<PartnerBOFX,Number> idColumn;
    @FXML
    private TableColumn<PartnerBOFX,String> nameColumn;
    @FXML
    private TableColumn<PartnerBOFX,String> surnameColumn;
    @FXML
    private TableColumn<PartnerBOFX,String> boValueColumn;
    private Stage thisStage;

    private PartnerBOListModel partnerBOListModel = new PartnerBOListModel();

    public void init(){

        try {
            partnerBOListModel.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        partnerBOTable.setItems(partnerBOListModel.getPartnerFXObservableList());
        idColumn.setCellValueFactory(cellDate->cellDate.getValue().parterIdSimpleIntegerPropertyProperty());
        nameColumn.setCellValueFactory(cellDate->cellDate.getValue().getPartnerFXSimpleObjectProperty().nameProperty());
        surnameColumn.setCellValueFactory(cellDate->cellDate.getValue().getPartnerFXSimpleObjectProperty().surnameProperty());
        boValueColumn.setCellValueFactory(cellDate->cellDate.getValue().partnerBoValuePropertyProperty().asString());

        boValueColumn.setCellFactory(param -> new TableCell<PartnerBOFX, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(Utils.getDecimalFormatWithTwoPlaces().format(Double.valueOf(item)));
                }
            }
        });
    }

    public void editBOOnAction() {
        if (partnerBOTable.getSelectionModel().getSelectedItem() != null) {
            PartnerBOFX partnerBOFX = partnerBOTable.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_PARTNER_PAYMENT_BO_EDIT_STAGE_FXML));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle(Utils.getResourceBundle().getString("partner.bo.edit.title"));
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            PartnerPaymentBOEditController partnerPaymentBOEditController = fxmlLoader.getController();
            partnerPaymentBOEditController.getPartnerBOFXModel().setPartnerBOFX(partnerBOFX);
            partnerPaymentBOEditController.init();
            partnerPaymentBOEditController.setThisStage(stage1);
            stage1.showAndWait();
            init();
       //     partnerTable.getSelectionModel().select(partnerListModel.getPartnerFXObservableList().filtered(partnerFX1 -> partnerFX1.getId() == partnerFX.getId()).get(0));
            partnerBOTable.scrollTo(partnerBOTable.getSelectionModel().getSelectedItem());
        }
    }

    public void closeButtonOnAction() {
        thisStage.close();
    }

    public void closeBOButtonOnAction() {

    }

    public void setThisStage(Stage stage1) {
        thisStage=stage1;
    }

    public void initializeBOOnAction() {
        init();
    }
}
