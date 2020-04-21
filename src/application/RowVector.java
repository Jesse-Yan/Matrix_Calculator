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
public class RowVector extends Matrix implements Vector {
  /**
   * Transform number array into RowVector form.
   * 
   * @param content - Number array stores elements
   * @return a 2D array that constructed in form of RowVector
   */
  private static Number[][] toMatrix(Number[] content) {
    Number[][] matrixContent = new Number[1][content.length];
    matrixContent[0] = content;
    return matrixContent;
  }

  /**
   * Constructor of RowVector base on Matrix Object
   * 
   * @param other - other Matrix
   */
  public RowVector(Matrix other) {
    super(other);
  }

  /**
   * Constructor of RowVector base on array
   * 
   * @param content - array stores vector elements.
   */
  public RowVector(Number[] content) {
    super(toMatrix(content));
  }

  /**
   * Function generate vector copy.
   */
  @Override
  protected RowVector copy() {
    return new RowVector(super.copy());
  }

  /**
   * transposed Function for RowVector.
   * 
   * @return transposed ColumnVector
   */
  @Override
  public ColumnVector transpose() {
    return new ColumnVector(super.transpose());
  }

  /**
   * Method solves for inner product of vector.
   * 
   * @return Numeric object represent inner product of vector null if Matrix dimensions Mismatch
   */
  @Override
  public Numeric innerProduct(Vector vector) {
    try {
      if (vector instanceof ColumnVector)
        return this.multiply((ColumnVector) vector).getEntry(0, 0);
      if (vector instanceof RowVector)
        return this.multiply(((RowVector) vector).transpose()).getEntry(0, 0);
    } catch (MatrixDimensionsMismatchException e) {
      e.printStackTrace();
    }
    return null;
  }
}
