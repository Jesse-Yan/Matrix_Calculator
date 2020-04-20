package application;

/**
 * This class is Fraction, provides several methods to modify fraction, fraction will be transformed
 * to minimum form when first created
 * 
 * @author Jesse
 *
 */
public class Fraction extends Number implements Comparable<Fraction> {

  // Store for numerator and denominator
  private int numerator;
  private int denominator;

  /**
   * Receives a number and turn it to a fraction, only if:
   * 1. it is an integer (Integer or Short)
   * 2. itself is a fraction
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
   * @param numerator the numerator of the fraction
   * @param denominator the denominator of the fraction
   * @return the Fraction the returned fraction.
   */
  public static Fraction of(int numerator, int denominator) {
    if(denominator == 0)
      throw new ArithmeticException("/ by zero");
    return new Fraction(numerator, denominator);
  }

  /**
   * Constructor of this Fraction
   * 
   * @param numerator
   * @param denominator
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
   * @param other
   */
  public Fraction(Fraction other) {
    this.numerator = other.numerator;
    this.denominator = other.denominator;
  }

  /**
   * Find the greatest common divisor
   * 
   * @param numerator
   * @param denominator
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

  @Override
  public int intValue() {
    if (numerator % denominator != 0)
      throw new ClassCastException("Cannot cast to an Integer");
    return numerator / denominator;
  }

  @Override
  public long longValue() {
    return numerator / denominator;
  }

  @Override
  public float floatValue() {
    return (float) numerator / (float) denominator;
  }

  @Override
  public double doubleValue() {
    return (double) numerator / (double) denominator;
  }

  /**
   * Add two Fractions
   * 
   * @param adder
   * @return new Fraction
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
   * @param subtractor
   * @return new Fraction
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
   * @param multiplier
   * @return new Fraction
   */
  public Fraction multiply(Fraction other) {
    return new Fraction(
        Math.multiplyExact(this.getNumerator(), other.getNumerator()),
        Math.multiplyExact(this.getDenominator(), other.getDenominator()));
  }

  /**
   * Divide a Fraction from another one
   * 
   * @param divisor
   * @return new Fraction
   */
  public Fraction dividedBy(Fraction other) {
    return new Fraction(
        Math.multiplyExact(this.getNumerator(), other.getDenominator()),
        Math.multiplyExact(this.getDenominator(), other.getNumerator()));
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
