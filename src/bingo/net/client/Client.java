package bingo.net.client;

import bingo.IConnectLost;
import bingo.net.Connection;

/**
 * Java CGL Client class
 * @author Max
 * @version 1.0.0
 * @since Jun 30, 2015
 */
public class Client {

	public ClientIO io;
	Connection connection;

	public Client(Connection connection){
		this.connection = connection;
		io = new ClientIO(this);
	}

	public void sendSocket(short socketType, Object... args) {
		io.sendSocket(socketType, args);
	}
	
	public void stop() {
		io.stop();
	}
	
	public boolean tryConnect() {
		return io.tryConnect();
	}
	
	public void addOnConnectionLost(IConnectLost a) {
		io.a = a;
	}

}
