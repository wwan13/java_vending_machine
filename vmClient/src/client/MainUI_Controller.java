package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;

public class MainUI_Controller {

    Client client = new Client();

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
    public Button serverBtn;

    @FXML
    public void serverOnOff(ActionEvent event) {
        if ( serverBtn.getText().equals("서버접속") ) {
            String IP = "127.0.0.1";
            int port = 8001;

            client.startClient(IP,port);
            serverBtn.setText("접속종료");
        }
        else {
            client.stopClient();
            serverBtn.setText("서버접속");
        }
    }


}
