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

/**
 * This is an ADT for the class {@link Matrix}. In this program only Matrix class implemented this
 * ADT. The meaning of this ADT is to make the codes more clearer, and make it easier for people to
 * understand the main function of the class {@link Matrix}. All public method in {@link Matrix}
 * except the constructors are declared here.
 * 
 * This class also divides the implementation work form the test work to make a test-driven
 * development easier.
 * 
 * @author Houming Chen, Archer Li
 *
 */
public interface MatrixADT {

  /**
   * Method return the columns number
   * 
   * @return number of columns
   */
  int getNumberOfColumn();

  /**
   * Method return the rows number
   * 
   * @return number of rows
   */
  int getNumberOfRow();

  /**
   * Getter of Matrix entry in certain position
   * 
   * @param row    - row number where entry is
   * @param column - column where entry is
   * @return - a Numeric Object that represent entry
   */
  Numeric getEntry(int row, int column);

  /**
   * Add this matrix by another matrix. The addition should be done by entries.
   * 
   * @param other - Matrix that being added
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - if the two matrix have different dimensions.
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_addition#Entrywise_sum
   */
  MatrixADT add(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * Subtract this matrix by another matrix. The subtraction should be done by entries, just like
   * the matrix addition.
   * 
   * @param other - Matrix that being subtract
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - if the two matrix have different dimensions.
   */
  MatrixADT subtract(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * Multiply this matrix by another matrix. The multiplication should be done by entries.
   * 
   * @param other - Matrix that being multiply
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - when properties of Matrix mismatch
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_multiplication
   */
  MatrixADT multiply(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * Get determinant of matrix. The given matrix must be a square matrix.
   * 
   * @return the determinant of the matrix
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Determinant
   */
  Numeric determinant() throws MatrixDimensionsMismatchException;

  /**
   * Get the inverse of a square matrix. The given matrix must be a square matrix.
   * 
   * If the given matrix is not invertible, an ArithmeticException with message will be thrown.
   * 
   * Warning: Although it is very rare, when entries have extremely large double values, float error
   * might cause the matrix throw wrong ArithmeticException. That is, in extreme cases, an
   * invertible matrix might also throw an ArithmeticException with messages telling that it is not
   * invertible.
   * 
   * @return matrix that been inverted
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - if the matrix is not invertible.
   * 
   * @see https://en.wikipedia.org/wiki/Invertible_matrix
   */
  MatrixADT inverse() throws MatrixDimensionsMismatchException, MatrixArithmeticException;


  /**
   * 
   * QR decomposition.
   * 
   * @return an array of length 2. The first is Q, and the second is R.
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition
   */
  MatrixADT[] QRDecomposition();

  /**
   * 
   * If it is possible to do LU decomposition, this method will do LU decomposition to the matrix.
   * Otherwise, it would do a LUP decomposition.
   * 
   * @return an array of length 3. The first is L, and the second is U. If LU decomposition is done,
   *         the third element in the array will be null, otherwise it will be the P.
   * 
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition
   */
  MatrixADT[] LUPDecomposition() throws MatrixDimensionsMismatchException;

  /**
   * Get the trace of matrix. The given matrix must be a square matrix.
   * 
   * @return the trace of the matrix
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Trace_(linear_algebra)
   */
  Numeric trace() throws MatrixDimensionsMismatchException;

  /**
   * Get the nullity of matrix.
   * 
   * @return the nullity of the matrix
   * 
   * @see https://mathworld.wolfram.com/Nullity.html
   */
  int nullity();


  /**
   * Get the EigenValues of the matrix. The given matrix must be a square matrix.
   * 
   * @return an array of Numeric[], which represents the eigenvalues of the matrix.
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Eigenvalues_and_eigenvectors
   */
  Numeric[] eigenvalues() throws MatrixDimensionsMismatchException;

  /**
   * Get the rank of matrix.
   * 
   * @return the rank of the matrix
   * 
   * @see https://en.wikipedia.org/wiki/Rank_(linear_algebra)
   */
  int rank();

  /**
   * Return the transpose of the Matrix.
   * 
   * For example, transpose of {{1, 2, 3}, {4, 5, 6}} will be {{1, 4}, {2, 5}, {3, 6}}
   * 
   * @return the transpose of the Matrix
   * 
   * @see https://en.wikipedia.org/wiki/Transpose
   */
  MatrixADT transpose();

  /**
   * 
   * Do Guassian-elimination on the matrix, and return the result matrix.
   * 
   * @return the matrix after guassianElimination
   * 
   * @see https://en.wikipedia.org/wiki/Gaussian_elimination
   */
  MatrixADT gussianElimination();

  /**
   * 
   * Do Cholesky-decomposition on a symmetric matrix. That is, decomposed the matrix into the
   * product of a lower triangular matrix and its transpose. Return an an array of length 2 of
   * matrix representing the result, in which the first element(index 0) is the lower triangular
   * matrix and the second element(index 1) is its transpose.
   * 
   * If the matrix is not symmetric, a MatrixArithmeticException with message will be thrown
   * 
   * Warning: this method cannot handle complex cases, so it will throw an ArithmeticException when
   * Cholesky-decomposition cannot be done on the real field.
   * 
   * @return an array of length 2 representing the result
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - if the matrix is not symmetric.
   * @throws ArithmeticException               - if Cholesky-decomposition cannot be done on the
   *                                           real field.
   */
  MatrixADT[] choleskyDecomposition()
      throws MatrixDimensionsMismatchException, MatrixArithmeticException;

  /**
   * Get the matrix to the power of n, the given matrix must be a square matrix.
   * 
   * If n is a positive integer, return the result of this matrix multiplies itself by n times.
   * 
   * IF n is 0, return a square diagonal matrix that has same dimensions with the given square
   * matrix.
   * 
   * If n is a negative integer, return the result of this matrix's transpose multiplies itself by n
   * times.
   * 
   * @param n - given n
   * @return - the matrix to the power of n
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - when n is negative but the matrix is non-invertible
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_multiplication#Powers_of_a_matrix
   */
  MatrixADT pow(int n) throws MatrixDimensionsMismatchException, MatrixArithmeticException;
}
