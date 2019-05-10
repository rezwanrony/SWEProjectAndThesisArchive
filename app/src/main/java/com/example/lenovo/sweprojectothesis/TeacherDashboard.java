package com.example.lenovo.sweprojectothesis;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherDashboard extends AppCompatActivity {

    TextView tv_name,tv_email,tv_phone;
    SQLiteHandler db;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    ImageView img_propic;
    String TAG = "permission";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        tv_name=findViewById(R.id.tv_proname_teacherdashboard);
        tv_email=findViewById(R.id.tv_email_teacherdashboard);
        tv_phone=findViewById(R.id.tv_phone_teacherdashboard);
        db=new SQLiteHandler(TeacherDashboard.this);
        ImageView img_settings=findViewById(R.id.img_settings_teacher);
        img_propic=findViewById(R.id.img_profilepicteacher);

        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();

        tv_name.setText(teacherList.get(0).getName().toString());
        tv_email.setText(teacherList.get(0).getEmail().toString());
        tv_phone.setText(teacherList.get(0).getPhone().toString());

        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        CardView card_seecourse=findViewById(R.id.card_seecourselist);
        card_seecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,RequestedStudentListActivity.class));
            }
        });
        CardView card_seecourse2=findViewById(R.id.card_virtualclassroomteacher);
        card_seecourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,CreateVirtualClassroomActivity.class));
            }
        });

        CardView card_vclist=findViewById(R.id.card_createdvc);
        card_vclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,CreatedClassroomListActivity.class));
            }
        });

        CardView card_projectstat=findViewById(R.id.card_projectstats);
        card_projectstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,ProjectStatsActivity.class));
            }
        });

        CardView card_projectlist=findViewById(R.id.card_seeprojectlisteacher);
        card_projectlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboard.this,ProjectStatsActivity.class));
            }
        });

        getTeacherImage();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1888) {
                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Bitmap convertimage = getResizedBitmap(photo, 600);
                    Uri imageUri = getImageUri(TeacherDashboard.this, photo);
                    InputStream imageStream = null;
                    String path = getRealPathFromURI(imageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    convertimage.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] byteArray = stream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    profilepicupload(encoded);
                    Log.d("encoded",encoded);
                    img_propic.setImageBitmap(convertimage);
                    //ProfilePicUpload(path);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }

        if (resultCode==RESULT_OK) {

            if (requestCode == 1) {

                try {
                    Uri imagesUri = data.getData();
                    String path = imagesUri.getPath();
                    InputStream imageStream = null;
                    try {
                        imageStream = getApplicationContext().getContentResolver().openInputStream(imagesUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    Bitmap convertimage = getResizedBitmap(selectedImage, 600);
                    Uri imageUris = getImageUri(TeacherDashboard.this, selectedImage);
                    String paths = getRealPathFromImageURI(imageUris);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    convertimage.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] byteArray = stream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    Log.d("encoded",encoded);
                    profilepicupload(encoded);
                    img_propic.setImageBitmap(convertimage);
                    Toast.makeText(getApplicationContext(), paths, Toast.LENGTH_SHORT).show();
                    //ProfilePicUpload(paths);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public String getRealPathFromImageURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(TeacherDashboard.this, new String[]{Manifest.permission.CAMERA}, 100);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    public  boolean isGalleryPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(TeacherDashboard.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getApplicationContext()
                        , "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }




    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherDashboard.this);

        builder.setTitle("Change Profile Pic!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }

                else if (options[item].equals("Choose from Gallery"))

                {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PHOTO_REQUEST);
                    }
                    else {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, 1);
                    }


                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();


    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void profilepicupload(String encoded){
        final ProgressDialog dialog=PDialog.showDialog(TeacherDashboard.this);
        String url=ApiUtils.BASE_URL+"profilepicuploadteacher.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);

        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", teacherList.get(0).getPhone());
        postParam.put("image", encoded);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            int status=response.getInt("status");
                            if (status==1) {
                                Toast.makeText(TeacherDashboard.this, response.getString("msg"), Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(TeacherDashboard.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    public void getTeacherImage(){
        final ProgressDialog dialog=PDialog.showDialog(TeacherDashboard.this);
        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();
        String url=ApiUtils.BASE_URL+"teacherdata.php?email="+teacherList.get(0).getPhone();
        final String TAG="Volley Response";
        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            int status=response.getInt("status");
                            if (status==1) {

                                JSONArray jsonArray=response.getJSONArray("data");

                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String encoded=jsonObject.getString("image");
                                Log.d("reencoded",encoded);
                                    byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    if (decodedByte==null){
                                        img_propic.setImageResource(R.drawable.profilepic);
                                    }
                                    else {
                                        img_propic.setImageBitmap(decodedByte);
                                    }



                            }
                            else {
                                Toast.makeText(TeacherDashboard.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);

    }
}
