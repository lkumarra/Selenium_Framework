package com.org.bank.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.bank.constants.Constants;
import com.org.bank.exceptions.KeyNotValidException;
import com.org.bank.exceptions.ValueNotFoundException;


/**
 * This class contains the methods related to reading the files 
 * @author Lavendra Kumar Rajput
 *
 * @Date 04/03/2023
 */
public class FileReaderUtil {

	Logger logger = LoggerFactory.getLogger(FileReaderUtil.class);

	private FileReader fileReader;
	private Properties properties;

	public FileReaderUtil() {
		try {
			String filePath = Constants.ConfigurationFile;
			fileReader = new FileReader(filePath);
			properties = new Properties();
			logger.info("Successfully find the file at path {}", filePath);
			try {
				properties.load(fileReader);
				logger.info("Successfully load the file {}", filePath);
			} catch (IOException e) {
				logger.error("Error occured while loading the file {}", e.getMessage().toString());
			}

		} catch (FileNotFoundException e) {
			logger.error("File not found {} ", e.getMessage().toString());
		}
	}

	/**
	 * Get the value of the key from propeties file
	 * @param key : Key to fetch the value
	 * @return Return the value of the key 
	 * @throws KeyNotValidException
	 * @throws ValueNotFoundException
	 */
	public String getPropertyValue(String key) throws KeyNotValidException, ValueNotFoundException {
		if (Objects.nonNull(key)) {
			logger.info("Key to get the value {}", key);
			String value = properties.getProperty(key);
			if (Objects.nonNull(value)) {
				return value;
			} else {
				logger.warn("No Value found for the key {}", key);
				throw new ValueNotFoundException(String.format("No value found for key : %s", key));
			}
		} else {
			logger.warn("{} is not a valid key ", key);
			throw new KeyNotValidException(String.format("%s key is not a valid key", key));
		}
	}
	
	public static void main(String[] args) {
		DbUtils dbUtils = new DbUtils();
		dbUtils.insertQuery("Insert into test_status(test_case_name, test_status, test_execution_time) values ('Test 1', 'passed', 123457)");
	}
}
