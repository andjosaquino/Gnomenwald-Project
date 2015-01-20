package GUI.DetailsPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.Timer;

import Crucial.Constants;
import Crucial.Format;
import GUI.MainFrame;

public class DetailsPanel extends JPanel{
	
	private static final long serialsVersionUID = 6915622549267792262L;
	
	private EventListenerList listerList = new EventListenerList();
	
	//SideBar Buttons
	public SideBarButton createVillageButton = new SideBarButton("Create Village");
	public SideBarButton removeVillageButton = new SideBarButton("Remove Village");
	public SideBarButton dissolveVillageButton = new SideBarButton("Dissolve Village");
	public SideBarButton createRoadButton = new SideBarButton("Create Road");
	public SideBarButton createCountryButton = new SideBarButton("Create Country");
	public SideBarButton primsCountryButton = new SideBarButton("Prims Country");
	public SideBarButton createGnomeButton = new SideBarButton("Create Gnome");
	public SideBarButton dijkstraButton = new SideBarButton("Dijkstra Gnome");
	public final JTextField searchField = new JTextField(15);
	
	
	public DetailsPanel() {
		
		initPanel();
		
		// [B] CREATING SEARCH FIELD
		initSearchField();
		initCreateRoad();
		initCreateVillage();
		initCreateCountry();
		initCreateGnome();
		initRemoveVillage();
		initDissolvingVillage();
		initDijkstra();
		initPrimsCountry();
		
		// Add to panel
		
		add(searchField);
		add(createRoadButton);
		add(createVillageButton);
		add(removeVillageButton);
		add(dissolveVillageButton);
		add(createCountryButton);
		add(createGnomeButton);
		add(dijkstraButton);
		add(primsCountryButton);
		
	}
	public void initCreateRoad(){
		createRoadButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Road Creation");
				
				fireDetailEvent(new DetailEvent(this, 0, "blah"));
			}
		});
		createRoadButton.setFont(new Font("Arial", Font.PLAIN, 14));
	}
	public void initCreateVillage(){
		createVillageButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent event){
				Format.G("Village Creation");
				
				String query = event.getQuery();
				
				fireDetailEvent(new DetailEvent(this, 1, query));
			}
		});
		createVillageButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initCreateCountry(){
		createCountryButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Country Creation");
				
				fireDetailEvent(new DetailEvent(this, 2, "blah"));
			}
		});
		createCountryButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initCreateGnome(){
		createGnomeButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Gnome Creation");
				
				fireDetailEvent(new DetailEvent(this, 3, "blah"));
			}
		});
		createGnomeButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initRemoveVillage(){
		removeVillageButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Removing Village");
				
				fireDetailEvent(new DetailEvent(this, 4, "blah"));
			}
		});
		removeVillageButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initDissolvingVillage(){
		dissolveVillageButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Dissolve Village");
				
				fireDetailEvent(new DetailEvent(this, 5, "blah"));
			}
		});
		dissolveVillageButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initDijkstra(){
		dijkstraButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Dijkstra");
				
				fireDetailEvent(new DetailEvent(this, 6, "blah"));
			}
		});
		dijkstraButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initPrimsCountry(){
		primsCountryButton.addDetailListener(new DetailListener(){
			public void detailEventOccured(DetailEvent e){
				Format.G("Prims");
				
				fireDetailEvent(new DetailEvent(this, 7, "blah"));
			}
		});
		primsCountryButton.setFont(new Font("Arial", Font.PLAIN, 14)); 
		
	}
	public void initPanel(){
		// [B] PANEL SETTING
		Dimension size = getPreferredSize();
		size.width = 227;
		setPreferredSize(size);
		setBackground(new Color(0x18181b));
		setBorder(new EmptyBorder(10,0,0,0));
	}
	public void initSearchField(){
		searchField.setBorder(null);
		searchField.setBackground(Constants.GREY);
		searchField.setPreferredSize(new Dimension(2270,50));
		searchField.setText("SEARCH");
		searchField.setFont(new Font("Arial", Font.PLAIN, 14));
		searchField.setForeground(Constants.LIGHTGREY);
		searchField.setBorder(BorderFactory.createCompoundBorder(
		        searchField.getBorder(), 
		        BorderFactory.createEmptyBorder(0, 20, 0, 20)));
		
	}
	
	public void fireDetailEvent(DetailEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i =0; i < listeners.length; i += 2){
			if (listeners[i] == DetailListener.class) {
				((DetailListener)listeners[i+1]).detailEventOccured(event);
			}
		}
	}
	
	public void addDetailListener(DetailListener listener){
		listenerList.add(DetailListener.class, listener);
	}
	
	public void removeDetailListener(DetailListener listener){
		listenerList.remove(DetailListener.class, listener);
	}
}

