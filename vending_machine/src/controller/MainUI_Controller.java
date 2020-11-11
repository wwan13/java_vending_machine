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

    // 재고가 남아있는지 아닌지 확인
    public boolean get_state(LinkedList<Beverage> beverage) {
        if ( beverage.size() == 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    // 판매 버튼이 눌렸을때
    public void sale_btn_clicked(LinkedList<Beverage> beverage) {
        // 판매 가능이면
        if ( get_state(beverage)==true ) {
            beverage.removeLast(); // 재고 하나 삭제
            System.out.println(beverage.size()+ "개 남음");
        }
        else {
            System.out.println("재고 부족");
        }
    }
}
