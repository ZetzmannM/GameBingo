package bingo.net.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import bingo.net.SocketTypeTable;

/**
 * Java Server Thread class, this class is responsible for accepting Sockets
 * @author Max
 * @version 1.0.0
 * @since Jun 30, 2015
 */
public class ServerThread extends Thread {

	volatile boolean running = true;
	ServerSocket server;
	int MAX = 10;
	int port;
	
	ArrayList<ServerCareTakerThread> threads;
	

	public ServerThread(int port){
		try {
			server = new ServerSocket(port);
			this.port = port;
			this.threads = new ArrayList<ServerCareTakerThread>();
			this.start();
		} catch (IOException e) {
			if(e instanceof BindException){
				System.out.println("ERROR Server already running? [port:"+port+"]");
			}else{
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendSocket(Socket s, short type, Object[] objects){
		try {
			System.out.println("SERVER | SND " + type);

			s.getOutputStream().write(ByteBuffer.allocate(2).putShort(type).array());
			SocketTypeTable.get(type).send(s, objects);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void notifyThreads() {
		synchronized(threads) {
			for(ServerCareTakerThread thr : threads) {
				thr.interrupt();
			}
		}
	}
	
	@Override
	public void run() {
		System.out.println("INFO Starting Server ... [port:"+port+"]");
		
		while(running){
			if(threads.size() < MAX) {
				try {
					Socket s = server.accept();
					synchronized(threads) {
						threads.add(new ServerCareTakerThread(s, threads.size(), this));
						threads.get(threads.size()-1).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class ServerCareTakerThread extends Thread {
	
	Socket s = null;
	int indx = 0;
	ServerThread thr;
	
	ServerCareTakerThread(Socket s, int indx, ServerThread thr){
		this.s = s;
		this.indx = 0;
		this.thr = thr;
	}
	
	
	@Override
	public void run() {
		while(!s.isClosed() && thr.running) {
			try {
				short type =  (short) (s.getInputStream().read() << 8 & 0xff00 | s.getInputStream().read() << 0 & 0x00ff);
				System.out.println("SERVER | RCV " + type);
				SocketTypeTable.get(type).recieve(s);
			} catch (IOException e) {
				System.err.println("Connection Reset!");
				break;
			}
		}
		
		synchronized(thr.threads) {
			thr.threads.remove(indx);
		}
	}
}
