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
    public Button lf_signup;
    @FXML
    public TextField lf_id_box;
    @FXML
    public TextField lf_pw_box;

    @FXML
    public void lf_login_btn(ActionEvent event) {
        String input_ID = lf_id_box.getText();
        String input_PW = lf_pw_box.getText();

        String temp;

        String filepath = String.format(Login_Controller.class.getResource("").getPath() + "../data_files/userInfo.txt");
        File file = new File(filepath);

        try (
                FileReader fr = new FileReader(file);
                BufferedReader bw = new BufferedReader(fr);
        ) {
            if(input_ID.isEmpty() || input_PW.isEmpty()) {
                Main_Controller.popup("아이디 혹은 비밀번호를 입력하세요.");
                return;
            }

            while((temp = bw.readLine()) != null) {
                if(temp.split(" ")[0].equals(input_ID) && temp.split(" ")[1].equals(input_PW)) {
                    Main_Controller.exit_stage(lf_login);
                    Main_Controller.new_stage("admin_ui","admin");
                    return;
                }
            }
            Main_Controller.popup("아이디 혹은 비밀번호가 잘못되었습니다.");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void lf_signup_btn(ActionEvent event) {
        Main_Controller.exit_stage(lf_signup);
        Main_Controller.new_stage("signup_form", "sign up");
    }

    @FXML
    public void go_back_btn(ActionEvent event) {
        Main_Controller.exit_stage(lf_signup);
        Main_Controller.new_stage("MainUI", "Vending Machine");
    }

}
