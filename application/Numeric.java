//////////////////////////////// CS 400 HEADER ////////////////////////////////
//
// Title: Ateam project - Matrix Calculator
// Course: COMP SCI 400, Spring 2020
//
///////////////////////////////// DESCRIPTION /////////////////////////////////
//
//
//
//////////////////////////////////// CREDITS //////////////////////////////////
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Pattern;

/**
 * The instance of this Numeric class represents a number, which can be a Integer, or a
 * {@link Fraction}, or a Double. It supports calculations like addition and subtraction, in which
 * the number will be cast into proper format automatically to do the calculation.
 * 
 * Since the main goal for this program is to help students learning linear algebra to understand
 * linear algebra better. The matrix supports calculations and outputs in Fractions, which will be
 * easier and more convenient for students to understand. However, not all calculations of matrix
 * can be done in fractions. Therefore, a {@link Numeric} class is written to provide a flexible way
 * to store numbers that can be either be an integer, or a fraction, or a double value.
 * 
 * Therefore, a instance of this class is always in one of the tree states: Integer states, Fraction
 * states, and Double states. In Integer state, the values are stored in Integer, which means the
 * value of the numeric is an integer. In Fraction state, the values are stored in Integer, which
 * means the value of the numeric is a fraction number. In Double state, the values are stored in
 * Integer, which means the value of the numeric is a decimal number. This is implemented by having
 * a {@link Number} instance in its field. Since the {@link Number} is an abstract class. The
 * instance can be either an instance of Integer, or Fraction, or Double. Methods in this class will
 * have different behaviors when this instance is in different types.
 * 
 * To do calculations, Integer can be cast to Fraction, and both Integer and Fraction can be cast to
 * Double. (Integer -> Fraction -> Double)
 * 
 * This Numeric class is an immutable class. That is, all public methods won't change the the state
 * or content of a Numeric instance. (However some private methods might change)
 * 
 * @author Houming Chen
 *
 */
public class Numeric extends Number implements Comparable<Numeric> {


  /**
   * Serial ID
   */
  private static final long serialVersionUID = 6599360948559127720L;

  /**
   * Since there are float error. Two decimal numbers are considered the same number if their first
   * 12 significant digits are same and have same exponent number.
   */
  final static int SIGNIFICANT_FIGURE_FOR_COMPARISON = 12;

  /**
   * For a inputed decimal number, if it has less than 12 digits, it will be turned into a fraction
   * for computation.
   */
  final static int SIGNIFICANT_FIGURE_FOR_INPUT_PARSE_FRACTION = 12;

  /**
   * Number of significant figures for decimal number output.
   */
  public static int outputSignificantFigures = 5;

  /**
   * The only non-static field for this class. This is a private Object representing the number,
   * which can only be a Integer, or a Fraction, or a Double. It represents the value of this
   * Numeric number.
   */
  private Number number;

  /**
   * The constructor to construct this Numeric instance with an given {@link Number}, the Numeric
   * will be casted into 1 of the 3 states Integer states, Fraction states, or Double states depend
   * on the type of the given {@link Number}.
   * 
   * @param integerNumber the given number
   */
  public Numeric(Number number) {
    if (number instanceof Numeric) { // This situation servers as a copy constructor
      this.number = ((Numeric) number).number;
    } else if (number instanceof Fraction) {
      try { // Try to turn fraction back to integer
        this.number = number.intValue();
      } catch (ClassCastException e) {
        this.number = number; // Failed
      }
    } else if (number instanceof Long) {
      if (Integer.MIN_VALUE <= number.longValue() && number.longValue() <= Integer.MAX_VALUE) {
        // Try to turn the Long type in to Integer type
        this.number = number.intValue();
      } else { // Failed
        this.number = number.doubleValue();
      }
    } else if (number instanceof Integer || number instanceof Short) {
      this.number = number.intValue(); // Integer state
    } else {
      this.number = number.doubleValue(); // Double state
    }
  }

  /**
   * Return whether this Numeric is in the Integer state.
   * 
   * @return true if this Numeric is in the Integer state, and false otherwise.
   */
  public boolean isInteger() {
    return number instanceof Integer;
  }

