import java.util.Map;

import javax.xml.crypto.Data;

public class Node implements Comparable<Node> {
	
	private int frequant;
	private Node l_child;
	private Node r_child;
	

	public Node(int frequant) {	
		this.frequant = frequant;
	}

	public int getFrequant() {
		return frequant;
	}

	public void setFrequant(int frequant) {
		this.frequant = frequant;
	}

	public Node getL_child() {
		return l_child;
	}

	public void setL_child(Node l_child) {
		this.l_child = l_child;
	}

	public Node getR_child() {
		return r_child;
	}

	public void setR_child(Node r_child) {
		this.r_child = r_child;
	}
	
	/**
	 *  comparer la taille des differente frequant de caractere
	 */
	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return this.frequant - o.frequant;
	}

	@Override
	public String toString() {
		return "Node [frequant=" + frequant + ", l_child=" + l_child + ", r_child=" + r_child
				+ "]";
	}
	
	
    
	
	
	
}
