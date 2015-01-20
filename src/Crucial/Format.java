package Crucial;



public class Format {
	/***************************************************************************
	 * Text Formatter for console, in order to read clearly, and debug faster.
	 ***************************************************************************
	 */
	
	public static final String ERROR = "[!]";
	public static final String CONSOLE = "[c]";
	public static final String GUI = "[c]";
	public static final String TEST = "[t]";
	
	public static void T(String query){
		
		if (Constants.testMode){
			System.out.println(TEST +  " " + query);
		}
	}
	public static void C(String query){
		System.out.println(CONSOLE +  " " + query);
	}
	public static void E(String query){
		System.out.println(ERROR +  " " + query);
	}
	public static void G(String query){
			System.out.println(GUI +  " " + query);
	}
	
	
	public static String clean(double naked, int places){
		//Initializing formatted
		String formatted = "";
		String nakedString = Double.toString(naked);
		
		if (nakedString.length() < places){
			for ( int i = 0; i < (places - nakedString.length()); i++ ){
				formatted += "0";
			}
			
			formatted += nakedString;
			return formatted;
		}
		else if (nakedString.length() == places){
			return nakedString;
		}
		else{
			return nakedString.substring(0,places);
		}
	}
	public static String clean(int naked, int places){
		//Initializing formatted
		String formatted = "";
		String nakedString = Integer.toString(naked);
		
		if (nakedString.length() < places){
			for ( int i = 0; i < (places - nakedString.length()); i++ ){
				formatted += "0";
			}
			
			formatted += nakedString;
			return formatted;
		}
		else if (nakedString.length() == places){
			return nakedString;
		}
		else{
			return nakedString.substring(0,places);
		}
	}
	public static String clean(String naked, int places){
		//Initializing formatted
		String temp = naked;
		String buffer ="";
		boolean odd = false;
		
		if (naked.length() % 2 != 0){
			odd = true;
		}
		
		
		if (naked.length() < places){
			
			for ( int i = 0; i < Math.round((places - naked.length())/2.0); i++ ){
				buffer += " ";
			}
			
			if (odd) {
				return buffer + temp + buffer.substring(0,buffer.length()-1);
			}
			else{
				return buffer + temp + buffer;
			}
			
		}
		else if (naked.length() == places){
			return naked;
		}
		
		else{
			return naked.substring(0,places);
		}
	}
}
