package bingo.action;

import java.io.IOException;
import java.net.Socket;

import bingo.ColorEncoder;
import bingo.GameSocketIO;
import bingo.SocketTypeConst;
import bingo.net.SocketAction;
import bingo.state.ClientState;
import bingo.state.ServerState;

//GameState is distributed
public class GameStateUpdate extends SocketAction {

	ClientState clnt;
	ServerState srv;
	
	
	public static String gameStatus = "";
	
	public GameStateUpdate(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}

	
	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		System.out.println("Sending update");
		GameSocketIO.writeGameState(srv.state, t.getOutputStream());
		
		float[] arr = srv.state.percentages();
		
		gameStatus="";
		for(int a = 0; a < arr.length; a++) {
			gameStatus += " | " + ColorEncoder.nameById(a) + ": " + (Math.round(arr[a]*1000)/10) + "%"; 
		}
		
		srv.intUi.setTitle(InitGame.initStatus+ GameStateUpdate.gameStatus);
	}

	@Override
	public void recieve(Socket t) throws IOException {
		System.out.println("Recieved update");
		GameSocketIO.readGameState(clnt.state, t.getInputStream());
		clnt.ui.drawState(clnt.state);
	}

}
