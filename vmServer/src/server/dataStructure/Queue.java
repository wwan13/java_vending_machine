package server.dataStructure;

import server.Beverage;

// 큐를 사용할때 필요한 노드
class queueNode {
    Beverage data;
    queueNode link;

    // 큐노드 생성자
    public queueNode( Beverage data ) {
        this.data = data;
    }
}

// 자료구조 큐
// 연결리스트로 구현
public class Queue {
    queueNode head;
    queueNode front;
    queueNode rear;

    // 큐 생성자
    public Queue() {
        this.head = null;
        this.front = null;
        this.rear = null;
    }

    // 큐가 비어있는지 아닌지 검사하는 메소드
    public boolean isEmpty() {
        if ( front == null && rear == null ) {
            return true;
        }
        else {
            return false;
        }
    }

    // 큐에 데이터 삽입하는 메소드
    public void enqueue( Beverage data ) {
        queueNode node = new queueNode(data);

        if ( isEmpty() == true ) {
            this.head = node;
            front = this.head;
            rear = this.head;
        }
        else {
            rear.link = node;
            rear = node;
        }
    }

    // 큐에서 데이터를 빼내고 반환하는 메소드
    public Beverage dequeue() {
        queueNode tmpNode;
        Beverage tmp;

        if ( isEmpty() == true ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // 남아있는 데이터가 1개 인 경우
        if ( front.link == null ) {
            tmp = front.data;
            head = null;
            front = null;
            rear = null;
        }
        else {
            tmp = front.data;
            tmpNode = front.link;
            head = tmpNode;
            front.link = null;
            front = head;
        }
        return tmp;
    }

    public Integer length() {
        Integer count = 0;
        queueNode temp = head;
        while ( temp != null ) {
            count++;
            temp = temp.link;
        }
        return count;
    }


}
