package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

/**
 * This class represents a Matrix and defines operations needed for a Matrix.
 * 
 * The field of this class only contains an 2D array that stores the entries of this Matrix.
 * 
 * @author Jesse, Houming Chen
 *
 */
public class Matrix implements MatrixADT {

  /*
   * The entries of the matrix
   */
  private Numeric[][] entry;

  /**
   * 
   * Zero Matrix constructor, which constructs a Zero matrix with given numbers of rows and a
   * columns.
   * 
   * All entries of the matrix created by this constructor will be zero. For example, new Matrix(2,
   * 3) will gives {{0, 0, 0}, {0, 0, 0}}
   * 
   * @param numberOfRow    - the given number of rows
   * @param numberOfColumn - the given number of columns
   */
  public Matrix(int numberOfRow, int numberOfColumn) {
    entry = new Numeric[numberOfRow][numberOfColumn];
    for (int i = 0; i < numberOfRow; i++)
      for (int j = 0; j < numberOfColumn; j++)
        entry[i][j] = new Numeric(0);
  }

  /**
   * This constructor constructs a matrix with a given 2D array of {@link Number} as the content
   * entries of the matrix.
   * 
   * See {@link Number} for information about this class. The defined class {@link Numeric} Numeric
   * extends {@link Number}. Classes like Integer, Float, or Double also extends Number.
   * 
   * @param - content a 2D array of Number as content of the matrix
   * 
   * @see https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html
   */
  public Matrix(Number[][] content) {
    entry = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      if (content[i] instanceof Numeric[])
        entry[i] = (Numeric[]) content[i].clone();
      else
        for (int j = 0; j < content[0].length; j++)
          entry[i][j] = new Numeric(content[i][j]);
  }

  /**
   * This constructor constructs a matrix with a given 2D array of String as the content entries of
   * the matrix.
   * 
   * The constructor will automatically convert these String to Numeric. See
   * {@link Numeric#Numeric(String)} for details about how the String is converted into Numeric.
   * 
   * @param content a 2D array of String as content of the matrix
   * 
   */
  public Matrix(String[][] content) {
    entry = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        entry[i][j] = new Numeric(content[i][j]);
  }

  /**
   * A copy constructor that copies the given matrix to construct the new matrix.
   * 
   * @param - other a given Matrix used to construct this matrix
   */
  public Matrix(Matrix other) {
    this(other.entry);
  }

  /**
   * Returns a deepest copy of this matrix, which is exactly the same as this matrix.
   * 
   * @return a copy of matrix.
   */
  protected Matrix copy() {
    return new Matrix(entry);
  }

  /**
   * A private helper method that generate a n*n identity matrix. This means it has 1 on every
   * entries on its diagonal line and has 0 on other entries.
   * 
   * @param - n a given integer to represent the number of rows and colomns of the identity matirx
   * @return a n*n identity matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Identity_matrix
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

  /**
   * Convert this matrix to String. The entries are converted to string by rows. In each row,
   * numbers are separated by a space. Rows are separated with "\n".
   */
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


  /**
   * Convert this matrix to string for Json output. The entries are converted to string by rows. In
   * each row, numbers are separated by a " ". Rows are separated with ",\n".
   * 
   * @return the converted string.
   * 
   * 
   */

  /**
   * Return a 2D array of String to represent the matrix. The 2D array will have exactly the same
   * dimensions with the matrix, and every Numeric values for the entries will be converted to
   * String. See {@link Numeric#toString()} for how the numeric values are converted to String.
   * 
   * @return a 2D array of String to represent the matrix
   */
  public String[][] toStringMatrix() {
    String string[][] = new String[entry.length][];
    for (int i = 0; i < entry.length; i++) {
      string[i] = new String[entry[i].length];
      for (int j = 0; j < entry[i].length; j++)
        string[i][j] = entry[i][j].toString();
    }
    return string;
  }

  /**
   * Return a 2D array of String to represent the matrix. The 2D array will have exactly the same
   * dimensions with the matrix, and every Numeric values for the entries will be converted to
   * String. Unlike the method toStringMatrix() {@link toStringMatrix()}, this method will first
   * cast the values of Numeric into Double state and then convert it to String to make sure the
   * output is in decimals format (not Fraction format). See {@link Numeric#castToDouble()} for how
   * the Numeric instance is casted into Double Numeric State. See {@link Numeric#toString()} for
   * how the numeric values are converted to String.
   * 
   * @return a 2D array of String to represent the matrix in decimal
   */
  public String[][] toDecimalStringMatrix() {
    String string[][] = new String[entry.length][];
    for (int i = 0; i < entry.length; i++) {
      string[i] = new String[entry[i].length];
      for (int j = 0; j < entry[i].length; j++)
        string[i][j] = entry[i][j].castToDouble().toString();
    }
    return string;
  }

  /**
   * return the number of columns.
   */
  @Override
  public int getNumberOfColumn() {
    return entry[0].length;
  }

  /**
   * return the number of rows.
   */
  @Override
  public int getNumberOfRow() {
    return entry.length;
  }

  /**
   * return the value of an entry (in {@link Numeric}}) specified by a given row index and a column
   * index.
   */
  @Override
  public Numeric getEntry(int row, int column) {
    return entry[row][column];
  }

