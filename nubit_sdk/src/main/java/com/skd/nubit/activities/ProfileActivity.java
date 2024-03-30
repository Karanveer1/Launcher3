package com.skd.nubit.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.skd.nubit.R;
import com.skd.nubit.utilityclasses.AmazonClient;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    String str_get_ProfileName, str_get_ProfileMobile, str_get_ProfileEmail, str_get_ProfileDOB,
            str_get_Profession, getProfile, getName, getNumber, getDob, getEmail, getProfession,
            getDeviceId, img_Path, get_Amazon_ImageURL, userChoosenTask;

    EditText edt_profileName, edt_profilePhone, edt_profileDOB, edt_profileEmail,
            edt_profileProfession;


    ImageView img_profile;


    ImageView img_back;
    TextView btn_submit;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;


    ProgressBar progress_profile;

    ScrollView profile_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        img_profile = findViewById(R.id.img_profile);
        profile_scroll = findViewById(R.id.profile_scroll);

        btn_submit = findViewById(R.id.btn_submit);

        img_back = findViewById(R.id.back);

        edt_profileName = findViewById(R.id.edit_username);
        edt_profilePhone = findViewById(R.id.edit_phone);
        edt_profileEmail = findViewById(R.id.edit_email);
        edt_profileDOB = findViewById(R.id.edit_dob);
        edt_profileProfession = findViewById(R.id.edit_occupation);
        progress_profile = findViewById(R.id.progressbar);


        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_profile.setOnClickListener(this);
        edt_profileDOB.setOnClickListener(this);


        getDeviceId = MyUtility.getDeviceID(ProfileActivity.this);


        fill_Profile_onCreate();

        MyApplication.getInstance().trackScreenView("Profile Screen");


        profile_scroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                profile_scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(getApplicationContext(), "Opps, Something went wrong,Try again Or try " +
                "other options", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {


        int i = view.getId();
        if (i == R.id.btn_submit) {
            MyApplication.getInstance().trackEvent("Profile Login", "Profile Updated",
                    "Profile Login");

            str_get_ProfileName = edt_profileName.getText().toString().trim();
            str_get_ProfileMobile = edt_profilePhone.getText().toString().trim();
            str_get_ProfileEmail = edt_profileEmail.getText().toString().trim();
            str_get_ProfileDOB = edt_profileDOB.getText().toString().trim();
            str_get_Profession = edt_profileProfession.getText().toString().trim();


            if (!TextUtils.isEmpty(img_Path) || !TextUtils.isEmpty(str_get_ProfileName) ||
                    !TextUtils.isEmpty(str_get_ProfileMobile) || !TextUtils.isEmpty
                    (str_get_ProfileDOB) || !TextUtils.isEmpty(str_get_ProfileEmail) ||
                    !TextUtils.isEmpty(str_get_Profession)) {

                if (!TextUtils.isEmpty(str_get_ProfileEmail) & !TextUtils.isEmpty
                        (str_get_ProfileMobile)) {
                    if (emailValidator(str_get_ProfileEmail) & str_get_ProfileMobile.length()
                            == 10 & !str_get_ProfileMobile.startsWith("0")) {

                        if (MyUtility.isConnectedToInternet()) {

                            if (!TextUtils.isEmpty(img_Path)) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new FileUploadStarter().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new FileUploadStarter().execute();
                                }


                            } else {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new updateProfileAsyn().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new updateProfileAsyn().execute();
                                }


                            }

                        } else {
                            MyUtility.NoInternet_Msg(ProfileActivity.this);
                        }


                    } else if (!emailValidator(str_get_ProfileEmail)) {
                        Toast.makeText(getApplicationContext(), "InValid Email Address.",
                                Toast.LENGTH_SHORT).show();
                    } else if (str_get_ProfileMobile.length() != 10 || str_get_ProfileMobile
                            .startsWith("0")) {
                        Toast.makeText(getApplicationContext(), "InValid Mobile Number.",
                                Toast.LENGTH_SHORT).show();
                    }


                } else if (!TextUtils.isEmpty(str_get_ProfileEmail)) {
                    if (!emailValidator(str_get_ProfileEmail)) {
                        Toast.makeText(getApplicationContext(), "InValid Email Address.",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        // Toast.makeText(getApplicationContext(), "Go", Toast.LENGTH_SHORT)
                        // .show();

                        if (!TextUtils.isEmpty(img_Path)) {


                            if (MyUtility.isConnectedToInternet()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new FileUploadStarter().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new FileUploadStarter().execute();
                                }
                            } else {
                                MyUtility.NoInternet_Msg(ProfileActivity.this);
                            }

                        } else {


                            if (MyUtility.isConnectedToInternet()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new updateProfileAsyn().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new updateProfileAsyn().execute();
                                }
                            } else {
                                MyUtility.NoInternet_Msg(ProfileActivity.this);
                            }
                        }
                    }
                } else if (!TextUtils.isEmpty(str_get_ProfileMobile)) {


                    if (str_get_ProfileMobile.length() != 10 || str_get_ProfileMobile
                            .startsWith("0")) {
                        Toast.makeText(getApplicationContext(), "InValid Mobile Number",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(getApplicationContext(), "Go", Toast.LENGTH_SHORT)
                        // .show();
                        if (!TextUtils.isEmpty(img_Path)) {


                            if (MyUtility.isConnectedToInternet()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new FileUploadStarter().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new FileUploadStarter().execute();
                                }
                            } else {
                                MyUtility.NoInternet_Msg(ProfileActivity.this);
                            }

                        } else {


                            if (MyUtility.isConnectedToInternet()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    new updateProfileAsyn().executeOnExecutor(AsyncTask
                                            .THREAD_POOL_EXECUTOR);
                                } else {
                                    new updateProfileAsyn().execute();
                                }
                            } else {
                                MyUtility.NoInternet_Msg(ProfileActivity.this);
                            }
                        }
                    }

                } else if (TextUtils.isEmpty(str_get_ProfileEmail) & TextUtils.isEmpty
                        (str_get_ProfileMobile)) {
                    // Toast.makeText(getApplicationContext(), "Go", Toast.LENGTH_SHORT).show();

                    if (!TextUtils.isEmpty(img_Path)) {


                        if (MyUtility.isConnectedToInternet()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                new FileUploadStarter().executeOnExecutor(AsyncTask
                                        .THREAD_POOL_EXECUTOR);
                            } else {
                                new FileUploadStarter().execute();
                            }
                        } else {
                            MyUtility.NoInternet_Msg(ProfileActivity.this);
                        }


                    } else {


                        if (MyUtility.isConnectedToInternet()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                new updateProfileAsyn().executeOnExecutor(AsyncTask
                                        .THREAD_POOL_EXECUTOR);
                            } else {
                                new updateProfileAsyn().execute();
                            }
                        } else {
                            MyUtility.NoInternet_Msg(ProfileActivity.this);
                        }
                    }

                }

            } else {

                Toast.makeText(ProfileActivity.this, "Fill atleast one field to upload " +
                        "profile", Toast.LENGTH_SHORT).show();
            }


        } else if (i == R.id.img_profile) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                check_for_Profile_permissions();
            } else {
                selectImage();
            }

        } else if (i == R.id.back) {
            finish();

        } else if (i == R.id.edit_dob) {
            setDate();


        }


    }


    private void selectImage() {
        final CharSequence[] items = {"Open Camera", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Take Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Open Camera")) {
                    userChoosenTask = "Open Camera";

                    cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";

                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {


        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(i, SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyUtility.Profile_REQUEST_PERMISSION_SETTING) {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, "android.permission" +
                    ".WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission
                    (ProfileActivity.this, "android.permission.CAMERA") == 0) {
                //Got Permission
                /*      Toast.makeText(getBaseContext(), "onActivityResult", Toast.LENGTH_LONG)
                .show();*/
                selectImage();
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        img_Path = destination.getPath();


      /*  byte[] byteArrayImage = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        Log.d("encodedImage",encodedImage);
*/


        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        img_profile.setImageBitmap(thumbnail);
    }


    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null,
                        null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                String newCompressURI = compressURI(ProfileActivity.this, filePath);


                img_Path = newCompressURI;

                img_profile.setImageURI(selectedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public class FileUploadStarter extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_profile.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {
            //  String path = params[0];
          /*  String s3_bucket_name = MyApplication.app_sharedPreferences.getString("bucket_name",
                    "");*/

            String s3_bucket_name=MyUtility.bucket_name;


            File fileToUpload = new File(img_Path);
            AmazonS3Client s3Client = AmazonClient.getS3Client(getApplicationContext());
            PutObjectRequest putRequest = new PutObjectRequest(s3_bucket_name, fileToUpload
                    .getName()
                    , fileToUpload);
            PutObjectResult putResponse = null;
            try {
                putResponse = s3Client.putObject(putRequest.withCannedAcl(CannedAccessControlList
                        .PublicRead));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = "";
            try {
                url = s3Client.getResourceUrl(s3_bucket_name, fileToUpload.getName());
                //generatePresignedUrl( urlRequest );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            //  Log.d("URL", url);
            progress_profile.setVisibility(View.GONE);
            get_Amazon_ImageURL = url;


            if (MyUtility.isConnectedToInternet()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new updateProfileAsyn().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    new updateProfileAsyn().execute();
                }
            } else {
                MyUtility.NoInternet_Msg(ProfileActivity.this);
            }


        }


    }


    public void setDate() {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edt_profileDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\" +
                ".[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class updateProfileAsyn extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_profile.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(get_Amazon_ImageURL)) {
                get_Amazon_ImageURL = MyApplication.app_sharedPreferences.getString
                        ("userProfileIcon", "");
            }


        }


        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(MyUtility.insert_profileDataAPI); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("device_id", getDeviceId);
                postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                        ("getAdvertisementId", ""));
                postDataParams.put("profile_image", get_Amazon_ImageURL);
                postDataParams.put("name", str_get_ProfileName);
                postDataParams.put("phone_no", str_get_ProfileMobile);
                postDataParams.put("dob", str_get_ProfileDOB);
                postDataParams.put("email", str_get_ProfileEmail);
                postDataParams.put("occupation", str_get_Profession);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(MyUtility.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();

                    return sb.toString();


                } else {
                    return "";
                }
            } catch (Exception e) {
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String result) {


            progress_profile.setVisibility(View.GONE);
            try {

                if (!TextUtils.isEmpty(result)) {


                    MyApplication.app_editor.putString("userProfileIcon", get_Amazon_ImageURL);
                    MyApplication.app_editor.putString("userProfileName", str_get_ProfileName);
                    MyApplication.app_editor.putString("userProfileMob", str_get_ProfileMobile);
                    MyApplication.app_editor.putString("userProfileDOB", str_get_ProfileDOB);
                    MyApplication.app_editor.putString("userProfileEMail", str_get_ProfileEmail);
                    MyApplication.app_editor.putString("userProfileProf", str_get_Profession);

                    MyApplication.app_editor.apply();
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast
                            .LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Profile uploading failed, Pls try " +
                            "after some time", Toast.LENGTH_LONG).show();
                    Log.d("nubit_problem", "insert_profileDataAPI not working,Pls check");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void fill_Profile_onCreate() {
        getProfile = MyApplication.app_sharedPreferences.getString("userProfileIcon", "");
        getName = MyApplication.app_sharedPreferences.getString("userProfileName", "");
        getNumber = MyApplication.app_sharedPreferences.getString("userProfileMob", "");
        getDob = MyApplication.app_sharedPreferences.getString("userProfileDOB", "");
        getEmail = MyApplication.app_sharedPreferences.getString("userProfileEMail", "");
        getProfession = MyApplication.app_sharedPreferences.getString("userProfileProf", "");

        if (!TextUtils.isEmpty(getProfile) && getProfile != null && !getProfile.equals("null")) {

            Glide.with(ProfileActivity.this).load(getProfile).asBitmap().thumbnail(0.5f)
                    .placeholder(R.drawable.placeholder_apps)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                    .into(img_profile);
        }
        if (!TextUtils.isEmpty(getName)) {
            edt_profileName.setText(getName);
        }
        if (!TextUtils.isEmpty(getNumber)) {
            edt_profilePhone.setText(getNumber);
        }
        if (!TextUtils.isEmpty(getDob)) {
            edt_profileDOB.setText(getDob);
        }
        if (!TextUtils.isEmpty(getEmail)) {
            edt_profileEmail.setText(getEmail);
        }
        if (!TextUtils.isEmpty(getProfession)) {
            edt_profileProfession.setText(getProfession);
        }

    }


    public static String compressURI(Context context, String path) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int targetHeight = 400;
        int targetWidth = 400;

        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        try {
            bmpOptions.inJustDecodeBounds = true;
            bmpOptions.inDither = false;
            bmpOptions.inPurgeable = true;
            bmpOptions.inInputShareable = true;
        } catch (Exception e) {
        }

        BitmapFactory.decodeFile(path, bmpOptions);
        int currHeight = bmpOptions.outHeight;
        int currWidth = bmpOptions.outWidth;

        int sampleSize = 1;
        if (currHeight > targetHeight || currWidth > targetWidth) {
            if (currWidth > currHeight) {
                sampleSize = Math.round((float) currHeight / (float) targetHeight);
            } else {
                sampleSize = Math.round((float) currWidth / (float) targetWidth);
            }
        }

        bmpOptions.inSampleSize = sampleSize;
        bmpOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmpOptions);
        Matrix matrix = null;
        try {
            ExifInterface exif = new ExifInterface(path);

            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface
                    .ORIENTATION_NORMAL);

            int rotationInDegrees = exifToDegrees(rotation);
            matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

        } catch (IOException e) {
            //  e.printStackTrace();
        }

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight
                (), matrix, true);


        newBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);

        InputStream in = new ByteArrayInputStream(bos.toByteArray());


        File destination = new File(Environment.getExternalStorageDirectory(),
                "/Lava/");
        if (!destination.exists()) {
            destination.mkdirs();
        }

        String imagename = "img_" + System.currentTimeMillis() + ".jpg";
        File finalfile = new File(destination, imagename);


        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(finalfile.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            copyStream(in, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bitmap.recycle();
            newBitmap.recycle();
            bitmap = null;
            newBitmap = null;
        } catch (Exception e) {

        }
//        return Uri.fromFile(new File(path));
//        return */in;
        return finalfile.getPath();
    }

    private float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * This is used for camera function and for facebbok single sign in.
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyUtility.Profile_PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                selectImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                        MyUtility.profile_permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity
                        .this, MyUtility.profile_permissionsRequired[1])) {
                    //Show Information about why you need the permission
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProfileActivity.this);
                    builder.setTitle("We needs your permission to proceed here");
                    builder.setMessage("Please grant all required permissions to make it working");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();

                            ActivityCompat.requestPermissions(ProfileActivity.this, MyUtility
                                    .profile_permissionsRequired, MyUtility
                                    .Profile_PERMISSION_CALLBACK_CONSTANT);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //switch_findMe.setChecked(falx``se);
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    // switch_findMe.setChecked(false);
                    Toast.makeText(getBaseContext(), "This Function should not work, If you will " +
                            "not provide the required permissions", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MyUtility.Profile_sentToSettings) {
            //  Toast.makeText(getBaseContext(), "onPostResume", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, "android.permission" +
                    ".WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission
                    (ProfileActivity.this, "android.permission.CAMERA") == 0) {
                //Got Permission
                //  Toast.makeText(getBaseContext(),"onPostResume",Toast.LENGTH_LONG).show();
                selectImage();
            }
        }
    }


    private void check_for_Profile_permissions() {

        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, MyUtility
                .profile_permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ProfileActivity.this, MyUtility
                .profile_permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                    MyUtility.profile_permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                    MyUtility.profile_permissionsRequired[1])) {
                //Show Information about why you need the permission
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app
                        .AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("We needs your permission to proceed here");
                builder.setMessage("Please grant all required permission to make it working");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(ProfileActivity.this, MyUtility
                                .profile_permissionsRequired, MyUtility
                                .Profile_PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // switch_findMe.setChecked(false);
                        dialog.cancel();
                    }
                });
                builder.show();

            } else if (MyApplication.app_sharedPreferences.getBoolean(MyUtility
                    .profile_permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app
                        .AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("We needs your permission to proceed here");
                builder.setMessage("Click Grant button> Go to Permissions>Enable all permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        MyUtility.Profile_sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MyUtility
                                .Profile_REQUEST_PERMISSION_SETTING);
                        // switch_findMe.setChecked(false);
                        Toast.makeText(getBaseContext(), "Now Click on permissions,and enable all" +
                                " required permissions", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // switch_findMe.setChecked(false);
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission

                ActivityCompat.requestPermissions(ProfileActivity.this, MyUtility
                        .profile_permissionsRequired, MyUtility
                        .Profile_PERMISSION_CALLBACK_CONSTANT);
            }


            MyApplication.app_editor.putBoolean(MyUtility.profile_permissionsRequired[0], true);
            MyApplication.app_editor.apply();


        } else {
            //You already have the permission, just go ahead.
            // switch_findMe.setChecked(true);
            selectImage();
        }


    }


}






