package application;

import java.util.Arrays;

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
   * @author Houming Chen
   *
   */
  public enum Mode {
    FRACTION, // Will give the result in fraction
    DECIMAL // Will give the result in decimal
  }

  /**
   * The mode of this calculator.
   */
  public Mode mode;

  /**
   * The reference of two 2D string arrays.
   */
  public String[][] matrix1, matrix2;

  /**
   * Construct the MatrixCalculator by only giving the first matrix. The mode is set as FRACTION by
   * default.
   * @param matrix1 the references of a String[][] as the first matrix
   */
  public MatrixCalculator(String[][] matrix1) {
    this(matrix1, null, Mode.FRACTION);
  }

  /**
   * Construct the MatrixCalculator by only giving the first matrix and a given mode.
   * @param matrix1 the references of a String[][] as the first matrix
   * @param mode    a given mode
   */
  public MatrixCalculator(String[][] matrix1, Mode mode) {
    this(matrix1, null, mode);
  }

  /**
   * Construct the MatrixCalculator by two references of 2D Sting arrays for the two matrices. The
   * mode is set as FRACTION by default.
   * @param matrix1 the references of a String[][] as the first matrix
   * @param matrix2 the references of a String[][] as the second matrix
   */
  public MatrixCalculator(String[][] matrix1, String[][] matrix2) {
    this(matrix1, matrix2, Mode.FRACTION);
  }

  /**
   * Construct the MatrixCalculator by two references of 2D Sting arrays for the two matrices and a
   * given mode.
   * @param matrix1 the references of a String[][] as the first matrix
   * @param matrix2 the references of a String[][] as the second matrix
   * @param mode    a given mode
   */
  public MatrixCalculator(String[][] matrix1, String[][] matrix2, Mode mode) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.mode = mode;
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
   * @throws ArithmeticException - if matrix1 is not invertible.
   */
  public String[][] getInverse() throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.inverse());
  }

  private String[][] properFormatted(Matrix matrix) {
    if (mode == Mode.DECIMAL)
      return matrix.toDecimalStringMatrix();
    else
      return matrix.toStringMatrix();
  }

  private String properFormatted(Numeric number) {
    if (mode == Mode.DECIMAL)
      return number.toString();
    else
      return number.castToDouble().toString();
  }

  /**
   * Demo
   * 
   * @param args
   * @throws MatrixDimensionsMismatchException
   */
  public static void main(String[] args) throws MatrixDimensionsMismatchException {

    String[][] matrixA = {{"1", "0", "0.1"}, {"5/3", "0", "-1/3"}, {"-3", "0.4", "-0.333"}};
    String[][] matrixB = {{"1", "2", "-3"}, {"1/3", "1/2", "-1/1024"}, {"0.5", "0.2", "-0.3"},};

    MatrixCalculator matrixCalculator = new MatrixCalculator(matrixA, matrixB);

    System.out.println("matirxC = matirxA + matrixB");
    String[][] matrixC = matrixCalculator.add();

    System.out.println("Print matirx C");
    System.out.println(Arrays.deepToString(matrixC) + '\n');

    System.out.println("matirxD = inverse of matrixA");
    String[][] matrixD = matrixCalculator.getInverse();

    System.out.println("Print matirx D");
    System.out.println(Arrays.deepToString(matrixD) + '\n');

    System.out.println("matirxE = inverse of matrixA (in decimal)");
    matrixCalculator.mode = MatrixCalculator.Mode.DECIMAL;
    String[][] matrixE = matrixCalculator.getInverse();

    System.out.println("Print matirx E");
    System.out.println(Arrays.deepToString(matrixE) + '\n');
  }

}
