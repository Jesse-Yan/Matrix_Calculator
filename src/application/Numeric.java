package application;

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
public class Numeric {
  /**
   * A private Object representing the number, which can only be a Integer, or a Fraction, or a
   * Double.
   */
  private Object number;

  /**
   * The constructor to construct this Numeric instance with an Integer.
   * 
   * @param integerNumber the given Integer instance
   */
  public Numeric(Integer integerNumber) {
    number = integerNumber;
  }

  /**
   * The constructor to construct this Numeric instance with a Double.
   * 
   * @param doubleNumber the given Double instance
   */
  public Numeric(Double doubleNumber) {
    number = doubleNumber;
  }

  /**
   * The constructor to construct this Numeric instance with a Fraction.
   * 
   * @param fractionNumber the given Fraction instance
   */
  public Numeric(Fraction fractionNumber) {
    try {
      number = fractionNumber.toInteger();;
    } catch (ClassCastException e) {
      number = fractionNumber;
    }
  }

  /**
   * The copy constructor that construct this Numeric instance with a another Numeric instance.
   * 
   * @param other another given Numeric instance.
   */
  public Numeric(Numeric other) {
    if (other.number instanceof Integer) {
      number = (Integer) other.number;
    } else if (other.number instanceof Fraction) {
      number = (Fraction) other.number;
    } else if (other.number instanceof Double) {
      number = (Double) other.number;
    } else
      throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  /**
   * Receive two Numeric reference, and cast their instance down until the instance become the same
   * type of the data. (Integer -> Fraction -> Double)
   * 
   * @param num1 the first given Numeric reference
   * @param num2 the second given Numeric reference
   */
  static void castUntilBalance(Numeric num1, Numeric num2) {
    if (num1.number instanceof Double) {
      if (num2.number instanceof Integer)
        num2.number = Double.valueOf((Integer) num2.number);
      else if (num2.number instanceof Fraction)
        num2.number = ((Fraction) num2.number).toDouble();
    } else if (num1.number instanceof Fraction) {
      if (num2.number instanceof Integer)
        num2.number = Fraction.of((Integer) num2.number);
      else if (num2.number instanceof Double)
        num1.number = ((Fraction) num1.number).toDouble();
    } else if (num1.number instanceof Integer) {
      if (num2.number instanceof Fraction)
        num1.number = Fraction.of((Integer) num1.number);
      else if (num2.number instanceof Double)
        num1.number = Double.valueOf((Integer) num1.number);
    }
  }

  @Override
  public String toString() {
    if (number instanceof Integer) {
      return ((Integer) number).toString();
    }
    if (number instanceof Double) {
      return ((Double) number).toString();
    }
    if (number instanceof Fraction) {
      return ((Fraction) number).toString();
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  /**
   * Get the sum of this Numeric Instance and another given Numeric Instance.
   * This method will not change the value of both of the instances.
   * @param other the other given Numeric Instance
   * @return the difference
   */
  public Numeric add(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    castUntilBalance(thisNum, other);
    if (thisNum.number instanceof Integer) {
      return new Numeric((Integer) thisNum.number + (Integer) other.number);
    }
    if (thisNum.number instanceof Fraction) {
      return new Numeric(((Fraction) thisNum.number).add((Fraction) other.number));
    }
    if (thisNum.number instanceof Double) {
      return new Numeric((Double) thisNum.number + (Double) other.number);
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  /**
   * Get the difference of this Numeric Instance and another given Numeric Instance.
   * This method will not change the value of both of the instances.
   * @param other the other given Numeric Instance
   * @return the difference
   */
  public Numeric subtract(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    castUntilBalance(thisNum, other);
    if (thisNum.number instanceof Integer) {
      return new Numeric((Integer) thisNum.number - (Integer) other.number);
    }
    if (thisNum.number instanceof Fraction) {
      return new Numeric(((Fraction) thisNum.number).subtract((Fraction) other.number));
    }
    if (thisNum.number instanceof Double) {
      return new Numeric((Double) thisNum.number - (Double) other.number);
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }
  
  /**
   * Get the difference of this Numeric Instance and another given Numeric Instance.
   * This method will not change the value of both of the instances.
   * @param other the other given Numeric Instance
   * @return the difference
   */
  public Numeric multiply(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    castUntilBalance(thisNum, other);
    if (thisNum.number instanceof Integer) {
      return new Numeric((Integer) thisNum.number * (Integer) other.number);
    }
    if (thisNum.number instanceof Fraction) {
      return new Numeric(((Fraction) thisNum.number).multiply((Fraction) other.number));
    }
    if (thisNum.number instanceof Double) {
      return new Numeric((Double) thisNum.number * (Double) other.number);
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  int toInteger() {
    if (number instanceof Integer) {
      return ((Integer) number);
    }
    throw new ClassCastException("Cannot cast to an Integer");
  }
  
  /**
   * A main method, just for test.
   * 
   * @param args args
   */
  public static void main(String args[]) {
    Numeric n1 = new Numeric(1); // n1 = 1
    Numeric n2 = new Numeric(new Fraction(1, 7)); // n2 = 1/7
    Numeric n3 = new Numeric(1.5); // n3 = 0.5
    System.out.println(n1.add(n1)); // n1 + n1 = 2
    System.out.println(n1.add(n2)); // n1 + n2 = 8/7
    System.out.println(n1.add(n3)); // n1 + n3 = 2.5
    System.out.println(n2.add(n3)); // n2 + n3 = 1.642857142857
    System.out.println(n1.subtract(n1)); // n1 - n1 = 0
    System.out.println(n2.multiply(n2)); // 1/7 * 1/7 = 1/49
  }
}
