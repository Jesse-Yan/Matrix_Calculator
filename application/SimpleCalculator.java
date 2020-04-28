package application;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Simple Calculator class that does not handle complex calculations
 * 
 * @author Jesse
 *
 */
public class SimpleCalculator {
  private Stack<Double> theStackI;
  private Stack<String> theStackP;
  private String[] input;
  private ArrayList<String> output;
  private double num1, num2, interAns;

  public SimpleCalculator(String[] s) {
    this.input = s;
    theStackP = new Stack<String>();
    output = new ArrayList<String>();
  }

  /**
   * Calculation method that read input expression base on stack
   * 
   * @return result after process
   */
  public double doCal() {
    doTrans();
    doParse();
    return theStackI.pop();
  }

  /**
   * Read expression from stack and store result in stack
   */
  public void doParse() {
    theStackI = new Stack<Double>();
    for (int i = 0; i < output.size(); i++) {
      String temp = output.get(i);
      if (!(temp.equals("+") || temp.equals("-") || temp.equals("*")
          || temp.equals("/"))) {
        theStackI.push(Double.parseDouble(temp));
      } else {
        num2 = theStackI.pop();
        num1 = theStackI.pop();
        switch (temp) {
          case "+":
            interAns = num1 + num2;
            break;
          case "-":
            interAns = num1 - num2;
            break;
          case "*":
            interAns = num1 * num2;
            break;
          case "/":
            interAns = num1 / num2;
            break;
          default:
            interAns = 0;
            break;
        }
        theStackI.push(interAns);
      }
    }
  }

  /**
   * read and distribute calculation priority
   */
  public void doTrans() {
    for (int i = 0; i < input.length; i++) {
      String temp = input[i];
      if (temp.equals("(")) {
        theStackP.push(temp);
      } else if (temp.equals("+") || temp.equals("-")) {
        goOper(temp, 1);
      } else if (temp.equals("*") || temp.equals("/")) {
        goOper(temp, 2);
      } else if (temp.equals(")")) {
        goParen();
      } else {
        output.add(temp);
      }
    }

    while (!theStackP.isEmpty()) {
      output.add(theStackP.pop());
    }
  }

  /**
   * add sting in stackP into output if there's no bracket in stack
   */
  private void goParen() {

    while (!theStackP.isEmpty()) {

      String s = theStackP.pop();
      if (s.equals("("))
        break;
      output.add(s);
    }

  }

  /**
   * helper method that process operations in level
   * 
   * @param temp -expression piece
   * @param i    - level limit
   */
  private void goOper(String temp, int i) {

    while (!theStackP.isEmpty()) {
      String s = theStackP.pop();
      if (s.equals("(")) {
        theStackP.push(s);
        break;
      } else {
        int j;

        if (s.equals("+") || s.equals("-")) {
          j = 1;
        } else {
          j = 2;
        }

        if (j < i) {
          theStackP.push(s);
          break;
        } else {
          output.add(s);
        }
      }
    }
    theStackP.push(temp);
  }
}
