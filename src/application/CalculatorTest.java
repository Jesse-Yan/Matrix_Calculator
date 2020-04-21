package application;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * This class tests the Calculator
 * 
 * @author Houming Chen
 *
 */
public class CalculatorTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}


  @SuppressWarnings("deprecation")
  @Test
  public void test() {
    try {
      assertEquals(Calculator.calcul("1+1*2"), 3.0, 0.0001);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
