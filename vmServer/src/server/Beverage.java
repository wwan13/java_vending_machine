package server;

public class Beverage {
    String name;
    Integer price;

    // 음료 생성자
    public Beverage( String name, Integer price ) {
        this.name = name;
        this.price = price;
    }

    // 이 음료의 이름을 반환 해주는 메소드
    public String getName() {
        return this.name;
    }

    // 이 음료의 가격을 반환 해주는 함수
    public  Integer getPrice() {
        return this.price;
    }
}
