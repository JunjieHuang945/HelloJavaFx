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
        primaryStage.setTitle("adb窗口工具");
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            System.exit(0);
        });
        primaryStage.show();
        mController = loader.getController();
        mController.initView();
        mController.setOwnerWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
