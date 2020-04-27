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
  public void test_LUP() {
    try {
      @SuppressWarnings("unused")
      Matrix matrix, expectedL, expectedU, expectedP;

      matrix = new Matrix(new String[][] {{"4", "3"}, {"6", "3"}});

    }catch(

  Exception e)
  {
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
        fail("The matrix is not invertible, but no MatrixArithmeticException thrown");
      } catch (MatrixArithmeticException e) {
        // Expected
      }
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
      @SuppressWarnings("unused")
      MatrixADT matrix, expectedQ, expectedR;
/*
      matrix = new Matrix(new Integer[][] {{12, -51, 4}, {6, 167, -68}, {-4, 24, -41}});

      expectedQ = new Matrix(
          new String[][] {{"6/7", "-69/175", "-58/175"},
              {"3/7", "158/175", "6/175"},
              {"-2/7", "6/35", "-33/35"}});

      expectedR = new Matrix(new Integer[][] {{14, 21, -14}, {0, 175, -70}, {0, 0, 35}});
      
      Matrix Q = matrix.QRDecomposition()[0];
      Matrix R = matrix.QRDecomposition()[1];
      
      System.out.println(Q);
      System.out.println(R);
      System.out.println(Q.multiply(R));
      System.out.println(Q.multiply(Q));
      
      assertEquals(expectedQ, matrix.QRDecomposition()[0]);
      assertEquals(expectedR, matrix.QRDecomposition()[1]);
      
      */

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Test eigenValue
   * 
   */
  @Test
  public void test_EigenValue() {
    try {
      MatrixADT matrix;

      matrix = new Matrix(new Integer[][] {{2, 1}, {1, 2}});

      TreeSet<Numeric> eigenValues = new TreeSet<Numeric>(Arrays.asList(matrix.eigenValues()));

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
  public void test_able_to_compute_EigenValue_using_random() {
    try {
      MatrixADT matrix;
      Numeric[][] randomEntries = new Numeric[3][3];
      Random random = new Random(1);
      int trialTimes = 100;
      int upperBoundOfEntries = 100;
      for(int k = 0; k < trialTimes; k++) {
        for(int i = 0; i < randomEntries.length; i++) {
          for(int j = 0; j < randomEntries[i].length; j++) {
            randomEntries[i][j] = Numeric.of(random.nextInt(upperBoundOfEntries));
          }
        }
        matrix = new Matrix(randomEntries);
        try {
          matrix.eigenValues();
        } catch (ArithmeticException e) {
          System.out.println(matrix);
        }
        
      }
      

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
