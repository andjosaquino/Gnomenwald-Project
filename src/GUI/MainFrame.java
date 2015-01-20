package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import Crucial.Constants;
import Crucial.Format;
import GUI.DetailsPanel.DetailEvent;
import GUI.DetailsPanel.DetailListener;
import GUI.DetailsPanel.DetailsPanel;
import GUI.DetailsPanel.SideBarButton;
import GUI.MapPanel.MapPanel;
import GUI.QueryPanel.QueryPanel;


public class MainFrame extends JFrame{
	
	private static DetailsPanel detailsPanel;
	private static MapPanel mapPanel;
	private static JScrollPane mapPanelView;
	private Container contentPane;
	private QueryPanel queryPanel;
	
	private Timer timer;
	
	public static int interactiveMode = Integer.MAX_VALUE;
	
	public MainFrame(String title){
		super(title);
		
		// Set Layout Manager
		setLayout(new BorderLayout());
		
		/* Create Swing Components 
		 * 		[1] Map Panel
		 * 		[2] DetailsPanel
		 * 		[3] Query Panel UNIMPLEMENTED*/
		
		
		// [1] Map Panel: Stores tileMap
		this.mapPanel = new MapPanel();
		
		// [2] Details Panel: Stores Sidebar of commands.
		detailsPanel = new DetailsPanel();
		
			// Retrieve commands from DetailsPanel Class
		detailsPanel.addDetailListener(new DetailListener() {
			public void detailEventOccured(DetailEvent event){
				interactiveMode = event.getMode();
				String query = event.getQuery();
				
				if      (interactiveMode == Constants.ROAD_CREATION){
					placeRoad();
				}
				else if (interactiveMode == Constants.VILLAGE_CREATION){
					placeVillage(query);
				}
				else if (interactiveMode == Constants.COUNTRY_CREATION){
					placeCountry();
				}
				else if (interactiveMode == Constants.GNOME_CREATION){
					placeGnome(query);
				}
				else if (interactiveMode == Constants.VILLAGE_REMOVAL){
					removeVillage(query);
				}
				else if (interactiveMode == Constants.VILLAGE_DISSOLUTION){
					dissolveVillage(query);
				}
				else if (interactiveMode == 6){
					performDijkstra();
				}
				else if (interactiveMode == 7){
					primsCountry();
				}
			}
		});
		
		
		/* Adding all Swing Components */
		this.contentPane = getContentPane();
		
		/* Nesting Map into JScrollPane*/
		
		nestMap();
		
		contentPane.add(mapPanelView, BorderLayout.CENTER);
		contentPane.add(detailsPanel, BorderLayout.EAST);
		// Add Behavior
		
		////////////////////////////////////////////////////////
		ActionListener repaintMap = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        mapPanel.repaint();
		    }
		};
		
		timer = new Timer(3000, repaintMap);
		timer.start();
		///////////////////////////////////////////////////////

	}
	
	public void nestMap(){
		
		// Make Scroll Pane
		JScrollPane map = new JScrollPane(mapPanel);
		// Set Dimension
		map.setSize(773, 540);
		
		// Align to middle
		Rectangle bounds = map.getViewport().getViewRect();
		Dimension size = map.getViewport().getViewSize();
		
		int x = (size.width - bounds.width) / 2 - 500;
		int y = (size.height - bounds.height) / 2 - 500;
		
		map.getViewport().setViewPosition(new Point(x, y));
		
		// Set to static var;
		this.mapPanelView = map;
		
		
	}
	
	public void placeVillage(String query){

		final String villageName = query;
		
		mapPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
				
				if (interactiveMode == Constants.VILLAGE_CREATION){
					mapPanel.buildVillage(xCoord, yCoord, villageName);
					detailsPanel.createVillageButton.resetButton();
					detailsPanel.createVillageButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
			
			public void mouseExited(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
		});

	}
	
	public void performDijkstra(){
		
		mapPanel.addMouseListener(new MouseAdapter(){
			
			public int startX;
			public int startY;
			
			@Override
			public void mousePressed(MouseEvent e){
				startX = e.getX();
				startY = e.getY();
			}
			
			public void mouseReleased(MouseEvent e){
				
				int endX = e.getX();
				int endY = e.getY();
				
				if (interactiveMode == 6){
					mapPanel.performDijkstraMap(startX, startY, endX, endY);
					detailsPanel.dijkstraButton.resetButton();
					detailsPanel.dijkstraButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
		});
	}
	
	public void dissolveVillage(String query){

		final String villageName = query;
		
		mapPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
				
				if (interactiveMode == Constants.VILLAGE_DISSOLUTION){
					
					mapPanel.dissolveVillage(xCoord, yCoord);

					detailsPanel.dissolveVillageButton.resetButton();
					detailsPanel.dissolveVillageButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
			
			public void mouseExited(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
		});

	}
	
	public void removeVillage(String query){

		final String villageName = query;
		
		mapPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
				
				if (interactiveMode == Constants.VILLAGE_REMOVAL){
					mapPanel.removeVillage(xCoord, yCoord);
					
					Format.C("MainFrame.Removing Village:");
					
					detailsPanel.removeVillageButton.resetButton();
					detailsPanel.removeVillageButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
			
			public void mouseExited(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
		});

	}
	
	public void placeGnome(String query){

		final String gnomeName = query;
		
		mapPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
				
				if (interactiveMode == Constants.GNOME_CREATION){
					
					Format.T("I GET HERE!");
					
					mapPanel.generateGnomeAtVillage(xCoord, yCoord, gnomeName);
					detailsPanel.createGnomeButton.resetButton();
					detailsPanel.createGnomeButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
			
			public void mouseExited(MouseEvent e){
				int xCoord = e.getX();
				int yCoord = e.getY();
			}
		});

	}
	
	public void placeRoad(){
		

		mapPanel.addMouseListener(new MouseAdapter(){
			
			public int startX;
			public int startY;
			
			@Override
			public void mousePressed(MouseEvent e){
				startX = e.getX();
				startY = e.getY();
			}
			
			public void mouseReleased(MouseEvent e){
				
				int endX = e.getX();
				int endY = e.getY();
				
				if (interactiveMode == Constants.ROAD_CREATION){
					mapPanel.buildRoad(startX, startY, endX, endY);
					detailsPanel.createRoadButton.resetButton();
					detailsPanel.createRoadButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
				}
			}
		});

	}
	
	public void placeCountry(){
		
		
		mapPanel.addMouseListener(new MouseAdapter(){
			
			public int villageLimit = 10;
			public int selectedCount = 0;
			
			public ArrayList<Point> potentialCoordinates = new ArrayList<Point>();
			
			public Point pendingCoordinates;
			public Point lastCoordinates;
			
			public boolean go = true;
			public boolean finished;
			
			
			@Override
			public void mousePressed(MouseEvent e){
				
				while(selectedCount < villageLimit && go){
					
					int pendingX = e.getX();
					int pendingY = e.getY();
					
					this.pendingCoordinates = new Point(pendingX,pendingY);
					
					Format.T("placeCountry: mousePressed" + pendingCoordinates.toString());
					
					go = false;
					
				}
			
				
			}
			
			public void mouseReleased(MouseEvent e){
				
				while(selectedCount < villageLimit && !go){
					
					int releaseX = e.getX();
					int releaseY = e.getY();
					
					Point releaseCoordinates = new Point(releaseX,releaseY);
					System.out.println(releaseCoordinates);
					
					if(this.pendingCoordinates.equals(releaseCoordinates)){
						
						potentialCoordinates.add(releaseCoordinates);
						lastCoordinates = releaseCoordinates;
						
						Format.T("placeCountry: mouseReleased" + releaseCoordinates.toString());
						
						selectedCount += 1;
					}
					
					go = true;
				}
				
				if (interactiveMode == Constants.COUNTRY_CREATION
						&& selectedCount == villageLimit){
					
					mapPanel.buildCountry(potentialCoordinates);
					detailsPanel.createCountryButton.resetButton();
					detailsPanel.createCountryButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
					go = true;
					selectedCount = 0;
				}

			}
		});
	}
	
	
	public void primsCountry(){
		
		
		mapPanel.addMouseListener(new MouseAdapter(){
			
			public int villageLimit = 10;
			public int selectedCount = 0;
			
			public ArrayList<Point> potentialCoordinates = new ArrayList<Point>();
			
			public Point pendingCoordinates;
			public Point lastCoordinates;
			
			public boolean go = true;
			public boolean finished;
			
			
			@Override
			public void mousePressed(MouseEvent e){
				
				while(selectedCount < villageLimit && go){
					
					int pendingX = e.getX();
					int pendingY = e.getY();
					
					this.pendingCoordinates = new Point(pendingX,pendingY);
					
					Format.T("placeCountry: mousePressed" + pendingCoordinates.toString());
					
					go = false;
				}
			
				
			}
			
			public void mouseReleased(MouseEvent e){
				
				while(selectedCount < villageLimit && !go){
					
					int releaseX = e.getX();
					int releaseY = e.getY();
					
					Point releaseCoordinates = new Point(releaseX,releaseY);
					System.out.println(releaseCoordinates);
					
					if(this.pendingCoordinates.equals(releaseCoordinates)){
						
						potentialCoordinates.add(releaseCoordinates);
						lastCoordinates = releaseCoordinates;
						
						Format.T("placeCountry: mouseReleased" + releaseCoordinates.toString());
						
						selectedCount += 1;
					}
					
					go = true;
				}
				
				if (interactiveMode == 7
						&& selectedCount == villageLimit){
					
					mapPanel.buildPrimsCountry(potentialCoordinates);
					detailsPanel.createCountryButton.resetButton();
					detailsPanel.createCountryButton.repaint();
					interactiveMode = Integer.MAX_VALUE;
					go = true;
					selectedCount = 0;
				}

			}
		});
	}
}

