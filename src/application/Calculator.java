package application;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class computes the result of the given expression
 * 
 * @author Jesse, Archer
 *
 */
public class Calculator {

  /**
   * Handling complex expressions
   * 
   * @param  expression
   * @return
   */
  public static double calcul(String expression) {

    // Handling Absolute values
    expression = expression.replaceAll("\\|\\-?(.+)\\|", "$1");
    
    // Handling PI
    expression = expression.replaceAll("\\u03c0", String.valueOf(Math.PI));

    // Handling e
    expression = expression.replaceAll("e", String.valueOf(Math.E));
    
    return calculate(expression);
  }

  /**
   * build up calculation expression and calculate result.
   * 
   * @param  expression String math expression
   * @return            result after calculation
   */
  public static double calculate(String expression) {

    expression = expression.replaceAll("(\\d+)\\(", "$1*(");

    if (expression.matches("\\-?\\d+"))
      return Double.valueOf(expression);
    ArrayList<String> s = new ArrayList<String>();
    char[] chs = expression.toCharArray();
    String temp = "";
    boolean tag = false;
    for (int i = 0; i < chs.length; i++) {
      if (chs[i] - '0' >= 0 && chs[i] - '0' <= 9) {
        temp += chs[i];

      } else if (chs[i] == '.') {
        temp += chs[i];

      } else if (chs[i] == ' ') {
        if (temp.length() != 0)
          s.add(temp);
        temp = "";

      } else if (chs[i] == '-') {
        if (temp.length() != 0)
          s.add(temp);
        temp = "-";
        if (chs[i + 1] == '(') {
          if (s.get(s.size() - 1).equals("/")
              || s.get(s.size() - 1).equals("*")) {
            s.add("(");
            tag = true;
          } else if (s.get(s.size() - 1).equals("-")) {
            s.set(s.size() - 1, "+");
            s.add("(");
          } else if (s.get(s.size() - 1).equals(")")
              || s.get(s.size() - 1).matches("\\d+")) {
            s.add("-");
            s.add("(");
          } else {
            s.add("0");
            s.add("-");
            s.add("(");
          }
          temp = "";
          i++;

        } else if (chs[i + 1] - '0' >= 0 && chs[i + 1] - '0' <= 9) {
          if (s.get(s.size() - 1).matches("\\d+")) {
            s.add("+");
          } else {
            continue;
          }
        } else {
          if (temp.length() != 0)
            s.add(temp);
          temp = "";
        }

      } else if (chs[i] == ')' && tag) {
        s.add(temp);
        temp = "";
        s.add(")");
        s.add("*");
        s.add("-1");
        tag = false;
      } else {
        if (temp.length() != 0)
          s.add(temp);
        temp = "";
        temp += chs[i];
        s.add(temp);
        temp = "";
      }
    }
    if (temp.length() != 0)
      s.add(temp);
    String[] spliter = s.toArray(new String[0]);
    ModifiedCal cal = new ModifiedCal(spliter);
    double result = cal.doCal();
    return result;
  }
}


class ModifiedCal {
  private Stack<Double> theStackI;
  private Stack<String> theStackP;
  private String[] input;
  private ArrayList<String> output;
  private double num1, num2, interAns;

  public ModifiedCal(String[] s) {
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
