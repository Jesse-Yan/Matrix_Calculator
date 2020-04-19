package application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.*;

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

    private String[] operations;
    public int matrix[][][];

    public String readJsonFile(String filePath) {
        String jsonStr = "";
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Parser(String jsonFilepath) throws FileNotFoundException {

        // read the json file
        if (jsonFilepath == null) throw new FileNotFoundException();
        String data = readJsonFile(jsonFilepath);
        JSONObject json = JSONObject.parseObject(data);
        System.out.println(json.get("Matrix"));
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser("1.json");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}

