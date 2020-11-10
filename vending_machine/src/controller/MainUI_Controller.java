package controller;

import model.*;
import java.util.LinkedList;

public class MainUI_Controller {

    LinkedList<Beverage> water = new LinkedList<Beverage>();
    LinkedList<Beverage> coffee = new LinkedList<Beverage>();
    LinkedList<Beverage> sports_drink = new LinkedList<Beverage>();
    LinkedList<Beverage> premium_coffee = new LinkedList<Beverage>();
    LinkedList<Beverage> soda = new LinkedList<Beverage>();

    // 음료별 초기 개수 설정
    public void init() {
        for ( int i = 0; i < 3; i++ ) {
            Water new_water = new Water();
            Coffee new_coffee = new Coffee();
            Sports_Drink new_sports_drink = new Sports_Drink();
            Premium_Coffee new_premium_coffee = new Premium_Coffee();
            Soda new_soda = new Soda();

            this.water.add(new_water);
            this.coffee.add(new_coffee);
            this.sports_drink.add(new_sports_drink);
            this.premium_coffee.add(new_premium_coffee);
            this.soda.add(new_soda);
        }
    }

    public boolean get_state(LinkedList<Beverage> beverage) {
        if ( beverage == null ) {
            return true;
        }
        else {
            return false;
        }
    }


    public void sale_btn_clicked(LinkedList<Beverage> beverage) {
        if ( get_state(beverage)==true ) {
            beverage.removeLast();
        }
        else {
            System.out.println("재고 부족");
        }
    }
}
