package application;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * The instance of this Numeric class represents a number, which can be a Integer, or a Fraction, or
 * a Double. It supports calculations like addition and subtraction, in which the number will be
 * cast into proper format automatically to do the calculation.
 * 
 * To do calculations, Integer can be cast to Fraction, and both Integer and Fraction can be cast to
 * Double. (Integer -> Fraction -> Double)
 * 
 * @author Houming Chen
 *
 */
public class Numeric extends Number implements Comparable<Numeric> {

  /**
   * Since there are float error. Two doubles are considered the same number if their first 12
   * significant digits are same and have same exponent number.
   */
  final static int MAXIMUM_SIGNIFICANT_FIGURE = 12;

  /**
   * A private Object representing the number, which can only be a Integer, or a Fraction, or a
   * Double.
   */
  private Number number;

  /**
   * The constructor to construct this Numeric instance with an given number
   * 
   * @param integerNumber the given number
   */
  public Numeric(Number number) {
    if (number instanceof Numeric) {
      this.number = ((Numeric) number).number;
    } else if (number instanceof Fraction) {
      try { // Try to turn fraction back to int value.
        this.number = number.intValue();
      } catch (ClassCastException e) {
        this.number = number;
      }
    } else if (number instanceof Long) {
      if (Integer.MIN_VALUE <= number.longValue() && number.longValue() <= Integer.MAX_VALUE) {
        this.number = number.intValue();
      } else {
        this.number = number.doubleValue();
      }
    } else if (number instanceof Integer || number instanceof Short) {
      this.number = number.intValue();
    } else {
      this.number = number.doubleValue();
    }
  }

  public static Numeric of(Number number) {
    return new Numeric(number);
  }

  @Override
  public String toString() {
    return number.toString();
  }

  /**
   * Get the sum of this Numeric Instance and another given Number Instance.
   * 
   * @param other other the other given Numeric Instance
   * @return the sum
   */
  public Numeric add(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        return new Numeric(thisNum.number.doubleValue() + otherNum.number.doubleValue());
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
   * @param other other the other given Numeric Instance
   * @return the difference
   */
  public Numeric subtract(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
      if (thisNum.number instanceof Double || otherNum.number instanceof Double) {
        return new Numeric(thisNum.number.doubleValue() - otherNum.number.doubleValue());
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
      throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
    }
    return this.subtract(new Numeric(other));
  }

  /**
   * Get the product of this Numeric Instance and another given Number Instance.
   * 
   * @param other other the other given Numeric Instance
   * @return the product
   */
  public Numeric multiply(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
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
      throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
    }
    return this.multiply(new Numeric(other));
  }

  /**
   * Get the quotient of this Numeric Instance and another given Number Instance.
   * 
   * @param other other the other given Numeric Instance
   * @return the quotient
   */
  public Numeric dividedBy(Number other) {
    if (other instanceof Numeric) {
      Numeric otherNum = new Numeric(other);
      Numeric thisNum = new Numeric(this);
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
      throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
    }
    return this.dividedBy(new Numeric(other));
  }

  /**
   * 
   * Check whether the given integer x is a perfect square number
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
   * If x is a perfect square number, get its square root.
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
   * Get the square root of this Numeric instance.
   * 
   * @return the square root of this Numeric instance.
   */
  public Numeric sqrt() {
    Numeric thisNum = new Numeric(this);
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
   * Round a given double to having at most MAXIMUM_SIGNIFICANT_FIGURE significant figures.
   * 
   * @param value a given double
   * @return the rounded double
   */
  private static double round(double value) {
    BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
    bigDecimal = bigDecimal.round(new MathContext(MAXIMUM_SIGNIFICANT_FIGURE));
    return bigDecimal.doubleValue();
  }

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
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Numeric)
      return compareTo((Numeric) obj) == 0;
    else if (obj instanceof Number)
      return compareTo(new Numeric((Number) obj)) == 0;
    return false;
  }

  @Override
  public int intValue() {
    if (number instanceof Integer) {
      return ((Integer) number);
    }
    throw new ClassCastException("Cannot cast to an Integer");
  }

  @Override
  public long longValue() {
    if (number instanceof Integer) {
      return ((Integer) number).longValue();
    }
    throw new ClassCastException("Cannot cast to an Long");
  }

  @Override
  public float floatValue() {
    return (float) number.doubleValue();
  }

  @Override
  public double doubleValue() {
    return number.doubleValue();
  }

  /**
   * A main method, just for test and demo.
   * 
   * @param args args
   */
  public static void main(String args[]) {
    Numeric n1 = new Numeric(1); // n1 = 1
    Numeric n2 = new Numeric(new Fraction(1, 7)); // n2 = 1/7
    Numeric n3 = new Numeric(1.5); // n3 = 0.5
    Numeric n4 = new Numeric(2147483647); // n4 = int_max
    System.out.println(n1.add(n1)); // n1 + n1 = 2
    System.out.println(n1.add(n2)); // n1 + n2 = 8/7
    System.out.println(n1.add(n3)); // n1 + n3 = 2.5
    System.out.println(n2.add(n3)); // n2 + n3 = 1.642857142857
    System.out.println(n1.subtract(n1)); // n1 - n1 = 0
    System.out.println(n2.multiply(n2)); // 1/7 * 1/7 = 1/49
    System.out.println(n1.add(n4)); // 1 + int_Max = 2.147483648E9

    Numeric n5 = new Numeric(2); // n5 = 2
    Numeric n6 = new Numeric(3); // n6 = 3
    Numeric n7 = new Numeric(0); // n7 = 0
    System.out.println(n5.dividedBy(n6)); // n5 / n6 = 2/3
    try {
      System.out.println(n5.dividedBy(n7));
    } catch (Exception e) {
      System.out.println(e.getMessage()); // "/ by zero"
    }
    
    System.out.println(Numeric.of(new Fraction(4, 9)).sqrt()); // sqrt(4/9) = 2/3
    System.out.println(Numeric.of(2).sqrt());  // sqrt(2) = 1.41421...
  }

}
