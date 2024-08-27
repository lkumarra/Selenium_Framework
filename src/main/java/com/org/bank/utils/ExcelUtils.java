package com.org.bank.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

@Slf4j
public final class ExcelUtils {

    private final String filePath;

    private final String FILE_LOADED_MESSAGE = "Successfully loaded the excel file located at path : {}";

    private ExcelUtils(String filePath) {
        this.filePath = filePath;
    }

    public static ExcelUtils newExcelUtils(String filePath) {
        return new ExcelUtils(filePath);
    }

    /**
     * Fetches data from an Excel file based on the provided SQL query.
     * <p>
     * This method uses the Fillo library to establish a connection to the Excel file and execute the query.
     * The result of the query is a list of maps, where each map represents a row in the result set.
     * The keys in the map are the column names (converted to lower case), and the values are the corresponding cell values.
     * <p>
     * If an error occurs while getting the value of a field, an error message is logged and the method continues to the next field.
     * If an error occurs while executing the query or establishing the connection, an error message is logged and an empty list is returned.
     *
     * @param query The SQL query to be executed.
     * @return A list of maps representing the result set of the query. Each map represents a row, with column names as keys and cell values as values.
     */
    public List<Map<String, Object>> fetchData(String query) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(filePath);
            log.info(FILE_LOADED_MESSAGE, filePath);
            Recordset recordset = connection.executeQuery(query);
            List<String> fields = recordset.getFieldNames();
            while (recordset.next()) {
                Map<String, Object> row = new HashMap<>();
                for (String field : fields) {
                    row.put(field.toLowerCase(), recordset.getField(field));
                }
                list.add(row);
            }
            log.info("Successfully read the values from file with query : {}", query);
        } catch (FilloException e) {
            log.error("Error while reading the value with query : {}", query);
        }
        return list;
    }


    /**
     * Fetches data from an Excel file based on the provided SQL query and returns it as a JSONArray.
     * <p>
     * This method uses the Fillo library to establish a connection to the Excel file and execute the query.
     * The result of the query is a JSONArray, where each JSONObject represents a row in the result set.
     * The keys in the JSONObject are the column names (converted to lower case), and the values are the corresponding cell values.
     * <p>
     * If an error occurs while getting the value of a field, an error message is logged and the method continues to the next field.
     * If an error occurs while executing the query or establishing the connection, an error message is logged and an empty JSONArray is returned.
     *
     * @param query The SQL query to be executed.
     * @return A JSONArray representing the result set of the query. Each JSONObject represents a row, with column names as keys and cell values as values.
     */
    public JSONArray fetchDataInJSON(String query) {
        JSONArray jsonArray = new JSONArray();
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(filePath);
            log.info(FILE_LOADED_MESSAGE, filePath);
            Recordset recordset = connection.executeQuery(query);
            List<String> fields = recordset.getFieldNames();
            while (recordset.next()) {
                JSONObject jsonObject = new JSONObject();
                for (String field : fields) {
                    jsonObject.put(field.toLowerCase(), recordset.getField(field));
                }
                jsonArray.put(jsonObject);
            }
            log.info("Successfully read the values from file with query : {}", query);
        } catch (FilloException e) {
            log.error("Error while reading the value with query : {}", query);
        }
        return jsonArray;
    }
}
