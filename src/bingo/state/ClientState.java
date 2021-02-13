package bingo.state;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import bingo.ConnectingUI;
import bingo.GameState;
import bingo.IButtonHandler;
import bingo.IConnectLost;
import bingo.IOnClose;
import bingo.IntroGUI;
import bingo.SocketTypeConst;
import bingo.UI;
import bingo.action.ClickUpdate;
import bingo.action.GameStateUpdate;
import bingo.action.InitGame;
import bingo.action.InitGameResp;
import bingo.action.WinUpdate;
import bingo.net.Connection;
import bingo.net.SocketTypeTable;
import bingo.net.client.Client;

public class ClientState {
	String ip;
	int port;
	
	public volatile int playerId = 0;
	
	public volatile IntroGUI intUi;
	Client clnt;
	
	public UI ui;
	public GameState state;
	public ConnectingUI cui;
	
	public ClientState(String ip, int port, IntroGUI hndl){
		this.ip = ip;
		this.port = port;
		this.intUi = hndl;
		hndl.getContentPane().removeAll();
		hndl.setLayout(new GridLayout(1, 1));
		hndl.setResizable(true);
		
		cui= new ConnectingUI();
		hndl.add(cui);
		hndl.setVisible(true);

		
		SocketTypeTable.registerAction(SocketTypeConst.INIT_GAME, new InitGame(this, null));
		SocketTypeTable.registerAction(SocketTypeConst.CLICK_UPDATE, new ClickUpdate(this, null));
		SocketTypeTable.registerAction(SocketTypeConst.GAME_STATE_UPDATE, new GameStateUpdate(this, null));
		SocketTypeTable.registerAction(SocketTypeConst.INIT_GAME_RESP, new InitGameResp(this, null));
		SocketTypeTable.registerAction(SocketTypeConst.WIN_UPD, new WinUpdate(this, null));

		
		clnt = new Client(new Connection(ip, port));
		clnt.addOnConnectionLost(new IConnectLost() {
			@Override
			public void act() {
				System.out.println("Reconnect");
				hndl.getContentPane().removeAll();
				hndl.setLayout(new GridLayout(1, 1));
				hndl.setResizable(true);
				
				cui.setText("");
				hndl.add(cui);
				hndl.setVisible(true);
				ui = null;
				
				boolean connected = false;
				for(int a = 0; a < 50; a++) {
					if(clnt.tryConnect()) {
						connected = true;
						break;
					}else {
						cui.setText("Failed to connect, trying again...");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!connected) {
					cui.setText("Failed to find server for too long...");
					System.exit(0);
				}			
				clnt.sendSocket(SocketTypeConst.INIT_GAME, playerId);
			}
		});
		hndl.addOnClose(new IOnClose() {
			@Override
			public void onClose() {
				if(clnt != null) {
					clnt.stop();
				}
			}
		});
		

		new Thread( new Runnable() {
			
			@Override
			public void run() {
				boolean connected = false;
				for(int a = 0; a < 50; a++) {
					if(clnt.tryConnect()) {
						connected = true;
						break;
					}else {
						cui.setText("Failed to connect, trying again...");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!connected) {
					cui.setText("Failed to find server for too long...");
					System.exit(0);
				}
				clnt.sendSocket(SocketTypeConst.INIT_GAME, 0);
			}
		}).start();
	}
	
	public synchronized void setButtonHandlers() {
		this.ui.registerButtonHandler(new IButtonHandler() {			
			@Override
			public void handleButton(int a, int b, ActionEvent e) {
				if(state.isFree(a, b)) {
					clnt.sendSocket(SocketTypeConst.CLICK_UPDATE, a,b); //Send update
				}
			}
		});
	}
	
	
}