  /**
   * Return whether this Numeric is in the Fraction state.
   * 
   * @return true if this Numeric is in the Fraction state, and false otherwise.
   */
  public boolean isFraction() {
    return number instanceof Fraction;
  }

  /**
   * Return whether this Numeric is in the Double state.
   * 
   * @return true if this Numeric is in the Double state, and false otherwise.
   */
  public boolean isDouble() {
    return number instanceof Double;
  }

  /**
   * 
   * Using regular expression to detect whether a given string is a decimal number.
   * 
   * @param string a given string.
   * @return true if the given string is a decimal number.
   */
  private static boolean theStringIsDouble(String string) {
    Pattern pattern = Pattern.compile("^-?[0-9]+\\.[0-9]+$");
    return pattern.matcher(string).matches();
  }

  /**
   * 
   * Using regular expression to detect whether a given string is a integer number.
   * 
   * @param string a given string.
   * @return true if the given string is a integer number.
   */
  private static boolean theStringIsInteger(String string) {
    Pattern pattern = Pattern.compile("^-?[0-9]+$");
    return pattern.matcher(string).matches();
  }

  /**
   * A private helper method that calculates ten to the power of the given exponent.
   * 
   * @param exponent - the given exponent
   * @return ten to the power of the exponent.
   */
  private int tenPow(int exponent) {
    int ans = 1;
    for (int i = 0; i < exponent; i++)
      ans *= 10;
    return ans;
  }

  /**
   * A private helper method that checks leading zeros in the given String representing number.
   * Throws NumberFormatException with message if the String has leading zeros.
   * 
   * @param string - a given String
   */
  private void leadingZeroCheck(String string) {
    if (string.length() > 1 && string.charAt(0) == '0' && string.charAt(1) != '.') {
      throw new NumberFormatException("Sorry, the given number " + string
          + " has leading zeros! Please avoid having leading zeros in the input numbers]");
    }
    if (string.length() > 2 && string.charAt(0) == '-' && string.charAt(1) == '0'
        && string.charAt(2) != '.') {
      throw new NumberFormatException("Sorry, the given number " + string
          + " has leading zeros! Please avoid having leading zeros in the input numbers]");
    }
  }

  /**
   * A private helper method that try to convert an integer formatted string to integer. Throws
   * NumberFormatException with message if the integer is illegal in this calculator (having leading
   * zeros or out of range [-100000, 100000]).
   * 
   * @param string a given integer formatted string
   * @return a Integer instance that has value converted from the string.
   */
  private Integer turnStringToInteger(String string) {
    leadingZeroCheck(string);
    if (string.length() > 8) {
      if (string.charAt(0) == '-')
        throw new NumberFormatException("Sorry, the given number " + string
            + " is too small! Please make sure that all the input numbers are in range [-100000, 100000]");
      throw new NumberFormatException("Sorry, the given number " + string
          + " is too big! Please make sure that all the input numbers are in range [-100000, 100000]");
    }

    Integer answerInteger = (Integer) Integer.parseInt(string);
    if (answerInteger.intValue() > 100000)
      throw new NumberFormatException("Sorry, the given number " + string
          + " is too big! Please make sure that all the input numbers are in range [-100000, 100000]");
    if (answerInteger.intValue() < -100000)
      throw new NumberFormatException("Sorry, the given number " + string
          + " is too small! Please make sure that all the input numbers are in range [-100000, 100000]");
    return answerInteger;
  }

  /**
   * A private helper method that checks whether a decimal formatted string represents a legal
   * decimal number. (No leading zeros, in range [-100000, 100000], and has no more than 3 decimal
   * digits.) Throws NumberFormatException with message if it is not legal.
   * 
   * @param string a given decimal formatted string
   */
  private void checkDouble(String string) {
    leadingZeroCheck(string);
    int decimalPointIndex = string.indexOf(".");
    int decimalPlaces = string.length() - decimalPointIndex - 1;
    if (decimalPointIndex > 8) {
      if (string.charAt(0) == '-')
        throw new NumberFormatException("Sorry, the given number " + string
            + " is too small! Please make sure that all the input numbers are in range [-100000, 100000]");
      throw new NumberFormatException("Sorry, the given number " + string
          + " is too big! Please make sure that all the input numbers are in range [-100000, 100000]");
    }
    if (decimalPlaces > 3)
      throw new NumberFormatException(
          "Sorry, the given integer " + string + " has more than 3 decimal places!"
              + " Please make sure that all the input numbers has no more than 3 decimal places.");
    double value = Double.parseDouble(string);
    if (value > 100000)
      throw new NumberFormatException("Sorry, the given value " + string
          + " is too big! Please make sure that all the input numbers are in range [-100000, 100000]");
    if (value < -100000)
      throw new NumberFormatException("Sorry, the given value " + string
          + " is too small! Please make sure that all the input numbers are in range [-100000, 100000]");
  }

