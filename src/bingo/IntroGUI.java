package bingo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import bingo.state.ClientState;
import bingo.state.ServerState;

public class IntroGUI extends JFrame {
	
	ArrayList<String> splitToList(String s){
		String[] arr = s.split("\n");
		ArrayList<String> res = new ArrayList<String>();
		for(String se : arr) {
			if(!se.trim().isEmpty()) {
				res.add(se);
			}
		}
		return res;
	}
	
	public IntroGUI() {
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 470);
		
		this.setTitle("GameRaceBingo");
		
		JTextArea text = new JTextArea("Enter goals separated by \\n, or json (text must then begin with '[' (like the json array) ");
		text.setEditable(false);
		text.setOpaque(false);
		text.setFocusable(false);
		text.setLineWrap(true);
		text.setBounds(5, 5, 470, 25);
		text.setBorder(null);
		text.setHighlighter(null);

		final JTextArea input = new JTextArea("");
		input.setEditable(true);
		input.setBackground(new Color(255,255,255));
		input.setFocusable(true);
		input.setLineWrap(true);
		input.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JScrollPane pane = new JScrollPane(input);
		pane.setBounds(5, 35, 475, 230);

		final JButton start = new JButton("start");
		start.setBounds(5, 270, 475, 35);
		
		final JTextField ip = new JTextField("192.168.178.100");
		JTextField port = new JTextField("3132");
		ip.setBounds(5, 310, 235, 25);
		port.setBounds(245, 310, 236, 25);
		
		final JCheckBox isServer = new JCheckBox("Run as server");
		isServer.setBounds(5, 335, 475, 25);
		isServer.setEnabled(true);
		
		final JCheckBox impScenarios = new JCheckBox("Detect wins/ties");
		impScenarios.setBounds(5, 360, 200, 15);
		impScenarios.setSelected(true);
		
		final JCheckBox voting = new JCheckBox("Enable democrating voting for resetting tiles");
		voting.setBounds(205, 360, 275, 15);
		voting.setSelected(true);

		
		port.setBorder(BorderFactory.createLineBorder(Color.blue));
		ip.setBorder(BorderFactory.createLineBorder(Color.blue));
		input.setBorder(BorderFactory.createLineBorder(Color.blue));

		SpinnerModel model =
		        new SpinnerNumberModel(0, //initial value
		                               0, //min
		                               5, //max
		                               1);             
		JSpinner spin = new JSpinner(model);
		spin.setBounds(5, 400, 475, 25);
		
		JTextField field = new JTextField("Wanted Player/Team id (0 for automatic assignment)");
		field.setBounds(5, 375, 475, 25);
		field.setOpaque(true);
		field.setBorder(null);
		field.setFocusable(false);
		field.setEditable(false);
		field.setVisible(true);
		
		this.add(spin);
		this.add(field);
		this.add(impScenarios);
		this.add(voting);
		this.add(isServer);
		this.add(ip);
		this.add(port);
		this.add(start);
		this.add(pane);
		this.add(text);
		
		isServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ip.setEnabled(!isServer.isSelected());
			}
		});
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				port.setBorder(BorderFactory.createLineBorder(Color.blue));
				ip.setBorder(BorderFactory.createLineBorder(Color.blue));
				input.setBorder(BorderFactory.createLineBorder(Color.blue));

				int prt = -1;
				try {
					prt = Integer.parseInt(port.getText());
				} catch(Exception ffffe) {
					port.setBorder(BorderFactory.createLineBorder(Color.red));
					return;
				}

				if(isServer.isSelected()) {
					if(input.getText().trim().isEmpty()) {
						input.setBorder(BorderFactory.createLineBorder(Color.red));
						return;
					}
					
					String in = input.getText();
					String[][] args;
					
					if(in.startsWith("[")) {
						try {
							args = JSONInterface.fromJson(in);
						}catch(Exception fefe) {
							input.setBorder(BorderFactory.createLineBorder(Color.red));
							return;

						}
					}else {						
						args = JSONInterface.fromStringList(splitToList(in));

					}
					
					if(args.length <= 1 && args[0].length <= 1) {
						input.setBorder(BorderFactory.createLineBorder(Color.red));
						return;
					}

					setup(true, "", prt, args, 1, impScenarios.isSelected(), voting.isSelected());
				}else {
					if(ip.getText().split("\\.").length != 4) {
						ip.setBorder(BorderFactory.createLineBorder(Color.red));
						return;
					}
					
					setup(false, ip.getText(), prt, new String[0][0], (int)spin.getValue(), false, false);
				}
			}
		});

		this.setVisible(true);
	}
	
	public void addOnClose(final IOnClose a) {
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	a.onClose();
            	System.exit(0);
            }
        });

	}
	
	private void setup(boolean server, String ip, int port, String[][] gols, int wantedId, boolean detect, boolean voting) {
		this.setVisible(false);
		
		if(server) {						
			new ServerState(gols, port, this, detect, voting);
			
		}else {
			new ClientState(ip, port, this, wantedId);
		}
	}
	
	
}
