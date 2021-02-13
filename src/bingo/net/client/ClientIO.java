package bingo.net.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import bingo.IConnectLost;
import bingo.net.SocketTypeTable;

/**
 * Class for sending Sockets from client to server
 * @author Max
 * @version 1.0.0
 * @since Jun 30, 2015
 */
public class ClientIO {

	Client c;
	Socket s;
	boolean running;
	volatile ClientCareTakerThread thr;
	volatile IConnectLost a = null;

	public ClientIO(Client c){
		this.c = c;
		this.running = true;
	}
	
	public void stop() {
		this.running = false;
		if(thr != null) {
			thr.interrupt();
		}
	}
	
	public boolean tryConnect() {
		try {
			this.s = new Socket(c.connection.ip, c.connection.port);
			thr = new ClientCareTakerThread(s, this);
			thr.start();
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			if ( e instanceof ConnectException){
				System.out.println("ERROR No server found [ip:"+c.connection.ip+";port:"+c.connection.port+"]");
			}
			return false;
		}
	}
	
	/**
	 * Sends a Socket with the given type
	 * @param type Socket type
	 * @param objects arguments
	 */
	public void sendSocket(short type, Object[] objects){
		try {
			s.getOutputStream().write(ByteBuffer.allocate(2).putShort(type).array());
			SocketTypeTable.get(type).send(s, objects);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class ClientCareTakerThread extends Thread {
	Socket s = null;
	ClientIO thr;
	
	ClientCareTakerThread(Socket s, ClientIO thr){
		this.s = s;
		this.thr = thr;
	}
	
	
	@Override
	public void run() {
		InputStream in;
		boolean bad = false;
		try {
			in = s.getInputStream();
			while(thr.running && !bad) {
				try {
					short type =  (short) (in.read() << 8 & 0xff00 | in.read() << 0 & 0x00ff);
					SocketTypeTable.get(type).recieve(s);
				} catch (IOException e) {
					System.err.println("Connection Reset!");
					bad = true;
					break;
				}
			}	
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		thr.thr = null;
		thr.a.act();
		System.out.println("Terminate old handler....");
	}

}
