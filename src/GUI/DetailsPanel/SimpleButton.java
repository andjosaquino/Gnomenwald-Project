package GUI.DetailsPanel;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.EventListenerList;

import Crucial.Constants;
import Crucial.Format;

import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SimpleButton extends JLayeredPane implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private String title = null;
	private String query;
	private EventListenerList listerList = new EventListenerList();
	private boolean hit = false;
	private boolean hover = false;
	
	public SimpleButton (String title){
		super();
		this.title = title;
		addMouseListener(this);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(227,50);
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2D = (Graphics2D)g;
		
		/* AntiAlias */
		
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g);
		
		 //[B] Instantiating Image

		Image notClicked = null; 
		Image hovered = null; 
		Image clicked = null;
		
		try {
			notClicked = ImageIO.read(Constants.LOC_NOT_CLICKED);
			clicked = ImageIO.read(Constants.LOC_CLICKED);
			hovered = ImageIO.read(Constants.LOC_HOVERED);
		} catch (IOException e) {
			Format.T("LOST"); e.printStackTrace();
		}
		
		if (notClicked == null){
			Format.T("Null");
		}
		
		if (hit){
			g2D.setColor(Constants.ORANGE);
			g2D.fillRect(0,0,227,227);
			g2D.setColor(Constants.WHITE);
			g2D.drawString(title,getWidth()/2 - 28,(getHeight()-177)/2 + 5);
			g2D.drawImage(notClicked,getWidth()/2-60,(getHeight()-177)/2-5,null);
		}
		else if(hover){
			g2D.setColor(Constants.ORANGE);
			g2D.fillRect(0,0,227,50);
			g2D.setColor(Constants.WHITE);
			g2D.drawString(title,getWidth()/2 - 28,getHeight()/2 + 5);
			g2D.drawImage(hovered,getWidth()/2-60,getHeight()/2-5,null);
		}
		else{
			setSize(227,50);
			g2D.setColor(Constants.ORANGE);
			g2D.fillRect(0,0,227,50);
			g2D.setColor(Constants.WHITE);
			g2D.drawString(title,getWidth()/2 - 28,getHeight()/2 + 5);
			g2D.drawImage(clicked,getWidth()/2-60,getHeight()/2-5,null);
		};

	}
	public void resetButton(){
		hit = false;
	}
	
	public void mousePressed(MouseEvent e){
		hit = true;
		repaint();
	}
	
	public void mouseReleased(MouseEvent e){
		repaint();
	}
	
	public void mouseClicked(MouseEvent e){
		int x = getX();
		int y = getY();
	}
	
	public void mouseEntered(MouseEvent e){
		hover = true;
		repaint();
	}
	public void mouseExited(MouseEvent e){
		hover = false;
		repaint();
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
	
	public void initImages(){
		return;
	}
	

}//end class
