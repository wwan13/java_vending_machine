package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

public class Main extends Application {

    public static ExecutorService threadpool;
    public static Vector<Client> clients = new Vector<Client>();

    ServerSocket serverSocket;

    public void startserver(String IP, int port) {

    }

    public void stopserver() {

    }




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
