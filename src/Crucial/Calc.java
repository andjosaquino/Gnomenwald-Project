package Crucial;
import java.util.ArrayList;


public class Calc{

	
	public static int calcRandom(int max){
		int random = (int) (Math.random() * max);
		return random;
	}
	
	public static int calcRandom(int min, int max){
		int random = (int) (Math.random() * (max - min));
		return min + random;
	}
	
	public static String calcName(int length){
		String[] alphabet  = ("A B C D E F G H I J K L M N "
				+ "O P Q R S T U V W X Y Z").split(" ");	
		String name = "";
		for (int i = 0; i < 6; i++){
			name += alphabet[Calc.calcRandom(alphabet.length)];
			
		}
		
		return name;
	}
	
	public static int pythagorean(int x, int y){
		double xDouble = (double)x;
		double yDouble = (double)y;
		return (int) Math.sqrt(Math.pow(xDouble,2) + Math.pow(yDouble,2));
	}
	
}
