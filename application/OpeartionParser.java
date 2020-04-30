//////////////////////////////// CS 400 HEADER ////////////////////////////////
//
// Title: Ateam project - Matrix Calculator
// Course: COMP SCI 400, Spring 2020
// Lecturer: Debra Deppeler
//
/////////////////////////////////// AUTHORS ///////////////////////////////////
//
// Chengpo Yan LEC001 xteam186 - cyan46@wisc.edu
// Jinming Zhang, LEC001, x-team132 - jzhang2279@wisc.edu
// Archer Li LEC001 x-team145 - zli885@wisc.edu
// Houming Chen, LEC001, xteam149 - hchen634@wisc.edu
// Chengxu Bian, LEC001, xteam102 - cbian7@wisc.edu
//
///////////////////////////////// SOURCE FILES ////////////////////////////////
//
// Calculator.java, CalculatorTest.java, CalSteps.java, Fraction.java
// Main.java, Matrix.java, MatrixADT.java, MatrixArithmeticException.java, 
// MatrixCalculator.java, MatrixDimensionsMismatchException.java,
// MatrixTest.java, Numeric.java, NumericTest.java, OpeartionParser.java, 
// SequenceSummary.java, SimpleCalculator.java, SingularException.java, 
// Writer.java, styleSheet.css
//
///////////////////////////////// DESCRIPTION /////////////////////////////////
//
// This project "Matrix calculator" by CS400 Ateam 2 aims to help students
// studying linear algebra to understand the calculations of linear algebra 
// better. This "Matrix calculator" can not only do many matrix calculations 
// like matrix multiplication, finding eigenvalues, and LUP, QR, or Cholesky 
// decompositions, but it can also support basic algebra calculations like a 
// normal calculator and analyzing sequence.
// 
// This "Matrix calculator" consists of two parts, a math calculator on the 
// left side, which supports basic algebra calculations and analyzing sequence,
// and a matrix calculator on the right side, which supports calculations of 
// matrices.
//
// For matrix calculations, this "Matrix calculator" also supports file inputs 
// and outputs. The input files should be json files in a specific format, and 
// the output files will also be json files. "Matrix calculator" is also 
// friendly to the computer user who does not have a keyboard. Users can input 
// their data by clicking buttons provided on the user interface.
//
//////////////////////////////////// CREDITS //////////////////////////////////
//
// LUP algorithms:
// https://courses.engr.illinois.edu/cs357/fa2019/references/ref-7-linsys/
//
// QR algorithms:
// https://www-users.cs.umn.edu/~saad/csci5304/FILES/LecN13.pdf
//
// Algorithm for reduction to Hessenberg-form:
// https://math.stackexchange.com/questions/732924/reducing-a-matrix-to-upper-
// hessenberg-form-using-householder-transformations-in
//
// Algorithm for Wilkinson- shift:
// http://web.stanford.edu/class/cme335/lecture5
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.File;
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
 * @author Archer (2020/4)
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
    public OpeartionParser(File file) throws IOException, ParseException, java.lang.ClassCastException {
      
      calSteps = new ArrayList<CalSteps>();
      
      if (file == null) throw new FileNotFoundException();
      
      ArrayList <String[][]> matrixList = new ArrayList<String[][]>();
      String operations = null;
      int rowSize = -1;
      int colSize = -1;
      String[][] sumMatrix = null;
      
      Object obj = new JSONParser().parse(new FileReader(file));
      JSONObject jo = (JSONObject) obj;
      JSONArray calculations = (JSONArray) jo.get("calculations");

      for (int i = 0; i < calculations.size(); i++) {
        JSONObject jsonPack = (JSONObject) calculations.get(i);
        operations = (String) jsonPack.get("operations");

        JSONArray datas = (JSONArray) jsonPack.get("datas");

        for (int j = 0; j<datas.size();++j) {
          JSONArray matrixs = (JSONArray)datas.get(j);
          rowSize = matrixs.size();
          for (int row = 0; row<matrixs.size();++row) {
            JSONArray rows = (JSONArray)matrixs.get(row);
            String ttteeemmmppp;
            if (colSize == -1) {             
              colSize = rows.size();
              sumMatrix = new String[rowSize][colSize];
            } 
            
            for (int col = 0; col < rows.size();++col) {
              ttteeemmmppp = ((String) rows.get(col));
              sumMatrix[row][col] = new String(ttteeemmmppp);
            }
          }
          String [][] realMatrix = new String[sumMatrix.length][];
          for (int a=0; a<sumMatrix.length;a++) {
            realMatrix[a] = sumMatrix[a].clone();
          }
          matrixList.add(realMatrix.clone());  
        }
        calSteps.add(new CalSteps(operations,matrixList));
        matrixList = new ArrayList<String[][]>();
        operations = null;
      }
    }
    
    /**
     * get list of matrix
     *
     * @return list of matrix
     */
    public List<CalSteps> getCalculations() {
        return calSteps;
    }

}
