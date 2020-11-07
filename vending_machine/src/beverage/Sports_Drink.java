package beverage;

// Sports_Drink Class
public class Sports_Drink extends Beverage {
    public void Sports_Drink() {
        this.remain = 3;                       // 남은 개수 설정 => 기본 3개
        this.price = 550;                      // 가격 설정 => 550원
        this.state = true;                     // 판매 가능 여부 상태 설정
    }
}
