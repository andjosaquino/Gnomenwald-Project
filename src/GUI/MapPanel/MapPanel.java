package GUI.MapPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument.Iterator;

import Crucial.Calc;
import Crucial.Constants;
import Crucial.Format;
import DataStructure.NodeRoad;
import DataStructure.NodeVillage;
import Dijsktra.Dijkstra;
import Dijsktra.VertexNode;
import Entity.Country;
import Entity.Road;
import Entity.Village;
import GUI.DetailsPanel.SideBarButton;

public class MapPanel extends JPanel{
	
	public static final Tile[] tileTypes = {TileTypes.GRASS_TILE,
											TileTypes.WATER_TILE};
	
	public static final int ROWS = Constants.ROWS;
	public static final int COLUMNS = Constants.COLUMNS;
	public static final int TILE_PIXEL_SIZE = Constants.TILE_PIXEL_SIZE;
	
	public static int tileWidth;
	public static int tileHeight;
	
	private Tile[][] tileMap;
	
	public static int offsetX;
	
	
	// [V] Entity Constants
	public static ArrayList<NodeRoad> drawnRoads = new ArrayList<NodeRoad>();
	public static ArrayList<NodeVillage> drawnVillages = new ArrayList<NodeVillage>();
	
	public static int countryLevel;
	

	
	public MapPanel() {	
		
		// [B] PANEL SETTING
		setFont(new Font("Arial", Font.PLAIN, 14));
		this.tileMap = new Tile[ROWS][COLUMNS];
		
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				
				int tileIndex;
				
				
				/*Terrain Generator*/
				
				if (i == 0 && j>0){
					Tile left = tileMap[i][j-1];
					tileIndex = TerrainGen.chunkLand(left);
				}
				else if (j == 0 && i>0){
					Tile top = tileMap[i-1][j];
					tileIndex = TerrainGen.chunkLand(top);
				}
				else if (i>1 && j>1 && Calc.calcRandom(100) < 95){
					Tile diagLeft = tileMap[i-1][j-1];
					Tile left     = tileMap[i][j-1];
					Tile top      = tileMap[i-1][j];
					if (j < COLUMNS - 1){
						Tile diagRight = tileMap[i-1][j+1];
						tileIndex = TerrainGen.chunkLand(left, top, 
								diagLeft, diagRight);
					}
					else{
						tileIndex = TerrainGen.chunkLand(left,top,diagLeft);
					}

				}
				else{
					tileIndex = Calc.calcRandom(2);
				}
				
				
				
				
				if (tileIndex == 0){
					
					int probability = Calc.calcRandom(100);
					
					if (probability < 30){
						
						if (probability < 10){
							tileMap[i][j] = new GrassTile(2);
						}
						else if(probability >= 10 && probability < 20){
							tileMap[i][j] = new GrassTile(3);
						}
						else{
							tileMap[i][j] = new GrassTile(4);
						}
					}
					
					else{
						tileMap[i][j] = new GrassTile(1);					}
				}
				else{
					tileMap[i][j] = tileTypes[tileIndex];
				}
			}
		}
		
		if (Constants.CARTESIAN){
			int preferredWidth = COLUMNS * TILE_PIXEL_SIZE;
			int preferredHeight = ROWS * TILE_PIXEL_SIZE;
			
			setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		}
		else{
			int preferredWidth = Calc.pythagorean(COLUMNS,ROWS) * TILE_PIXEL_SIZE;
			int preferredHeight = preferredWidth/2;
			
			setPreferredSize(new Dimension(preferredWidth, preferredHeight));
			setBackground(Constants.BLUE);
		}
	}
		
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		/* CARTESIAN MAP CREATION! */
		if (Constants.CARTESIAN){
			
			this.tileWidth = TILE_PIXEL_SIZE;
			this.tileHeight = TILE_PIXEL_SIZE;
			
			
			for (int i = 0; i < ROWS; i++){
				for (int j = 0; j < COLUMNS; j++){
					int x = i*tileWidth;
					int y = j*tileHeight;
					g.setColor(tileMap[i][j].color);
					g.fillRect(x,y,tileHeight,tileHeight);
				}
			}
		}
		
		/* ISOMETRIC MAP CREATION! */
		else{
			
			this.tileWidth = TILE_PIXEL_SIZE;
			this.tileHeight = tileWidth/2;
			
			for (int i = 0; i < ROWS; i++){
				this.offsetX = Calc.pythagorean(Constants.ROWS,Constants.COLUMNS)/2 * tileWidth;
				for (int j = 0; j < COLUMNS; j++){

					int x = (int) (tileWidth * ((i - j)/2.0)) + offsetX;
					int y = (int) (tileHeight * ((i + j)/2.0));
					
					if(tileMap[i][j].id == 2){
						g.drawImage(tileMap[i][j].isoImage,x,y+20,null);
					}
					
					BufferedImage decal = null;
					
					if (tileMap[i][j].decalImage == 1){
						decal = convertToARGB(toBI(Constants.getImage(Constants.LOC_BASIC_1)));
					}
					else if (tileMap[i][j].decalImage > 1 && tileMap[i][j].decalImage < 5){
						if (tileMap[i][j].decalImage == 2){
							decal = convertToARGB(toBI(Constants.getImage(Constants.LOC_TREE_1)));
						}
						if (tileMap[i][j].decalImage == 3){
							decal = convertToARGB(toBI(Constants.getImage(Constants.LOC_TREE_2)));
						}
						if (tileMap[i][j].decalImage == 4){
							decal = convertToARGB(toBI(Constants.getImage(Constants.LOC_TREE_3)));
						}
					}
					else if (tileMap[i][j].decalImage >= 5){
						if(tileMap[i][j].decalImage == 5){
							decal = convertToARGB(toBI(Constants.VILLAGE_1));
						}
						
						else if (tileMap[i][j].decalImage == 6){
							decal = convertToARGB(toBI(Constants.VILLAGE_2));
						}
						else if(tileMap[i][j].decalImage == 7){
							decal = convertToARGB(toBI(Constants.VILLAGE_3));
						}
					}
					
					g.drawImage(decal,x,y-32,null);
					
					//DRAW VILLAGE STRING
					if (tileMap[i][j].id == 3){
						// Drawing String
						String villageName = tileMap[i][j].getVillageReference().getData().name;
						g.setColor(Constants.ORANGE);
						g.fillRect(x+16,y-53,78,24);	
						g.setColor(Constants.WHITE);
						g.drawString(villageName,x+28,y-36);		
						
						// Drawing Gnome Orbs
						int residentNumber = tileMap[i][j].getVillageReference().getData().getResidentCount();
						if(residentNumber > 0){
							
							for(int r = 0; r < residentNumber; r ++){
						    g.setColor(Constants.WHITE);
						    g.fillOval(x + 20 + (r%2)*15,y -20 + (r/2)*15,10,10);
							}
						}
					}
				}
			}
			
			/* DRAW ROADS */
			
			for (NodeRoad e: drawnRoads){
				((Graphics2D) g).setStroke ( new BasicStroke ( 1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ) );
				
				if (e.clash == 2){
					g.setColor(Constants.ORANGE);
					g.drawLine(0, 0, 600, 600);
					g.drawLine(e.startX+10, e.startY+10, e.endX-10, e.endY-10);
					
					((Graphics2D) g).fillOval(e.endX-15, e.endY-15, 10, 10);
				}
				else{
					g.setColor(Constants.ORANGE);
					g.drawLine(0, 0, 600, 600);
					g.drawLine(e.startX, e.startY, e.endX, e.endY);
					
					((Graphics2D) g).fillOval(e.endX-5, e.endY-5, 10, 10);
					
				}
			

			}

		}
		
	}
	public static BufferedImage toBI(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static BufferedImage convertToARGB(BufferedImage image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(), image.getHeight(),
	        BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}

	public void performDijkstraMap(int startX, int startY, int endX, int endY){
		
		int[] startArray = cartesianToIndex(startX,startY);
		int[] endArray = cartesianToIndex(endX,endY);
		
		int axIndex = startArray[0];
		int ayIndex = startArray[1];
		int bxIndex = endArray[0];
		int byIndex = endArray[1];
		
		int[] startCoordinates = indexToIsometric(axIndex, ayIndex);
		int[] endCoordinates = indexToIsometric(bxIndex, byIndex);
		
		int axCoord = startCoordinates[0];
		int ayCoord = startCoordinates[1];
		int bxCoord = endCoordinates[0];
		int byCoord = endCoordinates[1];
		
		if (tileMap[axIndex][ayIndex].id == 3 &&
				tileMap[bxIndex][byIndex].id == 3){
			
				NodeVillage source = tileMap[axIndex][ayIndex].getVillageReference();
				NodeVillage end = tileMap[bxIndex][byIndex].getVillageReference();
				
				Dijkstra simulator = new Dijkstra();
				
				simulator.loadAll(source.getData().getCountry());
				
				ArrayList<VertexNode> shortestPathSimulation = 
				simulator.getShortestPath(simulator.findVillage(source.getData()),
						simulator.findVillage(end.getData()));
				
				ArrayList<Village> shortestPath = simulator.convertToVillage(shortestPathSimulation);
				
				source.getData().createGnome(shortestPath);
				
				repaint();
			
		}
		else{
			
			Format.E("One of the nodes is not a village.");
			
		}
	}
	
	public void buildRoad(int startX, int startY, int endX, int endY){
		
		int[] startArray = cartesianToIndex(startX,startY);
		int[] endArray = cartesianToIndex(endX,endY);
		
		int axIndex = startArray[0];
		int ayIndex = startArray[1];
		int bxIndex = endArray[0];
		int byIndex = endArray[1];
		
		int[] startCoordinates = indexToIsometric(axIndex, ayIndex);
		int[] endCoordinates = indexToIsometric(bxIndex, byIndex);
		
		int axCoord = startCoordinates[0];
		int ayCoord = startCoordinates[1];
		int bxCoord = endCoordinates[0];
		int byCoord = endCoordinates[1];
		
		if (tileMap[axIndex][ayIndex].id == 3 &&
				tileMap[bxIndex][byIndex].id == 3){
			
				NodeVillage source = tileMap[axIndex][ayIndex].getVillageReference();
				NodeVillage end = tileMap[bxIndex][byIndex].getVillageReference();
				
				boolean one = false;
				boolean two = false;
				
				//Check if line clash
				for (NodeRoad r: drawnRoads){
					
					NodeVillage sourceOfR = r.source;
					NodeVillage endOfR = r.end;
					
					if (source == sourceOfR || source == endOfR){
						one = true;
					}
					
					if (end == sourceOfR || end == endOfR){
						two = true;
					}
				
				}
				
				NodeRoad created;
				
				if(one && two){
					created = new NodeRoad(axCoord,ayCoord,bxCoord,byCoord,source,end,2);
				}
				else{
					created = new NodeRoad(axCoord,ayCoord,bxCoord,byCoord,source,end,1);
				}
				
				drawnRoads.add(created);
				
				Format.T("MapPanel.buildRoad() | "
						+ "("+ Integer.toString(startArray[0]) + "," 
						+ Integer.toString(startArray[1]) + ")"
						+ " connected to ("
						+ Integer.toString(endArray[0]) + ","
						+ Integer.toString(endArray[1]) + ")");
				
				repaint();
			
		}
		else{
			
			Format.E("One of the nodes is not a village.");
			
		}
	}
	
	public void buildVillage(int xCoord, int yCoord, String name){
		
		int[] tileArray = cartesianToIndex(xCoord,yCoord);
		int xIndex = tileArray[0];
		int yIndex = tileArray[1];
		
		if (tileMap[xIndex][yIndex].walkable){
			
			VillageTile created = new VillageTile(xCoord,yCoord,name);
			tileMap[xIndex][yIndex] = created;
			
			drawnVillages.add(created.getVillageReference());
			repaint();
		}
		else{
			Format.E("You cannot build here!");
		}

	}
	
	public void removeVillage(int xCoord, int yCoord){
		
		int[] tileArray = cartesianToIndex(xCoord,yCoord);
		int xIndex = tileArray[0];
		int yIndex = tileArray[1];
		
		if (tileMap[xIndex][yIndex].id == 3){
			
			NodeVillage pendingDestruction = 
					tileMap[xIndex][yIndex].getVillageReference();
			GrassTile ruins = new GrassTile(1);
			tileMap[xIndex][yIndex] = ruins;
			
			

			java.util.Iterator<NodeRoad> iter = drawnRoads.iterator();
			while( iter.hasNext()) {
			    NodeRoad roadArt = iter.next();
			    if(roadArt.getData().getSource() 
						== pendingDestruction.getData()
						|| 
						roadArt.getData().getEnd() 
						== pendingDestruction.getData()) {
			        iter.remove();
			    }
			}

			pendingDestruction.getData().destroy(0);
			repaint();
		}
		else{
			Format.E("This is not a village to begin wtih.");
		}

	}
	
	public void dissolveVillage(int xCoord, int yCoord){
		
		int[] tileArray = cartesianToIndex(xCoord,yCoord);
		int xIndex = tileArray[0];
		int yIndex = tileArray[1];
		
		if (tileMap[xIndex][yIndex].id == 3){
			
			NodeVillage pendingDestruction = 
					tileMap[xIndex][yIndex].getVillageReference();
			
			ArrayList<Road> arrivingToPending= new ArrayList<Road>();
			for (Road arrivingRoad : pendingDestruction.getData().getArrivingRoads()){
				arrivingToPending.add(arrivingRoad);
			}
			
			ArrayList<Road> leavingForPending= new ArrayList<Road>();
			for(Road leavingRoad: pendingDestruction.getData().getLeavingRoads()){
				leavingForPending.add(leavingRoad);
			}
			
			removeVillage(xCoord,yCoord);
			
			ArrayList<Point> arrivingSources = new ArrayList<Point>();
			for (Road r : arrivingToPending){
				arrivingSources.add(new Point(r.getMapNode().startX,r.getMapNode().startY));
			}
			
			ArrayList<Point> leavingDestinations = new ArrayList<Point>();
			for (Road r : leavingForPending){
				leavingDestinations.add(new Point(r.getMapNode().endX,r.getMapNode().endY));
			}
			
			for (Point source : arrivingSources){
				for (Point destination: leavingDestinations){
					buildRoad(source.x,source.y,destination.x,destination.y);
				}
			}
		
		
		}

	}
	
	public void buildCountry(ArrayList<Point> potentialCoordinates){
		
		for ( Point location : potentialCoordinates){
			
			int xCoord = location.x;
			int yCoord = location.y;
			
			int[] tileLocation = cartesianToIndex(xCoord,yCoord);
			
			int rowPos = tileLocation[0];
			int colPos = tileLocation[1];
			
			if(tileMap[rowPos][colPos].id == 3){
				
				Tile pendingTerritoryTile = tileMap[rowPos][colPos];
				NodeVillage pendingTerritory = tileMap[rowPos][colPos].getVillageReference();
				
				System.out.println(pendingTerritory.getData() + "$$$$$$$$$$$$$$$");
				
				if (this.countryLevel == 0){
					pendingTerritory.getData().affiliateToCountry(Constants.COUNTRY_1);
					pendingTerritoryTile.decalImage = 6;

				}else{
					pendingTerritory.getData().affiliateToCountry(Constants.COUNTRY_2);
					pendingTerritoryTile.decalImage = 7;
				}
				
				repaint();
			}
		}
		countryLevel += 1;
	}
	
	
	public void buildPrimsCountry(ArrayList<Point> potentialCoordinates){
		
		for ( Point location : potentialCoordinates){
			
			int xCoord = location.x;
			int yCoord = location.y;
			
			int[] tileLocation = cartesianToIndex(xCoord,yCoord);
			
			int rowPos = tileLocation[0];
			int colPos = tileLocation[1];
			
			if(tileMap[rowPos][colPos].id == 3){
				
				Tile pendingTerritoryTile = tileMap[rowPos][colPos];
				NodeVillage pendingTerritory = tileMap[rowPos][colPos].getVillageReference();
				
				System.out.println(pendingTerritory.getData() + "$$$$$$$$$$$$$$$");

				pendingTerritory.getData().affiliateToCountry(Constants.COUNTRY_1);
				pendingTerritoryTile.decalImage = 6;
				
				repaint();
			}
		}
	}
	
	public void generateGnomeAtVillage(int xCoord, int yCoord, String gnomeName){
		
		int[] tileArray = cartesianToIndex(xCoord,yCoord);
		int rowPos = tileArray[0];
		int colPos = tileArray[1];
		
		if (tileMap[rowPos][colPos].id == 3){
			
			tileMap[rowPos][colPos].getVillageReference().getData().createGnome();

			repaint();
		}
		else{
			Format.E("Not a valid gnome breeding ground.");
		}
	}

	public int[] cartesianToIndex(int xCoord, int yCoord){
		
		int xIndex;
		int yIndex;
		
		int errorX = tileWidth / 2;
		int errorY = tileHeight / 2;
		
		
		if (Constants.CARTESIAN){
			xIndex = xCoord/TILE_PIXEL_SIZE;
			yIndex = yCoord/TILE_PIXEL_SIZE;
			
		}
		else{
			xIndex = (int) Math.round(((xCoord - offsetX - errorX) /((double)tileWidth) 
					+ (yCoord-errorY)/((double)tileHeight)));
			yIndex = (int) Math.round(((yCoord-errorY)/((double)tileHeight) 
					- (xCoord-offsetX-errorX)/((double)tileWidth)));
		}
		
		int[] indexArray = {xIndex,yIndex};
		
		return indexArray;
		
	}
	
	public int[] indexToIsometric(int xIndex, int yIndex){
		
		int errorX = tileWidth / 2;
		int errorY = tileHeight / 2;
		
		
		int x = (int) (tileWidth * ((xIndex - yIndex)/2.0)) + errorX + offsetX;
		int y = (int) (tileHeight * ((xIndex + yIndex)/2.0)) + errorY;

		int[] isometricArray = {x,y};
		
		return isometricArray;
		
	}
	
}
