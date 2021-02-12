package bingo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectingUI extends JPanel {
	
	JTextField content;
	String basic ="Connecting...";
	
	public ConnectingUI(){
		this.setLayout(new GridLayout(1,1));
		Font font1 = new Font("SansSerif", Font.BOLD, 20);

		content = new JTextField("Connecting...");
		content.setFont(font1);
		content.setFocusable(false);
		content.setEditable(false);
		content.setOpaque(false);
		content.setBorder(null);
		content.setHorizontalAlignment(JTextField.CENTER);
		
		this.add(content);
		this.setVisible(true);
	}
	
	
	public void setText(String s) {
		content.setText(basic + "\n" + s);
		this.repaint();
	}

	
}
