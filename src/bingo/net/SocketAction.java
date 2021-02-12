package bingo.net;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Max
 * @version 1.0.0
 * @since Jun 30, 2015
 */
public abstract class SocketAction extends SocketIO{

	/**
	 * Defines what the client does with the socket. The sockets inputstream MUST REMAIN INVARIANT (no reading, just writing in this method!!)
	 * @param t	to be prepared Socket
	 * @param par Arguments
	 * @throws IOException	if something goes wrong
	 */
	public abstract void send(Socket t, Object[] arg)throws IOException;

	/**
	 * Defines what the server does with the socket
	 * @param t	received socket
	 * @throws IOException	if something goes wrong
	 */
	public abstract void recieve(Socket t)throws IOException;

}