package application;

import java.util.Arrays;

/**
 * This class defines required methods to do calculation on Matrixs
 * 
 * @author Jesse, Houming Chen
 *
 */
public class MatrixCalculator {
  
  public enum Mode {
    FRACTION, // Gives the result in fraction
    DECIMAL // Gives the result in decimal
  }
  
  public Mode mode;
  public String[][] matrix1, matrix2;
  
  public MatrixCalculator(String[][] matrix1, String[][] matrix2) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
  }
  
  public String[][] add() throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.add(secondMatrix);
    return properFormatted(answeMatrix);
  }
  
  public String[][] subtract() throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.subtract(secondMatrix);
    return properFormatted(answeMatrix);
  }
  
  public String[][] multiply() throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    Matrix answeMatrix = firstMatrix.multiply(secondMatrix);
    return properFormatted(answeMatrix);
  }
  
  public String getDeterminant() throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.determinant());
  }
  
  public String[][] getInverse() throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    return properFormatted(firstMatrix.inverse());
  }
  
  private String[][] properFormatted(Matrix matrix) {
    if(mode == Mode.DECIMAL)
      return matrix.toDecimalStringMatrix();
    else 
      return matrix.toStringMatrix();
  }
  
  private String properFormatted(Numeric number) {
    if(mode == Mode.DECIMAL)
      return number.toString();
    else 
      return number.castToDouble().toString();
  }
  
  /**
   * Demo
   * @param args
   * @throws MatrixDimensionsMismatchException 
   */
  public static void main(String[] args) throws MatrixDimensionsMismatchException {
    
    String[][] matrixA = {{"1", "0", "0.1"}, 
                          {"5/3", "0", "-1/3"},
                          {"-3", "0.4", "-0.333"}};
    String[][] matrixB = {{"1", "2", "-3"}, 
                          {"1/3", "1/2", "-1/1024"},
                          {"0.5", "0.2", "-0.3"},};
    
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
    matrixCalculator.mode = Mode.DECIMAL;
    String[][] matrixE = matrixCalculator.getInverse();
    
    System.out.println("Print matirx E");
    System.out.println(Arrays.deepToString(matrixE) + '\n');
  }
  
}
