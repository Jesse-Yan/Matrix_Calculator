package application;

/**
 * This class represents a Matrix and defines required operation needed for a Matrix
 * 
 * @author Jesse
 *
 */
public class Matrix implements MatrixADT {

  private Numeric[][] matrix;

  /**
   * Zero Matrix
   * 
   * @param content
   */
  public Matrix(int row, int column) {
    matrix = new Numeric[row][column];
    for (int i = 0; i < row; i++)
      for (int j = 0; j < column; j++)
        matrix[i][j] = new Numeric(0);
  }

  /**
   * Constructor of the matrix with Integers
   * 
   * @param row the row the matrix has
   * @param column the column the matrix has
   * @param content the content
   */
  public Matrix(int[][] content) {
    matrix = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        matrix[i][j] = new Numeric(content[i][j]);
  }

  /**
   * Constructor of the matrix with Fractions
   * 
   * @param row the row the matrix has
   * @param column the column the matrix has
   * @param content the content
   */
  public Matrix(Fraction[][] content) {
    matrix = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        matrix[i][j] = new Numeric(content[i][j]);
  }

  /**
   * Constructor of the matrix with Doubles
   * 
   * @param row the row the matrix has
   * @param column the column the matrix has
   * @param content the content
   */
  public Matrix(double[][] content) {
    matrix = new Numeric[content.length][content[0].length];
    for (int i = 0; i < content.length; i++)
      for (int j = 0; j < content[0].length; j++)
        matrix[i][j] = new Numeric(content[i][j]);
  }

  /**
   * Constructor of the matrix
   * 
   * @param row the row the matrix has
   * @param column the column the matrix has
   * @param content the content
   */
  public Matrix(Numeric[][] content) {
    matrix = new Numeric[content.length][content[0].length];
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
  public Numeric getEntry(int row, int column) {
    return matrix[row][column];
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        string += matrix[i][j].toString() + " ";
      }
      string += '\n';
    }
    return string;
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
          answer.matrix[i][j] =
              answer.matrix[i][j].add(this.getEntry(i, k).multiply(other.getEntry(k, j)));
    return answer;
  }

  // This should be private. Now it is public just for tests.
  public void forwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int k = 0; k < N; k++) {
      for (int i = k + 1; i < N; i++) {
        
        if(matrix[k][k].compareTo(new Numeric(0)) == 0) {
          throw new SingularException();
        }
        
        /*
         * factor f to set current row kth element to 0, and subsequently remaining kth column to 0
         */
        Numeric f = matrix[i][k].dividedBy(matrix[k][k]);

        /*
         * subtract fth multiple of corresponding kth row element
         */
        for (int j = 0; j < M; j++)
          matrix[i][j] = matrix[i][j].subtract(matrix[k][j].multiply(f));

        /* filling lower triangular matrix with zeros */
        matrix[i][k] = new Numeric(0);
      }
    }
  }

  //This should be private. Now it is public just for tests.
  public void backwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int k = N - 1; k >= 0; k--) {
      for (int i = 0; i < k; i++) {
        
        if(matrix[k][k].compareTo(new Numeric(0)) == 0) {
          throw new SingularException();
        }

        Numeric f = matrix[i][k].dividedBy(matrix[k][k]);

        for (int j = M - 1; j >= 0; j--)
          matrix[i][j] = matrix[i][j].subtract(matrix[k][j].multiply(f));

        matrix[i][k] = new Numeric(0);
      }
    }
  }



  @Override
  public MatrixADT gaussianElimination() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixADT inverse() {
    // TODO Auto-generated method stub
    return null;
  }

  
  private boolean isSquareMatirx() {
     return getNumberOfRow() == getNumberOfColumn();
  }
  
  @Override
  public Numeric getDeterminant() throws MatrixDimensionsMismatchException {
    if(!isSquareMatirx()) {
      throw new MatrixDimensionsMismatchException();
    }
    Matrix answerMatrix = new Matrix(matrix);
    try {
      answerMatrix.forwardElimination();
      answerMatrix.backwardElimination();
    } catch (SingularException singularException) {
      return new Numeric(0);
    }
    Numeric ansNumeric = new Numeric(1);
    for(int i = 0; i < answerMatrix.getNumberOfColumn(); i++) {
      ansNumeric = ansNumeric.multiply(answerMatrix.getEntry(i, i));
    }
      
    return ansNumeric;
  }



  /**
   * Calculate the determinant of the matrix
   * 
   * @return the determinant
   */
  public Numeric determinant() {
    return determinant(matrix);
  }

  /**
   * private helper method to calculate the determinant of a matrix
   * 
   * @param submatrix
   * @return Fraction
   */
  private Numeric determinant(Numeric[][] submatrix) {
    if (matrix.length == 1)
      return matrix[0][0];

    if (matrix.length == 2)
      return (matrix[0][0].multiply(matrix[1][1])).subtract(matrix[0][1].multiply(matrix[1][0]));

    Numeric det = new Numeric(0);

    int flag = 0;

    // Recursively calculate the determinant
    for (int i = 0; i < matrix.length; i++) {
      Numeric[][] temp = new Numeric[matrix.length - 1][matrix.length - 1];
      for (int j = 0; j < temp.length; j++) {
        int index = 0;
        for (int j2 = 0; j2 < temp.length; j2++) {
          if (i == index) {
            index++;
          }
          temp[j][j2] = matrix[j + 1][index++];
        }
      }
      det.add(matrix[0][i].multiply(
          new Numeric(Fraction.of((int) Math.pow(-1, flag++))).multiply(determinant(temp))));
    }
    return det;
  }



}
