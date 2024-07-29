package com.org.bank.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * This class contains the methods related to DataBase Utility
 *
 * @author Lavendra Kumar Rajput
 * @Date 04/03/2023
 */
@Slf4j
public final class DbUtils {
    private final JdbcTemplate jdbcTemplate;

    private static final String ROWS_IMPACTED = "Rows impacted with query : {} are : {}";

    /**
     * Constructor creating object of {@link FileReaderUtil}},
     * {@link DataSource}, {@link JdbcTemplate}
     */
    private DbUtils(String jdbcUrl, String userName, String password) {
        DataSource dataSource = new DriverManagerDataSource(jdbcUrl, userName, password);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private DbUtils(String jdbcUrl) {
        DataSource dataSource = new DriverManagerDataSource(jdbcUrl);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Factory method to create a new DbUtils instance.
     *
     * @param jdbcUrl  The JDBC URL for the database connection.
     * @param userName The username for the database connection.
     * @param password The password for the database connection.
     * @return A new DbUtils instance.
     * <p>
     * This method creates a new DbUtils object with the provided JDBC URL, username, and password.
     * It uses these parameters to create a new DriverManagerDataSource and JdbcTemplate, which are used for database operations.
     */
    public static DbUtils newDbUtils(String jdbcUrl, String userName, String password) {
        return new DbUtils(jdbcUrl, userName, password);
    }

    public static DbUtils newDbUtils(String jdbcUrl) {
        return new DbUtils(jdbcUrl);
    }


    /**
     * Executes an insert query on the database.
     * <p>
     * This method attempts to execute the provided SQL insert query using the jdbcTemplate object.
     * If the jdbcTemplate object is null, a warning is logged and the method returns without executing the query.
     * If an exception occurs during the execution of the query, an error is logged with the exception message.
     *
     * @param query The SQL insert query to be executed.
     */
    public synchronized void insertQuery(String query) {
        try {
            log.info("Query to insert data is : {}", query);
            int result = jdbcTemplate.update(query);
            log.info(ROWS_IMPACTED, query, result);
        } catch (Exception e) {
            log.error("Error occurred while inserting data with query : {} with error : {}", query, e.getMessage());
        }
    }

    /**
     * Executes a select query on the database and returns the result.
     * <p>
     * This method attempts to execute the provided SQL select query using the jdbcTemplate object.
     * If the jdbcTemplate object is null, a warning is logged and the method returns an empty list.
     * If an exception occurs during the execution of the query, an error is logged with the exception message.
     *
     * @param query The SQL select query to be executed.
     * @return A list of maps, where each map represents a row in the result set. The keys in the map are the column names, and the values are the corresponding column values.
     */
    public synchronized List<Map<String, Object>> selectQueryResult(String query) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            log.info("Query to select data is : {}", query);
            result = jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            log.error("Error occurred while fetching the data from query : {} with error : {}", query, e.getMessage());
        }
        return result;
    }

    /**
     * Executes an update query on the database.
     * <p>
     * This method attempts to execute the provided SQL update query using the jdbcTemplate object.
     * If the jdbcTemplate object is null, a warning is logged and the method returns without executing the query.
     * If an exception occurs during the execution of the query, an error is logged with the exception message.
     *
     * @param query The SQL update query to be executed.
     */
    public synchronized void updateQuery(String query) {
        try {
            log.info("Query to update data is  : {}", query);
            int result = jdbcTemplate.update(query);
            log.info(ROWS_IMPACTED, query, result);
        } catch (Exception e) {
            log.error("Error occurred while updating the data from query : {} with error : {}", query, e.getMessage());
        }
    }

    /**
     * Executes a delete query on the database.
     * <p>
     * This method attempts to execute the provided SQL delete query using the jdbcTemplate object.
     * If the jdbcTemplate object is null, a warning is logged and the method returns without executing the query.
     * If an exception occurs during the execution of the query, an error is logged with the exception message.
     *
     * @param query The SQL delete query to be executed.
     */
    public synchronized void deleteQuery(String query) {
        try {
            log.info("Query to delete data is  : {}", query);
            int result = jdbcTemplate.update(query);
            log.info(ROWS_IMPACTED, query, result);
        } catch (Exception e) {
            log.error("Error occurred while deleting the data from query : {} with error : {}", query, e.getMessage());
        }
    }
}
