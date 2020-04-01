import java.math.BigInteger;

public class TheMillionthFibonacci {
  
  public static BigInteger fib(BigInteger n) {

    BigInteger bi = n.abs();

    if (bi.intValue() == 0)
      return BigInteger.ZERO;
    else if(n.intValue() == -2)
      return BigInteger.ONE.negate();
    else if (bi.intValue() <= 2)
      return BigInteger.ONE;

    BigInteger[][] number =
        {{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};
    BigInteger[][] result =
        {{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};

    while (!bi.equals(BigInteger.ZERO)) {
      if (bi.mod(BigInteger.valueOf(2L)).equals(BigInteger.ONE))
        result = MultiplyMatrix(result, number);
      number = MultiplyMatrix(number, number);
      bi = bi.divide(BigInteger.valueOf(2L));
    }

    return result[1][1].multiply(
        n.compareTo(BigInteger.ZERO) > 0 ? BigInteger.ONE
            : n.abs().mod(BigInteger.valueOf(2L)).equals(BigInteger.ONE)
                ? BigInteger.ONE
                : BigInteger.ONE.negate());
  }

  private static BigInteger[][] MultiplyMatrix(BigInteger[][] result,
      BigInteger[][] number) {
    return new BigInteger[][] {
        {result[0][0].multiply(number[0][0])
                     .add(result[0][1].multiply(number[1][0])),
            result[0][0].multiply(number[0][1])
                        .add(result[0][1].multiply(number[1][1]))},
        {result[1][0].multiply(number[0][0])
                     .add(result[1][1].multiply(number[1][0])),
            result[1][0].multiply(number[0][1])
                        .add(result[1][1].multiply(number[1][1]))}};
  }
}
