package bingo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectingUI extends JPanel {
	public ConnectingUI(){
		this.setLayout(new GridLayout(1,1));
		Font font1 = new Font("SansSerif", Font.BOLD, 20);

		JTextField a = new JTextField("Connecting...");
		a.setFont(font1);
		a.setFocusable(false);
		a.setEditable(false);
		a.setBackground(new Color(0,0,0,0));
		a.setBorder(null);
		a.setHorizontalAlignment(JTextField.CENTER);
		
		this.add(a);
		this.setVisible(true);
	}

}
