package GUI;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI {
	
	public static void main(String[] Args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new MainFrame("Gnomenwald");
				frame.setUndecorated(false);
				frame.setSize(1280,720);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}