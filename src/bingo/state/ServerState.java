package bingo.state;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.Socket;

import bingo.GameState;
import bingo.IButtonHandler;
import bingo.IOnClose;
import bingo.IntroGUI;
import bingo.SocketTypeConst;
import bingo.UI;
import bingo.WinUI;
import bingo.action.ClickUpdate;
import bingo.action.DoubtAction;
import bingo.action.DoubtUpdate;
import bingo.action.GameStateUpdate;
import bingo.action.InitGame;
import bingo.action.InitGameResp;
import bingo.action.WinUpdate;
import bingo.net.SocketTypeTable;
import bingo.net.server.Server;

public class ServerState {
	public final String[][] goals;
	int port = 0;
	
	public volatile IntroGUI intUi;
	
	public UI ui;
	public GameState state;
	public volatile Server srvIO;
	public final boolean detectImpossibleScenarios;
	public final boolean demoVoting;
	
	public static volatile int playerCount = 1;
	public final int playerId = 1;
	
	private IntroGUI hndl;
	
	public ServerState(String[][] goals, int port, IntroGUI hndl, boolean detect, boolean voting){
		this.goals = goals;
		this.port = port;
		this.intUi = hndl;
		this.detectImpossibleScenarios = detect;
		this.demoVoting = voting;
		this.hndl = hndl;

		hndl.setTitle("Greetings: " + 0 + " | Players : " + 1 + " | Free: 100%");
		
		hndl.addOnClose(new IOnClose() {
			@Override
			public void onClose() {
				srvIO.shut();
			}
		});
				
		this.state = new GameState(goals.length, goals[0].length);
		
		SocketTypeTable.registerAction(SocketTypeConst.INIT_GAME, new InitGame(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.CLICK_UPDATE, new ClickUpdate(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.GAME_STATE_UPDATE, new GameStateUpdate(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.INIT_GAME_RESP, new InitGameResp(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.WIN_UPD, new WinUpdate(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.DOUBT, new DoubtAction(null, this));
		SocketTypeTable.registerAction(SocketTypeConst.DOUBT_UPDATE, new DoubtUpdate(null, this));

		hndl.getContentPane().removeAll();
		hndl.setLayout(new GridLayout(1, 1));
		hndl.setResizable(true);
		
		this.ui = new UI(goals);
		this.ui.drawState(state);
		
		final ServerState sRef = this;
		
		this.ui.registerButtonHandler(new IButtonHandler() {			
			@Override
			public void handleButton(int a, int b, ActionEvent e) {
					if(state.isFree(a, b)) {
					state.set(a, b, (byte) (playerId));
					ui.drawState(state);
					srvIO.sendSocket(SocketTypeConst.GAME_STATE_UPDATE); //Send update
					
					int winn = 0;
					if(detectImpossibleScenarios && ((winn = state.getWinner()) != -1)) {
						intUi.setVisible(false);
						intUi.getContentPane().removeAll();
						WinUI ui = new WinUI(playerId, winn, sRef);
						intUi.add(ui);
						intUi.setVisible(true);

						srvIO.sendSocket(SocketTypeConst.WIN_UPD, (short) winn);
					}
				}
			}
			
			@Override
			public void handleButton3(int a, int b, MouseEvent e) {
				if(!state.isFree(a, b)) {
					if(demoVoting && state.get(a, b) != playerId ) {
						state.flipDoubtStateOn(a, b, playerId);
					}
					

					if(state.get(a, b)== playerId 
					||(demoVoting&&((state.getDoubtsOn(a, b)/((float)(playerCount-1)))>0.6666666))) {
						state.resetDoubts(a,b);
						state.set(a, b, (byte)0);
					
						srvIO.sendSocket(SocketTypeConst.GAME_STATE_UPDATE);
					}
					
					srvIO.sendSocket(SocketTypeConst.DOUBT_UPDATE);
					ui.drawState(state);
				}
			}
		});
		
		hndl.add(ui);
		hndl.setVisible(true);
				
		srvIO = new Server(port);
	}
	
	
	public void showIntroDialogue() {
		hndl.setVisible(true);
	}
}
