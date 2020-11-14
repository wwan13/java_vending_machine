package server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_Controller {

    public static ExecutorService threadPool;
    public static Vector<Client> clients = new Vector<Client>();

    ServerSocket serverSocket;

    String IP = "127.0.0.1";
    int port = 8001;

    @FXML
    public Button serverBtn;
    @FXML
    public TextArea textArea;

    public void startServer(String IP, int port) {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(IP,port));
            printRequest();
        } catch ( Exception e ) {
            e.printStackTrace();
            if ( !serverSocket.isClosed() ) {
                stopServer();
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
                        Platform.runLater(() -> {
                            String message = String.format("연결완료\n");
                            textArea.appendText(message);
                        });
                    } catch ( Exception e ) {
                        if ( !serverSocket.isClosed() ) {
                            stopServer();
                        }
                    }
                }
            }
        };
        threadPool = Executors.newCachedThreadPool();
        threadPool.submit(thread);
    }

    public void stopServer() {
        try {
            Iterator<Client> iterator = clients.iterator();
            while(iterator.hasNext()) {
                Client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }

            if ( serverSocket != null && !serverSocket.isClosed() ) {
                serverSocket.close();
            }
            if ( threadPool != null && !threadPool.isShutdown() ) {
                threadPool.shutdown();
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    @FXML
    public void serverOnOff(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Calendar time = Calendar.getInstance();
        String timeFormat = dateFormat.format(time.getTime());

        if ( serverBtn.getText().equals("시작하기") ) {
            startServer(IP,port);
            Platform.runLater(() -> {
                textArea.setText("");
                String message = String.format("[ 서버시작 ]\n" + timeFormat + "\n\n");
                textArea.appendText(message);
                serverBtn.setText("종료하기");
            });
        }
        else {
            stopServer();
            Platform.runLater(() -> {
                String message = String.format("[ 서버종료 ]\n" + timeFormat + "\n\n");
                textArea.appendText(message);
                serverBtn.setText("시작하기");
            });
        }
    }

}
