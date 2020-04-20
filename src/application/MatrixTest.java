package application;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * This class tests the Matrix
 * 
 * @author Houming Chen
 *
 */
public class MatrixTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}


  /**
   * Test the constructor and getEntry
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
   * Test equal to
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
   * Test addition
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
   * Test subtraction
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
   * Test multiplication
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
   * Test multiplication by constant
   */
  @Test
  public void test_multiplication_by_constant() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{1, 2}, {-4, 0}, {0, -6}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{2, 4}, {-8, 0}, {0, -12}});
      assertEquals(expectedMatrix, matrix1.multiply(2));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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
        fail("The matrix is not invertible, but no ArithmeticException thrown");
      } catch (ArithmeticException arithmeticException) {
        // Expected
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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
            "The matrix is not a square matrix, but MatrixDimensionsMismatchException is not thrown");
      } catch (MatrixDimensionsMismatchException matrixDimensionsMismatchException) {
        // Expected
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_Inverse() {
    try {
      MatrixADT matrix;
      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 5}});
      assertEquals(new Matrix(new Integer[][] {{-5, 2}, {3, -1}}), matrix.inverse());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      try {
        matrix.inverse();
        fail("The matrix is not invertible, but no ArithmeticException thrown");
      } catch (ArithmeticException e) {
        // Expected
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_Vector() {
    try {
      MatrixADT rowVector1, rowVector2, rowVector3;
      rowVector1 = new RowVector(new Integer[] {1, 2, 3, 4});
      rowVector2 = new RowVector(new Integer[] {-1, 0, 1, -2});
      rowVector3 = new RowVector(new Integer[] {0, 2, 4, 2});
      assertEquals(rowVector3, rowVector1.add(rowVector2));
      assertEquals(((Vector) rowVector1).innerProduct((Vector) rowVector2), -6);


      rowVector1 = new ColumnVector(new Integer[] {1, 2, 3, 4});
      rowVector2 = new ColumnVector(new Integer[] {-1, 0, 1, -2});
      rowVector3 = new ColumnVector(new Integer[] {0, 2, 4, 2});
      assertEquals(rowVector3, rowVector1.add(rowVector2));
      assertEquals(((Vector) rowVector2).innerProduct((Vector) rowVector3), 0);


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 
   * Test QR Decomposition
   * 
   * 
   * @see https://en.wikipedia.org/wiki/QR_decomposition#Example
   */
  @Test
  public void test_QRDecomposition() {
    try {
      MatrixADT matrix, expectedQ, expectedR;

      matrix = new Matrix(new Integer[][] {{12, -51, 4}, {6, 167, -68}, {-4, 24, -41}});
      
      expectedQ = new Matrix(
          new Fraction[][] {{new Fraction(6, 7), new Fraction(-69, 175), new Fraction(-58, 175)},
              {new Fraction(3, 7), new Fraction(158, 175), new Fraction(6, 175)},
              {new Fraction(-2, 7), new Fraction(6, 35), new Fraction(-33, 35)}});
      
      expectedR = new Matrix(new Integer[][] {{14, 21, -14}, {0, 175, -70}, {0, 0, 35}});
      assertEquals(expectedQ, matrix.QRDecomposition()[0]);
      assertEquals(expectedR, matrix.QRDecomposition()[1]);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }



}
