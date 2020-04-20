package application;

/**
 * An object of this class represents a column vector.
 * 
 * 
 * @author Houming Chen
 *
 */
public class ColumnVector extends Matrix {
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[content.length][1];
    for (int i = 0; i < content.length; i++)
      matrixContent[i][0] = content[i];
    return matrixContent;
  }

  public ColumnVector(Number[] content) {
    super(toMatrix(content));
  }
}
