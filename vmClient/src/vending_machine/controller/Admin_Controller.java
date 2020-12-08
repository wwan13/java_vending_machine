package vending_machine.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import vending_machine.model.Beverage;
import vending_machine.model.Coin;

import java.awt.*;
import java.time.LocalDate;


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
    // 날자
    @FXML
    public DatePicker start_date;
    @FXML
    public DatePicker finish_date;


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
        String startDate = start_date.getValue().toString();
        String finishDate = finish_date.getValue().toString();
        System.out.println(startDate + "+" +finishDate);

    }
}
