package Dijsktra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import Crucial.Format;
import Entity.Country;
import Entity.Road;
import Entity.Village;



public class Dijkstra {
	
	public ArrayList<VertexNode> vertexDijk = new ArrayList<VertexNode>();
	public ArrayList<EdgeNode> edgeDijk = new ArrayList<EdgeNode>();
	
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
	
	
	public void computeAll(VertexNode current){
		
		// Once visited, set current minimum Distance to 0
		current.minimumCost = 0;
		
		// Add VertexNode to the priority Queue
		PriorityQueue<VertexNode> heap = 
				new PriorityQueue<VertexNode>();
		
		heap.add(current);
		
		while (!heap.isEmpty()){
			VertexNode from = heap.poll();
			
			for (EdgeNode leavingRoad: from.edgeToArray){
				
				int cost = leavingRoad.cost;
				VertexNode to = leavingRoad.end;
				
				int cumulativeCost = from.minimumCost + cost;
				
				if(cumulativeCost < to.minimumCost){
					// Only if VertexNode was in priority queue
					heap.remove(to);
					
					//Update Cost
					to.minimumCost = cumulativeCost;
					to.previous = from;
					
					heap.add(to);
				}
			}
		}
	}
	
	public ArrayList<VertexNode> getShortestPath(VertexNode from, VertexNode to){
		
		computeAll(from);
		
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
