package backend;

/**
 * This class is Fraction, provides several methods to modify fraction, fraction
 * will be transformed to minimum form when first created
 * 
 * @author Jesse
 *
 */
public class Fraction {

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
   * Find the greatest common divisor
   * 
   * @param  numerator
   * @param  denominator
   * @return             the greatest Common Divisor
   */
  private int gCD(int numerator, int denominator) {

    int gcdVal;

    numerator = Math.abs(numerator);
    denominator = Math.abs(denominator);
    if (numerator == denominator) {
      gcdVal = numerator;
    } else {
      if (numerator > denominator)
        gcdVal = gCD(numerator - denominator, denominator);
      else
        gcdVal = gCD(numerator, denominator - numerator);
    }
    return gcdVal;
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
}
