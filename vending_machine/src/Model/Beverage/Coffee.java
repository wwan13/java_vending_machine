package Model.Beverage;

// Coffee Class
public class Coffee extends Beverage {
    public void Coffee() {
        this.remain = 3;    // 남은 개수 설정 => 기본 3개
        this.price = 500;   // 가격 설정 => 500원
        this.state = true;  // 판매 가능 여부 상태 설정
    }
}
