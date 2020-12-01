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

    boolean water_is_soldOut;
    boolean coffee_is_soldOut;
    boolean sports_drink_is_soldOut;
    boolean premium_coffee_is_soldOut;
    boolean soda_is_soldOut;

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
        send("Beverage:water");
        total_coins -= 450;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("water");
    }

    @FXML
    public void coffee_Clicked(ActionEvent event) {
        send("Beverage:coffee");
        total_coins -= 500;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("coffee");
    }

    @FXML
    public void sports_drink_Clicked(ActionEvent event) {
        send("Beverage:sports drink");
        total_coins -= 550;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("sports drink");
    }

    @FXML
    public void premium_coffee_Clicked(ActionEvent event) {
        send("Beverage:premium coffee");
        total_coins -= 700;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("premium coffee");
    }

    @FXML
    public void soda_Clicked(ActionEvent event) {
        send("Beverage:soda");
        total_coins -= 750;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
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
        can_insert_1000();
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
        can_insert_1000();
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
        can_insert_1000();
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
        can_insert_1000();
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
        can_insert_1000();
        can_insert_additional_coin();
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
            set_beverage_state();
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
            soda.setText("750");
            if ( soda_is_soldOut ) {
                soda.setDisable(true);
                soda.setText("품절");
            }
        }
        else {
            soda.setDisable(true);
        }

        if ( total_coins >= 700 ) {
            premium_coffee.setDisable(false);
            premium_coffee.setText("700");
            if ( premium_coffee_is_soldOut ) {
                premium_coffee.setDisable(true);
                premium_coffee.setText("품절");
            }
        }
        else {
            premium_coffee.setDisable(true);
        }

        if ( total_coins >= 550 ) {
            sports_drink.setDisable(false);
            sports_drink.setText("550");
            if ( sports_drink_is_soldOut ) {
                sports_drink.setDisable(true);
                sports_drink.setText("품절");
            }
        }
        else {
            sports_drink.setDisable(true);
        }

        if ( total_coins >= 500 ) {
            coffee.setDisable(false);
            coffee.setText("500");
            if ( coffee_is_soldOut ) {
                coffee.setDisable(true);
                coffee.setText("품절");
            }
        }
        else {
            coffee.setDisable(true);
        }

        if ( total_coins >= 450 ) {
            water.setDisable(false);
            water.setText("450");
            if ( water_is_soldOut ) {
                water.setDisable(true);
                water.setText("품절");
            }
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

    // 서버 소켓이 종료되었을 때 여기서도 서버 접속이 종료 되었음을 알려줄 수 있어야 함 !!
    // 서버 접속 버튼을 눌렀는데 서버의 소켓이 열려있지 않을 경우 버튼을 비활성화 할 수 있어야함 !!

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
                System.out.println(message);
                request_condition(message);
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

    public void request_condition( String message ) {
        // request 규칙

        String request_type = message.split(":")[0];
        String detail_message = message.split(":")[1];

        if( request_type.equals("Beverage") ) {
            String[] stock = detail_message.split("-");
            if ( stock[0].equals("0") ) {
                water_is_soldOut = true;
                water.setDisable(true);
                Platform.runLater(() -> {
                    water.setText("품절");
                });
            }
            else if ( stock[0].equals("1") || stock[0].equals("2") || stock[0].equals("3") || stock[0].equals("4") ) {
                water_is_soldOut = false;
                Platform.runLater(() -> {
                    water.setText("450");
                });
            }
            else {
                return;
            }

            if ( stock[1].equals("0") ) {
                coffee_is_soldOut = true;
                coffee.setDisable(true);
                Platform.runLater(() -> {
                    coffee.setText("품절");
                });
            }
            else if ( stock[1].equals("1") || stock[1].equals("2") || stock[1].equals("3") || stock[1].equals("4") ) {
                coffee_is_soldOut = false;
                Platform.runLater(() -> {
                    coffee.setText("500");
                });
            }
            else {
                return;
            }

            if ( stock[2].equals("0") ) {
                sports_drink_is_soldOut = true;
                sports_drink.setDisable(true);
                Platform.runLater(() -> {
                    sports_drink.setText("품절");
                });
            }
            else if ( stock[2].equals("1") || stock[2].equals("2") || stock[2].equals("3") || stock[2].equals("4") ) {
                sports_drink_is_soldOut = false;
                Platform.runLater(() -> {
                    sports_drink.setText("550");
                });
            }
            else {
                return;
            }

            if ( stock[3].equals("0") ) {
                premium_coffee_is_soldOut = true;
                premium_coffee.setDisable(true);
                Platform.runLater(() -> {
                    premium_coffee.setText("품절");
                });
            }
            else if ( stock[3].equals("1") || stock[3].equals("2") || stock[3].equals("3") || stock[3].equals("4") ) {
                premium_coffee_is_soldOut = false;
                Platform.runLater(() -> {
                    premium_coffee.setText("700");
                });
            }
            else {
                return;
            }

            if ( stock[4].equals("0") ) {
                soda_is_soldOut = true;
                soda.setDisable(true);
                Platform.runLater(() -> {
                    soda.setText("품절");
                });
            }
            else if ( stock[4].equals("1") || stock[4].equals("2") || stock[4].equals("3") || stock[4].equals("4") ) {
                soda_is_soldOut = false;
                Platform.runLater(() -> {
                    soda.setText("750");
                });
            }
            else {
                return;
            }
        }
    }
}
