package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_email,et_password;
    SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new SQLiteHandler(MainActivity.this);
        Button btn_login=(Button)findViewById(R.id.btn_signin);
        TextView btn_signup=(TextView) findViewById(R.id.tv_signup);
        et_email=findViewById(R.id.et_email_login);
        et_password=findViewById(R.id.et_password_login);

        final String activity=getIntent().getStringExtra("activity");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equals("student")){
                    getStudentLogin();
                }
                else if (activity.equals("teacher")){
                    getTeacherLogin();
                }
               // startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
    }


    private void getStudentLogin(){

        final ProgressDialog dialog=PDialog.showDialog(MainActivity.this);
        String url=ApiUtils.BASE_URL+"studentlogin.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);


        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", et_email.getText().toString());
        postParam.put("password", et_password.getText().toString());


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
                                Toast.makeText(MainActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray=response.getJSONArray("StudentData");
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String name=jsonObject.getString("name");
                                String email=jsonObject.getString("email");
                                String phone=jsonObject.getString("phone");
                                String address=jsonObject.getString("address");
                                db.deleteStudent();
                                db.addStudent(name,email,phone,address);
                                startActivity(new Intent(MainActivity.this,StudentDashboardActivity.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private void getTeacherLogin(){

        final ProgressDialog dialog=PDialog.showDialog(MainActivity.this);
        String url=ApiUtils.BASE_URL+"teacherlogin.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", et_email.getText().toString());
        postParam.put("password", et_password.getText().toString());


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
                                Toast.makeText(MainActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray=response.getJSONArray("TeacherData");
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String name=jsonObject.getString("name");
                                String email=jsonObject.getString("email");
                                String phone=jsonObject.getString("phone");
                                db.deleteTeacher();
                                db.addTeacher(name,email,phone);
                                startActivity(new Intent(MainActivity.this,TeacherDashboard.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
