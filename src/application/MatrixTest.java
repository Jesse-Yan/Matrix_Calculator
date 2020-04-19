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
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }
  
  
  /**
   * Test the constructor and getEntry
   */
  @Test
  public void test000_constructor_and_getEntry() {
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
   * Test addition
   */
  @Test
  public void test001_addition() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{-2, 0, 0}, {-2, 3, 14}});
      assertEquals(expectedMatrix.toString(), matrix1.add(matrix2).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test subtraction
   */
  @Test
  public void test002_subtraction() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{0, 0, 2}, {6, 3, -6}});
      assertEquals(expectedMatrix.toString(), matrix1.subtract(matrix2).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test multiplication
   */
  @Test
  public void test003_multiplication() {
    try {
      MatrixADT matrix1 = new Matrix(new Integer[][] {{1, 2}, {-4, 0}, {0, -6}});
      MatrixADT matrix2 = new Matrix(new Integer[][] {{4, 3, 0}, {-2, 0, -5}});
      MatrixADT expectedMatrix = new Matrix(new Integer[][] {{0, 3, -10}, {-16, -12, 0}, {12, 0, 30}});
      assertEquals(expectedMatrix.toString(), matrix1.multiply(matrix2).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void test_Deteminant() {
    try {
      MatrixADT matrix;
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
      assertEquals(0, matrix.getDeterminant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {1, 2, 4}, {1, 3, 5}});
      assertEquals(-1, matrix.getDeterminant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2}, {3, 4}});
      assertEquals(-2, matrix.getDeterminant().intValue());
      matrix = new Matrix(new Integer[][] {{1, 2, 3}, {4, 5, 6}});
      try {
        matrix.getDeterminant();
        fail("The matrix is not a square matrix, but MatrixDimensionsMismatchException is not thrown");
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
      assertEquals(new Matrix(new Integer[][] {{-5, 2}, {3, -1}}).toString(), matrix.inverse().toString());
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
  
}