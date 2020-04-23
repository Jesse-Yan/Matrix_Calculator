package application;

/**
 * An object of this class represents a column vector.
 * 
 * 
 * @author Houming Chen
 *
 */
public class ColumnVector extends Matrix implements Vector {
  /**
   * Transform number array into ColumnVector form.
   * 
   * @param content - Number array stores elements
   * @return a 2D array that constructed in form of ColumnVector
   */
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[content.length][1];
    for (int i = 0; i < content.length; i++)
      matrixContent[i][0] = content[i];
    return matrixContent;
  }

  /**
   * Constructor of ColumnVector base on Matrix Object
   * 
   * @param other - other Matrix
   */
  public ColumnVector(Matrix other) {
    super(other);
  }

  /**
   * Constructor of ColumnVector base on array
   * 
   * @param content - array stores vector elements.
   */
  public ColumnVector(Number[] content) {
    super(toMatrix(content));
  }

  /**
   * Function generate vector copy.
   */
  @Override
  protected ColumnVector copy() {
    return new ColumnVector(super.copy());
  }

  /**
   * transposed Function for ColumnVector.
   * 
   * @return transposed RowVector
   */
  @Override
  public RowVector transpose() {
    return new RowVector(super.transpose());
  }

  /**
   * Method solves for inner product of vector.
   * 
   * @return Numeric object represent inner product of vector null if Matrix dimensions Mismatch
   */
  @Override
  public Numeric innerProduct(Vector vector) {
    try {
      if (vector instanceof RowVector)
        return ((RowVector) vector).multiply(this).getEntry(0, 0);
      if (vector instanceof ColumnVector)
        return (((ColumnVector) vector).transpose()).multiply(this).getEntry(0, 0);
    } catch (MatrixDimensionsMismatchException e) {
      e.printStackTrace();
    }
    return null;
  }

}
