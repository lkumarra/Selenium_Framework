package com.org.bank.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.org.bank.constants.Constants;

@Slf4j
public class ExcelUtils {


	private ExcelUtils() {
		
	}
	
	public static ExcelUtils newExcelUtils() {
		return new ExcelUtils();
	}
	/**
	 * Read the value from excel using select query
	 * 
	 * @param query : Query to read Data
	 * @return
	 */
	public List<HashMap<String, Object>> fetchData(String query) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			synchronized (this) {
				Fillo fillo = new Fillo();
				Connection connection = fillo.getConnection(Constants.ExcelFile);
				log.info("Successfully loaded the excel file located at path : {}", Constants.ExcelFile);
				Recordset recordset = connection.executeQuery(query);
				List<String> fields = recordset.getFieldNames();
				while (recordset.next()) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					fields.forEach(field -> {
						Object value = null;
						try {
							value = recordset.getField(field);
						} catch (FilloException e) {
							log.error("Error while getting the value of fields from excel with error message : {}",
									e.getMessage());
						}
						hashMap.put(field.toLowerCase(), value);
					});
					list.add(hashMap);
				}
			}
			log.info("Successfully read the values from file with query : {}", query);
			return list;
		} catch (FilloException e) {
			log.error("Error while reading the value with query : {}", query);
		}
		return list;
	}

	/**
	 * This method will read the data from excel and convert that data into json
	 * form
	 * 
	 * @param query : Query to fetch excel data
	 * @return Data in form of JSONArray
	 */
	public JSONArray fetchDataInJSON(String query) {
		JSONArray jsonArray = new JSONArray();
		try {
			synchronized (this) {
				Fillo fillo = new Fillo();
				Connection connection = fillo.getConnection(Constants.ExcelFile);
				log.info("Successfully loaded the excel file located at path : {}", Constants.ExcelFile);
				Recordset recordset = connection.executeQuery(query);
				List<String> fields = recordset.getFieldNames();
				while (recordset.next()) {
					JSONObject jsonObject = new JSONObject();
					fields.forEach(field -> {
						Object value = null;
						try {
							value = recordset.getField(field);
						} catch (FilloException e) {
							log.error("Error while getting the value of fields from excel with error message : {}",
									e.getMessage());
						}
						jsonObject.put(field.toLowerCase(), value);
					});
					jsonArray.put(jsonObject);
				}
			}
			log.info("Successfully read the values from file with query : {}", query);
			return jsonArray;
		} catch (FilloException e) {
			log.error("Error while reading the value with query : {}", query);
		}
		return jsonArray;
	}
}
