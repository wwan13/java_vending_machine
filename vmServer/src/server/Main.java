package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String IP = "127.0.0.1";
        int port = 8001;

        Parent root = FXMLLoader.load(getClass().getResource("serverUI_test.fxml"));
        primaryStage.setTitle("자판기 서버");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
