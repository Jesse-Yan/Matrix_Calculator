
public class Matrix_Determinant {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
  }


  public static int determinant(int[][] matrix) {

    if (matrix.length == 1)
      return matrix[0][0];

    if (matrix.length == 2)
      return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    long det = 0l;

    int flag = 0;

    for (int i = 0; i < matrix.length; i++) {
      int[][] temp = new int[matrix.length - 1][matrix.length - 1];
      for (int j = 0; j < temp.length; j++) {
        int index = 0;
        for (int j2 = 0; j2 < temp.length; j2++) {
          if (i == index)
            index++;
          temp[j][j2] = matrix[j + 1][index++];
        }
      }
      det += matrix[0][i] * Math.pow(-1, flag++) * determinant(temp);
    }
    return (int) det;
  }
}
