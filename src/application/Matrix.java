package application;

import java.util.Arrays;

/**
 * This class represents a Matrix and defines operations needed for a Matrix.
 * 
 * The field of this class only contains an 2D array that stores the entries of this Matrix.
 * 
 * @author Jesse, Houming Chen
 *
 */
public class Matrix implements MatrixADT {

  /*
   * The entries of the matrix
   */
  private Numeric[][] entry;

  /**
   * 
   * Zero Matrix constructor.
   * 
   * This constructor construct a Zero matrix with a given number of rows and a given number of
   * columns. All entries of the matrix created by this constructor will be zero.
   * 
   * For example, new Matrix(2, 3) will gives {{0, 0, 0}, {0, 0, 0}}
   * 
   * @param numberOfRow    the given number of rows
   * @param numberOfColumn the given number of columns
   */
  public Matrix(int numberOfRow, int numberOfColumn) {
    entry = new Numeric[numberOfRow][numberOfColumn];
    for (int i = 0; i < numberOfRow; i++)
      for (int j = 0; j < numberOfColumn; j++)
        entry[i][j] = new Numeric(0);
  }

  /**
   * This constructor construct a matrix with a given 2D array of Number as content of the matrix.
   * 
   * Fraction and Numeric both extends Number, and a Number can also be Integer, or Double, or ...
   * 
   * @param content a 2D array of Number as content of the matrix
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
   * 
   * A copy constructor
   * 
   * This constructor copies the given matrix to construct the matrix.
   * 
   * @param other a given Matrix used to construct this matrix
   */
  public Matrix(Matrix other) {
    this(other.entry);
  }

  /**
   * A private helper method that generate a n*n identity matrix
   * 
   * @param n a given integer to represent the number of rows and colomns of the identity matirx
   * @return a n*n identity matrix
   * 
   * @see https://en.wikipedia.org/wiki/Identity_matrix
   */
  private static Matrix identityMatrixWithSizeOf(int n) {
    Numeric[][] identityMatirxEntries = new Numeric[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        if (i == j)
          identityMatirxEntries[i][j] = new Numeric(1);
        else
          identityMatirxEntries[i][j] = new Numeric(0);
    return new Matrix(identityMatirxEntries);
  }

  @Override
  public int getNumberOfColumn() {
    return entry[0].length;
  }

  @Override
  public int getNumberOfRow() {
    return entry.length;
  }

  @Override
  public Numeric getEntry(int row, int column) {
    return entry[row][column];
  }

  /**
   * Convert this matrix to string. The entries are converted to string by rows. In each row,
   * numbers are separated by a " ". Rows are separated with "\n".
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
   * Get a deepest copy of the matrix. Which is exactly the same as this matrix.
   * 
   * @return a copy of matrix.
   */
  protected Matrix copy() {
    return new Matrix(entry);
  }

  /**
   * Receives another object (which should be a matrix), and check whether the given matrix is equal
   * to this matrix. Return true if the given object is a martix, and matrices have exactly same
   * dimension, and all entries are equal. Return false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Matrix) {
      try {
        sameDimensionCheck(((Matrix) obj));
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        return false;
      }
      for (int i = 0; i < entry.length; i++)
        for (int j = 0; j < entry[0].length; j++)
          if (!entry[i][j].equals(((Matrix) obj).entry[i][j]))
            return false;
      return true;
    }
    return false;
  }

  /*
   * Return the transpose of the matrix.
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
   * A private helper method that checks whether the given matrix has exactly the same number of
   * rows and the same number of columns with this matrix. If they have exactly the same number of
   * rows and columns, nothing will happen, otherwise this method will throw a
   * MatrixDimensionsMismatchException.
   * 
   * @param other the given matrix
   * @throws MatrixDimensionsMismatchException if the given matrix do not have the exactly same
   *                                           dimensions with this one.
   */
  private void sameDimensionCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Different number of rows");
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Different number of columns");
  }

