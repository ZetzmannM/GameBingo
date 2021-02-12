package bingo.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import bingo.GameSocketIO;
import bingo.GameState;
import bingo.SocketTypeConst;
import bingo.UI;
import bingo.net.SocketAction;
import bingo.net.SocketIO;
import bingo.state.ClientState;
import bingo.state.ServerState;

//First contact
public class InitGame extends SocketAction {

	ClientState clnt;
	ServerState srv;
	
	public InitGame(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}
	
	
	@Override
	public void send(Socket t, Object[] arg) throws IOException { 
		OutputStream out = t.getOutputStream();
		SocketIO.writeShort((short) clnt.playerId, out);
	}

	@Override
	public void recieve(Socket t) throws IOException {
		System.out.println("Recieved Greeting....");

		InputStream in = t.getInputStream();
		
		short s = (short) SocketIO.readShort(in); // PlayerId
		if(s == 0) {
			s = (short) ++srv.playerCount;
		}
		
		srv.srvIO.sendSocketIndividual(t, SocketTypeConst.INIT_GAME_RESP, s);
	}

}
