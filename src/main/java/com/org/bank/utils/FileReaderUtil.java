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

    /**
     * Constructor for the FileReaderUtil class.
     *
     * @param filePath The path to the file that needs to be read.
     *                 <p>
     *                 This constructor initializes a new FileReaderUtil object. It takes a file path as a parameter,
     *                 creates a new FileReader object with the given file path, and initializes the properties object.
     *                 <p>
     *                 It also logs the successful finding and loading of the file. If the file is not found, it logs an error message.
     *                 If there is an error while loading the file, it logs an error message.
     *                 <p>
     *                 Exceptions:
     *                 FileNotFoundException - If the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     *                 IOException - If an I/O error occurs.
     */
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
     * This method retrieves the value of a given key from the properties file.
     *
     * @param key The key for which the value needs to be retrieved.
     * @return The value of the given key.
     * @throws KeyNotValidException   If the provided key is null.
     * @throws ValueNotFoundException If no value is found for the given key.
     *                                <p>
     *                                The method first checks if the provided key is not null. If the key is null, it logs a warning and throws a KeyNotValidException.
     *                                If the key is not null, it attempts to retrieve the value for the key from the properties object.
     *                                If a value is found, it returns the value. If no value is found, it logs a warning and throws a ValueNotFoundException.
     */
    public String getPropertyValue(String key) throws KeyNotValidException, ValueNotFoundException {
        if (key == null) {
            log.warn("Key is not valid");
            throw new KeyNotValidException("Key is not valid");
        }

        log.info("Key to get the value {}", key);
        String value = properties.getProperty(key);

        if (value == null) {
            log.warn("No Value found for the key {}", key);
            throw new ValueNotFoundException("No value found for key : " + key);
        }

        return value;
    }

}
