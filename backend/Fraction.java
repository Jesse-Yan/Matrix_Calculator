package backend;

/**
 * This interface defines certain function required by the Fraction
 * 
 * @author Jesse
 *
 */
public interface Fraction {

  /**
   * Add two Fractions
   * 
   * @param adder
   * @return new Fraction
   */
  public Fraction add(Fraction other);
  
  /**
   * Subtract a Fraction from another one
   * 
   * @param subtractor
   * @return new Fraction
   */
  public Fraction subtract(Fraction other);
  
  /**
   * Multiply two Fractions
   * 
   * @param multiplier
   * @return new Fraction
   */
  public Fraction multiply(Fraction other);
  
  /**
   * Divide a Fraction from another one
   * 
   * @param divisor
   * @return new Fraction
   */
  public Fraction divide(Fraction other);
  
  /**
   * Return the numerator of this Fraction
   * 
   * @return numerator
   */
  public int getNumerator();
  
  /**
   * Return the denominator of this Fraction
   * 
   * @return denominator
   */
  public int getDenominator();
}
