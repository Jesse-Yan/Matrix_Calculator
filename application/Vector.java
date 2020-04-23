package application;

/**
 * 
 * An interface for ColumnVector and RowVector.
 * 
 * @author Houming Chen
 *
 */

public interface Vector {
  Numeric innerProduct(Vector vector);
}
