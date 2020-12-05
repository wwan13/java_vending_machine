package vending_machine.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

import java.io.*;

public class Login_Controller {

    @FXML
    public Button lf_login;
    @FXML
    public TextField lf_id_box;
    @FXML
    public TextField lf_pw_box;


    @FXML
    public void lf_login_btn(ActionEvent event) {

    }

    @FXML
    public void lf_signup_btn(ActionEvent e) {
        Main_Controller.new_stage("signup_form","sign up");
    }
}
