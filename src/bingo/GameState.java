package bingo;

import java.util.HashMap;

import bingo.state.ServerState;

public class GameState {
	
	private byte[][] state;
	private HashMap<Integer, Boolean>[][] doubts;

	int width, height;
	
	public GameState(int a, int b){
		this.width = a;
		this.height = b;
		state = new byte[a][b];
		doubts = new HashMap[a][b];
		for(int i = 0; i < a ; ++i) {
			for(int j = 0; j < b; ++j) {
				doubts[i][j] = new HashMap<Integer, Boolean>();
			}
		}
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
	
	public synchronized void resetDoubts(int a, int b) {
		doubts[a][b].clear();
	}
	
	public synchronized byte get(int a, int b) {
		return state[a][b];
	}
	
	public synchronized boolean flipDoubtStateOn(int a, int b, int pId) {
		if(doubts[a][b].containsKey(pId) && doubts[a][b].get(pId)) {
			doubts[a][b].put(pId, false);
			return false;
		}else {
			doubts[a][b].put(pId, true);
			return true;
		}
	}
	
	
	public synchronized int getDoubtsOn(int a, int b) {
		int count = 0;		
		for (Integer pId : this.doubts[a][b].keySet()) {
			if(this.doubts[a][b].get(pId)) {
				++count;
			}
		}
		return count;
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
			boolean unique = true;
			int max = 0;
			int pMax = 0;
			
			for(int a = 1; a <= ServerState.playerCount; ++a) {
				if(arr[a] > max) {
					pMax = a;
					max = arr[a];
					unique = true;
				}else {
					if(arr[a] == max) {
						unique = false;
					}
				}
			}
			if(unique) {
				return pMax;
			}else {
				return 0;
			}
			
		}else {		
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
	
}
