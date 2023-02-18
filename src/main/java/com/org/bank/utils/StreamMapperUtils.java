package com.org.bank.utils;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains the message related to mapping
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 04/03/2023
 */
public class StreamMapperUtils {

	Logger logger = LoggerFactory.getLogger(StreamMapperUtils.class);

	/**
	 * Mapped the Input Stream Response to the class
	 * 
	 * @param <T>    Class to mapped the response
	 * @param bs     Stream for which response needs to mapped
	 * @param class1 className
	 * @return Object of the class with mapped response
	 */
	public <T> T getClassMappedResponse(byte[] bs, Class<T> class1) {
		try {
			T response = new ObjectMapper().readValue(bs, class1);
			logger.info("Successfully mapped the response with class : {}", class1.getName());
			return response;
		} catch (IOException e) {
			logger.error("Error occured while mapping the response : {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Mapped the string to respective class
	 * 
	 * @param <T>    Class to mapped the response
	 * @param bs     Stream for which response needs to mapped
	 * @param class1 className
	 * @return Object of the class with mapped response
	 */
	public <T> T getClassMappedResponse(String string, Class<T> class1) {
		try {
			T response = new ObjectMapper().readValue(string, class1);
			logger.info("Successfully mapped the response with class : {}", class1.getName());
			return response;
		} catch (IOException e) {
			logger.error("Error occured while mapping the response : {}", e.getMessage());
		}
		return null;
	}
}
