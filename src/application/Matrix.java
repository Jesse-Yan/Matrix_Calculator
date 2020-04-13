package application;

/**
 * This class represents a Matrix and defines required operation needed for a
 * Matrix
 * 
 * @author Jesse
 *
 */
public class Matrix {

  private Fraction[][] matrix;

  /**
   * Constructor of the matrix
   * 
   * @param row     the row the matrix has
   * @param column  the column the matrix has
   * @param content the content
   */
  public Matrix(int row, int column, Fraction[]... content) {

    matrix = new Fraction[row][column];
    for (int i = 0; i < row; i++) {
      matrix[row] = content[i];
    }
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
}
