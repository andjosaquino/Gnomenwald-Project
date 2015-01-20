package GUI.MapPanel;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import Crucial.Calc;
import Crucial.Constants;
import Crucial.Format;
import DataStructure.NodeVillage;

public class GrassTile extends Tile {
	
	public int id;
	public Color color;
	public boolean walkable;
	public Image isoImage;
	public int decalImage;
	
	private NodeVillage villageReference;
	
	public GrassTile(int decalImage){
		super(1,Constants.GREEN,true,TileTypes.getImage(Constants.LOC_GRASS),decalImage);
		
		Image tree = null;
		
		if (Calc.calcRandom(10) > 5){
			if (Calc.calcRandom(10) > 5){
				this.decalImage = 1;
			}
			else{
				this.decalImage = 2;
			}
		}
		else{
			this.decalImage = Integer.MAX_VALUE;
		}
		
	}
	
	public NodeVillage getVillageReference(){
		return this.villageReference;
	}

}
