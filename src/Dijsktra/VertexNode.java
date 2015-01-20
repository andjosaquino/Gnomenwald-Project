package Dijsktra;
import java.util.ArrayList;

import Entity.Village;

public class VertexNode implements Comparable<VertexNode>{
	
	public Village data;
	public ArrayList<EdgeNode> edgeToArray = new ArrayList<EdgeNode>();
	
	// [V] Data for Dijsktra
	public int minimumCost = Integer.MAX_VALUE;
	public VertexNode previous;
	
	// [F] CompareTo Function
	public int compareTo(VertexNode other) {
		return Integer.compare(this.minimumCost, 
				other.minimumCost);
	}
	
	// [F] General
	public void addEdge(EdgeNode edge){
		edgeToArray.add(edge);
	}
	
	// [C] Constructor
	public VertexNode(Village village){
		this.data = village;
	}


}
