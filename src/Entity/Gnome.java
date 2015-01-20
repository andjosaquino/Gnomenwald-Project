package Entity;
import java.util.ArrayList;

import Crucial.Calc;
import Crucial.Format;


public class Gnome extends Thread{
	
	// [V] Class Variables
	public static int gnomesCreated;
	public static ArrayList<Gnome> gnomesActive = new ArrayList<Gnome>();
	
	// [V] Object Variables
	private String name;
	private Village currentLocation;
	private ArrayList<String> passport = new ArrayList<String>();
	private double money;
	private boolean dijkstra;
	private ArrayList<Village> dijkstraOrder = new ArrayList<Village>();
	
	// [V] Formatting Constants
	public String reference = "@" + this.name;
	
	public void run(){
		
		while(true){
			
			if(!isBroke()){
				if(dijkstra){
					if (dijkstraOrder.size() > 1){
						if (currentLocation.getAdjacentVillages().contains(dijkstraOrder.get(1))){
							travelTo(dijkstraOrder.get(1));
							dijkstraOrder.remove(dijkstraOrder.get(0));
						}
					}
					else if(dijkstraOrder.size() == 1){
						dijkstraOrder.remove(dijkstraOrder.get(0));
						dijkstra = false;
					}
				}
				else{
					Village destination = findAdjacentVillages(currentLocation);
					if (destination != null){
						travelTo(destination);
					}
				}
			}
			
			generateMoney();
			
			try{sleep(3000);} 
			catch(InterruptedException e){}
			
		}
	}
	
	public void recieveDijkstra(ArrayList<Village> order){
		this.dijkstra = true;
		this.dijkstraOrder = order;
		
		Format.T("Recieved Dijkstra");
	}
	
	public Village findAdjacentVillages(Village currentLocation){

		ArrayList<Village> pendingDestinations = currentLocation.getNeighbors();
		
		if (pendingDestinations.size() != 0){
			int random = Calc.calcRandom(pendingDestinations.size());
			Village randomDestination = pendingDestinations.get(random);
			return pendingDestinations.get(random);
		}
		else{
			return null;
		}
		
	}
	
	// [F] Gnome Functions
	public boolean isBroke(){
		
		boolean chain = false;
		
		for (Road r : this.currentLocation.getLeavingRoads()){
			if (this.money < r.getDistance()){
				chain = chain || true;
			}
			else{
				chain = chain || false;
			}
		}
		
		return chain;
	}
	
	public void payMoney(Village village, int cost){
		this.money -= cost;
		village.collectMoney(cost);
	}
	
	public void generateMoney(){
		int randomMoney = Calc.calcRandom(100,150);
		this.money += randomMoney;
		Format.C(this.name + " generated " + Integer.toString(randomMoney) +"|" + this.money);
	}
	
	public synchronized void travelTo(Village village){
		
		int populationThreshold;
		
		if (village.closed){
			Format.C("Sorry, " + village.name + " is closing.");
		}
		else if(!village.isFull()){
			
			int distance = 0;
			
			for (Road r : this.currentLocation.getLeavingRoads()){
				if (r.getEnd() == village){
					distance = r.getDistance();
				}
			}
			
			
			
			Format.C(this.name + " is travelling from " + 
					currentLocation.name + " to " + village.name);

			//Leave home
			this.currentLocation.removeResident(this);
			this.currentLocation = village;
			village.addResident(this);
			// Pay Money
			int cost = distance;
			this.payMoney(village,cost);
			// Add village to passport
		}
		else{
			Format.C("Sorry, " + village.name + 
					" is barring you under unfortunate circumstances");
		}
	}
	
	// [F] Getters
	public String getName_(){
		Format.T("Gnome.getName(): 01" + reference);
		return this.name;
	}
	public Village getCurrentLocation(){
		Format.T("Gnome.getCurrentLocation(): 01" + reference);
		return this.currentLocation;
	}
	public double getMoney(){
		Format.T("Gnome.getMoney(): 01" + reference);
		return this.money;
	}
	public ArrayList<String> getPassport(){
		Format.T("Gnome.getPassport(): 01" + reference);
		return this.passport;
	}
	
	
	// [C] Constructor
	public Gnome(String name, Village spawn){
		this.name = name;
		this.currentLocation = spawn;
		this.money = 600;

		Format.C(this.name + " initiated.");
		
		// Add to Spawn residents
		spawn.addResident(this);
		
		gnomesActive.add(this);
		gnomesCreated ++;
	}
	
	
}
