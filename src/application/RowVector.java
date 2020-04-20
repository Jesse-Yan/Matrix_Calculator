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
public class RowVector extends Matrix implements Vector{
  
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[1][content.length];
    matrixContent[0] = content;
    return matrixContent;
  }
  
  public RowVector(Matrix other) {
    super(other);
  }

  public RowVector(Number[] content) {
    super(toMatrix(content));
  }

  @Override
  protected RowVector copy() {
    return new RowVector(super.copy());
  }
  
  @Override
  public ColumnVector transpose() {
    return new ColumnVector(super.transpose());
  }
  
  @Override
  public Numeric innerProduct(Vector vector) {
    try {
      if(vector instanceof ColumnVector)
        return this.multiply((ColumnVector)vector).getEntry(0, 0);
      if(vector instanceof RowVector)
        return this.multiply(((RowVector)vector).transpose()).getEntry(0, 0);
    } catch (MatrixDimensionsMismatchException e) {
      e.printStackTrace();
    }
    return null;
  }
}
