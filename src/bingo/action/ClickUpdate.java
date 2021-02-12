package bingo.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import bingo.SocketTypeConst;
import bingo.WinUI;
import bingo.net.SocketAction;
import bingo.net.SocketIO;
import bingo.state.ClientState;
import bingo.state.ServerState;

//Someone clicked something
public class ClickUpdate extends SocketAction {

	ClientState clnt;
	ServerState srv;
	
	public ClickUpdate(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}
	
	
	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		int a = (int)arg[0];
		int b = (int)arg[1];
		OutputStream os =  t.getOutputStream();
				
		SocketIO.writeInteger(a, os);
		SocketIO.writeInteger(b, os);
		SocketIO.writeInteger(clnt.playerId, os);
	}
	

	@Override
	public void recieve(Socket t) throws IOException {
		int a = SocketIO.readInteger(t.getInputStream());
		int b = SocketIO.readInteger(t.getInputStream());
		int id = SocketIO.readInteger(t.getInputStream());
		
		if(srv.state.isFree(a, b)) {
			srv.state.set(a, b, (byte) id);
			srv.ui.drawState(srv.state);
		}
		
		srv.srvIO.sendSocket(SocketTypeConst.GAME_STATE_UPDATE);
		
		int winn = 0;
		if((winn = srv.state.getWinner()) != -1) {
			srv.intUi.setVisible(false);
			srv.intUi.getContentPane().removeAll();
			WinUI ui = new WinUI(srv.playerId, winn);
			srv.intUi.add(ui);
			srv.intUi.setVisible(true);

			srv.srvIO.sendSocket(SocketTypeConst.WIN_UPD, (short) winn);
		}
	}

}
