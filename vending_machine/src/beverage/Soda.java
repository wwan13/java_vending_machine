package beverage;

// Soda Class
public class Soda extends Beverage {
    public void Soda() {
        this.remain = 3;    // 남은 개수 설정 => 기본 3개
        this.price = 750;   // 가격 설정 => 750원
        this.state = true;  // 판매 가능 여부 상태 설정
    }
}
