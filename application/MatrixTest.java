//////////////////////////////// CS 400 HEADER ////////////////////////////////
//
// Title: Ateam project - Matrix Calculator
// Course: COMP SCI 400, Spring 2020
// Ateam: ateam2
//
///////////////////////////////// DESCRIPTION /////////////////////////////////
//This project ¡°Matrix calculator¡± by CS400 Ateam 2 aims to help students 
//studying linear algebra to understand the calculations linear algebra better. 
//This ¡°Matrix calculator¡± can not only do many matrix calculations like matrix 
//multiplication, finding eigenvalues, and do LUP, QR, or Cholesky 
//decompositions, but it can also support basic algebra calculations 
//like a normal calculator and analyzing sequence. 
//////////////////////////////////// CREDITS //////////////////////////////////
//
// Method to reduce a matrix to Hessenberg-form:
// https://math.stackexchange.com/questions/732924/reducing-a-matrix-to-upper-
// hessenberg-form-using-householder-transformations-in
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import static org.junit.Assert.fail;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This class uses junit test to test the {@link Matrix}.
 * 
 * @author Houming Chen
 *
 */
public class MatrixTest {

  /**
   * Basic structure of the junit test.
   * 
   * @throws Exception - UNEXPECTED
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * Basic structure of the junit test.
   * 
   * @throws Exception - UNEXPECTED
   */
  @After
  public void tearDown() throws Exception {
  }


  private final int RANDOM_MATRIX_ENTRIES_UPPERBOUND = 100;

  /**
   * This private helper method generate a random matrix by given number of rows and columns and a
   * Random instance.
   * 
   * @param numberOfRows
   * @param numberOfColumns
   * @param random
   * @return
   */
  private MatrixADT randomMatrix(int numberOfRows, int numberOfColumns, Random random) {
    Numeric[][] randomEntries = new Numeric[numberOfRows][numberOfColumns];
    for (int i = 0; i < randomEntries.length; i++) {
      for (int j = 0; j < randomEntries[i].length; j++) {
        randomEntries[i][j] = Numeric.of(random.nextInt(RANDOM_MATRIX_ENTRIES_UPPERBOUND));
      }
    }
    return new Matrix(randomEntries);
  }

  /**
   * 
   * This private helper method checks whether a given matrix is an identity matrix.
   * 
   * @param matrix
   * @return
   */
  private boolean isIdentityMatrix(MatrixADT matrix) {
    for (int i = 0; i < matrix.getNumberOfRow(); i++) {
      for (int j = 0; j < matrix.getNumberOfColumn(); j++) {
        if (i != j && !matrix.getEntry(i, j).isZeroBy(8))
          return false;
        if (i == j && !matrix.getEntry(i, j).equals(Numeric.of(1)))
          return false;
      }
    }
    return true;
  }

