package ir.alireza.sadeghi.shiraz.AI;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
	Tree<GameState> gameTree;

	public GameTree(Node<GameState> root) {
		gameTree = new Tree<>(root);
	}
	
	public void setNewRoot(Node<GameState> newRoot) {
		DeleteLayers.deleteEverythingAbove(newRoot, this.gameTree);
	} 
	
	public void buildFullTree(int layers) {
		//builds the entire tree we need (can be a LOT of nodes)
		gameTree.getRoot().children.clear();
		
		List<Node<GameState>> expanding = new ArrayList();
		expanding.add(gameTree.getRoot());

		//create layers
		for (int i = 1; i < layers + 1; i++) {
			AddNodes.addForMultiple(expanding);
			expanding = findAtDepth(i);
		}
	}
	
	//finds all the nodes at a certain depth
	public List<Node<GameState>> findAtDepth(int depth){
		Node<GameState> current = gameTree.getRoot();
		List<Node<GameState>> currentKids = new ArrayList();
		List<Node<GameState>> newKids = new ArrayList();
		
		currentKids.add(current);
		
		for (int i = 0; i < depth; i++) {
			for(int j = 0; j < currentKids.size(); j++) {
				if (currentKids.get(j).children !=null) {
					 newKids.addAll(currentKids.get(j).children);
				}
			}
			currentKids.clear();
			currentKids.addAll(newKids);
			newKids.clear();
		}
		return currentKids;
	}

	public Node<GameState> getRoot() {
		return gameTree.root;
	}
}