package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

public class Server_Controller {

    public static ExecutorService threadPool;
    public static Vector<Client> clients = new Vector<Client>();

    ServerSocket serverSocket;

    public void startServer(String IP, int port) {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(IP,port));
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
                    } catch ( Exception e ) {
                        if ( !serverSocket.isClosed() ) {
                            stopServer();
                        }
                    }
                }
            }
        };
    }

    public void stopServer() {
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

}
