package com.example.lenovo.sweprojectothesis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class VerificationCodeActivity extends AppCompatActivity {

    TextView tv_email;
    EditText et_verificationcode;
    Button btn_done,btn_resend;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        tv_email=findViewById(R.id.tv_emailverification);
        et_verificationcode=findViewById(R.id.et_verificationcode);
        btn_done=findViewById(R.id.btn_done_verificationcode);
        btn_resend=findViewById(R.id.btn_resendcodeemailverify);

        tv_email.setText(getIntent().getStringExtra("email"));

        final String verifystatus=getIntent().getStringExtra("verifystatus");

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifystatus.equals("student")) {
                    getStudentVerify();

                }
                else {
                    getTeacherVerify();
                }
            }
        });


        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifystatus.equals("student")) {
                    resendVerificationEmail();
                }
                else {
                    resendVerificationEmailTeacher();
                }
            }
        });

        gettoolbar(VerificationCodeActivity.this,"Verification");

    }

    private void getStudentVerify(){

        final ProgressDialog dialog=PDialog.showDialog(VerificationCodeActivity.this);
        String url=ApiUtils.BASE_URL+"studentvalidation.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", getIntent().getStringExtra("email"));
        postParam.put("code", et_verificationcode.getText().toString());


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
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VerificationCodeActivity.this,MainActivity.class).putExtra("activity","student"));
                            }
                            else {
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private void getTeacherVerify(){

        final ProgressDialog dialog=PDialog.showDialog(VerificationCodeActivity.this);
        String url=ApiUtils.BASE_URL+"teachervalidation.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", getIntent().getStringExtra("email"));
        postParam.put("code", et_verificationcode.getText().toString());


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
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VerificationCodeActivity.this,MainActivity.class).putExtra("activity","teacher"));
                            }
                            else {
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private void resendVerificationEmail(){

        final ProgressDialog dialog=PDialog.showDialog(VerificationCodeActivity.this);
        String url=ApiUtils.BASE_URL+"resendverification.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", getIntent().getStringExtra("email"));


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
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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

    private void resendVerificationEmailTeacher(){

        final ProgressDialog dialog=PDialog.showDialog(VerificationCodeActivity.this);
        String url=ApiUtils.BASE_URL+"resendteacherverification.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", getIntent().getStringExtra("email"));


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
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(VerificationCodeActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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

    public static void gettoolbar(final Activity activity, String name){
        ImageView img_back=activity.findViewById(R.id.img_backicon);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });

        TextView tv_name=activity.findViewById(R.id.toolbartitle);
        tv_name.setText(name);
    }
}
