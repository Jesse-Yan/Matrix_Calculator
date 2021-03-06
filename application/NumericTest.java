//////////////////////////////// CS 400 HEADER ////////////////////////////////
//
// Title: Ateam project - Matrix Calculator
// Course: COMP SCI 400, Spring 2020
// Lecturer: Debra Deppeler
//
/////////////////////////////////// AUTHORS ///////////////////////////////////
//
// Chengpo Yan LEC001 xteam186 - cyan46@wisc.edu
// Jinming Zhang, LEC001, x-team132 - jzhang2279@wisc.edu
// Archer Li LEC001 x-team145 - zli885@wisc.edu
// Houming Chen, LEC001, xteam149 - hchen634@wisc.edu
// Chengxu Bian, LEC001, xteam102 - cbian7@wisc.edu
//
///////////////////////////////// SOURCE FILES ////////////////////////////////
//
// Calculator.java, CalculatorTest.java, CalSteps.java, Fraction.java
// Main.java, Matrix.java, MatrixADT.java, MatrixArithmeticException.java, 
// MatrixCalculator.java, MatrixDimensionsMismatchException.java,
// MatrixTest.java, Numeric.java, NumericTest.java, OpeartionParser.java, 
// SequenceSummary.java, SimpleCalculator.java, SingularException.java, 
// Writer.java, styleSheet.css
//
///////////////////////////////// DESCRIPTION /////////////////////////////////
//
// This project "Matrix calculator" by CS400 Ateam 2 aims to help students
// studying linear algebra to understand the calculations of linear algebra 
// better. This "Matrix calculator" can not only do many matrix calculations 
// like matrix multiplication, finding eigenvalues, and LUP, QR, or Cholesky 
// decompositions, but it can also support basic algebra calculations like a 
// normal calculator and analyzing sequence.
// 
// This "Matrix calculator" consists of two parts, a math calculator on the 
// left side, which supports basic algebra calculations and analyzing sequence,
// and a matrix calculator on the right side, which supports calculations of 
// matrices.
//
// For matrix calculations, this "Matrix calculator" also supports file inputs 
// and outputs. The input files should be json files in a specific format, and 
// the output files will also be json files. "Matrix calculator" is also 
// friendly to the computer user who does not have a keyboard. Users can input 
// their data by clicking buttons provided on the user interface.
//
//////////////////////////////////// CREDITS //////////////////////////////////
//
// LUP algorithms:
// https://courses.engr.illinois.edu/cs357/fa2019/references/ref-7-linsys/
//
// QR algorithms:
// https://www-users.cs.umn.edu/~saad/csci5304/FILES/LecN13.pdf
//
// Algorithm for reduction to Hessenberg-form:
// https://math.stackexchange.com/questions/732924/reducing-a-matrix-to-upper-
// hessenberg-form-using-householder-transformations-in
//
// Algorithm for Wilkinson- shift:
// http://web.stanford.edu/class/cme335/lecture5
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class uses junit test to test the {@link Numeric}.
 * 
 * @author Houming Chen
 *
 */
public class NumericTest {
  
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
  
  /**
   * Test basic integer addition.
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
   * Test integer addition with int overflow
   */
  @Test
  public void test_integer_addition_with_overflow() {
    try {
      assertEquals(Numeric.of(2147483647).add(1), 2147483648L);
      assertEquals(Numeric.of(2147483648L).add(-1), 2147483647);
      assertEquals(Numeric.of(2147483647).add(-2147483647), 0);
      assertEquals(Numeric.of(0).add(-2147483649L), -2147483649L);
      assertEquals(Numeric.of(-2147483648).add(2147483648L), 0);
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test integer subtraction
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
   * Test integer addition with random data. Random seed 1.
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
          fail("Test " + i + " : " + x + "+" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(Numeric.of(x).add(y).equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "+" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  /**
   * Test integer subtraction with overflow
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
   * Test integer subtraction with random data. Random seed 1.
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
        if(calculatedAns.equals(expectedAns + 1)) {
          fail("Test " + i + " : " + x + "-" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(calculatedAns.equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "-" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test integer multiplication
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
   * Test integer multiplication with overflow
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
   * Test integer multiplication with random data. Random seed 1.
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
        /** The float error is too large for an int * int **/
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test basic division
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
   * Test integer division with random data. Random seed 1.
   */
  @Test
  public void test_integer_division_random_data() {
    try {
      Random random = new Random(1);
      for(int i = 0; i < 1000; i++) {
        int x = random.nextInt();
        int y = random.nextInt();
        double expectedAns = (double)x / (double)y; 
        Numeric calculatedAns = Numeric.of(new Fraction(x, y));
        if(!calculatedAns.equals(expectedAns)) {
          fail("Test " + i + " : " + x + "*" + y + ", which should be" + expectedAns + ". " + "But get" + calculatedAns + ". ");
        }
        if(calculatedAns.equals(expectedAns + 1)) {
          fail("Test " + i + " : " + x + "*" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns + 1) + ". ");
        }
        if(calculatedAns.equals(expectedAns - 1)) {
          fail("Test " + i + " : " + x + "*" + y + ", and get" + calculatedAns + ", which should equal to " + expectedAns + " but not " + (expectedAns - 1) + ". ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test basic division - divided by 0
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
   * Test fraction addition
   */
  @Test
  public void test_fraction_addition() {
    try {
      assertEquals(Numeric.of(Fraction.of(1)).add(Fraction.of(1)), 2);
      assertEquals(Numeric.of(new Fraction(1, 2)).add(new Fraction(3, 2)), 2);
      assertEquals(Numeric.of(new Fraction(0, 2)).add(new Fraction(0, 2)), 0);
      assertEquals(Numeric.of(new Fraction(1, 2)).add(new Fraction(-3, 2)), -1);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test fraction addition with overflow
   */
  @Test
  public void test_fraction_addition_with_overflow() {
    try {
      assertEquals(Numeric.of(new Fraction(1, 2147483647)).add(new Fraction(1, 2147483646)), 9.3132257526599982E-10);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * fraction division - divided by 0
   */
  @Test
  public void test_fraction_division_zero_divisor() {
    try {
      try {
        new Numeric(new Fraction(3, 2)).dividedBy(new Fraction(1, 3).subtract(new Fraction(1, 3))).toString();
        Assert.fail("Did not caught ArithmeticException");
      } catch (ArithmeticException e) {
        // Expected
      }
      // TODO add more tests
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}