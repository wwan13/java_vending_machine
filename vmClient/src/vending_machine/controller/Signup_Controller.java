package vending_machine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup_Controller {

    @FXML
    public TextField sf_id_box;
    @FXML
    public PasswordField sf_pw_box;
    @FXML
    public Button sf_confirm;
    @FXML
    public Button sf_signup;

    @FXML
    public TextField popup_text;

    private Boolean can_signup = false;


    @FXML
    public void sf_confirm_btn(ActionEvent event) {
        String filepath = String.format(Login_Controller.class.getResource("").getPath() + "../data_files/userInfo.txt");
        File file = new File(filepath);
        String userInfo;
        String ID;

        String current_ID = sf_id_box.getText();

        try (
                FileReader fr = new FileReader(file);
                BufferedReader bw = new BufferedReader(fr);
        ) {
            while ((userInfo = bw.readLine()) != null) {
                ID = userInfo.split(" ")[0];
                System.out.println(ID);
                if (current_ID.equals(ID) || ID.length() == 0) {
                    sf_confirm.setText("사용불가");
                    sf_id_box.setText("");
                    can_signup = false;
                    break;
                }
                sf_confirm.setText("사용가능");
                sf_signup.setDisable(false);
                can_signup = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sf_signup_btn(ActionEvent event) {
        if (check_password(sf_pw_box.getText())) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }

    // 비밀번호 유효성 검사하는 메소드
    private boolean check_password(String password) {
        Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
        Matcher m = p.matcher(password);

        if (m.find() && (password.length() >= 8)) {
            return true;
        } else {
            return false;
        }
    }

}
