package Crucial;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Entity.Country;


public class Constants{
	
	public static boolean testMode = true;
	
	// [V] Environment Constants
	public static final int GNOMELIMIT = 200;
	public static final int VILLAGELIMIT = 6;
	public static final int ROADLIMIT = 8;
	public static final int COUNTRYLIMIT = 2;
	
	// MAP CONSTANTS
	public static final boolean CARTESIAN = false;	
	public static final boolean DECAL = true;
	public static final int ROWS = 20;
	public static final int COLUMNS = 20;
	public static final int TILE_PIXEL_SIZE = 128;
	
	
	// COLOR CONSTANTS
	public static final Color DARKGREY = new Color(0x18181b);
	public static final Color ORANGE = new Color(0xf3643c);
	public static final Color GREY = new Color(0x292929);
	public static final Color LIGHTGREY = new Color(0x888894);
	public static final Color WHITE = new Color(0xffffff);
	public static final Color GREEN = new Color(0x7ba340);
	public static final Color BLUE = new Color(0x4086a3);
	
	// MENU STATES
	public static final int ROAD_CREATION = 0;
	public static final int VILLAGE_CREATION = 1;
	public static final int COUNTRY_CREATION = 2;
	public static final int GNOME_CREATION = 3;
	public static final int VILLAGE_REMOVAL = 4;
	public static final int VILLAGE_DISSOLUTION = 5;
	
	public static File LOC_HOVERED = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/hovered.png");
	public static File LOC_CLICKED = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/clicked.png");
	public static File LOC_NOT_CLICKED = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/notClicked.png");
	public static File LOC_GRASS = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/grassTile128.png");
	public static File LOC_WATER = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/waterTile128.png");
	public static File LOC_VILLAGE = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/villageTile128.png");
	public static File LOC_SELECTED = new File("C:/Users/Andrew/workspace/Gnomenwald3/image/selectTile128.png");
	public static File LOC_TREE_1 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/treeDecal20.png");
	public static File LOC_TREE_2 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/treeDecal20_2.png");
	public static File LOC_TREE_3 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/treeDecal20_3.png");
	public static File LOC_BASIC_1 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/basicDecal20.png");
	public static File LOC_VILLAGE_1 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/villageDecal20.png");
	public static File LOC_VILLAGE_2 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/villageDecal20_1.png");
	public static File LOC_VILLAGE_3 = new File ("C:/Users/Andrew/workspace/Gnomenwald3/image/villageDecal20_2.png");
	
	public static Image TREE_1 = getImage(LOC_TREE_1);
	public static Image TREE_2 = getImage(LOC_TREE_2);
	public static Image TREE_3 = getImage(LOC_TREE_3);
	public static Image BASE_1 = getImage(LOC_BASIC_1);
	public static Image VILLAGE_1 = getImage(LOC_VILLAGE_1);
	public static Image VILLAGE_2 = getImage(LOC_VILLAGE_2);
	public static Image VILLAGE_3 = getImage(LOC_VILLAGE_3);
	
	public static Country COUNTRY_1 = new Country("Artemis");
	public static Country COUNTRY_2 = new Country("Haphaestus");
	
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
