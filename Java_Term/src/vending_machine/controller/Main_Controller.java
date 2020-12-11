package vending_machine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vending_machine.model.Beverage;
import vending_machine.model.data_structure.Queue;
import vending_machine.model.data_structure.Stack;
import vending_machine.model.Coin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main_Controller {

    // 입력받은 돈을 갖고 있는 배열
    static ArrayList<Coin> coins;

    // 거스름돈을 담고 있는 스택
    static Stack change_10;
    static Stack change_50;
    static Stack change_100;
    static Stack change_500;
    static Stack change_1000;

    // 음료의 재고를 담은 큐
    static Queue water_stock;
    static Queue coffee_stock;
    static Queue sports_drink_stock;
    static Queue premium_coffee_stock;
    static Queue soda_stock;

    static Integer total_coins = 0;

    static Boolean can_init = true;

// FXML 변수 -------------------------------------

    // main
    @FXML
    public TextField text_coin;
    @FXML
    public Pane main_contents;
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
    public Pane contents_wrap;


    //------------------------------------------------

    // 컨트롤러 초기화 관련 메소드 --------------------------

    // 컨트롤러 초기화 메소드
    public void initialize() {
        init_change();
        coin_10.setDisable(false);
        coin_50.setDisable(false);
        coin_100.setDisable(false);
        coin_500.setDisable(false);
        coin_1000.setDisable(false);
        log_in.setDisable(false);
        if(can_init==true) {
            coins = new ArrayList<Coin>();
            init_Beverage();
            can_init = false;
        }
        set_beverage_state();
        can_insert_additional_coin();
        can_insert_1000();
    }

    // 거스름돈을 각각 3개 씩으로 초기화 하는 함수
    public void init_change() {
        change_10 = new Stack();
        change_50 = new Stack();
        change_100 = new Stack();
        change_500 = new Stack();
        change_1000 = new Stack();

        // 3개식 집어넣음
        for (int i = 0; i < 3; i++) {
            change_10.stackPush(new Coin(10));
        }
        for (int i = 0; i < 3; i++) {
            change_50.stackPush(new Coin(50));
        }
        for (int i = 0; i < 3; i++) {
            change_100.stackPush(new Coin(100));
        }
        for (int i = 0; i < 3; i++) {
            change_500.stackPush(new Coin(500));
        }
        for (int i = 0; i < 3; i++) {
            change_1000.stackPush(new Coin(1000));
        }
    }

    // 초기 음료의 갯수를 3개로 초기화 하는 메소드
    public void init_Beverage() {
        water_stock = new Queue();
        coffee_stock = new Queue();
        sports_drink_stock = new Queue();
        premium_coffee_stock = new Queue();
        soda_stock = new Queue();

        for (int i = 0; i < 3; i++) {
            water_stock.enqueue(new Beverage("water", 450));
        }
        for (int i = 0; i < 3; i++) {
            coffee_stock.enqueue(new Beverage("coffee", 550));
        }
        for (int i = 0; i < 3; i++) {
            sports_drink_stock.enqueue(new Beverage("sports drink", 550));
        }
        for (int i = 0; i < 3; i++) {
            premium_coffee_stock.enqueue(new Beverage("premium coffee", 700));
        }
        for (int i = 0; i < 3; i++) {
            soda_stock.enqueue(new Beverage("soda", 750));
        }
    }

    // ---------------------------------------------

    // FXML 버튼 관련 메소드 ----------------------------

    // main ui

    // 음료 버튼

    @FXML
    public void water_Clicked(ActionEvent event) {
        Date current_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        water_stock.dequeue();
        total_coins -= 450;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("water");
        write_file("Beverage.txt","water:"+dateFormat.format(current_date)+":450");
        if(water_stock.isEmpty()) {
            write_file("soldout.txt", "water : " + dateFormat.format(current_date));
        }
    }

    @FXML
    public void coffee_Clicked(ActionEvent event) {
        Date current_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        coffee_stock.dequeue();
        total_coins -= 500;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("coffee");
        write_file("Beverage.txt","coffee:"+dateFormat.format(current_date)+":500");
        if(water_stock.isEmpty()) {
            write_file("soldout.txt", "coffee : " + dateFormat.format(current_date));
        }
    }

    @FXML
    public void sports_drink_Clicked(ActionEvent event) {
        Date current_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sports_drink_stock.dequeue();
        total_coins -= 550;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("sports drink");
        write_file("Beverage.txt","sports drink:"+dateFormat.format(current_date)+":550");
        if(water_stock.isEmpty()) {
            write_file("soldout.txt", "sports drink : " + dateFormat.format(current_date));
        }
    }

    @FXML
    public void premium_coffee_Clicked(ActionEvent event) {
        Date current_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        premium_coffee_stock.dequeue();
        total_coins -= 700;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("premium coffee");
        write_file("Beverage.txt","premium coffee:"+dateFormat.format(current_date)+":700");
        if(water_stock.isEmpty()) {
            write_file("soldout.txt","premium coffee : " + dateFormat.format(current_date));
        }
    }

    @FXML
    public void soda_Clicked(ActionEvent event) {
        Date current_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        soda_stock.dequeue();
        total_coins -= 750;
        set_beverage_state();
        display_set_coin();
        can_insert_additional_coin();
        can_insert_1000();
        return_button_condition();
        output.setText("soda");
        write_file("Beverage.txt","soda:"+dateFormat.format(current_date)+":750");
        if(water_stock.isEmpty()) {
            write_file("soldout.txt", "soda : " + dateFormat.format(current_date));
        }
    }

    // 동전 입력 버튼

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
        can_insert_1000();
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
        can_insert_additional_coin();
        can_insert_1000();
        output.setText("");
    }

    // 코인을 반환 버튼

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

    // 로그인 동작 버트
    @FXML
    public void login_btn(ActionEvent event) {
        exit_stage(log_in);
        new_stage("login_form", "login");
    }

    // -----------------------------------------------

    // login form ------------------------------------


    // -----------------------------------------------

    // 기타 버튼의 상태 or 동작을 하는 메소드

    // ui에 코인이 얼마나 들어있는지 보여주는 메소드
    public void display_set_coin() {
        text_coin.setText(total_coins.toString());
    }

    public void set_beverage_state() {
        if (total_coins >= 750) {
            soda.setDisable(false);
            soda.setText("750");
            if (soda_stock.isEmpty()) {
                soda.setDisable(true);
                soda.setText("품절");
            }
        } else {
            soda.setDisable(true);
            if (soda_stock.isEmpty()) {
                soda.setText("품절");
            }
        }

        if (total_coins >= 700) {
            premium_coffee.setDisable(false);
            premium_coffee.setText("700");
            if (premium_coffee_stock.isEmpty()) {
                premium_coffee.setDisable(true);
                premium_coffee.setText("품절");
            }
        } else {
            premium_coffee.setDisable(true);
            if (premium_coffee_stock.isEmpty()) {
                premium_coffee.setText("품절");
            }
        }

        if (total_coins >= 550) {
            sports_drink.setDisable(false);
            sports_drink.setText("550");
            if (sports_drink_stock.isEmpty()) {
                sports_drink.setDisable(true);
                sports_drink.setText("품절");
            }
        } else {
            sports_drink.setDisable(true);
            if (sports_drink_stock.isEmpty()) {
                sports_drink.setText("품절");
            }
        }

        if (total_coins >= 500) {
            coffee.setDisable(false);
            coffee.setText("500");
            if (coffee_stock.isEmpty()) {
                coffee.setDisable(true);
                coffee.setText("품절");
            }
        } else {
            coffee.setDisable(true);
            if (coffee_stock.isEmpty()) {
                coffee.setText("품절");
            }
        }

        if (total_coins >= 450) {
            water.setDisable(false);
            water.setText("450");
            if (water_stock.isEmpty()) {
                water.setDisable(true);
                water.setText("품절");
            }
        } else {
            water.setDisable(true);
            if (water_stock.isEmpty()) {
                water.setText("품절");
            }
        }
    }

    // 3000원이 넘게 들어가면 버튼을 모두 비활성화하고 아니면 활성화하는 메소드
    public void can_insert_additional_coin() {
        if (total_coins >= 3000) {
            coin_10.setDisable(true);
            coin_50.setDisable(true);
            coin_100.setDisable(true);
            coin_500.setDisable(true);
            coin_1000.setDisable(true);
        } else {
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
        for (Coin coin : coins) {
            if (coin.value == 1000) {
                count++;
            }
        }
        if (count >= 3) {
            coin_1000.setDisable(true);
        } else {
            coin_1000.setDisable(false);
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
            if (total_coins != 0) {
                change_message = "|   ";
            }

            if (total_coins - 1000 >= 0 && change_1000.isEmpty() == false) {
                change_value = change_1000.stackPop().value;
                total_coins = total_coins - change_value;
                count_1000++;
            } else if (total_coins - 500 >= 0 && change_500.isEmpty() == false) {
                change_value = change_500.stackPop().value;
                total_coins = total_coins - change_value;
                count_500++;
            } else if (total_coins - 100 >= 0 && change_100.isEmpty() == false) {
                change_value = change_100.stackPop().value;
                total_coins = total_coins - change_value;
                count_100++;
            } else if (total_coins - 50 >= 0 && change_50.isEmpty() == false) {
                change_value = change_50.stackPop().value;
                total_coins = total_coins - change_value;
                count_50++;
            } else if (total_coins - 10 >= 0 && change_10.isEmpty() == false) {
                change_value = change_10.stackPop().value;
                total_coins = total_coins - change_value;
                count_10++;
            } else {
                output.setText("잔돈 부족");
                break;
            }
        }
        if (count_1000 > 0) {
            change_message = String.format(change_message + "1000원 : " + count_1000 + " 개   |   ");
        }
        if (count_500 > 0) {
            change_message = String.format(change_message + "500원 : " + count_500 + " 개   |   ");
        }
        if (count_100 > 0) {
            change_message = String.format(change_message + "100원 : " + count_100 + " 개   |   ");
        }
        if (count_50 > 0) {
            change_message = String.format(change_message + "50원 : " + count_50 + " 개   |   ");
        }
        if (count_10 > 0) {
            change_message = String.format(change_message + "10원 : " + count_10 + " 개   |   ");
        }
        text_coin.setText(total_coins.toString());
        output.setText(change_message);
    }

    public void return_button_condition() {
        if (total_coins == 0) {
            coin_return.setDisable(true);
        } else {
            coin_return.setDisable(false);
        }
    }


    // 모든 컨트롤러들에서 공통적으로 사용하는 메소드
    static public void exit_stage(Button btn) {
        Stage stage = (Stage)btn.getScene().getWindow();
        stage.close();
    }


    // 새로운 화면을 띄워주는 메소드
    static public void new_stage(String name, String title) {
        FXMLLoader loader = new FXMLLoader(Main_Controller.class.getResource("../view/" + name + ".fxml"));
        Parent root;
        Stage stage;
        try {
            root = (Parent) loader.load();
            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 팝업창을 띄워주는 메소드
    static public void popup(String message) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main_Controller.class.getResource("../view/popup.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);

        Popup_Controller pop = loader.getController();
        pop.init_popup(message);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    static public void write_file(String filename, String message) {
        String filepath = String.format(Main_Controller.class.getResource("").getPath() + "../data_files/"+filename);
        File file = new File(filepath);

        try (
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
