package GUI.MapPanel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Crucial.Constants;

public class TileTypes {
	
	public static Tile GRASS_TILE = new Tile(1, Constants.GREEN,
			true, getImage(Constants.LOC_GRASS),0);
	public static Tile WATER_TILE = new Tile(2, Constants.BLUE,
			false, getImage(Constants.LOC_WATER),0);
	public static Tile VILLAGE_TILE = new Tile(3, Constants.ORANGE,
			false, getImage(Constants.LOC_VILLAGE),0);
	public static Tile SELECTED_TILE = new Tile(4, Constants.WHITE,
			false, getImage(Constants.LOC_SELECTED),0);
	
	public static Image getImage(File name){
		Image image = null;
		try {
			image = ImageIO.read(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
