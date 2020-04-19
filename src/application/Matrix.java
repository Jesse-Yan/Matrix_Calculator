package application;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

/**
 * This class represents a Matrix and defines required operation needed for a Matrix
 * 
 * @author Jesse
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
  public MatrixADT subtract(MatrixADT other) throws MatrixDimensionsMismatchException {
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
  public MatrixADT multiply(MatrixADT other) throws MatrixDimensionsMismatchException {
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
   * Get the absolute value of a Numeric number
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
   * Swap two rows of the matrix. That is, swap rowX and rowY.
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
   * Do partial pivoting at kth row, return whether the row is swapped
   * 
   * @param k the row to do partial pivoting
   * @return true if the row is swapped
   * @throws SingularException if singular
   */
  private boolean partialPivoting(int k) throws SingularException {
    int indexOfMaxPivot = k;
    Numeric maxPivot = entry[indexOfMaxPivot][k];

    for (int i = k + 1; i < getNumberOfRow(); i++)
      if (abs(entry[i][k]).compareTo(maxPivot) > 0) {
        maxPivot = entry[i][k];
        indexOfMaxPivot = i;
      }

    if (maxPivot.compareTo(new Numeric(0)) == 0)
      throw new SingularException();

    if (indexOfMaxPivot != k) {
      swapRow(k, indexOfMaxPivot);
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

  @Override
  public MatrixADT gaussianElimination() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixADT inverse() throws MatrixDimensionsMismatchException {
    if (getNumberOfRow() != getNumberOfColumn()) {
      throw new MatrixDimensionsMismatchException("The matrix should be a square");
    }
    int N = getNumberOfRow();
    Numeric[][] augmentedMatrixEntries = new Numeric[N][2 * N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
        if (i == j) {
          augmentedMatrixEntries[i][j + N] = new Numeric(1);
        } else {
          augmentedMatrixEntries[i][j + N] = new Numeric(0);
        }
      }
    }
    Matrix augmentedMatrix = new Matrix(augmentedMatrixEntries);
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
  public Numeric getDeterminant() throws MatrixDimensionsMismatchException {
    if (getNumberOfRow() != getNumberOfColumn()) {
      throw new MatrixDimensionsMismatchException("The matrix should be a square");
    }
    Matrix answerMatrix = new Matrix(entry);
    boolean signChanged = false;
    try {
      signChanged = answerMatrix.forwardElimination();
      answerMatrix.backwardElimination();
    } catch (SingularException singularException) {
      return new Numeric(0);
    }
    Numeric ansNumeric = new Numeric(1);
    for (int i = 0; i < answerMatrix.getNumberOfColumn(); i++)
      ansNumeric = ansNumeric.multiply(answerMatrix.getEntry(i, i));
    if (signChanged)
      ansNumeric = new Numeric(0).subtract(ansNumeric);
    return ansNumeric;
  }
}
