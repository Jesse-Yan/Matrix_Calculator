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
   * @param row - row number where entry is
   * @param column - column where entry is
   * @return - a Numeric Object that represent entry
   */
  Numeric getEntry(int row, int column);

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
   * Multiply Matrix by a constant number. That is, every entries of this matrix should be
   * multiplied by this constant.
   * 
   * The number can be any type of instances of Number, which means it can be a Integer, or a
   * Double, or a Fraction, or a Numeric, or ...
   * 
   * @param constant - a constant that being multiply to the matrix
   * @return - Result Matrix
   * 
   */
  MatrixADT multiply(Number constant);

  /**
   * Divide the matrix by a constant number. That is, every entries of this matrix should be divided
   * by this constant.
   * 
   * The number can be any type of instances of Number, which means it can be a Integer, or a
   * Double, or a Fraction, or a Numeric, or ...
   * 
   * @param constant - a constant that divide the matrix
   * @return - Result Matrix
   * 
   */
  MatrixADT dividedBy(Number constant);

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
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_multiplication#Powers_of_a_matrix
   */
  MatrixADT pow(int n) throws MatrixDimensionsMismatchException;
  
  Matrix[] LUDecomposition() throws MatrixDimensionsMismatchException;

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
   * @throws ArithmeticException - if the matrix is not invertible.
   * 
   * @see https://en.wikipedia.org/wiki/Invertible_matrix
   */
  MatrixADT inverse() throws MatrixDimensionsMismatchException;

  /**
   * get determinant of matrix. The given matrix must be a square matrix.
   * 
   * @return the determinant of the matrix
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Determinant
   */
  Numeric determinant() throws MatrixDimensionsMismatchException;

  /**
   * 
   * QR decomposition. The given matrix must be a square matrix.
   * 
   * @return an array of length 2. The first is Q, and the second is R.
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition
   */
  Matrix[] QRDecomposition() throws MatrixDimensionsMismatchException;

  /**
   * Get the EigenValues of the matrix. The given matrix must be a square matrix.
   * 
   * @return matrix determinant
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Eigenvalues_and_eigenvectors
   */
  Numeric[] eigenValues() throws MatrixDimensionsMismatchException;

}
