package com.org.bank.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the message related to mapping
 *
 * @author Lavendra Kumar Rajput
 * @Date 04/03/2023
 */
@Slf4j
public final class StreamMapperUtils {


    private StreamMapperUtils() {
    }

    public static StreamMapperUtils newStreamMapperUtils() {
        return new StreamMapperUtils();
    }

    /**
     * Maps the given byte array to an instance of the specified class.
     * <p>
     * This method uses the Jackson ObjectMapper to convert a byte array into an instance of a specified class.
     * If the mapping is successful, it logs an informational message indicating the name of the class to which the response was mapped.
     * If an IOException occurs during the mapping process, it logs an error message with the details of the exception.
     *
     * @param <T>  The type of the class to which the response will be mapped.
     * @param data The byte array to be mapped to an instance of the specified class.
     * @param type The Class object representing the class to which the response will be mapped.
     * @return An instance of the specified class with the mapped response, or null if an error occurred during the mapping process.
     */
    public <T> T mapResponse(byte[] data, Class<T> type) {
        try {
            T result = new ObjectMapper().readValue(data, type);
            log.info("Mapped response to class: {}", type.getName());
            return result;
        } catch (IOException e) {
            log.error("Mapping error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Maps the given string to an instance of the specified class.
     * <p>
     * This method uses the Jackson ObjectMapper to convert a string into an instance of a specified class.
     * If the mapping is successful, it logs an informational message indicating the name of the class to which the response was mapped.
     * If an IOException occurs during the mapping process, it logs an error message with the details of the exception.
     *
     * @param <T>    The type of the class to which the response will be mapped.
     * @param string The string to be mapped to an instance of the specified class.
     * @param class1 The Class object representing the class to which the response will be mapped.
     * @return An instance of the specified class with the mapped response, or null if an error occurred during the mapping process.
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
