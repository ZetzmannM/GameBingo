package bingo.net.server;

import java.io.IOException;
import java.net.Socket;

import bingo.net.SocketTypeTable;

/**
 * Java Server Class
 * @author Max
 * @version 1.0.0
 * @since Jun 30, 2015
 */
public class Server {

	ServerThread server;

	public Server(int port){
		server = new ServerThread(port);
	}

	public void shut(){
		server.running = false;
		server.notifyThreads();
	}
	
	public void sendSocket(short socketType, Object... args) {
		for(ServerCareTakerThread thr : server.threads) {
			server.sendSocket(thr.s, socketType, args);
		}
	}
	
	public void sendSocketIndividual(Socket s, short socketType, Object... args) {
		server.sendSocket(s, socketType, args);
	}

}
