package Dijsktra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import Crucial.Format;
import Entity.Country;
import Entity.Road;
import Entity.Village;



public class Prim {
	
	public ArrayList<VertexNode> vertexDijk = new ArrayList<VertexNode>();
	public ArrayList<EdgeNode> edgeDijk = new ArrayList<EdgeNode>();
	
	public ArrayList<VertexNode> unvisited = vertexDijk;
	public ArrayList<VertexNode> visited = new ArrayList<VertexNode>();
	
	
	public PriorityQueue<EdgeNode> shortEdges = 
			new PriorityQueue<EdgeNode>();
	
	public ArrayList<EdgeNode> tree;
	
	public void loadAll(Country country){
		
		// Package villages into Dijkstra simulation entities
		System.out.println(country.villages.size());
		
		for(Village village : country.villages){
			
			Format.T("Adding " + village.name + " to Dijkstra");
			
			VertexNode villageDijk = new VertexNode(village);
			
			// Package Roads and put them into VertexNode EdgeNode arrays
			for (Road road : village.getLeaving()){
				
				EdgeNode roadDijk = new EdgeNode(road.getDistance(),road);
				villageDijk.addEdge(roadDijk);
				
				edgeDijk.add(roadDijk);
				
			}
			
			vertexDijk.add(villageDijk);			
			
		}
		
		/* Stick ends onto the edges of the roads by iterating through
		 * the edges array, and searching for their end villages and
		 * making the references.
		 */
		
		for(EdgeNode headless : edgeDijk){
			
			// Saving what actual Road end is.
			Village head = headless.data.getEnd();
			
			for (VertexNode potentialHead : vertexDijk){
				
				if (potentialHead.data == head){
					headless.setEnd(potentialHead);
				}
			}
		}
		
		for (EdgeNode e: this.edgeDijk){
			System.out.println(e + " | ");
		}
		
		
		for (VertexNode v: this.vertexDijk){
			System.out.println(v + " | ");
		}

	}
	
	public VertexNode findVillage(Village village){
		
		for (VertexNode vertex : vertexDijk){
			
			if (village == vertex.data){
				return vertex;
			}
		}
		return null;
	}
	
	/*
	public ArrayList<EdgeNode> compute(VertexNode focus){

		
		if (unvisited.size() == 1){
			return new ArrayList<EdgeNode>();
		}
		
		else{
			focus = unvisited.get(0);
			
			for (EdgeNode i : focus.edgeToArray){
				shortEdges.add(i);
			}
			
			EdgeNode shortest = shortEdges.poll();
			VertexNode destination = shortest.end;
			
			if(visited.contains(destination)){
				return shortEdges.add(compute(focus));
			}
			else{
			
				shortEdges.add(shortest);
				unvisited.remove(destination);
				visited.add(destination);
				
				shortEdges.add(shortest);
				
				return shortEdges.addAll(compute(destination));
			}
		}
		
		
			
		}*/
	
	public ArrayList<VertexNode> getShortestPath(VertexNode from, VertexNode to){
		
		//computeAll(from);
		
		ArrayList<VertexNode> pathReverse = new ArrayList<VertexNode>();
		
		// Add to path
		for (VertexNode last = to; last != null; last = last.previous){
			pathReverse.add(last);	
		}
		
		// Now, reverse it!
		ArrayList<VertexNode> pathStraight = new ArrayList<VertexNode>();
		
		for (int index = pathReverse.size()-1; index >= 0; index--){
			pathStraight.add(pathReverse.get(index));
		}
		
		return pathStraight;
	}
	
	public ArrayList<Village> convertToVillage(ArrayList<VertexNode> order){
		
		/* Separate Function to properly engage with real world and jump 
		 * out of the simulation.*/

		ArrayList<Village> orderInVillage = new ArrayList<Village>();
		
		for (VertexNode v : order){
			orderInVillage.add(v.data);
		}
		
		return orderInVillage;
		
	}
}
