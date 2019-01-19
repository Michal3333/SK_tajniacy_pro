package sample;

import controllers.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/LoginScreen.fxml"));
        LoginScreenController.stage = primaryStage;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setOnCloseRequest(rec -> {
            try {
                Stale.getWk().wyjdz();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
