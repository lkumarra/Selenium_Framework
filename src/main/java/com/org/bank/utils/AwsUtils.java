package com.org.bank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.org.bank.modals.SecretsModal;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 * 
 * @author Lavendra Kumar Rajput
 *
 * @Date 04/02/2023
 */
public class AwsUtils {

	private StreamMapperUtils streamMapperUtils;

	public AwsUtils() {
		streamMapperUtils = new StreamMapperUtils();
	}

	Logger logger = LoggerFactory.getLogger(AwsUtils.class);

	/**
	 * Map the stream to {@link SecretsModal}
	 * 
	 * @param awsRegion
	 * @param awsAccessKeyId
	 * @param awsSecretAccessKey
	 * @param bucket
	 * @param objectKey
	 * @return {@link SecretsModal}
	 */
	public SecretsModal getS3Object(String awsRegion, String awsAccessKeyId, String awsSecretAccessKey, String bucket,
			String objectKey) {
		try {
			System.setProperty("aws.region", awsRegion);
			System.setProperty("aws.accessKeyId", awsAccessKeyId);
			System.setProperty("aws.secretAccessKey", awsSecretAccessKey);
			S3Client client = S3Client.builder().build();
			GetObjectRequest request = GetObjectRequest.builder().bucket(bucket).key(objectKey).build();
			ResponseInputStream<GetObjectResponse> response = client.getObject(request);
			SecretsModal secretsModal = streamMapperUtils.getClassMappedResponse(response.readAllBytes(),
					SecretsModal.class);
			logger.info("Successfully get the response from s3");
			response.close();
			return secretsModal;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured while fetching the object from s3", e);
		}
		return null;
	}

}
