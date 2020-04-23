package application;

import java.util.ArrayList;

/**
 * Calculations - Object stores basic Calculations data
 * @author Archer (2020)
 * 
 */
public class CalSteps {
  
  private String operation;//operations stores here

  private ArrayList<Matrix> datas;//data stores here
  
  private Object result;
  
  private int type;//type that refers: 1, normal steps; 2, result in array
  //3, result in String

  

  /**
   * constructor of Calculations object
   * 
   * @param result - result of calculations
   */
  public CalSteps(Object result) {
    this.result = result;
    this.datas = null;
    this.operation = null;
    if (result instanceof String)
      this.type = 3;
    else this.type = 2;
  }
  
  /**
   * constructor of Calculations object
   * 
   * @param operation - operation
   * @param datas - datas
   */
  public CalSteps(String operation, ArrayList<Matrix> datas) {
    this.operation = operation;
    this.datas = datas;
    this.result = null;
    this.type = 1;
  }
  
  /**
   * constructor of Calculations object
   * 
   * @param operation - operation
   * @param datas - datas
   * @param result - result whether in String or List<Matrix>
   */
  public CalSteps(String operation, ArrayList<Matrix> datas, Object result) {
    this.operation = operation;
    this.datas = datas;
    this.result = result;
    if (result instanceof String)
      this.type = 3;
    else this.type = 2;
  }
  
  /**
   * Getter of operation
   * 
   * @return operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Getter of datas
   * 
   * @return datas
   */
  public ArrayList<Matrix> getDatas() {
    return datas;
  }
  
  /**
   * Getter of result 
   * 
   * @return result - result
   * @throws Exception - throw exception when case not met
   */
  public Object getResult() {
    return result;
  }
  
  /**
   * Getter for type
   * 
   * @return type in int
   */
  public int getType() {
    return type;
  }

}
