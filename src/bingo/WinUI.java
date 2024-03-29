package bingo;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import bingo.state.ServerState;

public class WinUI extends JPanel {

	private ServerState st = null;
	
	public WinUI(int playerId, int winner, ServerState st){
		String txt = "";
		this.st = st;
		
		if(winner == 0) {
			txt = "TIE! No one wins :I";
		}else {
			if(playerId == winner) {
				txt = "YOU WON!! You absolute legend!";
			}else {
				txt = "YOU LOST!! \n" + ColorEncoder.nameById(winner) + " beat you!";
				
			}
		}
		
		this.setLayout(new GridLayout(1,1));
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		
		JTextField a = new JTextField(txt);		
		a.setHorizontalAlignment(JTextField.CENTER);
		a.setFont(font1);
		a.setFocusable(false);
		a.setEditable(false);
		a.setBorder(null);
		a.setBackground(ColorEncoder.byId(winner));
		
		this.add(a);
		this.setVisible(true);
		
	}
	
}
