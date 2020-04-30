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
import java.util.Stack;

/**
 * Simple Calculator class that does not handle complex calculations
 * 
 * @author Jesse
 *
 */
public class SimpleCalculator {

  // Data Structure that supports the calculation
  private Stack<Double> theStackI;
  private Stack<String> theStackP;
  private String[] input;
  private ArrayList<String> output;
  private double num1, num2, interAns;

  /**
   * Constructor of the SimpleCalculator
   * 
   * @param s the expression to read
   */
  public SimpleCalculator(String[] s) {
    this.input = s;
    theStackP = new Stack<String>();
    output = new ArrayList<String>();
  }

  /**
   * Calculation method that read input expression base on stack
   * 
   * @return result after process
   */
  public double doCal() {
    doTrans();
    doParse();
    return theStackI.pop();
  }

  /**
   * Read expression from stack and store result in stack
   */
  public void doParse() {
    theStackI = new Stack<Double>();
    for (int i = 0; i < output.size(); i++) {
      String temp = output.get(i);
      if (!(temp.equals("+") || temp.equals("-") || temp.equals("*")
          || temp.equals("/"))) {
        theStackI.push(Double.parseDouble(temp));
      } else {
        num2 = theStackI.pop();
        num1 = theStackI.pop();
        switch (temp) {
          case "+":
            interAns = num1 + num2;
            break;
          case "-":
            interAns = num1 - num2;
            break;
          case "*":
            interAns = num1 * num2;
            break;
          case "/":
            interAns = num1 / num2;
            break;
          default:
            interAns = 0;
            break;
        }
        theStackI.push(interAns);
      }
    }
  }

  /**
   * read and distribute calculation priority
   */
  public void doTrans() {
    for (int i = 0; i < input.length; i++) {
      String temp = input[i];
      if (temp.equals("(")) {
        theStackP.push(temp);
      } else if (temp.equals("+") || temp.equals("-")) {
        goOper(temp, 1);
      } else if (temp.equals("*") || temp.equals("/")) {
        goOper(temp, 2);
      } else if (temp.equals(")")) {
        goParen();
      } else {
        output.add(temp);
      }
    }

    while (!theStackP.isEmpty()) {
      output.add(theStackP.pop());
    }
  }

  /**
   * add sting in stackP into output if there's no bracket in stack
   */
  private void goParen() {

    while (!theStackP.isEmpty()) {

      String s = theStackP.pop();
      if (s.equals("("))
        break;
      output.add(s);
    }

  }

  /**
   * helper method that process operations in level
   * 
   * @param temp -expression piece
   * @param i    - level limit
   */
  private void goOper(String temp, int i) {

    while (!theStackP.isEmpty()) {
      String s = theStackP.pop();
      if (s.equals("(")) {
        theStackP.push(s);
        break;
      } else {
        int j;

        if (s.equals("+") || s.equals("-")) {
          j = 1;
        } else {
          j = 2;
        }

        if (j < i) {
          theStackP.push(s);
          break;
        } else {
          output.add(s);
        }
      }
    }
    theStackP.push(temp);
  }
}
