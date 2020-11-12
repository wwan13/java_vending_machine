package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

public class Main extends Application {

    public static ExecutorService threadpool;
    public static Vector<Client> clients = new Vector<Client>();

    ServerSocket serverSocket;

    public void startserver(String IP, int port) {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(IP,port));
        } catch ( Exception e ) {
            e.printStackTrace();
            if ( !serverSocket.isClosed() ) {
                stopserver();
            }
            return;
        }

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                while( true ) {
                    try {
                        Socket socket = serverSocket.accept();
                        clients.add(new Client(socket));
                        System.out.println("[ 메세지 수신 성공 ]"
                                            + socket.getRemoteSocketAddress()               // 소켓 번호
                                            + ": " + Thread.currentThread().getName());     // 사용중인 쓰레드 이름
                    } catch ( Exception e ) {
                        if ( !serverSocket.isClosed() ) {
                            stopserver();
                        }
                    }
                }
            }
        };
    }

    public void stopserver() {
        try {
            Iterator<Client> iterator = clients.iterator();
            while(iterator.hasNext()) {
                Client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
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
