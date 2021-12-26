package bingo.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import bingo.SocketTypeConst;
import bingo.net.SocketAction;
import bingo.net.SocketIO;
import bingo.state.ClientState;
import bingo.state.ServerState;
import bingo.state.ClientState;
import bingo.state.ServerState;

public class DoubtAction extends SocketAction {
	
	ClientState clnt;
	ServerState srv;
	
	public DoubtAction(ClientState clnt, ServerState srv){
		this.srv = srv;
		this.clnt = clnt;
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
		
		if(!srv.state.isFree(a, b)) {
			if(srv.demoVoting && (srv.state.get(a, b) != id )) {
				srv.state.flipDoubtStateOn(a, b, id);
			}
			
			if(srv.state.get(a, b)== id 
			||(srv.demoVoting&&((srv.state.getDoubtsOn(a, b)/((float)(srv.playerCount-1)))>0.6666666))) {
				srv.state.resetDoubts(a,b);
				srv.state.set(a, b, (byte)0);
				srv.srvIO.sendSocket(SocketTypeConst.GAME_STATE_UPDATE);
			}
			
			srv.ui.drawState(srv.state);			
			srv.srvIO.sendSocket(SocketTypeConst.DOUBT_UPDATE);
		}
	}

}
