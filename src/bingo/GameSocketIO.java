package bingo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.parser.ParseException;

import bingo.net.SocketIO;

public class GameSocketIO {

	public static void writeGameState(GameState state, OutputStream os) throws IOException{
		for(int a = 0; a < state.width; ++a) {
			for(int b = 0; b < state.height; ++b) {
				SocketIO.writeShort(state.get(a, b), os);
			}
		}
	}
	
	public static void writeDoubtState(GameState state, OutputStream os) throws IOException {
		for(int a = 0; a < state.width; ++a) {
			for(int b = 0; b < state.height; ++b) {
				SocketIO.writeShort((short) state.getDoubtsOn(a, b), os);
			}
		}
	}
	
	public static void readGameState(GameState state, InputStream is) throws IOException{
		for(int a = 0; a < state.width; ++a) {
			for(int b = 0; b < state.height; ++b) {
				state.set(a, b, (byte) SocketIO.readShort(is));
			}
		}
	}
	public static void readDoubtState(GameState state, InputStream is) throws IOException{
		for(int a = 0; a < state.width; ++a) {
			for(int b = 0; b < state.height; ++b) {
				state.resetDoubts(a, b);
				short value = (short) SocketIO.readShort(is);
				for(int i = 0; i < value; ++i) {
					state.flipDoubtStateOn(a,b, i);
				}
			}
		}
	}

	
	public static void writeGoals(String[][] args, OutputStream os) throws IOException{
		SocketIO.writeString(os, JSONInterface.toJson(args));
	}
	
	public static String[][] readGoals(InputStream is) throws IOException, ParseException {
		String rad = SocketIO.readString(is);
		return JSONInterface.fromJson(rad);
	}
	
}
