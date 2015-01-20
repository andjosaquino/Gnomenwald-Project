package Environment;
import Crucial.Calc;
import Crucial.Constants;
import Entity.Gnome;
import Entity.Village;


public class Coordinator extends Thread {
	
	// [V] 
	public static final int VILLAGELIMIT = Constants.VILLAGELIMIT;
	public static final int GNOMELIMIT = Constants.GNOMELIMIT;
	public static final int COUNTRYLIMIT = Constants.COUNTRYLIMIT;
	
	
	
	// Helper Functions
	public Gnome gnomeGenerator(){
		
		String name = Calc.calcName(6);
		Village village = Village.villageActive.get(
				Calc.calcRandom(Village.villageActive.size())
				);
		
		Gnome newGnome = new Gnome(name,village);
		return newGnome;
	}
}
