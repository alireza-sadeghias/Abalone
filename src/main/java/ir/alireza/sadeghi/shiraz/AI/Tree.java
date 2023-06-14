package ir.alireza.sadeghi.shiraz.ai;

/*
 * A basic tree data structure.
 */

public class Tree<T> {
	public Node<T> root;
	
	public Tree (Node<T> root) {
		this.root = root;
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	public void setRoot(Node<T> newRoot) {
		this.root = newRoot;
	}
	
	
}
