package com.org.bank.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        try {
            FileReader fileReader = new FileReader(filePath);
            properties = new Properties();
            log.info("Successfully find the file at path {}", filePath);
            try {
                properties.load(fileReader);
                log.info("Successfully load the file {}", filePath);
            } catch (IOException e) {
                log.error("Error occurred while loading the file {}", e.getMessage());
            }
        } catch (FileNotFoundException e) {
            log.error("File not found {} ", e.getMessage());
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
