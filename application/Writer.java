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
// This project ¡°Matrix calculator" by CS400 Ateam 2 aims to help students
// studying linear algebra to understand the calculations of linear algebra 
// better. This ¡°Matrix calculator" can not only do many matrix calculations 
// like matrix multiplication, finding eigenvalues, and LUP, QR, or Cholesky 
// decompositions, but it can also support basic algebra calculations like a 
// normal calculator and analyzing sequence.
// 
// This ¡°Matrix calculator" consists of two parts, a math calculator on the 
// left side, which supports basic algebra calculations and analyzing sequence,
// and a matrix calculator on the right side, which supports calculations of 
// matrices.
//
// For matrix calculations, this ¡°Matrix calculator" also supports file inputs 
// and outputs. The input files should be json files in a specific format, and 
// the output files will also be json files. ¡°Matrix calculator" is also 
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
import java.io.PrintWriter;
import java.util.List;

/**
 * Writer - Class that generate .json file base on result
 * 
 * @author Archer (2020)
 * 
 */
public class Writer {
  private List<CalSteps> results;// list to store results in steps
  PrintWriter printWriter = null;// initialize a printWriter

  /**
   * constructor of Writer object
   * 
   * @param results - results list
   */
  public Writer(List<CalSteps> results) {
    this.results = results;
  }

  /**
   * helper method to write data and operation
   * 
   * @param cur
   */
  public void writeDataAndOperation(CalSteps cur) {
    printWriter.write("\t\t" + "\"operations\": \"" + cur.getOperation() + "\",\n");
    printWriter.write("\t\t" + "\"datas\": [\n");
    for (int j = 0; j < cur.getDatas().size(); ++j) {
      printWriter.write("\t\t\t");
      if (j != 0)
        printWriter.write(",");
      printWriter.write("[\n");

      printWriter.write(writeMatrixToJsonString(cur.getDatas().get(j)));

      printWriter.write("\t\t\t" + "]\n");
    }
    printWriter.write("\t\t" + "]\n");
  }

  /**
   * 
   * Helper method to change a matrix which is represented by String[][] to a String for Json output.
   * 
   * @param matrix a given String[][] to represent a matrix
   * @return a string for the Json output to represent a matrix.
   * 
   * @author Houming Chen
   */
  public static String writeMatrixToJsonString(String matrix[][]) {
      String string = "";
      for (int i = 0; i < matrix.length; i++) {
        if (i != 0)
          string += ",\n";
        string += "[";
        for (int j = 0; j < matrix[0].length; j++) {
          if (j != 0)
            string += ", ";
          string += "\"" + matrix[i][j] + "\"";
        }
        string += "]";
      }
      return string;
  }
  
  /**
   * helper method to write result
   * 
   * @param cur - current object calStep;
   */
  public void writeResult(CalSteps cur) {

    printWriter.write("\t\t" + "\"result\": ");
    if (cur.getType() == 3) {
      try {
        printWriter.write("\"" + (String) cur.getResult() + "\"\n");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        @SuppressWarnings("unchecked")
        List<String[][]> end = (List<String[][]>) cur.getResult();
        printWriter.write("[\n");
        for (int j = 0; j < end.size(); ++j) {
          printWriter.write("\t\t\t");
          if (j != 0)
            printWriter.write(",");
          printWriter.write("[\n");

          printWriter.write(writeMatrixToJsonString(end.get(j)));

          printWriter.write("\t\t\t" + "]\n");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      printWriter.write("\t\t" + "]\n");
    }
  }

  /**
   * Method that create a .json file with result stores in it.
   * 
   * @throws FileNotFoundException - if error in files
   */
  public void save(File file) throws FileNotFoundException {
    try {
      printWriter = new PrintWriter(file);
      printWriter.write("{" + "\n");
      printWriter.write("\t" + "\"" + "StepToResult" + "\":[\n");

      CalSteps cur = null;
      for (int i = 0; i < results.size(); ++i) {
        if (i != 0)
          printWriter.write("\t" + ",\n");
        printWriter.write("\t" + "{\n");
        cur = results.get(i);
        if (cur.getType()==1) {
          writeDataAndOperation(cur);
        } else if (cur.getOperation() == null && cur.getDatas() == null
            && cur.getResult() != null) {
          writeResult(cur);
        } else {
          writeDataAndOperation(cur);
          printWriter.write(",\n");
          writeResult(cur);
        }
        printWriter.write("\t" + "}\n");
      }
      printWriter.write("\t" + "]\n");
      printWriter.write("}");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw e;
    }
    printWriter.close();
  }

}
