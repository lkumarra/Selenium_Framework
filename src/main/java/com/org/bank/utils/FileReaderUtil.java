package com.org.bank.utils;

import java.io.FileReader;
import java.io.Reader;
import java.util.Objects;
import java.util.Properties;

import com.org.bank.exceptions.KeyNotValidException;
import com.org.bank.exceptions.ValueNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the methods related to reading the files
 *
 * @author Lavendra Kumar Rajput
 * @Date 04/03/2023
 */
@Slf4j
public final class FileReaderUtil {

    private Properties properties;

    private FileReaderUtil(String filePath) {
        Reader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            properties = new Properties();
            log.info("Successfully find the file at path {}", filePath);
            properties.load(fileReader);
            log.info("Successfully load the file {}", filePath);
        } catch (Exception e) {
            log.error("Error while reading the property file with error message : {}", e.getMessage());
        } finally {
            try {
                assert fileReader != null;
                fileReader.close();
            } catch (Exception e) {
                log.error("Error occurred while closing the reader with error message : {}", e.getMessage());
            }
        }
    }

    public static FileReaderUtil newFileReaderUtil(String filePath) {
        return new FileReaderUtil(filePath);
    }

    /**
     * Get the value of the key from properties file
     *
     * @param key : Key to fetch the value
     * @return Return the value of the key
     * @throws KeyNotValidException
     * @throws ValueNotFoundException
     */
    public String getPropertyValue(String key) throws KeyNotValidException, ValueNotFoundException {
        if (Objects.nonNull(key)) {
            log.info("Key to get the value {}", key);
            String value = properties.getProperty(key);
            if (Objects.nonNull(value)) {
                return value;
            } else {
                log.warn("No Value found for the key {}", key);
                throw new ValueNotFoundException(String.format("No value found for key : %s", key));
            }
        } else {
            log.warn("{} is not a valid key ", (Object) null);
            throw new KeyNotValidException(String.format("%s key is not a valid key", (Object) null));
        }
    }

}
