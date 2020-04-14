package application;

/**
 * This class is Fraction, provides several methods to modify fraction, fraction
 * will be transformed to minimum form when first created
 * 
 * @author Jesse
 *
 */
public class Fraction implements Comparable<Fraction>{

  // Store for numerator and denominator
  private int numerator;
  private int denominator;

  /**
   * Return a Fraction form of integer
   * 
   * @param  number the integer
   * @return        the Fraction
   */
  public static Fraction of(int number) {
    return new Fraction(number, 1);
  }

  /**
   * Return a Fraction
   * 
   * @param  numerator
   * @param  denominator
   * @return             the Fraction
   */
  public static Fraction of(int numerator, int denominator) {
    return new Fraction(numerator, denominator);
  }

  /**
   * Constructor of this Fraction
   * 
   * @param numerator
   * @param denominator
   */
  public Fraction(int numerator, int denominator) {
    if ((numerator < 0 && denominator < 0)
        || (numerator > 0 && denominator < 0)) {
      numerator = -numerator;
      denominator = -denominator;
    }
    int gcd = gCD(numerator, denominator);
    this.numerator = numerator / gcd;
    this.denominator = denominator / gcd;
  }
  
  /**
   * Construct the Fraction with another fraction
   * 
   * @param other
   */
  public Fraction(Fraction other) {
    this.numerator = other.numerator;
    this.denominator = other.denominator;
  }

  /**
   * Find the greatest common divisor
   * 
   * @param  numerator
   * @param  denominator
   * @return             the greatest Common Divisor
   */
  private int gCD(int numerator, int denominator) {
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
    if(numB == 0)
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
   * Return the Integer form of the Fraction
   * 
   * @return Integer
   */
  public Integer toInteger() {
    if(numerator % denominator == 0)
      throw new ClassCastException("Cannot cast to an Integer");
    return numerator / denominator;
  }

  /**
   * Return the Double form of the Fraction
   * 
   * @return Double
   */
  public Double toDouble() {
    return (double) numerator / (double) denominator;
  }

  /**
   * Add two Fractions
   * 
   * @param  adder
   * @return       new Fraction
   */
  public Fraction add(Fraction other) {

    return new Fraction(
        this.getNumerator() * other.getDenominator()
            + other.getNumerator() * this.getDenominator(),
        this.getDenominator() * other.getDenominator());
  }

  /**
   * Subtract a Fraction from another one
   * 
   * @param  subtractor
   * @return            new Fraction
   */
  public Fraction subtract(Fraction other) {

    return new Fraction(
        this.getNumerator() * other.getDenominator()
            - other.getNumerator() * this.getDenominator(),
        this.getDenominator() * other.getDenominator());
  }

  /**
   * Multiply two Fractions
   * 
   * @param  multiplier
   * @return            new Fraction
   */
  public Fraction multiply(Fraction other) {

    return new Fraction(this.getNumerator() * other.getNumerator(),
        this.getDenominator() * other.getDenominator());
  }

  /**
   * Divide a Fraction from another one
   * 
   * @param  divisor
   * @return         new Fraction
   */
  public Fraction divide(Fraction other) {

    return new Fraction(this.getNumerator() * other.getDenominator(),
        this.getDenominator() * other.getNumerator());
  }

  /**
   * Return the numerator of this Fraction
   * 
   * @return numerator
   */
  public int getNumerator() {
    return numerator;
  }

  /**
   * Return the denominator of this Fraction
   * 
   * @return denominator
   */
  public int getDenominator() {
    return denominator;
  }

  @Override
  public int compareTo(Fraction other) {
    int x = this.getNumerator() * other.getDenominator();
    int y = this.getDenominator() * other.getNumerator();
    if(x > y) {
      return 1;
    } if (x < y) {
      return -1;
    } else {
      return 0;
    }
  }
}
