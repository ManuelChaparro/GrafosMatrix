package structures;

public class Node<T> {
	
	private T data;
	private Node<T> next;
	private boolean visit;

	public Node(T data) {
		this.data = data;
		visit = false;
	}

	public T getValue() {
		return data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isVisit() {
		return visit;
	}

	public void setVisit(boolean visit) {
		this.visit = visit;
	}

	public String toString() {
		return "Node [data=" + data + ", next=" + next + ", visit=" + visit + "]";
	}	
}