  /**
   * The constructor to construct this Numeric instance with an given String, the Numeric will be
   * casted into 1 of the 3 states Integer states, Fraction states, or Double states depend on the
   * format of the given String.
   * 
   * @param string the given String
   */
  public Numeric(String string) {
    if (theStringIsInteger(string)) {
      number = turnStringToInteger(string);
    } else if (theStringIsDouble(string)) {
      checkDouble(string);
      int decimalPointIndex = string.indexOf(".");
      int decimalPlaces = string.length() - decimalPointIndex - 1;
      if (string.length() - 1 <= SIGNIFICANT_FIGURE_FOR_INPUT_PARSE_FRACTION) {
        // try to turn to Fraciton.
        boolean negative = (string.charAt(0) == '-');
        String part[] = string.split("\\.");
        int integerPart = Integer.parseInt(part[0]);
        if (negative)
          part[1] = "-" + part[1];
        int decimalPart = Integer.parseInt(part[1]);
        if (decimalPart == 0)
          number = Integer.valueOf(integerPart);
        number = new Fraction(decimalPart, tenPow(decimalPlaces)).add(Fraction.of(integerPart));
      } else {
        number = Double.parseDouble(string);
      }
    } else {
      throw new NumberFormatException("Your input may contain invalid characters or empty");
    }
  }

  /**
   * Generate a Numeric object with a given number
   * 
   * @param number a given number
   * @return a constructed Numeric object
   */
  public static Numeric of(Number number) {
    return new Numeric(number);
  }

  /**
   * Generate a Numeric object with a given string
   * 
   * @param number a given number
   * @return a constructed Numeric object
   */
  public static Numeric of(String string) {
    return new Numeric(string);
  }

  /**
   * Get the sum of this Numeric Instance and another given Number Instance.
   * 
   * In this method, the given Number instance will be convert to Numeric, and the addition will
   * depends on the states of the two Numerics. Numerics can only do calculations if they are in the
   * same state. Therefore, one of the Numeric might be casted down in the order (Integer ->
   * Fraction -> Double) to finish the calculation. However, both of the this instance and the given
   * instance won't be changed during this method. The cast process are doing on the copies of them.
   * 
   * @param other other the other given Numeric Instance
   * @return the sum
   */
  public Numeric add(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        double a = thisNum.number.doubleValue();
        double b = otherNum.number.doubleValue();
        if (Double.valueOf(round(a)).compareTo(Double.valueOf(round(-b))) == 0)
          return Numeric.of(0);
        return new Numeric(a + b);
      }
      if (thisNum.number instanceof Fraction || otherNum.number instanceof Fraction) {
        try {
          return new Numeric(Fraction.of(thisNum.number).add(Fraction.of(otherNum.number)));
        } catch (ArithmeticException arithmeticException) {
          if (arithmeticException.getMessage().equals("integer overflow")) {
            return new Numeric(thisNum.number.doubleValue() + otherNum.number.doubleValue());
          } else
            throw arithmeticException;
        }
      }
      if (thisNum.number instanceof Integer || otherNum.number instanceof Integer) {
        return new Numeric(thisNum.number.longValue() + otherNum.number.longValue());
      }

