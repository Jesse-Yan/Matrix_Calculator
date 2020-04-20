package application;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * The ADT for matrix.
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
   * @param row - row number where entry is
   * @param column - column where entry is
   * @return - a Numeric Object that represent entry
   */
  Numeric getEntry(int row, int column);

  /**
   * Return the transpose of the Matrix.
   * 
   * @return the transpose of the Matrix
   */
  MatrixADT transpose();

  /**
   * ADD two Matrix
   * 
   * @param other - Matrix that being added
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - when properties of Matrix mismatch
   */
  MatrixADT add(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * subtract Matrix by another
   * 
   * @param other - Matrix that being subtract
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - when properties of Matrix mismatch
   */
  MatrixADT subtract(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * multiply Matrix by another
   * 
   * @param other - Matrix that being multiply
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - when properties of Matrix mismatch
   */
  MatrixADT multiply(MatrixADT other) throws MatrixDimensionsMismatchException;

  /**
   * multiply Matrix by a constant number (which can be int, double, Fraction, Numeric ...)
   * 
   * @param constant - a constant that being multiply to the matrix
   * @return - Result Matrix
   */
  MatrixADT multiply(Number constant);

  /**
   * divide the matrix by a constant number (which can be int, double, Fraction, Numeric ...)
   * 
   * @param constant - a constant that divide the matrix
   * @return - Result Matrix
   */
  MatrixADT dividedBy(Number constant);

  /**
   * Get the matrix to the power of n
   * 
   * @param n - given n
   * @return - the matrix to the power of n
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  MatrixADT pow(int n) throws MatrixDimensionsMismatchException;

  /**
   * inverse the matrix
   * 
   * @return matrix that been inverted
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  MatrixADT inverse() throws MatrixDimensionsMismatchException;

  /**
   * get determinant of matrix
   * 
   * @return matrix determinant
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  Numeric determinant() throws MatrixDimensionsMismatchException;

  Matrix[] QRDecomposition() throws MatrixDimensionsMismatchException;

  /**
   * get EigenValue of matrix
   * 
   * @return matrix determinant
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  Numeric eigenValue() throws MatrixDimensionsMismatchException;

}
