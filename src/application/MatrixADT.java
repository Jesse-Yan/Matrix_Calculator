package application;

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
   * @param row    - row number where entry is
   * @param column - column where entry is
   * @return - a Numeric Object that represent entry
   */
  Numeric getEntry(int row, int column);

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
   * Do Gaussian Elimination (reduce Matrix into leading ones)
   * 
   * @return - Result Matrix
   * @throws MatrixDimensionsMismatchException - when properties of Matrix mismatch
   */
  MatrixADT gaussianElimination();

  /**
   * inverse the matrix
   * 
   * @return matrix that been inverted
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  MatrixADT inverse()  throws MatrixDimensionsMismatchException ;

  /**
   * get determinant of matrix
   * 
   * @return matrix determinant
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   */
  Numeric getDeterminant() throws MatrixDimensionsMismatchException;

}
