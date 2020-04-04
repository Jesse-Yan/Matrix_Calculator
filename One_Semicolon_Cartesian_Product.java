import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class One_Semicolon_Cartesian_Product {
  
  public static Set<int[]> cartesianProduct(int[][] sets) {

    Set<int[]> result = new TreeSet<int[]>(new Comparator<int[]>() {

      @Override
      public int compare(int[] o1, int[] o2) {

        return 1;
      }

    });

    if (sets.length == 0) {
      result.add(new int[0]);
      return result;
    }

    int[] count = new int[sets.length - 1];

    int level = 0;

    cartesian(sets, result, count, level);

    return result;
  }

  private static void cartesian(int[][] sets, Set<int[]> result, int[] count, int level) {

    if (level == sets.length - 1) {
      for (int i = 0; i < sets[level].length; i++) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int j = 0; j < count.length; j++) {
          temp.add(sets[j][count[j]]);
        }
        temp.add(sets[level][i]);
        result.add(temp.stream().mapToInt(Integer::intValue).toArray());
      }
    } else {
      for (int i = 0; i < sets[level].length; i++) {
        count[level] = i;
        cartesian(sets, result, count, level + 1);
      }
    }
  }
}

