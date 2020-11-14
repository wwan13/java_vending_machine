package client;

import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Socket socket;

    public void startClient( String IP, int port ) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    socket = new Socket( IP,port );
                    receive();
                } catch ( Exception e ) {
//                    if( !socket.isClosed() ) {
//                        stopClient();
//                        System.out.println("[ 서버 접속 실패 ]");
//                        Platform.exit();
//                    }
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    public void stopClient() {
        try {
            if( socket != null && !socket.isClosed() ) {
                socket.close();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while (true) {
            try {
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                if ( length == -1 ) {
                    throw new IOException();
                }
                String message = new String(buffer,0,length,"UTF-8");
//                Platform.runLater(()->{
//                    System.out.println(message);
//                });
            } catch ( Exception e ) {
                stopClient();
                break;
            }
        }

    }

    public void send( String message ) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                } catch ( Exception e ) {
                    stopClient();
                }
            }
        };
        thread.start();
    }
}
