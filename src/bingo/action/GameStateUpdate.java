package bingo.action;

import java.io.IOException;
import java.net.Socket;

import bingo.GameSocketIO;
import bingo.net.SocketAction;
import bingo.state.ClientState;
import bingo.state.ServerState;

//GameState is distributed
public class GameStateUpdate extends SocketAction {

	ClientState clnt;
	ServerState srv;
	
	public GameStateUpdate(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}

	
	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		System.out.println("Sending update");
		GameSocketIO.writeGameState(srv.state, t.getOutputStream());
	}

	@Override
	public void recieve(Socket t) throws IOException {
		System.out.println("Recieved update");
		GameSocketIO.readGameState(clnt.state, t.getInputStream());
		clnt.ui.drawState(clnt.state);
	}

}
