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

import java.util.ArrayList;

/**
 * This class arranges the expression and formulates it into the expression that
 * the SimpleCalculator can calculate
 * 
 * @author Jesse, Archer
 *
 */
public class Calculator {

  /**
   * Handling complex expressions
   * 
   * @param  expression
   * @return            String
   */
  public static String calcul(String expression) {

    // Handling Absolute values
    expression = expression.replaceAll("\\|\\-?(.+)\\|", "$1");

    // Handling PI
    expression = expression.replaceAll("\\u03c0", String.valueOf(Math.PI));

    // Handling e
    expression = expression.replaceAll("e", String.valueOf(Math.E));

    String result = String.valueOf(calculate(expression));

    return result.endsWith(".0") ? result.replace(".0", "") : result;
  }

  /**
   * Build up calculation expression and calculate result.
   * 
   * @param  expression String math expression
   * @return            result after calculation
   */
  private static double calculate(String expression) {

    expression = expression.replaceAll("(\\d+)\\(", "$1*(");

    if (expression.matches("\\-?\\d+"))
      return Double.valueOf(expression);
    ArrayList<String> s = new ArrayList<String>();
    char[] chs = expression.toCharArray();
    String temp = "";
    boolean tag = false;
    for (int i = 0; i < chs.length; i++) {
      if (chs[i] - '0' >= 0 && chs[i] - '0' <= 9) {
        temp += chs[i];

      } else if (chs[i] == '.') {
        temp += chs[i];

      } else if (chs[i] == ' ') {
        if (temp.length() != 0)
          s.add(temp);
        temp = "";

      } else if (chs[i] == '-') {
        if (temp.length() != 0)
          s.add(temp);
        temp = "-";
        if (chs[i + 1] == '(') {
          if (s.get(s.size() - 1).equals("/")
              || s.get(s.size() - 1).equals("*")) {
            s.add("(");
            tag = true;
          } else if (s.get(s.size() - 1).equals("-")) {
            s.set(s.size() - 1, "+");
            s.add("(");
          } else if (s.get(s.size() - 1).equals(")")
              || s.get(s.size() - 1).matches("\\d+")) {
            s.add("-");
            s.add("(");
          } else {
            s.add("0");
            s.add("-");
            s.add("(");
          }
          temp = "";
          i++;

        } else if (chs[i + 1] - '0' >= 0 && chs[i + 1] - '0' <= 9) {
          if (s.get(s.size() - 1).matches("\\d+")) {
            s.add("+");
          } else {
            continue;
          }
        } else {
          if (temp.length() != 0)
            s.add(temp);
          temp = "";
        }

      } else if (chs[i] == ')' && tag) {
        s.add(temp);
        temp = "";
        s.add(")");
        s.add("*");
        s.add("-1");
        tag = false;
      } else {
        if (temp.length() != 0)
          s.add(temp);
        temp = "";
        temp += chs[i];
        s.add(temp);
        temp = "";
      }
    }
    if (temp.length() != 0)
      s.add(temp);
    String[] spliter = s.toArray(new String[0]);
    SimpleCalculator cal = new SimpleCalculator(spliter);
    double result = cal.doCal();
    return result;
  }
}


