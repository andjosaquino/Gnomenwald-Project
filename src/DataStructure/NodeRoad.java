package DataStructure;

import Crucial.Calc;
import Entity.Road;
import Entity.Village;

public class NodeRoad {

	private Road data;
	public NodeVillage source;
	public NodeVillage end;
	
	public int startX;
	public int startY;
	public int endX;
	public int endY;
	public int clash;
	
	public NodeRoad(int startX,int startY, 
			        int endX,  int endY, 
			        NodeVillage source,
			        NodeVillage end, int clash){
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.source = source;
		this.end = end;
		this.clash = clash;
		
		int distance = Calc.pythagorean(startX-endX,startY-endY);
		
		this.data = new Road(source.getData(), end.getData(), distance, this);
		
	}
	
	public Road getData(){
		return this.data;
	}
}
