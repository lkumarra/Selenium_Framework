package com.org.bank.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * This class contains the methods related to database query
 *
 * @author Lavendra Kumar Rajput
 * @Date 04/03/2023
 */
@Slf4j
public final class DbUtils {
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor creating object of {@link FileReaderUtil}},
     * {@link DataSource}, {@link JdbcTemplate}
     */
    private DbUtils(String jdbcUrl, String userName, String password) {
        try {
            DataSource dataSource = new DriverManagerDataSource(jdbcUrl, userName, password);
            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            log.error("Error occurred while connecting with database with url : {} and error message is : {}", jdbcUrl, e.getMessage());
        }
    }

    private DbUtils(String jdbcUrl) {
        try {
            DataSource dataSource = new DriverManagerDataSource(jdbcUrl);
            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            log.error("Error occurred while connecting with database with url : {} and error message is : {}", jdbcUrl, e.getMessage());
        }
    }

    public static DbUtils newDbUtils(String jdbcUrl, String userName, String password) {
        return new DbUtils(jdbcUrl, userName, password);
    }

    @NotNull
    @Contract("_ -> new")
    public static DbUtils newDbUtils(String jdbcUrl) {
        return new DbUtils(jdbcUrl);
    }

    /**
     * This method will read the secrets values from properties file
     *
     * @param query : Query to insert the data
     */
    public synchronized void insertQuery(String query) {
        update(query);
    }

    /**
     * Get Data from database
     *
     * @param query : Query to get data
     */
    public synchronized List<Map<String, Object>> selectQueryResult(String query) {
        List<Map<String, Object>> result = null;
        synchronized (this) {
            if (Objects.nonNull(jdbcTemplate)) {
                try {
                    log.info("Query to select data is : {}", query);
                    result = jdbcTemplate.queryForList(query);
                } catch (Exception e) {
                    log.error("Error occurred while fetching the data from query : {} with error : {}", query,
                            e.getMessage());
                }
            } else {
                log.warn("Query : {} can not be executed as connection with database is not established", query);
            }
            return result;
        }
    }

    /**
     * Update data in database
     *
     * @param query : Query to update data
     */
    public synchronized void updateQuery(String query) {
        update(query);
    }

    private synchronized void update(String query) {
        synchronized (this) {
            if (Objects.nonNull(jdbcTemplate)) {
                try {
                    log.info("Query to delete data is  : {}", query);
                    int result = jdbcTemplate.update(query);
                    log.info("Rows impacted with query : {} are : {}", query, result);
                } catch (Exception e) {
                    log.error("Error occurred while deleting the data from query : {} with error : {}", query,
                            e.getMessage());
                }
            } else {
                log.warn("Query : {} can not be executed as connection with database is not established", query);
            }
        }
    }

    /**
     * Query to delete data
     *
     * @param query : Query to delete data
     */
    public synchronized void deleteQuery(String query) {
        updateQuery(query);
    }
}
