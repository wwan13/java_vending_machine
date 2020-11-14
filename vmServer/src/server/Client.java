package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// 클라이언트와 통신 할 수 있도록 해주는 클래스
public class Client {
    Socket socket;
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public Client(Socket socket) {
        this.socket = socket;
        this.message = "";
        receive();
    }


    // 클라이언트로부터 메세지를 받는 메소드
    public void receive() {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        setMessage("");
                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[512];

                        int length = in.read(buffer);
                        if (length == -1) {
                            throw new IOException();
                        }

                        System.out.println("[ 메세지 수신 성공 ]"
                                + socket.getRemoteSocketAddress()               // 소켓 번호
                                + ": " + Thread.currentThread().getName());     // 사용중인 쓰레드 이름

                        String message_ = new String(buffer, 0, length, "UTF-8");

                        message = message_;

                        // 모든 클라이언트에게 메세지 전송
                        for (Client client : Server_Controller.clients) {
                            client.send(message);
                        }

                    }
                } catch (Exception e) {
                    try {
                        System.out.println("[ 메세지 수신 실패 ]"
                                            + socket.getRemoteSocketAddress()               // 소켓 번호
                                            + ": " + Thread.currentThread().getName());     // 사용중인 쓰레드 이름
                        Server_Controller.clients.remove(Client.this);
                        socket.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        Server_Controller.threadPool.submit(thread);
    }

    // 클라이언트로 메세지를 보내는 메소드
    public void send( String message ) {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        System.out.println("[ 메세지 수신 실패 ]"
                                            + socket.getRemoteSocketAddress()               // 소켓 번호
                                            + ": " + Thread.currentThread().getName());     // 사용중인 쓰레드 이름
                        Server_Controller.clients.remove(Client.this);
                        socket.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
    }

}
