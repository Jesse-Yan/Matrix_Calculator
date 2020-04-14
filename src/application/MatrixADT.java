package application;

/**
 * The ADT for matrix.
 * 
 * @author Houming Chen
 *
 */
public interface MatrixADT {
  
  int getNumberOfColumn();
  int getNumberOfRow();
  Fraction getEntry(int row, int column);
  
  MatrixADT add(MatrixADT other) throws MatrixDimensionsMismatchException;
  MatrixADT subtract(MatrixADT other) throws MatrixDimensionsMismatchException;
  MatrixADT multiply(Fraction number);
  MatrixADT GaussianElimination(MatrixADT other) throws MatrixDimensionsMismatchException;
  MatrixADT multiply(MatrixADT other) throws MatrixDimensionsMismatchException;
  MatrixADT inverse();
  Fraction getDeterminant();
  
}
