package bingo.action;

import java.io.IOException;
import java.net.Socket;

import bingo.WinUI;
import bingo.net.SocketAction;
import bingo.net.SocketIO;
import bingo.state.ClientState;
import bingo.state.ServerState;

public class WinUpdate extends SocketAction {
	ClientState clnt;
	ServerState srv;
	
	public WinUpdate(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}

	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		SocketIO.writeShort((short)arg[0], t.getOutputStream());
	}

	@Override
	public void recieve(Socket t) throws IOException {
		short winner = (short) SocketIO.readShort(t.getInputStream());
		clnt.intUi.setVisible(false);
		clnt.intUi.getContentPane().removeAll();
		WinUI ui = new WinUI(clnt.playerId, winner, null);
		clnt.intUi.add(ui);
		clnt.intUi.setVisible(true);
	}

}
