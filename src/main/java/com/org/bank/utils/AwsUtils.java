package com.org.bank.utils;

import com.org.bank.modals.SecretsModal;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AwsUtils {

	private StreamMapperUtils streamMapperUtils;
	
	private AwsUtils() {
		streamMapperUtils = StreamMapperUtils.newStreamMapperUtils();
	}
	
	public static AwsUtils newAwsUtils() {
		return new AwsUtils();
	}

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
			log.info("Successfully get the response from s3");
			response.close();
			return secretsModal;
		} catch (Exception e) {
			log.error("Error occurred while fetching the object from s3 with error : {}", e.getMessage());
		}
		return new SecretsModal();
	}

}
