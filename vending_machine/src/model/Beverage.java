package model;

import java.util.Date;


// 기본 음료 클래스
public abstract class Beverage {
    Date current_date;          // 객체 생성 시간을 담은 시간 필드
    int price;                  // 가격
    boolean on_sale;            // 판매중인지 아닌지

    public Beverage() {
        // 판매 가능 상태로
        this.on_sale = true;
    }
}