  /*
   * Add matrices
   * 
   * @see https://en.wikipedia.org/wiki/Matrix_addition#Entrywise_sum
   */
  @Override
  public Matrix add(MatrixADT other) throws MatrixDimensionsMismatchException {
    sameDimensionCheck(other);
    Matrix answerMatrix = copy();
    for (int i = 0; i < answerMatrix.getNumberOfRow(); i++)
      for (int j = 0; j < answerMatrix.getNumberOfColumn(); j++)
        answerMatrix.entry[i][j] = answerMatrix.entry[i][j].add(other.getEntry(i, j));
    return answerMatrix;
  }

  /*
   * Subtract matrices
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
   * @param other the given matrix
   * @throws MatrixDimensionsMismatchException if the given matrix cannot be multiplied on this
   *                                           matrix.
   */
  private void multipicationCheck(MatrixADT other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Cannot support multiplication");
  }

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

  @Override
  public Matrix multiply(Number constant) {
    Matrix answer = copy();
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < this.getNumberOfColumn(); j++)
        answer.entry[i][j] = answer.entry[i][j].multiply(constant);
    return answer;
  }

  @Override
  public Matrix dividedBy(Number constant) {
    Matrix answer = copy();
    for (int i = 0; i < this.getNumberOfRow(); i++)
      for (int j = 0; j < this.getNumberOfColumn(); j++)
        answer.entry[i][j] = answer.entry[i][j].dividedBy(constant);
    return answer;
  }

  /**
   * A private helper method that check whether the matrix is a square matrix. Nothing happens if it
   * is a square matrix, otherwise throws a MatrixDimensionsMismatchException with message.
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
   * A private helper method to get the size of the square matrix.
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
   * A private helper method that helps to calculate the matrix to the power of n when n is a
   * positive integer.
   * 
   * The algorithm used is exponentiation by squaring.
   * 
   * @param n given n, which must be a positive integer
   * @return the matrix to the power of n
   * 
   * @see https://en.wikipedia.org/wiki/Exponentiation_by_squaring
   */
  private Matrix helperpow(int n) {
    if (n < 0)
      throw new IllegalArgumentException("Parameter must be greater than 0.");
    if (n == 1)
      return copy();
    try {
      Matrix matrixPowHalfN = helperpow(n / 2);
      if (n % 2 == 0)
        return matrixPowHalfN.multiply(matrixPowHalfN);
      if (n % 2 == 1)
        return this.multiply(matrixPowHalfN.multiply(matrixPowHalfN));
    } catch (MatrixDimensionsMismatchException e) {
      // Unexpected, since it must be a square matrix.
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Matrix pow(int n) throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if (n == 0)
      return identityMatrixWithSizeOf(N);
    else if (n > 0)
      return helperpow(n);
    else if (n < 0)
      return inverse().helperpow(-n);
    return null;
  }

  /**
   * A private helper method that get the absolute value of a Numeric number
   * 
   * @param n a given numeric number
   * @return the absolute value of n.
   */
  private static Numeric abs(Numeric n) {
    if (n.compareTo(new Numeric(0)) < 0)
      return new Numeric(0).subtract(n);
    return n;
  }

  /**
   * A private helper method that swap two rows of the matrix. That is, swap rowX and rowY.
   * 
   * @param rowX the index of the first row
   * @param rowY the index of the second row
   */
  private void swapRow(int rowX, int rowY) {
    int M = getNumberOfColumn();
    for (int i = 0; i < M; i++) {
      Numeric tmp = entry[rowX][i];
      entry[rowX][i] = entry[rowY][i];
      entry[rowY][i] = tmp;
    }
  }

  /**
   * 
   * This method receives a parameter k, and then it will find the index of the largest number on
   * the Kth column and Lth row (L >= K)
   * 
   * @param k a given k
   * @return the index of the largest number on the Kth column and Lth row (L >= K)
   */
  private int indexOfLargestPivotElement(int k) {
    int pivotRow = k;
    Numeric pivotElement = entry[pivotRow][k];
    for (int i = k + 1; i < getNumberOfRow(); i++)
      if (abs(entry[i][k]).compareTo(pivotElement) > 0) {
        pivotElement = entry[i][k];
        pivotRow = i;
      }
    return pivotRow;
  }

  /**
   * 
   * A private helper method that do partial pivoting at kth row, return whether the row is swapped.
   * 
   * @param k the row to do partial pivoting
   * @return true if rows are swapped during the partial pivoting
   * @throws SingularException if the pivot of this row is 0 and no rows have non-zero pivot can be
   *                           swapped with this row to make the pivot a non-zero number.
   * 
   * @see https://en.wikipedia.org/wiki/Pivot_element#Partial_and_complete_pivoting
   */
  private boolean partialPivoting(int k) throws SingularException {
    int pivotRow = indexOfLargestPivotElement(k);
    Numeric pivotElement = entry[pivotRow][k];
    if (pivotElement.compareTo(new Numeric(0)) == 0)
      throw new SingularException();
    if (pivotRow != k) {
      swapRow(k, pivotRow);
      return true;
    }
    return false;
  }

  /**
   * 
   * The first step of Gaussian-Elimination.
   * 
   * A private helper method that do the forward elimination of the Gaussian Elimination. That is,
   * eliminate the matrix to an Echelon form.
   * 
   * @throws SingularException if the matrix cannot be eliminate into a upper triangle matrix with
   *                           no zero element on the main diagonal.
   * @return true if there are odd number of row swaps, false otherwise.
   */
  private boolean forwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    boolean signChanged = false;
    for (int k = 0; k < N; k++) {
      signChanged = signChanged | partialPivoting(k);
      for (int i = k + 1; i < N; i++) {
        Numeric f = entry[i][k].dividedBy(entry[k][k]);
        for (int j = 0; j < M; j++)
          entry[i][j] = entry[i][j].subtract(entry[k][j].multiply(f));
        entry[i][k] = new Numeric(0);
      }
    }
    return signChanged;
  }

  /**
   * 
   * The second step of Gaussian-Elimination.
   * 
   * A private helper method that do the backward elimination of the Gaussian Elimination. That is,
   * eliminate the Echelon form.
   * 
   * @throws SingularException if the matrix cannot be eliminate into a diagnal triangle matrix with
   *                           no zero element on the main diagonal.
   */
  private void backwardElimination() throws SingularException {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int k = N - 1; k >= 0; k--) {
      for (int i = 0; i < k; i++) {
        Numeric f = entry[i][k].dividedBy(entry[k][k]);
        for (int j = 0; j < M; j++)
          entry[i][j] = entry[i][j].subtract(entry[k][j].multiply(f));
        entry[i][k] = new Numeric(0);
      }
    }
  }

  /**
   * 
   * The third step of Gaussian-Elimination.
   * 
   * Simplify the matrix after elimination. Make sure that all elements on the main diagonal is 1.
   */
  public void simplifyAfterElimination() {
    int N = getNumberOfRow();
    int M = getNumberOfColumn();
    for (int i = 0; i < N; i++) {
      Numeric f = entry[i][i];
      for (int j = 0; j < M; j++)
        entry[i][j] = entry[i][j].dividedBy(f);
    }
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
   * @param startRow    the given start row of the matrix to get the submatirx
   * @param endRow      the given end row of the matrix to get the submatirx
   * @param startColumn the given start column of the matrix to get the submatirx
   * @param endColumn   the given end column of the matrix to get the submatirx
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
   * The given matrix must have the same number of rows with this matrix.
   * 
   * @param other a given matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *                                           rows with this matrix.
   */
  private Matrix augmentMatirx(Matrix other) throws MatrixDimensionsMismatchException {
    if (this.getNumberOfRow() != other.getNumberOfRow())
      throw new MatrixDimensionsMismatchException("Must have same number of rows");
    int N = getNumberOfRow();
    int M1 = this.getNumberOfColumn();
    int M2 = other.getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries = new Numeric[N][M1 + M2];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M1; j++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int j = 0; j < M2; j++) {
        augmentedMatrixEntries[i][j + M1] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  /**
   * A private helper method that connect another matrix to the bottom of this this matrix to
   * construct a new augmented matrix.
   * 
   * The word "augment matrix" usually refers to connect another matrix/vector to the right of the
   * original matrix. This method performs similar functions but it connect another matrix to the
   * bottom of the original matrix. Therefore, it is called augmentMatirxByExtendingColumns.
   * 
   * The given matrix must have the same number of columns with this matrix.
   * 
   * @param other a given matrix
   * @return the augmented matrix
   * @throws MatrixDimensionsMismatchException if the given matrix does not have the same number of
   *                                           columns with this matrix.
   */
  private Matrix augmentMatirxByExtendingColumns(Matrix other)
      throws MatrixDimensionsMismatchException {
    if (this.getNumberOfColumn() != other.getNumberOfColumn())
      throw new MatrixDimensionsMismatchException("Must have same number of columns");
    int N1 = this.getNumberOfRow();
    int N2 = other.getNumberOfRow();
    int M = getNumberOfColumn();
    Numeric[][] augmentedMatrixEntries = new Numeric[N1 + N2][M];
    for (int j = 0; j < M; j++) {
      for (int i = 0; i < N1; i++) {
        augmentedMatrixEntries[i][j] = new Numeric(entry[i][j]);
      }
      for (int i = 0; i < N2; i++) {
        augmentedMatrixEntries[i + N1][j] = new Numeric(other.entry[i][j]);
      }
    }
    return new Matrix(augmentedMatrixEntries);
  }

  /**
   * 
   * This is a private helper method that combines four matrices in to one matrix.
   * 
   * @param leftTop     a given matrix to be the left top part of the combined matrix
   * @param rightTop    a given matrix to be the right top part of the combined matrix
   * @param leftBottom  a given matrix to be the left bottom part of the combined matrix
   * @param rightBottom a given matrix to be the right bottom part of the combined matrix
   * @return the combined matrix
   * @throws MatrixDimensionsMismatchException - if cannot combine due to number of rows or columns
   *                                           mismatches.
   */
  private static Matrix combineMatrix(Matrix leftTop, Matrix rightTop, Matrix leftBottom,
      Matrix rightBottom) throws MatrixDimensionsMismatchException {
    return leftTop.augmentMatirx(rightTop)
        .augmentMatirxByExtendingColumns(leftBottom.augmentMatirx(rightBottom));
  }

  public Matrix[] LUPDecomposition() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if (N == 1)
      return new Matrix[] {identityMatrixWithSizeOf(1), this.copy(), identityMatrixWithSizeOf(1)};

    Matrix A = copy();

    int i = A.indexOfLargestPivotElement(0);
    A.swapRow(0, i);

    Matrix A_bar11 = A.subMatrix(0, 1, 0, 1);
    Matrix A_bar12 = A.subMatrix(0, 1, 1, N);
    Matrix A_bar21 = A.subMatrix(1, N, 0, 1);
    Matrix A_bar22 = A.subMatrix(1, N, 1, N);

    Matrix S22 = A_bar22.subtract(A_bar21.multiply(A_bar12).dividedBy(A_bar11.getEntry(0, 0)));

    Matrix[] tmp = S22.LUPDecomposition();
    Matrix L22 = tmp[0];
    Matrix U22 = tmp[1];
    Matrix P22 = tmp[2];

    Matrix L11 = identityMatrixWithSizeOf(1);
    Matrix U11 = A_bar11.copy();
    Matrix L12 = new Matrix(1, N - 1);
    Matrix U12 = A_bar12.copy();

    Matrix L21 = P22.multiply(A_bar21).dividedBy(A_bar11.getEntry(0, 0));
    Matrix U21 = new Matrix(N - 1, 1);

    Matrix upperPartOfP = new Matrix(1, i).augmentMatirx(identityMatrixWithSizeOf(1))
        .augmentMatirx(new Matrix(1, N - i - 1));
    Matrix lowerPartOfP = P22.subMatrix(0, N - 1, 0, i).augmentMatirx(new Matrix(N - 1, 1))
        .augmentMatirx(P22.subMatrix(0, N - 1, i, N - 1));

    Matrix L = combineMatrix(L11, L12, L21, L22);
    Matrix U = combineMatrix(U11, U12, U21, U22);
    Matrix P = upperPartOfP.augmentMatirxByExtendingColumns(lowerPartOfP);

    return new Matrix[] {L, U, P};
  }


  public Matrix[] LUDecomposition() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    if (N == 1)
      return new Matrix[] {identityMatrixWithSizeOf(1), this.copy()};

    Matrix A = copy();

    Matrix A_bar11 = A.subMatrix(0, 1, 0, 1);
    Matrix A_bar12 = A.subMatrix(0, 1, 1, N);
    Matrix A_bar21 = A.subMatrix(1, N, 0, 1);
    Matrix A_bar22 = A.subMatrix(1, N, 1, N);

    if (A_bar11.getEntry(0, 0).equals(Numeric.of(0)))
      throw new IllegalArgumentException(
          "This Matrix does not have LU decompositions! Try LUP decompostion!");

    Matrix S22 = A_bar22.subtract(A_bar21.multiply(A_bar12).dividedBy(A_bar11.getEntry(0, 0)));

    Matrix[] tmp = S22.LUDecomposition();
    Matrix L22 = tmp[0];
    Matrix U22 = tmp[1];

    Matrix L11 = identityMatrixWithSizeOf(1);
    Matrix U11 = A_bar11.copy();
    Matrix L12 = new Matrix(1, N - 1);
    Matrix U12 = A_bar12.copy();

    Matrix L21 = A_bar21.dividedBy(A_bar11.getEntry(0, 0));
    Matrix U21 = new Matrix(N - 1, 1);

    Matrix L = combineMatrix(L11, L12, L21, L22);
    Matrix U = combineMatrix(U11, U12, U21, U22);

    return new Matrix[] {L, U};
  }



  /**
   * Find the inverse of the matrix by using Gaussian-Elimination on a augmentedMatrix.
   * 
   * @see https://en.wikipedia.org/wiki/Invertible_matrix#Gaussian_elimination
   */
  @Override
  public Matrix inverse() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Matrix augmentedMatrix = augmentMatirx(identityMatrixWithSizeOf(N));
    try {
      augmentedMatrix.forwardElimination();
      augmentedMatrix.backwardElimination();
      augmentedMatrix.simplifyAfterElimination();
    } catch (SingularException singularException) {
      throw new ArithmeticException("The matrix is not invertible!");
    }
    return augmentedMatrix.subMatrix(0, N, N, 2 * N);
  }

  /**
   * If the matrix is a square matrix, return the product of the diagonal.
   * 
   * @return the product of the diagonal
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix.
   */
  private Numeric productOfDiagonal() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Numeric ansNumeric = new Numeric(1);
    for (int i = 0; i < N; i++)
      ansNumeric = ansNumeric.multiply(entry[i][i]);
    return ansNumeric;
  }

  /**
   * Find the determinant of the matrix by using Gaussian-Elimination method.
   * 
   */
  @Override
  public Numeric determinant() throws MatrixDimensionsMismatchException {
    checkSquare();
    Matrix answerMatrix = copy();
    boolean signChanged = false;
    try {
      signChanged = answerMatrix.forwardElimination();
      answerMatrix.backwardElimination();
    } catch (SingularException singularException) {
      return new Numeric(0);
    }
    Numeric ansNumeric = answerMatrix.productOfDiagonal();
    if (signChanged)
      ansNumeric = new Numeric(0).subtract(ansNumeric);
    return ansNumeric;
  }

  /**
   * Calculate the Frobenius norm of the matrix.
   * 
   * That is, find the square root of the sum of the square of all entries.
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
   * 
   * A private helper method that separate the matrix into column vectors.
   * 
   * @return an array of column vectors representing the separated column vectors.
   * 
   */
  public ColumnVector[] toColumnVectors() {
    ColumnVector[] columnVectors = new ColumnVector[getNumberOfColumn()];
    for (int j = 0; j < getNumberOfColumn(); j++) {
      Numeric[] columnContent = new Numeric[getNumberOfRow()];
      for (int i = 0; i < getNumberOfRow(); i++) {
        columnContent[i] = entry[i][j];
      }
      columnVectors[j] = new ColumnVector(columnContent);
    }
    return columnVectors;
  }

  /**
   * 
   * A private static helper method that combine an array of ColumnVector back to a matrix.
   * 
   * @param columnVectors an array of ColumnVector
   * @return a matrix which is generated by combineing the column vectors.
   */
  public static Matrix combineColumnVectorsToMatirx(ColumnVector[] columnVectors) {
    Numeric[][] entry = new Numeric[columnVectors[0].getNumberOfRow()][columnVectors.length];
    for (int i = 0; i < entry.length; i++)
      for (int j = 0; j < entry[0].length; j++)
        entry[i][j] = columnVectors[j].getEntry(i, 0);
    return new Matrix(entry);
  }

  /**
   * Do QR decomposition using Gram–Schmidt process.
   * 
   * @see https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process
   * @see https://en.wikipedia.org/wiki/QR_decomposition#Using_the_Gram%E2%80%93Schmidt_process
   */
  @Override
  public Matrix[] QRDecomposition() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    ColumnVector[] a = toColumnVectors();
    ColumnVector[] u = new ColumnVector[N];
    ColumnVector[] e = new ColumnVector[N];

    for (int i = 0; i < N; i++) {
      u[i] = a[i].copy();
      for (int j = 0; j < i; j++) {
        u[i] = new ColumnVector(u[i].subtract(e[j].multiply(a[i].innerProduct(e[j]))));
      }
      e[i] = new ColumnVector(u[i].dividedBy(u[i].norm()));
    }

    Matrix Q = combineColumnVectorsToMatirx(e);
    Matrix R = Q.transpose().multiply(this);
    return new Matrix[] {Q, R};
  };

  /**
   * A private helper method that get an array of Numeric which are the entries on the diagonal
   * line. The matrix must be square matrix.
   * 
   * @return an array of Numeric which is the enties on the diagonal line of the square matrix
   * @throws MatrixDimensionsMismatchException - if the matrix is not a square matrix
   */
  private Numeric[] diagonal() throws MatrixDimensionsMismatchException {
    int N = getSizeOfSquareMatrix();
    Numeric[] diagnal = new Numeric[N];
    for (int i = 0; i < N; i++)
      diagnal[i] = new Numeric(entry[i][i]);
    return diagnal;
  }

  /**
   * Find the eigenvalue of the matrix by QR algorithm.
   * 
   * @see https://en.wikipedia.org/wiki/QR_algorithm
   */
  @Override
  public Numeric[] eigenValues() throws MatrixDimensionsMismatchException {
    Matrix A = copy(), lastA;
    Matrix[] QRDecomposition;
    do {
      lastA = A.copy();
      QRDecomposition = A.QRDecomposition();
      A = QRDecomposition[1].multiply(QRDecomposition[0]);
    } while (!Arrays.equals(A.diagonal(), lastA.diagonal()));
    return A.diagonal();
  }
}
