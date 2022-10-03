package Git;
// Represents a node of a doubly-linked list.

public class ListNode2 {
	private Object value;
	private ListNode2 previous;
	private ListNode2 next;


	public ListNode2(Object v) {
		value = v;
		previous = null;
		next = null;
	}

	public ListNode2(Object v, ListNode2 prev, ListNode2 nx) {
		value = v;
		previous = prev;
		next = nx;
	}

	public Object getValue() { return value; }
	public ListNode2 getPrevious() { return previous; }
	public ListNode2 getNext() { return next; }

	public void setValue(Object v) { value = v; }
	public void setPrevious(ListNode2 previous) { this.previous = previous; }
	public void setNext(ListNode2 nx) { next = nx; }
}


