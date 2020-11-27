package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class MainUI_Controller {

    Socket socket;

    static ArrayList<Coin> coins;

    static Stack change_10;
    static Stack change_50;
    static Stack change_100;
    static Stack change_500;
    static Stack change_1000;

    Integer total_coins = 0;

    String IP = "127.0.0.1";
    Integer port = 8001;

    @FXML
    public Button serverBtn;
    @FXML
    public TextField text_state;
    @FXML
    public TextField text_IP;
    @FXML
    public TextField text_port;
    @FXML
    public TextField text_coin;
    @FXML
    public Button water;
    @FXML
    public Button coffee;
    @FXML
    public Button sports_drink;
    @FXML
    public Button premium_coffee;
    @FXML
    public Button soda;
    @FXML
    public Button coin_10;
    @FXML
    public Button coin_50;
    @FXML
    public Button coin_100;
    @FXML
    public Button coin_500;
    @FXML
    public Button coin_1000;
    @FXML
    public Button coin_return;
    @FXML
    public TextField output;
    @FXML
    public Button log_in;

    @FXML
    public void water_Clicked(ActionEvent event) {
        send("water");
        total_coins -= 450;
        set_beverage_state();
        display_set_coin();
        output.setText("water");
    }

    @FXML
    public void coffee_Clicked(ActionEvent event) {
        send("coffee");
        total_coins -= 500;
        set_beverage_state();
        display_set_coin();
        output.setText("coffee");
    }

    @FXML
    public void sports_drink_Clicked(ActionEvent event) {
        send("sports drink");
        total_coins -= 550;
        set_beverage_state();
        display_set_coin();
        return_button_condition();
        output.setText("sports drink");
    }

    @FXML
    public void premium_coffee_Clicked(ActionEvent event) {
        send("premium coffee");
        total_coins -= 700;
        set_beverage_state();
        display_set_coin();
        return_button_condition();
        output.setText("premium coffee");
    }

    @FXML
    public void soda_Clicked(ActionEvent event) {
        send("soda");
        total_coins -= 750;
        set_beverage_state();
        display_set_coin();
        return_button_condition();
        output.setText("soda");
    }

    @FXML
    public void coinInsert_10(ActionEvent event) {
        coins.add(new Coin(10));
        total_coins += 10;
        display_set_coin();
        set_beverage_state();
        return_button_condition();
        can_insert_additional_coin();
        output.setText("");
    }

    @FXML
    public void coinInsert_50(ActionEvent event) {
        coins.add(new Coin(50));
        total_coins += 50;
        display_set_coin();
        set_beverage_state();
        return_button_condition();
        can_insert_additional_coin();
        output.setText("");
    }

    @FXML
    public void coinInsert_100(ActionEvent event) {
        coins.add(new Coin(100));
        total_coins += 100;
        display_set_coin();
        set_beverage_state();
        return_button_condition();
        can_insert_additional_coin();
        output.setText("");
    }

    @FXML
    public void coinInsert_500(ActionEvent event) {
        coins.add(new Coin(500));
        total_coins += 500;
        display_set_coin();
        set_beverage_state();
        return_button_condition();
        can_insert_additional_coin();
        output.setText("");
    }

    @FXML
    public void coinInsert_1000(ActionEvent event) {
        coins.add(new Coin(1000));
        total_coins += 1000;
        display_set_coin();
        set_beverage_state();
        return_button_condition();
        can_insert_1000();
        can_insert_additional_coin();
        output.setText("");
    }

    // 코인을 반환 해주는 메소드
    @FXML
    public void coinReturn(ActionEvent event) {
        return_change();
        return_button_condition();
        coins = null;
        coins = new ArrayList<Coin>();
        set_beverage_state();
        can_insert_additional_coin();
        can_insert_1000();
    }

    // 서버를 접속 및 자판기 초기화를 담당하는 메소드
    @FXML
    public void serverOnOff(ActionEvent event) {
        if ( serverBtn.getText().equals("서버접속") ) {
            startClient(IP,port);
            serverBtn.setText("접속종료");
            text_IP.setText(IP);
            text_port.setText(port.toString());
            text_state.setText("connected");
            coin_10.setDisable(false);
            coin_50.setDisable(false);
            coin_100.setDisable(false);
            coin_500.setDisable(false);
            coin_1000.setDisable(false);
            log_in.setDisable(false);
            coins = new ArrayList<Coin>();
            init_change();
        }
        else {
            stopClient();
            serverBtn.setText("서버접속");
            text_IP.setText("-");
            text_port.setText("-");
            text_state.setText("not connected");
            text_coin.setText("");
            output.setText("");
            coin_10.setDisable(true);
            coin_50.setDisable(true);
            coin_100.setDisable(true);
            coin_500.setDisable(true);
            coin_1000.setDisable(true);
            coin_return.setDisable(true);
            log_in.setDisable(true);
            water.setDisable(true);
            coffee.setDisable(true);
            sports_drink.setDisable(true);
            premium_coffee.setDisable(true);
            soda.setDisable(true);
            coins = null;
            change_10 = null;
            change_50 = null;
            change_100 = null;
            change_500 = null;
            change_1000  = null;
        }
    }

    // ui에 코인이 얼마나 들어있는지 보여주는 메소드
    public void display_set_coin() {
        text_coin.setText(total_coins.toString());
    }

    public void set_beverage_state() {
        if ( total_coins >= 750 ) {
            soda.setDisable(false);
        }
        else {
            soda.setDisable(true);
        }

        if ( total_coins >= 700 ) {
            premium_coffee.setDisable(false);
        }
        else {
            premium_coffee.setDisable(true);
        }

        if ( total_coins >= 550 ) {
            sports_drink.setDisable(false);
        }
        else {
            sports_drink.setDisable(true);
        }

        if ( total_coins >= 500 ) {
            coffee.setDisable(false);
        }
        else {
            coffee.setDisable(true);
        }

        if ( total_coins >= 450 ) {
            water.setDisable(false);
        }
        else {
            water.setDisable(true);
        }
    }

    // 3000원이 넘게 들어가면 버튼을 모두 비활성화하고 아니면 활성화하는 메소드
    public void can_insert_additional_coin() {
        if(total_coins>=3000) {
            coin_10.setDisable(true);
            coin_50.setDisable(true);
            coin_100.setDisable(true);
            coin_500.setDisable(true);
            coin_1000.setDisable(true);
        }
        else {
            coin_10.setDisable(false);
            coin_50.setDisable(false);
            coin_100.setDisable(false);
            coin_500.setDisable(false);
            coin_1000.setDisable(false);
        }
    }

    // 1000원짜리 지폐를 넣을수 있는지 없는지 판별하는 매소드
    public void can_insert_1000() {
        Integer count = 0;
        for ( Coin coin:coins ) {
            if( coin.value == 1000 ) {
                count++;
            }
        }
        if( count >= 3 ) {
            coin_1000.setDisable(true);
        }
        else {
            coin_1000.setDisable(false);
        }
    }

    // 거스름돈을 각각 3개 씩으로 초기화 하는 함수
    public void init_change() {
        change_10 = new Stack();
        change_50 = new Stack();
        change_100 = new Stack();
        change_500 = new Stack();
        change_1000 = new Stack();

        // 3개식 집어넣음
        for ( int i=0;i<3;i++ ) {
            change_10.stackPush(new Coin(10));
        }
        for ( int i=0;i<3;i++ ) {
            change_50.stackPush(new Coin(50));
        }
        for ( int i=0;i<3;i++ ) {
            change_100.stackPush(new Coin(100));
        }
        for ( int i=0;i<3;i++ ) {
            change_500.stackPush(new Coin(500));
        }
        for ( int i=0;i<3;i++ ) {
            change_1000.stackPush(new Coin(1000));
        }
    }

    // 거스름돈을 반환 해 주는 메소드
    public void return_change() {
        Integer change_value;
        String change_message = "";
        Integer count_1000 = 0;
        Integer count_500 = 0;
        Integer count_100 = 0;
        Integer count_50 = 0;
        Integer count_10 = 0;

        while (total_coins >= 0) {
            if ( total_coins != 0 ) {
                change_message = "|   ";
            }

            if ( total_coins - 1000 >= 0 && change_1000.isEmpty() == false ) {
                change_value = change_1000.stackPop().value;
                total_coins = total_coins - change_value;
                count_1000++;
            }
            else if ( total_coins - 500 >= 0 && change_500.isEmpty() == false ) {
                change_value = change_500.stackPop().value;
                total_coins = total_coins - change_value;
                count_500++;
            }
            else if ( total_coins - 100 >= 0 && change_100.isEmpty() == false ) {
                change_value = change_100.stackPop().value;
                total_coins = total_coins - change_value;
                count_100++;
            }
            else if ( total_coins - 50 >= 0 && change_50.isEmpty() == false ) {
                change_value = change_50.stackPop().value;
                total_coins = total_coins - change_value;
                count_50++;
            }
            else if ( total_coins - 10 >= 0 && change_10.isEmpty() == false ) {
                change_value = change_10.stackPop().value;
                total_coins = total_coins - change_value;
                count_10++;
            }
            else {
                output.setText("잔돈 부족");
                break;
            }
        }
        if( count_1000 > 0 ) {
            change_message = String.format( change_message + "1000원 : " + count_1000 + " 개   |   ");
        }
        if( count_500 > 0 ) {
            change_message = String.format( change_message + "500원 : " + count_500 + " 개   |   ");
        }
        if( count_100 > 0 ) {
            change_message = String.format( change_message + "100원 : " + count_100 + " 개   |   ");
        }
        if( count_50 > 0 ) {
            change_message = String.format( change_message + "50원 : " + count_50 + " 개   |   ");
        }
        if( count_10 > 0 ) {
            change_message = String.format( change_message + "10원 : " + count_10 + " 개   |   ");
        }
        text_coin.setText(total_coins.toString());
        output.setText(change_message);
    }

    public void return_button_condition() {
        if ( total_coins == 0 ) {
            coin_return.setDisable(true);
        }
        else {
            coin_return.setDisable(false);
        }
    }


    //************** 클라이언트 소켓 관련 매소드 *****************//

    // 클라이언트와 서버를 연결하는 메소드
    public void startClient( String IP, int port ) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    socket = new Socket( IP,port );
                    receive();
                } catch ( Exception e ) {
                    if( !socket.isClosed() ) {
                        stopClient();
                        System.out.println("[ 서버 접속 실패 ]");
                        Platform.exit();
                    }
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    // 클라이언트와 서버의 접속을 종료하는 메소드
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
