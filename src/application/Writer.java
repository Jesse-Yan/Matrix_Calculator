package application;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Writer {
  private List<CalSteps> results;//list to store results in steps
  PrintWriter printWriter = null;//initialize a printWriter

  /**
   * constructor of Writer object
   * 
   * @param results - results list
   */
  public Writer(List<CalSteps> results) {
    this.results = results;
  }

  /**
   * Method that create a .json file with result stores in it.
   * 
   * @throws FileNotFoundException - if error in files
   */
  public void save() throws FileNotFoundException {
    try {
      printWriter = new PrintWriter("Result.json");
      printWriter.write("{" + "\n");
      printWriter.write("\t" + "\""+"StepToResult"+"\":[\n");

      CalSteps cur = null;
      for (int i = 0; i < results.size(); ++i) {
        if (i != 0)
          printWriter.write("\t" + ",\n");
        printWriter.write("\t" + "{\n");
        cur = results.get(i);
        if (cur.getOperation() != null || cur.getDatas() != null) {
          printWriter.write("\t\t" + "\"operations\": \"" + cur.getOperation() + "\",\n");
          printWriter.write("\t\t" + "\"datas\": [\n");
          for (int j = 0; j < cur.getDatas().size(); ++j) {
            printWriter.write("\t\t\t");
            if (j != 0)
              printWriter.write(",");
            printWriter.write("[\n");

            printWriter.write(cur.getDatas().get(j).toJsonString());

            printWriter.write("\t\t\t" + "]\n");
          }
          printWriter.write("\t\t" + "]\n");
        } else {
          printWriter.write("\t\t" + "\"result\": ");
          if (cur.getType() == 3) {
            try {
              printWriter.write("\"" + (String) cur.getResult() + "\"\n");
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            try {
              List<Matrix> end = (List<Matrix>) cur.getResult();
              printWriter.write("[\n");
              for (int j = 0; j < end.size(); ++j) {
                printWriter.write("\t\t\t");
                if (j != 0)
                  printWriter.write(",");
                printWriter.write("[\n");

                printWriter.write(end.get(j).toJsonString());

                printWriter.write("\t\t\t" + "]\n");
              }
            } catch (Exception e) {
              e.printStackTrace();
            }

            printWriter.write("\t\t" + "]\n");
          }

        }
        printWriter.write("\t" + "}\n");
      }
      printWriter.write("\t" + "]\n");
      printWriter.write("}");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw e;
    }
    printWriter.close();
  }

  /**
   * Test purpose
   * 
   * @param args
   */
  public static void main(String[] args) {
    try {
      OpeartionParser parser = new OpeartionParser("SimpleData.json");
      List<CalSteps> curr = parser.getCalculations();
      curr.add(new CalSteps((Object)"Just a test"));
      Writer test = new Writer(curr);
      test.save();
      System.out.println("success!!");
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }
}