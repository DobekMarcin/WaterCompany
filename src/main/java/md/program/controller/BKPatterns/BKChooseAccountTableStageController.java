package md.program.controller.BKPatterns;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import md.program.modelFX.*;

import java.sql.SQLException;

public class BKChooseAccountTableStageController {

    @FXML
    private TableView<BKAccountFX> accountTable;
    @FXML
    private TableColumn<BKAccountFX, String> accountColumn;
    @FXML
    private TableColumn<BKAccountFX, String> desColumn;
    @FXML
    private TableColumn<BKAccountFX, Boolean> syntheticColumn;
    private PatternModelList patternInvoiceModelList = new PatternModelList();
    private BKAccountListModel bkAccountListModel;
    private int level = 0;

    private Stage thisStage;
    private BKPatternTableAddStageController bkPatternTableAddStageController;

    public void init() {
        try {
            bkAccountListModel.setLevel(level);
            bkAccountListModel.init();
            tableInit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void tableInit() {
        accountTable.setItems(bkAccountListModel.getBkAccountFXObservableList());

        accountColumn.setCellValueFactory(cellDate -> cellDate.getValue().accountProperty());
        desColumn.setCellValueFactory(cellDate -> cellDate.getValue().descriptionProperty());
        syntheticColumn.setCellValueFactory(cellData -> cellData.getValue().synProperty());
        syntheticColumn.setCellFactory(CheckBoxTableCell.forTableColumn(syntheticColumn));
    }

//    public void addPatternButtonOnAction(ActionEvent actionEvent) {
//        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource(FXML_ADD_NEW_PATTERN_FXML));
//        fxmlLoader.setResources(Utils.getResourceBundle());
//        Scene scene = null;
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Stage stage1 = new Stage();
//        stage1.setScene(scene);
//        stage1.setTitle(Utils.getResourceBundle().getString("book.keeping.patterns.add.title"));
//        stage1.initModality(Modality.APPLICATION_MODAL);
//        stage1.setResizable(false);
//        BKPatternTableAddStageController bkPatternTableAddStageController = fxmlLoader.getController();
//        bkPatternTableAddStageController.init();
//        bkPatternTableAddStageController.setThisStage(stage1);
//        stage1.showAndWait();
//        init();
//        patternTable.getSelectionModel().selectLast();
//        patternTable.scrollTo(patternTable.getSelectionModel().getSelectedItem());
//    }


    public void closeButtonOnAction() {
        bkPatternTableAddStageController.setResetAccount(1);
        thisStage.close();
    }


    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }


    public void chooseButtonOnAction() {
        if (accountTable.getSelectionModel().getSelectedItem() != null) {
            BKAccountFX bkAccountFX = accountTable.getSelectionModel().getSelectedItem();
            if (level == 0) bkAccountListModel.setLevel0(bkAccountFX);
            if (level == 1) bkAccountListModel.setLevel1(bkAccountFX);
            if (level == 2) bkAccountListModel.setLevel2(bkAccountFX);

            thisStage.close();
        }


    }

    public BKAccountListModel getBkAccountListModel() {
        return bkAccountListModel;
    }

    public void setBkAccountListModel(BKAccountListModel bkAccountListModel) {
        this.bkAccountListModel = bkAccountListModel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BKPatternTableAddStageController getBkPatternTableAddStageController() {
        return bkPatternTableAddStageController;
    }

    public void setBkPatternTableAddStageController(BKPatternTableAddStageController bkPatternTableAddStageController) {
        this.bkPatternTableAddStageController = bkPatternTableAddStageController;
    }
}
