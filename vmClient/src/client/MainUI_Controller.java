package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class MainUI_Controller {

    ArrayList<Coin> coins = new ArrayList<Coin>();

    String IP = "127.0.0.1";
    Integer port = 8001;

    Client client = new Client();

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
    public void water_Clicked(ActionEvent event) {
        client.send("water");
    }

    @FXML
    public void coffee_Clicked(ActionEvent event) {
        client.send("coffee");
    }

    @FXML
    public void sports_drink_Clicked(ActionEvent event) {
        client.send("sports drink");
    }

    @FXML
    public void premium_coffee_Clicked(ActionEvent event) {
        client.send("premium coffee");
    }

    @FXML
    public void soda_Clicked(ActionEvent event) {
        client.send("soda");
    }

    @FXML
    public void coinInsert_10(ActionEvent event) {
        coins.add(new Coin(10));
        ui_add_coin();
        set_button_color();
    }

    @FXML
    public void coinInsert_50(ActionEvent event) {
        coins.add(new Coin(50));
        ui_add_coin();
        set_button_color();
    }

    @FXML
    public void coinInsert_100(ActionEvent event) {
        coins.add(new Coin(100));
        ui_add_coin();
        set_button_color();
    }

    @FXML
    public void coinInsert_500(ActionEvent event) {
        coins.add(new Coin(500));
        ui_add_coin();
        set_button_color();
    }

    @FXML
    public void coinInsert_1000(ActionEvent event) {
        coins.add(new Coin(1000));
        ui_add_coin();
        set_button_color();
    }

    // 코인을 반환 해주는 메소드
    @FXML
    public void coinReturn(ActionEvent event) {
        text_coin.setText("");
        coins = null;
        coins = new ArrayList<Coin>();
        set_button_color();
    }


    // 서버를 키고 끄는 메소드
    @FXML
    public void serverOnOff(ActionEvent event) {
        if ( serverBtn.getText().equals("서버접속") ) {
            client.startClient(IP,port);
            serverBtn.setText("접속종료");
            text_IP.setText(IP);
            text_port.setText(port.toString());
            text_state.setText("connected");
        }
        else {
            client.stopClient();
            serverBtn.setText("서버접속");
            text_IP.setText("");
            text_port.setText("");
            text_state.setText("not connected");
        }
    }

    // ui에 코인이 얼마나 들어있는지 보여주는 메소드
    public void ui_add_coin() {
        Integer total_coins = 0;
        for ( Coin coin : coins ) {
            total_coins += coin.value;
        }
        text_coin.setText(total_coins.toString());
    }

    public void set_button_color() {
        Integer total_coins = 0;
        for ( Coin coin : coins ) {
            total_coins += coin.value;
        }

        if ( total_coins >= 750 ) {
            soda.setStyle("-fx-border-color: #55AA41");
        } else {
            soda.setStyle("-fx-border-color: #ED6A5E");
        }
        if ( total_coins >= 700 ) {
            premium_coffee.setStyle("-fx-border-color: #55AA41");
        } else {
            premium_coffee.setStyle("-fx-border-color: #ED6A5E");
        }
        if ( total_coins >= 550 ) {
            sports_drink.setStyle("-fx-border-color: #55AA41");
        } else {
            sports_drink.setStyle("-fx-border-color: #ED6A5E");
        }
        if ( total_coins >= 500 ) {
            coffee.setStyle("-fx-border-color: #55AA41");
        } else {
            coffee.setStyle("-fx-border-color: #ED6A5E");
        }
        if ( total_coins >= 450 ) {
            water.setStyle("-fx-border-color: #55AA41");
        } else {
            water.setStyle("-fx-border-color: #ED6A5E");
        }
    }


}
