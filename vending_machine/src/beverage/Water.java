package beverage;

// Water Class
public class Water extends Beverage {
    public void Water() {
        this.remain = 3;                       // 남은 개수 설정 => 기본 3개
        this.price = 450;                      // 가격 설정 => 450원
        this.state = true;                     // 판매 가능 여부 상태 설정
    }
}
