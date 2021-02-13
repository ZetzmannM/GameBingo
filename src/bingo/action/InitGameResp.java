package bingo.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import bingo.ColorEncoder;
import bingo.GameSocketIO;
import bingo.GameState;
import bingo.UI;
import bingo.net.SocketAction;
import bingo.net.SocketIO;
import bingo.state.ClientState;
import bingo.state.ServerState;

public class InitGameResp extends SocketAction{
	ClientState clnt;
	ServerState srv;
	
	public InitGameResp(ClientState clnt, ServerState stat) {
		this.clnt = clnt;
		this.srv = stat;
	}

	@Override
	public void send(Socket t, Object[] arg) throws IOException {
		short s = (short)arg[0];
		System.out.println("Sending Greeting Response....");

		
		OutputStream out = t.getOutputStream();
		
		SocketIO.writeShort(s, out);
		
		GameSocketIO.writeGoals(srv.goals, out);
		GameSocketIO.writeGameState(srv.state, out);		

	}

	@Override
	public void recieve(Socket t) throws IOException {
		System.out.println("Recieved Greeting Response....");

		InputStream in = t.getInputStream();
		
		clnt.playerId = SocketIO.readShort(in);
		System.out.println("Got assigned id " + clnt.playerId + " greeted");
		clnt.intUi.setTitle("You are player: " + clnt.playerId + " (" + ColorEncoder.nameById(clnt.playerId) + ")"); 
		
		String[][] args;
		try {
			clnt.intUi.setVisible(false);
			clnt.intUi.getContentPane().removeAll();

			args = GameSocketIO.readGoals(in);
			clnt.ui = new UI(args);
			clnt.state = new GameState(args.length, args[0].length);
			GameSocketIO.readGameState(clnt.state, in);
			clnt.setButtonHandlers();		
			clnt.ui.drawState(clnt.state);
			
			clnt.intUi.add(clnt.ui);
			clnt.intUi.setVisible(true);
			
		} catch (ParseException e) {
			System.out.print("Greeting failed, exiting");
			e.printStackTrace();
			System.exit(-1);
		}		
	}
}
