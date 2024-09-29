package md.program.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtil

{
    public static void dialogAboutApplication(String titleKey,String headerKey,String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        informationAlert.showAndWait();
    }

    public static void confirmationDialog(String titleKey,String headerKey,String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.NONE);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        informationAlert.getButtonTypes().clear();
        informationAlert.getButtonTypes().add(ButtonType.OK);
        informationAlert.showAndWait();
    }

    public static void errorAboutApplication(String titleKey,String headerKey,String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        informationAlert.showAndWait();
    }
    public static boolean yesNoDialog(String titleKey,String headerKey,String decriptionKey) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Utils.getResourceBundle().getString(titleKey));
        alert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        alert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        alert.getButtonTypes().addAll(ButtonType.CANCEL);
        Optional<ButtonType> choose = alert.showAndWait();
        return choose.get() == ButtonType.OK;
    }
}
