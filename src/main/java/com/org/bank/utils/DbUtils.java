package com.org.bank.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.org.bank.modals.SecretsModal;

/**
 * This class contains the methods related to DataBase Utitlity
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 04/03/2023
 */
public class DbUtils {

	Logger logger = LoggerFactory.getLogger(FileReaderUtil.class);

	private FileReaderUtil fileReaderUtil;
	private AwsUtils awsUtils;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	/**
	 * Constructor creating object of {@link FileReaderUtil}, {@link AwsUtils},
	 * {@link DataSource}, {@link JdbcTemplate}
	 */
	private DbUtils() {
		fileReaderUtil = FileReaderUtil.newFileReaderUtil();
		awsUtils = AwsUtils.newAwsUtils();
		SecretsModal secretsModal = getSecretsValues();
		if (Objects.nonNull(secretsModal.getJdbcUrl()) || Objects.nonNull(secretsModal.getPassword())) {
			dataSource = new DriverManagerDataSource(secretsModal.getJdbcUrl(), secretsModal.getUserName(),
					secretsModal.getPassword());
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}
	
	public static DbUtils newDbUtils() {
		return new DbUtils();
	}

	/**
	 * This method will read the secrets values from properties file
	 * 
	 * @return {@link SecretsModal}
	 */
	private SecretsModal getSecretsValues() {
		String awsRegion = null;
		String awsSecretKeyId = null;
		String awsSecretAccessKey = null;
		String bucketName = null;
		String objectKey = null;
		String driverClassName = null;
		SecretsModal secretsModal = null;
		try {
			awsRegion = fileReaderUtil.getPropertyValue("aws.region");
			awsSecretKeyId = fileReaderUtil.getPropertyValue("aws.accessKeyId");
			awsSecretAccessKey = fileReaderUtil.getPropertyValue("aws.secretAccessKey");
			bucketName = fileReaderUtil.getPropertyValue("aws.bucketName");
			objectKey = fileReaderUtil.getPropertyValue("aws.objectName");
			driverClassName = fileReaderUtil.getPropertyValue("postgresql.className");
			secretsModal = awsUtils.getS3Object(awsRegion, awsSecretKeyId, awsSecretAccessKey, bucketName, objectKey);
			secretsModal.setDriverClassName(driverClassName);
		} catch (Exception e) {
			logger.error(
					"Error occured while fetching the data from s3 and setting in secret modal class with error : {}",
					e.getMessage());
		}
		return secretsModal;
	}

	/**
	 * Insert the data in database
	 * 
	 * @param query : Query to insert the data
	 */
	public synchronized void insertQuery(String query) {
		synchronized (this) {
			if (Objects.nonNull(jdbcTemplate)) {
				try {
					logger.info("Query to insert data is : {}", query);
					int result = jdbcTemplate.update(query);
					logger.info("Rows impacted with query : {} are : {}", query, result);
				} catch (Exception e) {
					logger.error("Error occured while inserting data with query : {} with error : {}", query,
							e.getMessage());
				}
			} else {
				logger.warn("Query : {} can not be executed as connection with database is not establised", query);
			}
		}
	}

	/**
	 * Get Data from database
	 * 
	 * @param query : Query to get data
	 * @return
	 */
	public synchronized List<Map<String, Object>> selectQueryResult(String query) {
		List<Map<String, Object>> result = null;
		synchronized (this) {
			if (Objects.nonNull(jdbcTemplate)) {
				try {
					logger.info("Query to select data is : {}", query);
					result = jdbcTemplate.queryForList(query);
				} catch (Exception e) {
					logger.error("Error occured while fetching the data from query : {} with error : {}", query,
							e.getMessage());
				}
			} else {
				logger.warn("Query : {} can not be executed as connection with database is not establised", query);
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
		synchronized (this) {
			if (Objects.nonNull(jdbcTemplate)) {
				try {
					logger.info("Query to update data is  : {}", query);
					int result = jdbcTemplate.update(query);
					logger.info("Rows impacted with query : {} are : {}", query, result);
				} catch (Exception e) {
					logger.error("Error occured while updating the data from query : {} with error : {}", query,
							e.getMessage());
				}
			} else {
				logger.warn("Query : {} can not be executed as connection with database is not establised", query);
			}
		}
	}

	/**
	 * Query to delete data
	 * 
	 * @param query : Query to delete data
	 */
	public synchronized void deleteQuery(String query) {
		synchronized (this) {
			if (Objects.nonNull(jdbcTemplate)) {
				try {
					logger.info("Query to delete data is  : {}", query);
					int result = jdbcTemplate.update(query);
					logger.info("Rows impacted with query : {} are : {}", query, result);
				} catch (Exception e) {
					logger.error("Error occured while deleting the data from query : {} with error : {}", query,
							e.getMessage());
				}
			} else {
				logger.warn("Query : {} can not be executed as connection with database is not establised", query);
			}
		}
	}
}
