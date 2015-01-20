package Entity;
import java.util.ArrayList;

import DataStructure.NodeVillage;
import DataStructure.NodeGnome;

public class Country {
	
	public static int countriesCreated = 0;
	
	public String name;
	public ArrayList<Village> villages = new ArrayList<Village>();
	
	public void deaffiliateVillage(Village village){
		village.deaffiliateCountry();
		villages.remove(village);
	}
	
	public Country(String name){
		this.name = name;
		countriesCreated ++;
	}
}
