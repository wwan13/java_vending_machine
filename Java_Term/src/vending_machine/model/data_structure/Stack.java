package vending_machine.model.data_structure;

import vending_machine.model.Coin;

// 스택의 노드 -> 링크드 리스트로 구현
class stackNode {
    Coin data;
    stackNode link;

    // 스택노드 생성자
    stackNode(Coin data) {
        this.data = data;
        link = null;
    }
}

// 스택 자료구조
public class Stack {
    stackNode top;
    public Integer length;

    public Stack() {
        this.top = null;
        this.length=0;
    }

    // 스택이 비어있는지 아닌지 검사하는 메소드
    public boolean isEmpty() {
        if (top == null) {
            return true;
        } else {
            return false;
        }
    }

    // 스택 푸쉬 메소드
    public void stackPush(Coin data) {
        stackNode node = new stackNode(data);
        node.link = top;
        top = node;
        length++;
    }

    // 스택에서 top에 있는 값을 반환하는 메소드
    public Coin stackPeek() {
        if (isEmpty() == true) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return top.data;
        }
    }

    // 스택에서 top에 있는 값을 반환하고 삭제하는 메소드
    public Coin stackPop() {
        Coin data = stackPeek();
        top = top.link;
        length--;
        return data;
    }
}
