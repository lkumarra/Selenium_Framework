package com.org.bank.utils;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the message related to mapping
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 04/03/2023
 */
@Slf4j
public class StreamMapperUtils {


	private StreamMapperUtils() {
		
	}
	
	public static StreamMapperUtils newStreamMapperUtils() {
		return new StreamMapperUtils();
	}
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
			log.info("Successfully mapped the response with class : {}", class1.getName());
			return response;
		} catch (IOException e) {
			log.error("Error occurred while mapping the response : {}", e.getMessage());
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
			log.info("Successfully mapped the response with class : {}", class1.getName());
			return response;
		} catch (IOException e) {
			log.error("Error occurred while mapping the response : {}", e.getMessage());
		}
		return null;
	}
}
