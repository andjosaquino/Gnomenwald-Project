package Entity;

import Crucial.Format;
import DataStructure.NodeRoad;

public class Road {
	
	private Village source;
	private Village end;
	private int distance;
	private NodeRoad mapNode;
	
	// [C] Constructor
	public Road(Village source, Village end, int distance, NodeRoad nodeReference){
		this.source = source;
		this.end = end;
		this.distance = distance;
		this.mapNode = nodeReference;
		
		source.connectVillage(this,end);
		
		Format.T("Road.Constructor() | " + this + " | "
				+ "Road from " + source.name 
				+ " built to " 
				+ end.name
				+ " for a distance of "
				+ Integer.toString(distance));
	}

	public NodeRoad getMapNode() {
		return this.mapNode;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public Village getSource() {
		return this.source;
	}
	
	public Village getEnd() {
		return this.end;
	}
}
