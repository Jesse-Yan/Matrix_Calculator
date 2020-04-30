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
import java.util.Arrays;
import java.util.List;

/**
 * This class defines required methods to do calculation on Matrix. Matrix are represented by
 * String[][], and numbers are represented by a single String.
 * 
 * 
 * @author Jesse, Houming Chen
 *
 */
public class MatrixCalculator {

  /**
   * 
   * Defines the mode of the Calculator.
   * 
   * The Calculator has two modes - FRACTION mode and DECIMAL mode, which decides the format of
   * output. The default mode will be FRACTION mode.
   * 
   * In FRACTION mode, all outputs are in integer or fraction format whenever they can be
   * represented by integers or fractions. (Warning: there still might be decimal numbers!)
   * 
   * In DECIMAL mode, all outputs are in decimal format. (No integers! 0 will be printed as 0.0)
   * 
   * 
   * However, this is not actually used in our program. In our program, the PrintMode of every
   * MatrixCalculator instance are always FRACTION. The DECIMAL mode might be useful for the future
   * development.
   * 
   * @author Houming Chen
   *
   */
  public enum PrintMode {
    FRACTION, // Will give the result in fraction
    DECIMAL // Will give the result in decimal
  }

  /**
   * The mode of this calculator.
   */
  public PrintMode printMode;

  /**
   * The reference of two 2D string arrays.
   */
  public String[][] matrix1, matrix2;

  /**
   * Construct the MatrixCalculator by only giving the first matrix. The mode is set as FRACTION by
   * default.
   * 
   * @param matrix1 the references of a String[][] as the first matrix
   */
  public MatrixCalculator(String[][] matrix1) {
    this(matrix1, null, PrintMode.FRACTION);
  }

  /**
   * Construct the MatrixCalculator by only giving the first matrix and a given mode.
   * 
   * @param matrix1 the references of a String[][] as the first matrix
   * @param mode    a given mode
   */
  public MatrixCalculator(String[][] matrix1, PrintMode mode) {
    this(matrix1, null, mode);
  }

  /**
   * Construct the MatrixCalculator by two references of 2D Sting arrays for the two matrices. The
   * mode is set as FRACTION by default.
   * 
   * @param matrix1 the references of a String[][] as the first matrix
   * @param matrix2 the references of a String[][] as the second matrix
   */
  public MatrixCalculator(String[][] matrix1, String[][] matrix2) {
    this(matrix1, matrix2, PrintMode.FRACTION);
  }

