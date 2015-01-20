package Dijsktra;
import Entity.Road;



public class EdgeNode {
	
	public VertexNode end;
	public Road data;
	public int cost; // To be determined by tiles
	
	// [F]
	
	public void setEnd(VertexNode end){
		this.end = end;
	}
	
	
	// [C] Constructor
	
	public EdgeNode(VertexNode end, int cost, Road data){
		this.end = end;
		this.cost = cost;
		this.data = data;
	}
	
	public EdgeNode( int cost, Road data){
		this.cost = cost;
		this.data = data;
	}

}

