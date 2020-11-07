package Model.Beverage;

// Premium_Coffee Class
public class Premium_Coffee extends Beverage {
    public void Premium_Coffee() {
        this.remain = 3;    // 남은 개수 설정 => 기본 3개
        this.price = 700;   // 가격 설정 => 700원
        this.state = true;  // 판매 가능 여부 상태 설정
    }
}
