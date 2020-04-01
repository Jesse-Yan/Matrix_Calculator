import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Binomial_Expansion {

  public static void main(String[] args) {
    String s = expand("(y+5)^15");
    System.out.println(s);
  }

  public static String expand(String expr) {

    String[] spliter = expr.split("\\^");

    String polynomial = spliter[0].substring(1, spliter[0].length() - 1);

    if (Integer.valueOf(spliter[1]) == 0)
      return "1";
    if (Integer.valueOf(spliter[1]) == 1)
      return polynomial;

    int n = Integer.valueOf(spliter[1]);

    Pattern p1 = Pattern.compile("\\-*[0-9]*[a-z]{1}");
    Pattern p2 = Pattern.compile("\\b\\-*[0-9]+\\b");

    Matcher m1 = p1.matcher(polynomial);
    Matcher m2 = p2.matcher(polynomial);

    m1.find();
    int a = Integer.valueOf(m1.group().substring(0, m1.group().length() - 1).equals("") ? "1"
        : m1.group().substring(0, m1.group().length() - 1).equals("-") ? "-1"
            : m1.group().substring(0, m1.group().length() - 1));
    String s = m1.group().substring(m1.group().length() - 1);
    m2.find();
    int b = Integer.valueOf(m2.group());
    if (b == 0) {
      return (long) Math.pow(a, n) + s + "^" + n;
    }

    String result = "";
    for (int i = 0; i < n + 1; i++) {
      long coefficient = (long) (c(i, n) * Math.pow(a, n - i) * Math.pow(b, i));

      String coff = String.valueOf(coefficient);
      if (coff.equals("1") && n - i != 0)
        coff = "";
      if (coff.equals("-1") && n - i != 0)
        coff = "-";
      String partialResult;
      if (n - i == 0) {
        partialResult = coff;
      } else if (n - i == 1) {
        partialResult = coff + s;
      } else {
        partialResult = coff + s + "^" + (n - i);
      }
      result += partialResult.startsWith("-") ? partialResult : "+" + partialResult;
    }
    return result.startsWith("-") ? result : result.substring(1);
  }

  private static double c(int i, int n) {

    long n_f = factorial(n);
    long i_f = factorial(i);
    long ni_f = factorial(n - i);

    return (double) (n_f / (i_f * ni_f));
  }

  private static long factorial(long i) {

    if (i <= 1)
      return 1;

    return i * factorial(--i);
  }
}
