package structures;

public class Queue<T> {

	private Node<T> front;

	public Queue() {
		this.front = null;
	}

	public void push(T data) {
		Node<T> nodeAux = new Node<T>(data);
		if (front == null) {
			front = nodeAux;
		} else {
			Node<T> actual = front;
			while (actual.getNext() != null) {
				actual = actual.getNext();
			}
			actual.setNext(nodeAux);
		}
	}

	public T poll() {
		Node<T> actualNode = front;
		front = actualNode.getNext();
		return actualNode.getValue();
	}

	public T peek() throws NullPointerException {
		if (isEmpty()) {
			throw new NullPointerException();
		} else {
			Node<T> actualNode = front;
			return actualNode.getValue();
		}
	}

	public boolean isEmpty() {
		return front == null ? true : false;
	}

	@Override
	public String toString() {
		return "Queu [front=" + front + "]";
	}
	
}