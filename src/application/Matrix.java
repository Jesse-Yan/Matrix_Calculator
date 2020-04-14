package application;

/**
 * This class represents a Matrix and defines required operation needed for a
 * Matrix
 * 
 * @author Jesse
 *
 */
public class Matrix implements MatrixADT{

  private Fraction[][] matrix;
  
  /**
   * Zero Matrix
   * @param content
   */
  public Matrix(int row, int column) {
    matrix = new Fraction[row][column];
    for (int i = 0; i < row; i++)
      for (int j = 0; j < column; j++)
      matrix[i][j] = Fraction.of(0);
  }
  
  /**
   * Constructor of the matrix
   * 
   * @param row     the row the matrix has
   * @param column  the column the matrix has
   * @param content the content
   */
  public Matrix(int [][] content) {
    matrix = new Fraction[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        matrix[i][j] = Fraction.of(content[i][j]);
  }
  
  /**
   * Constructor of the matrix
   * 
   * @param row     the row the matrix has
   * @param column  the column the matrix has
   * @param content the content
   */
  public Matrix(Fraction[][] content) {
    matrix = new Fraction[content.length][];
    for (int i = 0; i < content.length; i++)
      matrix[i] = content[i].clone();
  }
  
  @Override
  public int getNumberOfColumn() {
    return matrix[0].length;
  }

  @Override
  public int getNumberOfRow() {
    return matrix.length;
  }

  @Override
  public Fraction getEntry(int row, int column) {
    return matrix[row][column];
  }

  private void additionCheck(MatrixADT other) throws MatrixDimensionsMismatchException{
    if(this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Different number of rows");
    if(this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Different number of columns");
  }
 
  @Override
  public Matrix add(MatrixADT other) throws MatrixDimensionsMismatchException {
    additionCheck(other);
    Matrix answerMatrix = new Matrix(this.matrix);
    for (int i = 0; i < answerMatrix.getNumberOfRow(); i++)
      for (int j = 0; j < answerMatrix.getNumberOfColumn(); j++)
        answerMatrix.matrix[i][j] = answerMatrix.matrix[i][j].add(other.getEntry(i, j));
    return answerMatrix;
  }
  
  @Override
  public MatrixADT subtract(MatrixADT other) throws MatrixDimensionsMismatchException {
    additionCheck(other);
    Matrix answer = new Matrix(this.matrix);
    for (int i = 0; i < answer.getNumberOfRow(); i++)
      for (int j = 0; j < answer.getNumberOfColumn(); j++)
        answer.matrix[i][j] = answer.matrix[i][j].subtract(other.getEntry(i, j));
    return answer;
  }

  private void multipicationCheck(MatrixADT other) throws MatrixDimensionsMismatchException{
    if(this.getNumberOfColumn() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Cannot support multiplication");
  }
  
  @Override
  public MatrixADT multiply(MatrixADT other) throws MatrixDimensionsMismatchException {
    multipicationCheck(other);
    Matrix answer = new Matrix(this.getNumberOfRow(),other.getNumberOfColumn());
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < other.getNumberOfColumn(); j++)
        for (int k = 0; k < this.getNumberOfColumn(); k++)
          answer.matrix[i][j] = answer.matrix[i][j].add(this.getEntry(i, k).multiply(other.getEntry(k, j)));
    return null;
  }

  @Override
  public MatrixADT inverse() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Fraction getDeterminant() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  
  /**
   * Calculate the determinant of the matrix
   * 
   * @return the determinant
   */
  public Fraction determinant() {
    return determinant(matrix);
  }

  /**
   * private helper method to calculate the determinant of a matrix
   * 
   * @param  submatrix
   * @return           Fraction
   */
  private Fraction determinant(Fraction[][] submatrix) {
    if (matrix.length == 1)
      return matrix[0][0];

    if (matrix.length == 2)
      return (matrix[0][0].multiply(matrix[1][1])).subtract(
          matrix[0][1].multiply(matrix[1][0]));

    Fraction det = Fraction.of(0);

    int flag = 0;

    // Recursively calculate the determinant
    for (int i = 0; i < matrix.length; i++) {
      Fraction[][] temp = new Fraction[matrix.length - 1][matrix.length - 1];
      for (int j = 0; j < temp.length; j++) {
        int index = 0;
        for (int j2 = 0; j2 < temp.length; j2++) {
          if (i == index) {
            index++;
          }
          temp[j][j2] = matrix[j + 1][index++];
        }
      }
      det.add(matrix[0][i].multiply(Fraction.of((int) Math.pow(-1, flag++)))
                          .multiply(determinant(temp)));
    }
    return det;
  }

  @Override
  public MatrixADT multiply(Fraction number) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixADT GaussianElimination(MatrixADT other) throws MatrixDimensionsMismatchException {
    // TODO Auto-generated method stub
    return null;
  }
  
}
