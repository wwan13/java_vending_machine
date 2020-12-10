package vending_machine.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Popup_Controller {
    @FXML
    public TextField message;

    public void init_popup(String msg) {
        message.setText(msg);
    }
}