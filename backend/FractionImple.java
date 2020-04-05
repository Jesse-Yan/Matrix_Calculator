package backend;

/**
 * This class is Fraction, provides several methods to modify fraction, fraction
 * will be transformed to minimum form when first created
 * 
 * @author Jesse
 *
 */
public class FractionImple implements Fraction {

  private int numerator;
  private int denominator;

  public FractionImple(int numerator, int denominator) {
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
   * @return
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

  @Override
  public String toString() {

    return denominator == 1 ? numerator + "" : numerator + "/" + denominator;
  }

  public FractionImple add(FractionImple other) {

    return new FractionImple(
        this.getNumerator() * other.getDenominator()
            + other.getNumerator() * this.getDenominator(),
        this.getDenominator() * other.getDenominator());
  }

  public FractionImple subtract(FractionImple other) {

    return new FractionImple(
        this.getNumerator() * other.getDenominator()
            - other.getNumerator() * this.getDenominator(),
        this.getDenominator() * other.getDenominator());
  }

  public FractionImple multiply(FractionImple other) {

    return new FractionImple(this.getNumerator() * other.getNumerator(),
        this.getDenominator() * other.getDenominator());
  }

  public FractionImple divide(FractionImple other) {

    return new FractionImple(this.getNumerator() * other.getDenominator(),
        this.getDenominator() * other.getNumerator());
  }

  public int getNumerator() {
    return numerator;
  }

  public int getDenominator() {
    return denominator;
  }
}
