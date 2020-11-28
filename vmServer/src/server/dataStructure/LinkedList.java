package server.dataStructure;

import server.Beverage;

class ListNode {
    private Beverage data;
    public ListNode link;

    public ListNode( Beverage beverage ) {
        this.data = beverage;
        this.link = null;
    }
}

public class LinkedList {

    private ListNode head;

    public LinkedList() {
        head = null;
    }

    public void insertNode( Beverage beverage ) {
        ListNode newNode = new ListNode(beverage);

        if ( this.head == null ) {
            this.head = newNode;
        }
        else {
            ListNode tempNode = head;

            while( tempNode.link != null ) {
                tempNode = tempNode.link;
            }
            tempNode.link = newNode;
        }
    }

    public void deleteNode() {
        ListNode tempNode;

        if ( this.head == null ) {
            return;
        }

        if ( this.head.link == null ) {
            head.link=null;
        }
        else {
            tempNode = head;

            while( tempNode.link.link == null ) {
                tempNode = tempNode.link;
            }
            tempNode.link = null;
        }
    }

}
