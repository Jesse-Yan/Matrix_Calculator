package application;

/**
 * The exception thrown when the dimension of matrices are not supported during the calculation.
 * 
 * 
 * @author Houming Chen
 *
 */
public class MatrixDimensionsMismatchException extends Exception {
  
  /**
   * default no-arg constructor
   */
  public MatrixDimensionsMismatchException() { }

  /**
   * This constructor is provided to allow user to include a message
   * @param msg Additional message for this exception
   */
  public MatrixDimensionsMismatchException(String msg) { 
      super(msg);
  }
}
