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
