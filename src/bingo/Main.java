package bingo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main(String[] args) {
		int wantedId = 0;
		if(args.length > 1) {
			wantedId = Integer.parseInt(args[1]);
		}
		new IntroGUI(wantedId);
	}

}
