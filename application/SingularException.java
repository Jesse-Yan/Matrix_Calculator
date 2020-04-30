//////////////////////////////// CS 400 HEADER ////////////////////////////////
//
// Title: Ateam project - Matrix Calculator
// Course: COMP SCI 400, Spring 2020
// Ateam: ateam2
//
///////////////////////////////// DESCRIPTION /////////////////////////////////
//This project ¡°Matrix calculator¡± by CS400 Ateam 2 aims to help students 
//studying linear algebra to understand the calculations linear algebra better. 
//This ¡°Matrix calculator¡± can not only do many matrix calculations like matrix 
//multiplication, finding eigenvalues, and do LUP, QR, or Cholesky 
//decompositions, but it can also support basic algebra calculations 
//like a normal calculator and analyzing sequence. 
//////////////////////////////////// CREDITS //////////////////////////////////
//
// Method to reduce a matrix to Hessenberg-form:
// https://math.stackexchange.com/questions/732924/reducing-a-matrix-to-upper-
// hessenberg-form-using-householder-transformations-in
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
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
