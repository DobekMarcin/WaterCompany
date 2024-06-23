package md.program.stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import md.program.controller.LoginStageController;
import md.program.utils.Utils;

public class LoginStage extends Application {

    public static void fun(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginStage.class.getResource("/FXML/LoginStage.fxml"));
        fxmlLoader.setResources(Utils.getResourceBundle());
        Scene scene = new Scene(fxmlLoader.load());
        LoginStageController loginStageController = fxmlLoader.getController();
        loginStageController.setStage(stage);
        stage.setScene(scene);
        stage.setTitle(Utils.getResourceBundle().getString("title.app"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
