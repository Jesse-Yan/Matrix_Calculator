package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * This class parse from a JSON file
 * @author Jesse
 *
 */
public class Parser {
  
  private String expression = null;
	public String matrix[][];
	
	public void parse(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
		
		// read the json file
    	Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    	JSONObject jo = (JSONObject) obj; 
    	
    	String status = (String)jo.get("status");
    	if(status.equals("numeric"))
    		expression = (String)jo.get("content");
    	
    	if(status.equals("matrix")) {
    		
    	}
    		
	
	}
	
	public String getExpression() {
		return this.expression;
	}
	
}
