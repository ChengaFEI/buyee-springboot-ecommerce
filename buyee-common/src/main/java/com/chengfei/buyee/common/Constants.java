package com.chengfei.buyee.common;

public class Constants {
    public static final String S3_BASE_URI;
    static {
	String bucketName = System.getenv().get("AWS_BUCKET_NAME").replace("\n", "");
	String region = System.getenv().get("AWS_REGION").replace("\n", "");
	String pattern = "https://%s.s3.%s.amazonaws.com";
	String uri = String.format(pattern, bucketName, region);
	S3_BASE_URI = bucketName == null ? "" : uri;
    };
}
