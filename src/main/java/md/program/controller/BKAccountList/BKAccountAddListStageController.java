package md.program.controller.BKAccountList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import md.program.database.model.BKAccountYear;
import md.program.modelFX.BKAccountListYearModel;
import md.program.modelFX.BKAccountYearFX;
import md.program.utils.DialogUtil;
import md.program.utils.converters.BKAccountYearConverter;

import java.sql.SQLException;

public class BKAccountAddListStageController {

    @FXML
    private TextField accountField;
    @FXML
    private TextField descField;

    private Stage stage;
    private BKAccountListYearModel bkAccountListYearModel;

    // To jest konto wybrane w tabeli (rodzic)
    private BKAccountYearFX parentAccount;

    // To jest nowe konto, które tworzymy
    private final BKAccountYearFX newAccountFX = new BKAccountYearFX();

    private boolean controllAdd=false;
    private int currentYear =0;

    /**
     * Inicjalizacja bindowania - dane z TextFieldów automatycznie trafiają do newAccountFX
     */
    public void init() {
        accountField.textProperty().bindBidirectional(newAccountFX.accountProperty());
        descField.textProperty().bindBidirectional(newAccountFX.descriptionProperty());
    }

    @FXML
    public void saveOnAction(ActionEvent actionEvent) {
        // Walidacja
        if (newAccountFX.getAccount().isEmpty() || newAccountFX.getDescription().isEmpty()) {
            DialogUtil.dialogAboutApplication("dialog.title", "dialog.title", "invoice.dialog.data");
            return;
        }
if(controllAdd){
    // Ustawiamy dane techniczne nowego konta na podstawie rodzica
    newAccountFX.setYear(currentYear);
    newAccountFX.setRoot(0);

    // Logika pełnej nazwy (np. numer konta rodzica - numer dziecka)
    String fullAccNumber = parentAccount.getAccount() + "-" + newAccountFX.getAccount();
    newAccountFX.setFullName(fullAccNumber);
}else {
    // Ustawiamy dane techniczne nowego konta na podstawie rodzica
    newAccountFX.setYear(parentAccount.getYear());
    newAccountFX.setRoot(parentAccount.getId());

    // Logika pełnej nazwy (np. numer konta rodzica - numer dziecka)
    String fullAccNumber = accountField.getText();
    newAccountFX.setFullName(fullAccNumber);

}

        try {


            // Konwersja FX -> DB (używamy Twojego konwertera)
            BKAccountYear dbAccount = BKAccountYearConverter.convertToBKAccountYear(newAccountFX);

            // Zapis do bazy
            bkAccountListYearModel.insertNewAccount(dbAccount);

            // Zamknięcie okna
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.errorAboutApplication("Błąd", "Błąd zapisu", "Nie udało się zapisać konta.");

        }
    }

    @FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        stage.close();
    }

    // Settery
    public void setThisStage(Stage stage) {
        this.stage = stage;
    }

    public void setBkAccountYear(BKAccountYearFX parentAccount) {
        this.parentAccount = parentAccount;
    }

    public void setBkAccountListYearModel(BKAccountListYearModel model) {
        this.bkAccountListYearModel = model;
    }

    public boolean isControllAdd() {
        return controllAdd;
    }

    public void setControllAdd(boolean controllAdd) {
        this.controllAdd = controllAdd;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}