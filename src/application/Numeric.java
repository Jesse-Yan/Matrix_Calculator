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
public class Numeric implements Comparable<Numeric> {
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
    if(number instanceof Fraction) {
      try {
        this.number = number.intValue();
      } catch (ClassCastException e) {
        this.number = number;
      }
    }
    else if(number instanceof Long)
      if(Integer.MIN_VALUE <= number.longValue() && number.longValue() <= Integer.MAX_VALUE)
        this.number = number.intValue();
      else 
        this.number = number.doubleValue();
    else if(number instanceof Integer || number instanceof Short)
      this.number = number.intValue();
    else
      this.number = number.doubleValue();
  }

  /**
   * The copy constructor that construct this Numeric instance with a another Numeric instance.
   * 
   * @param other another given Numeric instance.
   */
  public Numeric(Numeric other) {
    number = other.number;
  }

  @Override
  public String toString() {
    return number.toString();
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
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return new Numeric(thisNum.number.doubleValue() + other.number.doubleValue());
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return new Numeric(Fraction.of(thisNum.number).add(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return new Numeric(thisNum.number.longValue() + other.number.longValue());
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
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return new Numeric(thisNum.number.doubleValue() - other.number.doubleValue());
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return new Numeric(Fraction.of(thisNum.number).subtract(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return new Numeric(thisNum.number.longValue()  - other.number.longValue());
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
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return new Numeric(thisNum.number.doubleValue() * other.number.doubleValue());
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return new Numeric(Fraction.of(thisNum.number).multiply(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return new Numeric(thisNum.number.longValue() * other.number.longValue());
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }
  
  /**
   * Get the difference of this Numeric Instance and another given Numeric Instance.
   * This method will not change the value of both of the instances.
   * @param other the other given Numeric Instance
   * @return the difference
   */
  public Numeric dividedBy(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return new Numeric(thisNum.number.doubleValue() / other.number.doubleValue());
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return new Numeric(Fraction.of(thisNum.number).dividedBy(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return new Numeric(new Fraction(thisNum.number.intValue(), other.number.intValue()));
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
  }

  int toInteger() {
    if (number instanceof Integer) {
      return ((Integer) number);
    }
    throw new ClassCastException("Cannot cast to an Integer");
  }
  
  @Override
  public int compareTo(Numeric other) {
    other = new Numeric(other);
    Numeric thisNum = new Numeric(this);
    if (thisNum.number instanceof Double || other.number instanceof Double) {
      return Double.valueOf(thisNum.number.doubleValue()).compareTo(Double.valueOf(other.number.doubleValue()));
    }
    if (thisNum.number instanceof Fraction || other.number instanceof Fraction) {
      return (Fraction.of(thisNum.number).compareTo(Fraction.of(other.number)));
    }
    if (thisNum.number instanceof Integer || other.number instanceof Integer) {
      return Integer.valueOf(thisNum.number.intValue()).compareTo(Integer.valueOf(other.number.intValue()));
    }
    throw new ClassCastException("Cannot cast to Integer or Double or Fraction");
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
    Numeric n4 = new Numeric(2147483647); // n4 = int_max
    System.out.println(n1.add(n1)); // n1 + n1 = 2
    System.out.println(n1.add(n2)); // n1 + n2 = 8/7
    System.out.println(n1.add(n3)); // n1 + n3 = 2.5
    System.out.println(n2.add(n3)); // n2 + n3 = 1.642857142857
    System.out.println(n1.subtract(n1)); // n1 - n1 = 0
    System.out.println(n2.multiply(n2)); // 1/7 * 1/7 = 1/49
    System.out.println(n1.add(n4)); // 1/7 * 1/7 = 1/49
    
    Numeric n5 = new Numeric(2); // n5 = 2
    Numeric n6 = new Numeric(3); // n6 = 3
    Numeric n7 = new Numeric(0); // n7 = 0
    System.out.println(n5.dividedBy(n6)); // n5 / n6 = 0
    try {
      System.out.println(n5.dividedBy(n7));
    } catch (Exception e) {
      System.out.println(e.getMessage()); // "/ by zero"
    }
  } 
}