  /**
   * Test the constructor and getEntry method of the Matrix.
   */
  @Test
  public void test_constructor_and_getEntry() {
    try {
      MatrixADT matrix = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      assertEquals(-1, matrix.getEntry(0, 0).intValue());
      assertEquals(0, matrix.getEntry(0, 1).intValue());
      assertEquals(1, matrix.getEntry(0, 2).intValue());
      assertEquals(2, matrix.getEntry(1, 0).intValue());
      assertEquals(3, matrix.getEntry(1, 1).intValue());
      assertEquals(4, matrix.getEntry(1, 2).intValue());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test equal to method of the matrix.
   */
  @Test
  public void test_equal_to() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      assertEquals(matrix1, matrix2);
      matrix2 = new Matrix(new Integer[][] {{1, 0, 1}, {2, 3, 4}});
      assertNotEquals(matrix1, matrix2);
      matrix2 = new Matrix(new Integer[][] {{-1, 0}, {2, 3}});
      assertNotEquals(matrix1, matrix2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test addition method of the matrix.
   */
  @Test
  public void test_addition() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{-2, 0, 0}, {-2, 3, 14}});
      assertEquals(expectedMatrix, matrix1.add(matrix2));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test subtraction method of the matrix.
   */
  @Test
  public void test_subtraction() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{0, 0, 2}, {6, 3, -6}});
      assertEquals(expectedMatrix, matrix1.subtract(matrix2));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test multiplication method of the matrix.
   */
  @Test
  public void test_multiplication() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{1, 2}, {-4, 0}, {0, -6}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{4, 3, 0}, {-2, 0, -5}});
      MatrixADT expectedMatrix =
          new Matrix(new Integer[][] {{0, 3, -10}, {-16, -12, 0}, {12, 0, 30}});
      assertEquals(expectedMatrix, matrix1.multiply(matrix2));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test the determinant of the matrix.
   */
  @Test
  public void test_Deteminant() {
    try {
      MatrixADT matrix;
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      assertEquals(0, matrix.determinant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {1, 2, 4}, {1, 3, 5}});
      assertEquals(-1, matrix.determinant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 4}});
      assertEquals(-2, matrix.determinant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}});
      try {
        matrix.determinant();
        fail(
            "The matrix isn't a square matrix, but MatrixDimensionsMismatchException is not thrown");
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        // Expected
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test the inverse of the matrix.
   */
  @Test
  public void test_Inverse() {
    try {
      MatrixADT matrix;
      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 5}});
      assertEquals(new Matrix(new Integer[][] {{-5, 2}, {3, -1}}), matrix.inverse());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      try {
        matrix.inverse();
        fail("The matrix is not invertible, but no MatrixArithmeticException thrown");
      } catch (MatrixArithmeticException e) {
        // Expected
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test the inverse of the matrix by random. Some matrices will be generated randomly by Random
   * (using seed 1). This method will check whether the result of multiplying the matrix to its
   * inverse will result in identity matrix.
   */
  @Test
  public void test_Inverse_by_random() {
    try {
      int matrixSize = 6;
      MatrixADT matrix;
      Random random = new Random(1);
      int trialTimes = 100;
      for (int k = 0; k < trialTimes; k++) {
        matrix = randomMatrix(matrixSize, matrixSize, random);
        if (!isIdentityMatrix(matrix.multiply(matrix.inverse()))) {
          fail("the result of multiplying the matrix to its inverse is not identiy matrix"
              + "\n Matrix: \n" + matrix);
        }
      }
    } catch (

    Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Test QR Decomposition by random. Some matrices will be generated randomly by Random (using seed
   * 1). This method will check whether the result of multiplying decomposed Q matrix to the
   * decompose R matrix will become the original matrix, and whether the Q matrix is orthonormal.
   * 
   */
  @Test
  public void test_QRDecomposition_by_random() {
    try {
      int matrixSize = 6;
      MatrixADT matrix;
      MatrixADT[] QRdecomposition = new MatrixADT[2];
      Random random = new Random(1);
      int trialTimes = 100;
      for (int k = 0; k < trialTimes; k++) {
        matrix = randomMatrix(matrixSize, matrixSize, random);
        QRdecomposition = matrix.QRDecomposition();
        MatrixADT Q = QRdecomposition[0];
        MatrixADT R = QRdecomposition[1];
        if (!isIdentityMatrix(Q.multiply(Q.transpose()))) {
          System.out.println(Q.multiply(Q.transpose()));
          fail("Q is not orthonormal!" + "\n Matrix: \n" + matrix);
        }
        if (!Q.multiply(R).equals(matrix)) {
          fail("Q times R doesn't equal to the original matrix" + "\n Matrix: \n" + matrix);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test the pow method of Matrix.
   */
  @Test
  public void test_pow() {
    try {
      MatrixADT matrix, expectedMatirx;
      matrix = new Matrix(new Integer[][] {{1, 1}, {1, 0}});
      expectedMatirx = new Matrix(new Integer[][] {{55, 34}, {34, 21}});
      assertEquals(expectedMatirx, matrix.pow(9));

      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 4}});
      expectedMatirx = new Matrix(new Integer[][] {{1069, 1558}, {2337, 3406}});
      assertEquals(expectedMatirx, matrix.pow(5));

      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 4}});
      expectedMatirx = new Matrix(new Integer[][] {{1069, 1558}, {2337, 3406}});
      assertEquals(expectedMatirx, matrix.pow(5));

      matrix = new Matrix(new Integer[][] {{1, 1}, {1, 0}});
      expectedMatirx = new Matrix(new Integer[][] {{5, -8}, {-8, 13}});
      assertEquals(expectedMatirx, matrix.pow(-6));

      matrix = new Matrix(new Integer[][] {{1, 1}, {1, 0}});
      expectedMatirx = new Matrix(new Integer[][] {{1, 0}, {0, 1}});
      assertEquals(expectedMatirx, matrix.pow(0));

      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      expectedMatirx =
          new Matrix(new Integer[][] {{468, 576, 684}, {1062, 1305, 1548}, {1656, 2034, 2412}});
      assertEquals(expectedMatirx, matrix.pow(3));

      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      expectedMatirx = new Matrix(new Integer[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
      assertEquals(expectedMatirx, matrix.pow(0));

      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      try {
        matrix.pow(-2);
        fail("The matrix is not invertible, but no MatrixArithmeticException thrown");
      } catch (MatrixArithmeticException arithmeticException) {
        // Expected
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Test the transpose of the matrix.
   */
  @Test
  public void test_Transpose() {
    try {
      MatrixADT matrix, expectedMatrix;

      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}});
      expectedMatrix = new Matrix(new Integer[][] {{1, 4}, {2, 5}, {3, 6}});
      assertEquals(expectedMatrix, matrix.transpose());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Test LUP Decomposition by random. Some matrices will be generated randomly by Random (using
   * seed 1). This method will check whether the result is correct.
   * 
   */
  @Test
  public void test_LUP() {
    try {
      int matrixSize = 6;
      MatrixADT matrix;
      MatrixADT[] LUPdecomposition = new MatrixADT[3];
      Random random = new Random(1);
      int trialTimes = 100;
      for (int k = 0; k < trialTimes; k++) {
        matrix = randomMatrix(matrixSize, matrixSize, random);
        LUPdecomposition = matrix.LUPDecomposition();
        MatrixADT L = LUPdecomposition[0];
        MatrixADT U = LUPdecomposition[1];
        MatrixADT P = LUPdecomposition[2];
        if (P != null) {
          if (!L.multiply(U).equals(P.multiply(matrix))) {
            fail("LU not equal to PA" + "\n Matrix: \n" + matrix);
          }
        } else {
          if (!L.multiply(U).equals(matrix)) {
            fail("LU not equal to PA" + "\n Matrix: \n" + matrix);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * Test the eigenvalue of the matrix.
   */
  @Test
  public void test_Eigenvalue() {
    try {
      MatrixADT matrix;

      matrix = new Matrix(new Integer[][] {{2, 1}, {1, 2}});

      TreeSet<Numeric> eigenValues = new TreeSet<Numeric>(Arrays.asList(matrix.eigenvalues()));

      assertTrue(eigenValues.contains(Numeric.of(1)));
      assertTrue(eigenValues.contains(Numeric.of(3)));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Using random numbers to test whether Matrix class is able to compute eigenvalue.
   * 
   */
  @Test
  public void test_able_to_compute_Eigenvalue_using_random() {
    try {
      MatrixADT matrix;
      Random random = new Random(1);
      int trialTimes = 5;
      for (int k = 0; k < trialTimes; k++) {
        matrix = randomMatrix(3, 3, random);
        try {
          matrix.eigenvalues();
        } catch (Exception e) {
          fail("Cannot compute Eigenvalue" + "\n Matrix: \n" + matrix);
        }
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
