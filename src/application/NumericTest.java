package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
   * basic addition
   */
  @Test
  public void test000_basic_addition() {
    try {
      assertEquals("2", new Numeric(1).add(1).toString());
      assertEquals("15", new Numeric(8).add(7).toString());
      assertEquals("1", new Numeric(0).add(1).toString());
      assertEquals("-1", new Numeric(0).add(-1).toString());
      assertEquals("-1", new Numeric(-1).add(0).toString());
      assertEquals("-17", new Numeric(-17).add(0).toString());
      assertEquals("0", new Numeric(-3).add(3).toString());
      assertEquals("0", new Numeric(3).add(-3).toString());
      assertEquals("-4", new Numeric(-1).add(-3).toString());
      assertEquals("1024", new Numeric(1023).add(1).toString());
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * basic subtraction
   */
  @Test
  public void test001_basic_subtraction() {
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
  public void test002_basic_multiplication() {
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
  public void test003_basic_division() {
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
  public void test004_basic_division() {
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
  public void test005_fraction_addition() {
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
  public void test006_fraction_subtraction() {
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
  public void test006_fraction_multiplication() {
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
  public void test006_fraction_division() {
    try {
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}