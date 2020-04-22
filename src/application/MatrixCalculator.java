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
   * Return matrix1 to the power of n, where n is a given integer.
   * 
   * @param n a given integer
   * @return the result of matrix1 to the power of n.
   * @throws MatrixDimensionsMismatchException if matrix1 is not a square matrix.
   */
  public String[][] getPow(int n) throws MatrixDimensionsMismatchException {
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.pow(n));
  }

  private String[][] properFormatted(Matrix matrix) {
    if (mode == Mode.DECIMAL)
      return matrix.toDecimalStringMatrix();
    else
      return matrix.toStringMatrix();
  }

  private String properFormatted(Numeric number) {
    if (mode == Mode.DECIMAL)
      return number.castToDouble().toString();
    else
      return number.toString();
  }

  /**
   * Demo
   * 
   * @param args
   * @throws MatrixDimensionsMismatchException
   */
  public static void main(String[] args) throws MatrixDimensionsMismatchException {

    String[][] matrixA, matrixB, matrixC;
    MatrixCalculator matrixCalculator;
    
    //************************ Demo for Matrix Addition *********************************
    
    System.out.println("Demo for Matrix Addition");
    matrixA = new String[][] {{"1", "0", "0.1"}, {"5/3", "0", "-1/3"}, {"-3", "0.4", "-0.333"}};
    matrixB = new String[][] {{"1", "2", "-3"}, {"1/3", "1/2", "-1/1024"}, {"0.5", "0.2", "-0.3"}};
    
    matrixCalculator = new MatrixCalculator(matrixA, matrixB);
    matrixC = matrixCalculator.add();
    System.out.println("Fraction(Default) version: " + Arrays.deepToString(matrixC));
    
    matrixCalculator.mode = MatrixCalculator.Mode.DECIMAL; // Try decimal mode
    matrixC = matrixCalculator.add();
    System.out.println("Decimal version: " + Arrays.deepToString(matrixC) + '\n');
    
    //************************ Demo for Matrix Addition *********************************
    
    //************************ Demo for Matrix Multiplication *********************************
    System.out.println("Demo for Matrix Multiplication");
    matrixA = new String[][] {{"1", "2"}, {"3", "4"}, {"5", "6"}, {"7", "8"}};
    matrixB = new String[][] {{"1", "0", "2"},{"1", "0", "1/2"}};
    matrixCalculator = new MatrixCalculator(matrixA, matrixB);
    matrixC = matrixCalculator.multiply();
    System.out.println(Arrays.deepToString(matrixC) + '\n');
    //************************ Demo for Matrix Multiplication *********************************
    
    //************************ Demo for Determinant *********************************
    System.out.println("Demo for Determinant");
    matrixA = new String[][] {{"1/2", "1"}, {"3/2", "2"}};
    matrixCalculator = new MatrixCalculator(matrixA);
    System.out.println(matrixCalculator.getDeterminant() + '\n');
    //************************ Demo for Determinant *********************************
    
    //************************ Demo for Inverse *********************************
    System.out.println("Demo for Matrix Inverse");
    matrixA = new String[][] {{"1/2", "1"}, {"3/2", "2"}};
    matrixCalculator = new MatrixCalculator(matrixA);
    matrixC = matrixCalculator.getInverse();
    System.out.println(Arrays.deepToString(matrixC) + '\n');
    //************************ Demo for Inverse *********************************

    //************************ Demo for Trace *********************************
    System.out.println("Demo for Trace");
    matrixA = new String[][] {{"1/2", "1"}, {"3/2", "2"}};
    matrixCalculator = new MatrixCalculator(matrixA);
    System.out.println(matrixCalculator.getTrace() + '\n');
    //************************ Demo for Trace *********************************
    
    //************************ Demo for Pow *********************************
    System.out.println("Demo for Pow");
    matrixA = new String[][] {{"1/2", "1"}, {"3/2", "2"}};
    int n = 8;
    matrixCalculator = new MatrixCalculator(matrixA);
    System.out.println(Arrays.deepToString(matrixCalculator.getPow(n))+ '\n');
    //************************ Demo for Trace *********************************
  }

}
