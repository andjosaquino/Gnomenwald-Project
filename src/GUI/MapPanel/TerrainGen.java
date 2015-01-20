package GUI.MapPanel;

import Crucial.Calc;
import Crucial.Format;

public class TerrainGen {
	/*Based off of Schocastic Poetry Homework
	 *Used a probability matrix*/
	public static int chunkLand(Tile left){
		
		int probability = Calc.calcRandom(100);
		int landFactor = 0;
		
		if (left.id == 0){
			landFactor += 1;
		}
		else{
			landFactor -= 1.5;
		}
		
		if(probability < 50 + landFactor*23 ){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public static int chunkLand(Tile left, Tile top, Tile diagLeft){
		
		int probability = Calc.calcRandom(100);
		Tile[] tileArray = {left,top,diagLeft};
		int landFactor = 0;
		int waterFactor = 0;
		int waterMultiple = 4;
		
		for (Tile adjacent :tileArray){
			if (adjacent.id == 1){
				landFactor += 1;
			}
			if (adjacent.id == 2){
				waterFactor += 1;
			}
		}
		
		if(top.id == 2){
			waterMultiple = 5;
		}
		
		int landAdvantage = (int) (3*Math.pow(landFactor,2));
		int waterAdvantage = (int) (waterMultiple*Math.pow(waterFactor,2));
		int probabilityCompare =  landAdvantage - waterAdvantage;
		
		
		if(probability < 50 + landAdvantage - waterAdvantage){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public static int chunkLand(Tile left, Tile top, Tile diagLeft, Tile diagRight){
		
		int probability = Calc.calcRandom(100);
		Tile[] tileArray = {left,top,diagLeft,diagRight};
		int landFactor = 0;
		int waterFactor = 0;
		int waterMultiple = 3;
		
		for (Tile adjacent :tileArray){
			if (adjacent.id == 1){
				landFactor += 1;
			}
			if (adjacent.id == 2){
				waterFactor += 1;
			}
		}
		if (diagRight.id == 2){
			waterMultiple += 1;
		}
		
		if (left.id == 2){
			waterMultiple += 1;
		}
		
		if (diagRight.id == 2){
			waterMultiple += 1;
		}
		
		
		
		int landAdvantage = (int) (5*Math.pow(landFactor,2));
		int waterAdvantage = (int) (waterMultiple*Math.pow(waterFactor,2));
		int probabilityCompare =  landAdvantage - waterAdvantage;
		
		if(probability < 50 + landAdvantage - waterAdvantage){
			return 0;
		}
		else{
			return 1;
		}
	}
}
