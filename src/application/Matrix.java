package application;

import com.sun.corba.se.impl.orb.NormalDataCollector;

/**
 * This class represents a Matrix and defines required operation needed for a Matrix
 * 
 * @author Jesse, Houming Chen
 *
 */
public class Matrix implements MatrixADT {

  private Numeric[][] entry;

  /**
   * This constructor construct a matrix with a given number of rows and a given number of columns.
   * All entries of the matrix created by this constructor will be zero.
   * 
   * @param numberOfRow the given number of rows
   * @param numberOfColumn the given number of columns
   */
  public Matrix(int numberOfRow, int numberOfColumn) {
    entry = new Numeric[numberOfRow][numberOfColumn];
    for (int i = 0; i < numberOfRow; i++)
      for (int j = 0; j < numberOfColumn; j++)
        entry[i][j] = new Numeric(0);
  }

  /**
   * Constructor of the matrix with Integers
   * 
   * @param row the row the matrix has
   * @param column the column the matrix has
   * @param content the content
   */

  /**
   * This constructor construct a matrix with a given 2D array of Number as content of the matrix.
   * 
   * @param content a 2D array of Number as content of the matrix
   */
  public Matrix(Number[][] content) {
    entry = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        entry[i][j] = new Numeric(content[i][j]);
  }

  /**
   * This constructor construct a matrix with a given 2D array of Numeric as content of the matrix.
   * 
   * @param content a 2D array of Numeric as content of the matrix
   */
  public Matrix(Numeric[][] content) {
    entry = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      entry[i] = content[i].clone();
  }

  /**
   * A private helper method that generate a n*n identity matrix
   * 
   * @param n a given integer to represent the number of rows and colomns of the identity matirx
   * @return a n*n identity matirx
   */
  private static Matrix identityMatrixWithSizeOf(int n) {
    Numeric[][] identityMatirxEntries = new Numeric[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        if (i == j)
          identityMatirxEntries[i][j] = new Numeric(1);
        else
          identityMatirxEntries[i][j] = new Numeric(0);
    return new Matrix(identityMatirxEntries);
  }

  @Override
  public int getNumberOfColumn() {
    return entry[0].length;
  }

  @Override
  public int getNumberOfRow() {
    return entry.length;
  }

  @Override
  public Numeric getEntry(int row, int column) {
    return entry[row][column];
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < entry.length; i++) {
      for (int j = 0; j < entry[0].length; j++)
        string += entry[i][j].toString() + " ";
      string += '\n';
    }
    return string;
  }

  @Override
  protected Matrix clone() {
    return new Matrix(entry);
  }
  
  @Override
  public Matrix transpose() {
    Numeric[][] newEntiry = new Numeric[getNumberOfColumn()][getNumberOfRow()];
    for (int i = 0; i < entry.length; i++)
      for (int j = 0; j < entry[i].length; j++)
        newEntiry[j][i] = entry[i][j];
    return new Matrix(newEntiry);
  }

