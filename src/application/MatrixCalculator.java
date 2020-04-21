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
    String[][] matrixA = {{"1", "2", "-3"}, 
                          {"1/3", "1/2", "-1/1024"},
                          {"0.5", "0.2", "-0.3"},};
    String[][] matrixB = {{"1", "0", "0.1"}, 
                          {"5/3", "0", "-1/3"},
                          {"-3", "0.4", "-0.333"}};
    
    System.out.println("matirxC = matirxA + matrixB");
    String[][] matrixC = MatrixCalculator.add(matrixA, matrixB);
    
    System.out.println("Print matirx C");
    for(String[] row : matrixC) {
      for(String number : row)
        System.out.print(number + ' ');
      System.out.print("\n");
    }
    System.out.print("\n");
    
    System.out.println("matirxD = inverse of matrixB");
    String[][] matrixD = MatrixCalculator.getInverse(matrixB);
    
    System.out.println("Print matirx D");
    for(String[] row : matrixD) {
      for(String number : row)
        System.out.print(number + ' ');
      System.out.print("\n");
    }
  }
  
}
