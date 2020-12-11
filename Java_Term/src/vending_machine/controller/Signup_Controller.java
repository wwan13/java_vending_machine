package vending_machine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    private Boolean can_signup = false;


    @FXML
    public void sf_confirm_btn(ActionEvent event) {
        String filepath = String.format(Signup_Controller.class.getResource("").getPath() + "../data_files/userInfo.txt");
        File file = new File(filepath);
        String userInfo;
        String ID;

        String input_ID = sf_id_box.getText();

        try (
                FileReader fr = new FileReader(file);
                BufferedReader bw = new BufferedReader(fr);
        ) {
            if(input_ID.isEmpty()) {
                Main_Controller.popup("아이디를 입력하시오.");
                return;
            }
            while ((userInfo = bw.readLine()) != null) {
                System.out.println(userInfo);
                ID = userInfo.split(" ")[0];
                if (input_ID.equals(ID)) {
                    Main_Controller.popup("이미 존재하는 아이디 입니다.");
                    sf_id_box.setText("");
                    can_signup = false;
                    return;
                }
            }
            Main_Controller.popup("사용 가능한 아이디 입니다.");
            sf_signup.setDisable(false);
            can_signup = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sf_signup_btn(ActionEvent event) throws Exception {
        String ID = sf_id_box.getText();
        String PW = sf_pw_box.getText();
        System.out.println(ID+" + " + PW);
        if (check_password(PW)) {
            Main_Controller.write_file("userInfo.txt",String.format(ID + " " + PW));
            Main_Controller.exit_stage(sf_signup);
            Main_Controller.new_stage("login_form","login");
        } else {
            Main_Controller.popup("사용 할 수 없는 비밀번호 입니다.");
            sf_pw_box.setText("");
            sf_pw_box.setStyle("-fx-border-color:#FF0000");
        }
    }

    @FXML
    public void go_back_btn(ActionEvent event) {
        Main_Controller.exit_stage(sf_signup);
        Main_Controller.new_stage("login_form","login");
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
