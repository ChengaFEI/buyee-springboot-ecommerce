package com.chengfei.buyee.admin;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
public class AmazonS3Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Util.class);
    private static final String BUCKET_NAME;
    static {
	BUCKET_NAME = System.getenv("AWS_BUCKET_NAME");
    }
    // Create Tasks
    public static void saveFile(String folderName, String fileName, InputStream inputStream) {
	S3Client client = S3Client.builder().build();
	PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET_NAME)
		.key(folderName + "/" + fileName)
		.build();
	try (inputStream) {
	    int contentLength = inputStream.available();
	    client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
	} catch (IOException e) {
	    LOGGER.error("Could not upload file to Amazon S3.", e);
	}
    }
    // Read Tasks
    public static List<String> listFolder(String folderName) {
	S3Client client = S3Client.builder().build();
	ListObjectsRequest listRequest = ListObjectsRequest.builder()
		.bucket(BUCKET_NAME).prefix(folderName)
		.build();
	ListObjectsResponse response = client.listObjects(listRequest);
	List<S3Object> contents = response.contents();
	ListIterator<S3Object> listIterator = contents.listIterator();
	List<String> listKeys = new ArrayList<>();
	while (listIterator.hasNext()) {
	    S3Object object = listIterator.next();
	    listKeys.add(object.key());
	}
	return listKeys;
    }
    // Delete Tasks
    public static void deleteFile(String fileName) {
	S3Client client = S3Client.builder().build();
	DeleteObjectRequest request = DeleteObjectRequest.builder()
		.bucket(BUCKET_NAME).key(fileName)
		.build();
	client.deleteObject(request);
    }
    public static void deleteFolder(String folderName) {
	S3Client client = S3Client.builder().build();
	ListObjectsRequest listRequest = ListObjectsRequest.builder()
		.bucket(BUCKET_NAME).prefix(folderName)
		.build();
	ListIterator<S3Object> listIterator = client.listObjects(listRequest)
		.contents().listIterator();
	while (listIterator.hasNext()) {
	    S3Object object = listIterator.next();
	    DeleteObjectRequest request = DeleteObjectRequest.builder()
		    .bucket(BUCKET_NAME).key(object.key())
		    .build();
	    client.deleteObject(request);
	}
    }
}
