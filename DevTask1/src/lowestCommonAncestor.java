import java.util.ArrayList;

public class lowestCommonAncestor {

	public static final int FOUND_A = -2;
	public static final int FOUND_B = -3;
	
	public lowestCommonAncestor() {

	}

	// return the key of the LCA of the two nodes
	public static int getLCA(binaryTree theTree, binaryTree.treeNode val1, binaryTree.treeNode val2) {
		if (val1 == null || val2 == null)
			return -1;

		ArrayList<Integer> route1 = new ArrayList<>();
		ArrayList<Integer> route2 = new ArrayList<>();
		binaryTree.treeNode node1 = val1;
		binaryTree.treeNode node2 = val2;

		do {
			route1.add(node1.key);
			node1 = node1.parent;
		} while (node1 != null);

		do {
			route2.add(node2.key);
			node2 = node2.parent;
		} while (node2 != null);

		for (Integer theNode : route1) {
			for (Integer searchNode : route2)
				if (theNode.equals(searchNode))
					return theNode.intValue();
		}

		return -1;
	}
	

	// the binary tree that we will be traversing
	public class binaryTree {
		treeNode head;

		binaryTree() {
			head = null;
		}

		public int getKey(treeNode theNode) {
			if (theNode != null)
				return theNode.key;
			return -1;
		}

		public void add(int key, treeNode parent, boolean isLeft) {
			treeNode newNode = new treeNode(key, parent, null, null);
			if (head == null) { // check for first node
				head = newNode;
				return;
			}
			if (isLeft)
				parent.lChild = newNode;
			else
				parent.rChild = newNode;
		}

		public class treeNode {
			treeNode parent, lChild, rChild;
			int key;

			treeNode(int key, treeNode parent, treeNode lChild, treeNode rChild) {
				this.key = key;
				this.parent = parent;
				this.lChild = lChild;
				this.rChild = rChild;
			}
		}
	}

	
	//returns key of the LCA
	public static int getDAG_LCA(DAG theDAG, int keyA, int keyB) {
		//2 cases: one of the nodes is LCA || the nodes have a common LCA
		DAG.treeNode curNode = theDAG.head;
		
		int theNodeKey = bothInSubtreeOf(curNode, keyA, keyB);
		if(theNodeKey < 0)
			return -1;
		return theNodeKey;
	}
	
	//recursive function that traverses the tree and find the LCA
	private static int bothInSubtreeOf(DAG.treeNode curNode, int keyA, int keyB) {
		int curNodeVal = curNode.key;
		boolean foundA = false;
		boolean foundB = false;
		for(int i = 0; i < curNode.children.size(); i++) {
			int subTreeOf = bothInSubtreeOf(curNode.children.get(i), keyA, keyB);
			if(subTreeOf >= 0) {
				return subTreeOf;
			}else {
				if(subTreeOf == FOUND_B)
					foundB = true;
				if(subTreeOf == FOUND_A)
					foundA = true;
				}
			}
		if((foundA && (foundB || curNodeVal == keyB)) || (foundB && curNodeVal == keyA))
			return curNodeVal;
		
		if(curNodeVal == keyA && curNodeVal == keyB)
			return curNodeVal;
		
		if(foundA || curNodeVal == keyA)
			return FOUND_A;
		if(foundB || curNodeVal == keyB)
			return FOUND_B;
		
		return -1;
	}
	
	
	
	public class DAG {
		treeNode head = null;

		//pass a list of parents so parents can update list of children
		public void addNode(int key, ArrayList<treeNode> parents) {
			treeNode theNode = new treeNode(key);
			if(head == null)
				head = theNode;
			if (parents != null)
				for (treeNode parentNode : parents)
					if (parentNode != null)
						parentNode.children.add(theNode);
		}
		
		//given a key, tries to find the node in the tree
		public treeNode getNodeWithKey(int key) {
			if(head != null) {
				return getNodeRec(key, head);
			}
			return null;
		}
		
		//looks for a node in the tree, if not found returns null
		private treeNode getNodeRec(int key, treeNode node) {
			for(treeNode child : node.children) {
				treeNode theRetNode = getNodeRec(key, child);
				if(theRetNode != null)
					return theRetNode;
			}
			if(node.key == key)
				return node;
			return null;
		}
		
		//returns all of a given node's children's keys in plaintext
		public String printChildren(treeNode node) {
			String children = "";
			for(treeNode child : node.children)
				children += child.key + "  ";
			return children;
		}
		
		//returns all of a given node's children
		public ArrayList<treeNode> getChildren(treeNode node) {
			if(node != null)
				return node.children;
			return null;
		}
		
		class treeNode {
			public ArrayList<treeNode> children;
			public int key;

			treeNode(int key) {
				children = new ArrayList<treeNode>();
				this.key = key;
			}
		}
	}

}