  /**
   * Construct the MatrixCalculator by two references of 2D Sting arrays for the two matrices and a
   * given mode.
   * 
   * @param matrix1 the references of a String[][] as the first matrix
   * @param matrix2 the references of a String[][] as the second matrix
   * @param mode    a given mode
   */
  public MatrixCalculator(String[][] matrix1, String[][] matrix2, PrintMode mode) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.printMode = mode;
  }


  /**
   * Return the sum matrix of matrix1 and matrix2.
   * 
   * @return the sum of the two matrix, which is represented by a String[][].
   * @throws MatrixDimensionsMismatchException if matrix1 and matrix2 cannot be added due to
   *                                           different number of rows or columns
   */
  public String[][] add() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.add(secondMatrix);
    return properFormatted(answeMatrix);
  }

  /**
   * Return the difference matrix of matrix1 and matrix2. (matrix1 - matrix2)
   * 
   * @return the difference of the two matrix (matrix1 - matrix2), which is represented by a
   *         String[][].
   * @throws MatrixDimensionsMismatchException if matrix1 cannot subtract matrix2 due to different
   *                                           number of rows or columns
   */
  public String[][] subtract() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.subtract(secondMatrix);
    return properFormatted(answeMatrix);
  }

  /**
   * Return the product matrix of matrix1 and matrix2. (matrix1 * matrix2)
   * 
   * @return the product of the two matrix (matrix1 * matrix2), which is represented by a
   *         String[][].
   * @throws MatrixDimensionsMismatchException if matrix1 cannot multiply matrix2 because matrix1's
   *                                           number of rows doesn't equal to matrix2's number of
   *                                           columns.
   */
  public String[][] multiply() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.multiply(secondMatrix);
    return properFormatted(answeMatrix);
  }

  /**
   * Return the determinant of matrix1, which is represented by a String.
   * 
   * @return the determinant of matrix1, which is represented by a String.
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   */
  public String getDeterminant() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.determinant());
  }

  /**
   * Return the inverse of matrix1.
   * 
   * @return the inverse of matrix1, which is represented by a String[][].
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   * @throws MatrixArithmeticException         - if matrix1 is not invertible.
   */
  public String[][] getInverse()
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.inverse());
  }

  /**
   * 
   * Return the QR decomposition of matrix1, which is represented by a List of String[][]. The first
   * element in the list (index 0) will be Q, and the second element in the list (index 1) will be
   * R.
   * 
   * @return the QR decomposition of matrix1
   * @throws MatrixDimensionsMismatchException - if not a square matrix.
   * @throws MatrixArithmeticException         - Cannot do QR decomposition
   */
  public List<String[][]> getQRDecomposition() {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix[] qrResult = firstMatrix.QRDecomposition();
    ArrayList<String[][]> answer = new ArrayList<String[][]>();
    answer.add(properFormatted(qrResult[0]));
    answer.add(properFormatted(qrResult[1]));
    return answer;
  }

  /**
   * 
   * This method will try to do LU decomposition of matrix 1. If LU decomposition is impossible, it
   * will conduct a LUP decomposition. The result will be represented by a List of String[][].
   * 
   * If LU decomposition is done, The first element in the list (index 0) will be L, and the second
   * element in the list (index 1) will be U.
   * 
   * If LUP decomposition is done, The first element in the list (index 0) will be L, and the second
   * element in the list (index 1) will be U, the third element (index 2) will be P.
   * 
   * 
   * @return the the LUP decomposition of matrix1
   * @throws MatrixDimensionsMismatchException - if not a square matrix.
   */
  public List<String[][]> getLUPDecomposition() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix[] lupResult = firstMatrix.LUPDecomposition();
    ArrayList<String[][]> answer = new ArrayList<String[][]>();
    answer.add(properFormatted(lupResult[0]));
    answer.add(properFormatted(lupResult[1]));
    if (lupResult[2] != null)
      answer.add(properFormatted(lupResult[2]));
    return answer;
  }

  /**
   * Return the trace of matrix1, which is represented by a String.
   * 
   * @return the trace of matrix1, which is represented by a String.
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   */
  public String getTrace() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.trace());
  }

  /**
   * Return the nullity of matrix1.
   * 
   * @return the nullity of matrix1, which is represented by a String.
   */
  public String getNullity() {
    Matrix firstMatrix = new Matrix(matrix1);
    return firstMatrix.nullity() + "";
  }

  /**
   * Return the matrix1 after Guassian-Elimination, which is represented by a String[][].
   * 
   * @return the the matrix1 after Guassian-Elimination, which is represented by a String[][]
   */
  public String[][] getGuassianElimination() {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.gussianElimination());
  }

  /**
   * 
   * Get the Cholesky Decomposition of the matrix. The result will be represented by a List of
   * length 2 of String[][].
   * 
   * @return a List of length 2 of String[][] representing the result
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - if the matrix is not symmetric.
   */
  public List<String[][]> getCholeskyDecomposition()
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix[] choleskyDecomposition = firstMatrix.choleskyDecomposition();
    ArrayList<String[][]> answer = new ArrayList<String[][]>();
    answer.add(properFormatted(choleskyDecomposition[0]));
    answer.add(properFormatted(choleskyDecomposition[1]));
    return answer;
  }

  /**
   * Return the getEigenValues of matrix1.
   * 
   * @return the getEigenValues of matrix1, which is represented by a String[][]
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   */
  public String getEigenValues() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    return Arrays.toString(properFormatted(firstMatrix.eigenvalues()));
    // return properFormatted(firstMatrix.eigenvalues()); // maybe useful in the future.
  }

  /**
   * Return the rank of matrix1.
   * 
   * @return the rank of matrix1, which is represented by a String.
   */
  public String getRank() {
    Matrix firstMatrix = new Matrix(matrix1);
    return firstMatrix.rank() + "";
  }

  /**
   * Return the transpose of matrix1.
   * 
   * @return the transpose of matrix1, which is represented by a String[][].
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   */
  public String[][] getTranspose() {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.transpose());
  }

  /**
   * Return matrix1 to the power of n, where n is a given integer.
   * 
   * @param n a given integer
   * @return the result of matrix1 to the power of n.
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   * @throws MatrixArithmeticException         - when the given n is negative but matrix does not
   *                                           have an inverse
   */
  public String[][] getPow(int n)
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.pow(n));
  }

  /**
   * A helper method that convert a Matrix instance in to a String[][]. The conversion will be done
   * in the right format according to the {@link MatrixCalculator#printMode}.
   * 
   * @param matrix a given Matrix Instance
   * @return a String[][] converted from this Matrix Instance for output.
   */
  private String[][] properFormatted(Matrix matrix) {
    if (printMode == PrintMode.DECIMAL)
      return matrix.toDecimalStringMatrix();
    else
      return matrix.toStringMatrix();
  }

  /**
   * A helper method that convert a Numeric instance in to a String. The conversion will be done in
   * the right format according to the {@link MatrixCalculator#printMode}.
   * 
   * @param number a given Numeric Instance
   * @return a String converted from this Numeric Instance for output.
   */
  private String properFormatted(Numeric number) {
    if (printMode == PrintMode.DECIMAL)
      return number.castToDouble().toString();
    else
      return number.toString();
  }

  /**
   * A helper method that convert an array of Numeric instance in to a String[]. The conversion will
   * be done in the right format according to the {@link MatrixCalculator#printMode}.
   * 
   * @param number an array of Numeric instance
   * @return a String[] converted from this array of Numeric instance for output.
   */
  private String[] properFormatted(Numeric[] number) {
    String[] ansStrings = new String[number.length];
    for (int i = 0; i < number.length; i++) {
      if (printMode == PrintMode.DECIMAL)
        ansStrings[i] = number[i].castToDouble().toString();
      else
        ansStrings[i] = number[i].toString();
    }
    return ansStrings;
  }
}
