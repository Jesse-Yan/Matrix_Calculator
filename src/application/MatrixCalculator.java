package application;

/**
 * This class defines required methods to do calculation on Matrixs
 * 
 * @author Jesse, Houming Chen
 *
 */
public class MatrixCalculator {
  
  public static String[][] add(String[][] matrix1, String[][] matrix2) throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    return firstMatrix.add(secondMatrix).toStringMatrix();
  }
  
  public static String[][] subtract(String[][] matrix1, String[][] matrix2) throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    return firstMatrix.subtract(secondMatrix).toStringMatrix();
  }
  
  public static String[][] multiply(String[][] matrix1, String[][] matrix2) throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    Matrix secondMatrix = new Matrix(matrix2);
    return firstMatrix.multiply(secondMatrix).toStringMatrix();
  }
  
  public static String getDeterminant(String[][] matrix1) throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    return firstMatrix.determinant().toString();
  }
  
  public static String[][] getInverse(String[][] matrix1) throws MatrixDimensionsMismatchException{
    Matrix firstMatrix = new Matrix(matrix1);
    return firstMatrix.inverse().toStringMatrix();
  }
  
  /**
   * Demo
   * @param args
   * @throws MatrixDimensionsMismatchException 
   */
  public static void main(String[] args) throws MatrixDimensionsMismatchException {
    String[][] matrix1 = {{"1", "2", "-3"}, 
                          {"1/3", "1/2", "-1/3"},
                          {"0.5", "0.2", "-0.3"},};
    String[][] matrix2 = {{"1", "1", "0.111"}, 
                          {"5/3", "-1/3", "0.222"},
                          {"-3", "0.4", "-0.333"},};
    
    String[][] matrix3 = MatrixCalculator.add(matrix1, matrix2);
    for(String[] row : matrix3) {
      for(String number : row)
        System.out.print(number + ' ');
      System.out.print("\n");
    }
  }
  
}
