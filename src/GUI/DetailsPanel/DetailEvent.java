package GUI.DetailsPanel;


import java.util.EventObject;


public class DetailEvent extends EventObject {
	
	private static final long serialVersionUID = 4323566332867628183L;
	private int mode;
	private String query;
	
	public DetailEvent(Object source, int mode, String query){
		super(source);
		this.query = query;
		this.mode = mode;
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public String getQuery(){
		return this.query;
	}
}
