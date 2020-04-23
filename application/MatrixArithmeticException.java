package application;

/**
 * 
 * The exception thrown when the the matrix is cannot do the require calculation.
 * 
 * @author Houming Chen
 *
 */

public class MatrixArithmeticException extends Exception {

  /**
   * default no-arg constructor
   */
  public MatrixArithmeticException() {}

  /**
   * This constructor is provided to allow user to include a message
   * 
   * @param msg Additional message for this exception
   */
  public MatrixArithmeticException(String msg) {
    super(msg);
  }

}
