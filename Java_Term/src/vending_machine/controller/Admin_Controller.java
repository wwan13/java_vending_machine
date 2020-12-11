package vending_machine.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import vending_machine.model.Beverage;
import vending_machine.model.Coin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Admin_Controller {

    @FXML
    public TextField total_coins;
    // 음료 재고
    @FXML
    public TextField water_stock;
    @FXML
    public TextField coffee_stock;
    @FXML
    public TextField sports_drink_stock;
    @FXML
    public TextField premium_coffee_stock;
    @FXML
    public TextField soda_stock;
    // 거스름돈
    @FXML
    public TextField change_10;
    @FXML
    public TextField change_50;
    @FXML
    public TextField change_100;
    @FXML
    public TextField change_500;
    @FXML
    public TextField change_1000;

    @FXML
    public TextField ID;
    @FXML
    public PasswordField PW;

    @FXML
    public Button go_back;

    @FXML
    public TextArea soldout;

    @FXML
    public LineChart<String,Integer> line_chart;

    XYChart.Series series;


    // fxml 초기화
    public void initialize() {

        // 잔고
        total_coins.setText(Main_Controller.total_coins.toString());

        // 음료 재고
        water_stock.setText(Main_Controller.water_stock.length().toString());
        coffee_stock.setText(Main_Controller.coffee_stock.length().toString());
        sports_drink_stock.setText(Main_Controller.sports_drink_stock.length().toString());
        premium_coffee_stock.setText(Main_Controller.premium_coffee_stock.length().toString());
        soda_stock.setText(Main_Controller.soda_stock.length().toString());

        // 거스름돈
        change_10.setText(Main_Controller.change_10.length.toString());
        change_50.setText(Main_Controller.change_50.length.toString());
        change_100.setText(Main_Controller.change_100.length.toString());
        change_500.setText(Main_Controller.change_500.length.toString());
        change_1000.setText(Main_Controller.change_1000.length.toString());

        // ID PW
        ID.setText(Login_Controller.userID);
//        PW.setText(Login_Controller.userPW);

        init_soldout();
    }

    // 잔고 반환하는 메소드
    @FXML
    public void coin_null(ActionEvent event) {
        Main_Controller.total_coins = 0;
        Platform.runLater(() -> {
            total_coins.setText(Main_Controller.total_coins.toString());
        });
    }

    // 음료별 재고 주가
    @FXML
    public void add_water(ActionEvent event) {
        Main_Controller.water_stock.enqueue(new Beverage("water", 450));
        water_stock.setText(Main_Controller.water_stock.length().toString());
    }

    @FXML
    public void add_coffee(ActionEvent event) {
        Main_Controller.coffee_stock.enqueue(new Beverage("coffee", 500));
        coffee_stock.setText(Main_Controller.coffee_stock.length().toString());
    }

    @FXML
    public void add_sports_drink(ActionEvent event) {
        Main_Controller.sports_drink_stock.enqueue(new Beverage("sports drink", 550));
        sports_drink_stock.setText(Main_Controller.sports_drink_stock.length().toString());
    }

    @FXML
    public void add_premium_coffee(ActionEvent event) {
        Main_Controller.premium_coffee_stock.enqueue(new Beverage("sports drink", 700));
        premium_coffee_stock.setText(Main_Controller.premium_coffee_stock.length().toString());
    }

    @FXML
    public void add_soda(ActionEvent event) {
        Main_Controller.soda_stock.enqueue(new Beverage("soda", 750));
        soda_stock.setText(Main_Controller.soda_stock.length().toString());
    }

    // 거스름돈 추가 매소드
    @FXML
    public void add_10(ActionEvent event) {
        Main_Controller.change_10.stackPush(new Coin(10));
        change_10.setText(Main_Controller.change_10.length.toString());
    }

    @FXML
    public void add_50(ActionEvent event) {
        Main_Controller.change_50.stackPush(new Coin(50));
        change_50.setText(Main_Controller.change_50.length.toString());
    }

    @FXML
    public void add_100(ActionEvent event) {
        Main_Controller.change_100.stackPush(new Coin(100));
        change_100.setText(Main_Controller.change_100.length.toString());
    }

    @FXML
    public void add_500(ActionEvent event) {
        Main_Controller.change_500.stackPush(new Coin(500));
        change_500.setText(Main_Controller.change_500.length.toString());
    }

    @FXML
    public void add_1000(ActionEvent event) {
        Main_Controller.change_1000.stackPush(new Coin(1000));
        change_1000.setText(Main_Controller.change_1000.length.toString());
    }

    @FXML
    public void show_rate(ActionEvent event) {
        init_chart();
    }

    @FXML
    public void go_back_btn(ActionEvent event) {
        Main_Controller.exit_stage(go_back);
        Main_Controller.new_stage("MainUI","Vending Machine");
    }

    @FXML
    public void change_PW(ActionEvent event) throws Exception {
        Main_Controller.popup("비밀번호 변경이 완료되었습니다.");
    }

    void init_soldout() {
        String filepath = String.format(Signup_Controller.class.getResource("").getPath() + "../data_files/soldout.txt");
        File file = new File(filepath);
        String message;

        try (
                FileReader fr = new FileReader(file);
                BufferedReader bw = new BufferedReader(fr);
        ) {
            while ((message = bw.readLine()) != null) {
                soldout.appendText(message+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void init_chart() {
        Integer month;
        Integer price;
        String temp;

        Integer monthly_rate[] = {0,0,0,0,0,0,0,0,0,0,0,0};

        String filepath = String.format(Signup_Controller.class.getResource("").getPath() + "../data_files/Beverage.txt");
        File file = new File(filepath);
        try (
                FileReader fr = new FileReader(file);
                BufferedReader bw = new BufferedReader(fr);
        ) {
            while((temp = bw.readLine())!=null) {
                month = Integer.parseInt(temp.split(":")[1].split("-")[1]);
                price = Integer.parseInt(temp.split(":")[2]);
                monthly_rate[month-1] += price;
            }

            series = new XYChart.Series<String,Integer>();
            series.setName("월별 통계");
            series.getData().add(new XYChart.Data("1" ,monthly_rate[0]));
            series.getData().add(new XYChart.Data("2" ,monthly_rate[1]));
            series.getData().add(new XYChart.Data("3" ,monthly_rate[2]));
            series.getData().add(new XYChart.Data("4" ,monthly_rate[3]));
            series.getData().add(new XYChart.Data("5" ,monthly_rate[4]));
            series.getData().add(new XYChart.Data("6" ,monthly_rate[5]));
            series.getData().add(new XYChart.Data("7" ,monthly_rate[6]));
            series.getData().add(new XYChart.Data("8" ,monthly_rate[7]));
            series.getData().add(new XYChart.Data("9" ,monthly_rate[8]));
            series.getData().add(new XYChart.Data("10" ,monthly_rate[9]));
            series.getData().add(new XYChart.Data("11" ,monthly_rate[10]));
            series.getData().add(new XYChart.Data("12" ,monthly_rate[11]));

            line_chart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
