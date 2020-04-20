package application;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * This class tests the Matrix
 * 
 * @author Houming Chen
 *
 */
public class NumericTest {
  
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }
  
  /**
   * integer addition
   */
  @Test
  public void test_integer_addition() {
    try {
      assertEquals(Numeric.of(1).add(1), 2);
      assertEquals(Numeric.of(1).add(1), 2);
      assertEquals(Numeric.of(8).add(7), 15);
      assertEquals(Numeric.of(0).add(1), 1);
      assertEquals(Numeric.of(0).add(-1), -1);
      assertEquals(Numeric.of(-1).add(0), -1);
      assertEquals(Numeric.of(-17).add(0), -17);
      assertEquals(Numeric.of(-3).add(3), 0);
      assertEquals(Numeric.of(3).add(-3), 0);
      assertEquals(Numeric.of(-1).add(-3), -4);
      assertEquals(Numeric.of(1023).add(1), 1024);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer addition with int overflow
   */
  @Test
  public void test_integer_addition_with_overflow() {
    try {
      assertEquals(Numeric.of(2147483647).add(1), 2147483648L);
      assertEquals(Numeric.of(2147483648L).add(-1), 2147483647);
      assertEquals(Numeric.of(2147483647).add(-2147483647), 0);
      assertEquals(Numeric.of(0).add(-2147483648), -2147483648);
      assertEquals(Numeric.of(-2147483648).add(2147483648L), 0);
      assertEquals(Numeric.of(100000000000L).add(200000000000L), 300000000000L);
      assertEquals(Numeric.of(-100000000000L).add(200000000000L), 100000000000L);
      assertEquals(Numeric.of(100000000000L).add(-200000000000L), -100000000000L);
      assertEquals(Numeric.of(-100000000000L).add(-200000000000L), -300000000000L);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * basic subtraction
   */
  @Test
  public void test_basic_subtraction() {
    try {
      assertEquals("2", new Numeric(4).subtract(2).toString());
      assertEquals("2", new Numeric(2).subtract(0).toString());
      assertEquals("2", new Numeric(0).subtract(-2).toString());
      assertEquals("0", new Numeric(-2).subtract(-2).toString());
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * basic multiplication
   */
  @Test
  public void test_basic_multiplication() {
    try {
      assertEquals("8", new Numeric(4).multiply(2).toString());
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * basic divison
   */
  @Test
  public void test_basic_division() {
    try {
      assertEquals("2", new Numeric(4).dividedBy(2).toString());
      assertEquals("-3", new Numeric(6).dividedBy(-2).toString());
      assertEquals("3", new Numeric(-9).dividedBy(-3).toString());
      assertEquals("-9/2", new Numeric(-9).dividedBy(2).toString());
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * basic division - divided by 0
   */
  @Test
  public void test_basic_division_zero_divisor() {
    try {
      try {
        new Numeric(2).dividedBy(0).toString();
        Assert.fail("Did not caught ArithmeticException");
      } catch (ArithmeticException e) {
        // Expected
      }
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * fraction addition
   */
  @Test
  public void test_fraction_addition() {
    try {
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * fraction subtraction
   */
  @Test
  public void test_fraction_subtraction() {
    try {
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * fraction multiplication
   */
  @Test
  public void test_fraction_multiplication() {
    try {
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * fraction division
   */
  @Test
  public void test_fraction_division() {
    try {
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}