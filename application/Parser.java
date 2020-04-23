package application;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.List;

/**
 * This class parse from a JSON file
 *
 * @author Jesse
 * EXAMPLE JSON
 * It means (matrix[0] + matrix[1]) * matrix[2]
 * <p>
 * {
 * "operations": [
 * "+",
 * "*"
 * ],
 * "matrix": [
 * [
 * [2,5,6,21],
 * [54,543,5,32],
 * [234,23,432,5],
 * [123,43,25,3]
 * ],
 * [
 * [324,56,3,2],
 * [1,2,3,4],
 * [1,2,3,4],
 * [1,2,3,4]
 * ],
 * [
 * [1,2,3,4],
 * [1,2,3,4],
 * [1,2,3,4],
 * [1,2,3,4]
 * ]
 * ]
 * }
 */
public class Parser {

    @SuppressWarnings("rawtypes")
    private List operations;
    @SuppressWarnings("rawtypes")
    private List matrixList;

    /**
     * Read json string from given file
     *
     * @param filePath
     * @return json string
     */
    public String readJsonFile(String filePath) throws IOException {
        String jsonStr = "";

        File jsonFile = new File(filePath);
        FileReader fileReader = new FileReader(jsonFile);

        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;
    }

    /**
     * constructor method to parse a json file
     *
     * @param jsonFilepath
     * @throws FileNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public Parser(String jsonFilepath) throws IOException {

        // read the json file
        if (jsonFilepath == null) throw new FileNotFoundException();
        String data = readJsonFile(jsonFilepath);
        JSONObject json = JSONObject.parseObject(data);
        matrixList = (List) json.get("matrix");
        operations = (List) json.get("operations");
    }

    /**
     * get list of matrix
     *
     * @return list of matrix
     */
    @SuppressWarnings("rawtypes")
    public List getMatrix() {
        return matrixList;
    }

    /**
     * get list of operations
     *
     * @return list of operations
     */
    @SuppressWarnings("rawtypes")
    public List getOperations() {
        return operations;
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            Parser parser = new Parser("1.json");
            System.out.println("success!!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}

