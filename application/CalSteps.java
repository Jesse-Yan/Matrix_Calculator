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
// This project ¡°Matrix calculator¡± by CS400 Ateam 2 aims to help students
// studying linear algebra to understand the calculations of linear algebra 
// better. This ¡°Matrix calculator¡± can not only do many matrix calculations 
// like matrix multiplication, finding eigenvalues, and LUP, QR, or Cholesky 
// decompositions, but it can also support basic algebra calculations like a 
// normal calculator and analyzing sequence.
// 
// This ¡°Matrix calculator¡± consists of two parts, a math calculator on the 
// left side, which supports basic algebra calculations and analyzing sequence,
// and a matrix calculator on the right side, which supports calculations of 
// matrices.
//
// For matrix calculations, this ¡°Matrix calculator¡± also supports file inputs 
// and outputs. The input files should be json files in a specific format, and 
// the output files will also be json files. ¡°Matrix calculator¡± is also 
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

import java.util.ArrayList;

/**
 * Calculations - Object stores basic Calculations data
 * @author Archer (2020)
 * 
 */
public class CalSteps {
  
  private String operation;//operations stores here

  private ArrayList<String[][]> datas;//data stores here
  
  private Object result;
  
  private int type;//type that refers: 1, normal steps; 2, result in array
  //3, result in String

  

  /**
   * constructor of Calculations object
   * 
   * @param result - result of calculations
   */
  public CalSteps(Object result) {
    this.result = result;
    this.datas = null;
    this.operation = null;
    if (result instanceof String)
      this.type = 3;
    else this.type = 2;
  }
  
  /**
   * constructor of Calculations object
   * 
   * @param operation - operation
   * @param datas - datas
   */
  public CalSteps(String operation, ArrayList<String[][]> datas) {
    this.operation = operation;
    this.datas = datas;
    this.result = null;
    this.type = 1;
  }
  
  /**
   * constructor of Calculations object
   * 
   * @param operation - operation
   * @param datas - datas
   * @param result - result whether in String or List<String[][]>
   */
  public CalSteps(String operation, ArrayList<String[][]> datas, Object result) {
    this.operation = operation;
    this.datas = datas;
    this.result = result;
    if (result instanceof String)
      this.type = 3;
    else this.type = 2;
  }
  
  /**
   * Getter of operation
   * 
   * @return operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Getter of datas
   * 
   * @return datas
   */
  public ArrayList<String[][]> getDatas() {
    return datas;
  }
  
  /**
   * Getter of result 
   * 
   * @return result - result
   * @throws Exception - throw exception when case not met
   */
  public Object getResult() {
    return result;
  }
  
  /**
   * Getter for type
   * 
   * @return type in int
   */
  public int getType() {
    return type;
  }

}
