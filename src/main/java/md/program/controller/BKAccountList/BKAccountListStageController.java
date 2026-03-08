package md.program.controller.BKAccountList;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import md.program.controller.company.CompanyTableAddStageController;
import md.program.database.model.BKAccountYear;
import md.program.modelFX.*;
import md.program.stage.LoginStage;
import md.program.utils.DialogUtil;
import md.program.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class BKAccountListStageController {

    private static final String FXML_ADD_NEW_ACCOUNT = "/FXML/BKAccountAddListStage.fxml";

    @FXML
    private TreeTableView<BKAccountYearFX> treeTable;
    @FXML
    private TreeTableColumn<BKAccountYearFX, String> accountColumn;
    @FXML
    private TreeTableColumn<BKAccountYearFX, String> descColumn;
    @FXML
    private TreeTableColumn<BKAccountYearFX, Double> creditColumn;
    @FXML
    private TreeTableColumn<BKAccountYearFX, Double> debitColumn;
    @FXML
    private ComboBox<Integer> yearComboBox; // Dodano typ generyczny <Integer>
    private Stage stage;
    private final BKAccountListYearModel bkAccountListYearModel = new BKAccountListYearModel();
    private final SettingsModel settingsModel = new SettingsModel();

    /**
     * Główna metoda inicjalizująca widok
     */
    public void init() {
        configureColumns();
        initComboBox();
        loadDefaultYearData();
    }

    /**
     * Konfiguracja fabryk wartości dla kolumn (tylko raz przy starcie)
     */
    private void configureColumns() {

        accountColumn.setCellValueFactory(param -> param.getValue().getValue().accountProperty());
        descColumn.setCellValueFactory(param -> param.getValue().getValue().descriptionProperty());


        creditColumn.setCellValueFactory(param -> param.getValue().getValue().creditProperty().asObject());
        debitColumn.setCellValueFactory(param -> param.getValue().getValue().debitProperty().asObject());

    }

    /**
     * Ładowanie lat do ComboBoxa
     */
    private void initComboBox() {
        try {
            yearComboBox.setItems(bkAccountListYearModel.getAllYear());
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.errorAboutApplication("bookkeeping.error", "bookkeeping.error", "bookkeeping.year.list.error");
        }
    }

    /**
     * Automatyczne ładowanie danych dla domyślnego roku z ustawień
     */
    private void loadDefaultYearData() {
        try {
            int defaultYear = settingsModel.getDefaultYear();
            if (bkAccountListYearModel.checkBKPlanDefaultYear(defaultYear)) {
                yearComboBox.getSelectionModel().select((Integer) defaultYear);
                bkAccountListYearModel.setYear(defaultYear);

                bkAccountListYearModel.init(); // Pobranie danych
                renderTree();                  // Budowa drzewa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obsługa manualnej zmiany roku w ComboBox
     */
    @FXML
    public void selectYearOnAction() {
        Integer selectedYear = yearComboBox.getSelectionModel().getSelectedItem();

        if (selectedYear != null) {
            try {
                bkAccountListYearModel.setYear(selectedYear);
                bkAccountListYearModel.init();
                renderTree();
            } catch (SQLException e) {
                e.printStackTrace();
                DialogUtil.errorAboutApplication("bookkeeping.error", "bookkeeping.error", "bookkeeping.year.list.error2" + selectedYear);
            }
        }
    }

    /**
     * Odświeżenie/Budowa struktury drzewiastej w tabeli
     */
    private void renderTree() {
        TreeItem<BKAccountYearFX> root = bkAccountListYearModel.buildTree();
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
    }

    @FXML
    public void addAccountOnAction() {
        if (treeTable.getSelectionModel().getSelectedItem() != null) {
            BKAccountYearFX bkAccountYear = treeTable.getSelectionModel().getSelectedItem().getValue();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_ACCOUNT));
            fxmlLoader.setResources(Utils.getResourceBundle());
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setTitle("Dodaj konto");
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setResizable(false);
            BKAccountAddListStageController bkAccountAddListStageController = fxmlLoader.getController();
            bkAccountAddListStageController.init();
            bkAccountAddListStageController.setThisStage(stage1);
            bkAccountAddListStageController.setBkAccountYear(bkAccountYear);
            bkAccountAddListStageController.setBkAccountListYearModel(bkAccountListYearModel);
            bkAccountAddListStageController.setControllAdd(false);
            stage1.showAndWait();
            init();
            treeTable.getSelectionModel().selectLast();
            treeTable.scrollTo(treeTable.getSelectionModel().getSelectedIndex());
        } else {
            if (yearComboBox.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_ACCOUNT));
                fxmlLoader.setResources(Utils.getResourceBundle());
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setTitle("Dodaj konto");
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setResizable(false);
                BKAccountAddListStageController bkAccountAddListStageController = fxmlLoader.getController();
                bkAccountAddListStageController.init();
                bkAccountAddListStageController.setThisStage(stage1);
                bkAccountAddListStageController.setBkAccountListYearModel(bkAccountListYearModel);
                bkAccountAddListStageController.setControllAdd(true);
                bkAccountAddListStageController.setCurrentYear(yearComboBox.getSelectionModel().getSelectedItem());
                stage1.showAndWait();
                init();
                treeTable.getSelectionModel().selectLast();
                treeTable.scrollTo(treeTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    // Gettery i Settery
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void deleteAccountOnAction() {
        TreeItem<BKAccountYearFX> selectedItem = treeTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null || selectedItem.getValue() == null) {
            DialogUtil.errorAboutApplication("bookkeeping.error", "bookkeeping.error", "bookkeeping.account.delete");
            return;
        }

        BKAccountYearFX accountFX = selectedItem.getValue();

        try {
            // --- KLUCZOWE SPRAWDZENIE ---
            // Pytamy model (który zapyta repozytorium), czy są subkonta
            boolean hasChildren = bkAccountListYearModel.hasChildren(accountFX);

            if (hasChildren) {
                DialogUtil.errorAboutApplication(
                        "bookkeeping.error",
                        "bookkeeping.error",
                        "bookkeeping.account.delete.haschildren"
                );
                return; // Przerywamy usuwanie
            }

            // Jeśli nie ma dzieci, możemy bezpiecznie usunąć
            bkAccountListYearModel.deleteById(accountFX);

            // Usuwamy z widoku UI
            TreeItem<BKAccountYearFX> parent = selectedItem.getParent();
            if (parent != null) {
                parent.getChildren().remove(selectedItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


    }


}