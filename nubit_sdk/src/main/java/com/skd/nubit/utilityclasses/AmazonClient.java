package com.skd.nubit.utilityclasses;

import android.content.Context;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;


public class AmazonClient {



    private static AmazonS3Client sS3Client;


    private static BasicAWSCredentials getAWSCredentials() {

/*        String s3_access_key = MyApplication.app_sharedPreferences.getString("s3_access_key", "");
        String s3_secret_key = MyApplication.app_sharedPreferences.getString("s3_secret_key", "");*/


        return new BasicAWSCredentials(MyUtility.bucket_s3_access_key, "" +
                MyUtility.bucket_s3_secret_key);
    }


    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     *
     * @param context An Context instance.
     * @return A default S3 client.
     */
    public static AmazonS3Client getS3Client(Context context) {
        if (sS3Client == null) {
            sS3Client = new AmazonS3Client(getAWSCredentials());
        }
        return sS3Client;
    }

}
