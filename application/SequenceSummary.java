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

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;


/**
 * This class computes the summary of a sequence
 * 
 * @author Jesse
 *
 */
public class SequenceSummary {

  /**
   * This method summarize the sequence
   * 
   * @param  sequence the sequence to be analyzed
   * @return          Resulting String
   */
  public static String summary(String sequence) {

    sequence = sequence.trim();

    // Generate DoubleSummaryStatistics based on given sequence
    DoubleSummaryStatistics statistics = Arrays.stream(sequence.split(" +"))
                                               .mapToDouble(Double::parseDouble)
                                               .summaryStatistics();

    return "Number of elements in this sequence is " + statistics.getCount()
        + "\nSum of this sequence is " + statistics.getSum()
        + "\nAverage of the sequence is " + statistics.getAverage()
        + "\nMax is " + statistics.getMax() + "\nMin is " + statistics.getMin()
        + "\n";
  }
}
