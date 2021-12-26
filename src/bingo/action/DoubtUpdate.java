package bingo.action;

import java.io.IOException;
import java.net.Socket;

import bingo.GameSocketIO;
import bingo.net.SocketAction;
import bingo.state.ClientState;
import bingo.state.ServerState;

public class DoubtUpdate extends SocketAction {

	ClientState clnt;
	ServerState srv;
	
	public DoubtUpdate(ClientState clnt, ServerState srv){
		this.srv = srv;
		this.clnt = clnt;
	}

	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		GameSocketIO.writeDoubtState(srv.state, t.getOutputStream());
	}

	@Override
	public void recieve(Socket t) throws IOException {
		GameSocketIO.readDoubtState(clnt.state, t.getInputStream());
		clnt.ui.drawState(clnt.state);
	}
	
}
