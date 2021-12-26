package bingo;

import java.awt.Color;

public class ColorEncoder {

	public static Color byId(int id) {
		switch(id) {
		case 0: return Color.lightGray.brighter(); 
		case 1: return new Color(255,100,100);
		case 2: return new Color(100,100,255);
		case 3: return new Color(100,255,100);
		case 4: return new Color(200,200,0);
		case 5: return Color.pink;
		case 6: return Color.cyan;
		case 7: return Color.magenta;
		default:
			return Color.black;
		}
	}
	
	public static String nameById(int id) {
		switch(id) {
		case 0: return "Empty";  
		case 1: return "Red";
		case 2: return "Blue";
		case 3: return "Green";
		case 4: return "Yellow"; 
		case 5: return "Pink"; 
		case 6: return "Cyan";
		case 7: return "Magenta";
		default:
			return "black";
		}
	}
}
