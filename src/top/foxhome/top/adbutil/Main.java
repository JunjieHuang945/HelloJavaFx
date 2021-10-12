package top.foxhome.top.adbutil;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private Controller mController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(String.format("%s v%s", AppConfig.appName, AppConfig.version));
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
        mController = loader.getController();
        mController.initView();
        mController.setOwnerWindow(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            mController.release();
            event.consume();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
