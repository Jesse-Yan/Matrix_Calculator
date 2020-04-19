package application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;
import java.io.FileNotFoundException;

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

    public Parser(String jsonFilepath) throws FileNotFoundException {

        // read the json file
        if (jsonFilepath == null) throw new FileNotFoundException();
        File file = new File(jsonFilepath);
        String fileText = file.toString();
        JSONObject json = JSON.parseObject(fileText);
        System.out.println(json.get("operations"));


    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser("1.json");
        }catch (Exception e){

        }

    }
}

