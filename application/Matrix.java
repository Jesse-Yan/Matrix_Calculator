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
// This project ¡°Matrix calculator" by CS400 Ateam 2 aims to help students
// studying linear algebra to understand the calculations of linear algebra 
// better. This ¡°Matrix calculator" can not only do many matrix calculations 
// like matrix multiplication, finding eigenvalues, and LUP, QR, or Cholesky 
// decompositions, but it can also support basic algebra calculations like a 
// normal calculator and analyzing sequence.
// 
// This ¡°Matrix calculator" consists of two parts, a math calculator on the 
// left side, which supports basic algebra calculations and analyzing sequence,
// and a matrix calculator on the right side, which supports calculations of 
// matrices.
//
// For matrix calculations, this ¡°Matrix calculator" also supports file inputs 
// and outputs. The input files should be json files in a specific format, and 
// the output files will also be json files. ¡°Matrix calculator" is also 
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
import java.util.Collections;
import java.util.TreeSet;

/**
 * An object of this class represents a Matrix and implements calculations operations needed for a
 * Matrix. For the stability of the program, this class is only used in MatrixCalculator which
 * connects the front-end and the back-end. The field of this class only contains an 2D array of
 * {@link Numeric} that stores the entries of this Matrix.
 * 
 * Since the main goal for this program is to help students learning linear algebra to understand
 * linear algebra better. The matrix supports calculations and outputs in Fractions, which will be
 * easier and more convenient for students to understand. However, not all calculations of matrix
 * can be done in fractions. Therefore, a {@link Numeric} class is written to provide a flexible way
 * to store numbers that can be either be an integer, or a fraction, or a double value. Therefore,
 * This matrix class used a 2D array of Numeric to store its entries.
 * 
 * This matrix class is an immutable class. That is, all public methods won't change the content of
 * a Matrix instance. (However some private methods might change)
 * 
 * @author Houming Chen
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
   * A private static helper method that returns generate a n*n identity matrix, where n is
   * specified by the parameter "size". This means it has 1 on every entries on its diagonal line
   * and has 0 on other entries.
   * 
   * @param size - a given integer n to represent the number of rows and columns of the identity
   *             matrix.
   * @return a n*n identity matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Identity_matrix
   */
  private static Matrix identityMatrixWithSizeOf(int size) {
    Numeric[][] identityMatirxEntries = new Numeric[size][size];
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
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
      try { // Check whether the two matrices have exactly the same dimensions.
        sameDimensionCheck(((Matrix) obj));
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        return false;
      }
      // Comparison
      for (int i = 0; i < entry.length; i++)
        for (int j = 0; j < entry[0].length; j++)
          if (!entry[i][j].equals(((Matrix) obj).entry[i][j])) // Compare Numerics
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
          if (!entry[i][j].mathematicallyEquals(((Matrix) obj).entry[i][j])) // Compare Numerics
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
    // Comparison
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
   * A private helper method that receives a given row and given column, and then it will find the
   * row index of the largest number in absolute value on the given column and with row index equal
   * to or greater than the given row.
   * 
   * @param row   - the index of the given row
   * @param given - the index of the given column
   * @return the row index of the largest number on the given column and with row index equal to or
   *         greater than the given row
   */
  private int indexOfLargestPivotElement(int row, int column) {
    int pivotRow = row;
    Numeric pivotElement = entry[pivotRow][column];
    for (int i = row + 1; i < getNumberOfRow(); i++) {
      if (entry[i][column].abs().compareTo(pivotElement.abs()) > 0) {
        pivotElement = entry[i][column];
        pivotRow = i;
      }
    }
    return pivotRow;
  }

  /**
   * 
   * A private helper method that receives a given row and given column, and then it will find the
   * row index of the first number on the given column and with row index equal to or greater than
   * the given row.
   * 
   * The "first" means it has the smallest row index among all the possible values.
   * 
   * @param row   - the index of the given row
   * @param given - the index of the given column
   * @return the index of the first number on the given column and with row index equal to or
   *         greater than the given row
   */
  private int indexOfNextNonZeroPivotElement(int row, int column) {
    for (int i = row; i < getNumberOfRow(); i++)
      if (!entry[i][column].equals(Numeric.of(0)))
        return i;
    return row;
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
   * @param startRow    - the given start row of the matrix to get the submatirx
   * @param endRow      - the given end row of the matrix to get the submatirx
   * @param startColumn - the given start column of the matrix to get the submatirx
   * @param endColumn   - the given end column of the matrix to get the submatirx
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
   * @param other - a given matrix that is going to be augmented on this matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *                                           rows with this matrix.
   * 
   * @see https://en.wikipedia.org/wiki/Augmented_matrix#To_find_the_inverse_of_a_matrix
   */
  private Matrix augmentMatirx(Matrix other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException(
          "The augment matrix must have same number of rows");
    int numberOfRow = getNumberOfRow();
    int numberOfColumnOfFirstMatrix = this.getNumberOfColumn();
    int numberOfColumnOfSecondMatrix = other.getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries =
        new Numeric[numberOfRow][numberOfColumnOfFirstMatrix + numberOfColumnOfSecondMatrix];
    for (int i = 0; i < numberOfRow; i++) {
      for (int j = 0; j < numberOfColumnOfFirstMatrix; j++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int j = 0; j < numberOfColumnOfSecondMatrix; j++) {
        augmentedMatrixEntries[i][j + numberOfColumnOfFirstMatrix] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  /**
   * This is a private helper method that calculate the product of the diagonal of the matrix, the
   * matrix must be a square matrix.
   * 
   * @return the product of the diagonal of the matrix
   */
  private Numeric productOfDiagonal() throws MatrixDimensionsMismatchException {
    int size = getNumberOfColumn(); // already checked that it is a square matrix
    Numeric ansNumeric = new Numeric(1);
    for (int i = 0; i < size; i++)
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
    int size = getSizeOfSquareMatrix();
    Numeric ansNumeric = new Numeric(0);
    for (int i = 0; i < size; i++)
      ansNumeric = ansNumeric.add(entry[i][i]);
    return ansNumeric;
  }

  /**
   * A private helper method that swap two rows of the matrix, That is, swap rowX and rowY.
   * 
   * Warning: this private method might change the content in the entries!
   * 
   * @param rowX - the index of the first row that is going to be swapped with the second row
   * @param rowY - the index of the second row that is going to be swapped with the first row
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
   * @param rowIndex                  - the given row
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
    for (int row = 0; row < getNumberOfRow(); row++) {
      boolean success = false;
      int column = row + dif;
      if (column >= getNumberOfColumn())
        return getNumberOfColumn() - dif;
      while (!success) {
        try {
          partialPivotingWithStrategy(row, column, rowSwappingStrategy);
          success = true;
        } catch (SingularException e) {
          dif++;
          column = row + dif;
          if (column >= getNumberOfColumn())
            return getNumberOfColumn() - dif;
        }
      }
      divideTheRowByPivotElement(row, column);
      eliminateOtherRowsBy(row, column);
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
    int size = getSizeOfSquareMatrix();
    Matrix lower = new Matrix(size, size);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j <= i; j++) {
        Numeric sum = Numeric.of(0);
        if (j == i) {
          for (int k = 0; k < j; k++)
            sum = sum.add(lower.entry[j][k].multiply(lower.entry[j][k]));
          try {
            lower.entry[j][j] = entry[j][j].subtract(sum).sqrt();
          } catch (ArithmeticException arithmeticException) {
            if (arithmeticException.getMessage()
                .equals("Sorry, the result is not all real numbers.")) // change message.
              throw new ArithmeticException("Sorry, for this matrix,"
                  + " cholesky decomposition cannot be done on the real field.");
            else
              throw arithmeticException; // throw the original arithmeticException
          }
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
   * @throws MatrixDimensionsMismatchException - if the given matrix does not have the same number
   *                                           of columns with this matrix.
   */
  private Matrix augmentMatirxByExtendingColumns(Matrix other)
      throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Must have same number of columns");
    int numberOfRowOfFirstMatrix = this.getNumberOfRow();
    int numberOfRowOfSecondMatrix = other.getNumberOfRow();
    int numberOfColumn = getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries =
        new Numeric[numberOfRowOfFirstMatrix + numberOfRowOfSecondMatrix][numberOfColumn];
    for (int j = 0; j < numberOfColumn; j++) {
      for (int i = 0; i < numberOfRowOfFirstMatrix; i++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int i = 0; i < numberOfRowOfSecondMatrix; i++) {
        augmentedMatrixEntries[i + numberOfRowOfFirstMatrix][j] = new Numeric(other.entry[i][j]);
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
   * @param topLeft     - a given matrix to be the top left part of the combined matrix
   * @param topRight    - a given matrix to be the top right part of the combined matrix
   * @param bottomLeft  - a given matrix to be the bottom left part of the combined matrix
   * @param bottomRight - a given matrix to be the bottom right part of the combined matrix
   * @return the combined matrix
   * @throws MatrixDimensionsMismatchException - if cannot combine due to number of rows or columns
   *                                           mismatches.
   */
  private static Matrix combineMatrix(Matrix topLeft, Matrix topRight, Matrix bottomLeft,
      Matrix bottomRight) throws MatrixDimensionsMismatchException {
    return topLeft.augmentMatirx(topRight)
        .augmentMatirxByExtendingColumns(bottomLeft.augmentMatirx(bottomRight));
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
    int size = getSizeOfSquareMatrix(); // the size of the square matrix
    if (size == 1)
      return new Object[] {identityMatrixWithSizeOf(1), this.copy(), identityMatrixWithSizeOf(1),
          false};

    Matrix A = copy(); // a copy of this matrix

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

    Matrix ATopLeft = A.subMatrix(0, 1, 0, 1);
    Matrix ATopRight = A.subMatrix(0, 1, 1, size);
    Matrix ABottomLeft = A.subMatrix(1, size, 0, 1);
    Matrix ABottomRight = A.subMatrix(1, size, 1, size);

    Matrix smallA =
        ABottomRight.subtract(ABottomLeft.multiply(ATopRight).dividedBy(ATopLeft.getEntry(0, 0)));

    Object[] tmp = smallA.LUPDecompositionHelper(rowSwappingStrategy);
    Matrix LBottomRight = (Matrix) tmp[0];
    Matrix UBottomRight = (Matrix) tmp[1];
    Matrix PBottomRight = (Matrix) tmp[2];
    boolean previousSignChanged = (boolean) tmp[3];

    Matrix LTopLeft = identityMatrixWithSizeOf(1);
    Matrix UTopLeft = ATopLeft.copy();
    Matrix LTopRight = new Matrix(1, size - 1);
    Matrix UTopRight = ATopRight.copy();

    Matrix LBottomLeft = PBottomRight.multiply(ABottomLeft).dividedBy(ATopLeft.getEntry(0, 0));
    Matrix UBottomLeft = new Matrix(size - 1, 1);

    Matrix upperPartOfP = new Matrix(1, i).augmentMatirx(identityMatrixWithSizeOf(1))
        .augmentMatirx(new Matrix(1, size - i - 1));
    Matrix lowerPartOfP =
        PBottomRight.subMatrix(0, size - 1, 0, i).augmentMatirx(new Matrix(size - 1, 1))
            .augmentMatirx(PBottomRight.subMatrix(0, size - 1, i, size - 1));

    Matrix L = combineMatrix(LTopLeft, LTopRight, LBottomLeft, LBottomRight);
    Matrix U = combineMatrix(UTopLeft, UTopRight, UBottomLeft, UBottomRight);
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
    int size = getSizeOfSquareMatrix();
    Object[] decomposition =
        this.LUPDecompositionHelper(RowSwappingStrategy.USE_NEXT_NON_ZERO_PIVOT);
    Matrix theLMatirx = (Matrix) decomposition[0];
    Matrix theUMatirx = (Matrix) decomposition[1];
    Matrix thePMatirx = (Matrix) decomposition[2];
    if (thePMatirx.mathematicallyEquals(identityMatrixWithSizeOf(size)))
      thePMatirx = null;
    return new Matrix[] {theLMatirx, theUMatirx, thePMatirx};
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
    Matrix theUMatrix = (Matrix) LUPDecomposition[1];
    boolean signChanged = (boolean) LUPDecomposition[3];
    Numeric ansNumeric = theUMatrix.productOfDiagonal();
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
   * @param exponent given n, which must be a positive integer
   * @return the matrix to the power of n
   * 
   * @see https://en.wikipedia.org/wiki/Exponentiation_by_squaring
   */
  private Matrix helperpow(int exponent) {
    if (exponent < 0)
      throw new IllegalArgumentException("Parameter must be greater than 0.");
    if (exponent == 1)
      return copy();
    try {
      Matrix matrixPowHalfN = helperpow(exponent / 2);
      if (exponent % 2 == 0)
        return matrixPowHalfN.multiply(matrixPowHalfN);
      if (exponent % 2 == 1)
        return this.multiply(matrixPowHalfN.multiply(matrixPowHalfN));
    } catch (MatrixDimensionsMismatchException e) {
      // Unexpected, since it must be a square matrix. Only the pow() method of this class used this
      // method, and the pow() method would make sure that the given matrix is a square matrix.
      throw new RuntimeException("Unexpected exception in pow!");
    }
    throw new RuntimeException("Unexpected exception in pow!"); // Unexpected
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
   * @param exponent - given n
   * @return - the matrix to the power of n
   * @throws MatrixDimensionsMismatchException - when the matrix is not a square matrix.
   * @throws MatrixArithmeticException         - when n is negative but the matrix is non-invertible
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_multiplication#Powers_of_a_matrix
   */
  @Override
  public Matrix pow(int exponent)
      throws MatrixDimensionsMismatchException, MatrixArithmeticException {
    int N = getSizeOfSquareMatrix();
    if (exponent == 0)
      return identityMatrixWithSizeOf(N);
    else if (exponent > 0)
      return helperpow(exponent);
    else if (exponent < 0)
      return inverse().helperpow(-exponent);
    throw new RuntimeException("Unexpected exception in pow!");// Unexpected
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


  /**
   * This method will copy a matrix which is smaller than this matrix to this matrix. The copied
   * matrix will be place in this matrix from the given start row and the given start column of this
   * matrix, and the previous entries will be covered. The method will return the result, and this
   * original matrix won't change. The returned matrix will have exactly same number of columns and
   * rows with this original matrix.
   * 
   * @param givenMatrix - a given matrix that is going to be copied to this matrix
   * @param startRow    - the start row of this matrix where the covering starts
   * @param startColumn - the start column of this matrix there the covering starts
   * @return the result matrix after copying.
   * @throws MatrixDimensionsMismatchException
   */
  private Matrix substitueWith(Matrix givenMatrix, int startRow, int startColumn) {
    Matrix A = copy();
    for (int i = 0; i < givenMatrix.getNumberOfRow(); i++)
      for (int j = 0; j < givenMatrix.getNumberOfColumn(); j++)
        A.entry[startRow + i][startColumn + j] = givenMatrix.getEntry(i, j);
    return A;
  }

  /**
   * 
   * QR decomposition.
   * 
   * This method implement the QR decomposition by using the Household reflection.
   * 
   * @return an array of length 2. The first is Q, and the second is R.
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition
   * @see https://en.wikipedia.org/wiki/QR_decomposition#Using_Householder_reflections
   */
  @Override
  public Matrix[] QRDecomposition() {
    int numberOfRow = getNumberOfRow();
    Matrix R = this.copy(); // The R matrix
    Matrix Q = identityMatrixWithSizeOf(numberOfRow); // The Q matrix
    try {
      for (int k = 0; k < numberOfRow - 1; k++) {
        Matrix reflectionVector = new Matrix(numberOfRow, 1);
        reflectionVector =
            reflectionVector.substitueWith(R.subMatrix(k, numberOfRow, k, k + 1), k, 0);
        reflectionVector.entry[k][0] = reflectionVector.entry[k][0].add(reflectionVector.norm());
        reflectionVector = reflectionVector.dividedBy(reflectionVector.norm());
        R = R.subtract(reflectionVector.multiply(
            (R.transpose().multiply(reflectionVector).multiply(Numeric.of(2))).transpose()));// HR
        // QR Q=Q-2*Q*x*x'
        Q = Q.subtract(
            Q.multiply(reflectionVector).multiply(reflectionVector.transpose()).multiply(2));
      }
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      throw new RuntimeException("Unexpected exception in QR decomposition!");// Unexpected
    }
    return new Matrix[] {Q, R};
  }

  /**
   * A private helper method that reduce the given matrix to Hessenburg form and return the answer.
   * 
   * @return the matrix that has been reduced to a Hessenburg-from
   * 
   * @see https://en.wikipedia.org/wiki/Hessenberg_matrix
   * @see https://www.ams.org/journals/mcom/1969-23-108/S0025-5718-1969-0258255-3/S0025-5718-1969-0258255-3.pdf
   */
  private Matrix hessenburgForm() {
    int N = getNumberOfRow();
    Matrix A = copy(); // A copy of current matrix.
    try {
      for (int k = 0; k < N - 2; k++) {

        Matrix reflectionVector = A.subMatrix(k + 1, N, k, k + 1);
        Numeric alphaValue = reflectionVector.norm();

        if (reflectionVector.entry[0][0].compareTo(Numeric.of(0)) >= 0)
          alphaValue = alphaValue.opposite();
        reflectionVector.entry[0][0] = reflectionVector.entry[0][0].subtract(alphaValue);
        reflectionVector = reflectionVector.dividedBy(reflectionVector.norm());
        Matrix AwithoutFirstRowAndFirstColumn = A.subMatrix(k + 1, N, k + 1, N);
        AwithoutFirstRowAndFirstColumn = AwithoutFirstRowAndFirstColumn.subtract(reflectionVector
            .multiply(reflectionVector.transpose().multiply(AwithoutFirstRowAndFirstColumn))
            .multiply(Numeric.of(2)));
        A = A.substitueWith(AwithoutFirstRowAndFirstColumn, k + 1, k + 1);

        A.entry[k + 1][k] = alphaValue;

        for (int i = k + 2; i < N; i++) {
          A.entry[i][k] = Numeric.of(0);
        }
        Matrix AleftPart = A.subMatrix(0, N, k + 1, N);
        AleftPart = AleftPart.subtract(AleftPart.multiply(reflectionVector).multiply(Numeric.of(2))
            .multiply(reflectionVector.transpose()));
        A = A.substitueWith(AleftPart, 0, k + 1);

      }
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      throw new RuntimeException("Unexpected exception in reduction to hessenburg form!");// Unexpected
    }
    return A;

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

  //
  /**
   * A helper method that helps to do Wilkinson-Shift according its formula.
   * 
   * @param numA - the a parameter in Wilkinon-Shift's formula, which should be the entry[N - 2][N -
   *             2]
   * @param numB - the b parameter in Wilkinon-Shift's formula, which should be the entry[N - 2][N -
   *             1]
   * @param numC - the c parameter in Wilkinon-Shift's formula, which should be the entry[N - 1][N -
   *             1]
   * @return the shift value (mu value) calculated by Wilkinson-Shift's formula
   * @see http://web.stanford.edu/class/cme335/lecture5
   */
  private Numeric WilkinsonShiftHelper(Numeric numA, Numeric numB, Numeric numC) {
    Numeric delta = numA.subtract(numB).dividedBy(2);
    Numeric bSquare = numB.multiply(numB);
    Numeric deltaSquare = delta.multiply(delta);;
    return numB.add(delta).subtract(delta.sign().multiply(deltaSquare.add(bSquare).sqrt()));
  }

  /**
   * Use the Wilkinson-Shift formula implemented in
   * {@link Matrix#WilkinsonShiftHelper(Numeric, Numeric, Numeric)} to calculate the shift value (mu
   * value) of this matrix.
   * 
   * @return the calculated shift value (mu value) of this matrix.
   */
  private Numeric WilkinsonShift() {
    int size = entry.length; // Already checked square
    return WilkinsonShiftHelper(entry[size - 2][size - 2], entry[size - 2][size - 1],
        entry[size - 1][size - 1]);
  }

  /**
   * 
   * A private helper method for QR algorithm. It do a step of QR iteration with Wilkinon-Shift and
   * return the result matrix.
   * 
   * @return the result matrix after the QR iteration with Wiklinon-Shift
   * @throws MatrixDimensionsMismatchException
   * @see https://en.wikipedia.org/wiki/QR_algorithm
   */
  private Matrix qrIterationWithWilkinsonShift() {
    int N = entry.length; // Already checked square
    Numeric muValue = WilkinsonShift();
    Matrix muMatrix = identityMatrixWithSizeOf(N).multiply(muValue);
    try {
      Matrix[] QRDecomposition = this.subtract(muMatrix).QRDecomposition();
      return QRDecomposition[1].multiply(QRDecomposition[0]).add(muMatrix);
    } catch (MatrixDimensionsMismatchException e) {
      throw new RuntimeException("Unexpected exception in during QR iteration.");// Unexpected
    }
  }

  /**
   * 
   * A private helper method for QR algorithm. It do a step of QR iteration with a normal shift that
   * use the entry[N - 1][N - 1] for the shift value (mu value).
   * 
   * This is strange. Theoretically Wilkinon-Shift is enough for QR algorithm to converge. However,
   * in this program, there are still some matrix cannot converge. Therefore some normal shift are
   * introduced when Wilkinon-Shift QR iterations doesn't converge, but that is still not enough.
   * See known bugs in Readme for details.
   * 
   * @return the result matrix after the QR iteration with normal shift.
   * @throws MatrixDimensionsMismatchException - if the given matrix is not a square matrix.
   * @see https://en.wikipedia.org/wiki/QR_algorithm
   */
  private Matrix qrIterationWithNormalShift() throws MatrixDimensionsMismatchException {
    int N = entry.length; // Already checked square
    Numeric muValue = entry[N - 1][N - 1];
    Matrix muMatrix = identityMatrixWithSizeOf(N).multiply(muValue);
    Matrix[] QRDecomposition = this.subtract(muMatrix).QRDecomposition();
    return QRDecomposition[1].multiply(QRDecomposition[0]).add(muMatrix);
  }

  /**
   * The float calculations may cause floating error. Therefore, in order to decide whether a number
   * is zero, only a given number of decimal digits are used to decide whether a number is zero,
   * which is the {@link Matrix#lowestDigitPlace}.
   * 
   * However, this {@link Matrix#lowestDigitPlace} cannot be the same value all the time. Sometimes
   * if the matrix inputed by the user has many decimal digits, then this value can be adjust.
   * 
   * Therefore, this static final int value LOWEST_DIGITE_PLACE_STARTING_VALUE provides a starting
   * for the {@link Matrix#lowestDigitPlace}.
   */
  private static final int LOWEST_DIGITE_PLACE_STARTING_VALUE = 6;

  /**
   * The float calculations may cause floating error. Therefore, in order to decide whether a number
   * is zero, only a given number of decimal digits are used to decide whether a number is zero. And
   * this number will be specified by this lowestDigitPlace.
   */
  private static int lowestDigitPlace = LOWEST_DIGITE_PLACE_STARTING_VALUE;

  /**
   * A private helper method that checks the number of decimal digits of the inputs to help to
   * determine the adjustment of the {@link Matrix#lowestDigitPlace}. However, this value can at
   * most be 3, because due to the float error, this program cannot do computations for too many
   * decimal digits. If the number has more than 3 decimal digits, it will also return 3.
   * 
   * @return the numberOfDecimalDigits the number of decimal digits, but if it is greater than 3,
   *         return 3.
   */
  private int numberOfDecimalDigits() {
    int ans = 0;
    for (int i = 0; i < entry.length; i++)
      for (int j = 0; j < entry[i].length; j++) {
        double value = entry[i][j].doubleValue();
        value = value - Math.floor(value);
        int digits = 0;
        int maxDigit = 3;
        for (int k = 0; k < maxDigit && Math.abs(value) > 0.0000000000001; k++) {
          value *= 10;
          value = value - Math.floor(value);
          digits++;
        }
        ans = Integer.max(ans, digits);
      }
    return ans;
  }

  /**
   * A private helper method helps to adjust {@link Matrix#lowestDigitPlace}. It adds the number of
   * decimal digits returned by the {@link Matrix#numberOfDecimalDigits()}} to the original
   * {@link Matrix#lowestDigitPlace}.
   */
  private void adjustLowestDigitPlace() {
    lowestDigitPlace += numberOfDecimalDigits();
  }

  /**
   * A private helper method that turn the {@link Matrix#lowestDigitPlace} back to
   * {@link Matrix#LOWEST_DIGITE_PLACE_STARTING_VALUE}
   */
  private void setLowestDigitPlaceBack() {
    lowestDigitPlace = LOWEST_DIGITE_PLACE_STARTING_VALUE;
  }

  /**
   * 
   * A private helper method that check whether the two given matrix have same elements on their
   * diagonal lines (regardless of order).
   * 
   * @param A - the first given matrix
   * @param B - the second given matrix
   * @return true if the two two given matrix have same elements on their diagonal lines (regardless
   *         of order), and false otherwise.
   */
  private static boolean sameDiagnal(Matrix A, Matrix B) {
    try {
      Numeric array1[] = A.diagonal();
      Numeric array2[] = B.diagonal();
      Arrays.sort(array1);
      Arrays.sort(array2);
      for (int i = 0; i < array1.length; i++) {
        if (!array1[i].equals(array2[i], lowestDigitPlace / 2))
          return false;
      }
      return true;
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      throw new RuntimeException("Unexpected exception when comparing diagnals!");// Unexpected
    }
  }

  /**
   * 
   * A private helper method that makes tiny values (which can be regard as zeros) to zero. This
   * method is used for cleaning entries in the result matrix. The number of decimal digits used in
   * deciding whether a number can be regard as zero is specified by the parameter digits.
   * 
   * @param digits - the number of decimal digits used in deciding whether a number is zero
   * @return the result matrix after the elimination.
   */
  private Matrix eliminateSmallValues(int digits) {
    Matrix matrixCopy = copy();
    for (int i = 0; i < matrixCopy.entry.length; i++) {
      for (int j = 0; j < matrixCopy.entry[i].length; j++) {
        if (matrixCopy.entry[i][j].isZeroBy(digits)) {
          matrixCopy.entry[i][j] = Numeric.of(0);
        }
      }
    }
    return matrixCopy;
  }



  /**
   * A private helper method that helps to make the result matrix of the QR algorithm more easier
   * for next step to improve the stability of this program.
   * 
   * @return the matrix after the cleaning.
   */
  private Matrix cleanTheResultMatrix() {
    Matrix A = copy(); // A copy of current matrix
    A = A.eliminateSmallValues(lowestDigitPlace);
    for (int i = 0; i < 50; i++) {
      A = A.qrIterationWithWilkinsonShift();
    }
    A = A.eliminateSmallValues(lowestDigitPlace);
    return A;
  }

  /**
   * 
   * A helper method for computing eigenvalues that do QR algorithm. It will do QR algorithms on the
   * matrix and return the diagonal of the result matrix in an ArrayList to be potential
   * eigenvalues.
   * 
   * @return an ArrayList that contains the diagonal of the result matrix
   * @throws MatrixDimensionsMismatchException
   */
  public ArrayList<Numeric> eigenvaluesHelper() throws MatrixDimensionsMismatchException {
    int size = getNumberOfRow(); // Already checked square;
    // A is a copy of this matrix. lastA and lastLastA are used to record the previous status of A
    // in order to decide whether to stop the iteration.
    Matrix A = copy(), lastA = identityMatrixWithSizeOf(size), lastLastA;
    A = A.hessenburgForm();
    int interateCount = 0, currentSize = size;
    ArrayList<Numeric> potentialEigenValues = new ArrayList<Numeric>();
    do {
      lastLastA = lastA;
      lastA = A;
      A = A.qrIterationWithWilkinsonShift();
      interateCount++;
      if (A.entry[currentSize - 1][currentSize - 2].isZeroBy(lowestDigitPlace / 2)) {
        potentialEigenValues.add(Numeric.of(0));
        if (currentSize == 2) {
          break;
        }
        potentialEigenValues.add(A.entry[currentSize - 1][currentSize - 1]);
        lastLastA = lastA = A.subMatrix(0, currentSize - 1, 0, currentSize - 1);
        A = lastA.qrIterationWithWilkinsonShift();
        currentSize--;
      }
      if (interateCount % 1000 == 0) { // Try some normal shift
        for (int i = 0; i < 100; i++) {
          A = A.qrIterationWithNormalShift();
        }
      }
      if (interateCount > 4000) { // doesn't converge! Break!
        break;
      }
    } while (!sameDiagnal(A, lastLastA) && !sameDiagnal(A, lastA));
    A = A.cleanTheResultMatrix();
    Collections.addAll(potentialEigenValues, A.diagonal());
    return potentialEigenValues;
  }

  /**
   * 
   * A private helper method that try to convert the potential eigenvalue into fraction and check
   * whether the conversion is possible. If it is possible, returns the converted Numeric,
   * otherwise, throws a ClassCastException with messages.
   * 
   * @param potentialEigenvalue - a given potential eigenvalue
   * @return the converted Numeric in fraction state, if possible.
   */
  private Numeric tryToGetMathematicallyPreciseEigenvalue(Numeric potentialEigenvalue) {
    int size = getNumberOfRow(); // Already checked square;
    if (potentialEigenvalue.isZeroBy(lowestDigitPlace))
      potentialEigenvalue = Numeric.of(0);
    Numeric castedEigenValue = potentialEigenvalue.castToNearestFraction();
    try {
      if (this.subtract(identityMatrixWithSizeOf(size).multiply(castedEigenValue)).determinant()
          .mathematicallyEquals(Numeric.of(0))) {
        return castedEigenValue;
      }
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      throw new RuntimeException("Unexpected exception in casting to fraction.");// Unexpected
    }
    throw new ClassCastException("EigenValue Didn't match!");
  }

  /**
   * 
   * A private helper method that checks whether the potential eigenvalue is the true eigenvalue.
   * According to the QR algorithm with matrix reduction to Hessenberg form, the numbers on the
   * Diagonal lines might not be the true eigenvalue of the matrix because of the existence of
   * complex eigenvalues. Therefore, this method checks whether the result matrix is really a
   * eigenvalue by checking the characteristic equation equals to zero.
   * 
   * @param potentialEigenvalue - a given potential eigenvalue
   * @return true if the potential eigenvalue is a real eigenvalue.
   */
  private boolean checkEigenvalue(Numeric potentialEigenvalue) {
    int size = getNumberOfRow(); // Already checked square;
    try {
      Matrix M = this.subtract(identityMatrixWithSizeOf(size).multiply(potentialEigenvalue));
      Matrix U = M.LUPDecomposition()[1];
      for (int i = 0; i < size; i++) {
        if (U.entry[i][i].isZeroBy(lowestDigitPlace / 2)) {
          return true;
        }
      }
    } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
      throw new RuntimeException("Unexpected exception in checking the eigenvalue!");// Unexpected
    }
    return false;
  }

  /**
   * 
   * A private helper method that convert a TreeSet of Numeric values to an array of Numeric values,
   * and during the conversion, similar values will be combined into one value. (keeping the smaller
   * one).
   * 
   * @param eigenvalues - a TreeSet of Numeric values representing the calculated eigenvalues.
   * @return an array of Numeric value that is converted by the TreeSet.
   */
  private Numeric[] convertTreeSetToNumericArray(TreeSet<Numeric> eigenvalues) {
    Numeric[] eigenvalueArray = new Numeric[eigenvalues.size()];
    int numberOfEigenValue = 0;
    for (Numeric eigenvalue : eigenvalues) {
      if (numberOfEigenValue == 0 || !eigenvalueArray[numberOfEigenValue - 1]
          .equals(eigenvalueArray, Numeric.outputSignificantFigures + 1))
        eigenvalueArray[numberOfEigenValue++] = eigenvalue;
    }
    return eigenvalueArray;
  }

  /**
   * This method find the eigenvalues of the matrix by QR algorithm.
   * 
   * @see https://en.wikipedia.org/wiki/QR_algorithm
   */
  public Numeric[] eigenvalues() throws MatrixDimensionsMismatchException {
    int size = getSizeOfSquareMatrix();
    if (size == 1)
      return new Numeric[] {new Numeric(entry[0][0])};
    adjustLowestDigitPlace();
    ArrayList<Numeric> potentialEigenvalues = eigenvaluesHelper();
    TreeSet<Numeric> eigenvalues = new TreeSet<Numeric>();
    for (Numeric eigenvalue : potentialEigenvalues) {
      try {
        Numeric castedEigenValue = tryToGetMathematicallyPreciseEigenvalue(eigenvalue);
        eigenvalues.add(castedEigenValue);
      } catch (ClassCastException classCastException) {
        if (checkEigenvalue(eigenvalue)) {
          eigenvalues.add(eigenvalue);
        }
      }
    }
    setLowestDigitPlaceBack();
    return convertTreeSetToNumericArray(eigenvalues);
  }
}
