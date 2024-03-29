package bingo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class UI extends JPanel {
	
	String[][] goals;
	BtText[][] buttons;
	int rows, cols;
	IOnClose b;
	
	public UI(String[][] goals) {
		this.rows = goals.length;
		this.cols = goals[0].length;
		
		this.setLayout(new GridLayout(rows, cols));
		
		buttons = new BtText[rows][cols];
		for(int x = 0; x < rows; ++x) {	
			for(int y = 0; y < cols; ++y) {
				buttons[x][y] = new BtText(goals[x][y]);				
				this.add(buttons[x][y], x,y);
			}
		}
		
		this.setVisible(true);
		
	}
	
	public void drawState(GameState r){
		for(int a = 0; a < rows; ++a) {
			for(int b = 0; b < cols; ++b) {
				float f = r.getDoubtsOn(a, b)/3.0f;
				int r1 = Math.max(0, Math.min(255, (int) (0*f + 180*(1-f))));
				int g1 = Math.max(0, Math.min(255, (int) (0*f + 180*(1-f))));
				int b1 = Math.max(0, Math.min(255, (int) (0*f + 180*(1-f))));
				
				Border border = new LineBorder(new Color(r1,g1,b1), 1+((int)(2*f)), false);
				buttons[a][b].setBorder(border);
				buttons[a][b].setBackground(ColorEncoder.byId(r.get(a, b)));
			}
		}
	}
	
	public void registerButtonHandler(IButtonHandler hdnl) {
		for(int a = 0; a < rows; ++a) {
			for(int b = 0; b < cols; ++b) {
				final int ac = a;
				final int bc = b;
				buttons[a][b].addMouseListener(new MouseListener() {
					
					@Override
					public void mousePressed(MouseEvent e) {
						if(e.getButton() == MouseEvent.BUTTON3) {
							hdnl.handleButton3(ac, bc, e);
						}
					}
					
					@Override public void mouseReleased(MouseEvent e) { }
					@Override public void mouseExited(MouseEvent e) { }
					@Override public void mouseEntered(MouseEvent e) {  }					
					@Override public void mouseClicked(MouseEvent e) { }
				});
				
				buttons[a][b].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						hdnl.handleButton(ac, bc, e);
					}
				});
			}
		}
	}
		
}
class BtText extends JButton {
	
	BtText(String s) {
		super("<html><center>"+s+"</center></html>");
		this.setFocusable(false);
	}
	
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

}
