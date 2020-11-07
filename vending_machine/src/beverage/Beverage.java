package beverage;

// Bevarege Class
public abstract class Beverage {
    private int price;      // 남은 음료의 개수
    int remain;     // 남은 음료의 개수
    private Boolean state;  // 음료가 판매 가능인지 아닌지 알려주는 필드

    // 음료가 판매 가능인지 아닌지 판별해주는 메소드
    private void get_State() {
        if ( this.remain == 0 ) {
            // 남은 개수가 0이면 판매 불가
            this.state = false;
        }
        else  {
            // 남은 개수가 0이 아니면 판매 가능
            this.state = true;
        }
    }
}