  /**
   * A private helper method that checks whether the given matrix has exactly the same number of
   * rows and the same number of columns with this matrix. If they have exactly the same number of
   * rows and columns, nothing will happen, otherwise this method will throw a
   * MatrixDimensionsMismatchException.
   * 
   * @param other the given matrix
   * @throws MatrixDimensionsMismatchException - if the given matrix do not have the exactly same
   *                                           dimensions with this one.
   */
  private void sameDimensionCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Different number of rows");
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Different number of columns");
  }

  /**
   * Receives another object (which should be a matrix), and check whether the given matrix is equal
   * to this matrix. Return true if the given object is a Matrix, and matrices have exactly same
   * dimension, and all entries are equal. Return false otherwise.
   * 
   * Two decided whether two decimals are equal, they will be compared according to a specific
   * number of significant digits, which is decided by
   * {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Matrix) {
      try { // Check whether the two matrices have exactly the same dimenstions.
        sameDimensionCheck(((Matrix) obj));
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        return false;
      }
      for (int i = 0; i < entry.length; i++)
        for (int j = 0; j < entry[0].length; j++)
          if (!entry[i][j].equals(((Matrix) obj).entry[i][j]))
            return false;
      return true;
    }
    return false;
  }

  /**
   * Receives another object (which should be a matrix), and check whether the given matrix is equal
   * to this matrix mathematically. Return true if the given object is a Matrix, and matrices have
   * exactly same dimension, and all entries are equal. Return false otherwise.
   * 
   * The word "mathematically" means that, unlike the method {@link Matrix#equals(Object)}, this
   * method will always return false if any of the entries is a decimal number. That is, only when
   * this matrix and the given matrix is in Fraction or Integer State, there are possibilities that
   * the method will compare the two object and returns true if they are equal.
   * 
   * @param obj a given object which is used to compare with this matrix
   * @return true if the given object is a Matrix, and matrices have exactly same dimension, and all
   *         entries are equal, false otherwise.
   */
  private boolean mathematicallyEquals(Matrix obj) {
    if (obj instanceof Matrix) {
      try { // Check whether the two matrices have exactly the same dimensions.
        sameDimensionCheck(((Matrix) obj));
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        return false;
      }
      for (int i = 0; i < entry.length; i++)
        for (int j = 0; j < entry[0].length; j++)
          if (!entry[i][j].mathematicallyEquals(((Matrix) obj).entry[i][j]))
            return false;
      return true;
    }
    return false;
  }


  /**
   * Add this matrix by another matrix. The addition should be done by entries.
   * 
   * @param other - Matrix that is going to be added on this matrix
   * @return The result Matrix after addition
   * @throws MatrixDimensionsMismatchException - if the two matrix have different dimensions.
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_addition#Entrywise_sum
   */
  @Override
  public Matrix add(MatrixADT other) throws MatrixDimensionsMismatchException {
    sameDimensionCheck(other);
    Matrix answerMatrix = copy();
    for (int i = 0; i < answerMatrix.getNumberOfRow(); i++)
      for (int j = 0; j < answerMatrix.getNumberOfColumn(); j++)
        answerMatrix.entry[i][j] = answerMatrix.entry[i][j].add(other.getEntry(i, j));
    return answerMatrix;
  }

  /**
   * Subtract this matrix by another matrix. The subtraction should be done by entries, just like
   * the {@link Matrix#add(MatrixADT)}.
   * 
   * @param other - Matrix that is going to be subtracted from this matrix
   * @return The result Matrix after subtraction
   * @throws MatrixDimensionsMismatchException - if the two matrix have different dimensions.
   */
  @Override
  public Matrix subtract(MatrixADT other) throws MatrixDimensionsMismatchException {
    sameDimensionCheck(other);
    Matrix answer = copy();
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
   * @param other - a given matrix whose dimensions are going to be compared with ones of this
   * @throws MatrixDimensionsMismatchException - if the numbers of columns of this matrix doesn't
   *                                           equal to the number of rows of the given matrix,
   *                                           which means the multiplication cannot be done
   *                                           according to the definition of Matrix multiplication.
   */
  private void multipicationCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Cannot support multiplication");
  }

  /**
   * Multiply this matrix by another matrix.
   * 
   * @param other - Matrix that is going to be multiplied with this matrix
   * @return - The result Matrix after multiplication
   * @throws MatrixDimensionsMismatchException - if the numbers of columns of this matrix doesn't
   *                                           equal to the number of rows of the given matrix,
   *                                           which means the multiplication cannot be done
   *                                           according to the definition of Matrix multiplication.
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_multiplication
   */
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
   * A helper method that multiply Matrix by a constant number. That is, every entries of this
   * matrix should be multiplied by this constant.
   * 
   * See {@link Number} for information about this class. The defined class {@link Numeric} Numeric
   * extends {@link Number}. Classes like Integer, Float, or Double also extends Number.
   * 
   * @param constant - a constant that being multiply to the matrix
   * @return The result Matrix after multiplication
   * 
   */
  private Matrix multiply(Number constant) {
    Matrix answer = copy();
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < this.getNumberOfColumn(); j++)
        answer.entry[i][j] = answer.entry[i][j].multiply(constant);
    return answer;
  }

  /**
   * A helper method that divide the matrix by a constant number. That is, every entries of this
   * matrix should be divided by this constant.
   * 
   * See {@link Number} for information about this class. The defined class {@link Numeric} Numeric
   * extends {@link Number}. Classes like Integer, Float, or Double also extends Number.
   * 
   * @param constant - a constant that divide the matrix
   * @return The result Matrix after division
   */
  private Matrix dividedBy(Number constant) {
    Matrix answer = copy();
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < this.getNumberOfColumn(); j++)
        answer.entry[i][j] = answer.entry[i][j].dividedBy(constant);
    return answer;
  }

  /**
   * A private helper method that check whether the matrix is a square matrix. Nothing happens if it
   * is a square matrix, otherwise it will throw a MatrixDimensionsMismatchException with message.
   * 
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   * 
   */
  private void checkSquare() throws MatrixDimensionsMismatchException {
    if (getNumberOfRow() != getNumberOfColumn()) {
      throw new MatrixDimensionsMismatchException("The matrix is nor square.");
    }
  }

  /**
   * 
   * A private helper method to get the size of the square matrix. It will throw a
   * MatrixDimensionsMismatchException with message if the matrix is not square.
   * 
   * For example, a n*n square matrix will return n.
   * 
   * @return n, which is not only the number of rows but also the number of columns of the square
   *         matrix.
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   */
  private int getSizeOfSquareMatrix() throws MatrixDimensionsMismatchException {
    checkSquare();
    return getNumberOfRow();
  }


  /**
   * 
   * A private helper method that receives parameters k and l, and then it will find the row index
   * of the largest number in absolute value on the lth column and with row index equal to or
   * greater than k .
   * 
   * @param k - a given k
   * @param l - a given l
   * @return the index of the largest number on the lth column and with row index equal to or
   *         greater than k
   */
  private int indexOfLargestPivotElement(int k, int l) {
    int pivotRow = k;
    Numeric pivotElement = entry[pivotRow][l];
    for (int i = k + 1; i < getNumberOfRow(); i++)
      if (entry[i][l].abs().compareTo(pivotElement) > 0) {
        pivotElement = entry[i][l];
        pivotRow = i;
      }
    return pivotRow;
  }

  /**
   * 
   * A private helper method that receives parameters k and l, and then it will find the row index
   * of the first number on the lth column and with row index equal to or greater than k.
   * 
   * The "first" means it has the smallest row index among all the possible values.
   * 
   * @param k - a given k
   * @param l - a given l
   * @return the index of the first number on the lth column and with row index equal to or greater
   *         than k
   */
  private int indexOfNextNonZeroPivotElement(int k, int l) {
    for (int i = k; i < getNumberOfRow(); i++)
      if (!entry[i][l].equals(Numeric.of(0)))
        return i;
    return k;
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
   * @param startRow    the given start row of the matrix to get the submatirx
   * @param endRow      the given end row of the matrix to get the submatirx
   * @param startColumn the given start column of the matrix to get the submatirx
   * @param endColumn   the given end column of the matrix to get the submatirx
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
   * A private helper method that connect another matrix to the right of this this matrix to
   * construct a new augmented matrix.
   * 
   * The given matrix must have the same number of rows with this matrix, otherwise a
   * MatrixDimensionsMismatchException with message will be thrown.
   * 
   * @param other a given matrix that is going to be augmented on this matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *                                           rows with this matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Augmented_matrix#To_find_the_inverse_of_a_matrix
   */
  private Matrix augmentMatirx(Matrix other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Must have same number of rows");
    int N = getNumberOfRow();
    int M1 = this.getNumberOfColumn();
    int M2 = other.getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries = new Numeric[N][M1 + M2];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M1; j++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int j = 0; j < M2; j++) {
        augmentedMatrixEntries[i][j + M1] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  /**
   * This is a private helper method. If the matrix is a square matrix, it returns the product of
   * the diagonal, otherwise it throws a MatrixDimensionsMismatchException with message.
   * 
   * @return the product of the diagonal of the matrix
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   */
  private Numeric productOfDiagonal() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Numeric ansNumeric = new Numeric(1);
    for (int i = 0; i < N; i++)
      ansNumeric = ansNumeric.multiply(entry[i][i]);
    return ansNumeric;
  }

  /**
   * Return the trace of the (sum of the diagonal). The given matrix must be a square matrix,
   * otherwise a MatrixDimensionsMismatchException with message will be thrown.
   * 
   * @return the trace of the matrix
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   */
  @Override
  public Numeric trace() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Numeric ansNumeric = new Numeric(0);
    for (int i = 0; i < N; i++)
      ansNumeric = ansNumeric.add(entry[i][i]);
    return ansNumeric;
  }

  /**
   * A private helper method that swap two rows of the matrix, That is, swap rowX and rowY.
   * 
   * Warning: this private method might change the content in the entries!
   * 
   * @param rowX the index of the first row that is going to be swapped with the second row
   * @param rowY the index of the second row that is going to be swapped with the first row
   */
  private void swapRow(int rowX, int rowY) {
    if (rowX != rowY) {
      for (int i = 0; i < getNumberOfColumn(); i++) {
        Numeric tmp = entry[rowX][i];
        entry[rowX][i] = entry[rowY][i];
        entry[rowY][i] = tmp;
      }
    }
  }

  /**
   * 
   * A private helper method that do row swapping to try to make that the element at the given place
   * (specified by a given row and a given column) is a non-zero element.
   * 
   * If the current element is a non-zero element, nothing will happen, otherwise, this method will
   * find the next row that has non-zero element on this column to swap with this row to finish the
   * pivoting.
   * 
   * This method cannot promise the given row must become a non-zero element, if it cannot become a
   * non-zero element through row swapping, nothing will happen.
   * 
   * @param row    - a given row
   * @param column - a given column
   */
  private void swapRowWithNextNonZeroPivotRow(int row, int column) {
    int nextNonZeroPivotRow = indexOfNextNonZeroPivotElement(row, column);
    swapRow(row, nextNonZeroPivotRow);
  }

  /**
   * 
   * A private helper method that do row swapping to try to make that the element at the given place
   * (specified by a given row and a given column) is a non-zero element.
   * 
   * No matter whether the current element is a non-zero element, this method will always find the
   * row that has largest element in absolute value on this column to swap with this row.
   * 
   * This method cannot promise the given row must become a non-zero element, if it cannot become a
   * non-zero element through row swapping, nothing will happen.
   * 
   * @param row    - the given row
   * @param column - the given column
   */
  private void swapRowWithLargestPivotRow(int row, int column) {
    int largestPivotRow = indexOfLargestPivotElement(row, column);
    swapRow(row, largestPivotRow);
  }

  /**
   * 
   * This enum defines the row swapping strategies for elimination. During the Guassian-elimination
   * and LUP decomposition, each pivot used for elimination should be a non-zero element. Therefore,
   * if the row used for current elimination has a zero element on its pivot place, a row swap must
   * happen to make sure that the current row have a non-zero pivot. Therefore, there can be two
   * strategies, the first is swapping row with the next row that has a non-zero pivot whenever the
   * current row has a zero element on its pivot place (see
   * {@link Matrix#partialPivotingWithNextNonZeroPivot(int, int)}), the second is always swapping
   * row with the row that has greatest pivot in absolute value (see
   * {@link Matrix#indexOfLargestPivotElement(int, int)}). While the first strategy usually provides
   * a more understandable result for users (because there are less row swaps), the second strategy
   * usually provides a more precise result with less floating error.
   * 
   * @author Houming Chen
   * 
   * @see https://en.wikipedia.org/wiki/Gaussian_elimination
   *
   */
  private enum RowSwappingStrategy {
    USE_NEXT_NON_ZERO_PIVOT, USE_LARGEST_PIVOT
  }


  /**
   * 
   * A private helper method that do row swapping to try to make that the element at the given place
   * (specified by a given row and a given column) is a non-zero element. It will do row exchanges
   * with the given {@link Matrix.RowSwappingStrategy} to achieve this goal.
   * 
   * This method cannot promise the given row must become a non-zero element, if it cannot become a
   * non-zero element through row swapping, nothing will happen.
   * 
   * @param row    - the given row
   * @param column - the given column
   */
  private void swapRowWithStrategy(int rowIndex, int columnIndex,
      RowSwappingStrategy rowSwappingStrategy) {
    if (rowSwappingStrategy == RowSwappingStrategy.USE_NEXT_NON_ZERO_PIVOT)
      swapRowWithNextNonZeroPivotRow(rowIndex, columnIndex);
    else
      swapRowWithLargestPivotRow(rowIndex, columnIndex);
  }

  /**
   * 
   * 
   * A private helper method that helps to do row swapping by trying to make that the element at the
   * given place (specified by a given row and a given column) is a non-zero element. It will do row
   * exchanges with the given {@link Matrix.RowSwappingStrategy} to achieve this goal. If the given
   * place cannot become a non-zero element through row swapping, a SingularException will be
   * thrown.
   * 
   * Warning: this private method might change the content in the entries!
   * 
   * @param rowIndex            - the row to do partial pivoting
   * @param columnIndex         - the column to do partial pivoting
   * @param rowSwappingStrategy - a given RowSwappingStrategy for the row swapping strategy. see
   *                            {@link Matrix.RowSwappingStrategy} for details.
   * @throws SingularException - if there is not any row that can be swapped with this row to do the
   *                           pivoting
   */
  private void partialPivotingWithStrategy(int rowIndex, int columnIndex,
      RowSwappingStrategy rowSwappingStrategy) throws SingularException {
    swapRowWithStrategy(rowIndex, columnIndex, rowSwappingStrategy);
    if (entry[rowIndex][columnIndex].compareTo(new Numeric(0)) == 0)
      throw new SingularException();
  }

  /**
   * 
   * A private helper method that divides the entire row by a given element on it (which should be
   * the pivot). The row is specified by the first parameter "rowIndex", and the given element
   * (which should be the pivot) is specified by the second parameter "columnIndexOfPivotElement".
   * 
   * Warning: this private method will change the content in the entries!
   * 
   * @param rowIndex                  the given row to do the division.
   * @param columnIndexOfPivotElement the element in the row that this entire row is divided by
   */
  private void divideTheRowByPivotElement(int rowIndex, int columnIndexOfPivotElement) {
    Numeric f = entry[rowIndex][columnIndexOfPivotElement];
    entry[rowIndex][columnIndexOfPivotElement] = Numeric.of(1);
    for (int i = columnIndexOfPivotElement + 1; i < getNumberOfColumn(); i++)
      entry[rowIndex][i] = entry[rowIndex][i].dividedBy(f);
  }

  /**
   * 
   * A private helper that subtract constant times of a given row from other rows to make sure that
   * all the rows except the given row is 0 on the given column, which should be the column of the
   * index of the given row's pivot. The row is specified by the first parameter "rowIndex", and the
   * column is specified by the second parameter "columnIndexOfPivotElement".
   * 
   * Warning: this private method will change the content in the entries!
   * 
   * @param rowIndex                  the given row
   * @param columnIndexOfPivotElement the column of the index of the given row's pivot
   */
  private void eliminateOtherRowsBy(int rowIndex, int columnIndexOfPivotElement) {
    for (int i = 0; i < getNumberOfRow(); i++) {
      if (i == rowIndex)
        continue;
      Numeric f = entry[i][columnIndexOfPivotElement];
      for (int j = 0; j < getNumberOfColumn(); j++) {
        if (j == columnIndexOfPivotElement)
          entry[i][j] = Numeric.of(0);
        else
          entry[i][j] = entry[i][j].subtract(entry[rowIndex][j].multiply(f));
      }
    }
  }


  /**
   * A private helper method to do Gausian-elimination on this matrix. When needed, it will do row
   * exchanges with the given row swapping strategy. See {@link Matrix.RowSwappingStrategy}.
   * 
   * Warning: this private method will change the content in the entries!
   * 
   * @param rowSwappingStrategy - a given RowSwappingStrategy for the row swapping strategy. see
   *                            {@link Matrix.RowSwappingStrategy} for details.
   * @return the number of pivoting times during the Guassian-elimination, that is, the rank of the
   *         matrix.
   */
  private int gussianEliminate(RowSwappingStrategy rowSwappingStrategy) {
    int dif = 0;
    for (int k = 0; k < getNumberOfRow(); k++) {
      boolean success = false;
      int l = k + dif;
      if (l >= getNumberOfColumn())
        return getNumberOfColumn() - dif;
      while (!success) {
        try {
          partialPivotingWithStrategy(k, l, rowSwappingStrategy);
          success = true;
        } catch (SingularException e) {
          dif++;
          l = l + dif;
          if (l >= getNumberOfColumn())
            return getNumberOfColumn() - dif;
        }
      }
      divideTheRowByPivotElement(k, l);
      eliminateOtherRowsBy(k, l);
    }
    return getNumberOfColumn() - dif;
  }

  /**
   * Return the rank of matrix, which is calculated by the rank-nullity theorem. The rank is
   * calculated by using Guassian-elimination method.
   * 
   * @return the rank of the matrix
   * 
   * @see https://en.wikipedia.org/wiki/Rank_(linear_algebra)
   * @see https://en.wikipedia.org/wiki/Rank_(linear_algebra)#Computing_the_rank_of_a_matrix
   */
  public int rank() {
    Matrix answerMatrix = copy();
    return answerMatrix.gussianEliminate(RowSwappingStrategy.USE_LARGEST_PIVOT);
  }

  /**
   * Return the nullity of matrix, which is calculated by the rank-nullity theorem.
   * 
   * @return the nullity of the matrix
   * 
   * @see https://mathworld.wolfram.com/Nullity.html
   * @see https://en.wikipedia.org/wiki/Rank%E2%80%93nullity_theorem
   */
  @Override
  public int nullity() {
    return getNumberOfColumn() - rank();
  }

  /**
   * 
   * Do Guassian-elimination on the matrix, and return the result matrix.
   * 
   * This method will not change the entries of the this matrix.
   * 
   * @return the matrix after guassianElimination
   * 
   * @see https://en.wikipedia.org/wiki/Gaussian_elimination
   */
  @Override
  public Matrix gussianElimination() {
    Matrix answerMatrix = copy();
    answerMatrix.gussianEliminate(RowSwappingStrategy.USE_NEXT_NON_ZERO_PIVOT);
    return answerMatrix;
  }


  /**
   * Return the transpose of the Matrix.
   * 
   * @return the transpose of the Matrix
   * 
   * @see https://en.wikipedia.org/wiki/Transpose
   */
  @Override
  public Matrix transpose() {
    Numeric[][] newEntiry = new Numeric[getNumberOfColumn()][getNumberOfRow()];
    for (int i = 0; i < entry.length; i++)
      for (int j = 0; j < entry[i].length; j++)
        newEntiry[j][i] = entry[i][j];
    return new Matrix(newEntiry);
  }

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
  @Override
  public Matrix[] choleskyDecomposition()
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    if (!this.equals(this.transpose()))
      throw new MatrixArithmeticException(
          "The matrix should be symmetric to do Cholesky-decomposition");
    int N = getSizeOfSquareMatrix();
    Matrix lower = new Matrix(N, N);
    for (int i = 0; i < N; i++) {
      for (int j = 0; j <= i; j++) {
        Numeric sum = Numeric.of(0);
        if (j == i) {
          for (int k = 0; k < j; k++)
            sum = sum.add(lower.entry[j][k].multiply(lower.entry[j][k]));
          lower.entry[j][j] = entry[j][j].subtract(sum).sqrt();
        } else {
          for (int k = 0; k < j; k++)
            sum = sum.add(lower.entry[i][k].multiply(lower.entry[j][k]));
          lower.entry[i][j] = (entry[i][j].subtract(sum)).dividedBy(lower.entry[j][j]);
        }
      }
    }
    return new Matrix[] {lower, lower.transpose()};
  }


  /**
   * A private helper method that connect another matrix to the bottom of this this matrix to
   * construct a new augmented matrix.
   * 
   * The word "augment matrix" usually refers to connect another matrix/vector to the right of the
   * original matrix. While that function is provided by method
   * {@link Matrix#augmentMatirx(Matrix)}.This method performs similar functions but it connect
   * another matrix to the bottom of the original matrix. Therefore, it is called
   * augmentMatirxByExtendingColumns.
   * 
   * The given matrix must have the same number of columns with this matrix.
   * 
   * @param other - a given matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *                                           columns with this matrix.
   */
  private Matrix augmentMatirxByExtendingColumns(Matrix other)
      throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Must have same number of columns");
    int N1 = this.getNumberOfRow();
    int N2 = other.getNumberOfRow();
    int M = getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries = new Numeric[N1 + N2][M];
    for (int j = 0; j < M; j++) {
      for (int i = 0; i < N1; i++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int i = 0; i < N2; i++) {
        augmentedMatrixEntries[i + N1][j] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  /**
   * 
   * This is a private helper method that combines four matrices in to one matrix. For the given
   * four matrices, the first one will be placed on the left top, the second one will be placed on
   * the right top, the third one will be placed on the left bottom, the fourth one will be placed
   * on the right bottom. A MatrixDimensionsMismatchException with message will be thrown if the
   * shape(dimensions) of the provided matrices cannot be combined in this way.
   * 
   * @param leftTop     a given matrix to be the left top part of the combined matrix
   * @param rightTop    a given matrix to be the right top part of the combined matrix
   * @param leftBottom  a given matrix to be the left bottom part of the combined matrix
   * @param rightBottom a given matrix to be the right bottom part of the combined matrix
   * @return the combined matrix
   * @throws MatrixDimensionsMismatchException - if cannot combine due to number of rows or columns
   *                                           mismatches.
   */
  private static Matrix combineMatrix(Matrix leftTop, Matrix rightTop, Matrix leftBottom,
      Matrix rightBottom) throws MatrixDimensionsMismatchException {
    return leftTop.augmentMatirx(rightTop)
        .augmentMatirxByExtendingColumns(leftBottom.augmentMatirx(rightBottom));
  }

  /**
   * A private helper method that implements the LUP decomposition. It calculate the LUP
   * decomposition recursively by using the Crout algorithm. When needed, it will do row exchanges
   * with the given row swapping strategy. See {@link Matrix.RowSwappingStrategy}.
   * 
   * @param rowSwappingStrategy - a given RowSwappingStrategy for the row swapping strategy. see
   *                            {@link Matrix.RowSwappingStrategy} for details.
   * @return An Object array of length 4. The first tree are Matrix type representing the L matrix,
   *         U matrix, and P matrix respectively, the fourth element represents whether there are
   *         odd times of row swaps (which is used in {@link Matrix#determinant}).
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix
   * 
   * @see https://en.wikipedia.org/wiki/LU_decomposition#Crout_and_LUP_algorithms
   */
  private Object[] LUPDecompositionHelper(RowSwappingStrategy rowSwappingStrategy)
      throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if (N == 1)
      return new Object[] {identityMatrixWithSizeOf(1), this.copy(), identityMatrixWithSizeOf(1),
          false};

    Matrix A = copy();

    int i;
    boolean signChanged = false;
    if (rowSwappingStrategy == RowSwappingStrategy.USE_NEXT_NON_ZERO_PIVOT) {
      i = A.indexOfNextNonZeroPivotElement(0, 0);
    } else {
      i = A.indexOfLargestPivotElement(0, 0);
    }
    if (i != 0) {
      A.swapRow(0, i);
      signChanged = true;
    }

    Matrix A_bar11 = A.subMatrix(0, 1, 0, 1);
    Matrix A_bar12 = A.subMatrix(0, 1, 1, N);
    Matrix A_bar21 = A.subMatrix(1, N, 0, 1);
    Matrix A_bar22 = A.subMatrix(1, N, 1, N);

    Matrix S22 = A_bar22.subtract(A_bar21.multiply(A_bar12).dividedBy(A_bar11.getEntry(0, 0)));

    Object[] tmp = S22.LUPDecompositionHelper(rowSwappingStrategy);
    Matrix L22 = (Matrix) tmp[0];
    Matrix U22 = (Matrix) tmp[1];
    Matrix P22 = (Matrix) tmp[2];
    boolean previousSignChanged = (boolean) tmp[3];

    Matrix L11 = identityMatrixWithSizeOf(1);
    Matrix U11 = A_bar11.copy();
    Matrix L12 = new Matrix(1, N - 1);
    Matrix U12 = A_bar12.copy();

    Matrix L21 = P22.multiply(A_bar21).dividedBy(A_bar11.getEntry(0, 0));
    Matrix U21 = new Matrix(N - 1, 1);

    Matrix upperPartOfP = new Matrix(1, i).augmentMatirx(identityMatrixWithSizeOf(1))
        .augmentMatirx(new Matrix(1, N - i - 1));
    Matrix lowerPartOfP = P22.subMatrix(0, N - 1, 0, i).augmentMatirx(new Matrix(N - 1, 1))
        .augmentMatirx(P22.subMatrix(0, N - 1, i, N - 1));

    Matrix L = combineMatrix(L11, L12, L21, L22);
    Matrix U = combineMatrix(U11, U12, U21, U22);
    Matrix P = upperPartOfP.augmentMatirxByExtendingColumns(lowerPartOfP);

    return new Object[] {L, U, P, signChanged | previousSignChanged};
  }

  /**
   * 
   * If it is possible to do LU decomposition, this method will do LU decomposition to the matrix.
   * Otherwise, it would do a LUP decomposition.
   * 
   * This method implements Crout algorithm to find the LUP decomposition of the matrix.
   * 
   * @return an array of length 3. The first is L, and the second is U. If LU decomposition is done,
   *         the third element in the array will be null, otherwise it will be the P.
   * 
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition
   * @see https://en.wikipedia.org/wiki/LU_decomposition#Crout_and_LUP_algorithms
   */
  @Override
  public Matrix[] LUPDecomposition() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Object[] tmp = this.LUPDecompositionHelper(RowSwappingStrategy.USE_NEXT_NON_ZERO_PIVOT);
    Matrix L = (Matrix) tmp[0];
    Matrix U = (Matrix) tmp[1];
    Matrix P = (Matrix) tmp[2];
    if (P.mathematicallyEquals(identityMatrixWithSizeOf(N)))
      P = null;
    return new Matrix[] {L, U, P};
  }

  /**
   * Return determinant of matrix. The given matrix must be a square matrix.
   * 
   * This method find the determinant matrix by using LUP method.
   * 
   * @return the determinant of the matrix
   * @throws MatrixDimensionsMismatchException - when the given matrix is not a square matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Determinant
   * @see https://en.wikipedia.org/wiki/LU_decomposition#Computing_the_determinant
   */
  @Override
  public Numeric determinant() throws MatrixDimensionsMismatchException {
    checkSquare();
    Object[] LUPDecomposition = LUPDecompositionHelper(RowSwappingStrategy.USE_LARGEST_PIVOT);
    Matrix U = (Matrix) LUPDecomposition[1];
    boolean signChanged = (boolean) LUPDecomposition[3];
    Numeric ansNumeric = U.productOfDiagonal();
    if (signChanged)
      ansNumeric = ansNumeric.opposite();
    return ansNumeric;
  }

  /**
   * Find the inverse of the matrix by using Gaussian-Elimination on a augmentedMatrix.
   * 
   * @return matrix that been inverted
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - if the matrix is not invertible.
   * 
   * @see https://en.wikipedia.org/wiki/Invertible_matrix#Gaussian_elimination
   */
  @Override
  public Matrix inverse() throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    int N = getSizeOfSquareMatrix();
    if (determinant().equals(Numeric.of(0))) {
      throw new MatrixArithmeticException("The matrix is not invertible!");
    }
    Matrix augmentedMatrix = augmentMatirx(identityMatrixWithSizeOf(N));
    augmentedMatrix.gussianEliminate(RowSwappingStrategy.USE_LARGEST_PIVOT);
    return augmentedMatrix.subMatrix(0, N, N, 2 * N);
  }

  /**
   * 
   * A private helper method that helps to calculate the matrix to the power of n when n is a
   * positive integer.
   * 
   * The algorithm used is exponentiation by squaring.
   * 
   * @param n given n, which must be a positive integer
   * @return the matrix to the power of n
   * 
   * @see https://en.wikipedia.org/wiki/Exponentiation_by_squaring
   */
  private Matrix helperpow(int n) {
    if (n < 0)
      throw new IllegalArgumentException("Parameter must be greater than 0.");
    if (n == 1)
      return copy();
    try {
      Matrix matrixPowHalfN = helperpow(n / 2);
      if (n % 2 == 0)
        return matrixPowHalfN.multiply(matrixPowHalfN);
      if (n % 2 == 1)
        return this.multiply(matrixPowHalfN.multiply(matrixPowHalfN));
    } catch (MatrixDimensionsMismatchException e) {
      // Unexpected, since it must be a square matrix. Only the pow() method of this class used this
      // method, and the pow() method would make sure that the given matrix is a square matrix.
      e.printStackTrace();
    }
    return null;
  }

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
  @Override
  public Matrix pow(int n) throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    int N = getSizeOfSquareMatrix();
    if (n == 0)
      return identityMatrixWithSizeOf(N);
    else if (n > 0)
      return helperpow(n);
    else if (n < 0)
      return inverse().helperpow(-n);
    return null;
  }


  /**
   * Calculate the Frobenius norm of the matrix. That is, find the square root of the sum of the
   * square of all entries.
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_norm#Frobenius_norm
   */
  protected Numeric norm() {
    Numeric ans = new Numeric(0);
    for (int i = 0; i < entry.length; i++) {
      for (int j = 0; j < entry[0].length; j++) {
        ans = ans.add(entry[i][j].multiply(entry[i][j]));
      }
    }
    return ans.sqrt();
  }

  // Householder
  @Override
  public Matrix[] QRDecomposition() {
    Matrix A = copy();
    int N = getNumberOfRow();
    Matrix R = A;
    Matrix Q = identityMatrixWithSizeOf(N);

    try {
      for (int k = 0; k < N - 1; k++) {

        // x=zeros(m,1);
        Matrix X = new Matrix(N, 1);

        // x(k:m,1)=R(k:m,k);
        Matrix tmp = R.subMatrix(k, N, k, k + 1);
        X = X.substitueWith(tmp, k, N, 0, 1);

        // g=norm(x);
        Numeric g = X.norm();

        // v=x; v(k)=x(k)+g;
        Matrix V = X;
        V.entry[k][0] = X.entry[k][0].add(g);

        // s=norm(v);
        Numeric s = V.norm();

        // if s~=0, w=v/s;
        Matrix W = V.dividedBy(s);

        // u=2*R'*w;
        Matrix U = R.transpose().multiply(W).multiply(Numeric.of(2));

        // R=R-w*u'; %Product HR
        R = R.subtract(W.multiply(U.transpose()));


        // Q=Q-2*Q*w*w'; %Product QR
        Q = Q.subtract(Q.multiply(W).multiply(W.transpose()).multiply(2));

      }
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      // Unexpected
      matrixDimensionsMismatchException.printStackTrace();
    }
    return new Matrix[] {Q, R};
  }

  /**
   * A private helper method that get an array of Numeric which are the entries on the diagonal
   * line. The matrix must be square matrix.
   * 
   * @return an array of Numeric which is the entries on the diagonal line of the square matrix
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix
   */
  private Numeric[] diagonal() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Numeric[] diagnal = new Numeric[N];
    for (int i = 0; i < N; i++)
      diagnal[i] = new Numeric(entry[i][i]);
    return diagnal;
  }



  private Matrix substitueWith(Matrix givenMatrix, int startRow, int endRow, int startColumn,
      int endColumn) throws MatrixDimensionsMismatchException {
    Matrix A = copy();
    if (givenMatrix.getNumberOfRow() != endRow - startRow
        || givenMatrix.getNumberOfColumn() != endColumn - startColumn)
      throw new MatrixDimensionsMismatchException("Ah!");
    for (int i = 0; i < endRow - startRow; i++)
      for (int j = 0; j < endColumn - startColumn; j++)
        A.entry[startRow + i][startColumn + j] = givenMatrix.getEntry(i, j);
    return A;
  }

  private Matrix eliminateSmallValues(int digits) {
    Matrix A = copy();
    for (int i = 0; i < A.entry.length; i++) {
      for (int j = 0; j < A.entry[i].length; j++) {
        if (A.entry[i][j].isZeroBy(digits)) {
          A.entry[i][j] = Numeric.of(0);
        }
      }
    }
    return A;
  }

  private Matrix hessenburgForm() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Matrix A = copy();
    for (int k = 0; k < N - 2; k++) {

      // v = Q2D(k+1:n,k); alpha = -norm(v);
      Matrix v = A.subMatrix(k + 1, N, k, k + 1);
      Numeric alpha = v.norm().opposite(); // ???

      // if (v(1) < 0) alpha = -alpha; end
      if (v.entry[0][0].compareTo(Numeric.of(0)) < 0) {
        alpha = alpha.opposite();
      }

      // v(1) = v(1) - alpha; v = v / norm(v);
      v.entry[0][0] = v.entry[0][0].subtract(alpha);
      v = v.dividedBy(v.norm());

      // Q2D(k+1:n,k+1:n) = Q2D(k+1:n,k+1:n) - 2 * v * (v.' * Q2D(k+1:n,k+1:n));
      Matrix A_22 = A.subMatrix(k + 1, N, k + 1, N);
      Matrix A_22_ =
          A_22.subtract(v.multiply(v.transpose().multiply(A_22)).multiply(Numeric.of(2)));
      A = A.substitueWith(A_22_, k + 1, N, k + 1, N);

      // Q2D(k+1,k) = alpha;
      A.entry[k + 1][k] = alpha;

      // Q2D(k+2:n,k) = 0;
      for (int i = k + 2; i < N; i++) {
        A.entry[i][k] = Numeric.of(0);
      }


      // Q2D(1:n,k+1:n) = Q2D(1:n,k+1:n) - 2 * (Q2D(1:n,k+1:n) * v) * v.';
      Matrix A_2 = A.subMatrix(0, N, k + 1, N);
      Matrix A_2_ = A_2.subtract(A_2.multiply(v).multiply(Numeric.of(2)).multiply(v.transpose()));
      A = A.substitueWith(A_2_, 0, N, k + 1, N);

    }

    return A;

  }

  // @see http://web.stanford.edu/class/cme335/lecture5
  private Numeric WilkinsonShiftHelper(Numeric a, Numeric b, Numeric c) {
    Numeric delta = a.subtract(c).dividedBy(2);
    Numeric bSquare = b.multiply(b);
    Numeric deltaSquare = delta.multiply(delta);
    // return c.subtract(x.dividedBy(y));
    return c.add(delta).subtract(delta.sign().multiply(deltaSquare.add(bSquare).sqrt()));
  }

  private Numeric WilkinsonShift() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    return WilkinsonShiftHelper(entry[N - 2][N - 2], entry[N - 2][N - 1], entry[N - 1][N - 1]);
  }

  private Matrix qrIterationWithWilkinsonShift() throws MatrixDimensionsMismatchException {
    int N = entry.length; // Already checked square
    Numeric mu = WilkinsonShift();
    Matrix muMatrix = identityMatrixWithSizeOf(N).multiply(mu);
    Matrix[] QRDecomposition = this.subtract(muMatrix).QRDecomposition();
    return QRDecomposition[1].multiply(QRDecomposition[0]).add(muMatrix);
  }

  private static final int LOWEST_DIGITE_PLACE_STARTING_VALUE = 6;
  private static int lowestDigitPlace = LOWEST_DIGITE_PLACE_STARTING_VALUE;

  private int lowestDigitInMatrix() {
    int ans = 0;
    for (int i = 0; i < entry.length; i++)
      for (int j = 0; j < entry[i].length; j++) {
        double value = entry[i][j].doubleValue();
        value = value - Math.floor(value);
        int digits = 0;
        int maxDigit = 2;
        for (int k = 0; k < maxDigit && Math.abs(value) > 0.0000000000001; k++) {
          value *= 10;
          value = value - Math.floor(value);
          digits++;
        }
        ans = Integer.max(ans, digits);
      }
    return ans;
  }

  private static boolean sameDiagnal(Matrix A, Matrix B) throws MatrixDimensionsMismatchException {
    Numeric array1[] = A.diagonal();
    Numeric array2[] = B.diagonal();
    Arrays.sort(array1);
    Arrays.sort(array2);
    for (int i = 0; i < array1.length; i++) {
      if (!array1[i].equals(array2[i], lowestDigitPlace / 2))
        return false;
    }
    return true;
  }

  /**
   * Find the eigenvalue of the matrix by QR algorithm.
   * 
   * @throws SingularException - ???
   * 
   * 
   * @see https://en.wikipedia.org/wiki/QR_algorithm
   */
  @Override
  public Numeric[] eigenValues() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if (N == 1)
      return new Numeric[] {new Numeric(entry[0][0])};
    int compensateDecimalDigits = lowestDigitInMatrix();
    lowestDigitPlace += compensateDecimalDigits;
    Matrix A = copy(), lastA = identityMatrixWithSizeOf(N), lastLastA;
    A = A.hessenburgForm();
    int interateCount = 0, n = N;
    ArrayList<Numeric> potentialEigenValues = new ArrayList<Numeric>();
    do {
      lastLastA = lastA;
      lastA = A;
      A = A.qrIterationWithWilkinsonShift();
      interateCount++;

      if (A.entry[n - 1][n - 2].isZeroBy(lowestDigitPlace / 2)) {
        potentialEigenValues.add(Numeric.of(0));
        if (n == 2) {
          break;
        }
        potentialEigenValues.add(A.entry[n - 1][n - 1]);
        lastLastA = lastA = A.subMatrix(0, n - 1, 0, n - 1);
        A = lastA.qrIterationWithWilkinsonShift();
        n--;
      }
      // System.out.println(A);
      if (interateCount > 10000)
        throw new ArithmeticException("Doesn't Converge");
      if (sameDiagnal(A, lastLastA))
        break;
    } while (!sameDiagnal(A, lastA));
    A = A.eliminateSmallValues(lowestDigitPlace);
    for (int i = 0; i < 100; i++) {
      A = A.qrIterationWithWilkinsonShift();
    }
    A = A.eliminateSmallValues(lowestDigitPlace);

    Collections.addAll(potentialEigenValues, A.diagonal());

    TreeSet<Numeric> eigenValues = new TreeSet<Numeric>();

    // System.out.println(potentialEigenValues);

    for (Numeric eigenValue : potentialEigenValues) {
      try {
        if (eigenValue.isZeroBy(lowestDigitPlace))
          eigenValue = Numeric.of(0);
        // System.out.println("eigenValue:" + eigenValue);
        Numeric castedEigenValue = eigenValue.castToNearestFraction();
        // System.out.println("Casted:" + castedEigenValue);
        if (this.subtract(identityMatrixWithSizeOf(N).multiply(castedEigenValue)).determinant()
            .mathematicallyEquals(Numeric.of(0))) {
          eigenValues.add(castedEigenValue);
        } else {
          throw new ClassCastException("EigenValue Didn't match!");
        }
      } catch (ClassCastException classCastException) {

        Matrix M = this.subtract(identityMatrixWithSizeOf(N).multiply(eigenValue));
        Matrix U = M.LUPDecomposition()[1];
        // System.out.println("*" + A);
        // System.out.println("Error" + M.determinant().abs());

        for (int i = 0; i < N; i++) {
          if (U.entry[i][i].isZeroBy(lowestDigitPlace / 2)) {
            eigenValues.add(eigenValue);
            break;
          }
        }

      }
    }
    Numeric[] eigenValueArray = new Numeric[eigenValues.size()];
    int numberOfEigenValue = 0;
    for (Numeric eigenValue : eigenValues) {
      if (numberOfEigenValue == 0 || !eigenValueArray[numberOfEigenValue - 1]
          .equals(eigenValueArray, Numeric.outputSignificantFigures + 1))
        eigenValueArray[numberOfEigenValue++] = eigenValue;
    }

    lowestDigitPlace = LOWEST_DIGITE_PLACE_STARTING_VALUE;
    return eigenValueArray;
  }


  public static void main(String[] args)
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {

    /*
     * Matrix A = new Matrix(new String[][] {{"0", "2", "3", "4", "5"}, {"9.9", "7", "8", "9",
     * "10"}, {"11", "12", "13", "14", "15"}, {"16", "17", "18", "19", "20"}, {"21", "22", "23",
     * "24", "25"}});
     */

    Matrix A =
        new Matrix(new String[][] {{"4", "12", "-16"}, {"12", "37", "-43"}, {"-16", "-43", "98"}});
    System.out.println(Arrays.deepToString(A.eigenValues()));

  }

}
