package application;

import static org.junit.Assert.*;
import java.util.Random;
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
      assertEquals(Numeric.of(0).add(0), 0);
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
      assertEquals(Numeric.of(1).add(1023), 1024);
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
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer subtraction
   */
  @Test
  public void test_integer_subtraction() {
    try {
      assertEquals(Numeric.of(0).subtract(0), 0);
      assertEquals(Numeric.of(1).subtract(1), 0);
      assertEquals(Numeric.of(8).subtract(7), 1);
      assertEquals(Numeric.of(0).subtract(1), -1);
      assertEquals(Numeric.of(0).subtract(-1), 1);
      assertEquals(Numeric.of(-1).subtract(0), -1);
      assertEquals(Numeric.of(-17).subtract(0), -17);
      assertEquals(Numeric.of(-3).subtract(3), -6);
      assertEquals(Numeric.of(3).subtract(-3), 6);
      assertEquals(Numeric.of(-1).subtract(-3), 2);
      assertEquals(Numeric.of(1023).subtract(1), 1022);
      assertEquals(Numeric.of(1).subtract(1023), -1022);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer addition with random data. Random seed 1.
   */
  @Test
  public void test_integer_addition_random_data() {
    try {
      Random random = new Random(1);
      for(int i = 0; i < 1000; i++) {
        // Use long to avoid overflow.
        long x = random.nextInt();
        long y = random.nextInt();
        long expectedAns = x + y; 
        Numeric calculatedAns = Numeric.of(x).add(y);
        if(!calculatedAns.equals(expectedAns)) {
          fail("Test " + i + " : " + x + "+" + y + ", which should be" + expectedAns + ". " + "But get" + calculatedAns + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns + 1)) {
          fail("Test " + i + " : " + x + "+" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "+" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  /**
   * integer subtraction with overflow
   */
  @Test
  public void test_integer_subtraction_with_overflow() {
    try {
      assertEquals(Numeric.of(-2147483648).subtract(1), -2147483649L);
      assertEquals(Numeric.of(2147483647).subtract(-1), 2147483648L);
      assertEquals(Numeric.of(2147483647).subtract(2147483647), 0);
      assertEquals(Numeric.of(0).subtract(-2147483648), 2147483648L);
      assertEquals(Numeric.of(-2).subtract(2147483647), -2147483649L);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer subtraction with random data. Random seed 1.
   */
  @Test
  public void test_integer_subtraction_random_data() {
    try {
      Random random = new Random(1);
      for(int i = 0; i < 1000; i++) {
        // Use long to avoid overflow.
        long x = random.nextInt();
        long y = random.nextInt();
        long expectedAns = x - y; 
        Numeric calculatedAns = Numeric.of(x).subtract(y);
        if(!calculatedAns.equals(expectedAns)) {
          fail("Test " + i + " : " + x + "-" + y + ", which should be" + expectedAns + ". " + "But get" + calculatedAns + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns + 1)) {
          fail("Test " + i + " : " + x + "-" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "-" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer multiplication
   */
  @Test
  public void test_integer_multiplication() {
    try {
      assertEquals(Numeric.of(0).multiply(0), 0);
      assertEquals(Numeric.of(0).multiply(1), 0);
      assertEquals(Numeric.of(0).multiply(-1), 0);
      assertEquals(Numeric.of(1).multiply(0), 0);
      assertEquals(Numeric.of(-1).multiply(0), 0);
      assertEquals(Numeric.of(-1).multiply(1), -1);
      assertEquals(Numeric.of(-1).multiply(-1), 1);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer multiplication with overflow
   */
  @Test
  public void test_integer_multiplication_with_overflow() {
    try {
      assertEquals(Numeric.of(0).multiply(2147483648L), 0);
      assertEquals(Numeric.of(2147483648L).multiply(0), 0);
      assertEquals(Numeric.of(2147483647).multiply(2147483647), 2147483647L * 2147483647L);
      assertEquals(Numeric.of(2147483647).multiply(-2147483648), 2147483647L * -2147483648L);
      assertEquals(Numeric.of(-2147483648).multiply(2147483647), -2147483648L * 2147483647L);
      assertEquals(Numeric.of(-2147483648).multiply(-2147483648), -2147483648L * -2147483648L);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * integer multiplication with random data. Random seed 1.
   */
  @Test
  public void test_integer_multiplication_random_data() {
    try {
      Random random = new Random(1);
      for(int i = 0; i < 1000; i++) {
        // Use long to avoid overflow.
        long x = random.nextInt();
        long y = random.nextInt();
        long expectedAns = x * y; 
        Numeric calculatedAns = Numeric.of(x).multiply(y);
        if(!calculatedAns.equals(expectedAns)) {
          fail("Test " + i + " : " + x + "*" + y + ", which should be" + expectedAns + ". " + "But get" + calculatedAns + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns + 1)) {
          fail("Test " + i + " : " + x + "*" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "*" + y + ", and get" + Numeric.of(x).add(y) + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
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