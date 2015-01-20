package Entity;
import java.util.ArrayList;

import Crucial.Calc;
import Crucial.Format;
import DataStructure.NodeGnome;
import DataStructure.NodeRoad;
import DataStructure.NodeVillage;

public class Village {
	
	// [V] Class variables
	public static int villagesCreated;
	public static ArrayList<Village> villageActive = new ArrayList<Village>();
	
	// [V] Object Variables
	public String name;
	public boolean closed;
	private double money;
	private int residentCount;
	
	private Country country;
	private ArrayList<Road> leavingRoads = new ArrayList<Road>();
	private ArrayList<Road> arrivingRoads = new ArrayList<Road>();
	private ArrayList<Village> adjacentVillages = new ArrayList<Village>();
	private ArrayList<Gnome> residents = new ArrayList<Gnome>();
	

	// [V] Object Constants
	private int populationThreshold;
	
	// [V] Formatting Constants
	public String reference = "@" + this.name;
	
	// [F] Object Functions;
	public void collectMoney(int payment){
		this.money += payment;
	}
	
	public void destroy(int condition){
		Format.T("Village.destroy(): 01" + "|" + this);
		
		this.closed = true; //Prevents anyone from entering
		
		// Push all gnomes out of the village
		
		for (Gnome resident : residents){
			
			if(getAdjacentVillages().size() != 0){
				int randomIndex = Calc.calcRandom(getAdjacentVillages().size());
				resident.travelTo(getAdjacentVillages().get(randomIndex));
			}
			else{
				Format.C("DEATH TO US ALL");
			}
		}
		
		
		// Break all connections
		
		if (condition == 0){
			
			// Deal with arriving roads
			
			java.util.Iterator<Road> iter = arrivingRoads.iterator();
			while( iter.hasNext()) {
			    Road arrivingRoad = iter.next();
			    if(true) {
			        
					arrivingRoad.getSource().getAdjacentVillages().remove(this);
					arrivingRoad.getSource().removeLeavingRoad(arrivingRoad);
					iter.remove();
					
					//Remove References
			    }
			}
			
			
			// Deal with leaving roads
			
			java.util.Iterator<Road> iterator = leavingRoads.iterator();
			while(iterator.hasNext()) {
				
			    Road leavingRoad = iterator.next();
			    if(true) {
					
					leavingRoad.getEnd().removeArrivingRoad(leavingRoad);
					//Removing Reference
					this.getAdjacentVillages().remove(leavingRoad.getEnd());
					//Remove References
					iterator.remove();
			    }
			}
			
			// Deal with country affiliation
			
			if (country != null){
				this.country.deaffiliateVillage(this);
				
			}

		}

		return;
	}
	public boolean isFull(){
		return !(getResidentCount() < populationThreshold);
	}
	
	public NodeGnome findGnome(String gnomeName){
		//Searches through village Binary Tree Index
		Format.T("Village.findGnome(): 01" + "|" + this);
		return null;
	}
	public NodeVillage findVillage(String villageName){
		Format.T("Village.findVillage(): 01" + "|" + this);
		//Go into Country
		//Find Villages ArrayList
		//FindVillage
		
		//else{ "Village not found / Village under different Country" );
		return null;
	}
	public void connectVillage(Road road, Village village){
		Format.T("Village.connectVillage():" + reference);
		leavingRoads.add(road);
		getAdjacentVillages().add(village);
		village.arrivingRoads.add(road);
		
		// Take Village
		// Add this to neighbors.
	}
	public synchronized void addResident(Gnome gnome){
		residents.add(gnome);
		setResidentCount(getResidentCount() + 1);
	}
	
	public synchronized void removeResident(Gnome gnome){
		residents.remove(gnome);
		setResidentCount(getResidentCount() -1);
	}
	
	public void removeArrivingRoad(Road road){
		this.arrivingRoads.remove(road);
	}
	
	public void removeLeavingRoad(Road road){
		this.leavingRoads.remove(road);
	}
	
	public void createGnome(){
		
		String name = Calc.calcName(6);
		Gnome newGnome = new Gnome(name,this);
		
		new Thread(newGnome).start();
	}
	
	public void createGnome(ArrayList<Village> dijkstraOrder){
		
		String name = Calc.calcName(6);
		Gnome newGnome = new Gnome(name,this);
		
		newGnome.recieveDijkstra(dijkstraOrder);
		
		new Thread(newGnome).start();
	}
	
	// [F] Getters
	public ArrayList<Road> getLeaving(){
		return this.leavingRoads;
	}
	public ArrayList<Gnome> getResidents(){
		//Returns residents (type: ArrayList<NodeGnome)
		return this.residents;
	}
	public ArrayList<Village> getNeighbors(){
		return this.getAdjacentVillages();
	}
	
	public ArrayList<Road> getArrivingRoads(){
		return this.arrivingRoads;
	}
	public ArrayList<Road> getLeavingRoads(){
		return this.leavingRoads;
	}
	
	public Country getCountry(){
		return this.country;
	}
	public double getMoney(){
		return this.money;
	}
	
	// [F] Setters
	public synchronized void affiliateToCountry(Country country){
		Format.T("Village.affiliateToCountry(): Set Country to " + country.toString() + reference);
		country.villages.add(this);
		Format.T("Village.affiliateToCountry(): Adding "+ this.name + " to this country's set.");
		this.country = country;
	}
	public void setPopulationThreshold(int newThreshold){
		Format.T("Village.getpopulationThreshold(): Set populationThreshold to " + 
				Format.clean(populationThreshold,3) + reference);
		this.populationThreshold = newThreshold;
	}
	public void deaffiliateCountry(){
		this.country = null;
	}
	
	// [C] Constructor
	public Village(String name){
		this.name = Calc.calcName(6);
		this.populationThreshold = Calc.calcRandom(50,75);
		this.reference = " [@] " + this.name;
		this.closed = false;
		this.money = 5000;
		
		Format.C("Village, " + this.name + " has been created!");
		Village.villageActive.add(this);
		villagesCreated ++;
	}
	public int getResidentCount() {
		return residentCount;
	}
	public void setResidentCount(int residentCount) {
		this.residentCount = residentCount;
	}
	public ArrayList<Village> getAdjacentVillages() {
		return adjacentVillages;
	}
	public void setAdjacentVillages(ArrayList<Village> adjacentVillages) {
		this.adjacentVillages = adjacentVillages;
	}
		
}