  /**
   * A private helper method that checks whether the given matrix is able to be added on this
   * matrix. If the given matrix cannot be added, it will throw a MatrixDimensionsMismatchException,
   * otherwise nothing would happen.
   * 
   * @param other the given matrix
   * @throws MatrixDimensionsMismatchException if the given matrix cannot be added on this matrix.
   */
  private void additionCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Different number of rows");
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Different number of columns");
  }

  @Override
  public Matrix add(MatrixADT other) throws MatrixDimensionsMismatchException {
    additionCheck(other);
    Matrix answerMatrix = new Matrix(this.entry);
    for (int i = 0; i < answerMatrix.getNumberOfRow(); i++)
      for (int j = 0; j < answerMatrix.getNumberOfColumn(); j++)
        answerMatrix.entry[i][j] = answerMatrix.entry[i][j].add(other.getEntry(i, j));
    return answerMatrix;
  }

  @Override
  public Matrix subtract(MatrixADT other) throws MatrixDimensionsMismatchException {
    additionCheck(other);
    Matrix answer = new Matrix(this.entry);
    for (int i = 0; i < answer.getNumberOfRow(); i++)
      for (int j = 0; j < answer.getNumberOfColumn(); j++)
        answer.entry[i][j] = answer.entry[i][j].subtract(other.getEntry(i, j));
    return answer;
  }

  /**
   * A private helper method that checks whether the given matrix is able to be multiplied on this
   * matrix. If the given matrix cannot be multiplied, it will throw a
   * MatrixDimensionsMismatchException, otherwise nothing would happen.
   * 
   * @param other the given matrix
   * @throws MatrixDimensionsMismatchException if the given matrix cannot be multiplied on this
   *         matrix.
   */
  private void multipicationCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Cannot support multiplication");
  }

  @Override
  public Matrix multiply(MatrixADT other) throws MatrixDimensionsMismatchException {
    multipicationCheck(other);
    Matrix answer = new Matrix(this.getNumberOfRow(), other.getNumberOfColumn());
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < other.getNumberOfColumn(); j++)
        for (int k = 0; k < this.getNumberOfColumn(); k++)
          answer.entry[i][j] =
              answer.entry[i][j].add(this.getEntry(i, k).multiply(other.getEntry(k, j)));
    return answer;
  }

  /**
   * 
   * A private helper method to get the size of the square matrix.
   * 
   * For example, a n*n square matrix will return n.
   * 
   * @return n, which is not only the number of rows but also the number of columns of the square
   *         matrix.
   * @throws MatrixDimensionsMismatchException if the matrix is not a square matrix.
   */
  private int getSizeOfSquareMatrix() throws MatrixDimensionsMismatchException {
    if (getNumberOfRow() != getNumberOfColumn()) {
      throw new MatrixDimensionsMismatchException("The matrix is nor square.");
    }
    return getNumberOfRow();
  }

  public Matrix helperpow(int n) {
    if (n == 1)
      return new Matrix(entry);
    try {
      Matrix matrixPowHalfN = helperpow(n / 2);
      if (n % 2 == 0)
        return matrixPowHalfN.multiply(matrixPowHalfN);
      if (n % 2 == 1)
        return this.multiply(matrixPowHalfN.multiply(matrixPowHalfN));
    } catch (MatrixDimensionsMismatchException e) {
      // Unexpected, since it must be a square matrix.
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Matrix pow(int n) throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if(n == 0)
      return identityMatrixWithSizeOf(N);
    else if(n > 0)
      return helperpow(n);
    else if(n < 0)
      return inverse().helperpow(-n);
    return null;
  }

  /**
   * A private helper method that get the absolute value of a Numeric number
   * 
   * @param n a given numeric number
   * @return the absolute value of n.
   */
  private static Numeric abs(Numeric n) {
    if (n.compareTo(new Numeric(0)) < 0)
      return new Numeric(0).subtract(n);
    return n;
  }

  /**
   * A private helper method that swap two rows of the matrix. That is, swap rowX and rowY.
   * 
   * @param rowX the index of the first row
   * @param rowY the index of the second row
   */
  private void swapRow(int rowX, int rowY) {
    int M = getNumberOfColumn();
    for (int i = 0; i < M; i++) {
      Numeric tmp = entry[rowX][i];
      entry[rowX][i] = entry[rowY][i];
      entry[rowY][i] = tmp;
    }
  }

  /**
   * A pivate helper method that do partial pivoting at kth row, return whether the row is swapped
   * 
   * @param k the row to do partial pivoting
   * @return true if the row is swapped
   * @throws SingularException if singular
   */
  private boolean partialPivoting(int k) throws SingularException {
    int pivotRow = k;
    Numeric pivotElement = entry[pivotRow][k];
    for (int i = k + 1; i < getNumberOfRow(); i++)
      if (abs(entry[i][k]).compareTo(pivotElement) > 0) {
        pivotElement = entry[i][k];
        pivotRow = i;
      }
    if (pivotElement.compareTo(new Numeric(0)) == 0)
      throw new SingularException();
    if (pivotRow != k) {
      swapRow(k, pivotRow);
      return true;
    }
    return false;
  }

  /**
   * Do the forward elimination of the Gaussian Elimination. That is, eliminate the matrix to an
   * Echelon form.
   * 
   * @throws SingularException
   * @return true if there are odd number of row swaps, false otherwise.
   */
  public boolean forwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    boolean signChanged = false;
    for (int k = 0; k < N; k++) {
      signChanged = signChanged | partialPivoting(k);
      for (int i = k + 1; i < N; i++) {
        Numeric f = entry[i][k].dividedBy(entry[k][k]);
        for (int j = 0; j < M; j++)
          entry[i][j] = entry[i][j].subtract(entry[k][j].multiply(f));
        entry[i][k] = new Numeric(0);
      }
    }
    return signChanged;
  }

  /**
   * Do the backward elimination of the Gaussian Elimination. That is, eliminate the Echelon form.
   * 
   * @throws SingularException
   */
  public void backwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int k = N - 1; k >= 0; k--) {
      for (int i = 0; i < k; i++) {
        Numeric f = entry[i][k].dividedBy(entry[k][k]);
        for (int j = 0; j < M; j++)
          entry[i][j] = entry[i][j].subtract(entry[k][j].multiply(f));
        entry[i][k] = new Numeric(0);
      }
    }
  }

  /**
   * Simplify the matrix after elimination.
   */
  public void simplifyAfterElimination() {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int i = 0; i < N; i++) {
      Numeric f = entry[i][i];
      for (int j = 0; j < M; j++)
        entry[i][j] = entry[i][j].dividedBy(f);
    }
  }

  /**
   * 
   * A private helper method that gets a submatrix of the matrix. It will get the submatrix with
   * rows from the given start row to the given end row and with columns from the given start column
   * to the given end column.
   * 
   * The startRow and the startColumn will be included, but the endRow and endColumn will not be
   * included
   * 
   * @param startRow the given start row of the matrix to get the submatirx
   * @param endRow the given end row of the matrix to get the submatirx
   * @param startColumn the given start column of the matrix to get the submatirx
   * @param endColumn the given end column of the matrix to get the submatirx
   * @return a Matrix object which is the submatrix constructed by the given parameters.
   */
  private Matrix subMatrix(int startRow, int endRow, int startColumn, int endColumn) {
    Numeric[][] newEntires = new Numeric[endRow - startRow][endColumn - startColumn];
    for (int i = 0; i < newEntires.length; i++)
      for (int j = 0; j < newEntires[i].length; j++)
        newEntires[i][j] = entry[startRow + i][startColumn + j];
    return new Matrix(newEntires);
  }

  /**
   * A private helper method that connect another matrix to this matrix to construct a new augmented
   * matrix.
   * 
   * The given matrix must have the same number of rows with this matrix.
   * 
   * @param other a given matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *         rows with this matrix.
   */
  private Matrix augmentMatirx(Matrix other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Must have same number of rows");
    int N = getNumberOfRow();
    int M1 = this.getNumberOfColumn();
    int M2 = other.getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries = new Numeric[N][M1 + M2];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
        augmentedMatrixEntries[i][j + M1] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  @Override
  public Matrix inverse() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Matrix augmentedMatrix = augmentMatirx(identityMatrixWithSizeOf(N));
    try {
      augmentedMatrix.forwardElimination();
      augmentedMatrix.backwardElimination();
      augmentedMatrix.simplifyAfterElimination();
    } catch (SingularException singularException) {
      throw new ArithmeticException("The marix is not invertible!");
    }
    return augmentedMatrix.subMatrix(0, N, N, 2 * N);
  }

  @Override
  public Numeric determinant() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Matrix answerMatrix = new Matrix(entry);
    boolean signChanged = false;
    try {
      signChanged = answerMatrix.forwardElimination();
      answerMatrix.backwardElimination();
    } catch (SingularException singularException) {
      return new Numeric(0);
    }
    Numeric ansNumeric = new Numeric(1);
    for (int i = 0; i < N; i++)
      ansNumeric = ansNumeric.multiply(answerMatrix.getEntry(i, i));
    if (signChanged)
      ansNumeric = new Numeric(0).subtract(ansNumeric);
    return ansNumeric;
  }
  
  /**
   * Calculate the norm of the matrix.
   * @return the norm of the matrix.
   */
  private Numeric Norm() {
    Numeric ans = new Numeric(0);
    for (int i = 0; i < entry.length; i++) {
      for (int j = 0; j < entry[0].length; j++) {
        ans = ans.add(entry[i][j].multiply(entry[i][j]));
      }
    }
    return ans.sqrt();
  }

  @Override
  public Numeric eigenValue() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    return null;
  }
}
