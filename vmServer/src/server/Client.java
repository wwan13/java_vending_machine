package server;


import server.dataStructure.Queue;

import java.io.*;
import java.net.Socket;

// 클라이언트와 통신 할 수 있도록 해주는 클래스
public class Client {
    Socket socket;
    String message;
    int set=0;

    Queue water_stock;
    Queue coffee_stock;
    Queue sports_drink_stock;
    Queue premium_coffee_stock;
    Queue soda_stock;

//    public void setMessage(String message) {
//        this.message = message;
//    }

    public Client(Socket socket) {
        this.socket = socket;
        message = "";
        init_Beverage();
        receive();
    }

    // 클라이언트로부터 메세지를 받는 메소드
    public void receive() {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        set = 0;
                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[512];

                        set = 1;

                        int length = in.read(buffer);
                        if (length == -1) {
                            throw new IOException();
                        }

                        System.out.println("[ 메세지 수신 성공 ]"
                                + socket.getRemoteSocketAddress()               // 소켓 번호
                                + ": " + Thread.currentThread().getName());     // 사용중인 쓰레드 이름

                        String message_ = new String(buffer, 0, length, "UTF-8");

                        System.out.println(message_);

                        message = message_;

                        request_condition(message_);

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
        Server_Controller.threadPool.submit(thread);
    }

    // 초기 음료의 갯수를 3개로 초기화 하는 메소드
    public void init_Beverage() {
        water_stock = new Queue();
        coffee_stock = new Queue();
        sports_drink_stock = new Queue();
        premium_coffee_stock = new Queue();
        soda_stock = new Queue();

        for ( int i=0;i<3;i++ ) {
            water_stock.enqueue(new Beverage("water",450));
        }
        for ( int i=0;i<3;i++ ) {
            coffee_stock.enqueue(new Beverage("coffee",550));
        }
        for ( int i=0;i<3;i++ ) {
            sports_drink_stock.enqueue(new Beverage("sports drink",550));
        }
        for ( int i=0;i<3;i++ ) {
            premium_coffee_stock.enqueue(new Beverage("premium coffee",700));
        }
        for ( int i=0;i<3;i++ ) {
            soda_stock.enqueue(new Beverage("soda",750));
        }
    }

    // request 메세지를 받아서 처리하는 메소드
    public void request_condition( String message ) throws IOException {

        // request message 규칙
        // entire >> request-type:detail-message -> split(":")
        // request-type -> beverage, login, ...
        // [detail-message]
        // beverage >> water, coffee, sports drink, premium coffee, soda

        String request_type = message.split(":")[0];
        String detail_message = message.split(":")[1];

//        File Beverage_data = new File("../../../datafiles/Beverage.txt");

        String message_tmp = "";
        String send_message = "";
        Beverage tmp;

        if ( request_type.equals("Beverage") ) {
            String FilePath = Client.class.getResource("").getPath() + "../../../../../datafiles/Beverage.txt";
            File file = new File(FilePath);
            try(
                    FileWriter fw = new FileWriter(file,true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    )
            {
                if ( detail_message.equals("water") ) {
                    if ( water_stock.isEmpty() ) {
                        return;
                    }
                    // 물의 재고를 담은 큐에서 하나를 뺌
                    tmp = water_stock.dequeue();
                    // txt 파일에 저장할 형식으로 변환
                    message_tmp = String.format(tmp.getName()+"-"+tmp.getPrice().toString());
                }
                else if ( detail_message.equals("coffee") ) {
                    if ( coffee_stock.isEmpty() ) {
                        return;
                    }
                    tmp = coffee_stock.dequeue();
                    message_tmp = String.format(tmp.getName()+"-"+tmp.getPrice().toString());
                }
                else if ( detail_message.equals("sports drink") ) {
                    if ( sports_drink_stock.isEmpty() ) {
                        return;
                    }
                    tmp = sports_drink_stock.dequeue();
                    message_tmp = String.format(tmp.getName()+"-"+tmp.getPrice().toString());
                }
                else if ( detail_message.equals("premium coffee") ) {
                    if ( premium_coffee_stock.isEmpty() ) {
                        return;
                    }
                    tmp = premium_coffee_stock.dequeue();
                    message_tmp = String.format(tmp.getName()+"-"+tmp.getPrice().toString());
                }
                else if ( detail_message.equals("soda") ) {
                    if ( soda_stock.isEmpty() ) {
                        return;
                    }
                    tmp = soda_stock.dequeue();
                    message_tmp = String.format(tmp.getName()+"-"+tmp.getPrice().toString());
                }
                System.out.println(message_tmp);
                bw.write(message_tmp);
                bw.newLine();
                bw.flush();

                send_message = "Beverage:" + water_stock.length().toString() + "-"
                        + coffee_stock.length().toString() + "-"
                        + sports_drink_stock.length().toString() + "-"
                        + premium_coffee_stock.length().toString() + "-"
                        + soda_stock.length().toString();
                Client.this.send(send_message);

            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

}