      throw new ClassCastException(thisNum.number.getClass() + ":" + otherNum.number.getClass()
          + ": Cannot cast to Integer or Double or Fraction");
    }
    return this.add(new Numeric(other));
  }

  /**
   * Get the difference of this Numeric Instance and another given Number Instance.
   * 
   * In this method, the given Number instance will be convert to Numeric, and the subtraction will
   * depends on the states of the two Numerics. Numerics can only do calculations if they are in the
   * same state. Therefore, one of the Numeric might be casted down in the order (Integer ->
   * Fraction -> Double) to finish the calculation. However, both of the this instance and the given
   * instance won't be changed during this method. The cast process are doing on the copies of them.
   * 
   * @param other other the other given Numeric Instance
   * @return the difference
   */
  public Numeric subtract(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        double a = thisNum.number.doubleValue();
        double b = otherNum.number.doubleValue();
        if (Double.valueOf(round(a)).compareTo(Double.valueOf(round(b))) == 0)
          return Numeric.of(0);
        return new Numeric(a - b);
      }
      if (thisNum.number instanceof Fraction || otherNum.number instanceof Fraction) {
        try {
          return new Numeric(Fraction.of(thisNum.number).subtract(Fraction.of(otherNum.number)));
        } catch (ArithmeticException arithmeticException) {
          if (arithmeticException.getMessage().equals("integer overflow")) {
            return new Numeric(thisNum.number.doubleValue() - otherNum.number.doubleValue());
          } else
            throw arithmeticException;
        }
      }
      if (thisNum.number instanceof Integer || otherNum.number instanceof Integer) {
        return new Numeric(thisNum.number.longValue() - otherNum.number.longValue());
      }
      throw new ClassCastException(
          "Sorry, the Numeric cannot be casted" + " to Integer or Double or Fraction");
    }
    return this.subtract(new Numeric(other));
  }

  /**
   * Get the product of this Numeric Instance and another given Number Instance.
   * 
   * In this method, the given Number instance will be convert to Numeric, and the multiplication
   * will depends on the states of the two Numerics. Numerics can only do calculations if they are
   * in the same state. Therefore, one of the Numeric might be casted down in the order (Integer ->
   * Fraction -> Double) to finish the calculation. However, both of the this instance and the given
   * instance won't be changed during this method. The cast process are doing on the copies of them.
   * 
   * @param other other the other given Numeric Instance
   * @return the product
   */
  public Numeric multiply(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if ((thisNum.number instanceof Fraction || thisNum.number instanceof Integer)
          && thisNum.equals(Numeric.of(0))) {
        return new Numeric(0);
      }
      if ((otherNum.number instanceof Fraction || otherNum.number instanceof Integer)
          && otherNum.equals(Numeric.of(0))) {
        return new Numeric(0);
      }
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        return new Numeric(thisNum.number.doubleValue() * otherNum.number.doubleValue());
      }
      if (thisNum.number instanceof Fraction || otherNum.number instanceof Fraction) {
        try {
          return new Numeric(Fraction.of(thisNum.number).multiply(Fraction.of(otherNum.number)));
        } catch (ArithmeticException arithmeticException) {
          if (arithmeticException.getMessage().equals("integer overflow")) {
            return new Numeric(thisNum.number.doubleValue() * otherNum.number.doubleValue());
          } else
            throw arithmeticException;
        }
      }
      if (thisNum.number instanceof Integer || otherNum.number instanceof Integer) {
        return new Numeric(thisNum.number.longValue() * otherNum.number.longValue());
      }
      throw new ClassCastException(
          "Sorry, the Numeric cannot be casted" + " to Integer or Double or Fraction");
    }
    return this.multiply(new Numeric(other));
  }


  /**
   * Get the quotient of this Numeric Instance and another given Number Instance.
   * 
   * In this method, the given Number instance will be convert to Numeric, and the division will
   * depends on the states of the two Numerics. Numerics can only do calculations if they are in the
   * same state. Therefore, one of the Numeric might be casted down in the order (Integer ->
   * Fraction -> Double) to finish the calculation. However, both of the this instance and the given
   * instance won't be changed during this method. The cast process are doing on the copies of them.
   * 
   * @param other other the other given Numeric Instance
   * @return the quotient
   */
  public Numeric dividedBy(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if ((thisNum.number instanceof Fraction || thisNum.number instanceof Integer)
          && thisNum.equals(Numeric.of(0))) {
        return new Numeric(0);
      }
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        return new Numeric(thisNum.number.doubleValue() / otherNum.number.doubleValue());
      }
      if (thisNum.number instanceof Fraction || otherNum.number instanceof Fraction) {
        try {
          return new Numeric(Fraction.of(thisNum.number).dividedBy(Fraction.of(otherNum.number)));
        } catch (ArithmeticException arithmeticException) {
          if (arithmeticException.getMessage().equals("integer overflow")) {
            return new Numeric(thisNum.number.doubleValue() / otherNum.number.doubleValue());
          } else
            throw arithmeticException;
        }
      }
      if (thisNum.number instanceof Integer || otherNum.number instanceof Integer) {
        return new Numeric(new Fraction(thisNum.number.intValue(), otherNum.number.intValue()));
      }
      throw new ClassCastException(
          "Sorry, the Numeric cannot be casted" + " to Integer or Double or Fraction");
    }
    return this.dividedBy(new Numeric(other));
  }

  /**
   * 
   * A private helper method that checks whether the given integer x is a perfect square number
   * 
   * @param x a given integer
   * @return true if x is a perfect square number, false otherwise.
   */
  private static boolean isPerfectSquare(int x) {
    double sqrt = Math.sqrt(x);
    int sqrtInt = (int) Math.floor(sqrt);
    return sqrtInt * sqrtInt == x;
  }

  /**
   * 
   * A private helper method that get the square root of a perfect square number. The parameter
   * should be a perfect square root number.
   * 
   * @param x a given integer
   * @return a integer which is the square root of x.
   */
  private static Integer perfectSquareRoot(int x) {
    double sqrt = Math.sqrt(x);
    int sqrtInt = (int) Math.floor(sqrt);
    return sqrtInt;
  }

  /**
   * 
   * Get the square root of this Numeric instance. This method will try to gives the answer with a
   * Numeric in Integer state or Fraction state. However, not all square roots can be represented in
   * 
   * @return the square root of this Numeric instance.
   */
  public Numeric sqrt() {
    Numeric thisNum = new Numeric(this);
    if (thisNum.compareTo(Numeric.of(0)) < 0)
      throw new ArithmeticException("Sorry, the result is not all real numbers.");
    if (thisNum.number instanceof Integer && isPerfectSquare((Integer) thisNum.number)) {
      return new Numeric(perfectSquareRoot((Integer) thisNum.number));
    }
    if (thisNum.number instanceof Fraction
        && isPerfectSquare(((Fraction) thisNum.number).getNumerator())
        && isPerfectSquare(((Fraction) thisNum.number).getDenominator())) {
      return new Numeric(new Fraction(perfectSquareRoot(((Fraction) thisNum.number).getNumerator()),
          perfectSquareRoot(((Fraction) thisNum.number).getDenominator())));
    } else {
      return new Numeric(Math.sqrt(thisNum.number.doubleValue()));
    }
  }

  /**
   * Round a given decimal number to let it having the given significant figures.
   * 
   * @param value            - a given double representing the decimal number
   * @param significatFigure - a given int representing the significant figures
   * @return the rounded decimal number in double
   */
  private static double roundWithSignificantFigure(double value, int significantFigure) {
    if (Double.isInfinite(value))
      throw new ArithmeticException("Sorry, the absolute value of the result is too big,"
          + " so we cannot calculate and print the result.");
    if (Double.isNaN(value))
      throw new ArithmeticException(
          "Sorry, the absolute value of the result is too big or too small,"
              + " so we cannot calculate and print the result.");
    BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
    bigDecimal = bigDecimal.round(new MathContext(significantFigure));
    return bigDecimal.doubleValue();
  }

  /**
   * Round a given double to having at most {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}}
   * significant figures.
   * 
   * @param value a given double
   * @return the rounded double
   */
  private static double round(double value) {
    return roundWithSignificantFigure(value, SIGNIFICANT_FIGURE_FOR_COMPARISON);
  }

  /**
   * Compare this instance to another Numeric instance to implement Comparable<Numeric>. Since there
   * might be floating error during the calculations, two Double state Numeric instances are
   * considered equal if they are the same after rounding to having
   * {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}} number of significant digits.
   * 
   * @param other - a given Numeric instance that is going to be compared with this Numeric
   *              instance.
   * @return a negative integer, zero, or a positive integer as this Numeric instance is less than,
   *         equal to, or greater than the given Numeric instance, when comparing
   *         {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}} number of significant digits
   */
  @Override
  public int compareTo(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return Double.valueOf(round(thisNum.number.doubleValue()))
          .compareTo(Double.valueOf(round(other.number.doubleValue())));
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return (Fraction.of(thisNum.number).compareTo(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return Integer.valueOf(thisNum.number.intValue())
          .compareTo(Integer.valueOf(other.number.intValue()));
    }
    throw new ClassCastException(
        "Sorry, the Numeric cannot be casted" + " to Integer or Double or Fraction");
  }

  /**
   * Compare this instance to another Numeric instance to implement Comparable<Numeric>. Since there
   * might be floating error during the calculations, two Double state Numeric instances are
   * considered equal if they are the same after rounding to having a given number of significant
   * digits specified by the parameter digits.
   * 
   * @param other  - a given Numeric instance that is going to be compared with this Numeric
   *               instance
   * @param digits - the number of significant digits that is used for comparison
   * @return a negative integer, zero, or a positive integer as this Numeric instance is less than,
   *         equal to, or greater than the given Numeric instance when comparing a given number of
   *         significant digits
   */
  public int compareTo(Numeric other, int digits) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return Double.valueOf(roundWithSignificantFigure(thisNum.number.doubleValue(), digits))
          .compareTo(
              Double.valueOf(roundWithSignificantFigure(other.number.doubleValue(), digits)));
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return (Fraction.of(thisNum.number).compareTo(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return Integer.valueOf(thisNum.number.intValue())
          .compareTo(Integer.valueOf(other.number.intValue()));
    }
    throw new ClassCastException(
        "Sorry, the Numeric cannot be casted" + " to Integer or Double or Fraction");
  }

  /**
   * Check whether this instance is equal to another Numeric instance. Since there might be floating
   * error during the calculations, two Double state Numeric instances are considered equal if they
   * are the same after rounding to having {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}} number
   * of significant digits.
   * 
   * @param other - a given Numeric instance that is going to be compared with this Numeric
   *              instance.
   * @return true if the given Numeric instance is equal to this Numeric instance in comparing
   *         {@link Numeric#SIGNIFICANT_FIGURE_FOR_COMPARISON}} number of significant digits
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Numeric)
      return compareTo((Numeric) obj) == 0;
    else if (obj instanceof Number)
      return compareTo(new Numeric((Number) obj)) == 0;
    return false;
  }

  /**
   * Check whether this instance is equal to another Numeric instance. Since there might be floating
   * error during the calculations, two Double state Numeric instances are considered equal if they
   * are the same after rounding to having a given number of significant digits specified by the
   * parameter digits.
   * 
   * @param other  - a given Numeric instance that is going to be compared with this Numeric
   *               instance.
   * @param digits - the number of significant digits that is used for comparison
   * @return true if the given Numeric instance is equal to this Numeric instance in comparing a
   *         given number of significant digits
   */
  public boolean equals(Object obj, int digits) {
    if (obj instanceof Numeric)
      return compareTo((Numeric) obj, digits) == 0;
    else if (obj instanceof Number)
      return compareTo(new Numeric((Number) obj)) == 0;
    return false;
  }

  /**
   * 
   * Check whether this instance is equal to another Numeric instance.
   * 
   * The word "mathematically" means that, unlike the method {@link Numeric#equals(Object)}, this
   * method will always return false if any of the two compared instance is in decimal state. That
   * is, only when this matrix and the given matrix is in Fraction or Integer State, there are
   * possibilities that the method will compare the two object and returns true if they are equal.
   * 
   * @param obj - a given Numeric instance that is going to be compared with this Numeric instance.
   * @return true if this and the given Numeric instance are not in Double state and has same value.
   */
  public boolean mathematicallyEquals(Object obj) {
    if (this.number instanceof Double)
      return false;
    if (obj instanceof Numeric) {
      if (((Numeric) obj).number instanceof Double)
        return false;
      return compareTo((Numeric) obj) == 0;
    } else if (obj instanceof Number)
      return mathematicallyEquals(new Numeric((Number) obj));
    return false;
  }

  /**
   * Returns the value of the Numeric as an {@code int}.
   *
   * @return the numeric value represented by this object after conversion to type {@code int}.
   */
  @Override
  public int intValue() {
    if (number instanceof Integer) {
      return ((Integer) number);
    }
    throw new ClassCastException("Cannot cast to an Integer");
  }

  /**
   * Returns the value of the Numeric number as a {@code long}.
   *
   * @return the numeric value represented by this object after conversion to type {@code long}.
   */
  @Override
  public long longValue() {
    if (number instanceof Integer) {
      return ((Integer) number).longValue();
    }
    throw new ClassCastException("Cannot cast to an Long");
  }

  /**
   * Returns the value of the Numeric number as a {@code float}.
   *
   * @return the numeric value represented by this object after conversion to type {@code float}.
   */
  @Override
  public float floatValue() {
    return (float) number.doubleValue();
  }

  /**
   * Returns the value of the Numeric number as a {@code double}.
   *
   * @return the numeric value represented by this object after conversion to type {@code double}.
   */
  @Override
  public double doubleValue() {
    return number.doubleValue();
  }

  /**
   * Return a new Numeric instance which has a same value with this instance but is in the Double
   * state. (float error may happens when turning fraction to double).
   * 
   * @return a new Numeric instance which has a same value with this instance but is in the Double
   *         state.
   */
  public Numeric castToDouble() {
    return new Numeric(doubleValue());
  }

  /**
   * Return a Numeric instance that the opposite number of this Numeric number.
   * 
   * @return a Numeric instance that the opposite number of this Numeric number.
   */
  public Numeric opposite() {
    return Numeric.of(0).subtract(this);
  }

  /**
   * This method checks whether this Numeric is zero by only considering a given number of decimal
   * digits.
   * 
   * Since float calculations may cause floating error, in order to decide whether a number is zero,
   * only a given number of decimal digits are used to decide whether a number is zero. The number
   * of decimal digits considered is specified by the parameter "decimalDigits".
   * 
   * @param decimalDigits - the number of decimal digits considered when deciding whether this
   *                      number is zero.
   * @return true is this number is zero considering the given amount of decimal digits.
   */
  public boolean isZeroBy(int decimalDigits) {
    if (abs().compareTo(new Numeric(Math.pow(0.1, decimalDigits))) < 0)
      return true;
    return false;
  }

  /**
   * Return the sign of this Numeric instance.
   * 
   * @return -1 if this Numeric instance is negative, 1 otherwise.
   */
  public Numeric sign() {
    if (compareTo(new Numeric(0)) < 0)
      return Numeric.of(-1);
    return Numeric.of(1);
  }

  /**
   * Return the absolute value of this Numeric instance
   * 
   * @return the absolute value of this Numeric instance
   */
  public Numeric abs() {
    if (compareTo(new Numeric(0)) < 0)
      return opposite();
    return new Numeric(this);
  }

  /**
   * A private helper method that try to convert the Double state Numeric instance into Fraction
   * state or Integer state and check whether the conversion is possible. If it is possible, returns
   * the converted Numeric, otherwise, throws a ClassCastException with messages.
   * 
   * @return
   */
  public Numeric castToNearestFraction() {
    if (number instanceof Double) {
      Numeric absThis = this.abs();
      int numerator = 1;
      int denominator = 1;
      while (numerator < 1000 && denominator < 1000) {
        Numeric fractionVersion = Numeric.of(new Fraction(numerator, denominator));
        if (fractionVersion.compareTo(absThis, 5) < 0) {
          numerator++;
        } else if (fractionVersion.compareTo(absThis, 5) > 0) {
          denominator++;
        } else {
          if (compareTo(new Numeric(0)) < 0)
            return fractionVersion.opposite();
          return fractionVersion;
        }
      }
      throw new ClassCastException("Sorry, cannot cast to nearest Fraction");
    } else
      return new Numeric(number);
  }

  /**
   * Convert this Numeric instance to a String. Only {@link Numeric#outputSignificantFigures} number
   * of decimal digits will be reserved.
   */
  @Override
  public String toString() {
    if (number instanceof Double)
      return "" + roundWithSignificantFigure((Double) number, outputSignificantFigures);
    return number.toString();
  }

}
