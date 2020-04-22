package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * OpeartionParser - read .json file and form calculation steps object
 * 
 * @author Archer (2020)
 * 
 */
public class OpeartionParser {

    private ArrayList<CalSteps> calSteps;// list store all matrixs
    
    /**
     * constructor method to parse a json file
     *
     * @param jsonFilepath - path to .json file
     * @throws ParseException - if the given json cannot be parsed 
     * @throws MatrixDimensionsMismatchException - if matrix properties are wrong
     * @throws FileNotFoundException - if file not found
     * @throws java.lang.ClassCastException - if input data mismatch
     */
    public OpeartionParser(String jsonFilepath) throws IOException, ParseException, 
    MatrixDimensionsMismatchException, java.lang.ClassCastException {
      
      calSteps = new ArrayList<CalSteps>();
      
      if (jsonFilepath == null) throw new FileNotFoundException();
      
      ArrayList <Matrix> matrixList = new ArrayList<Matrix>();
      String operations = null;
      int rowSize = -1;
      int colSize = -1;
      String[][] sumMatrix = null;
      
      Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
      JSONObject jo = (JSONObject) obj;
      JSONArray calculations = (JSONArray) jo.get("calculations");

      for (int i = 0; i < calculations.size(); i++) {
        JSONObject jsonPack = (JSONObject) calculations.get(i);
        operations = (String) jsonPack.get("operations");
//       System.out.println("operations: "+operations);//test purpose

        JSONArray datas = (JSONArray) jsonPack.get("datas");

        for (int j = 0; j<datas.size();++j) {
          JSONArray matrixs = (JSONArray)datas.get(j);
          rowSize = matrixs.size();
          for (int row = 0; row<matrixs.size();++row) {
            JSONArray rows = (JSONArray)matrixs.get(row);
            if (colSize == -1) {             
              colSize = rows.size();
              sumMatrix = new String[rowSize][colSize];
            } 
            for (int col = 0; col < rows.size();++col) {
              sumMatrix[row][col] = (String) rows.get(col);
            }
            
          }
          matrixList.add(new Matrix(sumMatrix));  
        }
        calSteps.add(new CalSteps(operations,matrixList));
        matrixList = new ArrayList<Matrix>();
        operations = null;
      }
    }
    
    /**
     * get list of matrix
     *
     * @return list of matrix
     */
    @SuppressWarnings("rawtypes")
    public List getCalculations() {
        return calSteps;
    }

    /**
     * Test purpose
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
          OpeartionParser parser = new OpeartionParser("testCal.json");
            System.out.println("success!!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
