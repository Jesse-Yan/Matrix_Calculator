/**
 * 
 */
package application;

/**
 * An object of this class represents a row vector.
 * 
 * 
 * @author Houming Chen
 *
 */
public class RowVector extends Matrix {
  
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[1][content.length];
    matrixContent[0] = content;
    return matrixContent;
  }

  public RowVector(Number[] content) {
    super(toMatrix(content));
  }

}
