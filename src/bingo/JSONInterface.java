package bingo;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONInterface {
	
	static String[][] fromJson(String a) throws ParseException{
		JSONParser par = new JSONParser();
		Object obj = par.parse(a);
        JSONArray array = (JSONArray)obj;
        ArrayList<String> goals = new ArrayList<>();
        
        for(Object s : array) {
        	JSONObject js = (JSONObject) s;
        	goals.add((String)js.get("name"));
        }
  
		return fromStringList(goals);
	}
	
	static String toJson(String[][] ar) {
		JSONArray obj = new JSONArray();
		
		for(int a = 0; a<ar.length; ++a) {
			for(int b = 0; b<ar[0].length; ++b) {
				JSONObject entry = new JSONObject();
				entry.put("name", ar[a][b]);
				obj.add(entry);
			}
		}
		
		return obj.toJSONString();
	}
	
	static String[][] fromStringList(ArrayList<String> goals){
        ArrayList<Integer> i = getAllDivisors(goals.size());
        int cols = i.get((int) Math.floor(i.size()/2.0f));
        int rows = goals.size()/cols;
        
        String[][] res = new String[rows][cols];
        int ind = 0;
        
        for(int r = 0; r < rows; ++r) {
            for(int c = 0; c < cols; ++c) {
            	res[r][c] = goals.get(ind++);
            }
        }
        		
		return res;
	}
	
	private static ArrayList<Integer> getAllDivisors(int size){
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(int a = 1;  a < size; a++) {
			if((size %a) == 0) {
				res.add(a);
			}
		}
		res.add(size);
		return res;
	}
	
}
