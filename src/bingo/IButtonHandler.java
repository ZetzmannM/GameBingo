package bingo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public interface IButtonHandler {

	void handleButton(int a, int b, ActionEvent e);
	
	void handleButton3(int a, int b, MouseEvent e);
}
