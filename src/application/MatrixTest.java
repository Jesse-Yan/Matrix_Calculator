package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
   * Test the constructor.
   */
  @Test
  public void test000_constructor() {
    try {
      Fraction content[][] = new Fraction[2][3];
      content[0][0] = Fraction.of(-1);
      content[0][1] = Fraction.of(0);
      content[0][2] = Fraction.of(1);
      content[1][0] = Fraction.of(2);
      content[1][1] = Fraction.of(3);
      content[1][2] = Fraction.of(4);
      MatrixADT matrix = new Matrix(content);
      assertEquals(-1, matrix.getEntry(0, 0).toInteger());
      assertEquals(0, matrix.getEntry(0, 1).toInteger());
      assertEquals(1, matrix.getEntry(0, 2).toInteger());
      assertEquals(2, matrix.getEntry(1, 0).toInteger());
      assertEquals(3, matrix.getEntry(1, 1).toInteger());
      assertEquals(4, matrix.getEntry(1, 2).toInteger());
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
      MatrixADT matrix1 = new Matrix(new int[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new int[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT sumMatrix;
      sumMatrix = matrix1.add(matrix2);
      assertEquals(-2, sumMatrix.getEntry(0, 0).toInteger());
      assertEquals(0, sumMatrix.getEntry(0, 1).toInteger());
      assertEquals(0, sumMatrix.getEntry(0, 2).toInteger());
      assertEquals(-2, sumMatrix.getEntry(1, 0).toInteger());
      assertEquals(3, sumMatrix.getEntry(1, 1).toInteger());
      assertEquals(14, sumMatrix.getEntry(1, 2).toInteger());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test subtraction
   */
  @Test
  public void test001_subtraction() {
    try {
      MatrixADT matrix1 = new Matrix(new int[][] {{-1, 0, 1}, {2, 3, 4}});
      MatrixADT matrix2 = new Matrix(new int[][] {{-1, 0, -1}, {-4, 0, 10}});
      MatrixADT differenceMatrix;
      differenceMatrix = matrix1.subtract(matrix2);
      assertEquals(0, differenceMatrix.getEntry(0, 0).toInteger());
      assertEquals(0, differenceMatrix.getEntry(0, 1).toInteger());
      assertEquals(2, differenceMatrix.getEntry(0, 2).toInteger());
      assertEquals(6, differenceMatrix.getEntry(1, 0).toInteger());
      assertEquals(3, differenceMatrix.getEntry(1, 1).toInteger());
      assertEquals(-6, differenceMatrix.getEntry(1, 2).toInteger());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}