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
   * Serial Version ID
   */
  private static final long serialVersionUID = -1685120027128610074L;

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
