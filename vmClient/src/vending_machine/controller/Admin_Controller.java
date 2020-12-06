package vending_machine.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import vending_machine.Main;


public class Admin_Controller {

    @FXML
    public TextField total_coins;

    public void initialize() {
        total_coins.setText(Main_Controller.total_coins.toString());
    }

    public void coin_null(ActionEvent event) {
        Main_Controller.total_coins = 0;
        Platform.runLater(() -> {
            total_coins.setText(Main_Controller.total_coins.toString());
        });
    }
}
