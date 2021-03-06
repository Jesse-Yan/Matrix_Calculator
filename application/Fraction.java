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

/**
 * This class is Fraction, provides several methods to modify fraction, fraction will be transformed
 * to minimum form when first created
 * 
 * @author Jesse, Houming Chen
 *
 */
public class Fraction extends Number implements Comparable<Fraction> {

  /**
   * Serial Version ID
   */
  private static final long serialVersionUID = 4669375951730399546L;

  // Store for numerator and denominator
  private int numerator;
  private int denominator;

  /**
   * Receives a number and turn it to a fraction, only if: 1. it is an integer (Integer or Short) 2.
   * itself is a fraction
   * 
   * Otherwise a ClassCastException will be thrown.
   * 
   * @param number the integer
   * @return the Fraction
   */
  public static Fraction of(Number number) {
    if (number instanceof Integer || number instanceof Short)
      return new Fraction(number.intValue(), 1);
    if (number instanceof Fraction) {
      return new Fraction((Fraction) number);
    }
    throw new ClassCastException("Cannot cast into a Fraction");
  }

  /**
   * Return a Fraction
   * 
   * @param numerator   the numerator of the fraction
   * @param denominator the denominator of the fraction
   * @return the Fraction the returned fraction.
   */
  public static Fraction of(int numerator, int denominator) {
    if (denominator == 0)
      throw new ArithmeticException("/ by zero");
    return new Fraction(numerator, denominator);
  }

  /**
   * Constructor of this Fraction
   * 
   * @param numerator   the numerator of the fraction
   * @param denominator the denominator of the fraction
   */
  public Fraction(int numerator, int denominator) {

    if ((numerator < 0 && denominator < 0) || (numerator > 0 && denominator < 0)) {
      numerator = -numerator;
      denominator = -denominator;
    }
    int gcd = gcd(numerator, denominator);
    this.numerator = numerator / gcd;
    this.denominator = denominator / gcd;
  }

  /**
   * Construct the Fraction with another fraction
   * 
   * @param other a given Fraction instance that is going to be compared with this Fracion
   */
  public Fraction(Fraction other) {
    this.numerator = other.numerator;
    this.denominator = other.denominator;
  }

  /**
   * Find the greatest common divisor
   * 
   * @param numerator   the numerator of the fraction
   * @param denominator the denominator of the fraction
   * @return the greatest Common Divisor
   */
  private int gcd(int numerator, int denominator) {
    numerator = Math.abs(numerator);
    denominator = Math.abs(denominator);
    return gcdHelper(numerator, denominator);
  }

  /**
   * A helper method that find the greatest common divisor of two positive integers recursively
   * 
   * @param numA the first given number
   * @param numB the second given number
   * @return the greatest common divisor of numA and numB
   */
  private int gcdHelper(int numA, int numB) {
    if (numB == 0)
      return numA;
    else
      return gcdHelper(numB, numA % numB);
  }

  /**
   * Defines the String representation of this Fraction
   * 
   * @return String representation of the Fraction
   */
  @Override
  public String toString() {
    return denominator == 1 ? numerator + "" : numerator + "/" + denominator;
  }

  /**
   * Return the int value
   * 
   * @return value the int representation of the Fraction
   */
  @Override
  public int intValue() {
    if (numerator % denominator != 0)
      throw new ClassCastException("Cannot cast to an Integer");
    return numerator / denominator;
  }

  /**
   * Return the long value
   * 
   * @return value the long representation of the Fraction
   */
  @Override
  public long longValue() {
    return numerator / denominator;
  }

  /**
   * Return the float value
   * 
   * @return value the float representation of the Fraction
   */
  @Override
  public float floatValue() {
    return (float) numerator / (float) denominator;
  }

  /**
   * Return the double value
   * 
   * @return value the double representation of the Fraction
   */
  @Override
  public double doubleValue() {
    return (double) numerator / (double) denominator;
  }

  /**
   * Add two Fractions
   * 
   * @param other the Fraction that is going to be added with this Fraction
   * @return the result Fraction after addition.
   */
  public Fraction add(Fraction other) {
    return new Fraction(
        Math.addExact(Math.multiplyExact(this.getNumerator(), other.getDenominator()),
            Math.multiplyExact(other.getNumerator(), this.getDenominator())),
        Math.multiplyExact(this.getDenominator(), other.getDenominator()));
  }

  /**
   * Subtract a Fraction from another one
   * 
   * @param other the Fraction that is going to be subtracted with this Fraction
   * @return the result Fraction after subtraction.
   */
  public Fraction subtract(Fraction other) {
    return new Fraction(
        Math.subtractExact(Math.multiplyExact(this.getNumerator(), other.getDenominator()),
            Math.multiplyExact(other.getNumerator(), this.getDenominator())),
        Math.multiplyExact(this.getDenominator(), other.getDenominator()));
  }

  /**
   * Multiply two Fractions
   * 
   * @param multiplier the Fraction that is going to be multiplied with this Fraction
   * @return the result Fraction after multiplication.
   */
  public Fraction multiply(Fraction other) {
    return new Fraction(Math.multiplyExact(this.getNumerator(), other.getNumerator()),
        Math.multiplyExact(this.getDenominator(), other.getDenominator()));
  }

  /**
   * Divide a Fraction from another one
   * 
   * @param divisor the Fraction that is going to be divided by this Fraction
   * @return the result Fraction after division.
   */
  public Fraction dividedBy(Fraction other) {
    return new Fraction(Math.multiplyExact(this.getNumerator(), other.getDenominator()),
        Math.multiplyExact(this.getDenominator(), other.getNumerator()));
  }

  /**
   * Return the numerator of this Fraction
   * 
   * @return numerator the numerator of this Fraction
   */
  public int getNumerator() {
    return numerator;
  }

  /**
   * Return the denominator of this Fraction
   * 
   * @return denominator the denominator of this Fraction
   */
  public int getDenominator() {
    return denominator;
  }

  /**
   * comparing function that specific comparing fractions.
   * 
   * @return 1 if current Fraction is greater than other -1 if current Fraction is smaller than
   *         other 0 if both Fraction are equals.
   */
  @Override
  public int compareTo(Fraction other) {
    int x = this.getNumerator() * other.getDenominator();
    int y = this.getDenominator() * other.getNumerator();
    if (x > y) {
      return 1;
    }
    if (x < y) {
      return -1;
    } else {
      return 0;
    }
  }
}
