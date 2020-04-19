package application;

/**
 * The exception thrown when the the matirx is singular when the algorithm requires that the matrix
 * cannot be singular.
 * 
 * @author Houming Chen
 *
 */
public class SingularException extends Exception {

  /**
   * default no-arg constructor
   */
  public SingularException() {}

  /**
   * This constructor is provided to allow user to include a message
   * 
   * @param msg Additional message for this exception
   */
  public SingularException(String msg) {
    super(msg);
  }
}
