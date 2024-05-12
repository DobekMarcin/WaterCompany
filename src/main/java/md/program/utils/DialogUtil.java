package md.program.utils;

import javafx.scene.control.Alert;

public class DialogUtil

{
    public static void dialogAboutApplication(String titleKey,String headerKey,String decriptionKey) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(Utils.getResourceBundle().getString(titleKey));
        informationAlert.setHeaderText(Utils.getResourceBundle().getString(headerKey));
        informationAlert.setContentText(Utils.getResourceBundle().getString(decriptionKey));
        informationAlert.showAndWait();
    }
}
