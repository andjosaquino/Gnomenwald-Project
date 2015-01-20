package GUI.QueryPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

import Crucial.Constants;

public class QueryPanel extends JPanel {
	public QueryPanel() {
		
		// [B] PANEL SETTING
		Dimension size = getPreferredSize();
		setBackground(Constants.DARKGREY);
		setBorder(new EmptyBorder(10,0,0,0));
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(227,50);
	}
}
