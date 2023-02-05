package com.org.bank.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.org.bank.constants.Constants;

public class ExcelUtils {

	private Logger logger = LoggerFactory.getLogger(FileReaderUtil.class);

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
				logger.info("Successfully loaded the excel file located at path : {}", Constants.ExcelFile);
				Recordset recordset = connection.executeQuery(query);
				List<String> fields = recordset.getFieldNames();
				while (recordset.next()) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					fields.forEach(field -> {
						Object value = null;
						try {
							value = recordset.getField(field);
						} catch (FilloException e) {
							e.printStackTrace();
						}
						hashMap.put(field.toLowerCase(), value);
					});
					list.add(hashMap);
				}
			}
			logger.info("Successfully read the values from file with query : {}", query);
			return list;
		} catch (FilloException e) {
			logger.error("Error while reading the value with query : {}", query);
		}
		return list;
	}

	public static void main(String[] args) {
		ExcelUtils excelUtils = new ExcelUtils();
		System.out.println(excelUtils
				.fetchData("Select usedIdLabel,passwordLabel,submitButtonLabel,resetButtonLabel,title from LoginPage"));
	}
}
