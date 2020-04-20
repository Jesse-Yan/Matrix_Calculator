package application;

/**
 * An object of this class represents a column vector.
 * 
 * 
 * @author Houming Chen
 *
 */
public class ColumnVector extends Matrix implements Vector{
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[content.length][1];
    for (int i = 0; i < content.length; i++)
      matrixContent[i][0] = content[i];
    return matrixContent;
  }
  public ColumnVector(Matrix other) {
    super(other);
  }
  
  public ColumnVector(Number[] content) {
    super(toMatrix(content));
  }
  
  @Override
  protected ColumnVector copy() {
    return new ColumnVector(super.copy());
  }
  
  @Override
  public RowVector transpose() {
    return new RowVector(super.transpose());
  }
  
  @Override
  public Numeric innerProduct(Vector vector) {
    try {
      if(vector instanceof RowVector)
        return ((RowVector)vector).multiply(this).getEntry(0, 0);
      if(vector instanceof ColumnVector)
        return (((ColumnVector)vector).transpose()).multiply(this).getEntry(0, 0);
    } catch (MatrixDimensionsMismatchException e) {
      e.printStackTrace();
    }
    return null;
  }
  
}
