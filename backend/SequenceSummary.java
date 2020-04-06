package backend;

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
   * @return          DoubleSummaryStatistics information relates to the
   *                    sequence
   */
  public static DoubleSummaryStatistics summary(double[] sequence) {
    return Arrays.stream(sequence).summaryStatistics();
  }
}
