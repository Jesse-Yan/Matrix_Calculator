package application;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class parse from a JSON file
 * @author Jesse
 * EXAMPLE JSON
 * It means (matrix[0] + matrix[1]) * matrix[2]
 *
 * {
 *     "operations": [
 *         "+",
 *         "*"
 *     ],
 *     "matrix": [
 *         [
 *             [2,5,6,21],
 *             [54,543,5,32],
 *             [234,23,432,5],
 *             [123,43,25,3]
 *         ],
 *         [
 *             [324,56,3,2],
 *             [1,2,3,4],
 *             [1,2,3,4],
 *             [1,2,3,4]
 *         ],
 *         [
 *             [1,2,3,4],
 *             [1,2,3,4],
 *             [1,2,3,4],
 *             [1,2,3,4]
 *         ]
 *     ]
 * }
 */
public class Parser {
    File data;
    public Parser(String path) throws FileNotFoundException {
        if(path==null) throw new FileNotFoundException();
        if (!path.contains(".json")) path = path+".json";
        data = new File(path);
    }
}
