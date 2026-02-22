package md.program.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class DialogUtil {

    // Upewnij się, że ta ścieżka prowadzi do Twojego pliku CSS w resources
    private static final String CSS_PATH = "/css/style.css";

    private static void applyStyle(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        String css = DialogUtil.class.getResource(CSS_PATH).toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog-pane");
    }

    public static void dialogAboutApplication(String titleKey, String headerKey, String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));

        applyStyle(informationAlert);
        informationAlert.showAndWait();
    }

    public static void confirmationDialog(String titleKey, String headerKey, String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.NONE);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        informationAlert.getButtonTypes().clear();
        informationAlert.getButtonTypes().add(ButtonType.OK);

        applyStyle(informationAlert);
        informationAlert.showAndWait();
    }

    public static void errorAboutApplication(String titleKey, String headerKey, String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));

        applyStyle(informationAlert);
        informationAlert.showAndWait();
    }

    public static boolean yesNoDialog(String titleKey, String headerKey, String decriptionKey) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Utils.getResourceBundle().getString(titleKey));
        alert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        alert.setContentText(Utils.getResourceBundle().getString(decriptionKey));

        // Zostawiam ButtonType.CANCEL tak jak miałeś w oryginale
        alert.getButtonTypes().addAll(ButtonType.CANCEL);

        applyStyle(alert);
        Optional<ButtonType> choose = alert.showAndWait();

        // Zabezpieczenie przed błędem, jeśli użytkownik zamknie okno krzyżykiem
        return choose.isPresent() && choose.get() == ButtonType.OK;
    }
}