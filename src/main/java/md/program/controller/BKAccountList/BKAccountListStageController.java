package md.program.controller.BKAccountList;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TreeTableView<BKAccountYear> treeTable;
    @FXML
    private TreeTableColumn<BKAccountYear, String> accountColumn;
    @FXML
    private TreeTableColumn<BKAccountYear, String> descColumn;
    @FXML
    private TreeTableColumn<BKAccountYear,Double> creditColumn;
    @FXML
    private TreeTableColumn<BKAccountYear,Double> debitColumn;
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
        accountColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getValue().getAccount()));

        descColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getValue().getDescription()));
        creditColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getCredit()));

        creditColumn.setCellFactory(column -> new TreeTableCell<BKAccountYear, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        debitColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getDebit()));

        debitColumn.setCellFactory(column -> new TreeTableCell<BKAccountYear, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
    }

    /**
     * Ładowanie lat do ComboBoxa
     */
    private void initComboBox() {
        try {
            yearComboBox.setItems(bkAccountListYearModel.getAllYear());
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.errorAboutApplication("Błąd", "Błąd bazy", "Nie udało się pobrać listy lat.");
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
                DialogUtil.errorAboutApplication("Błąd", "Błąd", "Nie udało się załadować planu kont dla roku " + selectedYear);
            }
        }
    }

    /**
     * Odświeżenie/Budowa struktury drzewiastej w tabeli
     */
    private void renderTree() {
        TreeItem<BKAccountYear> root = bkAccountListYearModel.buildTree();
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
    }
    @FXML
    public void addAccountOnAction() {
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
        stage1.setTitle(Utils.getResourceBundle().getString("company.table.add.title"));
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setResizable(false);
        BKAccountAddListStageController bkAccountAddListStageController = fxmlLoader.getController();
        bkAccountAddListStageController.init();
        bkAccountAddListStageController.setThisStage(stage1);
        stage1.showAndWait();
        init();
        treeTable.getSelectionModel().selectLast();
        treeTable.scrollTo(treeTable.getSelectionModel().getSelectedIndex());
    }

    // Gettery i Settery
    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }
}