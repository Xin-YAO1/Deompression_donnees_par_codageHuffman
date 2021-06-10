import java.util.Map;

import javax.xml.crypto.Data;

public class Node implements Comparable<Node> {
	
	private int frequance;
	private Node l_child;
	private Node r_child;
	

	public Node(int frequance) {	
		this.frequance = frequance;
	}

	public int getFrequance() {
		return frequance;
	}

	public void setFrequance(int frequance) {
		this.frequance = frequance;
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
	 *  Compare the frequency of different characters.
	 */
	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return this.frequance - o.frequance;
	}

	@Override
	public String toString() {
		return "Node [frequance=" + frequance + ", l_child=" + l_child + ", r_child=" + r_child
				+ "]";
	}
	
	
    
	
	
	
}
