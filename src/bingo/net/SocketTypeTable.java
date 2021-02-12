package bingo.net;

import java.util.HashMap;

/**
 *	Class containing all the SocketActions registered under their socketTypes
 *	@author Max Zetzmann
 *	@since 12.03.2016, 23:08:25
 *	@version 1.0.0
 */

public class SocketTypeTable {

	static HashMap<Short, SocketAction> map = new HashMap<Short, SocketAction>();

	public static void registerAction(short socketType, SocketAction act){
		map.put(socketType, act);
	}

	public static SocketAction get(short type){
		return map.get(type);
	}

}
