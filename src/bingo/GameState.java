package bingo;

import java.util.HashMap;

import bingo.state.ServerState;

public class GameState {
	
	private byte[][] state;
	
	int width, height;
	
	public GameState(int a, int b){
		this.width = a;
		this.height = b;
		state = new byte[a][b];
	}
	
	public synchronized void set(int a, int b, byte c) {
		state[a][b] = c;
	}
	
	public boolean isFree(int a, int b) {
		return state[a][b] == 0;
	}
	
	public void reset() {
		state = new byte[width][height];
	}
	
	public synchronized byte get(int a, int b) {
		return state[a][b];
	}
	
	public float[] percentages() {
		float[] arr = new float[ServerState.playerCount+1];
		
		for(int a = 0; a < width; ++a) {
			for(int b = 0; b < height; ++b) {
				arr[state[a][b]]++;
			}
		}
		
		for(int a = 0; a < arr.length; a++) {
			arr[a] /= (float)(this.width * this.height);
		}
		
		return arr;
	}
	
	public synchronized int getWinner() {
		int[] arr = new int[ServerState.playerCount+1];
		
		for(int a = 0; a < width; ++a) {
			for(int b = 0; b < height; ++b) {
				arr[state[a][b]]++;
			}
		}
		
		if(arr[0] == 0) {
			return 0;
		}
		
		int maxPid = 0;
		int maxVal = 0;
		for(int a = 1; a <= ServerState.playerCount; ++a) {
			int vl = arr[a];
			if(vl > maxVal) {
				maxPid = a;
				maxVal = vl;
			}
		}

		int minDiff = Integer.MAX_VALUE;
		for(int a = 1; a <= ServerState.playerCount; ++a) {
			if(a != maxPid) {
				if(maxVal - arr[a] < minDiff) {
					minDiff = maxVal - arr[a];
				}
			}
		}
		
		if( minDiff > arr[0]) {
			return maxPid;
		}else {
			return -1;
		}
	}
	
}
