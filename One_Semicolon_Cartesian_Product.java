import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

//public static java.util.Set<int[]> cartesianProduct(final int[][] sets) {
//  return sets.length == 0 ? new java.util.HashSet<int[]>(java.util.Arrays.asList(new int[0]))
//      : java.util.stream.IntStream.of(sets[0]).boxed()
//          .flatMap(
//              x -> cartesianProduct(java.util.Arrays.copyOfRange(sets, 1, sets.length)).stream()
//                  .map(arr -> java.util.stream.IntStream.concat(java.util.stream.IntStream.of(x),
//                      java.util.stream.IntStream.of(arr)).toArray()))
//          .collect(java.util.stream.Collectors.toSet());
//}


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

